/**
 * Created by ZhangSheng on 2018/8/20 1409.
 */
Ext.define('OrientTdm.CollabDev.Processing.Model.ShareFilePanelExtModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id',
        'name',
        'createUser',
        'createTime',
        'updateUser',
        'updateTime',
        'remoteDesc',
        'version',
        'fileType',
        'fileLocation'
    ]
});