<template>
  <div class="blockquote-primary" style="border-radius: 0.5%">
    <h3 style="color: black"></h3>
    <div>
      <div style="float: left">
        <n-button
          disabled="disabled"
          class="namebutton"
          type="default"
          size="lg"
          link
          style="margin-bottom: 0px"
          ><h5 style="font-size: 20px">작성자</h5>
        </n-button>
      </div>
      <div>
        <textarea
          id="textarea"
          class="form-control"
          round
          readonly
          v-model="article.userid"
          placeholder="작성자"
          style="cursor: default; max-height: 50px; height: 50px"
        ></textarea>
      </div>
    </div>
    <div>
      <div style="float: left">
        <n-button
          disabled="disabled"
          class="namebutton"
          type="default"
          size="lg"
          link
          style="margin-bottom: 0px"
          ><h5 style="font-size: 20px">제목</h5>
        </n-button>
      </div>
      <div>
        <textarea
          id="textarea"
          class="form-control"
          round
          v-model="article.subject"
          placeholder="제목을 입력하세요"
          style="max-height: 50px; height: 50px"
        ></textarea>
      </div>
    </div>
    <div>
      <div style="float: left">
        <n-button
          disabled="disabled"
          class="namebutton"
          type="default"
          size="lg"
          link
          style="margin-bottom: 0px"
          ><h5 style="font-size: 20px">내용</h5>
        </n-button>
      </div>
      <textarea
        id="textarea"
        class="form-control"
        round
        v-model="article.content"
        placeholder="내용을 입력하세요"
      ></textarea>
    </div>

    <n-button
      type="default"
      class="btn-base"
      round
      v-if="this.type === 'register'"
      @click="onSubmit"
      style="font-size: 15px"
      >글작성</n-button
    >
    <n-button
      type="default"
      class="btn-base"
      round
      v-else
      @click="onSubmit"
      style="font-size: 15px"
      >글수정</n-button
    >
    <n-button
      class="btn-base"
      type="default"
      round
      @click="onReset"
      style="font-size: 15px; margin-left: 30px"
      >초기화</n-button
    >
  </div>
</template>

<script>
import { Card, Button } from "@/components";
import { mapState } from "vuex";
const memberStore = "memberStore";
import { Notification } from "element-ui";
import http from "@/api/http";

export default {
  name: "BoardInputItem",
  components: {
    Card,
    [Button.name]: Button,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
      isUserid: false,
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
      http.get(`/board/${this.$route.params.articleno}`).then(({ data }) => {
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.article = data;
      });
      this.isUserid = true;
    }
    this.article.userid = this.userInfo.userid;
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) {
        Notification.error({
          title: "Error",
          message: "에러가 발생했습니다",
          type: "error",
        });
      } else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
    },
    registArticle() {
      http
        .post(`/board`, {
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          if (data === "success") {
            Notification.success({
              title: "Success",
              message: "등록되었습니다",
              type: "success",
            });
          } else {
            Notification.error({
              title: "Error",
              message: "에러가 발생했습니다",
              type: "error",
            });
          }

          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/${this.article.articleno}`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          if (data === "success") {
            Notification.success({
              title: "Success",
              message: "수정되었습니다",
              type: "success",
            });
          } else {
            Notification.error({
              title: "Error",
              message: "에러가 발생했습니다",
              type: "error",
            });
          }
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "boardList", query: { no: "10" } });
        });
    },
    moveList() {
      this.$router.push({ name: "boardList", query: { no: "10" } });
    },
  },
};
</script>

<style>
.namebutton {
  margin-top: 8px;
}
textarea.form-control {
  max-height: 400px;
  height: 400px;
  margin-bottom: 10px;
  font-size: 17px;
}
#textarea {
  font-size: 15px;
}

.btn.btn-link {
  color: black;
  font-weight: 600;
  font-size: 17px;
  margin-bottom: 0;
}
.btn:disabled {
  opacity: 1;
}

.form-control[readonly] {
  background-color: white;
}
</style>
