/**
 * ${DESCRIPTION}
 * 组件数据面板
 * @author ZhangSheng
 * @create 2018-08-08 15:41 PM
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.ComponentData.ComponentDataPanel', {
    extend: 'OrientTdm.Common.Extend.Panel.OrientPanel',
    alias: 'widget.componentDataPanel',
    config: {
        nodeId: '',
        flowTaskId: '',
        //保存历史任务时 是否需要序列化至数据库 暂不保存历史信息
        isHistoryAble: false,
        //历史任务描述
        hisTaskDetail: null,
        selectedData: ''
    },
    initComponent: function () {
        var me = this;
        var params = {
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/getCompomentId.rdm', params, false, function (resp) {
            me.selectedData = resp.decodedData.results;
        });

        //“选择组件”下拉框
        var componentComboBox = Ext.create('OrientTdm.Common.Extend.Form.Field.OrientComboBox', {
            id: 'componentCombo',
            fieldLabel: '选择组件',
            labelWidth: 70,
            emptyText: '请选择',
            editable: false,
            //region: 'north',
            remoteUrl: serviceName + '/Component/list.rdm?',
            displayField: 'componentname',
            valueFiled: 'id',
            hideLabel: false,
            value: me.selectedData,
            //initFirstRecord: true,
            width: 0.2 * globalWidth,
            minWidth: 0.1 * globalWidth,
            maxWidth: 0.2 * globalWidth,
            style: {
                margin: '0 0 0 10'
            },

            listeners: {
                select: function (combobox, record) {
                    var componentId = record instanceof Array ? record[0].get('id') : record.get('id');
                    var componentName = record instanceof Array ? record[0].get('componentname') : record.get('componentname');
                    me.reBindCompoment(componentId); //重新绑定组件
                    me._initComponentPanel(componentId, componentName);//刷新组件显示面板
                }
            }
        });

        me.tbar = [
            componentComboBox
        ];

        Ext.apply(me, {
            layout: 'fit',
            autoScroll: true,
            items: [
                {
                    itemId: 'mainPanel',
                    xtype: 'container',
                    title: '占位面板'
                }
            ],
            icon: null,
            title: '组件数据'
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.mon(_this, 'initComponentPanel', _this._initComponentPanel, _this);
        _this.callParent();
    },
    _initComponentPanel: function (componentId, componentName) {
        var me = this;
        var params = {
            componentId: componentId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/Component/getComponentJSClass.rdm', params, true, function (resp) {
            var results = resp.decodedData.results;
            if (!Ext.isEmpty(results)) {
                Ext.require(results, function () {
                    var item = Ext.create(results, {
                        nodeId: me.nodeId,
                        componentId: componentId
                    });
                    if (item.xtype != 'window') {

                        //add componentPanel to mainPanel container
                        var mainPanel = me.down('#mainPanel');
                        var componentPanel = Ext.create('OrientTdm.Common.Extend.Panel.OrientPanel', {
                            id: 'componentPanel',
                            title: componentName,
                            padding: '0 0 0 5',
                            split: true,
                            autoScroll: true,
                            collapsible: true,
                            collapsed: false,
                            items: Ext.isArray(item) ? item : [
                                item
                            ],
                            layout: 'fit'
                            //collapseMode: 'mini'
                        });

                        mainPanel.removeAll();
                        mainPanel.add([componentPanel]);
                    }
                });
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, OrientLocal.prompt.unBindExtClass);
            }
        });
    },
    refreshByNewNode: function () {
        var _this = this;
        console.dir('trigger refresh by new node info in results set panel');
    },
    reBindCompoment: function (componentId) {
        var me = this;
        var params = {
            componentId: componentId,
            nodeId: me.nodeId
        };
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/resultSettings/reBindCompoment.rdm', params, false, function (resp) {

        });
    },
    validateComponents: function (successCallBack, scope) {
        var me = this;
        var components = me.query('baseComponent');
        for (var i = 0; i < components.length; i++) {
            var component = components[i];
            component.validateComponent(successCallBack, scope);
            //debugger;
        }
    }
});