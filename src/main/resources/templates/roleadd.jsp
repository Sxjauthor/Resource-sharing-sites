<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新增角色</title>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/><!-- bt的样式 -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script> <!-- bt的脚本 -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script><!-- jq的脚本[bt不需要,我们自己需要] -->
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
                <a class="nav-link active" aria-current="page" href="#">新增角色</a>
            </li>
        </ul>
    </div>
    <%--    表单--%>
    <div class="card container-fluid mb-4" id="card">
        <form id="sendForm" class="row g-3 card-body" method="post" action="${pageContext.request.contextPath}/role?act=roleAdd">
            <input type="hidden" name="roleid" value="${requestScope.roleid}">
            <div class="col-6">
                <label for="rolename" class="form-label" required>角色名</label>
                <input type="text" class="form-control" id="rolename" name="rolename" placeholder="2-5位字母或数字组成,大写字母开头">
                <span id="roleSpan" style="color: red"></span>
            </div>
            <div class="col-6">
                <label for="display" class="form-label" required>简短描述</label>
                <input type="text" class="form-control" id="display" name="display">
            </div>
            <hr>
            <c:forEach items="${requestScope.actions}" var="action">
                <div class="col-4">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="action${action.id}" name="actions" value="${action.id}">
                        <label class="form-check-label" for="action${action.id}">
                                ${action.actionname}
                        </label>
                    </div>
                </div>
            </c:forEach>
            <div class="col-12">
                <button type="submit" class="btn btn-primary" id="add">新增</button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    let b1=false;
    let b2=false;
    $("#rolename").blur(function (){
        let reg=/^[A-Z][a-zA-Z0-9]{1,4}$/;
        let val = $("#rolename").val();
        if(reg.test(val)){
            $.ajax({
                url:'${pageContext.request.contextPath}/role',
                type:'post',
                data:'act=checkRoleName&rolename='+val,
                dataType:'text',
                success:function (b){
                    if(b=="true"){
                        $("#rolename").removeClass("is-invalid").addClass("is-valid")
                        $("#roleSpan").text("")
                        b1=true;
                    }else{
                        $("#rolename").removeClass("is-valid").addClass("is-invalid")
                        $("#roleSpan").text("用户名已存在,请更换")
                        b1=false;
                    }
                }
            })
        }else{
            $("#rolename").removeClass("is-valid").addClass("is-invalid")
            $("#roleSpan").text("2-5位字母或数字组成,大写字母开头")
            b1=false;
        }
    })

    $("#display").blur(function (){
        let display = $("#display").val();
        if(display!=null&&display.length>0){
            $("#display").removeClass("is-invalid").addClass("is-valid")
            b2=true;
        }else{
            $("#display").removeClass("is-valid").addClass("is-invalid")
            b2=false;
        }
    })

    $("#add").click(function (e){
        if(b1&&b2){
            let $checkbox = $(":checkbox[name='actions']:checked");
            let list=[];
            $checkbox.each(function (){
                list.push(this.value)
            })
            let str = list.join("=");
            if(str.length==0||str==null){
                e.preventDefault()
            }
        }else{
            e.preventDefault()
        }
    })
</script>
</html>