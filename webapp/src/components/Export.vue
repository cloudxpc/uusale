<template>
  <div>
    <h1 class="page_header">历史订单导出</h1>
    <div class="weui-cells__title">开始日期和时间</div>
    <div class="weui-cells">
      <a class="weui-cell weui-cell_access" @click="selectDate('from')">
        <div class="weui-cell__bd">
          <p>开始日期</p>
        </div>
        <div class="weui-cell__ft">{{fromDateStr ? fromDateStr : '请选择'}}</div>
      </a>
      <a class="weui-cell weui-cell_access" @click="selectTime('from')">
        <div class="weui-cell__bd">
          <p>开始时间</p>
        </div>
        <div class="weui-cell__ft">{{fromTimeStr ? fromTimeStr : '请选择'}}</div>
      </a>
    </div>
    <div class="weui-cells__title">结束日期和时间</div>
    <div class="weui-cells">
      <a class="weui-cell weui-cell_access" @click="selectDate('to')">
        <div class="weui-cell__bd">
          <p>结束日期</p>
        </div>
        <div class="weui-cell__ft">{{toDateStr ? toDateStr : '请选择'}}</div>
      </a>
      <a class="weui-cell weui-cell_access" @click="selectTime('to')">
        <div class="weui-cell__bd">
          <p>结束时间</p>
        </div>
        <div class="weui-cell__ft">{{toTimeStr ? toTimeStr : '请选择'}}</div>
      </a>
    </div>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_primary" @click.prevent="submit">下载</button>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';

  export default {
    name: 'Export',
    data: function () {
      return {
        fromDate: '',
        fromDateStr: '',
        fromTime: '',
        fromTimeStr: '',
        toDate: '',
        toDateStr: '',
        toTime: '',
        toTimeStr: ''
      };
    },
    methods: {
      formatNumber: function(n) {
        n = n.toString()
        return n[1] ? n : '0' + n
      },
      selectDate: function (fromto) {
        var today = new Date();
        weui.datePicker({
          start: 2018,
          end: today,
          defaultValue: [today.getFullYear(), today.getMonth() + 1, today.getDate()],
          onConfirm: (result) => {
            if (fromto === 'from') {
              this.fromDate = result.map(a => this.formatNumber(a.value)).join('');
              this.fromDateStr = result.map(a => this.formatNumber(a.label)).join('');
            } else {
              this.toDate = result.map(a => this.formatNumber(a.value)).join('');
              this.toDateStr = result.map(a => this.formatNumber(a.label)).join('');
            }
          },
          id: 'datePicker' + fromto
        });
      },
      selectTime: function (fromto) {
        var hours = [];
        var minutes = [];
        for (var i = 0; i < 24; i++) {
          hours.push({
            label: i + '点',
            value: i
          });
        }
        for (var j = 0; j < 60; j++) {
          minutes.push({
            label: j + '分',
            value: j
          });
        }
        weui.picker(hours, minutes, {
          defaultValue: [0, 0],
          onConfirm: (result) => {
            if (fromto === 'from') {
              this.fromTime = result.map(a => this.formatNumber(a.value)).join('');
              this.fromTimeStr = result.map(a => this.formatNumber(a.label)).join('');
            } else {
              this.toTime = result.map(a => this.formatNumber(a.value)).join('');
              this.toTimeStr = result.map(a => this.formatNumber(a.label)).join('');
            }
          },
          id: 'timePicker' + fromto
        });
      },
      submit: function () {
        if (this.fromDate && this.toDate)
          window.location.href = this.$eventBus.baseUrl + "/report/" + this.fromDate + this.fromTime + '00' + "/" + this.toDate + this.toTime + '00';
      }
    }
  }
</script>
