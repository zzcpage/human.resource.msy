package com.service;

import com.domain.Pay;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
/**
 * 用于提供教师薪资业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface PayService {

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
    public Pay findPayByCid(long cid) ;

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
    public List<Pay> findAllPay() ;

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
    public boolean updatePay(Pay pay) ;

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
    public boolean deletePay(Pay pay) ;

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
    public boolean insertPay(Pay pay) ;

    /**
     * 查询老师薪酬
     * <p>根据教师id统计教师各个课程的薪酬再返回总的薪酬</p>
     * <p><pre>{@code
     * 例子：查询张老师id=7的所有课程的课程成绩
     * double pay = findTeacherPay(7) ;
     * }</pre></p>
     * @param tid   教师的唯一id
     * @return  返回老师的薪酬
     */
    public double findTeacherPay(Long tid) ;

    /**
     * 查询课程薪资
     * <p>根据课程id统计课程的薪资信息,课程薪资由基础工资和合格员工工资组成</p>
     * <p><pre>{@code
     * 例子：查询c语言课程的薪资(id=7)
     * double pay = findCoursePay(7) ;
     * }</pre></p>
     * @param cid   课程id
     * @return  返回课程的薪资
     */
    public HashMap findCoursePay(long cid);
}
