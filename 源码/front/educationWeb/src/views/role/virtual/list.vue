<script lang="ts" setup>
import { ref } from "vue";
import { VxeTableBar } from "@/components/ReVxeTableBar";

const vxeTableRef = ref();
const loading = ref(true);
const tableData = ref([]);

const columns = [
  { type: "seq", field: "seq", title: "序号", width: 30 },
  { field: "name", title: "名称", sortable: true },
  { field: "role", title: "角色" },
  { field: "phone", title: "手机" },
  { field: "mail", title: "邮箱" },
  { field: "password", title: "密码" },
];

async function onSearch() {
  loading.value = true;
  const mockList = [];
  mockList.push({
    id: 1,
    name: "student1",
    role: "学生",
    phone: "13736578295",
    mail: "137@outlook.com"
  });
  mockList.push({
    id: 1,
    name: "xiexinou",
    role: "教师",
    phone: "1373657888",
    mail: "122@outlook.com"
  });
  mockList.push({
    id: 1,
    name: "admin",
    role: "管理员",
    phone: "1373657999",
    mail: "333@outlook.com"
  });
  setTimeout(() => {
    loading.value = false;
  }, 500);
}

onSearch();
</script>

<template>
  <VxeTableBar
    :vxeTableRef="vxeTableRef"
    :columns="columns"
    title="虚拟表格"
    @refresh="onSearch"
  >
    <template v-slot="{ size, dynamicColumns }">
      <vxe-grid
        ref="vxeTableRef"
        v-loading="loading"
        show-overflow
        height="500"
        :size="size"
        :row-config="{ isHover: true }"
        :scroll-y="{ enabled: true }"
        :columns="dynamicColumns"
        :data="tableData"
      />
    </template>
  </VxeTableBar>
</template>
