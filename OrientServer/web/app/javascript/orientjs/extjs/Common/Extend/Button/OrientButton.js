/**
 * Created by enjoy on 2016/4/18 0018.
 */
Ext.define("OrientTdm.Common.Extend.Button.OrientButton", {
    extend: 'Ext.Base',
    constructor: function (config) {
        for (var key in config) {
            this[key] = config[key];
        }
    },
    //按钮点击时触发事件
    triggerClicked: function (modelGridPanel) {
        var me = this;
        me.bindGridPanel = modelGridPanel;
        //获取表格所属父容器
        var container = modelGridPanel.up();
        me.container = container;
        //获取父容器布局
        me.layout = container.getLayout();
        //获取布局别名
        me.layoutAlias = me.layout.alias;
        //根据不同布局采用不同操作模式
        if (me.layoutAlias == 'layout.card') {
            //如果grid的所属面板布局为card，则采用滑动的方式 加载其他面板
            me.layout.setActiveItem(1);
            var activeItem = me.layout.getActiveItem();
            activeItem.removeAll();
            activeItem.add(me.customPanel);
            activeItem.doLayout();
        } else if (me.layoutAlias == 'layout.border' && modelGridPanel.region == 'center') {
            //默认弹出下面板 可传入参数
            var respPanel = modelGridPanel.ownerCt.down("panel[region=south]");
            if (!respPanel) {
                respPanel = Ext.create("OrientTdm.Common.Extend.Panel.OrientPanel", {
                    region: 'south',
                    padding: '0 0 0 0',
                    deferredRender: false
                });
                modelGridPanel.ownerCt.add(respPanel);
                modelGridPanel.ownerCt.doLayout();
            }
            me.customPanel.maxHeight = (globalHeight - 300) * 0.9;
            respPanel.expand(true);
            respPanel.removeAll();
            respPanel.add(me.customPanel);
            respPanel.doLayout();
            respPanel.show();
        } else {
            var modelDesc = modelGridPanel.modelDesc;
            var fieldCount = 0;
            switch (me.btnDesc.name) {
                case '新增':
                    fieldCount = modelDesc.createColumnDesc.length + 1;
                    break;
                case '修改':
                    fieldCount = modelDesc.modifyColumnDesc.length + 1;
                    break;
                case '详细':
                    fieldCount = modelDesc.detailColumnDesc.length + 1;
                    break;
                case '附件':
                    fieldCount = 10;
                    break;
                default:
                    break;
            }
            var rowNum = me.customPanel.rowNum;
            //append group item count
            fieldCount = fieldCount / rowNum == 0 ? fieldCount / rowNum : (fieldCount / rowNum) + 1;
            //否则 采用弹出window的方式 加载其他面板
            var createWin = new Ext.Window({
                width: 0.6 * globalWidth,
                height: 80 * fieldCount,
                layout: 'fit',
                modal: true,
                title: me.customPanel.title,
                items: me.customPanel,
                listeners: {
                    'beforeshow': function () {
                        /* var items = me.customPanel.items.items[0].items.length;
                         if (items < 3) {
                             createWin.setHeight(items * 240);
                         }
                         else if (items >= 3 && items <= 7) {
                           createWin.setHeight(items * 25);
                         }
                         else {
                             createWin.setHeight(items * 50);
                         }*/
                    }
                }
            });
            me.customPanel.title = "";
            createWin.show();
        }
    },
    //按钮操作完毕后的成果
    getSuccessData: function () {
        throw ("子类必须重载此方法");
    },
    doBack: function (btn) {
        var me = this;
        if (me.layoutAlias == 'layout.card') {
            me.layout.setActiveItem(0);
        } else if (me.layoutAlias == 'layout.border' && me.bindGridPanel.region == 'center') {
            var respPanel = me.container.down("panel[region=south]");
            respPanel.hide();
            //respPanel.collapse();
        } else {
            if (me.customPanel) {
                me.customPanel.up('window').close();
            }
        }
        delete me.customPanel;
    }
});