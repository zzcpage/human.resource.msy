/**
 * 该js文件主要用于设置table表格的属性，以及设计table表格的ajax查询操作
 */

/**
 * 初始化管理员员工表格属性
 */
function initAdminStaffTable() {
    //1. 获取到表格对象以及增加删除员工按钮对象
    var $table = $("#usertable");   // 获取表单标签元素
    var $addbtn = $("#add-moadl");  // 获取增加用户按钮
    var $deletebtn = $("#delete"); // 获取删除用户按钮

    //2. 为增加员工按钮绑定增加员工事件

    function addUser() {
        // 方法功能: 该方法用于模态框增加用户时候进行验证，并提交给后台
        // 方法描述: 先获取到用户输入的用户各个信息，然后校验用户信息，如果校验完毕
        // 通过AJAX向后台发送新增用户信息，根据AJAX返回的结果提示用户操作的结果

        var username = $("#name").val(); //获取用户名
        var sex = $("#sex").val();     //获取性别
        var birthday = $("#birthday").val();     // 获取生日
        var phone = $("#phone").val();    //获取电话
        var department = $("#department").val();   // 获取部门

        // 下列几个判断语句用于检验表单各项值是否符合规范，如果不符合则返回-1
        if (checkUserName(username) == -1) {
            $("#name").focus();
            return;
        }
        if (checkBirthday(birthday) == -1) {
            $("#birthday").focus();
            return;
        }
        if (checkPhone(phone) == -1) {
            $("#phone").focus();
            return;
        }
        if (checkDepartment(department) === -1) {
            $("#department").focus();
            return;
        }
        var param = {
            username: username,
            sex: sex,
            birthday: birthday,
            phone: phone,
            department: department
        }

        //  ajax将新增用户数据以json格式传送给后端，进行用户的增加
        //  后端增加成功则返回data data为1 增加成功 data为-3 表示用户名重名 data为0表示增加失败
        $.ajax({
            url: "/human_resource_msy_war_exploded/user/addHuman",
            data: param,
            type: "GET",
            dataType: "json",
            success: function (data) {
                console.log(data.state);
                //  根据返回的data进行相应的处理
                //  data为1 表示增加成功 则关闭模态框，刷新表格数据
                //  data为-3 提示用户重名
                //  data为其他值 提示用户增加失败
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    $table.bootstrapTable("refresh");
                    $("#myModal").modal("hide");
                } else if ((typeof (data) != "undefined") && (data == -3)) {
                    alert("用户名已存在，无法添加");
                } else {
                    alert("增加失败")
                }
            },
            context: this
        });
    }
    $addbtn.bind("click", addUser);

    //3. 为删除员工按钮绑定删除事件
    function delUser() {
        // 方法功能: 该方法用于删除表格选中的用户
        // 方法描述: 首先获取到要删除用户的id列表，然后通过AJAX发送到后端，后端根据结构到的用户id
        // 进行删除,并将删除结果返回给页面

        // 通过getSelections 获取选中的用户列表,然后获取选中用户的id值，保存到userIds中
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            return;
        }

        var userIds = "";
        for (var i = 0; i < selects.length; i++) {
            userIds = userIds + selects[i].id + ',';
        }

        //构建请求参数,传送用户的id数据
        var param = {
            userIds: userIds
        };
        //  ajax请求,将数据发送到删除用户的控制器中
        $.ajax({
            url: "/human_resource_msy_war_exploded/staff/deleteStaff",
            data: param,
            type: "post",
            success: function (data) {
                //  根据返回的结果data进行处理,data为0表示删除成功，则刷新页面数据
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    alert("删除成功")
                    $table.bootstrapTable("refresh");
                }else{
                    console.log("in")
                    alert("删除失败，存在用户有已选课程")
                }
                // 将删除按钮恢复禁用状态
                $("#delete").attr("disabled", "disabled");
            },
            context: this
        });
    }
    $deletebtn.bind("click", delUser);
    //4. 下载员工列表
    var $downloadAdminStaff = $("#downloadAdminStaff") ;
    $downloadAdminStaff.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/staff/downloadStaff") ;
        console.log(forms.action);
        forms.submit() ;
    }) ;

    //4. 设置查看员工按钮事件
    function addFunctionAlty(e, value, row, index) {
        return [
            '<button id="staff" type="button" class="btn btn-primary">查看成绩</button>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            adminShowStaffGrade(row.id) ;
        }
    };

    // 5. 为table初始化属性设置
    // url: 数据来源地址
    // method：请求方式
    // columns：列属性
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/staff/limitPageStaff",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            }, {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            }, {
                field: 'username',
                title: "用户名",
                align: "center",
                valign: "middle"
            }, {
                field: 'sex',
                title: "性别",
                align: "center",
                valign: "middle"
            }, {
                field: 'birthday',
                title: "生日",
                align: "center",
                valign: "middle"
            }, {
                field: 'phone',
                title: "电话",
                align: "center",
                valign: "middle"
            }, {
                field: 'department',
                title: "部门",
                align: "center",
                valign: "middle"
            },{
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        /* 配置选择事件 */
        "onCheck": changeBtn,
        "onUncheck": changeBtn,
        "onCheckAll": changeBtn,
        "onUncheckAll": changeBtn,
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "用户名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        },
        onDblClickCell: function (field, value, row, $element) {
            // 方法功能: 用于使得用户可用行编辑数据 用于修改用户信息
            // 方法描述: 通过绑定表格双击事件使得表格可编辑 然后将修改后的数据传送给后台服务器
            // 如果修改成功则加载修改后的数据否则恢复原来的数据

            //设置表项可编辑
            $element.attr('contenteditable', true);
            $element.blur(function () {
                // 方法功能: 当用户编辑完成后，就把数据提交给后台
                // 方法描述: 用户编辑后，失去焦点，把数据提交给后台，然后对返回结果进行处理
                var flag = 0;  //作为用户数据是否合法的标志 flag为1表示不合法，则无法修改
                var tdValue = $element.html();  //修改后表项的数据
                var index = $element.parent().data('index'); //表项下标
                // switch 根据用户修改的列项，进行判断相应修改的数据是否合法，不合法则修改标记变量
                switch (field) {
                    case "id":
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "account" :
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "username":
                        if (checkUserName(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "birthday" :
                        if (checkBirthday(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "phone":
                        if (checkPhone(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "department" :
                        if (checkDepartment(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                }
                if (flag == 1) {
                    // 编辑后的数据格式有误则恢复原理啊的数据
                    $table.bootstrapTable('updateCell', {
                        index: index,       //行索引
                        field: field,       //列名
                        value: value        //原来的旧值
                    });
                    return;
                }
                row[field] = tdValue;//修改指定键的值
                // 向用户更新控制器发送请求，进行用户的更新操作
                $.ajax({
                    url: "/human_resource_msy_war_exploded/staff/updateStaff",
                    type: "post",
                    data: row,
                    success: function (data) {
                        console.log(data)
                        console.log(data.state);
                        // 根据返回data判断数据是否更新成功
                        // data为1表示更新成功 data为-4表示年龄数据出错,其它数据表示更新失败
                        if (!((typeof (data) != "undefined") && (data.state == 1))) {
                            alert("更新失败")
                            //更新列表数据 恢复原来的数据
                            $table.bootstrapTable('updateCell', {
                                index: index,       //行索引
                                field: field,       //列名
                                value: value        //cell值
                            });
                        }

                    }
                });
            });
        }
    });

    /**
     * 修改删除按钮禁用状态
     */
    function changeBtn() {
        // 方法功能：修改删除按钮的禁用状态,没有被选行则按钮为禁用状态反之为可用状态
        // 方法描述: 获取表格被选中的行，如果没有被选中的行则设置按钮为禁用，反之设置为可用
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            $("#delete").attr("disabled", "disabled");
        } else {
            $("#delete").removeAttr("disabled");
        }
    }

    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/**
 * 该js函数用于控制教师信息表格的
 * 增加教师，查看教师信息，搜索教师信息，删除教师信息等操作
 * 以及对table属性的设置
 */
function initTeacherTable() {

    var $table = $("#usertable");   // 获取表单标签元素
    var $addbtn = $("#add-moadl");  // 获取增加用户按钮
    var $deletebtn = $("#delete"); // 获取删除用户按钮

    /**
     * 为了实现增加教师的功能
     */
    function addTeacher() {
        // 方法功能: 该方法用于模态框增加用户时候进行验证，并提交给后台
        // 方法描述: 先获取到用户输入的用户各个信息，然后校验用户信息，如果校验完毕
        // 通过AJAX向后台发送新增用户信息，根据AJAX返回的结果提示用户操作的结果

        var username = $("#name").val(); //获取用户名
        var sex = $("#sex").val();     //获取性别
        var birthday = $("#birthday").val();     // 获取生日
        var phone = $("#phone").val();    //获取电话

        // 下列几个判断语句用于检验表单各项值是否符合规范，如果不符合则返回-1
        if (checkUserName(username) == -1) {
            $("#name").focus();
            return;
        }
        if (checkBirthday(birthday) == -1) {
            $("#birthday").focus();
            return;
        }
        if (checkPhone(phone) == -1) {
            $("#phone").focus();
            return;
        }

        var param = {
            username: username,
            sex: sex,
            birthday: birthday,
            phone: phone
        }

        //  ajax将新增用户数据以json格式传送给后端，进行用户的增加
        //  后端增加成功则返回data data为1 增加成功 data为-3 表示用户名重名 data为0表示增加失败
        $.ajax({
            url: "/human_resource_msy_war_exploded/user/addTeacher",
            data: param,
            type: "GET",
            dataType: "json",
            success: function (data) {
                console.log(data.state);
                //  根据返回的data进行相应的处理
                //  data为1 表示增加成功 则关闭模态框，刷新表格数据
                //  data为-3 提示用户重名
                //  data为其他值 提示用户增加失败
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    $table.bootstrapTable("refresh");
                    $("#myModal").modal("hide");
                } else if ((typeof (data) != "undefined") && (data == -3)) {
                    alert("用户名已存在，无法添加");
                } else {
                    alert("增加失败")
                }
            },
            context: this
        });
    }
    $addbtn.bind("click", addTeacher);    //为添加按钮绑定点击事件

    /**
     * 用于实现删除用户的功能
     */
    function delTeacher() {
        // 方法功能: 该方法用于删除表格选中的用户
        // 方法描述: 首先获取到要删除用户的id列表，然后通过AJAX发送到后端，后端根据结构到的用户id
        // 进行删除,并将删除结果返回给页面

        // 通过getSelections 获取选中的用户列表,然后获取选中用户的id值，保存到userIds中
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            return;
        }

        var userIds = "";
        for (var i = 0; i < selects.length; i++) {
            userIds = userIds + selects[i].id + ',';
        }

        //构建请求参数,传送用户的id数据
        var param = {
            userIds: userIds
        };
        //  ajax请求,将数据发送到删除用户的控制器中
        $.ajax({
            url: "/human_resource_msy_war_exploded/teacher/deleteTeacher",
            data: param,
            type: "post",
            success: function (data) {
                //  根据返回的结果data进行处理,data为0表示删除成功，则刷新页面数据
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    alert("删除成功")
                    $table.bootstrapTable("refresh");
                }else{
                    alert("教师还有教授课程，无法删除") ;
                }
                // 将删除按钮恢复禁用状态
                $("#delete").attr("disabled", "disabled");
            },
            context: this
        });
    }
    $deletebtn.bind("click", delTeacher);  //为删除按钮绑定删除方法

    var $downloadAdminTeacher = $("#downloadAdminTeacher") ;
    $downloadAdminTeacher.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/teacher/downloadTeacher") ;
        console.log(forms.action);
        forms.submit() ;
    }) ;

    //设置教师查看课程表事件
    //4. 设置查看员工按钮事件
    function addFunctionAlty(e, value, row, index) {
        return [
            '<div class="btn-group-vertical">\n' +
            '<button id="staff" type="button" class="btn btn-primary">查看课程表</button><br>'+
            '<button id="info-pay" type="button" class="btn btn-primary">薪资详情</button>'+
            '<button id="download-pay" type="button" class="btn btn-primary">下载薪资详情</button>'+
            '</div>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            showTeacherCourse(row.id) ;
        },
        'click #info-pay':function (e, value, row, index) {
            showDetailPay(row.id) ;
        },'click #download-pay':function (e, value, row, index) {
            var forms = $("<form></form>");
            $(document.body).append(forms) ;
            forms.method = "post" ;
            forms.attr('action',"/human_resource_msy_war_exploded/course/downloadTeacherCoursePay") ;
            var input1 = '<input type="hidden" name="tid"  value="'+row.id+'"/>' ;
            forms.append(input1) ;
            console.log(forms.action);
            forms.submit() ;
        }
    };

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/teacher/limitPageTeacher",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            }, {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            }, {
                field: 'username',
                title: "教师姓名",
                align: "center",
                valign: "middle"
            }, {
                field: 'sex',
                title: "性别",
                align: "center",
                valign: "middle"
            }, {
                field: 'birthday',
                title: "生日",
                align: "center",
                valign: "middle"
            }, {
                field: 'phone',
                title: "电话",
                align: "center",
                valign: "middle"
            },{
                field: 'pay',
                title: "薪资",
                align: "center",
                valign: "middle"
            },{
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        /* 配置选择事件 */
        "onCheck": changeBtn,
        "onUncheck": changeBtn,
        "onCheckAll": changeBtn,
        "onUncheckAll": changeBtn,
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "教师名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        },
        onDblClickCell: function (field, value, row, $element) {
            // 方法功能: 用于使得用户可用行编辑数据 用于修改用户信息
            // 方法描述: 通过绑定表格双击事件使得表格可编辑 然后将修改后的数据传送给后台服务器
            // 如果修改成功则加载修改后的数据否则恢复原来的数据

            //设置表项可编辑
            $element.attr('contenteditable', true);
            $element.blur(function () {
                // 方法功能: 当用户编辑完成后，就把数据提交给后台
                // 方法描述: 用户编辑后，失去焦点，把数据提交给后台，然后对返回结果进行处理
                var flag = 0;  //作为用户数据是否合法的标志 flag为1表示不合法，则无法修改
                var tdValue = $element.html();  //修改后表项的数据
                var index = $element.parent().data('index'); //表项下标
                // switch 根据用户修改的列项，进行判断相应修改的数据是否合法，不合法则修改标记变量
                switch (field) {
                    case "id":
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "account" :
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "username":
                        if (checkUserName(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "birthday" :
                        if (checkBirthday(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "phone":
                        if (checkPhone(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                }
                if (flag == 1) {
                    // 编辑后的数据格式有误则恢复原理啊的数据
                    $table.bootstrapTable('updateCell', {
                        index: index,       //行索引
                        field: field,       //列名
                        value: value        //原来的旧值
                    });
                    return;
                }
                row[field] = tdValue;//修改指定键的值
                // 向用户更新控制器发送请求，进行用户的更新操作
                $.ajax({
                    url: "/human_resource_msy_war_exploded/teacher/updateTeacher",
                    type: "post",
                    data: row,
                    success: function (data) {
                        console.log(data)
                        console.log(data.state);
                        // 根据返回data判断数据是否更新成功
                        // data为1表示更新成功 data为-4表示年龄数据出错,其它数据表示更新失败
                        if (!((typeof (data) != "undefined") && (data.state == 1))) {
                            alert("更新失败")
                            //更新列表数据 恢复原来的数据
                            $table.bootstrapTable('updateCell', {
                                index: index,       //行索引
                                field: field,       //列名
                                value: value        //cell值
                            });
                        }

                    }
                });
            });
        }
    });

    /**
     * 修改删除按钮禁用状态
     */
    function changeBtn() {
        // 方法功能：修改删除按钮的禁用状态,没有被选行则按钮为禁用状态反之为可用状态
        // 方法描述: 获取表格被选中的行，如果没有被选中的行则设置按钮为禁用，反之设置为可用
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            $("#delete").attr("disabled", "disabled");
        } else {
            $("#delete").removeAttr("disabled");
        }
    }

    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}


function initTeacherInfoPay(tid){
    var $table = $("#usertable");   // 获取表单标签元素

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitTeacherPageCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'num',
                title: "限制人数",
                align: "center",
                valign: "middle"
            },{
                field: 'qualified',
                title: "员工合格薪资/人",
                align: "center",
                valign: "middle"
            },{
                field: 'basic',
                title: "基础工资",
                align: "center",
                valign: "middle"
            },{
                field: 'peopleNum',
                title: "选课人数",
                align: "center",
                valign: "middle"
            },{
                field: 'qualifiedNum',
                title: "合格人数",
                align: "center",
                valign: "middle"
            },{
                field: 'pay',
                title: "薪资",
                align: "center",
                valign: "middle"
            }
        ],
        /* 配置选择事件 */
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id:tid,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });

    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/**
 * 该js函数用于课程信息表格的控制
 */
function initCourseTable() {
    var $table = $("#usertable");   // 获取表单标签元素
    var $addbtn = $("#add-moadl");  // 获取增加用户按钮
    var $deletebtn = $("#delete"); // 获取删除用户按钮



    /**
     * 为了实现增加教师的功能
     */
    function addCourse() {
        // 方法功能: 该方法用于模态框增加用户时候进行验证，并提交给后台
        // 方法描述: 先获取到用户输入的用户各个信息，然后校验用户信息，如果校验完毕
        // 通过AJAX向后台发送新增用户信息，根据AJAX返回的结果提示用户操作的结果

        var cname = $("#cname").val(); //获取用户名
        var addr = $("#addr").val();     //获取性别
        var time = $("#time").val();     // 获取生日
        var intrduce = $("#intrduce").val();
        var num = $("#num").val();
        var phone = $("#phone").val();    //获取电话
        var qualified = $("#qualified").val() ;
        var basic = $("#basic").val() ;
        // 下列几个判断语句用于检验表单各项值是否符合规范，如果不符合则返回-1
        var param = {
            cname: cname,
            addr: addr,
            time: time,
            phone: phone,
            num: num,
            intrduce: intrduce,
            qualified:qualified,
            basic:basic
        }

        //  ajax将新增用户数据以json格式传送给后端，进行用户的增加
        //  后端增加成功则返回data data为1 增加成功 data为-3 表示用户名重名 data为0表示增加失败
        $.ajax({
            url: "/human_resource_msy_war_exploded/course/addCourse",
            data: param,
            type: "GET",
            dataType: "json",
            success: function (data) {
                console.log(data.state);
                //  根据返回的data进行相应的处理
                //  data为1 表示增加成功 则关闭模态框，刷新表格数据
                //  data为-3 提示用户重名
                //  data为其他值 提示用户增加失败
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    $table.bootstrapTable("refresh");
                    $("#myModal").modal("hide");
                } else if ((typeof (data) != "undefined") && (data == -3)) {
                    alert("用户名已存在，无法添加");
                } else {
                    alert("增加失败")
                }
            },
            context: this
        });
    }

    $addbtn.bind("click", addCourse);    //为添加按钮绑定点击事件

    /**
     * 用于实现删除用户的功能
     */
    function delCourse() {
        // 方法功能: 该方法用于删除表格选中的用户
        // 方法描述: 首先获取到要删除用户的id列表，然后通过AJAX发送到后端，后端根据结构到的用户id
        // 进行删除,并将删除结果返回给页面

        // 通过getSelections 获取选中的用户列表,然后获取选中用户的id值，保存到userIds中
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            return;
        }

        var userIds = "";
        for (var i = 0; i < selects.length; i++) {
            userIds = userIds + selects[i].id + ',';
        }

        //构建请求参数,传送用户的id数据
        var param = {
            userIds: userIds
        };
        //  ajax请求,将数据发送到删除用户的控制器中
        $.ajax({
            url: "/human_resource_msy_war_exploded/course/deleteCourse",
            data: param,
            type: "post",
            success: function (data) {
                //  根据返回的结果data进行处理,data为0表示删除成功，则刷新页面数据
                if ((typeof (data) != "undefined") && (data.state == 1)) {
                    alert("删除成功")
                    $table.bootstrapTable("refresh");
                }else{
                    alert("删除失败")
                }
                // 将删除按钮恢复禁用状态
                $("#delete").attr("disabled", "disabled");
            },
            context: this
        });
    }

    $deletebtn.bind("click", delCourse);  //为删除按钮绑定删除方法

    var $downloadAdminCourse = $("#downloadAdminCourse") ;
    $downloadAdminCourse.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/course/downloadAllCourse") ;
        console.log(forms.action);
        forms.submit() ;
    }) ;

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitPageCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            },
            {
                field: 'phone',
                title: "授课老师电话",
                align: "center",
                valign: "middle"
            }, {
                field: 'num',
                title: "限制人数",
                align: "center",
                valign: "middle"
            },{
                field: 'qualified',
                title: "员工合格薪资/人",
                align: "center",
                valign: "middle"
            },{
                field: 'basic',
                title: "基础工资",
                align: "center",
                valign: "middle"
            }
        ],
        /* 配置选择事件 */
        "onCheck": changeBtn,
        "onUncheck": changeBtn,
        "onCheckAll": changeBtn,
        "onUncheckAll": changeBtn,
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        },
        onDblClickCell: function (field, value, row, $element) {
            // 方法功能: 用于使得用户可用行编辑数据 用于修改用户信息
            // 方法描述: 通过绑定表格双击事件使得表格可编辑 然后将修改后的数据传送给后台服务器
            // 如果修改成功则加载修改后的数据否则恢复原来的数据

            //设置表项可编辑
            $element.attr('contenteditable', true);
            $element.blur(function () {
                // 方法功能: 当用户编辑完成后，就把数据提交给后台
                // 方法描述: 用户编辑后，失去焦点，把数据提交给后台，然后对返回结果进行处理
                var flag = 0;  //作为用户数据是否合法的标志 flag为1表示不合法，则无法修改
                var tdValue = $element.html();  //修改后表项的数据
                var index = $element.parent().data('index'); //表项下标
                // switch 根据用户修改的列项，进行判断相应修改的数据是否合法，不合法则修改标记变量
                switch (field) {
                    case "id":
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "account" :
                        flag = 1;
                        alert("无法修改此列");
                        break;
                    case "username":
                        if (checkUserName(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "birthday" :
                        if (checkBirthday(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                    case "phone":
                        if (checkPhone(tdValue) == -1) {
                            flag = 1;
                        }
                        break;
                }
                if (flag == 1) {
                    // 编辑后的数据格式有误则恢复原理啊的数据
                    $table.bootstrapTable('updateCell', {
                        index: index,       //行索引
                        field: field,       //列名
                        value: value        //原来的旧值
                    });
                    return;
                }
                row[field] = tdValue;//修改指定键的值
                // 向用户更新控制器发送请求，进行用户的更新操作
                $.ajax({
                    url: "/human_resource_msy_war_exploded/course/updateCourse",
                    type: "post",
                    data: row,
                    success: function (data) {
                        console.log(data)
                        console.log(data.state);
                        // 根据返回data判断数据是否更新成功
                        // data为1表示更新成功 data为-4表示年龄数据出错,其它数据表示更新失败
                        if (!((typeof (data) != "undefined") && (data.state == 1))) {
                            alert("更新失败")
                            //更新列表数据 恢复原来的数据
                            $table.bootstrapTable('updateCell', {
                                index: index,       //行索引
                                field: field,       //列名
                                value: value        //cell值
                            });
                        }

                    }
                });
            });
        }
    });

    /**
     * 修改删除按钮禁用状态
     */
    function changeBtn() {
        // 方法功能：修改删除按钮的禁用状态,没有被选行则按钮为禁用状态反之为可用状态
        // 方法描述: 获取表格被选中的行，如果没有被选中的行则设置按钮为禁用，反之设置为可用
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            $("#delete").attr("disabled", "disabled");
        } else {
            $("#delete").removeAttr("disabled");
        }
    }

    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

function initAdminStaffGrade(id){
    var $table = $("#usertable");   // 获取表单标签元素

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitStaffPageSelectCourseGrade",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'username',
                title: "授课老师",
                align: "center",
                valign: "middle"
            },{
                field: 'grade',
                title: '分数',
                align: "center",
                valign: "middle"
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: id,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}


/*教师界面的函数模块*/


/*
   用于显示课程选课人员名单
 */
function showStaffTable(id, cid) {
    searchStaff(id, cid);
}

/**
 * 用于显示成绩录入界面
 */
function showLoadSource(id, cid) {
    loadStaffSource(id, cid);
}

/**
 * 该js函数用于教师课程信息管理的控制
 */
function initTeacherCourseTable(id) {
    var $table = $("#usertable");   // 获取表单标签元素

    function addFunctionAlty(value, row, index) {
        return [
            '<button id="staff" type="button" class="btn btn-primary">选课名单</button>',
            '<button id="download-staff" type="button" class="btn btn-primary">下载选课名单</button><br>',
            '<button id="resulte" type="button" class="btn btn-info">录入成绩</button>',
            '<button id="download-staff-grade" type="button" class="btn btn-primary">打印成绩单</button>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            /*显示选课名单页面*/
            showStaffTable(id, row.id);
        },
        'click #resulte': function (e, value, row, index) {
            /*显示录入成绩页面*/
            showLoadSource(id, row.id);
        },
        'click #download-staff':function (e, value, row, index) {
            /*下载选课名单*/
            var forms = $("<form></form>");
            $(document.body).append(forms) ;
            forms.method = "post" ;
            forms.attr('action',"/human_resource_msy_war_exploded/teacher/downloadStaff") ;
            console.log(forms.action)
            var input1 = '<input type="hidden" name="cid"  value="'+row.id+'"/>' ;
            var input2 = '<input type="hidden" name="tid"  value="'+ id+'"/>' ;
            forms.append(input1+input2) ;
            forms.submit() ;
        }, 'click #download-staff-grade':function (e, value, row, index) {
            /*下载课程成绩单*/
            var forms = $("<form></form>");
            $(document.body).append(forms) ;
            forms.method = "post" ;
            forms.attr('action',"/human_resource_msy_war_exploded/teacher/downloadStaffGrade") ;
            console.log(forms.action)
            var input1 = '<input type="hidden" name="cid"  value="'+row.id+'"/>' ;
            var input2 = '<input type="hidden" name="tid"  value="'+ id+'"/>' ;
            forms.append(input1+input2) ;
            forms.submit() ;
        }
    };

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course//limitTeacherPageCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'remainder',
                title: "选课情况",
                align: "center",
                valign: "middle"
            }, {
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "用户名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: id,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/**
 * 该js函数用于选课名单信息表格的控制
 */
function initTeacherStaffTable(id, cid) {
    console.log("进入 js函数 控制选课名单")
    var $table = $("#usertable");   // 获取表单标签元素

    function addFunctionAlty(e, value, row, index) {
        return [
            '<button id="staff" type="button" class="btn btn-primary">退选</button>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            /*进行退选操作*/
            var param = {
                uid: row.id,
                cid: cid
            }
            $.ajax({
                url: "/human_resource_msy_war_exploded/record/deleteRecord",
                data: param,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    console.log(data.state);
                    if ((typeof (data) != "undefined") && (data.state == 1)) {
                        $table.bootstrapTable("refresh");
                        $("#myModal").modal("hide");
                    } else if ((typeof (data) != "undefined") && (data == -3)) {
                        alert("用户名已存在，无法添加");
                    } else {
                        alert("增加失败")
                    }
                },
                context: this
            });
        }
    };

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/staff/limitTeacherPageStaff",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            }, {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            }, {
                field: 'username',
                title: "用户名",
                align: "center",
                valign: "middle"
            }, {
                field: 'sex',
                title: "性别",
                align: "center",
                valign: "middle"
            }, {
                field: 'birthday',
                title: "生日",
                align: "center",
                valign: "middle"
            }, {
                field: 'phone',
                title: "电话",
                align: "center",
                valign: "middle"
            }, {
                field: 'department',
                title: "部门",
                align: "center",
                valign: "middle"
            }, {
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                tid: id,
                cid: cid,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

function initLoadSourceTable(id, cid) {
    var $table = $("#usertable");   // 获取表单标签元素
    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/staff/limitPageStaffResulte",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            }, {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            }, {
                field: 'username',
                title: "用户名",
                align: "center",
                valign: "middle"
            }, {
                field: 'sex',
                title: "性别",
                align: "center",
                valign: "middle"
            }, {
                field: 'birthday',
                title: "生日",
                align: "center",
                valign: "middle"
            }, {
                field: 'phone',
                title: "电话",
                align: "center",
                valign: "middle"
            }, {
                field: 'department',
                title: "部门",
                align: "center",
                valign: "middle"
            },{
                field: 'grade',
                title: "分数",
                align: "center",
                valign: "middle"
            }
        ],
        /* 配置选择事件 */
        "onCheck": changeBtn,
        "onUncheck": changeBtn,
        "onCheckAll": changeBtn,
        "onUncheckAll": changeBtn,
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "用户名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                cid:cid,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        },
        onDblClickCell: function (field, value, row, $element) {
            // 方法功能: 用于使得用户可用行编辑数据 用于修改用户信息
            // 方法描述: 通过绑定表格双击事件使得表格可编辑 然后将修改后的数据传送给后台服务器
            // 如果修改成功则加载修改后的数据否则恢复原来的数据
            if(field != "grade") return  ;

            //设置表项可编辑
            $element.attr('contenteditable', true);
            $element.blur(function () {
                // 方法功能: 当用户编辑完成后，就把数据提交给后台
                // 方法描述: 用户编辑后，失去焦点，把数据提交给后台，然后对返回结果进行处理
                var flag = 0;  //作为用户数据是否合法的标志 flag为1表示不合法，则无法修改
                var tdValue = $element.html();  //修改后表项的数据
                var index = $element.parent().data('index'); //表项下标
                // switch 根据用户修改的列项，进行判断相应修改的数据是否合法，不合法则修改标记变量
                if(field != "grade"){
                    flag = 1 ;
                }
                if (flag == 1) {
                    // 编辑后的数据格式有误则恢复原理啊的数据
                    $table.bootstrapTable('updateCell', {
                        index: index,       //行索引
                        field: field,       //列名
                        value: value        //原来的旧值
                    });
                    return;
                }
                var datas = {
                    id : row.id,
                    cid : cid,
                    grade:tdValue
                }

                row[field] = tdValue;//修改指定键的值
                // 向用户更新控制器发送请求，进行用户的更新操作
                $.ajax({
                    url: "/human_resource_msy_war_exploded/resulte/updateResulte",
                    type: "post",
                    data: datas,
                    success: function (data) {
                        console.log(data)
                        console.log(data.state);
                        // 根据返回data判断数据是否更新成功
                        // data为1表示更新成功 data为-4表示年龄数据出错,其它数据表示更新失败
                        if (!((typeof (data) != "undefined") && (data.state == 1))) {
                            alert("更新失败")
                            //更新列表数据 恢复原来的数据
                            $table.bootstrapTable('updateCell', {
                                index: index,       //行索引
                                field: field,       //列名
                                value: value        //cell值
                            });
                        }else{
                            alert("录入成功")
                        }

                    }
                });
            });
        }
    });

    /**
     * 修改删除按钮禁用状态
     */
    function changeBtn() {
        // 方法功能：修改删除按钮的禁用状态,没有被选行则按钮为禁用状态反之为可用状态
        // 方法描述: 获取表格被选中的行，如果没有被选中的行则设置按钮为禁用，反之设置为可用
        var selects = $table.bootstrapTable("getSelections");
        if (selects.length == 0) {
            $("#delete").attr("disabled", "disabled");
        } else {
            $("#delete").removeAttr("disabled");
        }
    }

    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

function initLoadTeacherCourseTable(tid){
    var $table = $("#usertable");   // 获取表单标签元素
    var $download = $("#downloadCourse") ;
    $download.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/course/downloadCourse") ;
        console.log(forms.action)
        var input2 = '<input type="hidden" name="tid"  value="'+ tid+'"/>' ;
        forms.append(input2) ;
        forms.submit() ;
    }) ;

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course//limitTeacherPageCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: tid,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/*用户模块下的表格函数*/
function initStaffTable(id){
    var $table = $("#usertable");   // 获取表单标签元素

    function addFunctionAlty(value, row, index) {
        return [
            '<button id="staff" type="button" class="btn btn-primary">选课</button>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            /*进行选课*/
            var param = {
                uid: id,
                cid: row.id
            }
            console.log(param) ;
            $.ajax({
                url: "/human_resource_msy_war_exploded/record/addRecord",
                data: param,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    console.log(data.state);
                    if ((typeof (data) != "undefined") && (data.state == 1)) {
                        $table.bootstrapTable("refresh");
                        $("#myModal").modal("hide");
                        alert("选课成功");
                    } else if ((typeof (data) != "undefined") && (data.state == 2)) {
                        alert("课程人数已满，无法选择");
                    } else {
                        alert("选课失败")
                    }
                },
                context: this
            });
        }
    };

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitStaffPageCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'username',
                title: "授课老师",
                align: "center",
                valign: "middle"
            },{
                field: 'remainder',
                title: "选课情况",
                align: "center",
                valign: "middle"
            }, {
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: id,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/*用户模块下已选课程模块*/
function initStaffSelectCourseTable(id){
    var $table = $("#usertable");   // 获取表单标签元素
    var $downloadStaffCourse = $("#downloadStaffCourse") ;
    $downloadStaffCourse.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/staff/downloadStaffCourse") ;
        console.log(forms.action)
        var input1 = '<input type="hidden" name="id"  value="'+ id+'"/>' ;
        forms.append(input1) ;
        forms.submit() ;
    }) ;
    function addFunctionAlty(value, row, index) {
        return [
            '<button id="staff" type="button" class="btn btn-primary">退课</button>'
        ].join('');
    }

    window.operateEvents = {
        'click #staff': function (e, value, row, index) {
            /*进行退课*/
            var param = {
                uid: id,
                cid: row.id
            }
            console.log(param) ;
            $.ajax({
                url: "/human_resource_msy_war_exploded/record/deleteRecord",
                data: param,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    console.log(data.state);
                    if ((typeof (data) != "undefined") && (data.state == 1)) {
                        $table.bootstrapTable("refresh");
                        $("#myModal").modal("hide");
                        alert("退课成功");
                    }  else {
                        alert("退课失败")
                    }
                },
                context: this
            });
        }
    };

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitStaffPageSelectCourse",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'username',
                title: "授课老师",
                align: "center",
                valign: "middle"
            },{
                field: 'remainder',
                title: "选课情况",
                align: "center",
                valign: "middle"
            }, {
                field: 'operate',
                title: '操作',
                events: operateEvents,
                formatter: addFunctionAlty
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "用户名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: id,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

/*用户模块下的成绩模块*/
function initStaffGradeTable(id){
    var $table = $("#usertable");   // 获取表单标签元素

    var $downloadStaffCourseGrade = $("#downloadStaffCourseGrade") ;
    $downloadStaffCourseGrade.click(function (){
        var forms = $("<form></form>");
        $(document.body).append(forms) ;
        forms.method = "post" ;
        forms.attr('action',"/human_resource_msy_war_exploded/staff/downloadStaffCourseGrade") ;
        console.log(forms.action)
        var input1 = '<input type="hidden" name="id"  value="'+ id+'"/>' ;
        forms.append(input1) ;
        forms.submit() ;
    }) ;

    // 信息初始化 用于给table配置 列表数据url，列表项
    // 高度，分页，搜索框等功能
    $table.bootstrapTable({
        height: "680px",
        /* 配置数据加载 */
        url: "/human_resource_msy_war_exploded/course/limitStaffPageSelectCourseGrade",
        method: "GET",
        contentType: "application/x-www-form-urlencoded",
        columns: [
            {
                field: 'select',
                checkbox: true,
                align: "center",
                valign: "center"
            },
            {
                field: "id",
                title: "编号",
                align: "center",
                valign: "middle",
                visible: false
            },
            {
                field: 'cname',
                title: "课程名称",
                align: "center",
                valign: "middle"
            },
            {
                field: 'addr',
                title: "地点",
                align: "center",
                valign: "middle"
            },
            {
                field: 'time',
                title: "时间",
                align: "center",
                valign: "middle"
            },
            {
                field: 'intrduce',
                title: "介绍",
                align: "center",
                valign: "middle"
            }, {
                field: 'username',
                title: "授课老师",
                align: "center",
                valign: "middle"
            },{
                field: 'grade',
                title: '分数',
                align: "center",
                valign: "middle"
            }
        ],
        clickToSelect: true,
        /* 配置搜索框 */
        search: true,
        searchText: "",
        formatSearch: function () {
            return "课程名搜索";
        },
        searchOnEnterKey: false,
        trimOnSearch: true,
        customSearch: customSearch,  //用于指定列搜索
        /* 配置分页 */
        pagination: true,
        pageSize: 10,      //页面大小
        paginationDetailHAlign: "right",     // 用于隐藏分页信息
        pageNumber: 1,         // 页面起始信息
        cache: true,
        pageList: [5, 10, 20, 30],//分页步进值
        sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
        queryParams: function (params) {
            // 方法功能：用于返回分页的信息
            // 方法描述: 将需要请求的页面大小和页码以及模糊查询的关键字返回给后端
            return {
                id: id,
                pageSize: params.limit,
                page: params.offset,
                searchText: params.search
            }
        }
    });


    /**
     * 为了返回匹配搜索关键字的用户数据
     * @param data
     * @param text
     * @returns {[]}
     */
    function customSearch(data, text) {
        // 方法功能: 返回匹配搜索关键字的数据
        // 方法描述: 遍历所有用户，将匹配关键字的元素进行显示
        var arr = [];
        data.filter(function (row) {
            // 进行关键字的匹配
            var filter = (row.username + "").indexOf(text) > -1;
            if (filter) {
                arr.push(row);
            }
        });
        return arr;
    }
}

