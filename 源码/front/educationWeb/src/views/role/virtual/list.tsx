import List from "./list.vue";
import TreeList from "./treeList.vue";
import PageList from "./pageList.vue";

const rendContent = (val: string) =>
  `代码位置：src/views/table/virtual/${val}.vue`;

export const list = [
  {
    key: "pageList",
    content: rendContent("pageList"),
    title: "用户表格",
    component: PageList
  }
];
