<template>
  <div class="mapapp">
    <div class="category-buttons">
      <el-button
        @click="setSelected('house')"
        :class="{ 'btn-neutral': isHouseSelected }"
        >아파트</el-button
      >
      <el-button
        @click="setSelected('conv')"
        :class="{ 'btn-neutral': isConvSelected }"
        >편의점</el-button
      >
      <el-button
        @click="setSelected('hosp')"
        :class="{ 'btn-neutral': isHospSelected }"
        >병원</el-button
      >
      <el-button
        @click="setSelected('subw')"
        :class="{ 'btn-neutral': isSubwSelected }"
        >지하철</el-button
      >
      <el-button
        @click="setSelected('park')"
        :class="{ 'btn-neutral': isParkSelected }"
        >공원</el-button
      >
      <el-button
        @click="setSelected('cafe')"
        :class="{ 'btn-neutral': isCafeSelected }"
        >카페</el-button
      >
    </div>
    <div id="map"></div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import http from "@/api/http";
import { Button } from "/src/components";
export default {
  components: {
    ElButton: Button,
  },
  data() {
    return {
      isHouseSelected: true,
      isConvSelected: false,
      isHospSelected: false,
      isSubwSelected: false,
      isParkSelected: false,
      isCafeSelected: false,
      markers: [],
      infowindow: null,
      markerConvPositions: [],
      markerHospPositions: [],
      markerSubwPositions: [],
      markerParkPositions: [],
      markerCafePositions: [],
      apt: [],
      customOverays: [],
    };
  },
  computed: {
    ...mapState(["maplng", "maplat", "mapChange", "houses", "dist"]),
  },
  watch: {
    mapChange: function () {
      console.log("mapChange!!");
      this.setAptsOnMap();

      this.setMap();
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      script.onload = () => kakao.maps.load(this.initMap);
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapMutations([
      "SET_HOUSE",
      "SET_SIDE",
      "SET_MAP_LATLNG",
      "SET_IS_STATION_AREA",
      "SET_IS_SHOPPING_AREA",
      "SET_IS_ENVIROMENT_AREA",
    ]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.maplat, this.maplng),
        level: 4,
      };

      this.map = new kakao.maps.Map(container, options);
    },
    setAptsOnMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.maplat, this.maplng),
        level: 4,
      };
      this.map = new kakao.maps.Map(container, options);
      this.setSelected("house");
    },
    setSelected(type) {
      // 선택 전체 해제
      this.isHouseSelected = false;
      this.isConvSelected = false;
      this.isHospSelected = false;
      this.isSubwSelected = false;
      this.isParkSelected = false;
      this.isCafeSelected = false;

      // 전체 마커, 오버레이 초기화
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];

      for (var j = 0; j < this.customOverays.length; j++) {
        this.customOverays[j].setMap(null);
      }

      if (type === "house") {
        let imgSrc = "https://img.icons8.com/color/344/company.png";
        let imgSize = new kakao.maps.Size(40, 46);
        let img = new kakao.maps.MarkerImage(imgSrc, imgSize);

        this.houses.forEach((house) => {
          let marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(house.lat, house.lng),
            title: house.apartmentName,
            image: img,
            clickable: true,
          });

          this.markers.push(marker);
          let content = `<div class ="label"><span class="left"></span><span class="center">${house.apartmentName}</span><span class="right"></span></div>`;
          let customOveray = new kakao.maps.CustomOverlay({
            map: this.map,
            position: new kakao.maps.LatLng(house.lat, house.lng),
            content: content,
          });

          this.customOverays.push(customOveray);
          this.isHouseSelected = true;
        });
      } else {
        let positions = null;
        let imgSrc = null;
        if (type === "conv") {
          imgSrc = "https://cdn-icons-png.flaticon.com/512/7561/7561255.png";
          positions = this.markerConvPositions.map(
            (position) => new kakao.maps.LatLng(...position)
          );
          this.isConvSelected = true;
        } else if (type === "hosp") {
          imgSrc = "https://cdn-icons-png.flaticon.com/512/7561/7561288.png";
          positions = this.markerHospPositions.map(
            (position) => new kakao.maps.LatLng(...position)
          );
          this.isHospSelected = true;
        } else if (type === "subw") {
          imgSrc = "https://cdn-icons-png.flaticon.com/512/7561/7561390.png";
          positions = this.markerSubwPositions.map(
            (position) => new kakao.maps.LatLng(...position)
          );
          this.isSubwSelected = true;
        } else if (type === "park") {
          imgSrc = "https://cdn-icons-png.flaticon.com/512/7561/7561338.png";
          positions = this.markerParkPositions.map(
            (position) => new kakao.maps.LatLng(...position)
          );
          this.isParkSelected = true;
        } else if (type === "cafe") {
          imgSrc = "https://cdn-icons-png.flaticon.com/512/7561/7561235.png";
          positions = this.markerCafePositions.map(
            (position) => new kakao.maps.LatLng(...position)
          );
          this.isCafeSelected = true;
        }
        let imgSize = new kakao.maps.Size(60, 66);
        let img = new kakao.maps.MarkerImage(imgSrc, imgSize);
        if (positions.length > 0) {
          this.markers = positions.map(
            (position) =>
              new kakao.maps.Marker({
                map: this.map,
                position,
                image: img,
              })
          );
        }
      }
    },
    async createConvMarker() {
      this.markerConvPositions = [];
      for (let i = 1; i <= 5; i++) {
        const params = {
          y: this.maplat,
          x: this.maplng,
          radius: this.dist + 500,
          query: "편의점",
          page: i,
          sort: "accuracy",
        };
        await http
          .get("https://dapi.kakao.com/v2/local/search/keyword.json", {
            params,
            headers: {
              Authorization: `KakaoAK 97ffffe3be2fbc99e6c13714da117e3c`,
            },
          })
          .then(({ data }) => {
            data.documents.forEach((conv) => {
              this.markerConvPositions.push([conv.y, conv.x]);
            });
          });
      }
    },
    async createHospMarker() {
      this.markerHospPositions = [];
      const params = {
        y: this.maplat,
        x: this.maplng,
        radius: this.dist + 500,
        query: "병원",
        sort: "accuracy",
      };
      await http
        .get("https://dapi.kakao.com/v2/local/search/keyword.json", {
          params,
          headers: {
            Authorization: `KakaoAK 97ffffe3be2fbc99e6c13714da117e3c`,
          },
        })
        .then(({ data }) => {
          data.documents.forEach((hosp) => {
            this.markerHospPositions.push([hosp.y, hosp.x]);
          });
        });
    },
    async createSubwMarker() {
      this.markerSubwPositions = [];
      const params = {
        y: this.maplat,
        x: this.maplng,
        radius: this.dist + 1000,
        query: "지하철역",
      };
      await http
        .get("https://dapi.kakao.com/v2/local/search/keyword.json", {
          params,
          headers: {
            Authorization: `KakaoAK 97ffffe3be2fbc99e6c13714da117e3c`,
          },
        })
        .then(({ data }) => {
          data.documents.forEach((subw) => {
            this.markerSubwPositions.push([subw.y, subw.x]);
          });
        });
    },
    async createParkMarker() {
      this.markerParkPositions = [];
      const params = {
        y: this.maplat,
        x: this.maplng,
        radius: this.dist + 500,
        query: "공원",
      };
      await http
        .get("https://dapi.kakao.com/v2/local/search/keyword.json", {
          params,
          headers: {
            Authorization: `KakaoAK 97ffffe3be2fbc99e6c13714da117e3c`,
          },
        })
        .then(({ data }) => {
          data.documents.forEach((park) => {
            this.markerParkPositions.push([park.y, park.x]);
          });
        });
    },
    async createCafeMarker() {
      this.markerCafePositions = [];
      for (let i = 1; i <= 5; i++) {
        const params = {
          y: this.maplat,
          x: this.maplng,
          page: i,
          radius: this.dist + 500,
          query: "카페",
          sort: "accuracy",
        };
        await http
          .get("https://dapi.kakao.com/v2/local/search/keyword.json", {
            params,
            headers: {
              Authorization: `KakaoAK 97ffffe3be2fbc99e6c13714da117e3c`,
            },
          })
          .then(({ data }) => {
            data.documents.forEach((cafe) => {
              this.markerCafePositions.push([cafe.y, cafe.x]);
            });
          });
      }
    },
    setBadge() {
      if (this.markerSubwPositions.length >= 1) {
        this.SET_IS_STATION_AREA(true);
      }
      if (this.markerParkPositions.length >= 3) {
        this.SET_IS_ENVIROMENT_AREA(true);
      }
      if (
        this.markerCafePositions.length >= 5 ||
        this.markerConvPositions.length >= 5
      ) {
        this.SET_IS_SHOPPING_AREA(true);
      }
    },
    async setMap() {
      await this.createConvMarker();
      await this.createHospMarker();
      await this.createSubwMarker();
      await this.createParkMarker();
      await this.createCafeMarker();

      this.setBadge();
    },
  },
};
</script>

<style>
#map {
  width: 1500px;
  height: 800px;
  z-index: 0;
}
.label {
  margin-top: 20px;
  color: black;
}
.label * {
  display: inline-block;
  vertical-align: top;
}
.label .left {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png")
    no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  vertical-align: top;
  width: 7px;
}
.label .center {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png")
    repeat-x;
  display: inline-block;
  height: 24px;
  font-size: 12px;
  line-height: 24px;
}
.label .right {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px
    0 no-repeat;
  display: inline-block;
  height: 24px;
  overflow: hidden;
  width: 6px;
}
.category-buttons {
  margin-left: 900px;
  text-align: left;
  position: absolute;
  z-index: 1;
}
</style>
