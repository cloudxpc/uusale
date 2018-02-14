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
      <router-link :to="'/product-price/' + id" class="weui-cell weui-cell_link">
        <div class="weui-cell__bd">查看更多历史价格</div>
      </router-link>
    </div>
    <div class="weui-cells__title">商品描述</div>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__bd">
          <textarea class="weui-textarea" placeholder="请输入商品描述" rows="3" v-model="product.description"></textarea>
          <div class="weui-textarea-counter"><span>{{descNum}}</span>/100</div>
        </div>
        <div v-if="validation.description" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
    </div>
    <div class="weui-cells__title">商品图片</div>
    <uploader :value="product.images" @change="imageListChanged"></uploader>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_primary" @click="submit">提交</button>
    </div>
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
        updatedImageList: [],
        product: {
          id: null,
          name: null,
          description: null,
          price: null,
          images: []
        },
        validation: {
          name: false,
          description: false,
          price: false
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
      reset: function () {
        this.product = {
          id: null,
          name: null,
          description: null,
          price: null,
          images: []
        };
        this.validation = {
          name: false,
          description: false,
          price: false
        };
        this.updatedImageList = [];
      },
      init: function () {

        this.reset();

        if (!this.isNew) {
          this.$axios.get('/product?id=' + this.id).then(response => {
            if (response && response.data) {
              this.product = response.data;
              this.updatedImageList = this.product.images;
            }
          });
        }
      },
      imageListChanged: function (v) {
        this.updatedImageList = v;
      },
      validate: function () {
        this.validation.name = !this.product.name;
        this.validation.price = !this.priceAmt;
        this.validation.description = this.descNum > 100;

        return !this.validation.name
          && !this.validation.price
          && !this.validation.description;
      },
      submit: function () {
        if (!this.validate())
          return;

        this.product.price = this.priceAmt;
        this.product.images = this.updatedImageList;

        this.$axios.post('/product/save', this.product).then(response => {
          if (response && response.data) {
            this.$eventBus.alert('保存成功', () => {
              this.$router.push('/product/' + this.mode + '/' + response.data);
            });
          }
        });
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
