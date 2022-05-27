import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
import createPersistedState from "vuex-persistedstate";
import { Notification } from "element-ui";
Vue.use(Vuex);
import memberStore from "@/store/modules/memberStore.js";
export default new Vuex.Store({
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [],
    house: null,
    housedeals: [],
    sido: null,
    gugun: null,
    dong: null,
    maplat: 35.2044277,
    maplng: 126.8077683,
    mapChange: 1,
    dist: null,
    sideMode: "list",
    houseImg: null,
    isStationArea: false,
    isShoppingArea: false,
    isEnviromentArea: false,
  },
  getters: {},
  mutations: {
    /////////////////////////////// House start /////////////////////////////////////
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_SIDO(state, sido) {
      state.sido = sido;
    },
    SET_GUGUN(state, gugun) {
      state.gugun = gugun;
    },
    SET_DONG(state, dong) {
      state.dong = dong;
    },
    SET_HOUSE(state, house) {
      state.house = house;
    },
    SET_SIDE(state, mode) {
      state.sideMode = mode;
    },
    SET_HOUSE_IMG(state, houseImg) {
      state.houseImg = houseImg;
    },
    SET_IS_STATION_AREA(state, flag) {
      state.isStationArea = flag;
    },
    SET_IS_SHOPPING_AREA(state, flag) {
      state.isShoppingArea = flag;
    },
    SET_IS_ENVIROMENT_AREA(state, flag) {
      state.isEnviromentArea = flag;
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST(state) {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_HOUSE_LIST(state) {
      state.houses = [];
    },
    CLEAR_HOUSE(state) {
      state.house = null;
    },
    CLEAR_DIST(state) {
      state.dist = 0;
    },
    CLEAR_HOUSE_DEAL(state) {
      console.log("clear house deal");
      state.housedeals = [];
    },
    CLEAR_IS_STATION_AREA(state) {
      state.isStationArea = false;
    },
    CLEAR_IS_SHOPPING_AREA(state) {
      state.isShoppingArea = false;
    },
    CLEAR_IS_ENVIROMENT_AREA(state) {
      state.isEnviromentArea = false;
    },
    SET_HOUSE_DEAL(state, housedeals) {
      state.housedeals = housedeals;
      console.log("SET_HOUSE_DEAL");
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
      console.log(state.houses);
    },
    SET_MAP_LATLNG(state, { lat, lng }) {
      state.maplat = lat;
      state.maplng = lng;
      state.mapChange = state.mapChange * -1;
    },
    SET_APT_DISTANCE_FROM_CENTER(state, { lat1, lng1, lat2, lng2 }) {
      if (lat1 == lat2 && lng1 == lng2) {
        state.dist = 0;
        return;
      }
      var radLat1 = (Math.PI * lat1) / 180;
      var radLat2 = (Math.PI * lat2) / 180;
      var theta = lng1 - lng2;
      var radTheta = (Math.PI * theta) / 180;
      var dist =
        Math.sin(radLat1) * Math.sin(radLat2) +
        Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
      if (dist > 1) dist = 1;

      dist = Math.acos(dist);
      dist = (dist * 180) / Math.PI;
      dist = dist * 60 * 1.1515 * 1.609344 * 1000;
      if (dist < 100) dist = Math.round(dist / 10) * 10;
      else dist = Math.round(dist / 100) * 100;

      state.dist = dist;
    },
    /////////////////////////////// House end /////////////////////////////////////
  },
  actions: {
    /////////////////////////////// House start /////////////////////////////////////
    getSido({ commit }) {
      http
        .get(`/map/sido`)
        .then(({ data }) => {
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
      commit("SET_SIDE", "list");
      commit("CLEAR_HOUSE");
      commit("CLEAR_GUGUN_LIST");
      commit("CLEAR_DONG_LIST");
      commit("CLEAR_HOUSE_LIST");
    },
    getGugun({ commit }, sidoCode) {
      console.log(sidoCode);
      const params = { sido: sidoCode };
      http
        .get(`/map/gugun`, { params })
        .then(({ data }) => {
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
      commit("SET_SIDE", "list");
      commit("CLEAR_HOUSE");
      commit("CLEAR_DONG_LIST");
      commit("CLEAR_HOUSE_LIST");
    },
    getDong({ commit }, gugunCode) {
      const params = { gugun: gugunCode };
      http
        .get(`/map/dong`, { params })
        .then(({ data }) => {
          commit("SET_DONG_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
      commit("SET_SIDE", "list");
      commit("CLEAR_HOUSE");
      commit("CLEAR_HOUSE_LIST");
    },
    async getHouseList({ commit }, dongCode) {
      const params = { dong: dongCode };
      await http
        .get(`/map/apt`, { params })
        .then(({ data }) => {
          if (data.length === 0) {
            Notification.warning({
              title: "Warning",
              message: "아파트 정보가 없습니다",
              type: "warning",
            });
          } else {
            // 전체 아파트 위치의 중심 좌표 찾기
            let latAvg = 0;
            let lngAvg = 0;
            for (let i = 0; i < data.length; i++) {
              latAvg += parseFloat(data[i].lat);
              lngAvg += parseFloat(data[i].lng);
            }
            latAvg /= data.length;
            lngAvg /= data.length;

            // 중심 좌표에서 가장 먼 아파트와의 거리 찾기
            let latFar = latAvg;
            let lngFar = lngAvg;
            for (let i = 0; i < data.length; i++) {
              if (Math.abs(data[i].lat - latAvg) > Math.abs(latFar - latAvg)) {
                latFar = parseFloat(data[i].lat);
              }
              if (Math.abs(data[i].lng - lngAvg) > Math.abs(lngFar - lngAvg)) {
                lngFar = parseFloat(data[i].lng);
              }
            }

            commit("SET_APT_DISTANCE_FROM_CENTER", {
              lat1: latAvg,
              lng1: lngAvg,
              lat2: latFar,
              lng2: lngFar,
            });
            commit("CLEAR_IS_STATION_AREA");
            commit("CLEAR_IS_SHOPPING_AREA");
            commit("CLEAR_IS_ENVIROMENT_AREA");
            commit("SET_MAP_LATLNG", { lat: latAvg, lng: lngAvg });
            commit("SET_HOUSE_LIST", data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
      commit("SET_SIDE", "list");
    },
    async getHouseDeal({ commit }, aptCode) {
      console.log("getHouseDeal start");
      const params = { apt: aptCode };
      await http
        .get(`/map/deal`, { params })
        .then(({ data }) => {
          for (let i = 0; i < data.length; i++) {
            let date = `${data[i].dealYear}.${data[i].dealMonth}.${data[i].dealDay}`;
            Object.assign(data[i], { dealDate: date });
          }
          commit("SET_HOUSE_DEAL", data);
        })
        .catch((error) => {
          console.log(error);
        });
      console.log("getHouseDeal end");
    },
  },
  modules: {
    memberStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
