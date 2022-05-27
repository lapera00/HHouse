<template>
  <div
    class="item"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
  >
    <div cols="2" class="text-center align-self-center"></div>
    <a href="#" :class="{ 'mouse-over-color': isColor }" @click="goDetail">{{
      house.apartmentName
    }}</a>
    <div cols="10" class="align-self-center">
      {{ house.gugunName }} {{ house.dongName }}
    </div>
    <div>거래 수 : {{ house.cnt }}</div>
    <div>준공년도 : {{ house.buildYear }}</div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapMutations(["SET_HOUSE", "SET_SIDE", "SET_MAP_LATLNG", "CLEAR_DIST"]),
    ...mapState(["sideMode"]),
    colorChange(flag) {
      this.isColor = flag;
    },
    goDetail() {
      this.CLEAR_DIST();
      this.SET_HOUSE(this.house);
      this.SET_MAP_LATLNG({ lat: this.house.lat, lng: this.house.lng });
      this.SET_SIDE("detail");
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-color {
  color: tomato;
}
.item {
  margin-bottom: 20px;
  padding-top: 10px;
  border-top: 1px solid darkgray;
}
a {
  font-size: 15px;
}
</style>
