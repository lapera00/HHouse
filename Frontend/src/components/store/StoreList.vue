<template>
  <b-container v-if="stores && stores.length != 0" class="bv-example-row mt-3">
    <b-table striped hover :items="stores" :fields="fields"></b-table>
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>주변 상가 정보가 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "StoreList",
  data() {
    return {
      fields: [
        {
          key: "indsLclsNm",
          label: "분류",
        },
        {
          key: "bizesNm",
          label: "이름",
        },
        {
          key: "lnoAdr",
          label: "주소",
        },
      ],
    };
  },
  computed: {
    ...mapState(["stores"]),
  },
};
</script>

<style></style>
