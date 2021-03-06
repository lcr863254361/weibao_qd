/**
 * Created by Administrator on 2016/7/5 0005.
 */
Ext.define('OrientTdm.SysMgr.ModelRightsMgr.Common.ModelRightForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.modelRightForm',
    requires: [
        'OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel'
    ],
    config: {
        modelRightData: {},
        btnTypes: []
    },
    bodyStyle: 'border-width:0 0 0 0; background:transparent',
    bodyPadding: 10,
    layout: 'anchor',
    buttonAlign: 'center',
    defaults: {
        anchor: '100%',
        labelAlign: 'left',
        msgTarget: 'side',
        labelWidth: 90
    },
    initComponent: function () {
        var me = this;
        var operationArr = [];
        if (!Ext.isEmpty(me.modelRightData.get('operationsId'))) {
            Ext.each(me.modelRightData.get('operationsId').split(','), function (operationId) {
                operationArr.push(parseInt(operationId));
            });
        }
        var operatorStore = Ext.create('Ext.data.ArrayStore', {
            fields: [
                'resultId',
                'resultText'
            ],
            data: [
                ['=', '='], ['<>', '<>'], ['>', '>'], ['>=', '>='], ['<', '<'], ['<=', '<='],
                ['LIKE', 'LIKE'], ['BEWTEEN', 'BEWTEEN'], ['IS NOT NULL', 'IS NOT NULL'], ['IS NULL', 'IS NULL']
            ]
        });
        var operaCheckArr = [];
        Ext.each(me.btnTypes, function (btnType) {
            operaCheckArr.push(Ext.create('Ext.form.field.Checkbox', {
                name: btnType.code,
                boxLabel: btnType.name,
                checked: Ext.Array.contains(operationArr, btnType.id),
                innerValue: btnType.id
            }));
        });
        Ext.apply(me, {
            items: [
                {
                    xtype: 'fieldset',
                    border: '1 1 0 1',
                    collapsible: true,
                    title: 'εΊζ¬ζζ',
                    items: [
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'middle'
                            },
                            items: [
                                {
                                    xtype: 'displayfield',
                                    name: 'tableName',
                                    flex: 0.5,
                                    fieldLabel: 'ε½ειζ©',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'button',
                                    flex: 0.05,
                                    text: 'ε―θ§ε­ζ?΅θ?Ύη½?',
                                    handler: me._modifyModelColumnRight
                                }
                            ]
                        },
                        {
                            xtype: 'checkboxfield',
                            anchor: '100%',
                            fieldLabel: 'ζζζδ½',
                            labelWidth: 60,
                            boxLabel: 'ε¨ι',
                            listeners: {
                                change: me._changeAll
                            }
                        },
                        {
                            xtype: 'checkboxgroup',
                            padding: '0 0 0 60',
                            fieldLabel: '',
                            labelWidth: 60,
                            columns: 5,
                            items: operaCheckArr
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    border: '1 1 0 1',
                    layout: 'fit',
                    collapsible: true,
                    title: 'ζ°ζ?η±»θΏζ»€',
                    items: [
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'orientComboBox',
                                    flex: 0.2,
                                    padding: '0 0 5 0',
                                    fieldLabel: '',
                                    name: 'modelFilter.column',
                                    emptyText: 'θ―·ιζ©ζ°ζ?η±»ε­ζ?΅',
                                    remoteUrl: serviceName + '/modelData/getModelColumCombobox.rdm',
                                    valueField: 'sColumnName',
                                    displayField: 'text',
                                    queryCaching: false
                                },
                                {
                                    xtype: 'combobox',
                                    flex: 0.1,
                                    padding: '0 0 5 10',
                                    fieldLabel: '',
                                    emptyText: 'θ―·ιζ©ζ―θΎη¬¦',
                                    valueField: 'resultId',
                                    displayField: 'resultText',
                                    name: 'modelFilter.operator',
                                    mode: 'local',
                                    store: operatorStore
                                },
                                {
                                    xtype: 'textfield',
                                    flex: 0.15,
                                    padding: '0 0 5 10',
                                    fieldLabel: '',
                                    emptyText: 'θ―·θΎε₯ε­ζ?΅εΌ',
                                    name: 'modelFilter.filterValue'
                                }
                            ]
                        },
                        {
                            xtype: 'fieldcontainer',
                            height: '',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'middle',
                                pack: 'center'
                            },
                            items: [
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    text: 'ζ·»ε θΏζ»€ζ‘δ»Ά',
                                    handler: me._modelFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: '( ',
                                    text: 'ζ·»ε \'(\'',
                                    handler: me._modelFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: ') ',
                                    text: 'ζ·»ε \')\'',
                                    handler: me._modelFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: 'AND ',
                                    text: 'ζ·»ε ANDζ‘δ»Ά',
                                    handler: me._modelFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: 'OR ',
                                    text: 'ζ·»ε ORζ‘δ»Ά',
                                    handler: me._modelFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    text: 'ζΈη©ΊθΏζ»€ζ‘δ»Ά',
                                    handler: me._modelFilterBtnClicked
                                }
                            ]
                        },
                        {
                            xtype: 'textareafield',
                            height: 123,
                            padding: '0 0 5 0',
                            name: 'filter',
                            fieldLabel: ''
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    border: '1 1 0 1',
                    layout: 'fit',
                    collapsible: true,
                    title: 'η¨ζ·θΏζ»€ζ‘δ»Ά',
                    items: [
                        {
                            xtype: 'fieldcontainer',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'radiogroup',
                                    flex: 0.1,
                                    padding: '0 0 5 0',
                                    fieldLabel: 'ζ―ε¦ε»Ίη«ε³θ',
                                    items: [
                                        {
                                            xtype: 'radiofield',
                                            boxLabel: 'ζ―',
                                            name: 'isConnectUser',
                                            checked: true,
                                            listeners: {
                                                change: me._selectConnect
                                            }
                                        },
                                        {
                                            xtype: 'radiofield',
                                            boxLabel: 'ε¦',
                                            name: 'isConnectUser',
                                            listeners: {
                                                change: me._selectConnect
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'orientComboBox',
                                    flex: 0.1,
                                    padding: '0 0 5 10',
                                    fieldLabel: '',
                                    name: 'customFilter.column',
                                    emptyText: 'θ―·ιζ©ζ°ζ?η±»ε­ζ?΅',
                                    remoteUrl: serviceName + '/modelData/getModelColumCombobox.rdm',
                                    valueField: 'sColumnName',
                                    displayField: 'text',
                                    queryCaching: false
                                },
                                {
                                    xtype: 'orientComboBox',
                                    flex: 0.1,
                                    padding: '0 10 5 10',
                                    fieldLabel: 'ε³θ',
                                    labelWidth: 60,
                                    name: 'customFilter.userColumn',
                                    emptyText: 'θ―·ιζ©η¨ζ·θ‘¨ε­ζ?΅',
                                    remoteUrl: serviceName + '/user/getUserColumCombobox.rdm',
                                    valueField: 'columnName',
                                    displayField: 'displayName',
                                    queryCaching: false
                                },
                                {
                                    xtype: 'button',
                                    flex: 0.02,
                                    margins: '0 0 5 0',
                                    width: '',
                                    text: 'ε³θ',
                                    handler: me._connectUserTable
                                }
                            ]
                        },
                        {
                            xtype: 'fieldcontainer',
                            height: '',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'orientComboBox',
                                    flex: 0.2,
                                    padding: '0 0 5 0',
                                    fieldLabel: '',
                                    emptyText: 'θ―·ιζ©η¨ζ·θ‘¨ε­ζ?΅',
                                    name: 'customFilter.userColumnInner',
                                    remoteUrl: serviceName + '/user/getUserColumCombobox.rdm',
                                    valueField: 'columnName',
                                    displayField: 'displayName',
                                    queryCaching: false
                                },
                                {
                                    xtype: 'combobox',
                                    flex: 0.1,
                                    padding: '0 0 5 10',
                                    fieldLabel: '',
                                    emptyText: 'θ―·ιζ©ζ―θΎη¬¦',
                                    valueField: 'resultId',
                                    displayField: 'resultText',
                                    mode: 'local',
                                    name: 'customFilter.operator',
                                    store: operatorStore
                                },
                                {
                                    xtype: 'textfield',
                                    flex: 0.15,
                                    padding: '0 0 5 10',
                                    fieldLabel: '',
                                    name: 'customFilter.filterValue',
                                    emptyText: 'θ―·θΎε₯ε­ζ?΅εΌ'
                                }
                            ]
                        }, {
                            xtype: 'fieldcontainer',
                            height: '',
                            fieldLabel: '',
                            layout: {
                                type: 'hbox',
                                align: 'middle',
                                pack: 'center'
                            },
                            items: [
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    text: 'ζ·»ε θΏζ»€ζ‘δ»Ά',
                                    handler: me._userFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: '( ',
                                    text: 'ζ·»ε \'(\'',
                                    handler: me._userFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: ') ',
                                    text: 'ζ·»ε \')\'',
                                    handler: me._userFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: 'AND ',
                                    text: 'ζ·»ε ANDζ‘δ»Ά',
                                    handler: me._userFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    insertValue: 'OR ',
                                    text: 'ζ·»ε ORζ‘δ»Ά',
                                    handler: me._userFilterBtnClicked
                                },
                                {
                                    xtype: 'button',
                                    margins: '5',
                                    padding: '5 5 5 5',
                                    text: 'ζΈη©ΊθΏζ»€ζ‘δ»Ά',
                                    handler: me._userFilterBtnClicked
                                }
                            ]
                        },
                        {
                            xtype: 'textareafield',
                            height: 123,
                            name: 'userFilter',
                            padding: '0 0 5 0',
                            fieldLabel: ''
                        }
                    ]
                }, {
                    xtype: 'hiddenfield',
                    name: 'id'
                }, {
                    xtype: 'hiddenfield',
                    name: 'tableId'
                }, {
                    xtype: 'hiddenfield',
                    name: 'isTable'
                }, {
                    xtype: 'hiddenfield',
                    name: 'columnId'
                }, {
                    xtype: 'hiddenfield',
                    name: 'operationsId'
                }, {
                    xtype: 'hiddenfield',
                    name: 'roleInfo'
                }, {
                    xtype: 'hiddenfield',
                    name: 'addColumnIds'
                }, {
                    xtype: 'hiddenfield',
                    name: 'detailColumnIds'
                }, {
                    xtype: 'hiddenfield',
                    name: 'modifyColumnIds'
                }, {
                    xtype: 'hiddenfield',
                    name: 'exportColumnIds'
                }, {
                    xtype: 'hiddenfield',
                    name: 'roleInfos'
                }/*, {
                    xtype: 'hiddenfield',
                    name: 'tableIds'
                }*/
            ],
            buttons: [
                {
                    text: 'δΏε­',
                    handler: me._saveModelRight
                }
            ]
        })
        ;
        me.callParent(arguments);
    },
    _modifyModelColumnRight: function () {
        var me = this.up('modelRightForm');
        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelFormView/getModelColumn.rdm', {
            orientModelId: me.modelRightData.get('tableId')
        }, false, function (resp) {
            var columnData = resp.decodedData.results;
            //ι»θ?€ζ₯ζζζζι
            if (Ext.isEmpty(me.modelRightData.get('columnId'))) {
                var selecteds = Ext.Array.pluck(columnData, 'id').join(',');
                me._updateHiddenFieldValue('columnId', selecteds);
            }
            var tabItems = [
                Ext.create('OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel', {
                    itemId: 'choose_columnId',
                    title: 'ζΎη€Ίε­ζ?΅'
                }),
                Ext.create('OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel', {
                    itemId: 'choose_addColumnIds',
                    title: 'ζ°ε’ε­ζ?΅'
                }),
                Ext.create('OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel', {
                    itemId: 'choose_modifyColumnIds',
                    title: 'δΏ?ζΉε­ζ?΅'
                }),
                Ext.create('OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel', {
                    itemId: 'choose_detailColumnIds',
                    title: 'θ―¦η»ε­ζ?΅'
                }),
                Ext.create('OrientTdm.BackgroundMgr.CustomGrid.Common.ModelColumnSelectedPanel', {
                    itemId: 'choose_exportColumnIds',
                    title: 'ε―ΌεΊε­ζ?΅'
                })
            ];
            var columnWin = Ext.create('Ext.Window', Ext.apply({
                plain: true,
                height: 0.7 * globalHeight,
                width: 0.7 * globalWidth,
                layout: 'fit',
                maximizable: true,
                title: 'ε­ζ?΅ζζ',
                modal: true,
                items: [
                    {
                        xtype: 'orientTabPanel',
                        items: tabItems
                    }
                ],
                buttonAlign: 'center',
                listeners: {
                    show: function () {
                        var columnChoosePanels = this.query('modelColumnSelectedPanel');
                        //θ·εε­ζ?΅ζθΏ°
                        OrientExtUtil.AjaxHelper.doRequest(serviceName + '/modelFormView/getModelColumn.rdm', {
                            orientModelId: me.modelRightData.get('tableId')
                        }, false, function (resp) {
                            Ext.each(columnChoosePanels, function (panel) {
                                panel.needReconfig = true;
                                var panelItemId = panel.itemId;
                                var hiddenField = me.down('hiddenfield[name=' + panelItemId.substring(panelItemId.indexOf('_') + 1) + ']');
                                panel.original = hiddenField ? hiddenField.getValue() : '';
                                panel.columnData = resp.decodedData.results;
                                panel.validateModelChange();
                            });
                        });
                    }
                },
                buttons: [
                    {
                        text: 'δΏε­',
                        handler: function () {
                            var columnChoosePanels = this.up('window').query('modelColumnSelectedPanel');
                            Ext.each(columnChoosePanels, function (panel) {
                                var panelItemId = panel.itemId;
                                var hiddenFieldName = panelItemId.substring(panelItemId.indexOf('_') + 1);
                                var selectedGrid = panel.down('#selectedGrid');
                                var selectedStore = selectedGrid.getStore();
                                var selectedIds = [];
                                selectedStore.each(function (record) {
                                    selectedIds.push(record.get('id'));
                                });
                                me._updateHiddenFieldValue(hiddenFieldName, selectedIds.join(','));
                            });
                            columnWin.close();
                        }
                    }
                ]
            }, {}));
            columnWin.show();
        });
    },
    _changeAll: function (checkAllbox) {
        var me = this.up('modelRightForm');
        var checkBoxes = me.down('checkboxgroup').query('checkboxfield');
        Ext.each(checkBoxes, function (box) {
            if (box.boxLabel != checkAllbox.boxLabel) {
                var newValue = checkAllbox.checked;
                box.setValue(newValue);
            }
        });
    },
    _modelFilterBtnClicked: function () {
        var me = this.up('modelRightForm');
        var modelFilter = me.down('[name=filter]');
        if (this.text == 'ζ·»ε θΏζ»€ζ‘δ»Ά') {
            //ζ ‘ιͺζ―ε¦δΈΊη©Ί
            var columnValue = me.down('[name=modelFilter.column]').getValue();
            var operatorValue = me.down('[name=modelFilter.operator]').getValue();
            var filterValue = me.down('[name=modelFilter.filterValue]').getValue();
            if (!Ext.isEmpty(columnValue) && !Ext.isEmpty(columnValue)) {
                modelFilter.setValue(modelFilter.getValue() + ' ' + columnValue + ' ' + operatorValue + ' \'' + filterValue + '\' ');
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, 'θ―·η‘?δΏζ°ζ?η±»ε­ζ?΅γζ―θΎη¬¦δ»₯εε­ζ?΅εΌι½ζεΌ');
            }
        } else if (this.text == 'ζΈη©ΊθΏζ»€ζ‘δ»Ά') {
            modelFilter.setValue('');
        } else {
            modelFilter.setValue(modelFilter.getValue() + ' ' + this.insertValue + ' ');
        }
    },
    _userFilterBtnClicked: function () {
        var me = this.up('modelRightForm');
        var userFilter = me.down('[name=userFilter]');
        if (this.text == 'ζ·»ε θΏζ»€ζ‘δ»Ά') {
            var columnValue = me.down('[name=customFilter.userColumnInner]').getValue();
            var operatorValue = me.down('[name=customFilter.operator]').getValue();
            var filterValue = me.down('[name=customFilter.filterValue]').getValue();
            if (!Ext.isEmpty(columnValue) && !Ext.isEmpty(operatorValue)) {
                userFilter.setValue(userFilter.getValue() + ' SYS_USER.' + columnValue + ' ' + operatorValue + '\'' + filterValue + '\'');
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, 'θ―·η‘?δΏη¨ζ·ε­ζ?΅δ»₯εζδ½η¬¦ι½ε·²ιδΈ­');
            }
        } else if (this.text == 'ζΈη©ΊθΏζ»€ζ‘δ»Ά') {
            userFilter.setValue('');
        } else {
            userFilter.setValue(userFilter.getValue() + ' ' + this.insertValue + ' ');
        }
    },
    _selectConnect: function () {
        var me = this.up('modelRightForm');
        var able = this.boxLabel == 'ε¦' ? false : true;
        me.down('[name=customFilter.column]').setDisabled(able);
        me.down('[name=customFilter.userColumn]').setDisabled(able);
    },
    _connectUserTable: function () {
        var me = this.up('modelRightForm');
        if (me.down('radiogroup').down('radiofield[checked=true]').boxLabel == 'ζ―') {
            var userModelColumnValue = me.down('[name=customFilter.column]').getValue();
            var userColumnValue = me.down('[name=customFilter.userColumn]').getValue();
            var userFilter = me.down('[name=userFilter]');
            if (!Ext.isEmpty(userModelColumnValue) && !Ext.isEmpty(userColumnValue)) {
                userFilter.setValue(userFilter.getValue() + ' ' + userModelColumnValue + ' = SYS_USER.' + userColumnValue + ' ');
            } else {
                OrientExtUtil.Common.err(OrientLocal.prompt.error, 'θ―·η‘?δΏζ°ζ?η±»ε­ζ?΅γη¨ζ·ε­ζ?΅ι½ε·²ιδΈ­');
            }
        } else {
            OrientExtUtil.Common.err(OrientLocal.prompt.error, 'θ―·η‘?δΏε·¦δΎ§ζ―ε¦ε»Ίη«ε³θειζ‘ιδΈ­ζ―ζε¨εΊε');
        }

    },
    _updateHiddenFieldValue: function (fieldName, selecteds) {
        var me = this;
        me.modelRightData.set(fieldName, selecteds);
        me.down('hiddenfield[name=' + fieldName + ']').setValue(selecteds);

    },
    _saveModelRight: function () {
        //η»ε?operationsIdδΏ‘ζ―
        var me = this.up('modelRightForm');
        var checkBoxes = me.down('checkboxgroup').query('checkboxfield[checked=true]');
        var operationsId = '';
        var operationsIdArray = [];
        Ext.each(checkBoxes, function (checkBox) {
            operationsIdArray.push(checkBox.innerValue);
        });
        operationsId = operationsIdArray.join(',');
        me._updateHiddenFieldValue('operationsId', operationsId);
        //ζδΊ€θ³ζε‘η«―

        //var tableIds = me._getTableIds(me.ownerCt.ownerCt.westPanel);
        var roleInfos = me.ownerCt.down("orientCheckCombo").getValue();
        var actionUrl = null;
        if(roleInfos.length>1) {
            //me._updateHiddenFieldValue('tableIds', tableIds.join(","));
            me._updateHiddenFieldValue('roleInfos', roleInfos.join(","));
            actionUrl = serviceName + '/ModelRights/createOrUpdate.rdm';
        }
        else {
            var id = me.modelRightData.get('id');
            actionUrl = serviceName + '/ModelRights/create.rdm';
            if (!Ext.isEmpty(id)) {
                actionUrl = serviceName + '/ModelRights/update.rdm';
            }
        }

        me.getForm().submit({
            clientValidation: true,
            url: actionUrl,
            waitTitle: 'ζη€Ί',
            waitMsg: 'δΏε­δΈ­οΌθ―·η¨ε...',
            success: function (form, action) {
                OrientExtUtil.Common.tip('ζε', action.result.msg);
                //ηΉε»ζ°θηΉ
                me._updateHiddenFieldValue('id', action.result.results);
            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.CLIENT_INVALID:
                        OrientExtUtil.Common.err('ε€±θ΄₯', 'θ‘¨εε­ε¨ιθ――');
                        break;
                    case Ext.form.action.Action.CONNECT_FAILURE:
                        OrientExtUtil.Common.err('ε€±θ΄₯', 'ζ ζ³θΏζ₯ζε‘ε¨');
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                        OrientExtUtil.Common.err('ε€±θ΄₯', action.result.msg);
                }
            }
        });
    },
    _getTableIds: function(treePanel) {
        var checked = treePanel.getChecked();
        var ids = [];
        for(var i=0; i<checked.length; i++) {
            ids.push(checked[i].data.id);
        }
        if(ids.length == 0) {
            var selected = treePanel.getSelectionModel().getSelection();
            if(selected.length > 0) {
                for(var i=0; i<selected.length; i++) {
                    ids.push(selected[i].data.id);
                }
            }
        }
        return ids;
    },
    loadRecord: function (record) {
        //ιζ°recordζΉζ³
        var me = this;
        me.setModelRightData(record);
        var columnComboboxs = me.query('orientComboBox');
        Ext.each(columnComboboxs, function (combobox) {
            combobox.clearValue(null);
            combobox.getStore().getProxy().setExtraParam('orientModelId', me.modelRightData.get('tableId'));
            combobox.getStore().reload();
        });
        if (Ext.isEmpty(me.modelRightData.get('operationsId'))) {
            me.modelRightData.set('operationsId', '');
        }
        var checkBoxes = me.down('checkboxgroup').query('checkboxfield');
        Ext.each(checkBoxes, function (checkBox) {
            if (record.get('operationsId').indexOf(checkBox.innerValue) != -1) {
                checkBox.setValue(true);
            } else
                checkBox.setValue(false);
        });
        me.callParent(arguments);
    }
});