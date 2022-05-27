<template>
  <div class="section section-signup" style="padding-top: 80px">
    <div class="container">
      <div>
        <card class="card-login" header-classes="text-center" plain>
          <template slot="header">
            <h3 class="card-title title-up">회원정보</h3>
          </template>
          <template>
            <fg-input
              v-model="user.userid"
              class="no-border input-lg"
              placeholder="ID"
              addon-left-icon="now-ui-icons users_single-02"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="user.userpwd"
              class="no-border input-lg"
              placeholder="PASSWORD"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="user.username"
              class="no-border input-lg"
              placeholder="NAME"
              addon-left-icon="now-ui-icons text_caps-small"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="user.email"
              class="no-border input-lg"
              placeholder="Email"
              addon-left-icon="now-ui-icons ui-1_email-85"
              style="height: 70px"
            >
            </fg-input>
          </template>
          <div class="card-footer text-center">
            <n-button
              type="primary"
              round
              size="lg"
              @click="update"
              style="width: 320px; font-size: 14px"
              >회원정보 수정</n-button
            >
          </div>
        </card>
      </div>
      <div class="col text-center">
        <el-popconfirm
          confirm-button-text="확인"
          cancel-button-text="취소"
          icon="el-icon-info"
          icon-color="red"
          title="정말 탈퇴 할까요?"
          @confirm="drop"
        >
          <n-button
            style="width: 320px; font-size: 14px"
            class="btn btn-simple btn-round btn-white btn-lg"
            slot="reference"
          >
            회원탈퇴
          </n-button>
        </el-popconfirm>
      </div>
    </div>
  </div>
</template>

<script>
import { Card, FormGroupInput, Button } from "@/components";
import { mapState, mapActions } from "vuex";
import { Notification, Popconfirm } from "element-ui";
const memberStore = "memberStore";

export default {
  name: "MemberRegister",
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    ElPopconfirm: Popconfirm,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  data() {
    return {
      user: {
        userid: "",
        userpwd: "",
        username: "",
        email: "",
      },
    };
  },
  methods: {
    ...mapActions(memberStore, [
      "userUpdate",
      "getUserInfo",
      "userDelete",
      "userLogout",
    ]),
    async update() {
      await this.userUpdate(this.user);
      let token = sessionStorage.getItem("access-token"); //세션 스토리지에 토큰 가져오기
      if (this.isLogin) {
        let options = {
          title: "Success",
          message: "수정 되었습니다.",
          type: "success",
        };
        Notification.success(options);
        //화면에 ~~~님 반갑습니다 표시하기 위해 유저정보 가져오기
        await this.getUserInfo(token); //토큰 들고가서 사용자 정보 가져오기
        this.$router.push({ name: "home" });
      } else {
        this.$router.push({ name: "signin" });
      }
    },
    async drop() {
      if (confirm("회원 탈퇴하시겠습니까?")) {
        await this.userDelete(this.user);

        this.userLogout(this.userInfo.userid);
        sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
        sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
        if (this.$route.path != "/") this.$router.push({ name: "home" });
      }
    },

    onReset() {
      this.user.userpwd = "";
      this.user.username = "";
      this.user.email = "";
    },
  },
  created() {
    this.user.userid = this.userInfo.userid;
    this.user.username = this.userInfo.username;
    this.user.userpwd = this.userInfo.userpwd;
    this.user.email = this.userInfo.email;
  },
};
</script>

<style>
.section {
  background: none;
}
</style>
