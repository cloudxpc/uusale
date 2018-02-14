<template>
  <div class="weui-cells" id="uploader">
    <div class="weui-cell">
      <div class="weui-cell__bd">
        <div class="weui-uploader">
          <div class="weui-uploader__hd">
            <p class="weui-uploader__title">图片上传</p>
            <div class="weui-uploader__info">{{uploadCount}}/{{maxUploadCount}}</div>
          </div>
          <div class="weui-uploader__bd">
            <ul class="weui-uploader__files" id="uploaderFiles" @click="preview">
              <li v-for="img in initialImageList"
                  :key="img.id"
                  class="weui-uploader__file"
                  :data-id="img.id"
                  :style="'background-image: url(\'' + $eventBus.baseUrl + '/static/' + img.name + '\');'"></li>
            </ul>
            <div class="weui-uploader__input-box">
              <input name="file" id="uploaderInput" class="weui-uploader__input" type="file" accept="image/*"
                     capture="camera" multiple=""/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';
  import $ from 'jquery';

  export default {
    name: 'Uploader',
    props: ['value'],
    data: function () {
      return {
        uploadCount: 0,
        maxUploadCount: 5,
        updatedImageList: [],
        initialImageList: []
      };
    },
    watch: {
      value: 'init'
    },
    computed: {
      imageNames: function () {
        return this.updatedImageList.map(il => il.name).concat(this.initialImageList.map(i => i.name));
      }
    },
    mounted: function () {
      let vm = this;
      weui.uploader('#uploader', {
        url: this.$axios.defaults.baseURL + '/upload',
        auto: true,
        type: 'file',
        fileVal: 'file',
        compress: {
          width: 1600,
          height: 1600,
          quality: .8
        },
        onBeforeQueued: function (files) {
          // console.log('onBeforeQueued');
          // `this` 是轮询到的文件, `files` 是所有文件

          if (["image/jpg", "image/jpeg", "image/png", "image/gif"].indexOf(this.type) < 0) {
            weui.alert('请上传图片');
            return false; // 阻止文件添加
          }
          if (this.size > 10 * 1024 * 1024) {
            weui.alert('请上传不超过10M的图片');
            return false;
          }
          if (files.length > vm.maxUploadCount) { // 防止一下子选择过多文件
            weui.alert('最多只能上传' + vm.maxUploadCount + '张图片，请重新选择');
            return false;
          }
          if (vm.uploadCount + 1 > vm.maxUploadCount) {
            weui.alert('最多只能上传' + vm.maxUploadCount + '张图片');
            return false;
          }

          vm.uploadCount++;

          // return true; // 阻止默认行为，不插入预览图的框架
        },
        onQueued: function () {
          // console.log('onQueued');
          // console.log(this);

          // console.log(this.status); // 文件的状态：'ready', 'progress', 'success', 'fail'
          // console.log(this.base64); // 如果是base64上传，file.base64可以获得文件的base64

          // this.upload(); // 如果是手动上传，这里可以通过调用upload来实现；也可以用它来实现重传。
          // this.stop(); // 中断上传

          // return true; // 阻止默认行为，不显示预览图的图像
        },
        onBeforeSend: function (data, headers) {
          // console.log('onBeforeSend');
          // console.log(this, data, headers);
          // $.extend(data, { test: 1 }); // 可以扩展此对象来控制上传参数
          // $.extend(headers, { Origin: 'http://127.0.0.1' }); // 可以扩展此对象来控制上传头部

          $.extend(headers, {Authorization: vm.$axios.defaults.headers.common['Authorization']});

          // return false; // 阻止文件上传
        },
        onProgress: function (procent) {
          // console.log('onProgress');
          // console.log(this, procent);
          // return true; // 阻止默认行为，不使用默认的进度显示
        },
        onSuccess: function (ret) {
          // console.log('onSuccess');
          // console.log(this, ret);

          vm.updatedImageList.push({id: this.id, name: ret});
          vm.$emit('change', vm.imageNames);
          // return true; // 阻止默认行为，不使用默认的成功态
        },
        onError: function (err) {
          // console.log('onError');
          console.log(this, err);
          // return true; // 阻止默认行为，不使用默认的失败态
        }
      });
    },
    methods: {
      removeImageElements: function () {
        $('#uploaderFiles').children().each(function () {
          if (typeof $(this).data('id') === 'number')
            $(this).remove();
        });
      },
      init: function (v) {
        this.uploadCount = 0;
        this.updatedImageList = [];
        this.initialImageList = [];
        this.removeImageElements();

        if (v && v.length) {
          for (let i = 0; i < v.length; i++) {
            this.initialImageList.push({id: v[i], name: v[i]});
          }
          this.uploadCount = this.initialImageList.length;
        }
      },
      preview: function (e) {
        let target = e.target;
        while (target && !target.classList.contains('weui-uploader__file')) {
          target = target.parentNode;
        }
        if (!target) return;
        let url = target.getAttribute('style');
        if (url)
          url = url.match(/url\((.*?)\)/)[1].replace(/"/g, '');
        let id = target.getAttribute('data-id');

        let gallery = weui.gallery(url, {
          onDelete: () => {
            weui.confirm('确定删除该图片？', () => {
              this.uploadCount--;
              for (let i = 0; i < this.updatedImageList.length; i++) {
                if (this.updatedImageList[i].id.toString() === id) {
                  this.updatedImageList.splice(i, 1);
                  break;
                }
              }
              for (let i = 0; i < this.initialImageList.length; i++) {
                if (this.initialImageList[i].id.toString() === id) {
                  this.initialImageList.splice(i, 1);
                  break;
                }
              }
              target.remove();
              gallery.hide();

              this.$emit('change', this.imageNames);
            });
          }
        });
      }
    }
  }
</script>
