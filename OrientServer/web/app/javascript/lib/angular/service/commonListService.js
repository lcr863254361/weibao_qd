angular.module('commonListService', [])
.service('CommonListService', [function() {
    var service = {
        	//运算条件数组-日期
        	yesOrNoList:yesOrNoList=[
				{
					key:'是',
					value:true
				},
				{
					key:"否",
					value:false
				}
	      	]
    }
    return service;
}]);