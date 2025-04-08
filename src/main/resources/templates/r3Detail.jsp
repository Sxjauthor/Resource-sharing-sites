<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>资源详情</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
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
            width: 1200px;
            margin: 0px auto;
        }
    </style>
</head>
<body>
<%--设置当前页面国际化信息--%>
<fmt:setLocale value="${locale}"/>
<!-- 第一部分 -->
<nav class="navbar sticky-top navbar-expand-lg bg-warning">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/#">
            <img src="${pageContext.request.contextPath}/img/logo.png"/>
        </a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-2 mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/#soft"><fmt:bundle basename="j10"><fmt:message key="UtilitySoftware"/></fmt:bundle></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/#doc"><fmt:bundle basename="j10"><fmt:message key="HotMaterial"/></fmt:bundle></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/#wall" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:bundle basename="j10"><fmt:message key="Wallpaper"/></fmt:bundle>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resDetail?act=search&type=1&search=风景"><fmt:bundle basename="j10"><fmt:message key="scenery"/></fmt:bundle></a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resDetail?act=search&type=1&search=美女"><fmt:bundle basename="j10"><fmt:message key="beauty"/></fmt:bundle></a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resDetail?act=search&type=1&search=设计"><fmt:bundle basename="j10"><fmt:message key="design"/></fmt:bundle></a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/resDetail?act=search&type=1&search=军事"><fmt:bundle basename="j10"><fmt:message key="military"/></fmt:bundle></a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/locale" method="post" id="formLan">
                        <select id="lan" class="form-select" name="locale">
                            <option ${locale=="zh_CN"?'selected':''}>中文</option>
                            <option ${locale=="en_US"?'selected':''}>English</option>
                            <option ${locale=="ko_KR"?'selected':''}>한글</option>
                            <option ${locale=="fr_FR"?'selected':''}>En français</option>
                        </select>
                    </form>
                </li>
            </ul>
            <form class="d-flex me-auto" role="search" method="post" action="${pageContext.request.contextPath}/resDetail?act=search">
                <input class="form-control me-2" size="25" type="search" placeholder="Search" aria-label="Search" name="search" id="search">
                <button class="btn btn-success" type="submit" id="searchBtn"><fmt:bundle basename="j10"><fmt:message key="Search"/></fmt:bundle></button>
            </form>
            <!-- 登录/个人中心 2选1 -->
            <c:if test="${sessionScope.member==null}">
                <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-success"><fmt:bundle basename="j10"><fmt:message key="Login"/></fmt:bundle></a>
            </c:if>
            <c:if test="${sessionScope.member!=null}">
                <div class="btn-group">
                    <img style="cursor: pointer;" src="${head}/${sessionScope.member.head}"
                         class="dropdown-toggle rounded" data-bs-toggle="dropdown" aria-expanded="false" width="40px"/>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><button class="dropdown-item" type="button" disabled>会员-${sessionScope.member.nick}</button></li>
                        <c:if test="${sessionScope.member.signflag==0}">
                            <li><a class="dropdown-item" type="button" href="${pageContext.request.contextPath}/member?act=sign">签到</a></li>
                        </c:if>
                        <c:if test="${sessionScope.member.signflag==1}">
                            <li><a class="dropdown-item disabled" type="button">已签到</a></li>
                        </c:if>
                        <li><a class="dropdown-item disabled" type="button">金币:${sessionScope.member.gold}</a></li>
                        <li><button class="dropdown-item" type="button" id="personalInfoBtn">个人资料</button></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" type="button" href="${pageContext.request.contextPath}/login" id="logoutBtn">安全退出</a></li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</nav>

<%--第二部分 资源详情--%>
<div id="back">
    <a href="javascript:window.history.back()" class="fs-5">>>>返回</a>
</div>
<!-- 第二部分 资源详情 -->
<div class="container-fluid mb-4" id="con">
    <div class="row">
        <div class="col-md-8">
            <img src="${applicationScope.icon}${requestScope.res.thumbnail}" class="img-fluid rounded" alt="图片展示">
        </div>
        <div class="col-md-4">
            <div class="card bg-light">
                <div class="card-body">
                    <h5 class="card-title text-success fw-bold">资源详情</h5>
                    <p class="card-text">分类: ${requestScope.res.supname}${requestScope.res.subname==null?'':'-'}${requestScope.res.subname}</p>
                    <p class="card-text">描述: ${requestScope.res.display}</p>
                    <p class="card-text">价值: ${requestScope.res.isFree}金币</p>
                    <p class="card-text">下载量: ${requestScope.res.down}</p>
                    <p class="card-text">上传人: role${requestScope.res.uploader}</p>
                    <p class="card-text">上传时间: ${requestScope.res.joindate}</p>
                    <a href="${pageContext.request.contextPath}/resDetail?act=downloadD&id=${requestScope.res.id}" class="btn btn-success btn-block" id="get" name="${requestScope.res.isFree}">下载文件</a>
                    &nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/star1.svg" id="fav"/><span id="favspan">${requestScope.res.fav}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="row mt-3">
        <div class="card">
            <div class="card-body">
                <p class="fw-bold">评论</p>
                <hr class="border-secondary">
                <div class="row mb-4">
                    <div class="col-1 pe-0">
                        <img src="${applicationScope.head}/${sessionScope.member.head}" width="50px" height="50px"
                             class="rounded-5"/>
                    </div>
                    <div class="col-10 p-0">
                        <form id="publishForm" action="${pageContext.request.contextPath}/resDetail?act=publish&rid=${requestScope.res.id}&type=${requestScope.res.type}" method="post">
                            <input class="form-control" id="comment" name="comment" type="text" size="90px" style="height: 50px;"
                                   placeholder="我的评价是..."/>
                        </form>
                    </div>
                    <div class="col-1">
                        <a class="btn btn-outline-success btn-sm mt-2" id="publish">发表</a>
                    </div>
                </div>
                <c:forEach items="${requestScope.list}" var="comment">
                    <div class="row mt-1">
                        <div class="col-1 pe-0 pt-2">
                            <img src="${applicationScope.head}/${comment.head}" width="60px" height="60px"
                                 class="rounded-5"/>
                        </div>
                        <div class="col-10 p-0">
                            <label class="form-label">${comment.username}</label>
                            <p class="form-control">${comment.content}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<!-- 最下面的底部-->
<div class="container-fluid bg-dark-subtle myfoot">
    <div class="container py-2 fs-9 text-muted">
        Copyright &copy; 2017-2023 J10的网站 &trade;
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            隐私权声明
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            版权声明
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            素材使用协议
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            联系我们
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            公告
        </a>
        <a href="${pageContext.request.contextPath}/" class="d-block float-end fs-9 text-muted text-decoration-none">豫ICP备12009440号</a>
    </div>
    <hr class="border border-light my-1"/>
    <div class="container  py-2 fs-9">
    <span class="text-muted fs-9"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-link" viewBox="0 0 16 16">
  <path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z"/>
  <path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z"/>
</svg> 友情链接</span>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            隐私权声明
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            版权声明
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            素材使用协议
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            联系我们
        </a>
        <a href="${pageContext.request.contextPath}/" class="fs-9 text-muted text-decoration-none ms-2">
            公告
        </a>
    </div>
</div>
<%--toast--%>
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <img src="${pageContext.request.contextPath}/img/favicon.ico" width="20px" class="rounded me-2" alt="...">
            <strong class="me-auto">资源详情</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${requestScope.msg}</div>
    </div>
</div>
</body>
<script>
    $("#publish").click(function (){
        let val1 = $("#comment").val();
        if(val1!=null&&val1.trim().length>0){
            $("#publishForm").submit();
        }
    })
    $("#personalInfoBtn").click(function (){
        window.location.href="${pageContext.request.contextPath}/member?act=updateSession";
    })
    $("#get").click(function (e){
        let isFree = Number($("#get").prop("name"));
        let gold=Number("${sessionScope.member.gold}");
        if(gold<isFree){
            alert("金币不足,无法下载");
            e.preventDefault();
        }
    })
    $(function(){
        let text=$(".toast-body").text();
        if(text!=null&&text.length>0){
            let toastLiveExample = document.getElementById('liveToast')
            let toast = new bootstrap.Toast(toastLiveExample)
            toast.show()
        }
    })
    $("#fav").click(function (){
        let src = $("#fav").prop("src");
        let s = src.substring(34,35);
        if(s==1){
            $.ajax({
                url:'${pageContext.request.contextPath}/resDetail',
                type:'post',
                data:'act=fav3&i=1&id='+"${requestScope.res.id}",
                dataType:'json',
                success:function (res){
                    let src="${pageContext.request.contextPath}/img/star2.svg";
                    $("#fav").prop("src",src);
                    $("#favspan").text(res.fav)
                }
            })
        }else if(s==2){
            $.ajax({
                url:'${pageContext.request.contextPath}/resDetail',
                type:'post',
                data:'act=fav3&i=-1&id='+"${requestScope.res.id}",
                dataType:'json',
                success:function (res){
                    let src="${pageContext.request.contextPath}/img/star1.svg";
                    $("#fav").prop("src",src);
                    $("#favspan").text(res.fav)
                }
            })
        }
    })
    $(function (){
        $.ajax({
            url:'${pageContext.request.contextPath}/resDetail',
            type:'post',
            data:'act=isFav&id='+"${requestScope.res.id}",
            dataType:'text',
            success:function (b){
                if(b==1){
                    let src="${pageContext.request.contextPath}/img/star2.svg";
                    $("#fav").prop("src",src);
                }else if(b==0){
                    let src="${pageContext.request.contextPath}/img/star1.svg";
                    $("#fav").prop("src",src);
                }

            }
        })
    })
    $("#lan").change(function (){
        $("#formLan").submit();
    })
    $("#searchBtn").click(function (e){
        let val = $("#search").val();
        if(val==null||val.trim().length==0){
            e.preventDefault();
        }
    })
</script>
</html>