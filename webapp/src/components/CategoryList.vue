<template>
  <div>
    <h1 class="page_header">商品分类列表</h1>
    <div class="weui-cells">
      <div class="weui-cell weui-cell_vcode">
        <div class="weui-cell__hd">
          <label class="weui-label">分类名称</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入分类名称" v-model="current.name">
        </div>
        <div class="weui-cell__ft">
          <button type="button" class="weui-vcode-btn" @click="add">{{buttonText}}</button>
          <button type="button" class="weui-vcode-btn" @click="reset" style="color: #707070; margin-left: 0;" v-if="current.id">取消</button>
        </div>
      </div>
    </div>
    <div class="weui-cells__title">所有分类</div>
    <div class="weui-cells">
      <div class="weui-cell weui-cell_vcode" v-for="item in items">
        <div class="weui-cell__bd">
          <p>{{item.name}}</p>
        </div>
        <div class="weui-cell__ft">
          <button type="button" class="weui-vcode-btn" @click="edit(item)" style="color: #707070;">编辑</button>
          <button type="button" class="weui-vcode-btn" @click="remove(item.id)" style="color: #E64340; margin-left: 0;">删除</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'CategoryList',
    data: function () {
      return {
        items: [],
        current: {},
        buttonText: '添加'
      };
    },
    methods: {
      init: function () {
        this.$axios.get('/category/list').then(response => {
          if (response && response.data) {
            this.items = response.data;
            this.reset();
          }
        });
      },
      remove: function (id) {
        this.$eventBus.confirm('确认删除?', () => {
          this.$axios.post('/category/delete', {id: id}).then(response => {
            if (response && response.status === 200) {
              this.init();
            }
          });
        });
      },
      add: function () {
        this.$axios.post('/category/save', this.current).then(response => {
          if (response && response.status === 200) {
            this.init();
          }
        });
      },
      edit: function (item) {
        this.current = {id: item.id, name: item.name};
        this.buttonText = '保存';
      },
      reset: function () {
        this.current = {};
        this.buttonText = '添加';
      }
    },
    mounted: function () {
      this.init();
    }
  }
</script>
