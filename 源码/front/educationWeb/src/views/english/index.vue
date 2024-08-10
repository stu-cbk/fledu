<template>
  <el-container style="height: 80vh;">
    <!-- 左侧显示聊天记录 -->
    <el-aside width="50%" style="background: #fff; padding: 10px; border-right: 1px solid #dcdcdc;">
      <el-scrollbar style="height: 100%;">
        <el-timeline>
          <el-timeline-item
            v-for="(message, index) in messages"
            :key="index"
            :timestamp="message.role === 'user' ? 'User' : 'Bot'"
            :color="message.role === 'user' ? 'blue' : 'green'"
          >
            {{ message.text }}
          </el-timeline-item>
        </el-timeline>
      </el-scrollbar>
    </el-aside>

    <!-- 右侧容器 -->
    <el-container>
      <el-header style="height: 100px; border-bottom: thick double #32a1ce; display: flex; flex-direction: column; align-items: center; padding: 10px;">
        <!-- 按钮区域 -->
        <div class="button-container">
          <el-button 
            type="primary" 
            @click="startConversation" 
            class="start-button"
          >开始对话</el-button>
          <el-button 
            type="danger" 
            @click="endConversation" 
            class="end-button"
          >结束对话</el-button>
        </div>
      </el-header>
      <el-container width="100%">
        <el-main style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
          <el-container width="100%">
            <el-aside width="50%" style="position: relative">
              <!-- 摄像头内容 -->
              <video ref="videoElement" autoplay style="width: 90%; height: 100%;"></video>
              <!-- 麦克风图标 -->
              <div v-if="isTalking" class="microphone-container">
                <Microphone />
                <div class="microphone-fill" :style="{ height: micVolume + '%' }"></div>
              </div>
            </el-aside>
            <el-main style="display: flex; flex-direction: column; justify-content: center; align-items: center;">
              <!-- 右侧内容 -->
              <img v-if="showGif" src="@/assets/wating.gif" alt="Waiting" />
            </el-main>
          </el-container>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { Microphone } from '@element-plus/icons-vue';

const messages = ref([]);
const isTalking = ref(false);
const showGif = ref(false);
const videoElement = ref<HTMLVideoElement | null>(null);
const micVolume = ref(0);

let mediaStream: MediaStream | null = null;
let audioContext: AudioContext | null = null;
let analyser: AnalyserNode | null = null;
let microphone: MediaStreamAudioSourceNode | null = null;
let javascriptNode: ScriptProcessorNode | null = null;

const startConversation = async () => {
  showGif.value = true; // 显示 wating.gif
  isTalking.value = true;

  try {
    mediaStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
    if (videoElement.value) {
      videoElement.value.srcObject = mediaStream;
    }

    audioContext = new (window.AudioContext || window.webkitAudioContext)();
    analyser = audioContext.createAnalyser();
    microphone = audioContext.createMediaStreamSource(mediaStream);
    microphone.connect(analyser);
    analyser.fftSize = 256;

    const bufferLength = analyser.frequencyBinCount;
    const dataArray = new Uint8Array(bufferLength);

    javascriptNode = audioContext.createScriptProcessor(2048, 1, 1);
    javascriptNode.onaudioprocess = () => {
      analyser.getByteFrequencyData(dataArray);
      let sum = 0;
      for (let i = 0; i < bufferLength; i++) {
        sum += dataArray[i];
      }
      const average = sum / bufferLength;
      micVolume.value = (average / 128) * 100; // 根据音量调整填充高度
    };

    microphone.connect(javascriptNode);
    javascriptNode.connect(audioContext.destination);
  } catch (error) {
    console.error("Error accessing media devices.", error);
  }
};

const endConversation = () => {
  isTalking.value = false;
  showGif.value = false; // 隐藏 wating.gif

  if (mediaStream) {
    mediaStream.getTracks().forEach(track => track.stop());
    mediaStream = null;
  }

  if (audioContext) {
    audioContext.close();
    audioContext = null;
  }

  if (videoElement.value) {
    videoElement.value.srcObject = null;
  }
};
</script>

<style scoped>
.button-container {
  display: flex;
  gap: 10px;
}

.start-button:hover {
  background-color: green;
  border-color: green;
}

.end-button:hover {
  background-color: red;
  border-color: red;
}

.microphone-container {
  position: absolute;
  bottom: 10px;
  left: 10px;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: flex-end;
  overflow: hidden;
  background-color: transparent;
}

.microphone-fill {
  width: 100%;
  background-color: green;
  transition: height 0.1s;
}

</style>
