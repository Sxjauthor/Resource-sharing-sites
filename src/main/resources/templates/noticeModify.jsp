<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改公告</title>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/><!-- bt的样式 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script> <!-- bt的脚本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script><!-- jq的脚本[bt不需要,我们自己需要] -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/string.js/3.3.1/string.min.js"></script>
    <style>
        #nav{
            width:100%;
            border:1px solid darkgray;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="shadow p-3 mb-4 bg-body-tertiary rounded" id="nav">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#">修改公告</a>
            </li>
        </ul>
    </div>
    <%--    表单--%>
    <div class="card container-fluid mb-4" id="card">
        <form id="sendForm" class="row g-3 card-body" method="post" action="${pageContext.request.contextPath}/notice?act=modifyN">
            <input type="hidden" name="id" value="${requestScope.notice.id}">
            <div class="col-6">
                <label for="title" class="form-label" required>标题</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="20个字符以内"
                       maxlength="20" value="${requestScope.notice.title}">
            </div>
            <div class="col-12">
                <label for="content" class="form-label" required>内容</label>
                <textarea name="content" id="content" cols="60" rows="3" maxlength="180">${requestScope.notice.content}</textarea>
            </div>
            <hr>
            <div class="col-12">
                <button type="submit" class="btn btn-primary" id="add" disabled>修改</button>
                <a class="btn btn-secondary" href="javascript:window.history.back()">返回</a>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    let b1=false;
    let b2=false;

    $("#title").blur(function (){
        let title = $("#title").val();
        if(title!=null&&title.trim().length>0&&title!="${requestScope.notice.title}"){
            b1=true;
            $("#title").removeClass("is-invalid").addClass("is-valid")
            check()
        }else{
            b1=false;
            $("#title").removeClass("is-valid").addClass("is-invalid")
            check()
        }
    })

    $("#content").blur(function (){
        let content=$("#content").val();
        if(content!=null&&content.trim().length>0){
            if(content==="${requestScope.notice.content}"){
                b2=false;
                check()
            }else{
                b2=true;
                check()
            }
        }else{
            b2=false;
            check()
        }
    })

    function check(){
        if(b1||b2){
            $("#add").prop("disabled",false);
        }else{
            $("#add").prop("disabled",true);
        }
    }
</script>
</html>