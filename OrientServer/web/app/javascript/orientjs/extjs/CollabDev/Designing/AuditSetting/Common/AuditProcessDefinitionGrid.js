/**
 * Created by enjoy on 2016/4/6 0006.
 */
Ext.define('OrientTdm.CollabDev.Designing.AuditSetting.Common.AuditProcessDefinitionGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.auditProcessDefinitionGrid',
    requires: [
        'OrientTdm.BackgroundMgr.AuditFlowBind.Model.AuditProcessDefinitionExtModel'
    ],
    beforeInitComponent: Ext.emptyFn,
    afterInitComponent: Ext.emptyFn,
    usePage: false,
    config: {
        nodeId: '',
        saveCallBack: Ext.emptyFn
    },
    //视图初始化
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            iconCls: 'icon-save',
            text: '保存',
            itemId: 'save',
            scope: this,
            handler: me._saveBind
        }, {
            iconCls: 'icon-close',
            text: '关闭',
            disabled: false,
            itemId: 'close',
            scope: this,
            handler: function () {
                me.up('window').close();
            }
        }];
        return retVal;
    },
    createColumns: function () {
        return [
            {
                header: '流程名称',
                flex: 1,
                sortable: true,
                dataIndex: 'flowName',
                filter: {
                    type: 'string'
                }
            },
            {
                header: '流程版本',
                sortable: true,
                dataIndex: 'flowVersion',
                filter: {
                    type: 'string'
                }
            }
        ];
    },
    createStore: function () {
        var me = this;
        var retVal = Ext.create('Ext.data.Store', {
            model: 'OrientTdm.BackgroundMgr.AuditFlowBind.Model.AuditProcessDefinitionExtModel',
            autoLoad: true,
            proxy: {
                type: 'ajax',
                api: {
                    "read": serviceName + '/auditSettings/listAuditPds.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                },
                extraParams: {
                    nodeId: me.nodeId
                }
            }
        });
        this.store = retVal;
        return retVal;
    },
    _saveBind: function () {
        var me = this;
        //弹出备注输入框
        var toSaveData = [];
        var selectedRecords = OrientExtUtil.GridHelper.getSelectedRecord(me);
        if (selectedRecords.length > 0) {
            Ext.each(selectedRecords, function (record) {
                toSaveData.push({
                    settingsTarget: me.nodeId,
                    pdName: record.get('flowName'),
                    pdVersion: record.get('flowVersion')
                });
            });
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/auditSettings/createMulti.rdm', toSaveData, true, function () {
                if (me.saveCallBack) {
                    me.saveCallBack.call(me);
                }
                me.up('window').close();
            }, true, me);
        }
    }
});