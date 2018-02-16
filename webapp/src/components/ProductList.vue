<template>
  <div>
    <h1 class="page_header">商品列表</h1>
    <div class="weui-panel">
      <div class="weui-search-bar" id="searchBar">
        <form class="weui-search-bar__form">
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
        </form>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel" @click="searchText=''">取消</a>
      </div>
      <div v-if="$eventBus.isMch" class="weui-panel__hd">
        <router-link to="/main/product/edit/new" class="weui-btn weui-btn_mini weui-btn_primary">添加商品</router-link>
        <span style="flex: 1;"></span>
        <button type="button" class="weui-btn weui-btn_mini weui-btn_primary" @click="edit = !edit">{{edit ? '完成' : '编辑'}}</button>
      </div>
      <div class="weui-panel__bd">
        <div v-for="item in filteredProducts" :key="item.id" class="weui-media-box weui-media-box_appmsg">
          <router-link :to="'/main/product/' + ($eventBus.isMch ? 'edit' : 'view') + '/' + item.id" class="weui-media-box weui-media-box_appmsg product-link">
            <div class="weui-media-box__hd">
              <img class="weui-media-box__thumb" :src="getProductFirstImgSrc(item)" alt="">
            </div>
            <div class="weui-media-box__bd">
              <h4 class="weui-media-box__title">{{item.name}}</h4>
              <p class="weui-media-box__desc">{{item.description}}</p>
            </div>
            <span>{{item.price | currency}}</span>
          </router-link>
          <div class="weui-media-box__ft" v-if="!$eventBus.isMch">
            <button type="button" class="num-btn" @click="$cart.remove(item)">-</button>
            <span class="num">{{$cart.count(item)}}</span>
            <button type="button" class="num-btn" @click="$cart.add(item)">+</button>
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

  export default {
    name: 'ProductList',
    data: function () {
      return {
        products: [],
        searchText: '',
        edit: false
      };
    },
    computed: {
      filteredProducts: function () {
        return this.searchText ? this.$_.filter(this.products, (p) => p.name.indexOf(this.searchText) >= 0) : this.products;
      }
    },
    created: function () {
      this.init();
    },
    mounted: function () {
      weui.searchBar('#searchBar');
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
            if(response && response.status === 200) {
              for(let i = 0; i < this.products.length; i++){
                if (this.products[i].id === product.id){
                  this.products.splice(i, 1);
                  break;
                }
              }
              this.$eventBus.toast('商品已删除');
            }
          });
        });
      }
    }
  }
</script>

<style scoped>
  .product-link {
    flex: 1;
    padding: 0;
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
    height: 25px;
    width: 25px;
    padding: 0;
    border: 1px solid #888888;
    border-radius: 50%;
    line-height: 0;
    color: #888888;
    background-color: transparent;
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
</style>
