<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员信息修改</title>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
    <style>

    </style>
</head>
<body>
<div class="container-fluid">
    <div class="shadow p-3 mb-4 bg-body-tertiary rounded" id="nav">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#">管理员 - ${requestScope.manager.username}</a>
            </li>
        </ul>
    </div>
    <%--    表单--%>
    <div class="card container-fluid mb-4" id="card">
        <form id="sendForm" class="row g-3 card-body" method="post"
              action="${pageContext.request.contextPath}/role?act=updateManager">
            <input type="hidden" name="id" value="${requestScope.manager.id}">
            <div class="col-6">
                <label for="password" class="form-label">密码</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <div class="col-6">
                <label for="repassword" class="form-label">确认密码</label>
                <input type="password" class="form-control" id="repassword" name="repassword" placeholder="字母或数字">
            </div>
            <div class="col-6">
                <label for="roleid" class="form-label">角色</label>
                <select id="roleid" class="form-select" name="roleid">
                    <option value="0">选择</option>
                </select>
            </div>
            <div class="row mt-4">
                <div class="col-12 text-center">
                    <button type="submit" class="btn btn-primary me-2" id="send" disabled>提交修改</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<script>
    $(function (){
        $.ajax({
            url:'${pageContext.request.contextPath}/role',
            type:'post',
            data:'act=roleListJson',
            dataType:'json',
            success:function (roleList){
                for (let i = 0; i < roleList.length; i++) {
                    let role=roleList[i];
                    let $op=$("<option></option>")
                    $op.attr("value",role.id).text(role.rolename+" - "+role.display)
                    if(role.id==${requestScope.manager.roleid}){
                        $op.prop("selected",true)
                    }
                    $("#roleid").append($op)
                }
            }
        })
    })

    let b1=false;
    let b2=false;
    let b3=false;
    $("#password").blur(function (){
        let val = $("#password").val();
        let reg=/[a-z0-9]+/i;
        if(reg.test(val)){
            b1=true;
            $("#password").removeClass("is-invalid").addClass("is-valid")
        }else{
            b1=false;
            $("#password").removeClass("is-valid").addClass("is-invalid")
        }
        check()
    })

    $("#repassword").blur(function (){
        let val = $("#repassword").val();
        let p = $("#password").val();
        let reg=/[a-z0-9]+/i;
        if(reg.test(val)&&val==p){
            b2=true;
            $("#repassword").removeClass("is-invalid").addClass("is-valid")
        }else{
            b2=false;
            $("#repassword").removeClass("is-valid").addClass("is-invalid")
        }
        check()
    })

    $("#roleid").change(function (){
        let val1 = $("#roleid").val();
        if(val1!=null&&val1.length>0&&val1!="0"){
            b3=true;
        }else{
            b3=false;
        }
        check()
    })

    function check(){
        if((b1&&b2)||b3){
            $("#send").prop("disabled",false)
        }else{
            $("#send").prop("disabled",true)
        }
    }
</script>
</html>