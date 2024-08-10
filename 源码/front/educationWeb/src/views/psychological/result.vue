<template>
  <div class="result-container">
    <div class="result-content">
      <h1>{{ $t("psychological.psyresult") }}</h1>
      <p>{{ responseData }}</p>
      <button @click="goBack" class="back-button">返回</button>
      <button @click="goChat" class="back-button">心理咨询</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { getToken } from "../../utils/auth.ts";

export default {
  data() {
    return {
      FiledId: null, // Initialize FiledId
      responseData: null // To store the response data
    };
  },
  mounted() {
    this.FileId = JSON.parse(this.$route.query.resultData || '{}');
    const { data } = getToken(); // Assuming getToken returns a token in 'data'
    const headers = {
      'token': data
    };
    const params = {
      fileId: this.FileId
    };

    // Make GET request using Axios
    axios.get('http://47.116.201.0:10010/doc/getConclusion', { params, headers })
      .then(response => {
        this.responseData = response.data.data; // Store response data
      })
      .catch(error => {
        console.error('Error fetching API:', error);
      });
  },
  methods: {
    goBack() {
      this.$router.push({ name: 'psychologicalQuestionnaire' });
    },
    goChat() {
      this.$router.push({
        name: 'heartDcotor',
        query: {
          fileId: this.FileId,
          responseData: this.responseData
        }
      });
    },
  }
};
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
}

.result-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  font-family: 'Microsoft YaHei', Arial, sans-serif; /* 使用中文字体 */
}

.result-content {
  border: 1px solid #3498db; /* 使用温暖的绿色边框 */
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
  text-align: center;
  max-width: 800px; /* 设置最大宽度 */
  width: 100%; /* 宽度为100%（最大宽度） */
  box-sizing: border-box; /* 确保 padding 和 border 不会影响宽度 */
}

h1 {
  color: #3498db; /* 使用温暖的绿色 */
  font-size: 32px; /* 更大的标题 */
  margin-bottom: 20px;
}

.healing-message {
  margin-top: 20px;
  font-size: 20px; /* 更大的字体 */
  color: #666;
}

.healing-message img {
  margin-top: 20px;
  max-width: 40%; /* 图片宽度自适应 */
  border-radius: 8px; /* 圆角图片 */
  box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1); /* 图片阴影效果 */
}

.back-button {
  background-color: #3498db; /* 使用温暖的绿色 */
  color: white;
  padding: 10px 20px;
  margin: 10px; /* 适当的边距 */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}
</style>
