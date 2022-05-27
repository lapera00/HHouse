<template>
  <div>
    <h3>게시글 수정</h3>

    <board-input-item type="modify" />
  </div>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem.vue";

export default {
  name: "BoardModify",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
