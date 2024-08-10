<template>
  <div class="full-screen-image">
    <img src="@/assets/welcome.jpg" alt="Welcome Image">
  </div>
</template>

<script>
// 这里可以进行必要的JavaScript逻辑或者数据处理
export default {
  name: "FullScreenImage",
};
</script>

<style scoped>
.full-screen-image {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

img {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover; /* 保持图片比例并填充整个容器 */
}
</style>

