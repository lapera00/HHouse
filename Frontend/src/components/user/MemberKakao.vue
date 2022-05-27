<template>
  <div class="section section-signup" style="padding-top: 80px">
    <div class="container">
      <div>
        <card class="card-login" header-classes="text-center" plain>
          <template slot="header">
            <h3 class="card-title title-up">카카오 로그인</h3>
          </template>
          <template>
            <fg-input
              v-model="form.userid"
              class="no-border input-lg"
              addon-left-icon="now-ui-icons users_single-02"
              placeholder="ID"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="form.username"
              class="no-border input-lg"
              placeholder="PASSWORD"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="form.userpwd"
              class="no-border input-lg"
              placeholder="NAME"
              addon-left-icon="now-ui-icons text_caps-small"
              style="height: 70px"
            >
            </fg-input>

            <fg-input
              v-model="form.email"
              class="no-border input-lg"
              placeholder="Email"
              addon-left-icon="now-ui-icons ui-1_email-85"
              style="height: 70px"
            >
            </fg-input>
          </template>
          <div class="card-footer text-center">
            <n-button
              type="warning"
              size="lg"
              @click="onSubmit()"
              style="width: 300px; height: 48px; font-size: 14px"
              >카카오 회원가입</n-button
            >
          </div>
        </card>
      </div>
      <div class="col text-center">
        <router-link
          :to="{ name: 'signin' }"
          class="btn btn-simple btn-round btn-white btn-lg"
          style="width: 320px; font-size: 14px"
        >
          로그인 페이지로 이동
        </router-link>
      </div>
    </div>
  </div>
  <!-- <div class="klogin">
    <div class="container d-flex justify-content-center align-items-center">
      <div class="card">
        <div class="row">
          <div class="col-md-6">
            <n-form
              class="form"
              @submit="onSubmit"
              @reset="onReset"
              v-if="show"
            >
              <h2>회원가입</h2>

              <div class="inputbox mt-3">
                <span>이름</span>
                <input
                  type="text"
                  placeholder="실명을 입력해주세요"
                  class="form-control"
                  v-model="form.uname"
                  required
                />
              </div>
              <div class="inputbox mt-3">
                <span>이메일</span>
                <input
                  type="text"
                  v-text="form.email"
                  v-model="form.email"
                  class="form-control"
                  required
                  disabled
                />
              </div>
              <div class="inputbox mt-3">
                <span>핸드폰 번호</span>
                <input
                  type="text"
                  placeholder="010-0000-0000"
                  name=""
                  class="form-control"
                />
              </div>
              <div class="d-flex justify-content-between align-items-center">
                <div class="text-right">
                  <n-button
                    type="submit"
                    class="btn btn-success register btn-block"
                  >
                    Register
                  </n-button>
                </div>
                <div class="text-right">
                  <n-button
                    type="reset"
                    class="btn btn-success reset btn-block"
                  >
                    Reset
                  </n-button>
                </div>
              </div>
            </n-form>
          </div>
          <div class="col-md-6">
            <div class="text-center mt-5">
              <img src="https://i.imgur.com/98GXnDD.png" width="400" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div> -->
</template>

<script>
import axios from "axios";
import http from "@/api/http";
import { Card, FormGroupInput, Button } from "@/components";
export default {
  components: {
    Card,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
  created() {
    this.create();
  },
  data() {
    return {
      codes: "",
      form: {
        userid: "",
        userpwd: "",
        email: "",
        username: "",
      },
      show: true,
    };
  },
  methods: {
    create() {
      this.codes = this.$route.query.code;
      this.getToken();
    },
    login() {
      axios.post("http://localhost:9999/user/login", this.form).then((res) => {
        if (res.data != null) {
          document.cookie = `accessToken=${res.data}`;
          axios.defaults.headers.common["x-access-token"] = res.data;
          this.$router.push("/");
        }
      });
    },
    getToken() {
      http.get(`/user/klogin?authorize_code=${this.codes}`).then((res) => {
        this.form.userid = res.data.userid;
        this.form.userpwd = res.data.userpwd;
        this.form.email = res.data.email;
        this.form.username = res.data.username;

        this.login();
      });
    },
    onSubmit() {
      //   event.preventDefault();
      // alert(JSON.stringify(this.form));
      axios.post("http://localhost:9999/user/regist", this.form).then((res) => {
        console.log(res.status);
        this.login();
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
