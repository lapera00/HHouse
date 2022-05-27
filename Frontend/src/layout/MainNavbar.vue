<template>
  <navbar
    position="fixed"
    type="white"
    :transparent="transparent"
    :color-on-scroll="colorOnScroll"
    menu-classes="ml-auto"
    style="font-size: 18px"
  >
    <template>
      <router-link
        v-popover:popover1
        class="navbar-brand"
        :to="{ name: 'home' }"
      >
        <p style="font-size: 25px">HAPPY HOUSE</p>
      </router-link>
      <!-- <el-popover
        ref="popover1"
        popper-class="popover"
        placement="bottom"
        width="200"
        trigger="hover"
      >
        <div class="popover-body">
          Copyright for 아파트는돌덩이[ 이승진, 정현명 ]
        </div>
      </el-popover> -->
    </template>
    <template slot="navbar-menu">
      <li class="nav-item">
        <router-link :to="{ name: 'board' }">
          <a class="nav-link" target="_blank">
            <i class="now-ui-icons design_bullet-list-67"></i>
            <p>공지사항</p>
          </a>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link :to="{ name: 'news' }">
          <a class="nav-link" target="_blank">
            <i class="now-ui-icons education_paper"></i>
            <p>뉴스</p>
          </a>
        </router-link>
      </li>
      <li class="nav-item">
        <router-link :to="{ name: 'house' }">
          <a class="nav-link" target="_blank">
            <i class="el-icon-s-home" style="font-size: 18px; margin: 2px"></i>
            <p>아파트</p>
          </a>
        </router-link>
      </li>

      <drop-down
        v-if="userInfo"
        tag="li"
        :title="`${userInfo.username}`"
        icon="now-ui-icons  users_single-02"
        class="nav-item"
      >
        <nav-link>
          <router-link :to="{ name: 'home' }" style="color: black"
            ><i class="el-icon-close"></i>
            <p @click="onClickLogout">로그아웃</p></router-link
          >
        </nav-link>
        <nav-link>
          <router-link :to="{ name: 'mypage' }" style="color: black"
            ><i class="now-ui-icons business_badge"></i> 내정보</router-link
          >
        </nav-link>
        <nav-link>
          <router-link :to="{ name: 'like' }" style="color: black"
            ><i class="el-icon-star-off"></i> 관심목록</router-link
          >
        </nav-link>
      </drop-down>

      <li class="nav-item" v-if="userInfo == null">
        <router-link :to="{ name: 'signin' }">
          <a class="nav-link" target="_blank">
            <i class="el-icon-user" style="font-size: 18px; margin: 2px"></i>
            <p>로그인</p>
          </a>
        </router-link>
      </li>

      <li class="nav-item" v-if="userInfo == null">
        <router-link :to="{ name: 'signup' }">
          <a class="nav-link" target="_blank">
            <i class="now-ui-icons sport_user-run"></i>
            <p>회원가입</p>
          </a>
        </router-link>
      </li>

      <!-- <li class="nav-item">
        <a
          class="nav-link btn btn-neutral"
          href="https://www.creative-tim.com/product/vue-now-ui-kit-pro"
          target="_blank"
        >
          <i class="now-ui-icons arrows-1_share-66"></i>
          <p>Upgrade to PRO</p>
        </a>
      </li> -->

      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Twitter"
          data-placement="bottom"
          href="https://twitter.com/"
          target="_blank"
        >
          <i class="fab fa-twitter"></i>
          <p class="d-lg-none d-xl-none">Twitter</p>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Like us on Facebook"
          data-placement="bottom"
          href="https://www.facebook.com/"
          target="_blank"
        >
          <i class="fab fa-facebook-square"></i>
          <p class="d-lg-none d-xl-none">Facebook</p>
        </a>
      </li>
      <li class="nav-item">
        <a
          class="nav-link"
          rel="tooltip"
          title="Follow us on Instagram"
          data-placement="bottom"
          href="https://www.instagram.com/"
          target="_blank"
        >
          <i class="fab fa-instagram"></i>
          <p class="d-lg-none d-xl-none">Instagram</p>
        </a>
      </li>
    </template>
  </navbar>
</template>

<script>
import { DropDown, Navbar, NavLink } from "@/components";
import { Popover, Notification } from "element-ui";
import { mapState, mapActions, mapGetters, mapMutations } from "vuex";
import swal from "sweetalert";
const memberStore = "memberStore";

export default {
  name: "main-navbar",
  props: {
    transparent: Boolean,
    colorOnScroll: Number,
  },
  components: {
    DropDown,
    Navbar,
    NavLink,
    [Popover.name]: Popover,
    ElNotification: Notification,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout", "getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),

    onClickLogout() {
      Notification.success({
        title: "Success",
        message: "로그아웃 되었습니다.",
        type: "success",
      });
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "home" });
    },
    async check() {
      await this.$store.dispatch("refresh", this.checkUserInfo);
    },
  },
};
</script>

<style scoped></style>
