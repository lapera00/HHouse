<template>
  <div class="search-bar" align="center">
    <div id="container">
      <el-select v-model="sidoCode" @change="gugunList">
        <el-option
          v-for="sido in sidos"
          :key="sido.value"
          :label="sido.text"
          :value="sido.value"
        >
        </el-option>
      </el-select>
      <el-select v-model="gugunCode" @change="dongList">
        <el-option
          v-for="gugun in guguns"
          :key="gugun.value"
          :label="gugun.text"
          :value="gugun.value"
        >
        </el-option>
      </el-select>
      <el-select v-model="dongCode" @change="searchApt">
        <el-option
          v-for="dong in dongs"
          :key="dong.value"
          :label="dong.text"
          :value="dong.value"
        >
        </el-option>
      </el-select>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { Select, Option } from "element-ui";

export default {
  name: "HouseSearchBar",
  components: {
    ElSelect: Select,
    ElOption: Option,
  },
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(["sidos", "guguns", "dongs", "houses"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getDong", "getHouseList"]),
    ...mapMutations([
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_HOUSE_LIST",
      "CLEAR_HOUSE",
    ]),
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      console.log(this.dongCode);
      this.CLEAR_HOUSE_LIST();
      this.CLEAR_HOUSE();
      if (this.dongCode) this.getHouseList(this.dongCode);
    },
  },
};
</script>

<style>
.search-bar {
  position: absolute;
  margin-top: 10px;
  margin-left: 10px;
  z-index: 1;
}
</style>
