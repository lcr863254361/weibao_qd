/**
 * Created by enjoy on 2016/4/29 0029.
 */
Ext.define('OrientTdm.CollabDev.Common.Navigation.Model.CollabDevNodeModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'type',
        'version',
        'nodeOrder',
        'status',
        'isRoot',
        'bmDataId'
    ]
});