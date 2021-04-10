<%--
  Created by IntelliJ IDEA.
  User: GNY
  Date: 2018/5/17
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String modelId = request.getParameter("modelId");
    String dataId = request.getParameter("dataId");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css">
    <link/>

    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/jquery-ui-1.8.22.min.js"></script>
    <link rel="stylesheet" href="${ctx}/app/javascript/lib/plupload/base/jquery-ui.css"/>
    <!-- Third party script for BrowserPlus runtime (Google Gears included in Gears runtime now) -->
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/browserplus-min.js"></script>
    <!-- Load plupload and all it's runtimes and finally the jQuery UI queue widget -->
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/plupload.full.js"></script>
    <script type="text/javascript" src="${ctx}/app/javascript/lib/plupload/i18n/zh-cn.js"></script>
    <script type="text/javascript"
            src="${ctx}/app/javascript/lib/plupload/jquery.ui.plupload/jquery.ui.plupload.js"></script>
    <script type="text/javascript">
        // Convert divs to queue widgets when the DOM is ready
        $(function () {
            var initUtl = '${ctx}/modelFile/create.rdm?dataId=<%=dataId%>&modelId=<%=modelId%>';
            $("#uploader").plupload({
                // General settings
                runtimes: 'html5,flash,silverlight,gears,browserplus', // 这里是说用什么技术引擎
                url: initUtl, // 服务端上传路径
                //max_file_size: '10000MB', // 文件上传最大限制。
                //chunk_size: '1000MB', // 上传分块每块的大小，这个值小于服务器最大上传限制的值即可。
                unique_names: true, // 上传的文件名是否唯一
                //是否生成缩略图（仅对图片文件有效）
                resize: {width: 320, height: 240, quality: 90},
                //这个数组是选择器，就是上传文件时限制的上传文件类型
                /*filters: [
                 {title: "Image files", extensions: "image/jpg,image/gif,image/png"},
                 {title: "Office files", extensions: "doc,docx,ppt,pptx,xls,xlsx,pdf"},
                 {title: "Other files", extensions: "txt"}
                 ],*/
                // plupload.flash.swf 的所在路径
                flash_swf_url: '${ctx}/app/javascript/lib/upload/plupload.flash.swf',
                // silverlight所在路径
                silverlight_xap_url: '${ctx}/app/javascript/lib/upload/plupload.silverlight.xap',
                multipart_params: {}
            });
            var uploader = $('#uploader').plupload('getUploader');
            uploader.bind('BeforeUpload', function (up, file) {
                uploader.settings.url = initUtl;
                var secrecyId = '#secrecy' + file.id;
                var fileCatalogId = '#fileCatalog' + file.id;
                var fieDescId = '#fileDesc' + file.id;
                uploader.settings.url = uploader.settings.url + '&secrecy=' + $(secrecyId).val() + '&fileCatalog=' + $(fileCatalogId).val() + '&desc=' + $(fieDescId).val();
            });
            // Client side form validation
            $('form1').submit(function (e) {
                var uploader = $('#uploader').plupload('getUploader');
                // Files in queue upload them first
                if (uploader.files.length > 0) {

                    // When all files are uploaded submit form
                    uploader.bind('BeforeUpload', function () {
                        if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
                            $('form1')[0].submit();
                        }
                    });
                    uploader.start();
                } else
                    alert('You must at least upload one file.');
                return false;
            });
        });
    </script>
</head>
<body>
    <form id="form1">
        <div id="uploader" style="width: 99%;height: 100%;">
            <p>You browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.</p>
        </div>
    </form>
</body>
</html>
