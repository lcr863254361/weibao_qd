试验资源管理功能点导入说明
警告：该说明仅适用于系统中未集成试验资源管理的老系统。

1.导入试验资源管理相关代码，包括orient-testresource模块代码、TestResourceMgr的JS代码、ModelDataController.java和OrientExtUtil.js中的通用方法、orient-modeldata中tbom相关注解以及相关监听器等。
2.使用DS查看数据库中是否已存在试验资源管理模型，如有则跳过第3步。
3.使用任意用户登录系统，访问地址：http://127.0.0.1:8080/OrientEDM/resourceInit/initResourceDB.rdm，若返回成功则表明数据库初始化成功
4.修改config.properties中device.schemaId属性值为试验资源管理模型schemaId
5.重启服务器，在功能点管理中创建试验资源管理（“JS类”=OrientTdm.TestResourceMgr.TestResourceMgrDashBord，“包含Bom”=是），并在角色管理中分配功能点及TBom
6.在按钮管理中增加增加计量按钮（“自定义Ext类”=OrientTdm.TestResourceMgr.EquipmentCalcMgr.AddCalcButton）
