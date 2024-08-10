<template>
  <el-container class="outer-container">
    <el-aside width="75%" class="left-container">
      <div class="chat-container">
        <div class="messages" ref="messagesContainer">
          <div 
            v-for="(message, index) in messages" 
            :key="index" 
            class="message" 
            :class="message.from"
          >
            <el-card class="message-card" :body-style="{padding:'10px'}">
              <div class="message-content">
                <span v-html="message.text"></span>
              </div>
            </el-card>
          </div>
        </div>
        <div class="input-containers">
          <el-row :gutter="10" class="input-container">
            <el-col :span="20">
              <el-input v-model="inputMessage" @keyup.enter="sendMessage" placeholder="Type your message..."></el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="primary" @click="sendMessage" style="width: 100%;">Send</el-button>
            </el-col>
          </el-row>
        </div>
      </div>
    </el-aside>
    <el-container class="history-container">
      <el-header class="header">
        <el-button @click="clearMainChat" class="clear-chat-button" :class="{ 'active-chat': selectedChatIndex === -1 }">
         <el-icon><delete /></el-icon> 清空当前对话
        </el-button>
        <el-button @click="newChat" class="new-chat-button">
          <span class="plus-icon">+</span> 新增对话
        </el-button>
      </el-header>
      <el-main>
        <div class="history-list">
          <!-- Main Chat -->
          <div class="chat-item main-chat" :class="{ 'active-chat': selectedChatIndex === -1 }">
            <el-card class="chat-card" :body-style="{padding:'10px',width: '100%', backgroundColor: 'transparent'}">
              <div class="chat-preview">
                <span class="chat-preview-text">
                  Main Chat
                </span>
                <el-button class="load-chat-button" @click="loadMainChat" type="info" round>
                  加载聊天记录
                </el-button>
              </div>
            </el-card>
          </div>
          <!-- Chat List -->
          <div 
            v-for="(chat, index) in chatList" 
            :key="index" 
            class="chat-item"
            :class="{ 'active-chat': selectedChatIndex === index }"
          >
            <el-card class="chat-card" :body-style="{padding:'10px',width: '100%', backgroundColor: 'transparent'}">
              <div class="chat-preview">
                <span class="chat-preview-text">
                  {{ chat.title }}
                </span>
                <el-button 
                  class="chat-preview-time" 
                  @click="loadChat(index)"
                >
                  {{ formatDate(chat.timestamp) }}
                </el-button>
                <el-button 
                  class="delete-button" 
                  @click.stop="deleteChat(index)"
                >
                  <el-icon class="delete-icon"><close /></el-icon>
                </el-button>
              </div>
            </el-card>
          </div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>


<script setup lang="ts">
import { ElIcon } from 'element-plus'
import { Close, Delete } from '@element-plus/icons-vue'
import { ref, nextTick } from 'vue';
import { getToken } from "../../utils/auth.ts";
import { ElButton, ElScrollbar, ElInput, ElMessageBox, ElMessage } from 'element-plus';
import { Position } from '@element-plus/icons-vue';
import { $t, transformI18n } from "@/plugins/i18n";
import { onMounted } from 'vue';
import axios from 'axios';

interface Message {
  text: string;
  from: 'user' | 'bot';
}

interface Chat {
  timestamp: number;
  messages: Message[];
  title: string; // 添加标题字段
}

const icons = { ElIcon, Close, Delete };
const chatId = ref(null)

const messages = ref<Message[]>([]);
const chatList = ref<Chat[]>([]);
const inputMessage = ref<string>('');
const messagesContainer = ref<HTMLElement | null>(null);

const chatCounter = ref<number>(1); // 添加计数器
const selectedChatIndex = ref<number>(-1); // 添加 selectedChatIndex 变量

// 初始化 界面需要调用/queryChat接口
const sendMessage = async() => {
  const data = getToken().data
  if (inputMessage.value.trim()) {
    const question = inputMessage.value.trim()
    messages.value.push({ text: inputMessage.value, from: 'user' });
    console.log(inputMessage.value.trim())
    try {
      const responseURL = await axios.get('http://47.116.201.0:10010/helper/getBaseWssURL', 
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });
      const wssUrl = responseURL.data.data
      const responseText = await axios.post('http://47.116.201.0:10010/helper/getBaseSendText', 
      {
        content: inputMessage.value
      },
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });
      const sendText = responseText.data.data;

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
      setTimeout(async() => {
        const botMessage: Message = { text: accumulatedContent, from: 'bot' };
        messages.value.push(botMessage);
        nextTick(() => {
          const container = messagesContainer.value;
          if (container) {
            container.scrollTop = container.scrollHeight;
          }
        });
        console.log(chatId.value)
        const saveChatResponse = await axios.post(
          'http://47.116.201.0:10010/helper/saveChat',
          {
            answer: accumulatedContent,
            cid: chatId.value,
            question: question,
          },
          {
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            }
          }
        );
        inputMessage.value = '';
        console.log(saveChatResponse)
      }, 2000);
    }catch (error) {
      console.error('Error sending message:', error);
      ElMessage({
        type: 'error',
        message: transformI18n(t("chatbot.sendfail"))
      });
    }
  }
};

const newChat = async() => {
  const data = getToken().data
  const responseChat = await axios.get('http://47.116.201.0:10010/helper/createChat', 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    }
  });
  console.log(responseChat)
  chatId.value = responseChat.data.data
  chatList.value.push({
    timestamp: Date.now(),
    messages: [],
    title: `Chat ${chatCounter.value}`, // 设置标题
    id: responseChat.data.data
  });
  chatCounter.value++; // 递增计数器
  selectedChatIndex.value = chatList.value.length - 1; // 设置为新聊天
  messages.value = [];
};

const loadChat = async(index: number) => {
  // 调用/getHistoryChat接口
  const selectedChat = chatList.value[index];
  chatId.value = chatList.value[index].id
  if (selectedChat) {
    selectedChatIndex.value = index; // 更新 selectedChatIndex
    messages.value = [...selectedChat.messages];
    nextTick(() => {
      const container = messagesContainer.value;
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    });
  }
  console.log(chatList.value[index])
  const data = getToken().data
  messages.value = []
  console.log(chatList.value[index].id)
  const responseChat = await axios.get(`http://47.116.201.0:10010/helper/getHistoryData`, 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    },
    params: {
      id: chatList.value[index].id
    }
  });
  const chat = responseChat.data.data
  console.log(responseChat)
  // 遍历数据并处理
  for (let i = 0; i < chat.length; i += 1) {
    // 获取当前的 answer 和 question 对象
    const Obj = chat[i];

    // 将数据推入 messages.value 数组中
    messages.value.push({
      text: Obj.question,
      from: 'user'  // 假设这是 bot 发来的消息
    });

    messages.value.push({
      text: Obj.answer,
      from: 'bot'  // 假设这是用户发送的消息
    });
  }
};

const deleteChat = async(index: number) => {
  const data = getToken().data
  const responsedelete = await axios.get(`http://47.116.201.0:10010/helper/deleteChat`, 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    },
    params: {
      id: chatList.value[index].id
    }
  });
  console.log(responsedelete)
  chatList.value.splice(index, 1);
  if (selectedChatIndex.value === index) {
    selectedChatIndex.value = -1; // 如果删除的是当前选中的对话，重置 selectedChatIndex
  }
};

const clearMainChat = async() => {
  console.log(selectedChatIndex.value)
  const data = getToken().data
  messages.value = [];
  let id
  if(selectedChatIndex.value != -1){
    console.log(chatList.value[selectedChatIndex.value].id)
    id = chatList.value[selectedChatIndex.value].id
    const responseclear = await axios.get(`http://47.116.201.0:10010/helper/clearHistoryData`, 
    {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
      params: {
        id: id
      }
    });
    console.log(responseclear)  
  }else{
    const responseclear = await axios.get(`http://47.116.201.0:10010/helper/clearHistoryData?id`, 
    {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
    });
    console.log(responseclear)  
  }
};

const formatDate = (timestamp: number) => {
  const date = new Date(timestamp);
  return date.toLocaleDateString();
};

const loadMainChat = async() => {
  messages.value = []
  const data = getToken().data
  console.log(data)
  console.log('http://47.116.201.0:10010/helper/getHistoryData')
  const responseChat = await axios.get('http://47.116.201.0:10010/helper/getHistoryData?id', 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    }
  });
  const chat = responseChat.data.data
  console.log(chat)
  // 遍历数据并处理
  for (let i = 0; i < chat.length; i += 1) {
    // 获取当前的 answer 和 question 对象
    const Obj = chat[i];

    // 将数据推入 messages.value 数组中
    messages.value.push({
      text: Obj.question,
      from: 'user'  // 假设这是 bot 发来的消息
    });

    messages.value.push({
      text: Obj.answer,
      from: 'bot'  // 假设这是用户发送的消息
    });
  }
  selectedChatIndex.value = -1; // 设置为 main-chat
};

onMounted(async() => {
  loadMainChat()
  const data = getToken().data
  const responseChats = await axios.get('http://47.116.201.0:10010/helper/queryChat', 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    }
  });
  const chats = responseChats.data.data
  console.log(chats)
  for(let i = 0; i < chats.length; i+=1){
    const chat = chats[i];
    chatList.value.push({
      timestamp: chat.time,
      messages: [],
      title: `Chat ${chatCounter.value}`, // 设置标题
      id: chat.userId
  });
  chatCounter.value++; // 递增计数器
  }
});
</script>

<style scoped>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  overflow: hidden; /* Ensure no overflow on the body */
}

h1 {
  margin: 0;
  padding: 1rem;
  text-align: center;
}

.outer-container {
  height: 85vh;
  display: flex;
}

.left-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 75%;
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden; /* Prevent scrolling on the chat container */
}

.messages {
  flex: 1;
  overflow-y: auto; /* Enable vertical scrolling for the messages area */
}

.message {
  margin: 0.5rem 0;
  max-width: 100%; /* Limit card width */
  word-break: break-word; /* Prevent long words from causing overflow */
}

.message-card.is-always-shadow {
  box-shadow: none;
}

.message.user .message-card {
  background-color: rgb(200, 200, 200); /* Grey background for user messages */
  color: black; /* Black font color for user messages */
  margin-left: auto;
  max-width: 50%; /* Limit card width */
  border-radius: 50px; /* Rounded corners for user messages */
}

.message.bot .message-card {
  background-color: transparent; /* Transparent background for bot messages */
  color: black; /* Black font color for bot messages */
  max-width: 100%; /* Limit card width */
  min-height: 50px;
}

.input-containers {
  height: 80px; /* Fixed height for the input area */
  display: flex;
  align-items: center;
}

.input-container {
  height: 100%; /* Make input-container fill its parent */
  width: 100%; /* Ensure it takes full width */
  align-items: center;
  display: flex; /* Ensure children align correctly */
}

.history-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2); /* Left shadow */ 
}

.header {
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2); /* Left shadow */ 
}

.new-chat-button {
  display: flex;
  align-items: center;
  height: 100%;
  width: 50%;
}

.clear-chat-button {
  display: flex;
  align-items: center;
  height: 100%;
  width: 50%;
}

.clear-chat-button el-icon {
  margin-right: 0.5rem; /* Space between icon and text */
  transition: color 0.3s; /* 添加颜色过渡 */
}

.clear-chat-button:hover {
  background-color: #fdd; /* Light red background on hover */
  color: darkred; /* 深红色字体 */
}

.clear-chat-button:hover el-icon {
  color: darkred; /* 深红色图标 */
}

.plus-icon {
  font-size: 1.5rem;
  margin-right: 0.5rem;
}

.history-list {
  flex: 1;
  overflow-y: auto; /* Enable vertical scrolling for the chat history */
}

.chat-item {
  margin: 0.5rem 0;
  width: 100%;
}

.chat-item.active-chat {
  background-color: #8cc0dd; /* 灰色背景 */
}

.date-group {
  margin-bottom: 1rem;
}

.date-label {
  font-size: 1.2rem;
  font-weight: bold;
  padding: 0.5rem;
  background-color: #f0f0f0;
  border-bottom: 1px solid #ddd;
}

.chat-preview {
  display: flex;
  background-color: transparent; /* Transparent background */
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem;
  flex: 1;
}

.chat-preview-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.chat-preview-time {
  font-size: 0.8rem;
  color: #888;
  background-color: transparent; /* Transparent background */
  border: none; /* Remove default button border */
  padding: 0; /* Remove default padding */
  cursor: pointer; /* Change cursor to pointer */
  text-align: center; /* Center text */
  display: inline-block; /* Adjust display to inline-block */
}

.chat-preview-time:hover {
  color: #000; /* Change color on hover */
  text-decoration: underline; /* Add underline on hover */
}

.main-chat .chat-preview-time {
  color: red; /* Highlight the clear chat button */
}

.delete-button {
  margin-left: 0.5rem;
  font-size: 1.2rem;
  color: black;
  background: white;
  border: none; /* Remove default button border */
  cursor: pointer;
  padding: 0; /* Remove default button padding */
  display: flex; /* Align content properly */
  align-items: center; /* Center align the "X" */
}

.delete-icon {
  color: black; /* Default icon color */
  transition: color 0.3s ease, border 0.3s ease; /* Smooth color and border transition */
}

.delete-button:hover .delete-icon {
  color: red; /* Icon color on hover */
}

.delete-button:hover {
  border: 2px solid red; /* Red border on hover */
  border-radius: 4px; /* Optional: rounded corners for the border */
}

.chat-card {
  margin: 0;
  background-color: transparent;
}

</style>

