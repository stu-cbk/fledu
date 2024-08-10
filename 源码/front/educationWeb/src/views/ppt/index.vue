<template>
  <div class="container">
    <div class="left-section">
      <div class="image-scroll-box">
        <div v-for="(thumbnail, index) in thumbnails" :key="index" class="image-row"
             @click="selectImage(thumbnail.key)"
             :class="{ 'selected': selectedImage === thumbnail.key }">
          <img :src="thumbnail.thumbnail" class="ppt-thumbnail" :alt="thumbnail.name">
          <span class="image-name">{{ thumbnail.name }}</span>
        </div>
      </div>
    </div>
    <div class="right-section">
      <div v-if="!generatingOutline" class="input-container">
        <input type="text" v-model="inputText" placeholder="输入文本..." class="custom-input">
      </div>
      <div v-if="!generatingOutline" class="button-container">
        <button class="generate-btn" @click="generateOutline">生成大纲</button>
      </div>
      <div v-if="generatingOutline" class="loading-container">
        <p>正在生成大纲，请稍候...</p>
      </div>
      <div v-if="outlineData !== '' " class="outline-container">
        <!-- Toggle between editable and read-only mode -->
        <template v-if="!editingOutline">
          <h2>生成的大纲内容：</h2>
          <pre>{{ outlineData }}</pre>
          <div class="button-container">
            <button class="edit-btn" @click="editOutline">编辑</button>
            <button class="generate-ppt-btn" @click="generatePPT">一键生成PPT</button>
          </div>
        </template>
        <template v-else>
          <textarea v-model="outlineData" class="editable-outline"></textarea>
          <div class="button-container">
            <button class="save-btn" @click="saveOutline">保存</button>
            <button class="cancel-btn" @click="cancelEdit">取消</button>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getToken } from "../../utils/auth.ts";
import ppt from '@/router/modules/ppt';
import { router, resetRouter } from "@/router";

const inputText = ref('');
const pptContentId = ref('');
const thumbnails = ref([]);
const selectedImage = ref<string>(''); // Ref to store selected image name
const generatingOutline = ref(false); // Ref to track if outline is being generated
const outlineData = ref('')
const editingOutline = ref(false); // Ref to track if outline is being edited

const fetchThumbnails = async () => {
  try {
    const { data } = getToken();
    const response = await axios.get('http://47.116.201.0:10010/ppt/getThemeList', {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    const responseData = response.data.data;
    thumbnails.value = responseData.map(theme => ({
      name: theme.name,
      thumbnail: theme.thumbnail,
      key: theme.key
    }));
    console.log(responseData)
  } catch (error) {
    console.error('Error fetching thumbnails:', error);
  }
};

onMounted(() => {
  fetchThumbnails();
});

const selectImage = (imageName: string) => {
  selectedImage.value = imageName;
};

const generateOutline = async () => {
  try {
    generatingOutline.value = true; // 设置正在生成大纲的状态为 true
    const { data } = getToken();
    const response = await axios.post('http://47.116.201.0:10010/ppt/createOutline', {
      theme: selectedImage.value || 'auto',
      query: inputText.value
    }, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    console.log(response.data)
    
    pptContentId.value = BigInt(response.data.data); // 更新 pptContent 状态，假设这是生成的大纲数据
    console.log(pptContentId.value)

    const getResponse = await axios.get(
      `http://47.116.201.0:10010/ppt/getOutline?id=${pptContentId.value}`,
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      }
    );
    outlineData.value = JSON.parse(getResponse.data.data);
    console.log(outlineData.value)
    outlineData.value = displayOrderedContent(outlineData.value);

  } catch (error) {
    console.error('Error generating outline:', error);
  } finally{
    generatingOutline.value = false;
  }
};

function displayOrderedContent(data) {
  let result = '';
  
  result += `标题: ${data.title}\n`;
  result += `副标题: ${data.subTitle}\n\n`;

  data.chapters.forEach((chapter, index) => {
    result += `${index + 1}. ${chapter.chapterTitle}\n`;
    chapter.chapterContents.forEach((content, subIndex) => {
      result += `   ${String.fromCharCode(97 + subIndex)}. ${content.chapterTitle}\n`;
    });
    result += '\n';
  });

  return result;
}

function convertToJSON(input) {
    let lines = input.split('\n');
    let result = {
        "chapters": [],
        "title": "",
        "subTitle": "",
        "end": ""
    };

    let currentChapter = {};
    let currentSection = {};

    lines.forEach(line => {
        if (line.startsWith('标题: ')) {
            result.title = line.substring(4).trim();
        } else if (line.startsWith('副标题: ')) {
            result.subTitle = line.substring(5).trim();
        } else if (line.match(/^\d+\. /)) {
            // New chapter
            if (Object.keys(currentChapter).length > 0) {
                result.chapters.push(currentChapter);
            }
            currentChapter = {
                "chapterTitle": line.substring(line.indexOf(' ') + 1).trim(),
                "chapterContents": []
            };
        } else if (line.match(/^\s+[a-z]\. /)) {
            // New section within current chapter
            if (Object.keys(currentSection).length > 0) {
                currentChapter.chapterContents.push(currentSection);
            }
            currentSection = {
                "chapterTitle": line.substring(line.indexOf('.') + 2).trim()
            };
        }
    });

    // Push the last section and chapter into result
    if (Object.keys(currentSection).length > 0) {
        currentChapter.chapterContents.push(currentSection);
    }
    if (Object.keys(currentChapter).length > 0) {
        result.chapters.push(currentChapter);
    }

    return JSON.stringify(result, null, 4);
}

const editOutline = () => {
  editingOutline.value = true;
};

const saveOutline = async() => {
  const { data } = getToken();
  editingOutline.value = false; // 结束编辑状态
  const idString = pptContentId.value.toString();
  try {
    const editedOutlineText = outlineData.value.trim();
    const parsedOutline = convertToJSON(editedOutlineText);
    console.log(parsedOutline);
    const jsonOutlineString = JSON.stringify(parsedOutline);
    const response = await axios.post('http://47.116.201.0:10010/ppt/updateOutline', {
      id: idString,
      outline: jsonOutlineString
    }, {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    console.log(response)
    // 更新 outlineData.value 以显示最新的大纲内容
    outlineData.value = displayOrderedContent(JSON.parse(parsedOutline));
  } catch (error) {
    console.error('Error parsing outline:', error);
    // 可以添加错误处理逻辑，例如提示用户或者回滚操作
  }
};

const cancelEdit = () => {
  // Cancel edit and revert back to read-only mode
  editingOutline.value = false;
};

const generatePPT = async() => {
  // 一键生成PPT的逻辑，根据需求实现
  console.log('Generate PPT');
  const idString = pptContentId.value.toString();
  try{
      const { data } = getToken();
      const Response = await axios.get(
      `http://47.116.201.0:10010/ppt/createPPT?id=${pptContentId.value}`,
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      }
    );
    console.log(Response)
    router.push({ name: 'progress', query: { pptContentId: idString } });
  }catch(error){
    console.error('Error parsing outline:', error)
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-around;
  align-items: flex-start;
  width: 100%;
  height: 90vh;
  padding: 20px;
  box-sizing: border-box;
}

.left-section {
  width: 30%;
  padding: 10px;
  height: 100%;
}

.right-section {
  width: 70%;
  padding: 10px;
  height: 100%;
}

.image-scroll-box {
  overflow-y: auto;
  height: 100%;
}

.image-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  padding: 10px;
}

.image-row:hover {
  background-color: #f0f0f0;
}

.ppt-thumbnail {
  width: 150px;
  height: auto;
  margin-right: 10px;
  border: 2px solid transparent;
}

.ppt-thumbnail:hover {
  border-color: #007BFF;
}

.selected {
  background-color: rgba(0, 123, 255, 0.7);
  border-color: #007BFF;
}

.input-container {
  margin-bottom: 10px;
}

.custom-input {
  width: 100%;
  height: 50px;
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 5px;
  transition: border-color 0.3s ease;
}

.custom-input:focus {
  outline: none;
  border-color: #007BFF;
}

.button-container {
  text-align: center;
}

.generate-btn, .edit-btn, .generate-ppt-btn, .save-btn, .cancel-btn {
  margin: 0 5px;
  padding: 12px 24px;
  font-size: 16px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.generate-btn:hover, .edit-btn:hover, .generate-ppt-btn:hover, .save-btn:hover, .cancel-btn:hover {
  background-color: #0056b3;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
}

.loading-container p {
  font-size: 18px;
  color: #333;
}

.outline-container {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f5f5f5;
  max-width: 800px;
  height: 500px;
  overflow-y: auto;
}

.outline-container h2 {
  margin-bottom: 10px;
}

.editable-outline {
  width: 100%;
  height: 100%;
  padding: 10px;
  font-size: 16px;
  border: 2px solid #ddd;
  border-radius: 5px;
  resize: vertical; /* Allow vertical resizing */
  transition: border-color 0.3s ease;
}

.editable-outline:focus {
  outline: none;
  border-color: #007BFF;
}
</style>
