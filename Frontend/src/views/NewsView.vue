<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image">
        <img src="../../public/img/apt/도시야경01-10.jpg" />
        <!-- 교환 -->
      </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">뉴스</h1>
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title">아파트 관련 뉴스</h2>
            <h5 class="description">출처 : 네이버뉴스</h5>
          </div>
        </div>
        <div class="separator separator-primary"></div>
        <div class="text-center">
          <news-in />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Button, FormGroupInput } from "@/components";
import NewsIn from "@/components/navernews/NewsIn.vue";

export default {
  name: "landing",
  bodyClass: "landing-page",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    NewsIn,
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
