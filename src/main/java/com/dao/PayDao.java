package com.dao;

import com.domain.Pay;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 薪资表的增删改查操作
 * @author : zzc
 * @date: 2021-09-03 14:39
 **/
public interface PayDao {

    /*查询操作*/

    /**
     * 搜索课程薪资
     * <p>根据课程id来搜索薪资表</p>
     * <p><pre>{@code
     * 例子：需要查询课程id=7的薪资表
     * Pay pay = findPayByCid(7) ;
     * }</pre></p>
     * @param cid   课程Id
     * @return  返回对应课程的薪资表
     */
    @Select("select * from pay where cid = #{cid}")
     public Pay findPayByCid(@Param("cid") long cid);

    /**
     * 查询所有的课程的薪资表
     * <p>查询所有的薪资表</p>
     * <P><pre>{@code
     * 例子：管理员查询所有课程薪资信息
     * List<Pay> list = findAllPay() ;
     * }
     * </pre></P>
     * @return 返回所有课程的薪资信息
     */
    @Select("select * from pay")
    public List<Pay> findAllPay();

    /*插入操作*/

    /**
     * 增加课程薪资记录
     * <p>根据课程id以及薪资基本信息增加一条课程的薪资记录</p>
     * <p><pre>{@code
     * 例子：增加课程(id=7)的薪资信息 pay
     * boolean resulte = insertPay(new Pay(课程id,合格员工薪资，基础工资))  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param  pay  包含课程id，薪资信息
     * @return 成功返回true，失败返回false
     */
    @Insert("insert into pay values(#{cid},#{qualified} ,#{basic})")
    public boolean insertPay(Pay pay);

    /*删除操作*/

    /**
     * 删除薪资记录
     * <p>删除薪资记录,根据课程id进行删除</p>
     * <p><pre>{@code
     * 例子：需要删除课程id=7的课程的薪资记录
     * boolean resulte = deleteLectureByCid(new Pay(7))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param pay   需要提供课程id
     * @return  返回删除结果
     */
    @Delete("delete from pay where cid=#{cid}")
    public boolean deletePay(Pay pay) ;

    /*更新操作*/

    /**
     * 更新课程的信息
     * <p>根据课程id更新课程的薪资信息</p>
     * <p><pre>{@code
     * 例子：需要更新课程id=7的课程的信息信息 pay
     * boolean resulte = updatePay(pay)  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param pay  需要提供课程id以及课程薪资基本信息
     * @return  成功返回true,失败返回false
     */
    @Update("update pay set qualified=#{qualified},basic=#{basic} where cid = #{cid}")
    public boolean updatePay(Pay pay) ;

}
