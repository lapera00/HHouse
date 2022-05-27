<template>
  <card type="login" plain style="padding-top: 40px">
    <div slot="header" class="logo-container">
      <!-- <img v-lazy="'img/now-logo.png'" alt="" />/ -->
    </div>
    <h3 class="card-title title-up" style="margin-bottom: 30px">로그인</h3>
    <fg-input
      v-model="user.userid"
      class="no-border input-lg"
      addon-left-icon="now-ui-icons users_single-02"
      placeholder="ID "
    >
    </fg-input>

    <fg-input
      v-model="user.userpwd"
      class="no-border input-lg"
      addon-left-icon="now-ui-icons ui-1_lock-circle-open"
      placeholder="PASSWORD"
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
          style="width: 300px; height: 48px; font-size: 14px"
          @click="confirm()"
          >로그인
        </n-button>
      </div>
      <div class="about">
        <img
          style="cursor: pointer"
          src="../../assets/kakao_login_medium_wide.png"
          @click="kakaologin()"
        />
        <br />
      </div>
      <div style="height: 10px"></div>
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
      <div class="pull-right">
        <h6>
          <a
            href=""
            class="link footer-link"
            @click="movefindpw()"
            onclick="return false;"
            style="font-size: 13px"
            >비밀번호 찾기</a
          >
        </h6>
      </div>
    </template>
  </card>
</template>

<script>
import http from "@/api/http";
import { mapState, mapActions } from "vuex";
import { Card, Button, FormGroupInput } from "@/components";
const memberStore = "memberStore";

export default {
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  data() {
    return {
      codes: null,
      user: {
        userid: null,
        userpwd: null,
        email: null,
        username: null,
      },
    };
  },
  created() {
    this.codes = this.$route.query.code;

    if (this.codes != null) {
      this.getToken();
    }
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]), //로그인 여부 확인하기
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user); //acations의 로그인 메소드 실행
      let token = sessionStorage.getItem("access-token"); //세션 스토리지에 토큰 가져오기
      if (this.isLogin) {
        //화면에 ~~~님 반갑습니다 표시하기 위해 유저정보 가져오기
        await this.getUserInfo(token); //토큰 들고가서 사용자 정보 가져오기
        this.$router.push({ name: "home" });
      }
    },
    moveup() {
      this.$router.push({ name: "signup" });
    },
    movefindpw() {
      this.$router.push({ name: "findpw" });
    },
    kakaologin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=e81c68d2e77e76b219b8c083a25b9a31&redirect_uri=http://localhost:8080/user/signin&response_type=code`
      );
    },
    getToken() {
      http.get(`/user/klogin?authorize_code=${this.codes}`).then((res) => {
        this.user.userid = res.data.userid;
        this.user.userpwd = res.data.userpwd;
        this.user.email = res.data.email;
        this.user.username = res.data.username;

        this.confirm();
      });
    },
  },
};

// import http from "@/api/http";
// export default {
//   name: "MemberLogin",
//   data() {
//     return {
//       isLoginError: false,
//       user: {
//         userid: "",
//         userpwd: "",
//       },
//     };
//   },
//   methods: {
//     confirm() {
//       http
//         .get(`/user/login/${this.user.userid}/${this.user.userpwd}`)
//         .then(({ data }) => {
//           let msg = "문제가 발생했습니다.";
//           if (data === "success") {
//             const user1 = [this.user.userid];
//             sessionStorage.setItem("loginUser", user1);

//             msg = "로그인이 완료되었습니다.";
//           }
//           alert(msg);
//           // 현재 route를 /list로 변경.
//           this.$router.push("/");
//           this.$router.go("/");
//         });
//     },
//     movePage() {
//       this.$router.push({ name: "signUp" });
//     },
//   },
// };
</script>

<style></style>
