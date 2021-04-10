Ext.define('OrientTdm.TaskPrepareMgr.TBomTemplateMgr.TaskPrepareFlowTemp.UploadDataAnalysis2', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.uploadDataAnalysis2',
    config:{
      data:'',
        mag:''
    },
    html:'<div id=\'container\' style=\'width:1500px;height:600px\'></div>',
   // extend:'Ext.Container',

    initComponent :function (){
        var me = this;

        me.callParent(arguments);
    },
    afterRender : function(){
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
                    text: this.msg
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
                                y2: 1
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