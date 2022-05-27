<template>
  <div>
    <div class="page-header clear-filter" filter-color="blue">
      <parallax
        class="page-header-image"
        style="background-image: url('img/apt/도시야경01-9.jpg')"
      >
      </parallax>
      <div class="container">
        <div class="content-center brand">
          <!-- <img class="n-logo" src="img/now-logo.png" alt="" /> -->
          <h1 class="h1-seo">HAPPY HOUSE</h1>
          <h3>더 쉽게 더 완벽하게 나에게 맞는 아파트 찾기 서비스</h3>
        </div>
        <h6
          class="category category-absolute"
          style="text-align: center; margin-left: 8px"
        >
          Made by 아파트는돌덩이 [ 이승진, 정현명 ]
          <!-- <a href="#" target="_blank" onclick="return false;">
            <img src="img/invision-white-slim.png" class="invision-logo" /> </a
          >. Coded by -->
          <!-- <a href="#" target="_blank" onclick="return false;">
            <img
              src="img/creative-tim-white-slim2.png"
              class="creative-tim-logo"
            /> </a
          >. -->
        </h6>
      </div>
    </div>
    <!-- <div class="main">
      <div class="section section-images">
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <div class="hero-images-container">
                <img src="img/hero-image-1.png" alt="" />
              </div>
              <div class="hero-images-container-1">
                <img src="img/hero-image-2.png" alt="" />
              </div>
              <div class="hero-images-container-2">
                <img src="img/hero-image-3.png" alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>
<script>
import { Parallax } from "@/components";

export default {
  name: "index",
  bodyClass: "index-page",
  components: {
    Parallax,
  },
};
</script>
<style></style>
