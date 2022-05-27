<template>
  <card type="login" plain>
    <div slot="header" class="logo-container">
      <img v-lazy="'img/now-logo.png'" alt="" />
    </div>
    <h3 class="card-title title-up" style="margin-bottom: 30px">
      비밀번호 찾기
    </h3>
    <fg-input
      v-model="user.userid"
      class="no-border input-lg"
      addon-left-icon="now-ui-icons users_single-02"
      placeholder="아이디를 입력하세요"
    >
    </fg-input>

    <fg-input
      v-model="user.email"
      class="no-border input-lg"
      addon-left-icon="now-ui-icons ui-1_lock-circle-open"
      placeholder="이메일을 입력하세요"
    >
    </fg-input>

    <template slot="raw-content">
      <div class="card-footer text-center">
        <!-- <a class="btn btn-primary btn-round btn-lg btn-block" @click="confirm"
          >로그인</a
        > -->
        <n-button
          type="primary"
          size="lg"
          round
          style="width: 320px"
          @click="findpw()"
          >비밀번호 찾기
        </n-button>
      </div>
      <div class="pull-left">
        <h6>
          <a
            href=""
            class="link footer-link"
            @click="moveup()"
            onclick="return false;"
            style="font-size: 13px"
            >회원가입</a
          >
        </h6>
      </div>
    </template>
  </card>
</template>

<script>
import { Card, Button, FormGroupInput } from "@/components";
import { Notification } from "element-ui";
import http from "@/api/http";
export default {
  name: "MemberLogin",
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      isLoginError: false,
      user: {
        userid: "",
        email: "",
      },
    };
  },
  methods: {
    findpw() {
      http
        .get(`/user/findpw/${this.user.userid}/${this.user.email}`)
        .then(({ data }) => {
          let msg = "조건에 맞는 사용자가 없습니다.";
          if (data) {
            msg = `비밀번호는 ${data} 입니다.`;
            Notification.info({
              title: "info",
              message: msg,
              type: "info",
            });
            this.$router.push({ name: "signin" });
          }
        })
        .catch(
          Notification.info({
            title: "info",
            message: "일치하는 정보가 없습니다.",
            type: "info",
          })
        );
    },
    moveup() {
      this.$router.push({ name: "signup" });
    },
  },
};
</script>

<style></style>
