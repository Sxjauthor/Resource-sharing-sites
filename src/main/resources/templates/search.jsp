<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <title>搜索结果</title>
    <style>
        .h-20{height: 20%;}
        .fs-9{font-size: 0.9em;}
        .w-2x{width: 24%; transition: all 0.3s;}
        .h-200{height: 160px;}
        #con2{
            width:100%;
        }
        #searchInfo{
            width:102%;
            background-image: url("${pageContext.request.contextPath}/img/bg.jpg");
            background-position: center center;
            background-repeat: no-repeat;
            background-size: cover;
            height: 50px;
        }
        #aa{
            margin:0 auto;
            display: flex;
            width:250px;
        }
        a{
            text-decoration: none;
            color:black;
        }
        a:hover{
            color:aquamarine;
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
                <input class="form-control me-2" size="25" type="search" placeholder="Search" aria-label="Search" name="search" id="search" value="${requestScope.search}">
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

<%--第二部分 --%>
<div class="container-fluid" id="con2">
    <div class="row" id="searchInfo">
        <div id="aa" class="mt-3">
            <a class="d-block me-3 ${requestScope.type=="2"?'border-bottom border-secondary border-2':''}" href="${pageContext.request.contextPath}/resDetail?act=search&search=${requestScope.search}">软件</a>
            <a class="d-block me-3 ${requestScope.type=="3"?'border-bottom border-secondary border-2':''}" href="${pageContext.request.contextPath}/resDetail?act=search&type=3&search=${requestScope.search}">资料</a>
            <a class="d-block ${requestScope.type=="1"?'border-bottom border-secondary border-2':''}" href="${pageContext.request.contextPath}/resDetail?act=search&type=1&search=${requestScope.search}">壁纸</a>
        </div>
    </div>
    <div class="container-fluid row p-5 pt-3">
        <!-- 列举部分-->
        <div class="mt-2 d-flex flex-wrap justify-content-between align-content-between">
            <c:forEach items="${requestScope.list}" var="res">
                <c:if test="${requestScope.type=='1'}">
                    <a class="d-block w-2x h-200 mb-3 border rounded p-2" href="${pageContext.request.contextPath}/resDetail?act=detail1&id=${res.id}">
                </c:if>
                <c:if test="${requestScope.type=='2'}">
                    <a class="d-block w-2x h-200 mb-3 border rounded p-2" href="${pageContext.request.contextPath}/resDetail?act=detail2&id=${res.id}">
                </c:if>
                <c:if test="${requestScope.type=='3'}">
                    <a class="d-block w-2x h-200 mb-3 border rounded p-2" href="${pageContext.request.contextPath}/resDetail?act=detail3&id=${res.id}">
                </c:if>
                    <div class="d-flex">
                        <c:if test="${requestScope.type=='1'}">
                            <div><img src="${applicationScope.img}${res.thumbnail}" width="100px" height="54px" class="border rounded"/></div>
                        </c:if>
                        <c:if test="${requestScope.type=='2'}">
                            <div><img src="${applicationScope.icon}${res.thumbnail}" width="54px" height="54px" class="border rounded"/></div>
                        </c:if>
                        <c:if test="${requestScope.type=='3'}">
                            <div><img src="${applicationScope.icon}${res.thumbnail}" width="54px" height="54px" class="border rounded"/></div>
                        </c:if>
                        <div class="ms-2 text-muted fs-9">
                                ${res.resname} <br/>
                            <button class="btn-sm bg-success-subtle disabled fs-9 text-muted border-0 rounded">
                                <c:if test="${res.isFree==0}">
                                    免费
                                </c:if>
                                <c:if test="${res.isFree!=0}">
                                    ${res.isFree}金币
                                </c:if>
                            </button>
                        </div>
                    </div>
                    <p class="text-muted fs-9 my-3">${res.display}</p>
                    <div class="d-flex justify-content-between">
                        <div>
                            <span class="fs-9 text-muted"><img src="${pageContext.request.contextPath}/img/calendar.svg"/> ${res.joindate}</span>
                            <span class="ms-2 fs-9 text-muted"><img src="${pageContext.request.contextPath}/img/look.svg"/> ${res.pv}</span>
                        </div>
                        <div>
                            <button class="btn btn-sm btn-outline-success rounded-pill" id="get${res.id}" name="${res.isFree}">获取</button>
                        </div>
                    </div>
                </a>
            </c:forEach>
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
</body>
<script>
    $("#personalInfoBtn").click(function (){
        window.location.href="${pageContext.request.contextPath}/member?act=updateSession";
    })
    $(document).on("click", "button[id^=get]", function (e) {
        e.stopPropagation(); // 阻止事件冒泡
        let isFree=Number($(this).prop("name"));
        let gold = Number("${sessionScope.member.gold}");
        if(gold>=isFree){
            let getId = $(this).prop("id");
            let id = getId.substring(3, getId.length);
            if ("${requestScope.type}" == "1") {
                window.location.href = "${pageContext.request.contextPath}/resDetail?act=downloadW&id=" + id;
            } else if ("${requestScope.type}" == "2") {
                window.location.href = "${pageContext.request.contextPath}/resDetail?act=downloadS&id=" + id;
            } else if ("${requestScope.type}" == "3") {
                window.location.href = "${pageContext.request.contextPath}/resDetail?act=downloadD&id=" + id;
            }
            return false; // 阻止默认行为
        }else{
            alert("金币不足,无法下载");
            return false;
        }
    });

    $(".w-2x").hover(function(){
        $(this).addClass("shadow");
    },function(){
        $(this).removeClass("shadow");
    })

    $(".myfoot a").hover(function(){
        $(this).removeClass("text-muted").addClass("text-warning");
    },function(){
        $(this).removeClass("text-warning").addClass("text-muted");;
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