/**
 * Created by Administrator on 2016/12/17
 */
Ext.define('OrientTdm.HomePage.Msg.MsgGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.msgGrid',
    requires: [
        'OrientTdm.HomePage.Msg.CwmMsg',
        'OrientTdm.HomePage.homePageShow.MsgFormPortal',
        'OrientTdm.CollabDev.Processing.AncestryAnalyze.FeedbackDashbord'
    ],
    config: {
        url: null,
        usePage: true
    },
    viewConfig: {
        stripeRows: true,
        autoFill: true,
        forceFit: true
    },
    initComponent: function () {
        var me = this;
        //当鼠标停留在标题列和内容列时，显示qtip
        me.columns = [{
            header: "标题", dataIndex: "title", width: 160, renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            }
        }, {
            header: "内容", dataIndex: "content", flex: 1, renderer: function (value, meta, record) {
                meta.tdAttr = 'data-qtip="' + value + '"';
                return value;
            }
        }, {
            header: "时间", dataIndex: "timestamp", width: 180, renderer: function (value) {
                return Ext.Date.format(new Date(value), "Y-m-d H:i:s");
            }
        }];

        me.store = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.HomePage.Msg.CwmMsg',
            proxy: {
                type: 'ajax',
                url: me.url,
                reader: {
                    type: 'json',
                    root: 'results',
                    idProperty: 'id',
                    totalProperty: 'total'
                }
            },
            autoLoad: true
        });

        me.selModel = Ext.create("Ext.selection.CheckboxModel", {
            mode: 'MULTI'
        });
        if (me.usePage) {
            me.bbar = Ext.create('Ext.PagingToolbar', {
                store: me.store,
                displayInfo: true,
                displayMsg: '{0} - {1} of {2}',
                emptyMsg: "没有数据"
            });
        }
        me.callParent();
        this.addEvents('refreshGrid');
    },
    initEvents: function () {
        var me = this;
        me.mon(me, 'celldblclick', me.celldblclick, me);
        me.mon(me, 'refreshGrid', me.refreshGrid, me);
    },
    getSelectedIds: function () {
        var me = this;
        var sels = this.getSelectionModel().getSelection();
        var ids = [];
        for (var i = 0; i < sels.length; i++) {
            var data = sels[i].raw;
            ids.push(data.id);
        }
        return ids;
    },
    refreshGrid: function () {
        this.getSelectionModel().clearSelections();
        var store = this.getStore();
        var lastOptions = store.lastOptions;
        store.reload(lastOptions);
    },
    celldblclick: function (view, td, cellIndex, record, tr, rowIndex) {
        var me = this;
        var msg = record.data;
        var type = msg.type;
        if (type == CwmMsg.TYPE_COLLAB_FEEDBACK) {
            me.feedback(msg);
        }
        else {
            me.showDetailMsgWin(msg);
        }
        me.markReaded([msg.id]);
    },
    feedback: function (msg) {
        var me = this;
        var data = JSON.parse(msg.data);
        var centerPanel = Ext.getCmp('orient-center');
        //下游反馈消息判重，多次点击同一条消息时，不需要重复创建下游数据面板。当前只显示一个下游反馈tab页
        var items = centerPanel.items;
        for (var i = items.length - 1; i > 0; i--) {
            if (items.items[i].id.indexOf('feedbackDashbord') != -1) {
                centerPanel.remove(items.items[i]);
            }
        }

        //获取上游任务名称
        var taskName = '';
        var params = {
            nodeId: data.toNodeId,
            nodeVersion: data.toVersion
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ancestryAnalyze/getTask.rdm', params, false, function (resp) {
            taskName = Ext.decode(resp.responseText);
        });

        //获取下游任务名称
        var fromTaskName = '';
        var fromParams = {
            nodeId: data.nodeId,
            nodeVersion: data.version
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/ancestryAnalyze/getTask.rdm', fromParams, false, function (resp) {
            fromTaskName = Ext.decode(resp.responseText);
        });

        var functionPanel = Ext.create('OrientTdm.CollabDev.Processing.AncestryAnalyze.FeedbackDashbord', {
            nodeId: data.toNodeId,
            version: data.toVersion,
            bmDataId: data.toBmDataId,
            type: data.toType,
            fromNodeId: data.nodeId,
            fromVersion: data.version,
            fromBmDataId: data.bmDataId,
            fromType: data.type,
            taskName: taskName,
            fromTaskName: fromTaskName
        });

        centerPanel.add(functionPanel);
        centerPanel.setActiveTab(functionPanel);
        me.up("window").close();
    },
    showDetailMsgWin: function (msg) {
        var me = this;
        var detailForm = Ext.create('OrientTdm.HomePage.Msg.MsgDetail', {
            msg: msg
        });
        OrientExtUtil.WindowHelper.createWindow(detailForm, {
            title: '消息详细',
            buttons: [
                {
                    text: '关闭',
                    iconCls: 'icon-close',
                    handler: function () {
                        this.up('window').close();
                        OrientExtUtil.HomeHelper.refreshNoticeMsgCount();
                        me.refreshMsgGridPortal();
                    }
                }
            ],
            listeners: {
                close: function () {

                }
            }
        }, 400, 600);
    },
    markReaded: function (ids) {
        var me = this;
        Ext.Ajax.request({
            url: serviceName + "/msg/markReaded.rdm",
            method: 'POST',
            params: {
                readed: true
            },
            jsonData: Ext.encode(ids),
            headers: {
                "Content-Type": "application/json;charset=UTF-8"
            },
            success: function (response, options) {
                me.refreshGrid();
                OrientExtUtil.HomeHelper.refreshNoticeMsgCount();
                me.refreshMsgGridPortal();
            },
            failure: function (result, request) {
                Ext.MessageBox.alert("错误", "消息标记出错");
            }
        });
    },
    refreshMsgGridPortal: function () { //刷新磁贴中的消息grid面板
        var msgGridPortal = Ext.ComponentQuery.query('msgGridPortal')[0];
        if (msgGridPortal) {
            msgGridPortal.fireEvent('refreshGrid');
        }
    }
});