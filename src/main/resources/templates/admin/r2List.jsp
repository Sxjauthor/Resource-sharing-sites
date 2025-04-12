<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>软件资源管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-primary-subtle">
<div class="container-fluid">
    <button class="btn btn-secondary disabled">软件资源管理</button>
    <div class=" py-2 d-flex">
        <form class="d-flex w-50" method="post" action="${pageContext.request.contextPath}/res/soft">
            <input class="form-control me-2 w-50 border-primary" type="text" placeholder="Search" name="resname" value="${requestScope.resname}">
            <select class="form-select me-2 w-25 border-primary" name="status">
                <option value="0">状态</option>
                <option value="1" ${requestScope.status=='1'?'selected':''}>正常</option>
                <option value="2" ${requestScope.status=='2'?'selected':''}>禁用</option>
                <option value="3" ${requestScope.status=='3'?'selected':''}>审核中</option>
            </select>
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
</div>

<div class="p-2">
    <table class="table table-hover table-bordered border-primary table-sm">
        <thead>
        <tr>
            <th scope="col"><div class="form-check form-check-inline">
                <input class="form-check-input border-primary" type="checkbox" id="inlineCheckbox1" value="option1">
                <label class="form-check-label" for="inlineCheckbox1">全选</label>
            </div>
            </th>
            <th scope="col">资源名</th>
            <th scope="col">简介</th>
            <th scope="col">缩略图</th>
            <th scope="col">上传时间</th>
            <th scope="col">上传会员ID</th>
            <th scope="col">价值</th>
            <th scope="col">浏览量</th>
            <th scope="col">收藏量</th>
            <th scope="col">下载量</th>
            <th scope="col">状态</th>
            <th scope="col">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.page.dataList}" var="res">
            <tr>
                <th scope="row"><div class="form-check form-check-inline">
                    <input class="form-check-input border-primary" type="checkbox" id="res${res.id}" value="${res.id}">
                </div></th>
                <td>${res.resname}</td>
                <td>${res.display}</td>
                <td>
                    <img class="img-thumbnail" src="${applicationScope.icon}${res.thumbnail}" width="40px">
                </td>
                <td>${res.joindate}</td>
                <td>${res.uploader}</td>
                <td>${res.isFree}金币</td>
                <td>${res.pv}</td>
                <td>${res.fav}</td>
                <td>${res.down}</td>
                <td class="statusTD">${res.status==1?'正常':''}${res.status==2?'禁用':''}${res.status==3?'审核中':''}</td>
                <td>
                    <button id="ban${res.id}" name="${res.id}" class="btn btn-outline-danger ${res.status==1?'':'d-none'}">禁用</button>
                    <button id="recover${res.id}" name="${res.id}" class="btn btn-outline-warning ${res.status==2?'':'d-none'}">恢复</button>
                    <button id="pass${res.id}" name="${res.id}" class="btn btn-outline-success ${res.status==3?'':'d-none'}">通过</button>
                    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/res/soft?act=comment&id=${res.id}">评论</a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:if test="${requestScope.page.currentPage>1}">
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/res/soft?resname=${requestScope.resname}&status=${requestScope.status}">首页</a></li>
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/res/soft?cur=${requestScope.page.currentPage-1}&resname=${requestScope.resname}&status=${requestScope.status}">上一页</a></li>
        </c:if>
        <c:forEach begin="${requestScope.page.SPage}" end="${requestScope.page.EPage}" var="i">
            <li class="page-item ${i==requestScope.page.currentPage?'active':''}"><a class="page-link" href="${pageContext.request.contextPath}/res/soft?cur=${i}&resname=${requestScope.resname}&status=${requestScope.status}">${i}</a></li>
        </c:forEach>
        <c:if test="${requestScope.page.currentPage<requestScope.page.totalPages}">
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/res/soft?cur=${requestScope.page.currentPage+1}&resname=${requestScope.resname}&status=${requestScope.status}">下一页</a></li>
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/res/soft?cur=${requestScope.page.totalPages}&resname=${requestScope.resname}&status=${requestScope.status}">尾页</a></li>
        </c:if>
        <li class="page-item"><a class="page-link">${requestScope.page.currentPage}/${requestScope.page.totalPages}</a></li>
    </ul>
</nav>
<%--toast--%>
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <img src="${pageContext.request.contextPath}/img/favicon.ico" width="20px" class="rounded me-2" alt="...">
            <strong class="me-auto">软件资源管理</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${requestScope.msg}</div>
    </div>
</div>
</body>
<script>
    $(function(){
        let text=$(".toast-body").text();
        if(text!=null&&text.length>0){
            let toastLiveExample = document.getElementById('liveToast')
            let toast = new bootstrap.Toast(toastLiveExample)
            toast.show()
        }
    })
    $(document).on("click","button[id^=ban]",function (){
        let id = $(this).prop("name");
        window.location.href="${pageContext.request.contextPath}/res/soft?act=ban&id="+id;
    })
    $(document).on("click","button[id^=recover]",function (){
        let id = $(this).prop("name");
        window.location.href="${pageContext.request.contextPath}/res/soft?act=recover&id="+id;
    })
    $(document).on("click","button[id^=pass]",function (){
        let id = $(this).prop("name");
        window.location.href="${pageContext.request.contextPath}/res/soft?act=pass&id="+id;
    })
    $("#inlineCheckbox1").change(function (){
        let prop=$("#inlineCheckbox1").prop("checked")
        if(prop){
            let $checkbox = $(":checkbox[id^=res]");
            $checkbox.each(function (){
                $(this).prop("checked",true);
            })
        }else{
            let $checkbox = $(":checkbox[id^=res]");
            $checkbox.each(function (){
                $(this).prop("checked",false);
            })
        }
    })
</script>
</html>