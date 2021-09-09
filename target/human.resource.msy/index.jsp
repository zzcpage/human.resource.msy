<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>登陆界面</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap-table.min.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
    <!-- 加载 Jquery 文件 -->
    <script type="text/javascript" src="js/jquery.min.js"></script>

    <!-- 加载bootstrap js 文件 -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-locale-all.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-toolbar.min.js"></script>
    <style>
        #header{
            position: relative;
            left: 590px;
            height: 40px;
            margin-top: 100px;
        }
        #main{
            top: 100px;
            left: 480px;
            position: relative;
            height: 360px;
            width: 480px;
        }
        #info_form{
            left: 40px;
            top: 60px;
            position: relative;
        }
        #btn{
            width: 120px;
        }
    </style>
</head>
<body>
<div id="header">
    <h1>课程管理系统</h1>
</div>
<div id="main">
    <form action="user/login" class='form-horizontal' id='info_form' method="post">
        <div class='form-group'>
            <label class='col-sm-2 control-label'>账号</label>
            <div class='col-sm-6'>
                <input type='text' name="account" class='form-control' placeholder='账号'>
            </div>
        </div>
        <div class='form-group'>
            <label class='col-sm-2 control-label'>密码</label>
            <div class='col-sm-6'>
                <input type='password' name="password" class='form-control' placeholder='密码'>
            </div>
        </div>
        <div class='form-group'>
            <div class="col-sm-offset-2 col-sm-6"><input id="btn" type="submit" value="登录" class="btn-primary" /></div>
        </div>
    </form>
</div>

</body>
</html>

