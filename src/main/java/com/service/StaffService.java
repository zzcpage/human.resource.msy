package com.service;

import com.domain.Staff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
/**
 * 用于提供员工业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface StaffService {

    /**
     * 查询员工
     * <p>根据员工id查询员工记录</p>
     * <p><pre>{@code
     * 例子：查找张三id=9的信息
     * Staff staff = findStaffById(9);
     * }</pre></p>
     * @param id    用户的id
     * @return  返回查询的结果，成功返回员工记录失败返回null
     */
    public Staff findStaff(long id) ;

    /**
     * 查询所有员工信息
     * <p>查询所有的员工信息</p>
     * <p><pre>{@code
     * 例子：管理员查看员工信息
     * List<Staff> list = findAllStaff() ;
     * }</pre></p>
     * @return  返回所有的员工信息
     */
    public List<Staff> findAllStaff();

    /**
     * 分页查询员工信息
     * <p>员工的分页查询，根据offset定位数据的起始位置，根据pagesize确定需要查询的数据量</p>
     * <p><pre>{@code
     * 例子：查询从第十个数据开始的十个元素
     * List<Staff> list = findStaffLimit(10,10) ;
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pagesize  选取的数据量大小
     * @return  返回查询的指定位置后的pagesize个数据
     */
    public List<Staff> findStaffLimit(int offset , int pagesize);

    /**
     * 带有用户名模糊查询的分页查询员工信息
     * <p>根据用户传入的数据起始位置，数量量，模糊查询关键词进行查询</p>
     * <p><pre>{@code
     * 例子：查询用户名带有张的第十个数据开始的十个记录
     * List<Staff> list = findCourseLimitAndSearch(10,10,"张");
     * }
     * </pre></p>
     * @param offset    数据起始位置
     * @param pagesize     数据量大小
     * @param search    用户名模糊查询关键字
     * @return  返回模糊查询结果记录中[offset,offset+pagesize]的结果
     */
    public List<Staff> findStaffLimitAndSearch(int offset , int pagesize,String search);

    /**
     * 用户名模糊查询
     * <p>根据模糊查询关键字查询所有满足条件的用户</p>
     * <p><pre>{@code
     * 例子：查询所有用户名带有'张'的记录
     * List<Staff> = findAllCourseSearch("张") ;
     * }</pre></p>
     * @param search    模糊查询关键词
     * @return  返回所有的模糊查询匹配的结果
     */
    public List<Staff> findAllStaffSearch( String search) ;

    /**
     * 查询教师课程下所有选课成员信息
     * <p>根据课程id查询课程下所有选课成员的信息</p>
     * <p><pre>{@code
     * 例子：查询C语言课程(id=7)下所有选课成员信息
     * List<Staff> list =findAllTeacherStaff(7) ;
     * }</pre></p>
     * @param valueOf   课程的唯一标识符
     * @return  返回课程下所有选课成员的信息
     */
    List<Staff> findAllCourceStaff(Long valueOf);

    /**
     * 分页查询课程的所有选课成员
     * <p>根据课程的cid进行查询课程下成员信息第[offset,offset+pagesize]条记录</p>
     * <p><pre>{@code
     * 例子:  查询C语言课程id=7第十条到第二十条的记录
     * List<Course> list = findTeacherStaffLimit(10，10，7);
     * }
     * </pre></p>
     * @param valueOf   课程唯一标识符
     * @param offset 页面起始位置
     * @param pageSize 页面大小
     * @return  返回课程下选课成员的第[offset,offset+pagesize]条记录
     */
    List<Staff> findCourceStaffLimit(int offset, int pageSize, Long valueOf);

    /**
     * 模糊查询教师课程下的所有选课成员信息
     * <p>根据课程id以及用户名模糊查询关键词查询成员信息</p>
     * <p><pre>{@code
     * 例子：查询C语言课程(id=7)下用户名带有"张"的成员信息
     * List<Staff> list = findAllTeacherStaffSearch("张",7);
     * }</pre></p>
     * @param search    模糊查询关键词
     * @param valueOf       课程id
     * @return  返回满足模糊查询下对应课程下的所有成员信息
     */
    List<Staff> findAllCourceStaffSearch(  String search, Long valueOf);

    /**
     * 分页模糊查询课程下员工名单
     * <p>根据课程id以及用户名模糊查询关键词查询员工名单返回第[offset,offset+pagesize]条记录</p>
     * <p><pre>{@code
     * 例子:  查询C语言课程id=7员工名称带有"张"的第十条到第二十条的记录
     * List<Course> list = findTeacherStaffLimit(10，10，"张"，7);
     * }</pre></p>
     * @param offset    页面起始位置
     * @param pageSize  页面大小
     * @param search    模糊查询关键词
     * @param valueOf   课程id
     * @return  返回满足课程id和模糊查询结果的第[offset,offset+pagesize]条记录
     */
    List<Staff> findStaffCourceLimitAndSearch(int offset, int pageSize, String search, Long valueOf);


    /**
     * 增加一条用户记录
     * <p>根据员工的id,用户名，生日，性别，电话，部门信息进行员工记录的增加</p>
     * <p><pre>{@code
     * 例子：增加一个员工记录 staff，由于需要先创建用户然后增加员工记录，所以员工的id可以通过用户id代替.
     * boolean resulte = insertStaff(staff)  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param staff 包含员工的所有基本信息。
     * @return 返回增加结果，成功返回true，失败返回false
     */
    public HashMap insertStaff(Staff staff) ;

    /**
     * 删除员工
     * <p>根据员工的id删除员工信息</p>
     * <p><pre>{@code
     * 例子：删除张三id=7
     * boolean resulte = deleteStaff(new Staff(7))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param staff 需要删除的员工，需要包含id属性
     * @return  返回删除结果，成功返回true，失败返回false
     */
    public boolean deleteStaff(Staff staff) ;

    /**
     * 批量删除员工
     * <p>根据员工的id列表删除员工信息</p>
     * <p><pre>{@code
     * 例子：删除员工id为1,2,3,5的员工
     * boolean resulte = deleteStaffs({1,2,3,5})  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param lst 需要删除的员工的id列表
     * @return  返回删除结果，成功返回true，失败返回false
     */
    public boolean deleteStaffs(List<Long> lst) ;

    /**
     * 更新员工信息
     * <p>根据员工id确定员工记录，然后对基本信息进行更新操作</p>
     * <p><pre>{@code
     * 例子：更新张三id=7的基本信息 staff
     * boolean resulte = updateStaff(staff)  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param staff 需要包含员工的基本信息和员工id
     * @return  返回更新结果，成功返回true，失败返回false
     */
    public boolean updateStaff(Staff staff) ;

}
