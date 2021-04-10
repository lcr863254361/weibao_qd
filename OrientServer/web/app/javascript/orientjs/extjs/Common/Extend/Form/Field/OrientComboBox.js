/**
 * Created by enjoy on 2016/3/21 0021.
 */
Ext.define('OrientTdm.Common.Extend.Form.Field.OrientComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.orientComboBox',
    queryParam: 'filter',
    queryMode: 'local',
    triggerAction: 'all',
    anyMatch: true,
    minChars: 2,
    config: {
        initFirstRecord: false,
        remoteUrl: null,
        displayField: 'value',
        valueField: 'id',
        storeListeners: {}
    },
    initComponent: function () {
        var me = this;
        me.store = me.initStore();
        //绑定store
        this.callParent(arguments);
    },
    initStore: function () {
        var me = this;
        var store = Ext.create('Ext.data.Store', {
            autoLoad: true,
            proxy: {
                type: 'ajax',
                startParam: 'startIndex',
                limitParam: 'maxResults',
                url: me.remoteUrl,
                pageSize: 25,
                reader: {
                    type: 'json',
                    totalProperty: 'totalProperty',
                    root: 'results'
                }
            },
            fields: [{
                name: me.valueField,
                type: 'string'
            }, {
                name: me.displayField,
                type: 'string'
            }],
            listeners: {
                load: function (store, records) {
                    if (records.length > 0 && Ext.isEmpty(me.getValue()) && me.initFirstRecord) {
                        me.setValue(records[0]);
                        me.fireEvent('select', me, records[0]);
                    }
                    //if combobox has default value then fire select event to select default value
                    //modified by TeddyJohnson 2018.8.9
                    else if(!Ext.isEmpty(me.value)){
                        var record;
                        Ext.each(records,function(recd){
                            if(recd.get(me.valueField) == me.value){
                                record = recd;
                            }
                        });
                        if(record){
                            me.fireEvent('select', me, record);
                        }
                    }
                }
            }
        });
        //初始化combobox时可以为store指定监听
        for (var listener in me.storeListeners) {
            if (me.storeListeners.hasOwnProperty(listener)) {
                store.on(listener, me.storeListeners[listener], store);
            }
        }
        return store;
    },
    setValue: function (value, doSelect) {
        if (null == this.getValue()) {
            //判断是否可以转为json格式
            if (value && !(value instanceof Object)) {
                var originalValue = value;
                if (this.isJson(value)) {
                    value = Ext.decode(value);
                    value = this.store.createModel(value);
                } else
                    value = originalValue;
            }
        }
        this.callParent(arguments);
    },
    isJson: function (obj) {
        var retVal = false;
        try {
            Ext.decode(obj);
            retVal = true;
        } catch (e) {

        }
        return retVal;
    }
});