<template>
  <div>
    <h1 class="page_header">商品列表</h1>
    <div class="weui-panel">
      <div class="weui-search-bar" id="searchBar">
        <div class="weui-search-bar__form">
          <div class="weui-search-bar__box">
            <i class="weui-icon-search"></i>
            <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required=""
                   v-model="searchText">
            <a href="javascript:" class="weui-icon-clear" id="searchClear" @click="searchText=''"></a>
          </div>
          <label class="weui-search-bar__label" id="searchText"
                 style="transform-origin: 0px 0px; opacity: 1; transform: scale(1, 1);">
            <i class="weui-icon-search"></i>
            <span>搜索</span>
          </label>
        </div>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel" @click="searchText=''">取消</a>
      </div>
      <div class="weui-panel__hd">
        <categoritor ref="cat" v-model="categoryId" :edit="$eventBus.isMch"></categoritor>
      </div>
      <div v-if="$eventBus.isMch" class="weui-panel__hd">
        <router-link to="/main/product/edit/new" class="weui-btn weui-btn_mini weui-btn_primary">添加商品</router-link>
        <!--<span style="flex: 1;"></span>-->
        <!--<button type="button" class="weui-btn weui-btn_mini weui-btn_primary" @click="edit = !edit">{{edit ? '完成' : '编辑'}}</button>-->
      </div>
      <div class="weui-panel__bd">
        <div v-for="item in filteredProducts" :key="item.id" class="weui-media-box weui-media-box_appmsg">
          <router-link :to="'/main/product/' + ($eventBus.isMch ? 'edit' : 'view') + '/' + item.id"
                       class="weui-media-box weui-media-box_appmsg product-link">
            <div class="weui-media-box__hd">
              <img class="weui-media-box__thumb" :src="getProductFirstImgSrc(item)" alt="">
            </div>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">{{item.name}}<span v-if="item.state === 'U'"
                                                                   class="shelve">(该商品已下架)</span></h4>
              <p class="weui-media-box__desc">{{item.description}}</p>
            </div>
            <span>{{item.price | currency}}</span>
          </router-link>
          <div class="weui-media-box__ft" v-if="!$eventBus.isMch">
            <!--<button type="button" class="num-btn" @click="$cart.remove(item)" v-show="$cart.count(item) > 0"></button>-->
            <!--<span class="num" v-show="$cart.count(item) > 0">{{$cart.count(item)}}</span>-->
            <button type="button" class="num-btn" @click="addToCart(item)"></button>
          </div>
          <div class="weui-media-box__ft" v-if="edit">
            <button type="button" class="weui-btn weui-btn_mini weui-btn_warn" @click="removeProduct(item)">删除</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';
  import Categoritor from "./Categoritor";

  export default {
    components: {Categoritor},
    name: 'ProductList',
    data: function () {
      return {
        products: [],
        searchText: '',
        edit: false,
        categoryId: null
      };
    },
    computed: {
      filteredProducts: function () {
        var items = this.products;
        if (this.categoryId) {
          items = this.$_.filter(items, (p) => p.categoryId === this.categoryId);
        }
        if (this.searchText) {
          items = this.$_.filter(items, (p) => p.name.indexOf(this.searchText) >= 0);
        }
        return items;
      }
    },
    created: function () {
      this.init();
    },
    mounted: function () {
      weui.searchBar('#searchBar');
      this.$refs.cat.init();
    },
    methods: {
      init: function () {
        let url = this.$eventBus.isMch ? '/product/mch/list' : '/product/list';
        this.$axios.get(url).then(response => {
          if (response && response.data) {
            this.products = response.data;
          }
        });
      },
      getProductFirstImgSrc: function (product) {
        if (product && product.images && product.images.length) {
          return this.$eventBus.imgBaseUrl + this.$_.head(product.images);
        } else {
          return '';
        }
      },
      removeProduct: function (product) {
        this.$eventBus.confirm('确认删除' + product.name + '?', () => {
          this.$axios.post('/product/delete', product).then(response => {
            if (response && response.status === 200) {
              for (let i = 0; i < this.products.length; i++) {
                if (this.products[i].id === product.id) {
                  this.products.splice(i, 1);
                  break;
                }
              }
              this.$eventBus.toast('商品已删除');
            }
          });
        });
      },
      addToCart: function (product) {
        this.$cart.addToCart(product);
        weui.toast('已添加至购物车', 500);
      }
    }
  }
</script>

<style scoped>
  .product-link {
    flex: 1;
    padding: 0;
    min-width: 0;
  }

  .weui-panel__hd, .weui-media-box__hd {
    display: flex;
  }

  .weui-media-box__ft {
    display: flex;
    align-items: center;
    padding-left: 15px;
  }

  .weui-media-box__ft .num-btn {
    position: relative;
    height: 25px;
    width: 25px;
    padding: 0;
    border: 1px solid #888888;
    box-sizing: border-box;
    border-radius: 50%;
    line-height: 0;
    color: #888888;
    background-color: transparent;
    outline: none;
  }

  .num-btn:before {
    content: '';
    position: absolute;
    width: 10px;
    height: 2px;
    left: 50%;
    top: 50%;
    margin-left: -5px;
    margin-top: -1px;
    background-color: #888888;
  }

  .num-btn:after {
    content: '';
    position: absolute;
    width: 2px;
    height: 10px;
    left: 50%;
    top: 50%;
    margin-left: -1px;
    margin-top: -5px;
    background-color: #888888;
  }

  .weui-media-box__ft .num-btn:active {
    background-color: #c0c0c0;
  }

  .weui-media-box__ft .num {
    width: 20px;
    padding-left: 5px;
    padding-right: 5px;
    text-align: center;
  }

  .weui-btn {
    margin: 0 !important;
  }

  .shelve {
    padding-left: 10px;
    color: #999999;
    font-size: 14px;
  }
</style>
