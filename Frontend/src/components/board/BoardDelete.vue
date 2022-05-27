<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col><b-alert show variant="danger">삭제처리중...</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/api/http";
import { Notification } from "element-ui";
export default {
  name: "BoardDelete",
  created() {
    http.delete(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      if (data === "success") {
        Notification.success({
          title: "Success",
          message: "삭제 되었습니다.",
          type: "success",
        });
      } else {
        Notification.error({
          title: "Error",
          message: "삭제 처리시 문제가 발생했습니다.",
          type: "error",
        });
      }

      // 현재 route를 /list로 변경.
      this.$router.push({ name: "boardList" });
    });
  },
};
</script>

<style></style>
