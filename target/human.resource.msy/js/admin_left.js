/**
 * 用于为主界面右侧的按钮提供函数支持
 */

/**
 * 员工管理模块
 */
function staffClick(){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
    //2. 加载右侧数据内容
    var rightText = "<!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"add\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">\n" +
        "            添加\n" +
        "        </button>\n" +
        "        <button id=\"delete\" class=\"btn btn-secondary\" disabled>删除</button>\n" +
        "        <button id=\"downloadAdminStaff\" class=\"btn btn-primary\" >下载员工表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>" ;
    var modelText = "<!-- 添加用户的模态框 -->\n" +
        "<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
        "    <div class=\"modal-dialog\">\n" +
        "        <div class=\"modal-content\">\n" +
        "            <div class=\"modal-header\">\n" +
        "                <h4 class=\"modal-title\" id=\"myModalLabel\">\n" +
        "                    用户信息\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <!-- 模态框的内容区域 即显示的表项-->\n" +
        "            <div class=\"modal-body\">\n" +
        "                <div class=\"row\">\n" +
        "                    <label for=\"name\" class=\"col-sm-4 control-label\">姓名:</label>\n" +
        "                    <div class=\"col-sm-8\">\n" +
        "                        <input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"请输入姓名\" />\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"row\">\n" +
        "                    <label for=\"sex\" class=\"col-sm-4 control-label\">性别:</label>\n" +
        "                    <div class=\"col-sm-8\">\n" +
        "                        <select id=\"sex\" class=\"form-control\">\n" +
        "                            <option>男</option>\n" +
        "                            <option>女</option>\n" +
        "                        </select>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"row\">\n" +
        "                    <label for=\"birthday\" class=\"col-sm-4 control-label\">生日:</label>\n" +
        "                    <div class=\"col-sm-8\">\n" +
        "                        <input type=\"text\" class=\"form-control\" id=\"birthday\" placeholder=\"请输入员工生日\" />\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"row\">\n" +
        "                    <label for=\"phone\" class=\"col-sm-4 control-label\">电话:</label>\n" +
        "                    <div class=\"col-sm-8\">\n" +
        "                        <input type=\"text\" class=\"form-control\" id=\"phone\" placeholder=\"请输入员工联系电话\" />\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"row\">\n" +
        "                    <label for=\"department\" class=\"col-sm-4 control-label\">部门:</label>\n" +
        "                    <div class=\"col-sm-8\">\n" +
        "                        <input type=\"text\" class=\"form-control\" id=\"department\" placeholder=\"请输入员工部门\" />\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "\n" +
        "            <!-- 模态框的操作区域 增加和关闭 -->\n" +
        "            <div class=\"modal-footer\">\n" +
        "                <button id=\"add-moadl\" type=\"button\" class=\"btn btn-primary\">\n" +
        "                    添加\n" +
        "                </button>\n" +
        "                <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">\n" +
        "                    关闭\n" +
        "                </button>\n" +
        "            </div>\n" +
        "        </div><!-- /.modal-content -->\n" +
        "    </div><!-- /.modal -->\n" +
        "</div>" ;
        rightText = rightText+modelText ;
        $(".right").append(rightText) ;
        initAdminStaffTable();
}

/**
 * 教师管理模块
 */
function teacherClick(){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
    //2. 加载右侧数据内容
    var divText = "<!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"add\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">\n" +
        "            添加\n" +
        "        </button>\n" +
        "        <button id=\"delete\" class=\"btn btn-secondary\" disabled>删除</button>\n" +
        "        <button id=\"downloadAdminTeacher\" class=\"btn btn-primary\" >下载教师表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>" ;
    var modelText = " <!-- 添加用户的模态框 -->\n" +
        "    <div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
        "        <div class=\"modal-dialog\">\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header\">\n" +
        "                    <h4 class=\"modal-title\" id=\"myModalLabel\">\n" +
        "                        教师信息\n" +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <!-- 模态框的内容区域 即显示的表项-->\n" +
        "                <div class=\"modal-body\">\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"name\" class=\"col-sm-4 control-label\">姓名:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"请输入姓名\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"sex\" class=\"col-sm-4 control-label\">性别:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <select id=\"sex\" class=\"form-control\">\n" +
        "                                <option>男</option>\n" +
        "                                <option>女</option>\n" +
        "                            </select>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"birthday\" class=\"col-sm-4 control-label\">生日:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"birthday\" placeholder=\"请输入员工生日\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"phone\" class=\"col-sm-4 control-label\">电话:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"phone\" placeholder=\"请输入员工联系电话\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "\n" +
        "                <!-- 模态框的操作区域 增加和关闭 -->\n" +
        "                <div class=\"modal-footer\">\n" +
        "                    <button id=\"add-moadl\" type=\"button\" class=\"btn btn-primary\">\n" +
        "                        添加\n" +
        "                    </button>\n" +
        "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">\n" +
        "                        关闭\n" +
        "                    </button>\n" +
        "                </div>\n" +
        "            </div><!-- /.modal-content -->\n" +
        "        </div><!-- /.modal -->\n" +
        "    </div>" ;
    $(".right").append(divText+modelText) ;
    initTeacherTable();
}

/**
 *  课程信息管理模块
 */
function courseClick(){
    //1. 清空右侧的所有数据信息
    $(".right").empty() ;
    //2. 加载右侧数据内容
    var courseText = " <!-- 功能列表区域-->\n" +
        "    <div id=\"toolbar\">\n" +
        "        <button id=\"add\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#myModal\">\n" +
        "            添加\n" +
        "        </button>\n" +
        "        <button id=\"delete\" class=\"btn btn-secondary\" disabled>删除</button>\n" +
        "        <button id=\"downloadAdminCourse\" class=\"btn btn-primary\" >下载课程表</button>\n" +
        "    </div>\n" +
        "    <!-- 设置表格属性 -->\n" +
        "    <table id=\"usertable\"\n" +
        "           data-toolbar=\"#toolbar\"\n" +
        "           data-show-refresh=\"true\"\n" +
        "           data-show-toggle=\"true\"\n" +
        "           data-id-field=\"id\"\n" +
        "           data-pagination=\"true\">\n" +
        "    </table>\n" +
        "    <!-- 添加用户的模态框 -->\n" +
        "    <div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
        "        <div class=\"modal-dialog\">\n" +
        "            <div class=\"modal-content\">\n" +
        "                <div class=\"modal-header\">\n" +
        "                    <h4 class=\"modal-title\" id=\"myModalLabel\">\n" +
        "                        课程信息\n" +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <!-- 模态框的内容区域 即显示的表项-->\n" +
        "                <div class=\"modal-body\">\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"cname\" class=\"col-sm-4 control-label\">课程名称:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"cname\" placeholder=\"请输入课程名称\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"addr\" class=\"col-sm-4 control-label\">开课地点:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" id=\"addr\" class=\"form-control\" placeholder=\"请输入开课地点\">\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"time\" class=\"col-sm-4 control-label\">时间:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"time\" placeholder=\"请输入开课时间\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"intrduce\" class=\"col-sm-4 control-label\">课程介绍:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"intrduce\" placeholder=\"请输入课程介绍\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"num\" class=\"col-sm-4 control-label\">数量:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"num\" placeholder=\"请输入课程人数\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"phone\" class=\"col-sm-4 control-label\">授课老师电话:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"phone\" placeholder=\"请输入授课老师电话\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"qualified\" class=\"col-sm-4 control-label\">员工合格薪资/人:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"qualified\" placeholder=\"请输入员工合格薪资/人\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"row\">\n" +
        "                        <label for=\"basic\" class=\"col-sm-4 control-label\">基础薪资:</label>\n" +
        "                        <div class=\"col-sm-8\">\n" +
        "                            <input type=\"text\" class=\"form-control\" id=\"basic\" placeholder=\"请输入基础薪资\" />\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <!-- 模态框的操作区域 增加和关闭 -->\n" +
        "                <div class=\"modal-footer\">\n" +
        "                    <button id=\"add-moadl\" type=\"button\" class=\"btn btn-primary\">\n" +
        "                        添加\n" +
        "                    </button>\n" +
        "                    <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">\n" +
        "                        关闭\n" +
        "                    </button>\n" +
        "                </div>\n" +
        "            </div><!-- /.modal-content -->\n" +
        "        </div><!-- /.modal -->\n" +
        "    </div>";
    $(".right").append(courseText) ;
    initCourseTable();
}


function adminShowStaffGrade(id){
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
    initAdminStaffGrade(id);
}

/**
 * 显示教师的具体薪资
 */
function  showDetailPay(tid){
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
    initTeacherInfoPay(tid);
}

