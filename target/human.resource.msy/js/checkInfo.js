/**
 * 该js文件 主要用于提供数据的验证功能
 */

/**
 * 校验电话是否合法
 * @param str 校验数据
 * @return 合法返回0 非法返回-1
 */
function checkPhone(str){
    // 方法功能：用于校验电话是否合法
    // 方法描述：通过正则表达式匹配电话，如果合法则返回0，非法返回-1.
    // 电话的格式为由非0开头的11位数字组成的数据.
    var match = /^[1-9][0-9]{10}$/ ;
    if(str==null||str.length==0){
        alert("电话不能为空");
        return -1 ;
    }else if(!match.test(str)){
        alert("电话号码格式为非0开头的十一位数字");
        return -1 ;
    }
    return 0 ;
}

/**
 * 用于校验地址是否合法
 * @param str 待校验数据
 * @return 合法返回0 非法返回-1
 */
function checkDepartment(str){
    // 方法功能：用于校验地址是否合法
    // 方法描述：通过正则表达式匹配电话，如果合法则返回0，非法返回-1.
    // 地址的格式为1-20位的字符组成
    if(str==null||str.length==0){
        alert("地址不能为空");
        return -1 ;
    }else if(str.length>20){
        alert("地址长度不能超过20");
        return -1 ;
    }
    return 0 ;
}
/**
 * 用户名格式的校验
 * @param 待校验数据
 * @return 合法返回0 非法返回-1
 */
function checkUserName(str){
    // 方法功能：用于校验用户名是否合法
    // 方法描述：通过正则表达式匹配用户名，如果合法则返回0，非法返回-1.
    // 用户名的格式为由字母和数字组成的6-10位数据
    if(str==null||str.length==0){
        alert("姓名不能为空");
        return -1 ;
    }else if (str.length > 20){
        alert("姓名长度不能超过20");
        return -1 ;
    }
    return 0 ;
}


/**
 * 用于校验年龄是否合法
 * @param str 待校验数据
 * @return 合法返回0 非法返回-1
 */
function checkBirthday(str){
    // 方法功能：用于校验生日是否合法
    // 方法描述：通过正则表达式匹配电话，如果合法则返回0，非法返回-1.
    // 年龄的格式为1-3位数字组成
    if(str==null||str.length==0){
        alert("生日不能为空");
        return -1 ;
    }else if(str.length>20){
        alert("年龄长度不能超过20");
        return -1 ;
    }
    return 0 ;
}