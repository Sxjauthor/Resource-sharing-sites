<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人资料</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script> <!-- bt的脚本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script><!-- jq的脚本[bt不需要,我们自己需要] -->
    <style>
        .h-20{height: 20%;}
        .fs-9{font-size: 0.9em;}
        .w-2x{width: 24%; transition: all 0.3s;}
        .h-200{height: 160px;}
        #card{
            width:800px;
            margin:auto;
            margin-top:37px;
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

<!--个人信息 展示-->
<div class="card container-fluid mb-4" id="card">
    <form class="row g-3 card-body" method="post" action="${pageContext.request.contextPath}/member?act=modify" enctype="multipart/form-data">
        <div class="col-6">
            <label for="username" class="form-label">用户名</label>
            <input type="text" class="form-control" id="username" value="${sessionScope.member.username}" readonly>
        </div>
        <div class="col-6">
            <label for="joindate" class="form-label">注册日期</label>
            <input type="text" class="form-control" id="joindate" value="${sessionScope.member.joindate}" readonly>
        </div>
        <div class="col-3">
            <label for="level" class="form-label">会员级别</label>
            <input type="text" class="form-control" id="level" value="${sessionScope.member.level}" readonly>
        </div>
        <div class="col-3">
            <label for="gold" class="form-label">金币数量</label>
            <input type="text" class="form-control" id="gold" value="${sessionScope.member.gold}" readonly>
        </div>
        <div class="col-3">
            <label for="concount" class="form-label">连续签到次数</label>
            <input type="text" class="form-control" id="concount" value="${sessionScope.member.concount}" readonly>
        </div>
        <div class="col-3">
            <label for="sign" class="form-label">今日签到</label>
            <input type="text" class="form-control" id="sign" value="${sessionScope.member.signflag==1?'已签到':'未签到'}" readonly>
        </div>
        <hr style="color: #a7acb1;">
        <div class="col-3">
            <label for="nick" class="form-label">修改昵称</label>
            <input type="text" class="form-control" id="nick" name="nick" value="${sessionScope.member.nick}">
        </div>
        <div class="col-7">
            <label for="head" class="form-label">修改头像</label>
            <input type="file" class="form-control" id="head" name="head">
        </div>
        <div class="col-2">
            <label for="img" class="form-label">显示</label>
            <div><img src="${applicationScope.head}/${sessionScope.member.head}" class="img-thumbnail" id="img" width="60px"></div>
        </div>
        <div class="col-6">
            <label for="password" class="form-label">修改密码</label>
            <input type="password" class="form-control" id="password" name="password">
        </div>
        <div class="col-6">
            <label for="repassword" class="form-label">重复密码</label>
            <input type="password" class="form-control" id="repassword" name="repassword">
        </div>
        <div class="col-6">
            <a class="btn btn-warning" id="fav" href="${pageContext.request.contextPath}/resDetail?act=myFav">我的收藏</a>
        </div>
        <div class="col-6">
            <button type="submit" class="btn btn-primary" id="modifyBtn" disabled>提交修改</button>
        </div>
    </form>
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
            <strong class="me-auto">个人信息</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${param.t==1?'修改成功':''}${param.t==2?'没有更改项':''}</div>
    </div>
</div>
<script>
    $("#personalInfoBtn").click(function (){
        window.location.href="${pageContext.request.contextPath}/member?act=updateSession";
    })
    $(function (){
        let t = $(".toast-body").text();
        if(t!=null&&t.length>0){
            let liveToastTemplate=document.getElementById("liveToast")
            new bootstrap.Toast(liveToastTemplate).show()
        }
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

    $("#logoutBtn").click(function (e){
        let b = confirm("确定要退出吗?");
        if(!b){
            e.preventDefault();
        }
    })

    let b1=false;
    let b2=false;
    let b3=false;
    let b4=false;

    $("#head").change(function (){
        let val=this.files[0];
        let url = URL.createObjectURL(val);
        $("#img").prop("src",url)
        b4=true;
        modifyBtn()
    })

    function modifyBtn(){
        if(b1||b4||(b2&&b3)){
            $("#modifyBtn").attr("disabled",false);
        }else{
            $("#modifyBtn").attr("disabled",true);
        }
    }

    $("#nick").blur(function (){
        let sessionN="${sessionScope.member.nick}";
        console.log(sessionN)
        let nick=$("#nick").val()
        let regExp = /[a-z]+|[\u4e00-\u9fa5]+/i;
        if(sessionN!=nick){
            if(regExp.test(nick)){
                $("#nick").removeClass("is-invalid").addClass("is-valid")
                b1=true;
            }else{
                $("#nick").removeClass("is-valid").addClass("is-invalid");
                b1=false;
            }
        }
        modifyBtn()
    })
    $("#password").blur(function (){
        let regExp = /^[0-9a-z]{3,6}$/i;
        let password=$("#password").val()
        if(!regExp.test(password)){
            $("#password").removeClass("is-valid").addClass("is-invalid")
            b2=false;
        }else{
            $("#password").removeClass("is-invalid").addClass("is-valid");
            b2=true;
        }
        modifyBtn()
    })
    $("#repassword").blur(function (){
        let password=$("#password").val()
        let repassword=$("#repassword").val()
        let regExp = /^[0-9a-z]{3,6}$/i;
        // 用==比较  不能用equals
        if(regExp.test(repassword)&&repassword==password){
            $("#repassword").removeClass("is-invalid").addClass("is-valid")
            b3=true;
        }else{
            $("#repassword").removeClass("is-valid").addClass("is-invalid");
            b3=false;
        }
        modifyBtn()
    })

</script>
</body>
</html>