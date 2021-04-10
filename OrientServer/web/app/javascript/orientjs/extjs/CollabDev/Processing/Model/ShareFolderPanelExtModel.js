/**
 * Created by ZhangSheng on 2018/8/20 1409.
 */
Ext.define('OrientTdm.CollabDev.Processing.Model.ShareFolderPanelExtModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'folderOrder',
        'nodeId',
        'pid'
    ],
    proxy: {
        type: 'ajax',
        api: {
            'read': serviceName + '/shareFolder/list.rdm',
            'create': serviceName + '/shareFolder/create.rdm',
            'destroy': serviceName + '/shareFolder/delete.rdm',
            'update': serviceName + '/shareFolder/update.rdm'
        },
        reader: {
            type: 'json',
            successProperty: 'success',
            totalProperty: 'totalProperty',
            root: 'results',
            messageProperty: 'msg'
        },
        writer: {
            type: 'json',
            encode: true,
            root: 'formData',
            allowSingle: false
        }
    }
});