/**
 * Created by Administrator on 2016/8/24 0024.
 */
Ext.define('OrientTdm.Collab.common.auditFlow.StartAuditFlowPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.startAuditFlowPanel',
    requires: [
        'OrientTdm.Collab.common.auditFlow.ChooseAuditAssignGraphPanel'
    ],
    config: {
        modelId: '',
        dataIds: [],
        successCallback: Ext.emptyFn
    },
    initComponent: function () {
        var me = this;
        //选择审批模板
        var selectPdCombobox = me._createSelectPdCombobox();
        //发起流程
        Ext.apply(me, {
            items: [selectPdCombobox],
            buttons: [
                {
                    text: '启动流程',
                    handler: me._startAuditFLow,
                    scope: me,
                    iconCls: 'icon-startFlow'
                }
            ]
        });
        this.callParent(arguments);
    },
    _createSelectPdCombobox: function () {
        var me = this;
        var combobox = Ext.create('OrientTdm.Common.Extend.Form.Field.OrientComboBox', {
            initFirstRecord: true,
            remoteUrl: serviceName + '/AuditFlowModelBind/getModelBindPds.rdm?modelId=' + me.modelId,
            anchor: '100%',
            name: 'pdId',
            fieldLabel: '选择流程定义',
            listeners: {
                select: me._createChooseTaskAssignGrid,
                scope: me
            }
        });
        var retVal = {
            xtype: 'fieldset',
            border: '1 1 1 1',
            collapsible: true,
            title: '第一步（选择审批流程）',
            items: [
                combobox
            ]
        };
        return retVal;
    },
    _createChooseTaskAssignGrid: function (combo, records) {
        var me = this;
        var selectData = records;
        if (Ext.isArray(records)) {
            selectData = records[0];
        }
        //初始化选人表格
        me.remove(me.down('#setAssignFieldSet'));
        var chooseAuditAssignPanel = Ext.create('OrientTdm.Collab.common.auditFlow.ChooseAuditAssignGraphPanel', {
            bindId: selectData.get('id'),
            pdId: selectData.get('value')
        });
        var fieldSet = {
            xtype: 'fieldset',
            border: '1 1 1 1',
            itemId: 'setAssignFieldSet',
            collapsible: true,
            title: '第二步（设置人员）',
            items: [
                chooseAuditAssignPanel
            ]
        };
        me.add(fieldSet);

    },
    _startAuditFLow: function () {
        var me = this;
        //判断是否选择流程定义
        var pdId = me.down('orientComboBox').getRawValue();
        if (Ext.isEmpty(pdId)) {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.unBindPd);
        } else {
            var assigners = me.down('chooseAuditAssignGraphPanel').getAssignInfos();
            var bindDatas = [];
            var taskUserAssigns = {};
            Ext.each(me.dataIds, function (dataId) {
                bindDatas.push(
                    {
                        model: me.modelId,
                        dataId: dataId,
                        extraParams: {
                            auditFlowModelBindId: me.down('orientComboBox').getValue()
                        }
                    }
                );
            });
            Ext.each(assigners, function (assigner) {
                if (!Ext.isEmpty(assigner.assign_username)) {
                    if (assigner.assign_username.indexOf(',') != -1) {
                        //用户组
                        taskUserAssigns[assigner.taskName] = {
                            candidateUsers: assigner.assign_username
                        };
                    } else {
                        //单用户
                        taskUserAssigns[assigner.taskName] = {
                            currentUser: assigner.assign_username
                        };
                    }
                }
            });
            var auditParams = {
                pdId: pdId,
                auditType: 'ModelDataAudit',
                bindDatas: bindDatas,
                taskUserAssigns: taskUserAssigns
            };
            OrientExtUtil.AjaxHelper.doRequest(serviceName + '/auditFlow/control/start.rdm', auditParams, true, function (resp) {
                if (me.successCallback) {
                    me.successCallback.call(me);
                }
                if (me.up('window')) {
                    me.up('window').close();
                }
            }, true);
        }
    }
});