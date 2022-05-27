<template>
  <tr>
    <!-- <td>{{ articleno }}</td> -->
    <td>{{ username }}</td>
    <th class="text-left">
      {{ content }}
    </th>
    <td>
      {{ regtime | dateFormat }}
      <n-button
        type="defult"
        v-if="idch()"
        simple
        round
        size="sm"
        @click="deletereview"
        style="height: 26px margin:0px 0px; font-size:15px;"
        class="pull-right"
      >
        삭제
      </n-button>
    </td>
  </tr>
</template>

<script>
import moment from "moment";
import http from "@/api/http";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "ReviewListItem",
  props: {
    // index: Number,
    aptCode: String,
    commentno: String,
    content: String,
    username: String,
    userid: String,
    regtime: String,
    star: String,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
  created() {},
  methods: {
    idch() {
      if (this.userInfo != null) {
        if (
          this.userid === this.userInfo.userid ||
          this.userInfo.authority === 5
        ) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    },
    deletereview() {
      if (confirm("해당 리뷰를 삭제하시겠습니까??")) {
        http.delete(`/housereview/${this.commentno}`).then(({ data }) => {
          let msg = "문제가 발생했습니다.";
          if (data === "success") {
            msg = "댓글 삭제가 완료되었습니다.";
          }
          console.log(msg);
          this.$emit("viewreview");
        });
      }
    },
  },
};
</script>

<style></style>
