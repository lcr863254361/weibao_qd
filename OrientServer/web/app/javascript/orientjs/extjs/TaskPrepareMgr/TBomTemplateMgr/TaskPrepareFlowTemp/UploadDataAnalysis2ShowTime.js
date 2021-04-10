Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.UploadDataAnalysis2ShowTime', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.uploadDataAnalysis2ShowTime',
    config:{
      data:''
    },
    html:'<div id=\'container\' style=\'width:1500px;height:600px\'></div>',
   // extend:'Ext.Container',

    initComponent :function (){
        var me = this;
        me.callParent(arguments);
    },
    afterRender :function(){
        Highcharts.setOptions({ global: { useUTC: false } });
            var start = +new Date();
            Highcharts.setOptions({ global: { useUTC: false } });
            Highcharts.stockChart('container', {
                chart: {
                    events: {
                        load: function () {
                            if (!window.isComparing) {
                                this.setTitle(null, {
                                    text: 'Built chart in ' + (new Date() - start) + 'ms'
                                });
                            }
                        }
                    },
                    zoomType: 'x'

                },
                tooltip: {
                    split: false,
                    shared: true,
                },
                loading:{

                },
                rangeSelector: {
                    buttons: [{
                        type: 'day',
                        count: 3,
                        text: '3d'
                    }, {
                        type: 'week',
                        count: 1,
                        text: '1w'
                    }, {
                        type: 'month',
                        count: 1,
                        text: '1m'
                    }, {
                        type: 'month',
                        count: 6,
                        text: '6m'
                    }, {
                        type: 'year',
                        count: 1,
                        text: '1y'
                    }, {
                        type: 'all',
                        text: 'All'
                    }],
                    selected: 3
                },
                yAxis: {
                    title: {
                        text: '数值'
                    }
                },
                title: {
                    text:  this.msg
                },
                subtitle: {
                    text: 'Built chart in ...' // dummy text to reserve space for dynamic subtitle
                },
                series: this.data
            });
     //   });

    },
    afterRender23 : function(){
        Highcharts.setOptions({ global: { useUTC: false } });
        var me = this;
        //$.getJSON('https://data.jianshukeji.com/jsonp?filename=json/usdeur.json&callback=?', function (data) {
            chart = Highcharts.chart('container', {
                chart: {
                    zoomType: 'x'
                },
                title: {
                    text: '数据折线图'
                },
                subtitle: {
                    text: document.ontouchstart === undefined ?
                        '鼠标拖动可以进行缩放' : '手势操作进行缩放'
                },

                tooltip: {
                    dateTimeLabelFormats: {
                        second: '%H:%M:%S',
                        minute: '%H:%M',
                        hour: '%H:%M',
                        day: '%Y-%m-%d',
                        week: '%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    }
                },
                xAxis: {
                    type: 'datetime',
                    dateTimeLabelFormats: {
                        millisecond: '%H:%M:%S.%L',
                        second: '%H:%M:%S',
                        minute: '%H:%M',
                        hour: '%H:%M',
                        day: '%m-%d',
                        week: '%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    }
                },
                yAxis: {
                    title: {
                        text: '数值'
                    }
                },
                legend: {
                    enabled: false
                },
                plotOptions: {
                    area: {
                        fillColor: {
                            linearGradient: {
                                x1: 0,
                                y1: 0,
                                x2: 0,
                                y2: 1,
                                x3: 0,
                                y3: 2
                            },
                            stops: [
                                [0, new Highcharts.getOptions().colors[0]],
                                [1, new Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                            ]
                        },
                        marker: {
                            radius: 2
                        },
                        lineWidth: 1,
                        states: {
                            hover: {
                                lineWidth: 1
                            }
                        },
                        threshold: null
                    }
                },
                series: this.data
          //  });
        });

        me.callParent(arguments);
    }
});