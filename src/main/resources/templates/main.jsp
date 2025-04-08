<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <title>管理员页面</title>
    <style>
        .w-18{width: 12%;}
        .w-82{width: 88%;}
    </style>
</head>
<body>
<!-- 第一部分 上面的 -->
<div class="container-fluid bg-dark d-flex justify-content-between">
    <a class="d-block p-2" href="${pageContext.request.contextPath}/index.jsp">
        <img src="${pageContext.request.contextPath}/img/logo.png" width="160px"/>
    </a>
    <div>
        <div class="btn-group">
            <div class="dropdown-toggle rounded text-light p-2" data-bs-toggle="dropdown" aria-expanded="false" >
                <img style="cursor: pointer;" src="${pageContext.request.contextPath}/img/head5.jpeg"
                     width="50px"/>
                <span>${sessionScope.manager.rolename} - ${sessionScope.manager.display}</span>
            </div>
            <ul class="dropdown-menu dropdown-menu-end">
                <li><button class="dropdown-item" type="button">签到</button></li>
                <li><button class="dropdown-item" type="button">个人资料</button></li>
                <li><button class="dropdown-item" type="button">上传资料</button></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" type="button" href="${pageContext.request.contextPath}/login2" id="logout">安全退出</a></li>
            </ul>
        </div>
    </div>
</div>

<!-- 第二部分 -->
<div class="container-fluid d-flex p-0 pt-1 justify-content-between" style="height: 630px;">
    <!-- 菜单 -->
    <div class="w-18 bg-dark">
        <div class="d-grid gap-2 col-10 mx-auto pt-2">
            <c:forEach items="${sessionScope.manager.actionList}" var="action">
                <a href="${pageContext.request.contextPath}${action.actionurl}" target="show" class="btn btn-outline-success bg-transparent" type="button">${action.actionname}</a>
            </c:forEach>
        </div>
    </div>
    <!-- iframe -->
    <div class="w-82">
        <iframe style="transition: all 0.3s;" name="show" width="100%" height="100%" frameborder="0" scrolling="auto" src="${pageContext.request.contextPath}/default.jsp"></iframe>
    </div>
</div>
<script>
    $("#logout").click(function (e){
        let b = confirm("确定要退出吗?");
        if(!b){
            e.preventDefault()
        }
    })
</script>
</body>
</html>