import Vue from "vue";
import VueRouter from "vue-router";
import Index from "./pages/Index.vue";
import Landing from "./pages/Landing.vue";
import Login from "./pages/Login.vue";
import Profile from "./pages/Profile.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import BoardView from "./views/BoardView.vue";
import MemberView from "./views/MemberView.vue";
import HouseView from "./views/HouseView.vue";
import StoreView from "./views/StoreView.vue";
import NewsView from "./views/NewsView.vue";
import LikeView from "./views/LikeView.vue";

import store from "@/store/index.js";

Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    // alert("로그인이 필요한 페이지입니다.");
    next({ name: "signin" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    components: { default: Index, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/landing",
    name: "landing",
    components: { default: Landing, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "white" },
    },
  },
  {
    path: "/login",
    name: "login",
    components: { default: Login, header: MainNavbar },
    props: {
      header: { colorOnScroll: 400 },
    },
  },
  {
    path: "/profile",
    name: "profile",
    components: { default: Profile, header: MainNavbar, footer: MainFooter },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/user",
    name: "user",
    components: {
      default: MemberView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
    children: [
      {
        path: "signin",
        name: "signin",
        component: () => import("@/components/user/MemberLogin.vue"),
      },
      {
        path: "signup",
        name: "signup",
        component: () => import("@/components/user/MemberRegister.vue"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/MemberMypage.vue"),
      },
      {
        path: "findpw",
        name: "findpw",
        component: () => import("@/components/user/MemberFindpw.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    components: {
      default: BoardView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },

    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "write",
        name: "boardRegister",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardRegister.vue"),
      },
      {
        path: "detail/:articleno",
        name: "boardDetail",
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "modify/:articleno",
        name: "boardModify",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardModify.vue"),
      },
      {
        path: "delete/:articleno",
        name: "boardDelete",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/board/BoardDelete.vue"),
      },
    ],
  },
  {
    path: "/house",
    name: "house",
    components: {
      default: HouseView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/like",
    name: "like",
    components: {
      default: LikeView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/store",
    name: "store",
    components: {
      default: StoreView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
  {
    path: "/news",
    name: "news",
    components: {
      default: NewsView,
      header: MainNavbar,
      footer: MainFooter,
    },
    props: {
      header: { colorOnScroll: 400 },
      footer: { backgroundColor: "black" },
    },
  },
];

const router = new VueRouter({
  mode: "history",
  linkExactActiveClass: "active",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior: (to) => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  },
});

export default router;
