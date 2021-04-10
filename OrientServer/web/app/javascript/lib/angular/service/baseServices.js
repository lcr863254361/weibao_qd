var baseServices = angular.module( "baseServices", [] );
baseServices.factory("$jsonToFormData",function() {
	function transformRequest( data, getHeaders ) {
		var headers = getHeaders();
		headers["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
		return $.param(data);
	}
	return( transformRequest );
})
.directive('htCheckbox', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var key = attrs["htCheckbox"];
            var getValAry = function(){
                var val = getValByScope(element,key);
                if(val){
                	return val.split(",");
                }
                return [];
            };
            scope.$watch(key,function(newVal,oldVal){
            	var ary = getValAry();
                if(ary&&ary.length&&ary.join(",").indexOf(attrs["value"])!=-1){
                    element[0].checked = true;
                }
            });
            element.bind('change',function(){
                var elementVal = element[0].checked,
                    option = attrs["value"],
                    array = getValAry();
                if(elementVal){
                    array.push(option);
                }
                else{
                    array.remove(option);
                }
                setValToScope(element,array.join(','),null,key);
            });
        }
    };
})
.directive('htDate', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
        	var ngModel = attrs.ngModel;
        	switch(attrs.htDate){
        		case "date":
        			$(element).on("focus",function(){
        				var me = $(this);
        				WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true});
        				me.blur();
    				   scope.$apply(function(){
                           eval("(scope." + ngModel + "='" + me.val() + "')");    
                       });
        			});
        		break;
         		case "datetime":
        			$(element).on("focus",function(){
        				var me = $(this);
        				WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true});
        				me.blur();
    				   scope.$apply(function(){
                           eval("(scope." + ngModel + "='" + me.val() + "')");    
                       });
    			});
        		break;
        		case "wdateTime":
        			$(element).on("focus",function(){
        				var me = $(this), dateFmt=  (me.attr('datefmt')?me.attr('datefmt'):'yyyy-MM-dd');
            			WdatePicker({dateFmt:dateFmt,alwaysUseStartDate:true});
            			me.blur();
        			   scope.$apply(function(){
                           eval("(scope." + ngModel + "='" + me.val() + "')");    
                       });
        			});
        		break;
        	}
        }
    };
})

.service('BaseService', ['$http','$jsonToFormData', function($http,$jsonToFormData) {
    var service = {
    		get:function(url,callback){
    			$http.get(url).success(function(data,status){
            		if(callback){
            			callback(data,status);
            		}
        		})
        		.error(function(data,status){
        			if(callback){
            			callback(data,status);
        			}
        			//TODO 根据返回的错误状态(status)显示对应的全局提示
        		});
    		},
    		post:function(url,param,callback){
    			$http.post(url,param,{transformRequest:$jsonToFormData})
				 .success(function(data,status){
					 if(callback){
	    				callback(data,status);
	    			 }
				 })
				 .error(function(data,status){
					 if(callback){
	    				callback(data,status);
	    			 }
					 //TODO 根据返回的错误状态(status)显示对应的全局提示
				 });
    		},
    		//m内容，b:true->alert输出；false:console
    		show:function(m,b){
    			if(b==null||b==false){
    				console.info(m);
    			}else{
    				alert(m+"");
    			}
    		}
        }
    return service;
}]);

