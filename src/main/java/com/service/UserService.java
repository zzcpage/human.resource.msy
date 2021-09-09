package com.service;

import com.domain.User;

/**
 * 提供用户业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface UserService {

    /**
     * 用户登录
     * <p>根据用户账号和密码返回用户信息</p>
     * <p><pre>{@code
     * 例子：传入账号:123456，密码:123456
     *  User user =  UserLogin(123456,123456) ;
     * }</pre></p>
     * @param account   用户账号
     * @param password  用户密码
     * @return  返回用户,成功返回用户对象，失败返回NULL
     */
    public User UserLogin(String account , String password) ;


    /**
     * 根据用户账号查询用户
     * <p>根据提供的用户账号进行查找用户，账号一般为用户的手机号码</p>
     * <p><pre>{@code
     * 例子：查找账号为12345678910的用户信息
     * User user = findUserByAccount("12345678910") ;
     * }</pre></p>
     * @param phone   用户账号,一般为用户的电话号码
     * @return  返回账号,成功返回对应账号失败返回NULL
     */
    public User findUser(String phone);

    /**
     * 根据Id查找用户
     * <p>利用提供的id进行用户的查找</p>
     * <p><pre>{@code
     * 例子：需要查找用户id=7的用户信息
     * User user = findUserById(7) ;
     * }</pre></p>
     * @param id    用户的唯一标识符
     * @return  返回查询到的用户，成功返回true，失败返回false
     */
    public User findUserById(long id);

    /**
     * 增加一条用户
     * <p>根据用户的账号，密码和权限创建新的用户,教师权限为1，员工权限为2</p>
     * <p><pre>{@code
     * 例子：新增一条员工记录
     * boolean resulte = insertUser(new User("123456","12345678910",2))  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param user  需要增加的用户包含用户账号，密码，权限信息
     * @return  返回增加结果，成功返回true，失败返回false
     */
    public boolean insertUser(User user,String phone,int right);

    /**
     * 修改用户的密码
     * <p>更新用户的密码信息，通过id定位需要更新的记录</p>
     * <p><pre>{@code
     * 例子：更新用户id=7的密码为123456（加密）
     * boolean resulte = updateUser(new User(7,加密(123456)))  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param user  需要修改用户，包含用户id以及更新后的密码信息
     * @return  返回修改的结果
     */
    public boolean updateUser(User user) ;

    /**
     * 删除用户
     * <p>根据用户唯一标识符删除用户</p>
     * <p><pre>{@code
     * 例子：删除用户id为7的用户信息
     * boolean resulte = deleteUser(new User(7))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param user   user存放用户唯一标识符
     * @return 删除成功返回true,删除失败返回false
     */
    public boolean deleteUser(User user) ;

}
