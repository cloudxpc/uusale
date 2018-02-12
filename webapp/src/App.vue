<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created: function () {
    this.$axios.get('/');
  }
}
</script>

<style>

</style>
