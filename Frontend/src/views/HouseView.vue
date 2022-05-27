<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image">
        <img src="../../public/img/apt/도시야경01-12.jpg" />
        <!-- 교환 -->
      </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">아파트</h1>
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
    <div>
      <div class="container" style="max-width: 100%">
        <div class="row section">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title">나에게 맞는 아파트 찾기</h2>
            <h5 class="description">지역을 선택하세요</h5>
            <!-- <h5 class="description"></h5> -->
          </div>
        </div>
        <div class="separator separator-primary"></div>
        <div class="content-center" align="center">
          <house-search-bar></house-search-bar>
          <div class="apt-info" align="center">
            <house-map></house-map>
            <house-side-bar></house-side-bar>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseMap from "@/components/house/HouseMap.vue";
import { Button, FormGroupInput } from "@/components";
import HouseSideBar from "@/components/house/HouseSideBar.vue";
export default {
  name: "HouseView",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    HouseSearchBar,
    HouseMap,
    HouseSideBar,
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}

.apt-info {
  display: flex;
}
</style>
