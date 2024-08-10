<template>
  <div class="editor-header">
    <div class="left">
      <Popover trigger="click" placement="bottom-start" v-model:value="mainMenuVisible">
        <template #content>
          <FileInput accept="application/vnd.openxmlformats-officedocument.presentationml.presentation"  @change="files => {
            importPPTXFile(files)
            mainMenuVisible = false
          }">
            <PopoverMenuItem>导入 pptx 文</PopoverMenuItem>
          </FileInput>
          <PopoverMenuItem @click="setDialogForExport('pptx')">导出文件</PopoverMenuItem>
        </template>
        <div class="menu-item"><IconHamburgerButton class="icon" /></div>
      </Popover>

      <div class="title">
        <Input 
          class="title-input" 
          ref="titleInputRef"
          v-model:value="titleValue" 
          @blur="handleUpdateTitle()" 
          v-if="editingTitle" 
        ></Input>
        <div 
          class="title-text"
          @click="startEditTitle()"
          :title="title"
          v-else
        >{{ title }}</div>
      </div>
    </div>

    <div class="right">
      <div class="group-menu-item">
        <div class="menu-item" v-tooltip="'幻灯片放映'" @click="enterScreening()">
          <IconPpt class="icon" />
        </div>
        <Popover trigger="click" center>
          <template #content>
            <PopoverMenuItem @click="enterScreeningFromStart()">从头开始</PopoverMenuItem>
            <PopoverMenuItem @click="enterScreening()">从当前页开始</PopoverMenuItem>
          </template>
          <div class="arrow-btn"><IconDown class="arrow" /></div>
        </Popover>
      </div>
      <div class="menu-item" v-tooltip="'导出'" @click="setDialogForExport('pptx')">
        <IconDownload class="icon" />
      </div>
      <button class="menu-item"  @click="generateVideo()">
        {{ videoGenerate ? '正在生成' : '生成视频' }}
      </button>
    </div>

    <Drawer
      :width="320"
      v-model:visible="hotkeyDrawerVisible"
      placement="right"
    >
      <HotkeyDoc />
    </Drawer>

    <FullscreenSpin :loading="exporting" tip="正在导入..." />
  </div>
</template>

<script lang="ts" setup>
import { nextTick, ref} from 'vue'
import { storeToRefs } from 'pinia'
import { useMainStore, useSlidesStore } from '@/store'
import useScreening from '@/hooks/useScreening'
import useImport from '@/hooks/useImport'
import useSlideHandler from '@/hooks/useSlideHandler'
import type { DialogForExportTypes } from '@/types/export'

import HotkeyDoc from './HotkeyDoc.vue'
import FileInput from '@/components/FileInput.vue'
import FullscreenSpin from '@/components/FullscreenSpin.vue'
import Drawer from '@/components/Drawer.vue'
import Input from '@/components/Input.vue'
import Popover from '@/components/Popover.vue'
import PopoverMenuItem from '@/components/PopoverMenuItem.vue'
import axios from 'axios'

const mainStore = useMainStore()
const slidesStore = useSlidesStore()
const { title } = storeToRefs(slidesStore)
const { enterScreening, enterScreeningFromStart } = useScreening()
const { importSpecificFile, importPPTXFile, exporting } = useImport()
const { resetSlides } = useSlideHandler()

const mainMenuVisible = ref(false)
const hotkeyDrawerVisible = ref(false)
const editingTitle = ref(false)
const titleInputRef = ref<InstanceType<typeof Input>>()
const titleValue = ref('')
const audioText = ref<string[]>([]);
const videoGenerate = ref(false)

const startEditTitle = () => {
  titleValue.value = title.value
  editingTitle.value = true
  nextTick(() => titleInputRef.value?.focus())
}

const handleUpdateTitle = () => {
  slidesStore.setTitle(titleValue.value)
  editingTitle.value = false
}

const goLink = (url: string) => {
  window.open(url)
  mainMenuVisible.value = false
}

const setDialogForExport = (type: DialogForExportTypes) => {
  mainStore.setDialogForExport(type)
  mainMenuVisible.value = false
}

const generateVideo = async() => {
  videoGenerate.value = true
  audioText.value = []
  for (let i = 0; i < slidesStore.slides.length; i++) {
    const slide = slidesStore.slides[i];
    const remark = slide.remark;
     if(!remark){
      alert('请填写完所有ppt的语音内容');
      audioText.value = []
      videoGenerate.value = false
      return;
    }
    // 使用正则表达式匹配 <p> 标签中的文本内容
    var match = remark.match(/<p[^>]*>(.*?)<\/p>/);
    if (match) {
      audioText.value.push(match[1]);
    }
  }
  const ID = slidesStore.id
  
  const data = location.search.split("?token=")[1]
  try{
    const response = await axios.post(
      'http://111.229.20.120:10010/video/uploadTextList',
      {
       "id":ID,
       "textList":audioText.value
      },
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      }
    );
    console.log(response)
    }catch(error){
      console.error('error happen in upload text list',error)
  }
  const intervalTime = 3000;
  const intervalId = setInterval(async () => {
    try {
      const response = await axios.get(`http://111.229.20.120:10010/video/queryMp3Process?fileId=${slidesStore.id}`, {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      });
      console.log(response.data.data);

      if (response.data.data === 100) {
        clearInterval(intervalId); // 如果值为100，退出定时器循环
        try {
          const response1 = await axios.get(`http://111.229.20.120:10010/video/createVideo?fileId=${slidesStore.id}`, {
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            }
          });
          console.log(response1.data.status)
         
          if (response1.data.status === 200) {
            const download = `http://111.229.20.120:10010/video/mp4/${slidesStore.id}`
            window.open(download, '_blank');      
            videoGenerate.value = false
          }
        } catch (error) {
          console.error('Error create video:', error);
          videoGenerate.value = false
        }
        console.log('Process completed successfully.');
        videoGenerate.value = false
      }
    } catch (error) {
      console.error('Error fetching thumbnails:', error);
      clearInterval(intervalId); // 在出错时停止定时器循环
      videoGenerate.value = false
    }
  }, intervalTime);

};
</script>

<style lang="scss" scoped>
.editor-header {
  background-color: #fff;
  user-select: none;
  border-bottom: 1px solid $borderColor;
  display: flex;
  justify-content: space-between;
  padding: 0 5px;
}
.left, .right {
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu-item {
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
  padding: 0 10px;
  border-radius: $borderRadius;
  cursor: pointer;

  .icon {
    font-size: 18px;
    color: #666;
  }

  &:hover {
    background-color: #f1f1f1;
  }
}
.group-menu-item {
  height: 30px;
  display: flex;
  margin: 0 8px;
  padding: 0 2px;
  border-radius: $borderRadius;

  &:hover {
    background-color: #f1f1f1;
  }

  .menu-item {
    padding: 0 3px;
  }
  .arrow-btn {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
}
.title {
  height: 32px;
  margin-left: 2px;
  font-size: 13px;

  .title-input {
    width: 200px;
    height: 100%;
    padding-left: 0;
    padding-right: 0;
  }
  .title-text {
    min-width: 20px;
    max-width: 400px;
    line-height: 32px;
    padding: 0 6px;
    border-radius: $borderRadius;
    cursor: pointer;

    @include ellipsis-oneline();

    &:hover {
      background-color: #f1f1f1;
    }
  }
}
.github-link {
  display: inline-block;
  height: 30px;
}
</style>