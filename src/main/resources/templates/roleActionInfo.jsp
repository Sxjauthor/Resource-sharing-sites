<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色资源信息</title>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/><!-- bt的样式 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script> <!-- bt的脚本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script><!-- jq的脚本[bt不需要,我们自己需要] -->
    <style>
        #nav {
            width: 100%;
            border: 1px solid darkgray;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="shadow p-3 mb-4 bg-body-tertiary rounded" id="nav">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#">${requestScope.rolename}
                    - ${requestScope.display}</a>
            </li>
        </ul>
    </div>
    <%--    表单--%>
    <div class="card container-fluid mb-4" id="card">
        <form id="sendForm" class="row g-3 card-body" method="post"
              action="${pageContext.request.contextPath}/role?act=updateRole">
            <input type="hidden" name="roleid" value="${requestScope.roleid}">
            <div class="col-6">
                <label for="rolename" class="form-label">角色名</label>
                <input type="text" class="form-control" id="rolename" name="rolename" value="${requestScope.rolename}">
            </div>
            <div class="col-6">
                <label for="display" class="form-label">简短描述</label>
                <input type="text" class="form-control" id="display" name="display" value="${requestScope.display}">
            </div>
            <hr>
            <c:forEach items="${requestScope.actions}" var="action">
                <div class="col-4">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" ${action.have?"checked":""}
                               id="action${action.id}" name="actions" value="${action.id}">
                        <label class="form-check-label" for="action${action.id}">
                                ${action.actionname}
                        </label>
                    </div>
                </div>

            </c:forEach>
            <div class="row mt-4">
                <div class="col-12 text-center">
                    <button type="button" class="btn btn-primary me-2" id="send">提交修改</button>
                    <button type="button" class="btn btn-secondary" id="return">返回</button>
                </div>
            </div>
        </form>
    </div>
</div>
<%--toast--%>
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <img src="${pageContext.request.contextPath}/img/favicon.ico" width="20px" class="rounded me-2" alt="...">
            <strong class="me-auto">角色信息修改</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${requestScope.msg}</div>
    </div>
</div>
</body>
<script>
    $("#return").click(function () {
        window.history.back();
    })

    let oldName = $("#rolename").val()
    let oldDisplay = $("#display").val()
    let oldHave = [];
    $(":checkbox[id^='action'][name^='action']:checked").each(function () {
        oldHave.push(this.value)
    })

    $("#send").click(function () {
        let b1 = $("#rolename").val() == oldName
        let b2 = $("#display").val() == oldDisplay
        let newHave = [];
        $("checkbox[id^='action'][name^='action']:checked").each(function () {
            newHave.push(this.value)
        })
        let b3 = newHave.join("=") == oldHave.join("=")
        if (!(b1 && b2 && b3)) {
            $("#sendForm").submit()
        }
    })

    $(function () {
        let msg = $(".toast-body").text()
        if (msg != null && msg.length > 0) {
            let toastLiveExample = document.getElementById("liveToast");
            let toast = new bootstrap.Toast(toastLiveExample);
            toast.show()
        }
    })

</script>
</html>