<#setting number_format="0">
<#macro input field>
    <#if field.type == "C_Simple"><#---字符串类型-->
        <#switch field.controlType>
            <#case 0><#--单行文本框-->
            <span name="editable-input" style="display:inline-block;" isflag="tableflag">
					<input type="text" name="${field.dbName}" lablename="${field.text}" class="inputText" value=""
                           validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"
                           isflag="tableflag"/>
				</span>
                <#break>
        </#switch>
    <#elseif field.type == "C_Float" || field.type == "C_Integer" || field.type == "C_BigInteger" || field.type == "C_Decimal" || field.type == "C_Double"><#---数字类型-->
    <#--TODO 区分各种数字类型精度 细化校验规则-->
        <input name="${field.dbName}" type="text" value=""
               showType="${field.ctlProperty}"
               validate="{number:true,maxIntLen:${field.numberLength},maxDecimalLen:${field.numberPrecision}<#if field.isRequired == 1>,required:true</#if>}">
    <#elseif field.type == "C_Date"><#---日期类型-->
        <input name="${field.dbName}" type="text" class="Wdate" dateFmt="${field.dateFmt}" displaydate="0" value=""
               validate="{empty:false<#if field.isRequired == 1>,required:true</#if>}">
    <#elseif field.type == "C_DateTime"><#---时间类型-->
        <input name="${field.dbName}" type="text" class="Wdate" dateFmt="${field.dateFmt}" displaydate="0" value=""
               validate="{empty:false<#if field.isRequired == 1>,required:true</#if>}">
    <#elseif field.type == "C_Boolean"><#---是否-->
        <#list field.aryOptions?keys as optkey>
        <label><input type="radio" name="${field.dbName}" value="${optkey}" lablename="${field.aryOptions[optkey]}"
                      validate="{<#if field.isRequired == 1>required:true</#if>}"/>${field.aryOptions[optkey]}</label>
        </#list>
    <#elseif field.type == "C_Text"><#---大文本框-->
        <textarea name="${field.dbName}"
                  validate="{empty:false<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}">
        </textarea>
    <#elseif field.type == "C_File"><#---附件-->
        <div name="div_attachment_container">
            <div class="attachement"></div>
            <textarea style="display:none" controltype="attachment" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" fileType="${field.type}" class="link selectFile" atype="select"
               onclick="AttachMgr.directUpLoadFile(this);"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_Ods"><#---ODS附件-->
        <div name="div_attachment_container">
            <div class="attachement"></div>
            <textarea style="display:none" controltype="attachment" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" fileType="${field.type}" class="link selectFile" atype="select"
               onclick="AttachMgr.directUpLoadFile(this);"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_Hadoop"><#---Hadoop附件-->
        <div name="div_attachment_container">
            <div class="attachement"></div>
            <textarea style="display:none" controltype="attachment" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" fileType="${field.type}" class="link selectFile" atype="select"
               onclick="AttachMgr.directUpLoadFile(this);"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_SingleEnum"><#---单选下来框-->
        <span name="editable-input" style="display:inline-block;padding:2px;" class="selectinput">
                            <select name="${field.dbName}" lablename="${field.text}" controltype="select"
                                    validate='{empty:false<#if field.isRequired == 1>,required:true</#if>}'>
                                <#list field.aryOptions?keys as optkey>
                                    <option value="${optkey}">${field.aryOptions[optkey]}</option>
                                </#list>
                            </select>
        </span>
    <#elseif field.type == "C_MultiEnum"><#---多选下拉框-->
        <input lablename="${field.fieldDesc}" class="staticMultiComboBox" value="" staticStore="${field.selectJson}"
               validate="{empty:false<#if field.isRequired == 1>,required:true</#if>}"
               name="${field.dbName}" height="200" width="200"/>
    <#elseif field.type == "C_SingleTableEnum"><#---单选表枚举约束-->
        <div name="div_tablenum_container">
            <div class="tablenum"></div>
            <textarea style="display:none" controltype="tablenum" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" class="link selectEnumData" atype="select"
               onclick="ModelEnumMgr.showModelData(this,'${field.bindModelId}','${field.displayColumnDBName}',false);"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_MultiTableEnum"><#---多选表枚举约束-->
        <div name="div_tablenum_container">
            <div class="tablenum"></div>
            <textarea style="display:none" controltype="tablenum" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" class="link selectEnumData" atype="select"
               onclick="ModelEnumMgr.showModelData(this,'${field.bindModelId}','${field.displayColumnDBName}',true);"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_Relation"><#---关系属性-->
        <div name="div_relation_container">
            <div class="relation"></div>
            <textarea style="display:none" controltype="relation" name="${field.dbName}"
                      lablename="${field.text}"></textarea>
            <a href="javascript:;" field="${field.dbName}" class="link selectModelData" atype="select"
               onclick="RelationMgr.showModelData(this,'${field.sModelName}','${field.refModelId}','${field.refModelName}','${field.refType}','${field.refModelShowColumn}');"
               validate="{<#if field.isRequired == 1>required:true</#if>}">选择</a>
        </div>
    <#elseif field.type == "C_Check"><#---检查属性-->
        <div name="${field.dbName}" class="checkTable" filedText="${field.text}">
        </div>
        <textarea style="display:none" controltype="checkTable" name="${field.dbName}"
                  lablename="${field.text}"></textarea>
    <#elseif field.type == "C_NameValue"><#---键值对属性-->
        <div name="${field.dbName}" class="dynamicTable" filedText="${field.text}">
        </div>
        <textarea style="display:none" controltype="dynamicTable" name="${field.dbName}"
                lablename="${field.text}"></textarea>
    <#elseif field.type == "C_SubTable"><#---子表属性-->
        <div name="${field.dbName}" class="dynamicTable" filedText="${field.text}">
        </div>
        <textarea style="display:none" controltype="dynamicTable" name="${field.dbName}"
                  lablename="${field.text}"></textarea>
    </#if>

</#macro>