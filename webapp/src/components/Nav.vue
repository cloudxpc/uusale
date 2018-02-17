<template>
  <div class="nav">
    <a @click="$router.go(-1)">返回</a>
  </div>
</template>

<script>
  export default {
    name: 'Nav'
  }
</script>

<style scoped>
  .nav {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    padding: 10px 15px;
    margin: 0;
    height: 25px;
    border-bottom: 1px solid #c0c0c0;
    background-color: #fcfcfc;
    display: flex;
    align-items: center;
    z-index: 99;
  }

  .nav a {
    color: #999999;
    margin-left: 10px;
  }

  .nav a:active {
    background-color: #dadada;
  }

  .nav a:before {
    content: "";
    position: absolute;
    top: 18px;
    left: 15px;
    margin: 0px;
    border-left: 2px solid #999999;
    border-bottom: 2px solid #999999;
    width: 8px;
    height: 8px;
    transform: rotate(45deg);
    -o-transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
  }
</style>
