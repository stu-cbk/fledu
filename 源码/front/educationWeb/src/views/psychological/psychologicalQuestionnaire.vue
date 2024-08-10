<template>
  <div class="questionnaire-container">
    <h1>心理健康自测问卷</h1>
    <div class="questionnaire-buttons">
      <!-- 问卷类型选择按钮 -->
      <button @click="selectQuestionnaire(0)">焦虑症自测</button>
      <button @click="selectQuestionnaire(1)">孤独症自测</button>
      <button @click="selectQuestionnaire(2)">拖延症自测</button>
      <button @click="selectQuestionnaire(3)">抑郁症自测</button>
      <button @click="selectQuestionnaire(4)">成熟自测</button>
      <button @click="selectQuestionnaire(5)">社交恐惧自测</button>
    </div>

    <!-- 根据selectedQuestionnaire是否为null来决定显示问卷表单 -->
    <div v-if="selectedQuestionnaire !== null" class="questionnaire-form">
      <div v-for="(question, index) in questions" :key="index" class="question">
        <div>{{index+1}}.{{ question.question }}</div>
        <div class="options">
          <div class="option" v-for="(option, optionIndex) in question.options" :key="optionIndex">
             <div style="display: block; /* 确保垂直排列 */">
              <input type="radio" :name="'question' + index" :value="option" v-model="question.selectedOption">
              {{ option }}
            </div>
          </div>
        </div>
      </div>
      <button class="submit-button" @click="submitForm(selectedQuestionnaire)">提交问卷</button>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue';
import { getToken } from "../../utils/auth.ts"; // Make sure auth.ts handles token retrieval properly

export default {
  data() {
    return {
      selectedQuestionnaire: null,
      questions: [], // Array to store fetched questions
      currentQuestionIndex: 0,
    };
  },
  methods: {
    selectQuestionnaire(questionnaireNumber) {
      this.selectedQuestionnaire = questionnaireNumber;
      const { data } = getToken(); // Ensure this function returns the token correctly
      const url = `http://47.116.201.0:10010/doc/getQuestionAndOptions?type=${questionnaireNumber}`;
      
      fetch(url, {
        method: 'GET',
        headers: {
          'token': data
        }
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        // Assuming data.data is an array of questions with options
        this.questions = data.data.map(question => ({
          ...question,
          selectedOption: null, // Initialize selectedOption for each question
          options: Object.values(question).filter(value => typeof value === 'string').slice(1) // Assuming options are strings
        }));
        this.currentQuestionIndex = 0; // Start with the first question
      })
      .catch(error => {
        console.error('Error loading data:', error);
      });
    },
    submitForm(questionnaireNumber) {
      const allQuestionsAnswered = this.questions.every(question => question.selectedOption !== null);

      if (!allQuestionsAnswered) {
        alert('请回答所有问题再提交问卷。');
        return;
      }

      // Map selected options to 1-based index (assuming options are strings)
      const optionsToSend = this.questions.map(question => {
        const selectedOptionIndex = question.options.indexOf(question.selectedOption) + 1;
        return selectedOptionIndex;
      });

      const { data } = getToken(); // Retrieve token
      const url = 'http://47.116.201.0:10010/doc/uploadOptions';

      // Prepare data for POST request
      const postData = {
        options: optionsToSend,
        type: String(questionnaireNumber) // Convert to string as required
      };

      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'token': data
        },
        body: JSON.stringify(postData)
      })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(responseData => {
        // Assuming responseData contains the necessary data for redirection
        this.resetForm();
        this.$router.push({ 
          name: 'psyresult',
          query: { resultData: JSON.stringify(responseData.data) } // Pass data as a query parameter
        });
      })
      .catch(error => {
        console.error('Error uploading options:', error);
      });
    },
    resetForm() {
      this.selectedQuestionnaire = null;
      this.questions = [];
      this.currentQuestionIndex = 0;
    }
  }
};
</script>

<style scoped>
.questionnaire-container {
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  text-align: center;
}

h1 {
  color: #333;
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
}

.questionnaire-buttons {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  margin-bottom: 20px;
}

.questionnaire-buttons button {
  background-color: #cfe0ff;
  color: black;
  border: none;
  padding: 10px 18px; /* Adjusted padding */
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  margin: 8px;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.3s ease;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

.questionnaire-buttons button:hover {
  background-color: #2980b9;
}

.questionnaire-form {
  max-width: 600px; /* Adjust the width as per your requirement */
  margin: 0 auto; /* Center align horizontally */
  background-color: #ffffff;
  padding: 30px;
  padding-left: 100px;
  border-radius: 12px;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
}

.question {
  margin-bottom: 30px;
  font-size: 16px;
  text-align: left; /* Right-align the questions */
}

h2 {
  color: #555;
  font-size: 24px;
  margin-bottom: 15px;
}

.options {
  justify-content: center;
  flex-wrap: wrap;
}

.option {
  margin-right: 30px;
  text-align: left; /* Right-align the options */
}

input[type="radio"] {
  margin-right: 8px;
}

.submit-button {
  background-color: #cfe0ff;
  color: black;
  padding: 12px 24px; /* Adjusted padding */
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.1s ease;
  font-size: 18px;
  margin-top: 30px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}

.submit-button:hover {
  background-color: #cfe0ff;
  transform: scale(1.02);
}
</style>
