/**
 *  该js文件用于提供辅助功能
 *
 */

/**
 * 用于提示用户是否执行相应的操作
 * @param msg   提示信息
 * @return {boolean}    执行返回true ,取消返回false
 */
function confirmationBox(msg){
    //1. 检验提示信息是否合法
    if(msg == null || msg.toString().trim()==""){
        return false ;
    }
    //2. 创建确认框，获取用户选择的结果，选择确认返回true , 选择取消返回false
    var choice = confirm(msg) ;
    return choice ;
}