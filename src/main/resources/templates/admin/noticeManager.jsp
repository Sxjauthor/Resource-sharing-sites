<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>公告管理</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body class="bg-primary-subtle">
<div class="container-fluid">
    <button class="btn btn-secondary disabled mb-3">公告管理</button>
    <div class="row py-2">
        <div class="col-12 d-flex justify-content-between align-items-center">
            <form class="d-flex flex-grow-1" method="post" action="${pageContext.request.contextPath}/notice?">
                <input class="form-control me-2 w-50 border-primary" type="search" placeholder="Search" name="title" value="${requestScope.title}">
                <select class="form-select me-2 w-25 border-primary" name="status">
                    <option value="0">状态</option>
                    <option value="1" ${requestScope.status=='1'?'selected':''}>未发布</option>
                    <option value="2" ${requestScope.status=='2'?'selected':''}>发布</option>
                    <option value="3" ${requestScope.status=='3'?'selected':''}>下架</option>
                </select>
                <input type="date" name="start" class="form-control me-2 w-25 border-primary" value="${requestScope.start}">
                <span class="me-2 mt-2">-</span>
                <input type="date" name="end" class="form-control me-2 w-25 border-primary" value="${requestScope.end}">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            <div class="d-flex">
                <a class="btn btn-primary ms-2" href="${pageContext.request.contextPath}/noticeadd.jsp">新增公告</a>
                <a id="del" class="btn btn-danger ms-2" href="">批量下架</a>
            </div>
        </div>
    </div>
</div>

<div class="p-2">
    <table class="table table-hover table-bordered border-primary">
        <thead>
        <tr>
            <th scope="col"><div class="form-check form-check-inline">
                <input class="form-check-input border-primary" type="checkbox" id="inlineCheckbox1" value="option1">
                <label class="form-check-label" for="inlineCheckbox1">全选</label>
            </div>
            <th scope="col">标题</th>
            <th scope="col">作者</th>
            <th scope="col">创建时间</th>
            <th scope="col">发布时间</th>
            <th scope="col">状态</th>
            <th scope="col">阅读量</th>
            <th scope="col">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.page.dataList}" var="notice">
            <tr>
                <th scope="row"><div class="form-check form-check-inline">
                    <input class="form-check-input border-primary" type="checkbox" id="notice${notice.id}" value="${notice.id}">
                </div></th>
                <td>${notice.title}</td>
                <td>${notice.cusername}</td>
                <td>${notice.createtime}</td>
                <td>${notice.publishtime}</td>
                <td>${notice.status}</td>
                <td>${notice.look}</td>
                <td>
                    <c:if test="${notice.status==1&&(notice.creater==sessionScope.manager.id||sessionScope.manager.id==1)}">
                        <a class="btn btn-outline-success btn-sm" href="${pageContext.request.contextPath}/notice?act=publish&id=${notice.id}">发布</a>
                        <a class="btn btn-outline-primary btn-sm" href="${pageContext.request.contextPath}/notice?act=modify&id=${notice.id}">修改</a>
                    </c:if>
                    <c:if test="${notice.status==2}">
                        <c:if test="${notice.creater==sessionScope.manager.id||sessionScope.manager.id==1}">
                            <a class="btn btn-outline-warning btn-sm" href="${pageContext.request.contextPath}/notice?act=throwout&id=${notice.id}">下架</a>
                        </c:if>
                        <a class="btn btn-outline-dark btn-sm" href="${pageContext.request.contextPath}/notice?act=detailM&id=${notice.id}">查看</a>
                    </c:if>
                    <c:if test="${notice.status==3}">
                        <a class="btn btn-outline-dark btn-sm" href="${pageContext.request.contextPath}/notice?act=detailM&id=${notice.id}">查看</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:if test="${requestScope.page.currentPage!=1}">
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/notice?title=${requestScope.title}&status=${requestScope.status}&start=${requestScope.start}&end=${requestScope.end}">首页</a></li>
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/notice?curpage=${requestScope.page.currentPage-1}&title=${requestScope.title}&status=${requestScope.status}&start=${requestScope.start}&end=${requestScope.end}">上一页</a></li>
        </c:if>
        <c:forEach begin="${requestScope.page.SPage}" end="${requestScope.page.EPage}" var="i">
            <li class="page-item ${requestScope.page.currentPage==i?'active':''}"><a class="page-link" href="${pageContext.request.contextPath}/notice?curpage=${i}&title=${requestScope.title}&status=${requestScope.status}&start=${requestScope.start}&end=${requestScope.end}">${i}</a></li>
        </c:forEach>
        <c:if test="${requestScope.page.currentPage!=requestScope.page.totalPages}">
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/notice?curpage=${requestScope.page.currentPage+1}&title=${requestScope.title}&status=${requestScope.status}&start=${requestScope.start}&end=${requestScope.end}">下一页</a></li>
            <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/notice?curpage=${requestScope.page.totalPages}&title=${requestScope.title}&status=${requestScope.status}&start=${requestScope.start}&end=${requestScope.end}">尾页</a></li>
        </c:if>
        <li class="page-item"><a class="page-link">${requestScope.page.currentPage}/${requestScope.page.totalPages}</a></li>
    </ul>
</nav>
<%--toast--%>
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <img src="${pageContext.request.contextPath}/img/favicon.ico" width="20px" class="rounded me-2" alt="...">
            <strong class="me-auto">公告管理</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${requestScope.msg}</div>
    </div>
</div>
</body>
<script>
    $(function (){
        let msg=$(".toast-body").text()
        if(msg!=null&&msg.length>0){
            let toastLiveExample = document.getElementById("liveToast");
            let toast = new bootstrap.Toast(toastLiveExample);
            toast.show()
        }
    })

    $("#inlineCheckbox1").change(function (){
        let prop=$("#inlineCheckbox1").prop("checked")
        if(prop){
            let $checkbox = $(":checkbox[id^=notice]");
            $checkbox.each(function (){
                $(this).prop("checked",true);
            })
        }else{
            let $checkbox = $(":checkbox[id^=notice]");
            $checkbox.each(function (){
                $(this).prop("checked",false);
            })
        }
    })

    $("#del").click(function (){
        let $check = $(":checkbox[id^=notice]:checked");
        if($check.length>0){
            let list=[];
            $check.each(function (){
                let val = $(this).val();
                list.push(val)
            })
            console.log(list)
            let href="${pageContext.request.contextPath}/notice?act=delete&list="+list;
            $("#del").prop("href",href);
        }else{
            e.preventDefault();
        }
    })
</script>
</html>