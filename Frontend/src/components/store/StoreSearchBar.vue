<template>
  <b-row class="mt-4 mb-4 text-center">
    <b-col class="sm-3">
      <b-form-select
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="gugunCode"
        :options="guguns"
        @change="searchStore"
      ></b-form-select>
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "StoreSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
    };
  },
  computed: {
    ...mapState(["sidos", "guguns", "stores"]),
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_STORE_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getStoreList"]),
    ...mapMutations([
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_STORE_LIST",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    searchStore() {
      if (this.gugunCode) this.getStoreList(this.gugunCode);
    },
  },
};
</script>

<style></style>
