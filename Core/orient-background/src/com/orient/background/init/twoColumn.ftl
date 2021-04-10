<script>
    function handRowEvent(ev, table) {
        $("td.tdNo", table).each(function (i) {
            $(this).text(i + 1);
        });
    }
</script>
<#setting number_format="0">
<#function getFieldList fieldList>
    <#assign rtn>
        <#assign index=0>
        <#list fieldList as field>
            <#if  field.controlType lt 17>
                <#if index % 2 == 0>
                    <tr>
                </#if>
                    <td align="right" style="width:15%;" class="formTitle" nowrap="nowarp"><span>${field.text}</span>:
                    </td>
                    <td style="width:35%;" class="formInput">
                        <@input field=field/>
                    </td>
                <#if index % 2 == 0 && !field_has_next>
                    <td style="width:15%;" class="formTitle"></td>
                    <td style="width:35%;" class="formInput"></td>
                </#if>
                <#if index % 2 == 1 || !field_has_next>
                    </tr>
                </#if>
                <#assign index=index+1>
            </#if>
        </#list>
    </#assign>
    <#return rtn>
</#function>
<#function getComplexFieldList fieldList>
    <#assign rtn>
        <#list fieldList as field>
            <#if  field.controlType gt 16>
                <@input field=field/>
                </br>
            </#if>
        </#list>
    </#assign>
    <#return rtn>
</#function>
<#function setTeamField teams>
    <#assign rtn>
        <#list teams as team>
        <tr>
            <td colspan="4" class="teamHead">${team.teamName}</td>
        </tr>
        ${getFieldList(team.teamFields)}
        </#list>
    </#assign>
    <#return rtn>
</#function>

<table cellpadding="2" cellspacing="0" border="1" class="formTable">
    <tr>
        <td colspan="4" class="formHead">${table.text }</td>
    </tr>
<#if teamFields??>
    <#if isShow>
        <#if showPosition == 1>
        ${setTeamField(teamFields)}
        ${getFieldList(fields)}
        <#else>
        ${getFieldList(fields)}
        ${setTeamField(teamFields)}
        </#if>
    <#else>
    ${setTeamField(teamFields)}
    </#if>
<#else>
${getFieldList(fields)}
</#if>
</table>
<br/>
${getComplexFieldList(fields)}
<br/>
