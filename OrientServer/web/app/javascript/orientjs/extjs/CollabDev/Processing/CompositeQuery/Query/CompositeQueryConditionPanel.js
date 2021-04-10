/**
 * 综合查询条件输入面板
 */
Ext.define('OrientTdm.CollabDev.Processing.CompositeQuery.Query.CompositeQueryConditionPanel', {
    extend: 'OrientTdm.Common.Extend.Form.OrientForm',
    alias: 'widget.compositeQueryConditionPanel',
    bodyStyle: 'background:white;',
    initComponent: function () {
        var _this = this;

        var items = [
            {
                xtype: 'combo',
                labelWidth: 60,
                width: 200,
                margin: '0 5 0 5',
                editable: false,
                queryModel: 'local',
                displayField: 'display',
                valueField: 'value',
                value: '0',//默认所有
                store: {
                    fields: ['display', 'value'],
                    data: [
                        {'display': '所有', 'value': '0'},
                        {'display': '项目', 'value': '1'},
                        {'display': '计划', 'value': '2'},
                        {'display': '数据包', 'value': '3'},
                        {'display': '负责人', 'value': '4'},
                        {'display': '专业', 'value': '5'},
                        {'display': '岗位', 'value': '6'},
                        {'display': '研发数据', 'value': '7'},
                        {'display': '标签', 'value': '8'},
                        {'display': '全文检索', 'value': '9'}
                    ]
                },
                listConfig: {}
            },
            {
                xtype: 'textfield',
                width: 400,
                margin: '0 5 0 5'
            },

            {
                xtype: 'button',
                text: '查询',
                width: 100,
                border: '0px!important;',
                style: 'background:#5fa2dd;',
                scale: 'medium',
                margin: '0 5 0 5',
                handler: function () {
                    _this._doQuery();

                }
            }];

        Ext.apply(_this, {
            layout: {
                type: 'hbox',
                padding: '5',
                pack: 'center',
                align: 'middle'
            },
            items: items
        });

        _this.callParent(arguments);
    },
    _doQuery: function () {
        var _this = this;
        var formValue = OrientExtUtil.FormHelper.generateFormData(_this.getForm());
        if (_this.ownerCt.centerPanel.centerPanel) {
            _this.ownerCt.centerPanel.centerPanel.fireEvent('filterGrid', formValue);
        }
    }
});