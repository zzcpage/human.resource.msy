/**
 * 员工左侧功能栏函数模块
 */
function getHumanInfo(id){
    //1. 清空子元素
    $(".right").empty() ;
    //2. ajax请求数据，然后增加元素
    var param = {
        id:id
    }
    console.log(id);
    $.ajax({
        url : "/human_resource_msy_war_exploded/staff/getStaff" ,
        data : param ,
        type : "POST" ,
        dataType:"json",
        success : function (data){
            console.log(data);
            var divText = "<form class=\"form-horizontal\" id=\"info_form\" >\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">姓名</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" id=\"username\" class=\"form-control\" value='"+data.username+"'  placeholder=\"姓名\">\n" +
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
                "                <input type=\"text\" id=\"birthday\" class=\"form-control\"  value='"+data.birthday+"'  placeholder=\"eg:1998-08-15\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">电话</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" class=\"form-control\" id=\"phone\" value='"+data.phone+"' placeholder=\"(11位电话号码))\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <label   class=\"col-sm-2 control-label\">部门</label>\n" +
                "            <div class=\"col-sm-3\">\n" +
                "                <input type=\"text\" class=\"form-control\" id=\"department\" value='"+data.department+"' placeholder=\"(11位电话号码))\">\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"form-group\">\n" +
                "            <div class=\"col-sm-offset-2 col-sm-3\">\n" +
                "                <button type=\"button\" class=\"btn btn-primary\" onclick='updateHumanInfo("+id+")'  id=\"info_btn\">修改</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </form>"
            $(".right").append(divText) ;
            $("#sexSelect").val(data.sex) ;
        },
        context:this
    })
}
function updateHumanInfo(id){
    var username = $("#username").val() ;
    var sex = $("#sexSelect").val() ;
    var birthday = $("#birthday").val();
    var phone = $("#phone").val() ;
    var department = $("#department").val();
    var param = {
        id:id,
        username:username,
        sex:sex,
        birthday:birthday,
        phone:phone,
        department:department
    }
    console.log(param)
    $.ajax({
        url : "/human_resource_msy_war_exploded/staff/updateStaff" ,
        data : param ,
        type : "POST" ,
        dataType:"json",
        success : function (data){
            if(data.state==1){
                alert("修改成功");
            }else{
                alert("修改失败");
            }
        }
    });
}

/**
 * 课程浏览模块，用于显示员工所选课程
 * @param id 员工唯一标识符
 */
function showCource(id){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
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
    $(".right").append(courseText) ;
    initStaffTable(id) ;
}
/**
 *  已选课程模块
 */
function showSelectCource(id){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
    //2. 加载右侧数据内容
    var courseText =
        "<!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"downloadStaffCourse\" class=\"btn btn-primary\" >下载课程表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText) ;
    initStaffSelectCourseTable(id) ;
}
/**
 * 查询课程成绩操作
 */
function searchCourseGrade(id){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
    //2. 加载右侧数据内容
    var courseText =
        "<!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"downloadStaffCourseGrade\" class=\"btn btn-primary\" >下载成绩表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n"
    $(".right").append(courseText) ;
    initStaffGradeTable(id);

}