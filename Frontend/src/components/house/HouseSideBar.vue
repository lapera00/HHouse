<template>
  <div class="apt-side-app">
    <div class="apt-list" v-if="sideMode === 'list'">
      <div class="content">
        <h3>현재 지역의 아파트 정보</h3>
        <div class="apt-list-item">
          <house-list-item
            v-for="(house, index) in houses"
            :key="index"
            :house="house"
          ></house-list-item>
        </div>
      </div>
    </div>
    <div v-else class="apt-deal">
      <div class="content">
        <div class="apt-deal-info content-item">
          <div class="apt-title-content">
            <div style="overflow: hidden">
              <span class="apt-title" style="float: left">
                {{ house.apartmentName }}
              </span>

              <div id="modals">
                <div class="modal-buttons">
                  <el-button
                    type="default"
                    @click.native="modals.classic = true"
                    @click="viewreview()"
                    style="margin-top: 0px; margin-left: 20px"
                  >
                    리뷰보기
                  </el-button>
                  <span
                    class="apt-like"
                    style="float: right"
                    v-if="userInfo !== null"
                  >
                    <el-button
                      v-if="!isUserLikeHouse"
                      type="warning"
                      class="el-icon-star-off"
                      circle
                      @click="registLikeHouse"
                      style="margin-top: 0px"
                    ></el-button>
                    <el-button
                      v-else
                      type="warning"
                      class="el-icon-star-on"
                      circle
                      @click="deleteLikeHouse"
                      style="margin-top: 0px"
                    ></el-button>
                  </span>
                </div>
              </div>
            </div>
            <span class="apt-badge">
              <apt-badge type="info" v-if="isStationArea">역세권</apt-badge>
              <apt-badge type="primary" v-if="isShoppingArea">인프라</apt-badge>
              <apt-badge type="success" v-if="isEnviromentArea">환경</apt-badge>
              <el-popover
                placement="bottom"
                title="뱃지 기준"
                width="600"
                trigger="click"
              >
                <h5>
                  <apt-badge type="info">역세권</apt-badge> : 아파트 기준 1km
                  이내 지하철 역이 존재할 시
                </h5>
                <h5>
                  <apt-badge type="primary">인프라</apt-badge> : 아파트 기준
                  500m 이내 편의점 또는 카페가 5개 이상일 시
                </h5>
                <h5>
                  <apt-badge type="success">환경</apt-badge> : 아파트 기준 500m
                  이내 공원이 3개 이상일 시
                </h5>
                <i
                  class="el-icon-question"
                  size="medium"
                  style="margin-left: 30px"
                  slot="reference"
                ></i>
              </el-popover>
            </span>
          </div>
          <div>
            <div>{{ house.buildYear }}년 준공</div>
            <div>
              {{ house.sidoName }} {{ house.gugunName }} {{ house.dongName }}
              {{ house.jibun }}
            </div>
          </div>
        </div>

        <div class="content-item">
          <h5 class="sub-title">이집 시세는?</h5>
          <div>{{ minDealAmount }} ~ {{ maxDealAmount }}</div>
        </div>
        <div class="content-item">
          <h5 class="sub-title">
            실거래가
            <el-popover
              placement="bottom"
              title="날짜 별 실거래가 (최근 10 거래)"
              width="800"
              trigger="click"
              ><div>
                <canvas
                  id="myChart"
                  style="display: block"
                  class="chartjs-render-monitor"
                ></canvas>
              </div>
              <el-button
                icon="el-icon-pie-chart"
                circle
                slot="reference"
              ></el-button>
            </el-popover>
          </h5>
          <div>{{ house.dealAmount }}</div>
        </div>
        <div class="apt-deal-item content-item">
          <el-table :data="housedeals">
            <el-table-column prop="dealDate" width="110" label="거래일자" />
            <el-table-column prop="area" label="면적" width="70" />
            <el-table-column prop="floor" label="층" width="50" />
            <el-table-column prop="dealAmount" label="거래가격" />
          </el-table>
        </div>
      </div>
    </div>
    <modal :show.sync="modals.classic" headerClasses="justify-content-center">
      <h4 slot="header" class="title title-up">아파트 리뷰</h4>
      <table class="table">
        <thead>
          <tr>
            <th>작성자</th>
            <th>내용</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          <review-list-item
            v-for="article in reviews"
            :key="article.commentno"
            v-bind="article"
            @viewreview="viewreview()"
          />
        </tbody>
      </table>
      <div v-if="idch()">
        <div style="float: left; width: 355px">
          <fg-input
            v-model="content"
            class="no-border input-lg"
            addon-left-icon="now-ui-icons ui-1_simple-delete"
            placeholder="댓글을 입력하세요"
          >
          </fg-input>
        </div>
        <div style="float: left">
          <el-button
            class="buttonr"
            type="defult"
            simple
            round
            @click="writereview()"
            style="
              height: 40px;
              width: 80px;
              font-size: 15px;
              margin-top: 3px;
              margin-left: 5px;
            "
            >등록</el-button
          >
        </div>
      </div>
    </modal>
  </div>
</template>

<script>
import HouseListItem from "@/components/house/HouseListItem.vue";
import ReviewListItem from "@/components/house/ReviewListItem.vue";
import { mapActions, mapState, mapMutations } from "vuex";
import { Table, TableColumn, Popover, Button, Notification } from "element-ui";
import { Badge, Modal, FormGroupInput } from "@/components";
import http from "@/api/http";
const memberStore = "memberStore";
import Chart from "chart.js";

export default {
  name: "HouseList",
  components: {
    HouseListItem,
    ElTable: Table,
    ElTableColumn: TableColumn,
    AptBadge: Badge,
    ElButton: Button,
    Modal,
    ElPopover: Popover,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    ReviewListItem,
  },
  data() {
    return {
      minDealAmount: null,
      maxDealAmount: null,
      isUserLikeHouse: false,
      modals: {
        classic: false,
        mini: false,
      },
      reviews: [],
      content: "",
    };
  },
  watch: {
    sideMode: async function () {
      console.log("sideMode change!!");
      if (this.sideMode === "detail") {
        this.CLEAR_HOUSE_DEAL();
        this.isUserLikeHouse = false;
        this.housedeals = null;
        await this.getHouseDeal(this.house.aptCode);
        if (this.housedeals !== null) {
          this.createChart("myChart", this.chartData);
        }
        this.getLikeHouse();
      }
    },
  },
  beforeUpdate() {
    console.log("beforeUpdate");
    this.getMaxDealAmount();
    this.getMinDealAmount();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
    ...mapState([
      "houses",
      "sideMode",
      "house",
      "housedeals",
      "isStationArea",
      "isShoppingArea",
      "isEnviromentArea",
    ]),
  },
  methods: {
    ...mapMutations(["CLEAR_HOUSE_DEAL"]),
    ...mapActions(["getHouseDeal"]),
    ...mapActions(memberStore, ["getUserInfo"]),
    getMaxDealAmount() {
      let maxValue = String(
        Math.max(
          ...this.housedeals.map((item) =>
            parseInt(item.dealAmount.replaceAll(",", ""))
          )
        )
      );
      if (maxValue.length >= 5) {
        this.maxDealAmount =
          maxValue.substring(0, maxValue.length - 4) +
          "억" +
          maxValue.substring(maxValue.length - 4) +
          "만원";
      } else {
        this.maxDealAmount = maxValue + "만원";
      }
    },
    getMinDealAmount() {
      let minValue = String(
        Math.min(
          ...this.housedeals.map((item) =>
            parseInt(item.dealAmount.replaceAll(",", ""))
          )
        )
      );
      if (minValue.length >= 5) {
        this.minDealAmount =
          minValue.substring(0, minValue.length - 4) +
          "억" +
          minValue.substring(minValue.length - 4) +
          "만원";
      } else {
        this.minDealAmount = minValue + "만원";
      }
    },

    // 유저가 현재 아파트를 즐겨찾기 했는 지 확인하는 함수
    getLikeHouse() {
      // 로그인하지 않았다면 아무것도 하지 않음
      if (!this.userInfo) {
        return;
      }
      http
        .get(`/map/like?userid=${this.userInfo.userid}`)
        .then(({ data }) => {
          for (let i = 0; i < data.length; i++) {
            if (data[i].aptCode === this.house.aptCode) {
              console.log("이미 이 아파트 즐겨찾기함");
              this.isUserLikeHouse = true;
              break;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // 아파트 즐겨찾기 눌렀을 때 db에 저장하는 함수
    async registLikeHouse() {
      if (this.userInfo) {
        const params = {
          userid: this.userInfo.userid,
          aptCode: this.house.aptCode,
        };
        console.log(params);
        await http
          .post(`/map/like`, params)
          .then(({ data }) => {
            Notification.success({
              title: "Success",
              message: "관심목록에 등록되었습니다",
              type: "success",
            });
            this.isUserLikeHouse = true;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },

    // 즐겨찾기 해제할 때
    async deleteLikeHouse() {
      if (this.userInfo) {
        const params = {
          userid: this.userInfo.userid,
          aptCode: this.house.aptCode,
        };
        console.log(params);
        await http
          .delete(
            `/map/like?userid=${this.userInfo.userid}&aptCode=${this.house.aptCode}`
          )
          .then(({ data }) => {
            console.log(data);
            Notification.success({
              title: "Success",
              message: "관심목록에서 제거되었습니다",
              type: "success",
            });
            this.isUserLikeHouse = false;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },

    createChart(chartId, chartData) {
      console.log("createChart");
      console.log(this.housedeals);

      let labels = [];
      let data = [];
      for (let i = 0; i < this.housedeals.length; i++) {
        if (i >= 10) {
          break;
        }
        labels.unshift(this.housedeals[i].dealDate);
        data.unshift(
          parseInt(this.housedeals[i].dealAmount.replaceAll(",", ""))
        );
      }

      chartData = {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              label: "실거래가",
              data: data,
              fill: { value: 25 },
              borderColor: "rgb(141,71,255)",
              tension: 0.1,
            },
          ],
        },
      };

      const ctx = document.getElementById(chartId);
      const myChart = new Chart(ctx, {
        type: chartData.type,
        data: chartData.data,
        options: chartData.options,
      });
    },
    viewreview() {
      console.log("test" + this.housedeals);
      console.log(this.housedeals[0].aptCode);
      console.log("dd?");

      http
        .get(`/housereview/${this.housedeals[0].aptCode}`)
        .then(({ data }) => {
          this.reviews = data;
          console.log(this.reviews);
        });
    },
    idch() {
      if (this.userInfo == null) {
        return false;
      } else {
        return true;
      }
    },
    writereview() {
      http
        .post(`/housereview`, {
          userid: this.userInfo.userid,
          content: this.content,
          aptCode: this.housedeals[0].aptCode,
        })
        .then(({ data }) => {
          let msg = "문제가 발생했습니다.";
          if (data === "success") {
            msg = "리뷰작성이 완료되었습니다.";
          }
          this.content = "";
          this.viewreview();
        });
    },
  },
};
</script>

<style>
.apt-side-app {
  width: 24%;
  color: black;
  text-align: left;
  height: 800px;
  overflow: auto;
}

.apt-list,
.apt-deal {
  margin-top: 5%;
}

.content {
  margin-left: 20px;
  margin-right: 20px;
  /* background-color: silver; */
}

.content-item {
  /* background-color: white; */
  margin-bottom: 30px;
}

.apt-deal-info {
  color: grey;
}

.apt-title-content,
.sub-title {
  border-bottom: 1px solid grey;
  color: black;
}

.apt-title {
  font-size: 25px;
  color: black;
  font-weight: 500;
}

.apt-badge {
  font-size: 20px;
}
</style>
