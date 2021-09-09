package com.dao;

import org.apache.ibatis.annotations.*;
import com.domain.User;
import org.springframework.stereotype.Repository;

/**
 * 该类用于提供对于用户的数据库操作
 * 包括对用户的查询，修改，删除，增加等操作
 * @author zzc
 * @version 1.1.0
 */
@Repository
public interface UserDao {

    /*查询操作*/

    /**
     * 根据用户名和密码查询用户
     * <p>根据提供的用户名和密码对用户信息进行查询</p>
     * <p><pre>{@code
     * 例子：查询账号为 123456，密码为 123456(经过md5加密)的用户
     * User user =  findUserByAccountAndPassword(123456,加密(123456)) ;
     * }</pre></p>
     * @param account   用户账号
     * @param password  用户密码，经过MD5加密后的密码
     * @return  返回用户对象，存在则返回对象，不存在返回Null
     */
    @Select("select * from users where account=#{account} and password=#{password}")
    public User findUserByAccountAndPassword(@Param("account") String account,@Param("password") String password);

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
    @Select("select * from users where id=#{id}")
    public User findUserById(@Param("id") long id);

    /**
     * 根据用户账号查询用户
     * <p>根据提供的用户账号进行查找用户，账号一般为用户的手机号码</p>
     * <p><pre>{@code
     * 例子：查找账号为12345678910的用户信息
     * User user = findUserByAccount("12345678910") ;
     * }</pre></p>
     * @param account   用户账号
     * @return  返回账号,成功返回对应账号失败返回NULL
     */
    @Select("select * from users where account=#{account}")
    public User findUserByAccount(@Param("account")String account);

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
    @Insert("insert into users(account,password,rights) values(#{account},#{password},#{rights});")
    public boolean insertUser(User user) ;

    /**
     * 删除用户
     * <p>根据用户唯一标识符删除用户</p>
     * <p><pre>{@code
     * 例子：删除用户id为7的用户信息
     * boolean resulte = deleteUser(7)  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param id    用户唯一标识符
     * @return 删除成功返回true,删除失败返回false
     */
    @Delete("delete from users where id = #{id}")
    public boolean deleteUser(@Param("id") Long id);

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
    @Update("update users set  password=#{password}  where id = #{id}")
    public boolean updateUser(User user);

    /**
     * 修改用户的账号
     * <p>更新用户的账号信息，通过id定位需要更新的记录</p>
     * <p><pre>{@code
     * 例子：更新用户id=7的账号为
     * boolean resulte = updateUser(new User(7,1234567890))  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param user  需要修改用户，包含用户id以及更新后的账号信息
     * @return  返回修改的结果
     */
    @Update("update users set  account=#{account}  where id = #{id}")
    public boolean updateAccount(User user);
}
