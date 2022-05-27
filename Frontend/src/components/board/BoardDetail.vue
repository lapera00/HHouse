<template>
  <div class="content-center">
    <div>
      <div>
        <div style="text-align: left">
          <h4 class="title" style="font-size: 30px; margin-bottom: 0px">
            {{ article.subject }}
          </h4>

          <span style="float: left"
            ><p style="padding-top: 10px">
              {{ article.username }} &nbsp &nbsp
            </p></span
          >
          <span
            ><p class="description" style="height: 50px; padding-top: 10px">
              {{ article.regtime
              }}<n-button
                class="btn-base"
                type="default"
                round
                size="sm"
                @click="listArticle"
                style="
                  font-size: 15px;
                  float: right;
                  margin-top: 0px;
                  padding-top: 8px;
                  padding-bottom: 8px;
                  padding-left: 15px;
                  padding-right: 15px;
                "
                >글목록</n-button
              >
            </p>
          </span>

          <hr />
        </div>
        <textarea
          id="textara"
          class="form-control"
          round
          readonly
          style="
            cursor: default;
            font-size: 20px;
            color: black;
            border-style: none;
            max-height: 400px;
            height: 400px;
          "
          v-model="article.content"
          placeholder="내용을 입력하세요"
        ></textarea>
        <div align="right" style="">
          <a
            href=""
            class="btn btn-default btn-icon btn-round btn-base"
            onclick="return false;"
            ><i class="fab fa-twitter"></i
          ></a>
          <a
            href=""
            class="btn btn-default btn-icon btn-round btn-base"
            onclick="return false;"
            ><i class="fab fa-linkedin"></i
          ></a>
        </div>

        <div align="right" v-if="userInfo">
          <n-button
            class="btn-base"
            v-if="userInfo.authority == 5"
            type="default"
            round
            size="sm"
            @click="moveModifyArticle"
            style="font-size: 15px"
            >글수정</n-button
          >
          <el-popconfirm
            confirm-button-text="확인"
            cancel-button-text="취소"
            icon="el-icon-info"
            icon-color="red"
            title="정말 삭제 할까요?"
            @confirm="deleteArticle"
          >
            <n-button
              class="btn-base"
              v-if="userInfo.authority == 5"
              type="default"
              round
              size="sm"
              style="font-size: 15px"
              slot="reference"
              >글삭제</n-button
            >
          </el-popconfirm>
        </div>
        <!-- <div class="pull-right">
          <n-button
            type="danger"
            simple
            size="sm"
            @click="listArticle"
            style="font-size: 15px"
            >글목록</n-button
          >
        </div> -->
      </div>
    </div>
    <div style="height: 50px"></div>
    <table style="font-size: 13pt; width: 1100px">
      <thead align="left">
        <tr>
          <!-- <th style="width: 50px"></th> -->
          <th style="font-size: 20px">댓글</th>
          <!-- <th style="width: 100px">fds</th> -->
          <!-- <th></th> -->
        </tr>
        <hr />
      </thead>
      <tbody align="left" style="width: 1100px">
        <board-comment-list
          v-for="comment in comments"
          :key="comment.commentno"
          v-bind="comment"
        />
      </tbody>
    </table>
    <div style="height: 10px"></div>
    <div v-if="idch()">
      <div style="float: left; width: 1000px">
        <fg-input
          v-model="comment.content"
          class="no-border input-lg"
          addon-left-icon="now-ui-icons ui-1_simple-delete"
          placeholder="댓글을 입력하세요"
        >
        </fg-input>
      </div>
      <div style="float: left">
        <n-button
          class="buttonr btn-base"
          type="default"
          round
          @click="writecomment"
          style="height: 40px; width: 80px; font-size: 15px"
          >등록</n-button
        >
      </div>
    </div>
    <div style="height: 200px"></div>
  </div>
</template>

<script>
// import moment from "moment";
import { Card, Button, FormGroupInput } from "@/components";
import http from "@/api/http";
import BoardCommentList from "@/components/board/item/BoardCommentList.vue";
import { mapState } from "vuex";
import { Popconfirm, Notification } from "element-ui";
const memberStore = "memberStore";

export default {
  name: "BoardDetail",
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    BoardCommentList,
    ElPopconfirm: Popconfirm,
  },
  data() {
    return {
      article: {},
      comments: [],
      comment: {
        articleno: "",
        content: "",
        userid: "",
        regtime: "",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
      this.article = data;
    });
    http.get(`/comment/${this.$route.params.articleno}`).then(({ data }) => {
      this.comments = data;
    });
  },

  methods: {
    listArticle() {
      this.$router.push({ name: "boardList" });
    },
    moveModifyArticle() {
      if (this.userInfo != null) {
        if (
          this.article.userid === this.userInfo.userid ||
          this.userInfo.authority === 5
        ) {
          this.$router.replace({
            name: "boardModify",
            params: { articleno: this.article.articleno },
          });
        } else {
          Notification.error({
            title: "Error",
            message: "작성자가 아닙니다",
            type: "error",
          });
        }
      } else {
        Notification.error({
          title: "Error",
          message: "로그인이 필요한 기능입니다",
          type: "error",
        });
      }
    },
    deleteArticle() {
      if (this.userInfo != null) {
        if (
          this.article.userid === this.userInfo.userid ||
          this.userInfo.authority === 5
        ) {
          this.$router.replace({
            name: "boardDelete",
            params: { articleno: this.article.articleno },
          });
          Notification.Success({
            title: "Success",
            message: "삭제되었습니다",
            type: "Success",
          });
        } else {
          Notification.error({
            title: "Error",
            message: "작성자가 아닙니다",
            type: "error",
          });
        }
      } else {
        Notification.error({
          title: "Error",
          message: "로그인이 필요한 기능입니다.",
          type: "error",
        });
      }
    },

    writecomment() {
      console.log(this.$route.params.articleno);

      http
        .post(`/comment`, {
          userid: this.userInfo.userid,
          content: this.comment.content,
          articleno: this.$route.params.articleno,
        })
        .then(({ data }) => {
          if (data === "success") {
            Notification.success({
              title: "Success",
              message: "댓글이 작성되었습니다",
              type: "success",
            });
          } else {
            Notification.error({
              title: "Error",
              message: "문제가 발생했습니다",
              type: "error",
            });
          }
          http
            .get(`/comment/${this.$route.params.articleno}`)
            .then(({ data }) => {
              this.comments = data;
            });
          this.comment.content = "";
        });
    },
    idch() {
      if (this.userInfo == null) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>

<style scoped>
.buttonr {
  margin-top: 2px;
  margin-left: 20px;
}
</style>
