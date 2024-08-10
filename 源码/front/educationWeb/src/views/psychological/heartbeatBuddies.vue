<template>
  <div class="chat-container">
    <!-- 按钮 -->
    <div class="button-container">
      <el-button class="action clear" @click="clearHistory"> {{ t("chatbot.clearHistory") }}</el-button>
      <el-button class="action load" @click="loadHistory">{{ t("chatbot.loadHistory") }}</el-button>
    </div>
    <!-- 对话列表 -->
    <el-scrollbar height="610px">
      <!-- 初始系统消息 -->
      <div class="message-container">
        <div class="right-message">
          <div class="bubble bubble-right">
            <div class="content">{{ t("psychological.first") }}</div>
            <div class="info">
              <div class="date">{{ formatTimestamp(new Date()) }}</div>
            </div>
          </div>
        </div>
      </div>
      <!-- 选项按钮 -->
      <div class="options-container">
        <el-button class="option-button" @click="selectOption($event.target.innerText)">{{ t("psychological.studyProblem") }}</el-button>
        <el-button class="option-button" @click="selectOption($event.target.innerText)">{{ t("psychological.lifeProblem") }}</el-button>
        <el-button class="option-button" @click="selectOption($event.target.innerText)">{{ t("psychological.connectionProblem") }}</el-button>
        <el-button class="option-button" @click="selectOption($event.target.innerText)">{{ t("psychological.otherProblem") }}</el-button>
      </div>
      <!-- 实际消息列表 -->
      <div class="message-container" v-for="(item, index) in messages" :key="item.id">
        <!-- 左侧自己的回复 -->
        <div class="left-message" v-if="!item.deleted2">
          <div class="bubble bubble-left">
            <div class="content">{{ item.text }}</div>
            <div class="info">
              <div class="date">{{ formatTimestamp(item.timestamp) }}</div>
              <div class="actions">
                <span class="action" @click="deleteMsg(index, 'self')">{{ t("chatbot.delete") }}</span>
                <span class="action" @click="copyMsg(index)">{{ t("chatbot.copy") }}</span>
              </div>
            </div>
          </div>
        </div>
        <!-- 右侧系统的回复 -->
        <div class="right-message" v-if="!item.deleted">
          <div class="bubble bubble-right">
            <div class="content">{{ item.textSystem }}</div>
            <div class="info">
              <div class="date">{{ formatTimestamp(item.timestamp) }}</div>
              <div class="actions">
                <span class="action" @click="deleteMsg(index, 'system')">{{ t("chatbot.delete") }}</span>
                <span class="action" @click="copyMsg(index)">{{ t("chatbot.copy") }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
    <!-- 输入框 -->
    <div class="input-container">
      <el-input
        v-model="input"
        @keydown.enter.prevent="sendMessage"
        :placeholder="t('chatbot.input')"
        class="input"
        type="textarea"
        :rows="3"
        autosize
        :style="{ maxHeight: '90px', overflowY: 'auto' }"
      ></el-input>
      <div class="actions">
        <el-button
          :class="{ 'is-sending': isSending }"
          :type="isSending ? 'default' : 'success'"
          :disabled="isSending"
          @click="sendMessage"
          :icon="isSending ? 'el-icon-loading' : Position"
        >
          {{ isSending ? t("chatbot.waiting") : t("chatbot.send") }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ElButton, ElScrollbar, ElInput, ElMessageBox, ElMessage } from 'element-plus';
import { Position } from '@element-plus/icons-vue';
import { ref, nextTick } from 'vue';
import { useI18n } from "vue-i18n";
import { $t, transformI18n } from "@/plugins/i18n";
import { getToken } from "../../utils/auth.ts";
import axios from 'axios';

const messages = ref([]) // 总回复
const input = ref('')
const isGptShow = ref(false)
const { t } = useI18n();
const isSending = ref(false);

const sendMessage = async () => {
  isSending.value = true;
  const { data } = getToken();
  if (input.value.trim() !== '') {
    try {
      const response = await axios.post(
        'http://47.116.201.0:10010/helper/mentalChat',
        { text: input.value },
        {
          headers: {
            'token': data,
            'Content-Type': 'application/json'
          }
        }
      );
      const wssUrl = response.data.data.requestUrl;
      const sendText = response.data.data.sendText;
      const sendTextJson = JSON.parse(response.data.data.sendText); // Parse sendText JSON
      const chatId = sendTextJson.parameter.chat.chat_id;

      // Establish WebSocket connection
      const socket = new WebSocket(wssUrl);

      let accumulatedContent = '';

      socket.onopen = function (event) {
        console.log('WebSocket connection established.');
        // Send the sendText message over WebSocket
        socket.send(sendText);
      };

      socket.onmessage = function (event) {
        const serverResponse = JSON.parse(event.data);

        // 拼接收到的 content 到全局作用域的字符串
        if (serverResponse.payload && serverResponse.payload.choices && serverResponse.payload.choices.text) {
          serverResponse.payload.choices.text.forEach(choice => {
            if (choice.content) {
              accumulatedContent += choice.content;
            }
          });
        }
      };

      socket.onclose = function (event) {
        console.log('WebSocket connection closed:', event);
      };

      socket.onerror = function (error) {
        console.error('WebSocket error:', error);
        // Handle WebSocket error
        ElMessage({
          type: 'error',
          message: 'WebSocket error occurred.'
        });
      };
      setTimeout(async () => {
        try {
          // Make a POST request to saveChat endpoint
          const saveChatResponse = await axios.post(
            'http://47.116.201.0:10010/helper/saveChat',
            {
              answer: accumulatedContent,
              chatId: chatId, // Assuming chatId is defined in your scope
              question: input.value,
              type: "1"
            },
            {
              headers: {
                'token': data,
                'Content-Type': 'application/json'
              }
            }
          );
          console.log(chatId, accumulatedContent, input.value)
          console.log('Chat saved successfully:', saveChatResponse);
          // After successfully saving the chat, update the UI
          const newMessage = {
            id: Date.now(),
            text: input.value,
            timestamp: new Date(),
            textSystem: accumulatedContent,
          };
          messages.value.push(newMessage);
          input.value = '';
          scrollToBottom();
          isSending.value = false;
        } catch (error) {
          console.error('Error saving chat:', error);
          // Handle error appropriately
        }
      }, 2000); // 2000 milliseconds = 2 seconds delay

    } catch (error) {
      console.error('Error sending message:', error);
      ElMessage({
        type: 'error',
        message: transformI18n(t("chatbot.sendfail"))
      });
      isSending.value = false;
    }
  }
};

const clearHistory = async () => {
  const { data } = getToken();
  messages.value = [] // 清空消息列表
  try {
    const url = 'http://47.116.201.0:10010/helper/clearHistoryData?type=1';
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'token': data
      }
    });
  } catch (error) {
    console.error('Error loading history:', error);
  }
}

const loadHistory = async () => {
  const { data } = getToken();
  try {
    const url = 'http://47.116.201.0:10010/helper/getHistoryData?type=1';
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'token': data
      }
    });
    const responseData = await response.json();
    for (let item of responseData.data) {
      const newMessage = {
        id: Date.now(),
        text: item.question,
        timestamp: new Date(),
        textSystem: item.answer,
      };
      messages.value.push(newMessage);
    }
  } catch (error) {
    console.error('Error loading history:', error);
  }
}

const scrollToBottom = async () => {
  await nextTick()
  const messageElements = document.getElementsByClassName('message-container')
  const lastMessageElement = messageElements[messageElements.length - 1]
  lastMessageElement.scrollIntoView({ behavior: 'smooth', block: 'end' })
}

const formatTimestamp = timestamp => {
  const options = {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric'
  }
  return new Intl.DateTimeFormat('default', options).format(timestamp)
}

const copyMsg = index => {
  const text = messages.value[index].text
  const textarea = document.createElement('textarea')
  textarea.value = text
  document.body.appendChild(textarea)
  textarea.select()
  document.execCommand('copy')
  document.body.removeChild(textarea)
  ElMessage({
    type: 'success',
    message: '复制成功'
  })
}

const deleteMsg = (index, type) => {
  ElMessageBox.confirm(transformI18n(t("chatbot.warningContent")), transformI18n(t("chatbot.warning")), {
    confirmButtonText: transformI18n(t("chatbot.makesure")),
    cancelButtonText: transformI18n(t("chatbot.cancel")),
    type: 'warning'
  }).then(() => {
    if (type === 'system') {
      const message = messages.value[index]
      delete message.textSystem
      message.deleted = true
    } else if (type === 'self') {
      const message = messages.value[index]
      delete message.text
      message.deleted2 = true
    }
    ElMessage({
      type: 'success',
      message: '删除成功'
    })
  }).catch(() => { })
}

const selectOption = (optionText) => {
  // 设置输入框的值为选项按钮上显示的文本
  input.value = optionText;
  // 发送选项文本作为消息
  console.log('input.value',input.value)
  sendMessage();
};


</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.button-container {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}
.action {
  cursor: pointer;
  margin-left: 10px;
  color: #1296db;
}
.clear {
  color: #ff4500; /* Red color for clear action */
}
.load {
  color: #32cd32; /* Green color for load action */
}
.message-container {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}
.left-message {
  display: flex;
  flex-direction: row-reverse;
  align-items: flex-start;
}
.right-message {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}
.bubble {
  max-width: 70%;
  border-radius: 10px;
  padding: 10px;
  margin: 5px;
}
.bubble-left {
  background-color: #cfe8ff; /* 浅蓝色 */
}
.bubble-right {
  background-color: #cfe8ff; /* 默认灰色 */
}
.content {
  word-wrap: break-word;
  flex: 1;
}
.info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 5px;
}
.date {
  color: #999;
  font-size: 12px;
}
.actions {
  display: flex;
}
.action {
  cursor: pointer;
  margin-left: 10px;
  color: #1296db;
}
.input-container {
  display: flex;
  align-items: center;
  margin-top: 20px;
}
.actions {
  display: flex;
  align-items: center;
  margin-left: 10px;
}
.clear {
  cursor: pointer;
  color: #1296db;
}

.send-button {
  margin-left: 10px;
  background-color: #32cd32; /* Light green */
  color: #ffffff; /* White text */
}

.send-button.is-sending {
  background-color: #cccccc; /* Gray when sending */
  color: #666666; /* Darker text for contrast */
}

.options-container {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.option-button {
  margin: 5px;
}

/* 修改 el-loading 样式 */
.el-loading-parent--relative {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
