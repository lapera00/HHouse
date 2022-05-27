import jwt_decode from "jwt-decode";
import axios from "axios";
import { Notification } from "element-ui";
import router from "../../router";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false, //로그인 여부
    isLoginError: false, //로그인 중 에러 발생 여부
    userInfo: null, //로그인 정보
  },
  getters: {
    checkUserInfo: function (state) {
      //로그인 정보 가져오기
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      // 로그인 여부 셋팅
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      //로그인 에러 여부 셋팅
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      //로그인 정보 셋팅
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      //id,pw로 로그인 처리
      const instance = axios.create({
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });
      await instance
        .post("/user/login", JSON.stringify(user))
        .then((response) => {
          //정상 응답 받으면,
          if (response.data.message === "success") {
            let acToken = response.data["access-token"]; //access 토큰 정보(jwt)
            let refToken = response.data["refresh-token"]; //refresh-token 정보
            commit("SET_IS_LOGIN", true); //로그인 여부 셋팅
            commit("SET_IS_LOGIN_ERROR", false); // 에러 없음 셋팅
            sessionStorage.setItem("access-token", acToken); //세션 스토리지에 토큰 저장해두기
            sessionStorage.setItem("refresh-token", refToken);
          } else {
            alert(
              "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."
            );
            commit("SET_IS_LOGIN", false); //로그인 실패 저장
            commit("SET_IS_LOGIN_ERROR", true); //로그인 에러 셋팅
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token); //유저 정보 얻기 위해 토큰 해석

      axios.defaults.headers["access-token"] =
        sessionStorage.getItem("access-token"); //axios에 access-token 셋팅

      const instance = axios.create({
        //axios 통신 보낼때 Content-type 포함해야함
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });

      await instance
        .get(`/user/info/${decode_token.userid}`)
        .then((response) => {
          //정상 응답 받으면
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo); //유저 정보 셋팅
            // console.log(response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        })
        .catch((err) => {
          console.log(err.response.status);
          if (err.response.status === 401) {
            //응답으로 401은 권한 에러 : access token 만료
            console.log("토큰 재발급 받으세요.");
          }
        });
    },
    async userLogout({ commit }, userid) {
      const instance = axios.create({
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });
      await instance.put(`/user/logout/${userid}`).then((response) => {
        //정상 응답 받으면,
        if (response.data.message === "success") {
          console.log("리프레시 토큰 제거 성공");
          commit("SET_IS_LOGIN", false);
          commit("SET_USER_INFO", null);
        } else {
          console.log("리프레시 토큰 제거 실패");
        }
      });
      router.push({ name: "home" });
    },
    async refresh({ commit }, user) {
      const instance = axios.create({
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });
      instance.defaults.headers["refresh-token"] =
        sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
      console.log("토큰 재발급 시작");
      console.log("기존 토큰 : {}", sessionStorage.getItem("access-token"));

      await instance
        .post(`user/refresh`, JSON.stringify(user))
        .then((response) => {
          //발급 성공
          if (response.data.message === "success") {
            let acToken = response.data["access-token"]; //응답 데이터 중 access-token 꺼내기
            console.log("새로 발급 : {}", acToken);
            sessionStorage.setItem("access-token", acToken); //sessionStorage에 꺼낸 access-token 저장
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            //401받으면? 리스페시 토큰도 기간 만료!! --> 다시 로그인 필수
            console.log("갱신 실패");
            //다시 로그인 전에 DB에 저장된 리프레시 토큰 제거 수행
            instance
              .put(`/user/logout/${user.userid}`)
              .then((response) => {
                if (response.data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("리프레시 토큰도 만료되었습니다. 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false); //유저 로그인 상태 바꾸기
                commit("SET_USER_INFO", null); // 셋팅된 유저 정보 없애기
              })
              .catch((err) => {
                console.log(err);
                alert("뭔가 잘못 됐어요...");
                commit("SET_IS_LOGIN", false); //유저 로그인 상태 바꾸기
                commit("SET_USER_INFO", null); // 셋팅된 유저 정보 없애기
              });
          }
        });
    },
    async userUpdate({ commit, dispatch }, user) {
      const instance = axios.create({
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });
      await instance
        .put("/user/update", JSON.stringify(user))
        .then((response) => {
          //정상 응답 받으면,
          if (response.data.message === "success") {
            console.log("회원정보를 수정하였습니다. ");
            commit("SET_USER_INFO", user);
          } else {
            console.log("수정 실패");
          }
        })
        .catch((err) => {
          console.log(err.response.status);
          if (err.response.status === 401) {
            //응답으로 401은 권한 에러 : access token 만료
            Notification.error({
              title: "Error",
              message: "로그인 시간이 만료되었습니다. 다시 로그인해 주세요.",
              type: "error",
            });
            console.log("토큰 재발급 받으세요.");
            dispatch("userLogout", user.userid);
          }
        });
    },
    async userDelete({ commit, dispatch }, user) {
      const instance = axios.create({
        baseURL: "http://localhost:9999/vue",
        headers: {
          "Content-type": "application/json",
        },
      });
      await instance
        .delete("/user/delete", {
          data: user,
        })
        .then((response) => {
          //정상 응답 받으면,
          if (response.data.message === "success") {
            console.log(commit);
            console.log("회원탈퇴 되었습니다.");
            Notification.success({
              title: "Success",
              message: "회원탈퇴 되었습니다",
              type: "success",
            });
          } else {
            console.log("에러발생");
          }
        })
        .catch((err) => {
          console.log(err.response.status);
          if (err.response.status === 401) {
            //응답으로 401은 권한 에러 : access token 만료
            Notification.error({
              title: "Error",
              message: "로그인 시간이 만료되었습니다. 다시 로그인해 주세요.",
              type: "error",
            });
            console.log("토큰 재발급 받으세요.");
            dispatch("userLogout", user.userid);
          }
        });
    },
  },
};

export default memberStore;
