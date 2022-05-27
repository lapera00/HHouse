<template>
  <tr style="width: 1100px">
    <td style="width: 1100px">
      <tr style="width: 1100px; font-size: 15px">
        {{
          username
        }}
        &nbsp&nbsp
        {{
          regtime | dateFormat
        }}
      </tr>
      <tr style="width: 1100px">
        <div style="margin-top: 10px; margin-bottom: 5px"></div>
        <div style="width: 1100px" class="regtimediv">
          &nbsp&nbsp{{ content }}
          <n-button
            type="default"
            v-if="idch()"
            round
            size="sm"
            @click="commentdelete"
            style="height: 26px margin:0px 0px; font-size:15px"
            class="pull-right btn-base"
          >
            삭제
          </n-button>
        </div>
      </tr>

      <hr />
    </td>
  </tr>
</template>

<script>
import { Button } from "@/components";
import moment from "moment";
import http from "@/api/http";
import { mapState } from "vuex";
import { Notification } from "element-ui";
const memberStore = "memberStore";

export default {
  name: "BoardListItem",
  components: {
    [Button.name]: Button,
  },
  props: {
    articleno: Number,
    commentno: Number,
    userid: String,
    content: String,
    regtime: String,
    username: String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD HH:mm:ss");
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    commentdelete() {
      if (confirm("해당 댓글을 삭제하시겠습니까??")) {
        http.delete(`/comment/${this.commentno}`).then(({ data }) => {
          let msg = "문제가 발생했습니다.";
          if (data === "success") {
            msg = "댓글 삭제가 완료되었습니다.";
          }
          // alert(msg);
        });
        this.$router.go(`/board/detail/${this.articleno}`);
      } else {
        Notification.error({
          title: "Error",
          message: "에러가 발생했습니다",
          type: "error",
        });
      }
    },
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
  },
};
</script>

<style>
.pull-right {
  margin: 0px 1px;
}
.regtimediv {
  margin: 5px 1px;
}
.form-control[readonly] {
  background-color: white;
}
</style>
