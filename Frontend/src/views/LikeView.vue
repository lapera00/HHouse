<template>
  <div>
    <div class="page-header page-header-small">
      <parallax class="page-header-image">
        <img src="../../public/img/apt/도시야경01-2.jpg" />
        <!-- 교환 -->
      </parallax>
      <div class="content-center">
        <div class="container">
          <h1 class="title">관심 목록</h1>
        </div>
      </div>
    </div>
    <div class="section section-about-us">
      <div class="container">
        <div class="row">
          <div class="col-md-8 ml-auto mr-auto text-center">
            <h2 class="title">내가 좋아요 한 아파트</h2>
            <h5 class="description"></h5>
            <div class="card-container">
              <div v-for="(houseinfo, index) in likeHouseInfos" :key="index">
                <apt-card style="width: 20rem">
                  <div class="card-body">
                    <h4 class="card-title">{{ houseinfo.apartmentName }}</h4>
                    <h6 class="card-subtitle mb-2 text-muted">
                      {{ houseinfo.buildYear }}
                    </h6>
                    <p class="card-text">
                      {{ houseinfo.sidoName }} {{ houseinfo.gugunName }}
                      {{ houseinfo.dongName }}
                    </p>
                    <el-button
                      circle
                      @click="deleteLikeHouse(houseinfo.aptCode)"
                    >
                      <el-icon class="el-icon-delete-solid"></el-icon>
                    </el-button>
                  </div>
                </apt-card>
              </div>
            </div>
          </div>
        </div>
        <div class="separator separator-primary"></div>
      </div>
    </div>
  </div>
</template>

<script>
const memberStore = "memberStore";
import http from "@/api/http";
import { mapState } from "vuex";
import { Card } from "/src/components";
import { Icon, Button } from "element-ui";
import { Notification } from "element-ui";
export default {
  name: "LikeView",
  components: {
    AptCard: Card,
    ElIcon: Icon,
    ElButton: Button,
  },
  data() {
    return {
      likeHouseInfos: [],
      flag: 1,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  watch: {
    flag: function () {
      this.getLikeHouse();
    },
  },
  created() {
    this.getLikeHouse();
  },
  methods: {
    async getLikeHouse() {
      // 로그인하지 않았다면 아무것도 하지 않음
      if (!this.userInfo) {
        return;
      }

      let likeHouseCodes = [];
      console.log(this.userInfo.userid);
      await http
        .get(`/map/like?userid=${this.userInfo.userid}`)
        .then(({ data }) => {
          for (let i = 0; i < data.length; i++) {
            likeHouseCodes.push(data[i].aptCode);
          }
        })
        .catch((error) => {
          console.log(error);
        });

      for (let i = 0; i < likeHouseCodes.length; i++) {
        console.log(likeHouseCodes[i]);
        this.getHouseInfo(likeHouseCodes[i]);
      }
    },
    getHouseInfo(aptCode) {
      http
        .get(`/map/aptinfo?aptCode=${aptCode}`)
        .then((data) => {
          console.log("죠기", data.data);
          this.likeHouseInfos.push(data.data);
        })
        .catch((error) => {
          console.log(error);
        });
      console.log("여기 ", this.likeHouseInfos);
    },
    // // 즐겨찾기 해제할 때
    async deleteLikeHouse(aptCode) {
      console.log(aptCode);
      if (this.userInfo) {
        await http
          .delete(`/map/like?userid=${this.userInfo.userid}&aptCode=${aptCode}`)
          .then(({ data }) => {
            console.log(data);
            Notification.success({
              title: "Success",
              message: "관심목록에서 제거되었습니다",
              type: "success",
            });
            this.isUserLikeHouse = false;
            this.flag *= -1;
            location.reload();
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
</script>
<style>
.card-container {
  /* width: 100%;
  display: flex;
  overflow: auto; */
}
</style>
