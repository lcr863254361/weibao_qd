        //声明全局变量
        var formvalue = "";
        var deFlag = 1;
        var inFlag=1;

//      $(function() {
//          //初始化第一行
//          firstCell = $("#row0 td:eq(0)").html();
//          secondCell = $("#row0 td:eq(1)").html();
//          thirdCell = $("#row0 td:eq(2)").html();
//          fourthCell = $("#row0 td:eq(3)").html();
//      });                                                 
        
        //-----------------新增一行-----------start---------------
        function insertNewRow(specialRowData,isDeOrIn) {
        	var rowInsertJquery;
        	if(isDeOrIn){
        		 //获取表格有多少行
            var rowLength = $("#decreaseTable tr").length;
             //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
            var rowId = "deRow" + deFlag;
            // 这里的行数减2，是因为要减去底部的一行和顶部的一行，
             rowInsertJquery=$("#decreaseTable tr:eq(" + (rowLength - 2) + ")");
        	}else{
        	var rowLength = $("#increaseTable tr").length;
             //这里的rowId就是row加上标志位的组合。是每新增一行的tr的id。
            var rowId = "inRow" + inFlag;
             rowInsertJquery=$("#increaseTable tr:eq(" + (rowLength - 2) + ")");
        	}
            //每次往下标为flag+1的下面添加tr,因为append是往标签内追加。所以用after
            var insertStr = "<tr id=" + rowId + ">"
                          + "<td>"+
					 "<input type='text' style='width: 100%;height: 100%;border:none;'>"
                          +"</td>"
                          + "<td>"+
					 "<input type='number' style='width: 100%;height: 100%;border:none;' class=\"deal\"\n" +
                "                       onmousewheel=\"stopScrollFun()\"\n" +
                "                       onDOMMouseScroll=\"stopScrollFun()\">"
                          +"</td>"
                          + "<td>"+ 
					 "<input type='number' style='width: 100%;height: 100%;border:none;' class=\"deal\"\n" +
                "                       onmousewheel=\"stopScrollFun()\"\n" +
                "                       onDOMMouseScroll=\"stopScrollFun()\">"
                          +"</td>"
                          + "<td style='width:40px'><input type='button' name='delete' value='删除' style='width: 100%;height: 100%;border:none;'  onclick='deleteSelectedRow(\"" + rowId +"\","+isDeOrIn+ ")' />";
                          +"</tr>";
            //这里的行数减2，是因为要减去底部的一行和顶部的一行，剩下的为开始要插入行的索引
                          rowInsertJquery.after(insertStr); //将新拼接的一行插入到当前行的下面
             //为新添加的行里面的控件添加新的id属性。
          if(isDeOrIn){
          	 $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "decreaseDevice" + deFlag);
             $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "decreaseAirWeight" + deFlag);
             $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "decreasePWaterVolume" + deFlag);
             $("#" + rowId + " td:eq(0)").children().eq(0).attr("name", "decreaseDevice" + deFlag);
             $("#" + rowId + " td:eq(1)").children().eq(0).attr("name", "decreaseAirWeight" + deFlag);
             $("#" + rowId + " td:eq(2)").children().eq(0).attr("name", "decreasePWaterVolume" + deFlag);
             if (typeof specialRowData!== 'undefined'&&specialRowData!==null) {
                 $("#" + rowId + " td:eq(0)").children().eq(0).attr("value", specialRowData.deviceName);
                 $("#" + rowId + " td:eq(1)").children().eq(0).attr("value", specialRowData.airWeight);
                 $("#" + rowId + " td:eq(2)").children().eq(0).attr("value", specialRowData.pwVolume);
             }
             //每插入一行，flag自增一次
             deFlag++;
             $("#deRow0 td:eq(0)").attr("rowspan", deFlag);
          }else{
          	 $("#" + rowId + " td:eq(2)").attr("colspan",2);
          	 $("#" + rowId + " td:eq(0)").children().eq(0).attr("id", "increaseDevice" + inFlag);
             $("#" + rowId + " td:eq(1)").children().eq(0).attr("id", "increaseAirWeight" + inFlag);
             $("#" + rowId + " td:eq(2)").children().eq(0).attr("id", "increasePWaterVolume" + inFlag);  
          	 $("#" + rowId + " td:eq(0)").children().eq(0).attr("name", "increaseDevice" + inFlag);
             $("#" + rowId + " td:eq(1)").children().eq(0).attr("name", "increaseAirWeight" + inFlag);
             $("#" + rowId + " td:eq(2)").children().eq(0).attr("name", "increasePWaterVolume" + inFlag);
              if (typeof specialRowData!== 'undefined'&&specialRowData!==null) {
                  $("#" + rowId + " td:eq(0)").children().eq(0).attr("value", specialRowData.deviceName);
                  $("#" + rowId + " td:eq(1)").children().eq(0).attr("value", specialRowData.airWeight);
                  $("#" + rowId + " td:eq(2)").children().eq(0).attr("value", specialRowData.pwVolume);
              }
            //每插入一行，flag自增一次
            inFlag++;
            $("#inRow0 td:eq(0)").attr("rowspan", inFlag);
          }  
         }
             //-----------------删除一行，根据行ID删除-start--------    
             function deleteSelectedRow(rowID,isDeOrIn) {
             // if (confirm("确定删除该行吗？")) {
                 $("#" + rowID).remove();
                 if(isDeOrIn){
                 	deFlag--;
                 $("#deRow0 td:eq(0)").attr("rowspan", deFlag);              	
                 }else{
                 	inFlag--;
                 $("#inRow0 td:eq(0)").attr("rowspan", inFlag);
                 }
//                $("#combineRow td:eq(0)").attr("rowspan", $("#specialTable tr").length-5);
//              }
         }
  //-----------------清空一行，根据行ID清空-start--------    
             function clearSelectedRow(rowID) {
                 $("#"+rowID+" td:eq(1)").children().eq(0).val("");
                 $("#"+rowID+" td:eq(2)").children().eq(0).val("");
                 $("#"+rowID+" td:eq(3)").children().eq(0).val("");  
         }

        //-----------------获取表单中的值----start--------------
        function GetValue(isDeOrInGetValue) {
            var tableRowData=[];
            var trArrays;
            if (isDeOrInGetValue) {
                 trArrays = $("#decreaseTable tr");
            }else {
                trArrays = $("#increaseTable tr");
            }
                trArrays.each(function(i) {
                    if (i >= 0&&i<trArrays.length-1) {
                        //获取每行第一个单元格里的selector控件的值
                        if (i==0){
                            var deviceName=$(this).children().eq(1).children().eq(0).val();
                            var airWeight=$(this).children().eq(2).children().eq(0).val();
                            var pwVolume=$(this).children().eq(3).children().eq(0).val();
                        } else{
                            var deviceName=$(this).children().eq(0).children().eq(0).val();
                            var airWeight=$(this).children().eq(1).children().eq(0).val();
                            var pwVolume=$(this).children().eq(2).children().eq(0).val();
                        }
                        if (deviceName!=null&&deviceName!=""){
                            var singleObject = new Object();
                            singleObject.deviceName = deviceName;
                            singleObject.airWeight=airWeight;
                            singleObject.pwVolume=pwVolume;
                            singleObject.rowNumber =i;
                            tableRowData.push(singleObject);
                        }
                    }
                });
            return tableRowData;
            }
