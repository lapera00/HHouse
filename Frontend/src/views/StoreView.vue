<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-orange">
      <b-icon icon="house-fill"></b-icon> Store Service
    </h3>
    <b-row>
      <b-col>
        <store-search-bar></store-search-bar>
        <store-list></store-list>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import StoreSearchBar from "@/components/store/StoreSearchBar.vue";
import StoreList from "@/components/store/StoreList.vue";
// import HouseDetail from "@/components/house/HouseDetail.vue";

export default {
  name: "StoreView",
  components: {
    StoreSearchBar,
    StoreList,
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
</style>
