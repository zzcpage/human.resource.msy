<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/8/31
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员界面</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-table.min.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.min.css">
    <!-- 加载 Jquery 文件 -->
    <script type="text/javascript" src="../js/jquery.min.js"></script>

    <!-- 加载bootstrap js 文件 -->
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap-table-locale-all.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap-table-toolbar.min.js"></script>
    <!-- 加载用户自定义 js 文件 -->
    <script type="text/javascript" src="../js/teacher_left.js"></script>
    <script type="text/javascript" src="../js/human_left.js"></script>
    <script type="text/javascript" src="../js/checkInfo.js"></script>
    <script type="text/javascript" src="../js/table_init.js"></script>
    <script type="text/javascript" src="../js/admin_left.js"></script>

    <style>
        .left {
            left: 0;
            top: 120px;
            width: 200px;
            height: 480px;
            position: relative;
            float: left;
        }

        .left li {
            margin-top: 25px;
            margin-bottom: 25px;
        }

        .right {
            top: 80px;
            left: 160px;
            width: 980px;
            height: 720px;
            position: relative;
            float: left;
        }
        .right .fixed-table-toolbar{
            height: 30px;
        }
        .bootstrap-table .fixed-table-container .fixed-table-body{
            height: auto;
        }

    </style>
</head>
<body>
<!-- 界面分成两块区域 ，左侧用于显示功能按钮, 右侧用于显示结果-->
<!-- 左侧功能 1.员工管理 ， 2. 教师管理 ， 3. 课程信息 ， 4.打印信息-->
<div class="left">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul class="nav nav-list well">
                    <li class="active">
                        <a onclick="staffClick()">员工管理</a>
                    </li>
                    <li>
                        <a onclick="teacherClick()">教师管理</a>
                    </li>
                    <li>
                        <a onclick="courseClick()">课程管理</a>
                    </li>
                    <li>
                        <a href="../index.jsp">退出</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="right">

</div>
</body>
</html>

