<template>
  <tr>
    <!-- <td>{{ articleno }}</td> -->
    <td>{{ articleno }}</td>
    <th class="text-left">
      <router-link
        :to="{ name: 'boardDetail', params: { articleno: articleno } }"
        style="color: black"
        >{{ subject }}
        <a
          href=""
          v-if="commentcount != 0"
          style="color: red; font-weight: 500"
          >{{ [commentcount] }}</a
        ></router-link
      >
    </th>
    <td>{{ hit }}</td>
    <td>{{ username }}</td>
    <td>{{ regtime | dateFormat }}</td>
  </tr>
</template>

<script>
import moment from "moment";
import http from "@/api/http";

export default {
  name: "BoardListItem",
  props: {
    // index: Number,
    articleno: Number,
    userid: String,
    subject: String,
    hit: Number,
    regtime: String,
    username: String,
  },
  data() {
    return {
      commentcount: "",
    };
  },

  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
  created() {
    http.get(`/comment/count/${this.articleno}`).then(({ data }) => {
      this.commentcount = data;
      if (this.commentcount === "") {
        this.commentcount = 0;
      }
    });
  },
};
</script>

<style></style>
