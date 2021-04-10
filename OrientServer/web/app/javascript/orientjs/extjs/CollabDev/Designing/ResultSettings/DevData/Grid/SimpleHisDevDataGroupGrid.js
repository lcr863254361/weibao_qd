/**
 * 查看简单数据的所有版本
 * Created by gny on 18/10/27
 */
Ext.define('OrientTdm.CollabDev.Designing.ResultSettings.DevData.Grid.SimpleHisDevDataGroupGrid', {
    extend: 'OrientTdm.Common.Extend.Grid.OrientGrid',
    alias: 'widget.hisDevDataGroupGrid',
    requires: [
        'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel'
    ],
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            features: [{
                id: 'nodeVersion',
                ftype: 'grouping',
                groupHeaderTpl: '{nodeVersion}',
                hideGroupedHeader: false,
                enableGroupingMenu: false
            }]
        });
        me.callParent(arguments);
    },
    createStore: function () {
        var retVal = Ext.create('Ext.data.Store', {
            autoLoad: true,
            model: 'OrientTdm.CollabDev.Designing.ResultSettings.DevData.Model.DevDataModel',
            groupField: 'nodeVersion',
            proxy: {
                type: 'ajax',
                api: {
                    'read': serviceName + '/HisDataObj/getSimpleHisDevDatas.rdm'
                },
                reader: {
                    type: 'json',
                    successProperty: 'success',
                    totalProperty: 'totalProperty',
                    root: 'results',
                    messageProperty: 'msg'
                }
            }
        });

        this.store = retVal;
        return retVal;
    },
    createColumns: function () {
        var me = this;
        return [
            {
                header: '数据包版本',
                flex: 1,
                sortable: true,
                dataIndex: 'nodeVersion'
            },
            {
                header: '名称',
                flex: 1,
                sortable: true,
                dataIndex: 'dataobjectname'
            }, {
                header: '类型',
                flex: 1,
                sortable: true,
                dataIndex: 'dataTypeShowName'
            }, {
                header: '值',
                flex: 1,
                sortable: true,
                dataIndex: 'value',
                renderer: function (value, p, record) {
                    var retVal = value;
                    var extendsTypeRealName = record.get('extendsTypeRealName');
                    if ('date' == extendsTypeRealName && value) {
                        retVal = value.substr(0, value.indexOf('T'));
                    } else if ('file' == extendsTypeRealName) {
                        //json串
                        if (!Ext.isEmpty(value)) {
                            retVal = '';
                            var templateArray = [
                                "<span class='attachement-span'>",
                                "<a target='_blank' class='attachment'  onclick='OrientExtend.FileColumnDesc.handleFile(\"#fileId#\",\"C_File\")' title='#title#'>#name#</a>",
                                '<a href="javascript:;" onclick="OrientExtend.FileColumnDesc.handleFile(\'{id}\',\'C_File\');" title="下载" class="download">',
                                '</a>',
                                '</span>'
                            ];
                            var template = templateArray.join('');
                            var fileJson = Ext.decode(value);
                            var fileSize = fileJson.length;
                            Ext.each(fileJson, function (fileDesc, index) {
                                var fileId = fileDesc.id;
                                var fileName = fileDesc.name.length > 10 ? (fileDesc.name.substr(0, 6) + '...') : fileDesc.name;
                                retVal += template.replace("#name#", fileName).replace("#title#", fileName).replace("#fileId#", fileId);
                                if (index != (fileSize - 1)) {
                                    retVal += "</br>";
                                }
                            });
                        }
                    }
                    return retVal;
                }
            }, {
                header: '单位',
                flex: 1,
                sortable: true,
                dataIndex: 'unit'
            }, {
                header: '版本',
                flex: 1,
                sortable: true,
                dataIndex: 'version'
            }, {
                header: '修改人',
                flex: 1,
                sortable: true,
                dataIndex: 'modifiedUser'
            }, {
                header: '修改时间',
                xtype: 'datecolumn',
                format: "Y-m-d H:i:s",
                flex: 1,
                sortable: true,
                dataIndex: 'modifytime'
            }
        ];
    },
    createToolBarItems: function () {
        var me = this;
        var retVal = [{
            iconCls: 'icon-back',
            text: '返回',
            itemId: 'back',
            scope: this,
            handler: me._onBackClick
        }];
        return retVal;
    },
    _onBackClick: function () {
        var me = this;
        var layout = me.ownerCt.getLayout();
        layout.setActiveItem(0);
        var combo =  Ext.ComponentQuery.query('processingDevDataMainTabPanel')[0].down('selfDevDataPanel').down('#taskVersionSwitchCombo');
        combo.enable();
    }
});