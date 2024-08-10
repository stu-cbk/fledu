<script lang="ts" setup>
import { ref, reactive, computed } from "vue";
import { VxeTableBar } from "@/components/ReVxeTableBar";
import { mock } from "node:test";
import { getToken } from "../../../utils/auth.ts";
import axios from 'axios';

const vxeTableRef = ref();
const loading = ref(true);
const tableData = ref([]);

const getTableHeight = computed(() => {
  return (size: string) => {
    switch (size) {
      case "medium":
        return 531;
      case "small":
        return 482;
      case "mini":
        return 433;
    }
  };
});

const pagerConfig = reactive({
  total: 0,
  currentPage: 1,
  pageSize: 10,
  pageSizes: [5, 10, 15, 20]
});

const columns = [
  { type: "seq", field: "seq", title: "序号", width: 30 },
  { field: "name", title: "名称", sortable: true },
  { field: "role", title: "角色" },
  { field: "phone", title: "手机" },
  { field: "mail", title: "邮箱" },
];

async function onSearch() {
  loading.value = true;
  const mockList = [];
  try {
    const { data } = getToken();
    const response = await axios.get('http://47.116.201.0:10010/user/selectAllStudent', {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    const responsedata = response.data.data
    for (let i = 0; i < responsedata.length; i++) {
      mockList.push({
        id: responsedata[i].id,
        name: responsedata[i].userName,
        role: '学生',
        phone: responsedata[i].userPhone,
        mail: responsedata[i].userEmail,
      });
    };
    const response2 = await axios.get('http://47.116.201.0:10010/user/selectAllTeacher', {
      headers: {
        'token': data,
        'Content-Type': 'application/json'
      }
    });
    const responsedata2 = response2.data.data
    for (let i = 0; i < responsedata.length; i++) {
      mockList.push({
        id: responsedata2[i].id,
        name: responsedata2[i].userName,
        role: '教师',
        phone: responsedata2[i].userPhone,
        mail: responsedata2[i].userEmail,
      });
    };
  } catch (error) {
    console.error('Error getting userdata:', error);
  }
  console.log(mockList)
  tableData.value = mockList
  setTimeout(() => {
    loading.value = false;
  }, 500);
}
const handlePageChange = ({ currentPage, pageSize }) => {
  pagerConfig.currentPage = currentPage;
  pagerConfig.pageSize = pageSize;
  onSearch();
};

onSearch();
</script>

<template>
  <VxeTableBar
    :vxeTableRef="vxeTableRef"
    :columns="columns"
    title="分页表格"
    @refresh="onSearch"
  >
    <template v-slot="{ size, dynamicColumns }">
      <vxe-grid
        ref="vxeTableRef"
        v-loading="loading"
        show-overflow
        :height="getTableHeight(size)"
        :size="size"
        :column-config="{ resizable: true }"
        :columns="dynamicColumns"
        :pagerConfig="pagerConfig"
        :data="tableData"
        @page-change="handlePageChange"
      />
    </template>
  </VxeTableBar>
</template>
