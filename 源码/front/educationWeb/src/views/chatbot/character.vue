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
        <el-button @click="centerDialogVisible = true" class="new-character-button">
          <span class="plus-icon">+</span> 新增角色
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
                  加载
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

    <!-- 新增角色对话框 -->
    <el-dialog
      v-model="centerDialogVisible"
      title="新增角色"
      width="500"
      align-center
      center
    >
      <el-form :model="newChatForm">
        <el-form-item label="角色名称">
          <el-input v-model="newChatForm.title"></el-input>
        </el-form-item>
        <el-form-item label="对话场景">
          <el-input type="textarea" v-model="newChatForm.scene" class="scene-input"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="createNewChat">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup lang="ts">
import { ElIcon } from 'element-plus'
import { Close, Delete } from '@element-plus/icons-vue'
import { ref, nextTick } from 'vue';
import axios from 'axios';
import { $t, transformI18n } from "@/plugins/i18n";
import { getToken } from "../../utils/auth.ts";
import { ElButton, ElScrollbar, ElInput, ElMessageBox, ElMessage } from 'element-plus';
import { onMounted } from 'vue';

interface Message {
  text: string;
  from: 'user' | 'bot';
}

interface Chat {
  timestamp: number;
  messages: Message[];
  title: string;
  scene?: string;
}

const icons = { ElIcon, Close, Delete };

const messages = ref<Message[]>([]);
const chatList = ref<Chat[]>([]);
const charName = ref<string[]>([]);
const inputMessage = ref<string>('');
const cid = ref(null)
const messagesContainer = ref<HTMLElement | null>(null);

const chatCounter = ref<number>(1);
const selectedChatIndex = ref<number>(-1);
const centerDialogVisible = ref(false);
const newChatForm = ref<{ title: string; scene: string }>({ title: '', scene: '' });

const sendMessage = async() => {
  const data = getToken().data
  if (inputMessage.value.trim()) {
    const question = inputMessage.value;
    messages.value.push({ text: inputMessage.value, from: 'user' });
    if(!cid.value){
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
                    content: question
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
                console.log(cid.value)
                const saveChatResponse = await axios.post('http://47.116.201.0:10010/helper/saveChat',
                {
                    answer: accumulatedContent,
                    cid: cid.value,
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
    }else{
      try{
        const ChatResponse = await axios.post('http://47.116.201.0:10010/helper/customizeChat',
          {
            cid: cid.value,
            question: question,
          },
          { 
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            }
          }
        );
        const sendText = ChatResponse.data.data.sendText;
        const wssUrl = ChatResponse.data.data.requestUrl
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
          console.log(cid.value)
            const saveChatResponse = await axios.post('http://47.116.201.0:10010/helper/saveChat',
              {
                answer: accumulatedContent,
                cid: cid.value,
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
  }
};

const createNewChat = async() => {
  const data = getToken().data
  const isInCharNameList = charName.value.includes(newChatForm.value.title);
  if (isInCharNameList) {
    ElMessage({
        type: 'error',
        message: '角色名称重复'
    });
    return;
  } else {
    charName.value.push(newChatForm.value.title)
    const createResponse = await axios.post(
        'http://47.116.201.0:10010/helper/createCharacter',
        {
            mission: newChatForm.value.scene,
            name: newChatForm.value.title,
        },
        {
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            }
        }
    );
    chatList.value.push({
        timestamp: Date.now(),
        messages: [],
        title: newChatForm.value.title,
        scene: newChatForm.value.scene,
        cid: createResponse.data.data
    });
    chatCounter.value++;
    selectedChatIndex.value = chatList.value.length - 1;
    messages.value = [];
    centerDialogVisible.value = false;
    newChatForm.value = { title: '', scene: '' };
  }
  
};

const loadChat = async(index: number) => {
  const selectedChat = chatList.value[index];
  cid.value = selectedChat.cid
  console.log(cid.value)
  if (selectedChat) {
    selectedChatIndex.value = index;
    messages.value = [...selectedChat.messages];
    nextTick(() => {
      const container = messagesContainer.value;
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    });
  }
  messages.value = []
  const id = chatList.value[index].cid
  const data = getToken().data
  const responseChat = await axios.get(`http://47.116.201.0:10010/helper/getHistoryData`, 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    },
    params: {
      id: id
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
  console.log(chatList)
  const responsedelete = await axios.get(`http://47.116.201.0:10010/helper/deleteCharacter`, 
  {
    headers: {
      'token': data,
      'Content-Type': 'application/json'
    },
    params: {
      id: chatList.value[index].cid
    }
  });
  console.log(responsedelete)
  chatList.value.splice(index, 1);
  if (selectedChatIndex.value === index) {
    selectedChatIndex.value = -1;
  }
};

const clearMainChat = async() => {
  console.log(selectedChatIndex.value)
  const data = getToken().data
  messages.value = [];
  let id;
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
  cid.value = null
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
    const data = getToken().data
    loadMainChat()
    try {
        const response = await axios.get('http://47.116.201.0:10010/helper/info', 
        {
            headers: {
                'token': data,
                'Content-Type': 'application/json'
            }
        });
        const responseIds = await axios.get('http://47.116.201.0:10010/helper/queryAllId', 
        {
            headers: {
                'token': data,
                'Content-Type': 'application/json'
            }
        });
        const chats = responseIds.data.data;
        for(let i = 0; i < chats.length; i++){
          const chat = chats[i]
          chatList.value.push({
            timestamp: Date.now(),
            messages: [],
            title: chat.name,
            scene: chat.mission,
            cid: chat.cid
          });
        }
    }catch (error) {
        console.error('Error sending message:', error);
        ElMessage({
            type: 'error',
            message: transformI18n(t("chatbot.sendfail"))
        });
    }
    
});
</script>

<style scoped>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  overflow: hidden;
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
  overflow: hidden;
}

.messages {
  flex: 1;
  overflow-y: auto;
}

.message {
  margin: 0.5rem 0;
  max-width: 100%;
  word-break: break-word;
}

.message-card.is-always-shadow {
  box-shadow: none;
}

.message.user .message-card {
  background-color: rgb(200, 200, 200);
  color: black;
  margin-left: auto;
  max-width: 50%;
  border-radius: 50px;
}

.message.bot .message-card {
  background-color: transparent;
  color: black;
  max-width: 100%;
  min-height: 50px;
}

.input-containers {
  height: 80px;
  display: flex;
  align-items: center;
}

.input-container {
  height: 100%;
  width: 100%;
  align-items: center;
  display: flex;
}

.history-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
}

.header {
  padding: 1rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
}

.new-character-button {
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
  margin-right: 0.5rem;
  transition: color 0.3s;
}

.clear-chat-button:hover {
  background-color: #fdd;
  color: darkred;
}

.clear-chat-button:hover el-icon {
  color: darkred;
}

.plus-icon {
  font-size: 1.5rem;
  margin-right: 0.5rem;
}

.history-list {
  flex: 1;
  overflow-y: auto;
}

.chat-item {
  margin: 0.5rem 0;
  width: 100%;
}

.chat-item.active-chat {
  background-color: #8cc0dd;
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
  background-color: transparent;
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
  background-color: transparent;
  border: none;
  padding: 0;
  cursor: pointer;
  text-align: center;
  display: inline-block;
}

.chat-preview-time:hover {
  color: #000;
  text-decoration: underline;
}

.main-chat .chat-preview-time {
  color: red;
}

.delete-button {
  margin-left: 0.5rem;
  font-size: 1.2rem;
  color: black;
  background: white;
  border: none;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
}

.delete-icon {
  color: black;
  transition: color 0.3s ease, border 0.3s ease;
}

.delete-button:hover .delete-icon {
  color: red;
}

.delete-button:hover {
  border: 2px solid red;
  border-radius: 4px;
}

.chat-card {
  margin: 0;
  background-color: transparent;
}

/* 新增角色对话框样式 */
.scene-input {
  height: calc(1.5 * 3rem); /* 角色名称行高的3倍 */
}
</style>
