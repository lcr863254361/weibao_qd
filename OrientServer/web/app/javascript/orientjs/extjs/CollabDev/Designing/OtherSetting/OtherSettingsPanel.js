/**
 * ${DESCRIPTION}
 * 项目其他设置面板
 * @author ZhangSheng
 * @create 2018-07-30 16:54 PM
 */
Ext.define('OrientTdm.CollabDev.Designing.OtherSetting.OtherSettingsPanel', {
    extend: 'OrientTdm.CollabDev.Common.BaseVersionPanel',
    alias: 'widget.otherSettingsPanel',
    config: {
        schemaId: TDM_SERVER_CONFIG.COLLAB_SCHEMA_ID,
        templateName: TDM_SERVER_CONFIG.TPL_PROJECT_SETTING,
        modelName: TDM_SERVER_CONFIG.PROJECT_SETTINGS
    },
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            padding: '0 0 0 5',
            items: [
                {
                    xtype: 'container',
                    title: '占位面板'
                }
            ],
            iconCls: 'icon-collabDev-extraSetting',
            title: '6.项目其他设置'
        });
        me.callParent(arguments);
    },
    initEvents: function () {
        var _this = this;
        _this.mon(_this, 'refreshByNewNode', _this.refreshByNewNode, _this);
        _this.callParent();
    },
    refreshByNewNode: function () {
        var _this = this;
        if (_this.getInited() == false) {
            _this.setInited(true);
            _this.removeAll();
            //
            var modelId = OrientExtUtil.ModelHelper.getModelId(_this.modelName, _this.schemaId);
            var templateId = OrientExtUtil.ModelHelper.getTemplateId(modelId, _this.templateName);
            var modelGrid = Ext.create('OrientTdm.Common.Extend.Grid.OrientModelGrid', {
                itemId: 'otherSettingsGrid',
                modelId: modelId,
                templateId: templateId,
                isView: 0,
                usePage: false,
                border: false,
                queryUrl: serviceName + '/otherSettings/queryOtherSettings.rdm?modelName=' + _this.modelName + '&bmDataId=' + _this.bmDataId,
                selModel: {
                    selType: 'rowmodel',
                    mode: 'SIGNLE'
                },  //设置勾选框这一列不显示
                afterInitComponent: function () {
                    var thisGrid = this;
                    var cols = thisGrid.columns;
                    var isValidCol = cols[1];
                    //创建数据模型
                    Ext.regModel('ValidInfo', {
                        fields: [{name: 'validDisplay'}, {name: 'validValue'}]
                    });
                    //定义“是否启用”下拉框中显示的数据源
                    var isValidStore = Ext.create('Ext.data.Store', {
                        model: 'ValidInfo',
                        data: [
                            {validDisplay: '启用', validValue: '启用'},
                            {validDisplay: '禁用', validValue: '禁用'}
                        ]
                    });
                    //创建“是否启用”字段的下拉框
                    var isValidCombobox = Ext.create('Ext.form.field.ComboBox', {
                        store: isValidStore,
                        displayField: 'validDisplay',
                        valueFiled: 'validValue',
                        queryMode: 'local',
                        editable: false,
                        triggerAction: 'all',
                        listeners: {
                            change: {
                                fn: _this.changeValue,
                                scope: _this
                            }
                        }
                    });
                    //设置“是否启用”列的editor
                    isValidCol.editor = isValidCombobox;

                    //单元格双击可编辑
                    thisGrid.cellEditing = new Ext.grid.plugin.CellEditing({
                        clicksToEdit: 2
                    });
                    thisGrid.plugins = thisGrid.cellEditing;
                    //渲染combo设置为禁用时，禁用显示为红色
                    _this.renderComboValue.call(thisGrid, cols);
                }
            });

            _this.add([modelGrid]);
        } else {
            var respPanel = _this.items.getAt(0);
            respPanel.getStore().getProxy().api.read = serviceName + '/otherSettings/queryOtherSettings.rdm?modelName=' + _this.modelName + '&bmDataId=' + _this.bmDataId;
            respPanel.fireEvent('refreshGrid');
        }
    },
    //下拉框值改变事件
    changeValue: function (obj, newValue, oldValue, eOpt) {
        //skip grid init process
        if (obj == null)
            return;
        var me = this;
        var modelId = OrientExtUtil.ModelHelper.getModelId(me.modelName, me.schemaId);
        var grid = me.down('#otherSettingsGrid');
        var cols = grid.columns;
        var isValidCol = cols[1];
        //get current othersettings record
        var record = OrientExtUtil.GridHelper.getSelectedRecord(grid);
        //update grid record
        record[0].data[isValidCol.dataIndex] = newValue;
        //makeup othersettings update record json
        var formData = '{"fields":' + Ext.encode(record[0].data) + '}';
        var params = {
            modelId: modelId,
            formData: formData
        };
        //update CB_PROJECT_SETTINGS Database
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelData/updateModelData.rdm', params, true, function (resp) {
            var retV = Ext.decode(resp.responseText);
            grid.fireEvent('refreshGrid');
        });
    },
    renderComboValue: function (cols) {
        var colObj = cols[1];
        colObj.renderer = function (value) {
            return value == '禁用' ? '<span style="color:red;">' + value + '</span>' : value;
        }
    }
});