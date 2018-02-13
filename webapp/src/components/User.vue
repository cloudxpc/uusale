<template>
  <div>
    <h1 class="page_header">用户首页</h1>
    <button type="button" class="weui-btn" @click="loadProductList">ok</button>
  </div>
</template>

<script>
  export default {
    name: 'User',
    methods: {
      loadProductList: function () {
        this.$axios.get('/product/list').then(response => {
          console.log(response);
        });
      }
    }
  }
</script>
