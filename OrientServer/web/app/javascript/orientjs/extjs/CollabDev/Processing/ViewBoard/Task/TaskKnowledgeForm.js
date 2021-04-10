/**
 * Created by ZhangSheng on 2018/8/28.
 */
Ext.define('OrientTdm.CollabDev.Processing.ViewBoard.Task.TaskKnowledgeForm', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.taskKnowledgeForm',
    minHeight: 350,
    config: {},
    initComponent: function () {
        me = this;
        //获取屏幕分辨率，设置需要显示的知识推荐的卡片个数
        var windowWidth = Ext.getBody().getWidth();
        var cols = 3;
        if (windowWidth >= 1440) {
            cols = 6;
        }
        else if (1080 <= windowWidth && windowWidth < 1440) {
            cols = 4;
        }
        else if (windowWidth < 1080) {
            cols = 3;
        }
        //var bodyHeight = Ext.getBody().getHeight();
        Ext.apply(me, {
            html: '<iframe width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" src = "' + serviceName +
            '/app/javascript/orientjs/extjs/BackgroundMgr/CustomForm/Common/cardLayout.jsp?knowledgeCnt=' + cols + '"></iframe>',
            layout: 'fit',
            modal: true
        });

        this.callParent(arguments);
    },
    initEvents: function () {
        var me = this;
        me.callParent();
    }
});