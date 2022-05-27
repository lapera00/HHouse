<template>
  <div>
    <h3>게시글 작성</h3>

    <board-input-item type="register" />
  </div>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem.vue";

export default {
  name: "BoardWrite",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
