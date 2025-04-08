<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色管理</title>
    <link href="${pageContext.request.contextPath}/img/favicon.ico" rel="icon" sizes="any" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
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
                <a class="nav-link active" aria-current="page" href="#">角色管理</a>
            </li>
        </ul>
    </div>
    <div class="row">
        <div class="col-8">
            <a type="button" href="${pageContext.request.contextPath}/role?act=rolelist" class="btn btn-primary">角色列表</a>
            <a type="button" class="btn btn-success" href="${pageContext.request.contextPath}/role?act=roleAddPre">新增角色</a>
            <a type="button" class="btn btn-danger" href="" id="delsR">批量删除</a>
            <table class="table table-hover table-bordered border-primary mt-2 table-sm">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input border-primary" type="checkbox" id="chk1" value="1">
                                <label class="form-check-label" for="chk1">全选</label>
                            </div>
                        </th>
                        <th scope="col">ID</th>
                        <th scope="col">角色名称</th>
                        <th scope="col">创建时间</th>
                        <th scope="col">简单描述</th>
                        <th scope="col">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.roleList}" var="role">
                    <tr>
                        <th scope="row">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input border-primary" type="checkbox" id="role${role.id}" value="${role.id}">
                            </div>
                        </th>
                        <td>${role.id}</td>
                        <td>${role.rolename}</td>
                        <td>${role.createtime}</td>
                        <td>${role.display}</td>
                        <td>
                            <c:if test="${role.id!=1}">
                                <a type="button" class="btn btn-outline-warning btn-sm" href="${pageContext.request.contextPath}/role?act=actions&roleid=${role.id}&rolename=${role.rolename}&display=${role.display}">修改资源</a>
                                <a type="button" class="btn btn-outline-danger btn-sm delRoleBtn" href="${pageContext.request.contextPath}/role?act=delRole&id=${role.id}">删除角色</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


        <div class="col-12">
            <button type="button" class="btn btn-primary" id="adminBtn">管理员列表</button>
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop">新增管理员</button>
            <a type="button" class="btn btn-danger" href="" id="delsM">批量删除</a>
            <table class="table table-hover table-bordered border-primary mt-2 table-sm" id="adminTab">
                <thead>
                    <tr>
                        <th scope="col">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input border-primary" type="checkbox" id="chk2" value="chk2">
                                <label class="form-check-label" for="chk2">全选</label>
                            </div>
                        </th>
                        <th scope="col">ID</th>
                        <th scope="col">用户名</th>
                        <th scope="col">密码</th>
                        <th scope="col">角色信息</th>
                        <th scope="col">创建时间</th>
                        <th scope="col">操作</th>
                    </tr>
                </thead>
                <tbody id="tbM">

                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="staticBackdropLabel">新增管理员</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="row g-3" method="post" action="${pageContext.request.contextPath}/role?act=addManager">
                    <div class="col-12">
                        <label for="username" class="form-label">username</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="字母或数字" required>
                        <span id="userSpan" style="color: red"></span>
                    </div>
                    <div class="col-md-4">
                        <label for="roleid" class="form-label">角色</label>
                        <select id="roleid" class="form-select" name="roleid">
                            <option value="0">选择</option>
                        </select>
                    </div>
                    <div class="col-12 text-center">
                        <button type="submit" id="sBtn" class="btn btn-primary" disabled>新增</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%--toast--%>
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
            <img src="${pageContext.request.contextPath}/img/favicon.ico" width="20px" class="rounded me-2" alt="...">
            <strong class="me-auto">角色管理</strong>
            <small>刚刚</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">${requestScope.msg}</div>
    </div>
</div>
</body>
<script>
    $("#chk1").change(function (){
        let prop = $("#chk1").prop("checked");
        if(prop){
            let $checkbox = $(":checkbox[id^=role]");
            $checkbox.each(function (){
                $(this).prop("checked",true)
            })
        }else{
            let $checkbox = $(":checkbox[id^=role]");
            $checkbox.each(function (){
                $(this).prop("checked",false)
            })
        }
    })

    $("#chk2").change(function (){
        let prop = $("#chk2").prop("checked");
        if(prop){
            let $checkbox = $(":checkbox[id^=manager]");
            $checkbox.each(function (){
                $(this).prop("checked",true)
            })
        }else{
            let $checkbox = $(":checkbox[id^=manager]");
            $checkbox.each(function (){
                $(this).prop("checked",false)
            })
        }
    })

    $("#delsR").click(function (e){
        let $delsR = $(":checkbox[id^=role]:checked");
        if($delsR.length>0){
            let list=[];
            $delsR.each(function (){
                let val = $(this).val();
                list.push(val)
            })
            let href="${pageContext.request.contextPath}/role?act=delsR&list="+list;
            $("#delsR").prop("href",href)
        }else{
            e.preventDefault()
        }
    })

    $("#delsM").click(function (e){
        let $delsM = $(":checkbox[id^=manager]:checked");
        if($delsM.length>0){
            let list=[];
            $delsM.each(function (){
                let val = $(this).val();
                list.push(val)
            })
            let href="${pageContext.request.contextPath}/role?act=delsM&list="+list;
            $("#delsM").prop("href",href)
        }else{
            e.preventDefault()
        }
    })

    $(".delRoleBtn").click(function (e){
        let con = confirm("确定要删除该角色吗?");
        if(!con){
            e.preventDefault()
        }
    })

    let msg=$(".toast-body").text()
    if(msg!=null&&msg.length>0){
        let toastLiveExample = document.getElementById("liveToast");
        let toast = new bootstrap.Toast(toastLiveExample);
        toast.show()
    }

    let b1=false;
    let b2=false;

    $("#username").blur(function (){
        let reg=/^[a-z0-9]+$/i;
        let val = $("#username").val();
        if(reg.test(val)){
            $.ajax({
                url:'${pageContext.request.contextPath}/role',
                type:'post',
                data:'act=checkName&username='+val,
                dataType:'text',
                success:function (b){
                    if(b=="true"){
                        $("#username").removeClass("is-invalid").addClass("is-valid")
                        $("#userSpan").text("")
                        b1=true;
                        check()
                    }else{
                        $("#username").removeClass("is-valid").addClass("is-invalid")
                        $("#userSpan").text("用户名已存在,请更换")
                        b1=false;
                        check()
                    }
                }
            })
        }else{
            $("#username").removeClass("is-valid").addClass("is-invalid")
            $("#userSpan").text("字母或数字")
            b1=false;
            check()
        }
    })

    $("#roleid").change(function (){
        let val1 = $("#roleid").val();
        if(val1!=null&&val1.length>0&&val1!="0"){
            b2=true;
        }else{
            b2=false;
        }
        check()
    })

    function check(){
        if(b1&&b2){
            $("#sBtn").prop("disabled",false)
        }else{
            $("#sBtn").prop("disabled",true)
        }
    }

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
                    $("#roleid").append($op)
                }
            }
        })
    })

    $("#adminBtn").click(function (){
        $("#tbM").empty()
        $.ajax({
            url:'${pageContext.request.contextPath}/role',
            type:'post',
            data:'act=managerlist',
            dataType:'json',
            success:function (managerList){
                for (let i = 0; i < managerList.length; i++) {
                    let manager=managerList[i];
                    let id=manager.id;
                    let idStr="manager"+id
                    let $tr = $("<tr></tr>");
                    let $th=$("<th scope='row'></th>")
                    let $div = $("<div class='form-check form-check-inline'></div>");
                    let $input = $("<input class='form-check-input border-primary' type='checkbox'>");
                    $input.attr("id",idStr).attr("value",id)
                    $div.append($input)
                    $th.append($div)
                    $tr.append($th)
                    let $td1 = $("<td></td>");
                    $td1.text(manager.id)
                    let $td2 = $("<td></td>");
                    $td2.text(manager.username)
                    let $td3 = $("<td></td>");
                    $td3.text(manager.password)
                    let $td4 = $("<td></td>");
                    $td4.text(manager.rolename+" - "+manager.display)
                    let $td5 = $("<td></td>");
                    $td5.text(manager.createtime)
                    let $td6 = $("<td></td>");
                    let $btn1=$(`<a type="button" class="btn btn-outline-warning btn-sm">修改</a>`)
                    let href1="${pageContext.request.contextPath}/role?act=modifyM&id="+id+
                        "&username="+manager.username+"&display="+manager.display+"&createtime="+manager.createtime
                        +"&roleid="+manager.roleid;
                    $btn1.attr("href",href1)
                    let $btn2=$(`<a type="button" class="btn btn-outline-danger btn-sm delM">删除</a>`)
                    let href2="${pageContext.request.contextPath}/role?act=deleteM&id="+id;
                    $btn2.attr("href",href2)
                    if(id!=1){
                        $td6.append($btn1).append($btn2)
                    }
                    $tr.append($td1).append($td2).append($td3).append($td4).append($td5).append($td6)
                    $("#tbM").append($tr)
                }
            }
        })
    })
    //这种方式对于由ajax动态生成的按钮可能无效
    //因为事件在页面加载的时候绑定，但那时候按钮还没有出现在页面上(DOM文档树上)
    // $(".delM").click(function (e){
    //     let con1 = confirm("确定要删除该管理员吗?");
    //     if(!con1){
    //         e.preventDefault()
    //     }
    // })
    // 使用事件委托绑定删除按钮的点击事件
    $(document).on("click", ".delM", function (e) {
        let con1 = confirm("确定要删除该管理员吗?");
        if (!con1) {
            e.preventDefault();
        }
    });
</script>
</html>