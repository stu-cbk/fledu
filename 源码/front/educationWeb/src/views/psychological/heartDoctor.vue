<template>
  <div class="chat-wrapper">
    <!-- Left Column -->
    <div class="chat-column">
      <!-- Display session conclusion data -->
      <div class="session-conclusion" v-if="sessionConclusion">
        <h2>{{ t("psychological.resultDescription") }}</h2>
        <div class="conclusion-content">
          <p>{{ sessionConclusion }}</p>
        </div>
      </div>
      <!-- 对话列表 -->
      <el-scrollbar height="500px">
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
    
    <!-- Right Column -->
    <div class="history-sessions">
      <h2>历史会话</h2>
      <div class="session-card" v-for="(session, index) in historySessions" :key="session.id" @click="getSessionConclusion(session.id)">
        <div class="session-title">{{ session.title }}</div>
        <div class="session-title">{{ session.time }}</div>
        <div class="session-actions">
          <span class="action clear" @click="deleteSession(session.id)">{{ t("chatbot.delete") }}</span>
        </div>
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
import { onMounted } from 'vue';
import { useRoute } from "vue-router";

const messages = ref([]);
const input = ref('');
const { t } = useI18n();
const isSending = ref(true);
const { query } = useRoute();


// 预定义的历史会话数据
const historySessions = ref([]);

var sessionConclusion = ref(null); 

var sessionConclusionId = ref(null); 

const sendMessage = async () => {
  isSending.value = true;
  const { data } = getToken();
  let accumulatedContent = '';
  if (input.value.trim() !== '') {
    try {
      const response = await axios.post(
        'http://47.116.201.0:10010/doc/getSendText',
        {
          question: input.value,
          fileId: sessionConclusionId.value // Assuming sessionConclusion has the fileId
        },
        {
          headers: {
            token: data,
            'Content-Type': 'application/json'
          }
        }
      );
      const sendText = response.data.data

      const responseURL = await axios.get('http://47.116.201.0:10010/doc/getWssURL', {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });
      const wssUrl = responseURL.data.data;

      // Establish WebSocket connection
      const socket = new WebSocket(wssUrl);

      socket.onopen = function (event) {
        console.log("sockent open")
        socket.send(sendText);
      };

      socket.onmessage = function (event) {
       
        const serverResponse = JSON.parse(event.data);
        console.log(serverResponse)
        if(serverResponse.content){
          accumulatedContent += serverResponse.content;
        }

      };

      socket.onclose = function (event) {
        console.log('WebSocket connection closed:', event);
      };

      socket.onerror = function (error) {
        console.error('WebSocket error:', error);
        ElMessage({
          type: 'error',
          message: 'WebSocket error occurred.'
        });
      };
      setTimeout(async () => {
        try {
          console.log(sessionConclusionId.value)
          const saveChatResponse = await axios.post(
            'http://47.116.201.0:10010/doc/saveChat',
            {
              answer: accumulatedContent,
              fileId: sessionConclusionId.value, // Assuming chatId is defined in your scope
              question: input.value,
            },
            {
              headers: {
                'token': data,
                'Content-Type': 'application/json'
              }
            }
          );
          console.log(saveChatResponse)
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
        }
      }, 8000); // 2000 milliseconds = 2 seconds delay
      // Handle response as needed
    } catch (error) {
      console.error('Error sending message:', error);
      ElMessage({
        type: 'error',
        message: transformI18n(t("chatbot.sendfail"))
      });
    } finally {
      
    }
  }
};

const getFileIds = async () => {
  try {
    const { data } = getToken();
    const response = await axios.get('http://47.116.201.0:10010/doc/getFileIds', {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    const responseData = response.data.data;
    console.log(responseData)
    responseData.forEach((item, index) => {
      let title = '';
      switch (item.type) {
        case '0':
          title = '焦虑症自测问答';
          break;
        case '1':
          title = '孤独症自测问答';
          break;
        case '2':
          title = '拖延症自测问答';
          break;
        case '3':
          title = '抑郁症自测问答';
          break;
        case '4':
          title = '成熟自测问答';
          break;
        case '5':
          title = '社交恐惧自测问答';
          break;
        default:
          title = `自测问答 ${index + 1}`;
          break;
      }
      const gmtTime = new Date(item.lastChangeTime);
      const beijingTime = new Date(gmtTime.getTime() + (8 * 60 * 60 * 1000));

      historySessions.value.push({
        id: item.fileId,
        title: title,
        time:beijingTime.toLocaleString('zh-CN', { timeZone: 'Asia/Shanghai' }),
        content: ''  // You can initialize content here if needed
      });
    });

  } catch (error) {
    console.error('Error fetching file IDs:', error);
  }
};


const scrollToBottom = async () => {
  await nextTick();
  const messageElements = document.getElementsByClassName('message-container');
  const lastMessageElement = messageElements[messageElements.length - 1];
  lastMessageElement.scrollIntoView({ behavior: 'smooth', block: 'end' });
};

const formatTimestamp = timestamp => {
  const options = {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric'
  };
  return new Intl.DateTimeFormat('default', options).format(timestamp);
};

const copyMsg = index => {
  const text = messages.value[index].text;
  const textarea = document.createElement('textarea');
  textarea.value = text;
  document.body.appendChild(textarea);
  textarea.select();
  document.execCommand('copy');
  document.body.removeChild(textarea);
  ElMessage({
    type: 'success',
    message: '复制成功'
  });
};

const deleteMsg = (index, type) => {
  ElMessageBox.confirm(transformI18n(t("chatbot.warningContent")), transformI18n(t("chatbot.warning")), {
    confirmButtonText: transformI18n(t("chatbot.makesure")),
    cancelButtonText: transformI18n(t("chatbot.cancel")),
    type: 'warning'
  }).then(() => {
    if (type === 'system') {
      const message = messages.value[index];
      delete message.textSystem;
      message.deleted = true;
    } else if (type === 'self') {
      const message = messages.value[index];
      delete message.text;
      message.deleted2 = true;
    }
    ElMessage({
      type: 'success',
      message: '删除成功'
    });
  }).catch(() => { });
};

const deleteSession = async (sessionId) => {
  try {
    await ElMessageBox.confirm('确定要删除这个历史会话吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    console.log('sessionId', sessionId)
    const { data } = getToken(); // 获取token
    const url = `http://47.116.201.0:10010/doc/deleteHistoryChat`;

    const responseChatDelete = await axios.get(url, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
      params: {
        fileId: sessionId,
      }
    });
    console.log(responseChatDelete)

    const urlField = `http://47.116.201.0:10010/doc/deleteFile`;

    const responseFileDelete = await axios.get(urlField, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
      params: {
        fileId: sessionId,
      }
    });
    console.log(responseFileDelete)   

    // 删除前端显示的历史会话
    const sessionIndex = historySessions.value.findIndex(session => session.id === sessionId);
    if (sessionIndex !== -1) {
      historySessions.value.splice(sessionIndex, 1);
    }
    // 提示删除成功
    ElMessage({
      type: 'success',
      message: '删除成功'
    });

  } catch (error) {
    console.error('Error deleting session:', error);
    ElMessage({
      type: 'error',
      message: '删除会话时出错'
    });
  }
};

const updateHistorySessions = (title, content) => {
  const existingSessionIndex = historySessions.value.findIndex(session => session.title === title);
  if (existingSessionIndex !== -1) {
    historySessions.value[existingSessionIndex].content = content;
  } else {
    const newSession = {
      id: historySessions.value.length + 1,
      title: title,
    };
    historySessions.value.push(newSession);
  }
};

const getSessionConclusion = async (sessionId) => {
  console.log(sessionId)
  messages.value = []
  sessionConclusionId.value = sessionId
  isSending.value = false
  try {
    const { data } = getToken();
    const response = await axios.get(`http://47.116.201.0:10010/doc/getConclusion`, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
      params: {
        fileId: sessionId // Assuming sessionId is the fileId needed for the request
      }
    });
    sessionConclusion.value = response.data.data; // Store response data in sessionConclusion
    console.log('GET Conclusion Response:', response.data);

    // Handle response data as needed
    // Now, make another request to queryUploadProcess
    const queryProcessResponse = await axios.get(`http://47.116.201.0:10010/doc/queryUploadProcess`, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
      params: {
        fileId: sessionId
      }
    });

    const fileId = sessionConclusionId.value;
    console.log(fileId)
    const url = `http://47.116.201.0:10010/doc/getHistoryChat?fileId=${fileId}`;
    try {
      const responseChat = await fetch(url, {
        method: 'GET',
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });
  
      const responseData = await responseChat.json();
      console.log('chat',responseData)

      for (let item of responseData.data) {
        const newMessage = {
          id: Date.now(), // Generate a unique ID for each message
          text: item.question,
          timestamp: new Date(), // Assuming you want to timestamp each message with the current time
          textSystem: item.answer
        };
        messages.value.push(newMessage);
      }
    } catch (error) {
      console.error('Error fetching chat history:', error);
    }
  } catch (error) {
    console.error('Error fetching session conclusion:', error);
  }
};

onMounted(() => {
  getFileIds();
  if(query.fileId){
    sessionConclusionId.value = query.fileId;
    sessionConclusion.value = query.responseData;
    isSending.value = false
  }
});


</script>

<style scoped>
/* 使用柔和的亮蓝色调 */
.chat-wrapper {
  display: flex;
  align-items: flex-start;
  background-color: #f0f5ff; /* 淡蓝色背景 */
}

.chat-column {
  flex: 1;
  margin-right: 20px;
  align-self: flex-start;
}

.history-sessions {
  padding: 20px;
  background-color: #f0f0f0;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.session-conclusion {
  background-color: #cfe0ff; /* 柔和的淡蓝色背景 */
  padding: 10px;
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  max-height: 200px;
  overflow-y: auto;
}

.session-conclusion h2 {
  font-size: 1.2rem;
  margin-bottom: 10px;
  color: #3c82e3; /* 深蓝色文字 */
}

.conclusion-content {
  background-color: #e3edff; /* 淡蓝紫色背景 */
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  max-height: 160px;
  overflow-y: auto;
}

.conclusion-content p {
  margin: 0;
  font-size: 1rem;
  line-height: 1.6;
  color: #5e88c4; /* 深灰蓝色文字 */
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
  background-color: #cfe0ff; /* 柔和的淡蓝色气泡 */
}

.bubble-right {
  background-color: #cfe0ff; /* 柔和的淡蓝色气泡 */
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
  color: #8b9db9; /* 深蓝灰色日期 */
  font-size: 12px;
}

.actions {
  display: flex;
}

.action {
  cursor: pointer;
  margin-left: 10px;
  color: #5284c7; /* 深蓝色操作 */
}

.input-container {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  z-index: 1100;
  height: 100px;
}

.input {
  width: 600px;
}

.actions {
  display: flex;
  align-items: center;
  margin-left: 10px;
}

.send-button {
  margin-left: 10px;
  background-color: #68a3e6; /* 中蓝色按钮 */
  color: #68a3e6;
}

.send-button.is-sending {
  background-color: #cccccc;
  color: #666666;
}

.history-sessions {
  background-color:  #cfe0ff;
  border: 1px solid  #cfe0ff;
  border-radius: 8px;
  padding: 20px;
}

/* 会话卡片样式 */
.session-card {
  background-color: #e3edff;
  border: 1px solid #e3edff;
  border-radius: 6px;
  margin-bottom: 10px;
  padding: 15px;
  cursor: pointer;
  transition: box-shadow 0.3s ease;
}

.session-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 标题样式 */
.session-title {
  font-weight: bold;
  color: #333333;
  font-size: 18px;
  margin-bottom: 5px;
}

/* 操作按钮样式 */
.session-actions {
  margin-top: 10px;
}

.session-actions .action {
  color: #ff4d4f;
  cursor: pointer;
  font-size: 14px;
}

.session-actions .action:hover {
  text-decoration: underline;
}
</style>