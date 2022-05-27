<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image">
        <img src="../../public/img/apt/도시야경01-2.jpg" />
        <!-- 교환 -->
      </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">공지사항</h1>
          <!-- <div class="text-center">
            <a href="#pablo" class="btn btn-primary btn-icon btn-round">
              <i class="fab fa-facebook-square"></i>
            </a>
            <a href="#pablo" class="btn btn-primary btn-icon btn-round">
              <i class="fab fa-twitter"></i>
            </a>
            <a href="#pablo" class="btn btn-primary btn-icon btn-round">
              <i class="fab fa-google-plus"></i>
            </a>
          </div> -->
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title" style="margin-bottom: 70px">공지사항</h2>
            <!-- <h5 class="description">공지사항입니다222</h5> -->
          </div>
        </div>
        <div class="separator separator-primary"></div>
        <div class="text-center">
          <router-view></router-view>
          <board-page />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Button, FormGroupInput } from "@/components";
import BoardPage from "@/components/board/BoardPage.vue";

export default {
  name: "landing",
  bodyClass: "landing-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    BoardPage,
  },
  data() {
    return {
      form: {
        firstName: "",
        email: "",
        message: "",
      },
    };
  },
};
</script>
<style></style>
