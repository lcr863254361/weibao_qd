/**
 * Created by enjoy on 2016/3/16 0016.
 */
Ext.define('OrientTdm.SysMgr.UserMgr.Model.UserListExtModel', {
    extend: 'Ext.data.Model',
    fields: [
        'id', 'userName', 'allName', 'password', 'sex','birthday','phone','mobile', 'email','post','grade','department','notes','unit','personClassify','country','nation','identityCardNumber'
    ]
});