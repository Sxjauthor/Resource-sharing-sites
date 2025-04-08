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
    <title>公告详情</title>
    <style>
        body{
            background-color: #F6F6F6;
        }
        #back{
            height:45px;
        }
        a{
            text-decoration: none;
            color:#6B6B6B;
            line-height: 45px;
        }
        a:hover{
            color:#FFC107;
        }
        #con{
            width:80%;
            align-items: center;
            background-color: white;
        }
        #nav{
            width:100%;
            border:1px solid darkgray;
        }
    </style>
</head>
<body>
<div class="shadow p-3 mb-2 bg-body-tertiary rounded" id="nav">
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">公告详情</a>
        </li>
    </ul>
</div>
<%--第二部分 公告--%>
<div id="back">
    <a href="javascript:window.history.back()" class="fs-5">>>>返回</a>
</div>
<div class="container-fluid p-3 mb-4" id="con">
    <div class="row">
        <p class="text-center fw-bold fs-3 m-0 p-2">${requestScope.notice.title}</p>
    </div>
    <hr class="bg-dark-subtle">
    <div class="row m-2">
        <p class="text-center fs-6 fst-italic m-0 p-0">作者:${requestScope.notice.cusername}</p>
    </div>
    <div class="row m-3">
        <p class="text-center fs-5 m-0 p-0">${requestScope.notice.content}</p>
    </div>
    <div class="row">
        <p class="text-center fs-6 p-3">发布日期:${requestScope.notice.publishtime}&nbsp;&nbsp;&nbsp;
        阅读量:${requestScope.notice.look}</p>
    </div>
</div>
</body>
<script>
    $.ajax({
        url:'${pageContext.request.contextPath}/noticeMem?act=look',
        type:'post',
        data:'id=${requestScope.notice.id}',
    })
</script>
</html>