<template>
  <div class="category">
    <a href="javascript:;" class="category-item" :class="{active: !value}" @click="itemClick(null)">默认分类</a>
    <a v-for="item in items" href="javascript:;" class="category-item" :class="{active:item.id === value}" @click="itemClick(item.id)">{{item.name}}</a>
    <router-link v-if="edit" :to="'/main/categories'" class="category-item-manage">管理</router-link>
  </div>
</template>

<script>
  export default {
    name: 'Categoritor',
    props: ['value', 'edit'],
    data: function () {
      return {
        items:[]
      };
    },
    methods: {
      itemClick: function (id) {
        this.$emit('input', id);
      },
      init: function () {
        this.$axios.get('/category/list').then(response => {
          if (response && response.data) {
            this.items = response.data;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .category {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-start;
    flex-wrap: wrap;
  }

  .category-item {
    text-decoration: none;
    padding: 2px 10px;
    margin: 2px 5px 2px 0;
    border: 1px solid #1AAD19;
    border-radius: 15px;
    color: #1AAD19;
  }

  .category-item.active{
    color: #ffffff;
    background-color: #1AAD19;
  }

  .category-item-manage {
    border: 0;
    color: #586C94;
    text-decoration: underline;
    padding: 2px 10px;
    margin: 2px 5px 2px 0;
  }

  .category-item-manage:active{
    color: #E64340;
  }
</style>
