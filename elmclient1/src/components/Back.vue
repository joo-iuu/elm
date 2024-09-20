<template>
  <ul class="goback">
    <li @click="goBack">
      <i class="fa fa-arrow-left"></i>
    </li>
  </ul>



</template>

<script>
export default {
  name: 'goBack',
  methods: {
    goBack() {
      this.$router.go(-1);
    }
  }
}

</script>

<style>
.wrapper .back li.parent{
float: left;
  z-index: 9999;

  top:0;
  color:#fff;
}
</style>