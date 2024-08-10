<template>
  <div class="progress-container">
   
    <div class="progress-bar-outer">
      <div class="progress-bar-container">
        <div class="progress-bar" :style="{ width: `${progress}%` }">
          <span class="progress-text">{{ progress }}%</span>
        </div>
      </div>
    </div>
     <button  v-if="progress === 100" @click="downloadPPT" class="download-button">
      下载ppt
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getToken } from "../../utils/auth.ts";
import { useRoute } from "vue-router";
const progress = ref(0); 
const { query } = useRoute();
import axios from 'axios';

const pptId = ref('')

onMounted(() => {
  pptId.value = BigInt(query.pptContentId)
  console.log(pptId.value)
  progressPPt()
});

const progressPPt = async () => {
  const progressBar = document.getElementById('progress-bar'); // Assuming you have a progress bar element

  let intervalId = setInterval(async () => {
    try {
      const { data } = await getToken();
      const response = await axios.get(`http://47.116.201.0:10010/ppt/queryPPT?id=${pptId.value}`, {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });

      // Assuming response.data.data.process is a string like '30-大纲生成完毕'
      const progressStatus = response.data.data.process;

      console.log(progressStatus);

      // Update progress bar
      progress.value = progressStatus;

      if (progress.value >= 100) {
        clearInterval(intervalId); // Stop checking progress

        return;
      }

    } catch (error) {
      console.error('Error fetching thumbnails:', error);
      clearInterval(intervalId); // Stop checking progress on error
    }
  }, 3000); // Check every 3 seconds
};

const downloadPPT = async () => {
  try {
    const { data } = await getToken();
    const response = await axios.get(`http://47.116.201.0:10010/ppt/getPPT?id=${pptId.value}`, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      },
    });
    const download = response.data.data
    window.open(download, '_blank');
  }catch(error){
    console.error('download ppt ', error)
  }
   
}

</script>

<style scoped>
.progress-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh; /* Full viewport height */
  position: relative; /* Ensure relative positioning for child elements */
}

.download-button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-bottom: 20px; /* Space between button and progress bar */
  margin-top: 20px
}

.download-button:hover {
  background-color: #0056b3;
}

.progress-bar-outer {
  width: 100%;
}

.progress-bar-container {
  position: relative; /* Ensure relative positioning for absolute child */
  width: 100%;
  height: 30px; /* Adjust height as needed */
  background-color: #f0f0f0;
  border-radius: 5px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  width: 0%;
  background-color: #007BFF; /* Progress bar color */
  transition: width 0.3s ease;
  position: relative; /* Ensure relative positioning for absolute child */
}

.progress-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff; /* Text color */
  font-weight: bold;
}
</style>