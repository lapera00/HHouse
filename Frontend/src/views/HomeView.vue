<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h3 class="underline-steelblue">
      <!-- <b-icon icon="house"></b-icon> HappyHouse -->
    </h3>
    <b-row>
      <b-col></b-col>
      <b-col cols="">
        <b-jumbotron
          bg-variant="light"
          text-variant="dark"
          border-variant="light"
        >
          <template #header
            >HappyHouse
            <div style="height: 50px"></div>
          </template>

          <template #lead>
            <img src="@/assets/house_1.png" alt="" />
          </template>

          <div style="height: 300px"></div>

          <hr class="my-4" />
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "HomeView",
  props: {
    msg: String,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
