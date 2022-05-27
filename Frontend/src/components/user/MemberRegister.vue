<template>
  <div class="section section-signup" style="padding-top: 80px">
    <div class="container">
      <div>
        <card class="card-login" header-classes="text-center" plain>
          <template slot="header">
            <h3 class="card-title title-up">회원가입</h3>
            <!-- <div class="social-line">
              <a
                href="#pablo"
                class="btn btn-neutral btn-facebook btn-icon btn-round"
              >
                <i class="fab fa-facebook-square"></i>
              </a>
              <a
                href="#pablo"
                class="btn btn-neutral btn-twitter btn-icon btn-lg btn-round"
              >
                <i class="fab fa-twitter"></i>
              </a>
              <a
                href="#pablo"
                class="btn btn-neutral btn-google btn-icon btn-round"
              >
                <i class="fab fa-google-plus"></i>
              </a>
            </div> -->
          </template>
          <template>
            <fg-input
              v-model="user.userid"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons users_single-02"
              placeholder="ID"
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
              size="lg"
              @click="check"
              style="width: 300px; height: 48px; font-size: 14px"
              >회원가입</n-button
            >
          </div>
          <div class="about">
            <img
              style="cursor: pointer"
              src="../../assets/kakao_start_medium_wide.png"
              @click="kakaologin()"
            />
            <br />
          </div>
        </card>
      </div>
      <div class="col text-center">
        <router-link
          :to="{ name: 'signin' }"
          class="btn btn-simple btn-round btn-white btn-lg"
          style="width: 300px; height: 48px; font-size: 14px"
        >
          로그인 페이지로 이동
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { Card, FormGroupInput, Button } from "@/components";
import http from "@/api/http";
export default {
  name: "MemberRegister",
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
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
    regist() {
      http
        .post(`/user/regist`, {
          userid: this.user.userid,
          userpwd: this.user.userpwd,
          username: this.user.username,
          email: this.user.email,
        })
        .then(({ data }) => {
          let msg = "문제가 발생했습니다.";
          if (data === "success") {
            msg = "정상적으로 회원가입이 완료되었습니다.";
          } else {
            msg = "이미 사용한 아이디입니다.";
          }
          swal(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "signin" });
        });
    },
    onReset() {
      this.user.userid = "";
      this.user.userpwd = "";
      this.user.username = "";
      this.user.email = "";
    },
    check() {
      if (
        this.user.userid == "" ||
        this.user.userpwd == "" ||
        this.user.username == "" ||
        this.user.email == ""
      ) {
        swal("모든 정보를 입력해주세요");
      } else {
        this.idcheck();
      }
    },
    idcheck() {
      http.get(`/user/idch/${this.user.userid}`).then(({ data }) => {
        let msg = "문제가 발생했습니다.";
        if (data === "success") {
          msg = "사용가능한 아이디";
          this.regist();
        } else {
          msg = "이미 사용한 아이디입니다.";
          swal(msg);
        }
      });
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
</script>

<style>
.section {
  background: none;
}
</style>
