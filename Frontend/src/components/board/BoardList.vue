<template>
  <div>
    <div align="right">
      <div v-if="userInfo">
        <n-button
          class="btn-base"
          v-if="userInfo.authority == 5"
          @click="moveWrite()"
          type="default"
          style="
            font-size: 16px;
            padding-top: 7px;
            padding-bottom: 7px;
            padding-left: 15px;
            padding-right: 15px;
          "
          >글쓰기</n-button
        >
      </div>
    </div>
    <table class="table" width="1000" style="font-size: 17px">
      <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>조회수</th>
          <th>작성자</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody>
        <board-list-item
          v-for="article in articles"
          :key="article.articleno"
          v-bind="article"
        />
      </tbody>
    </table>
  </div>
</template>

<script>
import { Button, FormGroupInput } from "@/components";
import http from "@/api/http";
import BoardListItem from "@/components/board/item/BoardListItem";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "BoardList",
  components: {
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    BoardListItem,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  data() {
    return {
      articles: [],
      pageLimit: 10,
      pageOffet: 0,
      articleidnex: "",
    };
  },
  created() {
    this.initList();
  },
  watch: {
    "$route.query": function () {
      this.initList();
    },
  },

  methods: {
    moveWrite() {
      this.$router.push({ name: "boardRegister" });
    },

    initList() {
      http.get(`/board/pagelink/count`).then(({ data }) => {
        this.articleidnex = data;
      });
      if (this.$route.query.no == null) {
        this.$route.query.no = 10;
      }
      http
        .get(`/board/pagelink`, {
          params: {
            limit: this.pageLimit,
            offset: `${this.$route.query.no - this.pageLimit}`,
          },
        })
        .then(({ data }) => {
          this.articles = data;
        });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}

.btn-base {
  background-color: #ffffff;
  color: gray;
  border: 1px solid gray;
}
.btn-base:hover {
  background-color: gray;
}
.a:hover {
  background-color: gray;
}
</style>
