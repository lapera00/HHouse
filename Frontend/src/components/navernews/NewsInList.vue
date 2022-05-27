<template>
  <tr>
    <!-- <td>{{ articleno }}</td> -->
    <div align="left">
      <div class="news" style="float: left; width: 840px; padding-top: 30px">
        <p>{{ press }} | {{ time }}</p>
        <p>
          <a
            :href="url"
            style="color: blue; font-weight: 500; font-size: 19px"
            >{{ title }}</a
          >
        </p>
        <a :href="url" style="color: black"
          ><p>{{ contents }}</p></a
        >
        <!-- <p>{{ other }}</p> -->
      </div>
      <div class="news" style="float: right; padding-top: 30px">
        <a :href="url" style="color: black"><img :src="thumbnail" alt="" /></a>
      </div>
      <hr style="margin-top: 30px" />
    </div>
    <!-- <div><hr /></div> -->
  </tr>
</template>

<script>
export default {
  name: "NewsInList",
  props: {
    url: String,
    title: String,
    contents: String,
    time: String,
    press: String,
    thumbnail: String,
    // other: String,
  },
};
</script>

<style></style>
