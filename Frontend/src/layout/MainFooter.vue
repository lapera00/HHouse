<template>
  <footer
    class="footer"
    :class="{ [`footer-${type}`]: type }"
    :data-background-color="backgroundColor"
  >
    <div class="container">
      <nav>
        <ul>
          <li>
            <!-- <a href="https://www.creative-tim.com"> Creative Tim </a> -->
            <a href="" onclick="return false;">Project</a>
          </li>
          <li>
            <!-- <a href="https:///presentation.creative-tim.com"> About Us </a> -->
            <a href="" onclick="return false;">광주 4반</a>
          </li>
          <li>
            <!-- <a href="https:///blog.creative-tim.com"> Blog </a> -->
            <a href="" onclick="return false;">아파트는돌덩이</a>
          </li>
        </ul>
      </nav>
      <div class="copyright">
        &copy; {{ year }}. Copyright for 아파트는돌덩이 [ 이승진, 정현명 ]
        <!-- <a href="https:///www.invisionapp.com" target="_blank" rel="noopener"
          >Invision</a
        >. Coded by
        <a href="https://binarcode.com" target="_blank" rel="noopener"
          >BinarCode</a
        >
        and
        <a href="https://www.creative-tim.com" target="_blank" rel="noopener"
          >Creative Tim</a
        >. -->
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String,
  },
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style></style>
