/**
 *  用于教师界面的控制
 */
function changeInfo(id) {
    //1. 清空子元素
    $(".right").empty();
    //2. ajax请求数据，然后增加元素
    var param = {
        id: id
    }

    $.ajax({
        url: "/human_resource_msy_war_exploded/teacher/getTeacher",
        data: param,
        type: "POST",
        dataType: "json",
        success: function (data) {

            var divText = "<form class=\"form-horizontal\" id=\"info_form\" >\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">姓名</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" id=\"username\" class=\"form-control\" value='" + data.username + "'  placeholder=\"姓名\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">性别</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <select id='sexSelect' class=\"form-control\"  >\n" +
                "                    <option>男</option>\n" +
                "                    <option>女</option>\n" +
                "                </select>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">生日</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" id=\"birthday\" class=\"form-control\"  value='" + data.birthday + "'  placeholder=\"eg:1998-08-15\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">电话</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" class=\"form-control\" id=\"phone\" value='" + data.phone + "' placeholder=\"(11位电话号码))\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <div class=\"col-sm-offset-2 col-sm-3\">\n" +
                "                <button type=\"button\" class=\"btn btn-primary\" onclick='updateTeacher(" + id + ")'  id=\"info_btn\">修改</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </form>"
            $(".right").append(divText);
            $("#sexSelect").val(data.sex);
        },
        context: this
    })

}

/**
 * 更新用户的数据信息
 */
function updateTeacher(id) {
    var username = $("#username").val();
    var sex = $("#sexSelect").val();
    var birthday = $("#birthday").val();
    var phone = $("#phone").val();
    var param = {
        id: id,
        username: username,
        sex: sex,
        birthday: birthday,
        phone: phone
    }
    console.log(param)
    $.ajax({
        url: "/human_resource_msy_war_exploded/teacher/updateTeacher",
        data: param,
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.state == 1) {
                alert("修改成功");
            } else {
                alert("修改失败");
            }
        }
    });
}

/**
 *  用于浏览课程成绩
 */
function searchCourse(id) {

    //1. 清空右侧的所有数据信息
    $(".right").empty();
    //2. 加载右侧数据内容
    var courseText =
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText);
    initTeacherCourseTable(id);
}

/**
 * 查看选课成员
 * @param id
 */
function searchStaff(id, cid) {
    //1. 清空右侧的所有数据信息
    $(".right").empty();
    //2. 加载右侧数据内容
    var courseText =
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText);
    initTeacherStaffTable(id, cid);
}

/**
 *  用于显示录入成绩表格
 *  @param id  教师唯一标识符
 *  @param cid  课程唯一标识符
 */
function loadStaffSource(id, cid) {
    //1. 清空右侧的所有数据信息
    $(".right").empty();
    //2. 加载右侧数据内容
    var courseText =
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText);
    initLoadSourceTable(id, cid);
}

/**
 * 用于显示教师的课表信息
 * @param tid  教师的唯一标识符
 */
function showTeacherCourse(tid) {
    //1. 清空右侧的所有数据信息
    $(".right").empty();
    //2. 加载右侧数据内容
    var courseText =
        "<!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"downloadCourse\" class=\"btn btn-primary\" >下载课程表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText);
    //3. 设置table的属性
    initLoadTeacherCourseTable(tid);
}

function changePassword(id) {
    //1. 清空子元素
    $(".right").empty();
    //2.  ，然后增加元素


    var divText = "<form class=\"form-horizontal\" id=\"info_form\" >\n" +
        "        <div class=\"form-group\">\n" +
        "            <label   class=\"col-sm-2 control-label\">原密码:</label>\n" +
        "            <div class=\"col-sm-3\">\n" +
        "                <input type=\"password\" id=\"psw-old\" class=\"form-control\" name='password' placeholder=\"旧密码\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"form-group\">\n" +
        "            <label   class=\"col-sm-2 control-label\">新密码:</label>\n" +
        "            <div class=\"col-sm-3\">\n" +
        "                <input type=\"password\" id=\"psw-new\" class=\"form-control\"  name='passwordNew'  placeholder=\"新密码\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"form-group\">\n" +
        "            <div class=\"col-sm-offset-2 col-sm-3\">\n" +
        "                <button type=\"button\" class=\"btn btn-primary\" onclick='updatePassword(" + id + ")'  id=\"info_btn\">修改</button>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </form>"
    $(".right").append(divText);

}

function updatePassword(id) {
    var psw1 = $("#psw-old").val();
    var psw2 = $("#psw-new").val();
    var param = {
        id: id,
        password: psw1,
        passwordNew: psw2
    }
    $.ajax({
        url: "/human_resource_msy_war_exploded/user/updateUserPassword",
        data: param,
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.state == 1) {
                alert("修改成功");
            } else {
                alert("修改失败");
            }
        }
    });
}
