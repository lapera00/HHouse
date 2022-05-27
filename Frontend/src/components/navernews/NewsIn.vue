<template>
  <div>
    <div align="right" style="height: 70px">
      <div style="float: right">
        <n-button
          class="btn-base"
          type="default"
          round
          @click="newsin()"
          style="font-size: 15px; margin-top: 20px; height: 40px ;"
          >검색</n-button
        >
      </div>
      <div style="float: right">
        <textarea
          id="textara"
          class="form-control"
          @keyup.enter="newsin()"
          style="
            font-size: 17px;
            color: black;
            max-height: 50px;
            height: 50px;
            max-width: 200px;
            display: inline-block;
            margin-top: 10px;
            margin-right: 20px;
          "
          v-model="search"
          placeholder="검색어를 입력하세요"
        ></textarea>
      </div>
    </div>
    <table class="table">
      <tbody>
        <news-in-list
          v-for="(news, index) in aptnews"
          :key="index"
          v-bind="news"
        />
      </tbody>
    </table>
  </div>
</template>

<script>
import { Button } from "@/components";
import cheerio from "cheerio";
import axios from "axios";
import NewsInList from "./NewsInList.vue";

export default {
  name: "News",
  data() {
    return {
      aptnews: [],
      search: "",
    };
  },
  components: {
    [Button.name]: Button,
    NewsInList,
  },
  created() {
    this.newsin();
  },

  methods: {
    async newsin() {
      let item = "아파트";
      if (this.search == "") {
      } else {
        item = this.search;
      }
      console.log(item);
      const incodeurl = encodeURI(item);
      const API_URL = `https://search.naver.com/search.naver?where=news&ie=UTF-8&query=${incodeurl}`;

      await axios({
        url: API_URL,
        method: "get",
      })
        .then(({ data }) => {
          const $ = cheerio.load(data); // 가지고 오는data load
          //   const $titlist = $(".news_area");
          const $titlist = $(".news_wrap.api_ani_send");
          //   const $uplsit = $(".bx");
          let informations = [];
          $titlist.each((idx, node) => {
            informations.push({
              url: $(node).find(".news_area > a").attr("href"), // 뉴스URL
              title: $(node).find(".news_tit:eq(0)").text(), // 뉴스제목 크롤링
              press: $(node).find(".info_group > a").text(), // 출판사 크롤링
              time: $(node).find(".info_group > span").text(), // 기사 작성 시간 크롤링
              contents: $(node).find(".dsc_wrap").text(), // 기사 내용 크롤링
              thumbnail: $(node).find(" a > img ").attr("src"),
            });
            // $uplsit.each((idx, node) => {
            //   informations.push({
            //     other: $(node).find(" .news_more ").attr("href"),
            //   });
            // });
            // console.log(informations);
            this.aptnews = informations;
          }); //for문과 동일
          this.search = "";
        })
        .catch(console.log("에러"));
    },
  },
};
</script>

<style>
.btn-base {
  background-color: #ffffff;
  color: gray;
  border: 1px solid gray;
}
.btn-base:hover {
  background-color: gray;
}
</style>
