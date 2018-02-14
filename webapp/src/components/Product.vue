<template>
  <div>
    <h1 class="page_header">{{pageTitle}}</h1>
    <div class="weui-cells__title">商品信息</div>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">名称</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入商品名称" v-model="product.name">
        </div>
        <div v-if="validation.name" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">价格</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="number" placeholder="请输入商品价格" v-model="product.price">
        </div>
        <div v-if="validation.price" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
    </div>
    <div class="weui-cells__title">商品描述</div>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__bd">
          <textarea class="weui-textarea" placeholder="请输入商品描述" rows="3" v-model="product.description"></textarea>
          <div class="weui-textarea-counter"><span>{{descNum}}</span>/100</div>
        </div>
      </div>
    </div>
    <div class="weui-cells__title">商品图片</div>
    <uploader></uploader>
    <img src="../assets/logo.png"/>
  </div>
</template>

<script>
  import Uploader from "./Uploader";

  export default {
    components: {Uploader},
    name: 'Product',
    props: ['id', 'mode'],
    data: function () {
      return {
        product: {
          id: null,
          name: null,
          description: null,
          price: null,
          images: []
        },
        validation: {
          id: null,
          name: null,
          description: null,
          price: null,
          images: []
        }
      };
    },
    computed: {
      isEditMode: function () {
        return this.mode === 'edit';
      },
      isNew: function () {
        return this.isEditMode && this.id === 'new';
      },
      pageTitle: function () {
        return this.isEditMode ? (this.isNew ? '添加商品' : '编辑商品') : '商品详情';
      },
      descNum: function () {
        return this.product.description ? this.product.description.length : 0;
      },
      priceAmt: function () {
        return this.$amount(this.product.price);
      },
      priceCurrency: function () {
        return this.$currency(this.product.price);
      }
    },
    methods: {
      init: function () {

      }
    },
    watch: {
      '$route': 'init'
    },
    created: function () {
      this.init();
    }
  }
</script>
