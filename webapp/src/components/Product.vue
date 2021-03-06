<template>
  <div>
    <h1 class="page_header">{{pageTitle}}</h1>
    <div v-if="isEditMode">
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
        <router-link v-if="!isNew" :to="'/main/product-price/' + id" class="weui-cell weui-cell_link">
          <div class="weui-cell__bd">查看更多历史价格</div>
        </router-link>
      </div>
      <div class="weui-cells__title">商品分类</div>
      <div class="weui-cells">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <categoritor ref="cat" v-model="product.categoryId" edit="true"></categoritor>
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
          <div v-if="validation.description" class="weui-cell__ft">
            <i class="weui-icon-warn"></i>
          </div>
        </div>
      </div>
      <div class="weui-cells__title">商品图片</div>
      <!--<uploader :value="product.images" @change="imageListChanged"></uploader>-->
      <uploader
        title="图片上传"
        :value="product.images"
        accessKeyId="LTAIcuIgAwMEpJrR"
        accessKeySecret="29XynWQrXqQ4HZhAu9acj03oYZEH25"
        :uploadUrl="$eventBus.imgBaseUrl"
        :imgBaseUrl="$eventBus.imgBaseUrl"
        filenameUrl="/upload/getfilename"
        @change="imageListChanged"></uploader>
      <div class="weui-btn-area">
        <button type="button" class="weui-btn weui-btn_primary" @click="submit">提交</button>
        <button type="button" class="weui-btn weui-btn_plain-primary" @click="shelve" v-if="!isNew">{{shelveText}}</button>
        <button type="button" class="weui-btn weui-btn_warn" @click="remove" v-if="!isNew">删除</button>
      </div>
    </div>
    <article class="weui-article" v-else>
      <h1>{{product.name}}</h1>
      <section>
        <h2 class="title">
          {{product.price | currency}}
          <span class="link">
            (<router-link :to="'/main/product-price/' + id" class="weui-cell_link">查看更多历史价格</router-link>)
          </span>
        </h2>
        <section>
          <p>{{product.description}}</p>
          <br/>
          <p>
            <img v-for="img in product.images" :src="$eventBus.imgBaseUrl + img" alt="">
          </p>
        </section>
      </section>
    </article>
  </div>
</template>

<script>
  import Uploader from "./AliOssUploader";
  import Categoritor from "./Categoritor";

  export default {
    components: {Uploader, Categoritor},
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
          state: null,
          images: [],
          categoryId: null
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
      },
      shelveText: function () {
        return this.product.state === 'A' ? '下架' : '上架';
      }
    },
    methods: {
      reset: function () {
        this.product = {
          id: null,
          name: null,
          description: null,
          price: null,
          state: null,
          images: [],
          categoryId: null
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
              if (this.isEditMode) {
                this.$refs.cat.init();
              }
            }
          });
        } else {
          this.$refs.cat.init();
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
              this.$router.replace('/main/product/' + this.mode + '/' + response.data);
            });
          }
        });
      },
      remove: function () {
        this.$eventBus.confirm('确认删除?', () => {
          this.$axios.post('/product/delete', this.product).then(response => {
            if(response && response.status === 200) {
              this.$router.go(-1);
              this.$eventBus.toast('商品已删除');
            }
          });
        });
      },
      shelve: function () {
        this.$eventBus.confirm('确认将商品' + this.shelveText + '?', () => {
          this.$axios.get('/product/shelve?id=' + this.id).then(response => {
            if(response && response.status === 200) {
              this.$eventBus.toast('商品已' + this.shelveText);
              this.init();
            }
          });
        });
      }
    },
    watch: {
      '$route': 'init'
    },
    mounted: function () {
      this.init();
    }
  }
</script>

<style scoped>
  .link {
    margin-left: 15px;
    font-size: 14px;
  }

  .link:active {
    background-color: #d5d5d5;
  }
</style>
