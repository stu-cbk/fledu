<script setup lang="ts">
import { getCardList } from "@/api/list";
import { message } from "@/utils/message";
import { ElMessageBox } from "element-plus";
import { ref, onMounted, nextTick } from "vue";
import ListCard from "./components/ListCard.vue";
import { useRenderIcon } from "@/components/ReIcon/src/hooks";
import AddFill from "@iconify-icons/ri/add-circle-line";
import { getToken } from "../../utils/auth.ts";
import axios from 'axios';

defineOptions({
  name: "CardList"
});


const INITIAL_DATA = {
  name: "",
  img: "",
};

const pagination = ref({ current: 1, pageSize: 12, total: 0 });

const productList = ref([]);
const dataLoading = ref(true);

const getCardListData = async () => {
  try{
      const { data } = getToken();
      const Response = await axios.get(
      'http://47.116.201.0:10010/ppt/getHistoryData',
      {
        headers: {
          'token': data,
          'Content-Type': 'application/json'
        }
      }
    );
    const dataList = Response.data.data
    const requests = dataList.map(async id => {
      try {
        const infoResponse = await axios.get(
          `http://47.116.201.0:10010/ppt/getInfo?id=${id}`,
          {
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            }
          }
        );
        const response = await axios.get(
          `http://47.116.201.0:10010/ppt/getPPT?id=${id}`,
          {
            headers: {
              'token': data,
              'Content-Type': 'application/json'
            },
          }
        );
        const download = response.data.data
        if(infoResponse.data.data.name && infoResponse.data.data.coverImgSrc && download){
          productList.value.push({ name:infoResponse.data.data.name, img: infoResponse.data.data.coverImgSrc, url:download});
        }
      } catch (error) {
        console.error(`Error fetching info for id ${id}:`, error);
      }
    });
     // 等待所有请求完成
    await Promise.all(requests);
    pagination.value.total = productList.value.length
    console.log(productList)
    console.log(pagination.value)
    setTimeout(() => {
      dataLoading.value = false;
    }, 500);
  }catch(error){
    console.error('Error parsing outline:', error)
  }
  
};

onMounted(() => {
  getCardListData();
});

const formDialogVisible = ref(false);
const formData = ref({ ...INITIAL_DATA });
const searchValue = ref("");

const onPageSizeChange = (size: number) => {
  pagination.value.pageSize = size;
  pagination.value.current = 1;
};
const onCurrentChange = (current: number) => {
  pagination.value.current = current;
};

const handleManageProduct = product => {
  formDialogVisible.value = true;
  nextTick(() => {
    formData.value = { ...product, status: product?.isSetup ? "1" : "0" };
  });
};
</script>

<template>
  <div>
    <div class="w-full flex justify-between mb-4">
    </div>
    <div
      v-loading="dataLoading"
      element-loading-svg-view-box="-10, -10, 50, 50"
    >
      <template v-if="pagination.total > 0">
        <el-row :gutter="16">
          <el-col
            v-for="(product, index) in productList
              .slice(
                pagination.pageSize * (pagination.current - 1),
                pagination.pageSize * pagination.current
              )
              .filter(v =>
                v.name.toLowerCase().includes(searchValue.toLowerCase())
              )"
            :key="index"
            :xs="24"
            :sm="12"
            :md="8"
            :lg="6"
            :xl="4"
          >
            <ListCard
              :product="product"
              @delete-item="handleDeleteItem"
              @manage-product="handleManageProduct"
            />
          </el-col>
        </el-row>
        <el-pagination
          v-model:currentPage="pagination.current"
          class="float-right"
          :page-size="pagination.pageSize"
          :total="pagination.total"
          :page-sizes="[12, 24, 36]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="onPageSizeChange"
          @current-change="onCurrentChange"
        />
      </template>
    </div>
  </div>
</template>
