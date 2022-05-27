<template>
  <div class="page-header clear-filter" filter-color="white">
    <div class="page-header-image"></div>
    <div class="content">
      <div class="container">
        <div class="col-md-5 ml-auto mr-auto">
          <router-view></router-view>
        </div>
      </div>
    </div>
    <!-- <main-footer></main-footer> -->
  </div>
</template>
<script>
import { Card, Button, FormGroupInput } from "@/components";
import MainFooter from "@/layout/MainFooter";
export default {
  name: "login-page",
  bodyClass: "login-page",
  components: {
    Card,
    MainFooter,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
  },
};
</script>
<style>
.page-header-image {
  background-image: url("../assets/도시야경01-9.jpg");

  /* background-color: gray; */
}
</style>
