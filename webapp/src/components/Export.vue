<template>
    <div>
        <h1 class="page_header">历史订单导出</h1>
        <div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">开始</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="datetime-local" v-model="from">
                </div>
                <div class="weui-cell__ft"></div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label">结束</label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" type="datetime-local" v-model="to">
                </div>
                <div class="weui-cell__ft"></div>
            </div>
        </div>
        <div class="weui-btn-area">
            <button type="button" class="weui-btn weui-btn_primary" @click.prevent="submit">下载</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Export',
        data: function () {
            return {
                from: '',
                to: ''
            };
        },
        methods: {
            format: function(dte) {
                var fmt = "yyyyMMddhhmmss";
                var o = {
                    "M+": dte.getMonth() + 1, //月份 
                    "d+": dte.getDate(), //日 
                    "h+": dte.getHours(), //小时 
                    "m+": dte.getMinutes(), //分 
                    "s+": dte.getSeconds(), //秒 
                    "q+": Math.floor((dte.getMonth() + 3) / 3), //季度 
                    "S": dte.getMilliseconds() //毫秒 
                };
                if (/(y+)/.test(fmt))
                    fmt = fmt.replace(RegExp.$1, (dte.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o) {
                    if (new RegExp("(" + k + ")").test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                    }
                }
                return fmt;
            },
            submit: function () {
                window.location.href = this.$eventBus.baseUrl + "/report/" + this.format(new Date(this.from)) + "/" + this.format(new Date(this.to));
            }
        }
    }
</script>
