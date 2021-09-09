package com.service;

import com.domain.Staff;
import com.domain.Teacher;

import java.util.HashMap;
import java.util.List;
/**
 * 用于提供教师业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface TeacherService {

    /**
     * 查询教师
     * <p>根据教师的id查询教师信息</p>
     * <p><pre>{@code
     * 例子：查询张老师(id=7)的基本信息
     * Teacher teacher = findTeacherById(7) ;
     * }</pre></p>
     * @param id    教师的id
     * @return  返回查询的结果
     */
    public Teacher findTeacher(long id) ;

    /**
     * 查询所有教师信息
     * <p>查询所有的教师信息</p>
     * <p><pre>{@code
     * 例子：管理员需要所有的教师信息
     *  List<Teacher> list = findAllTeacher() ;
     * }</pre></p>
     * @return  返回所有教师信息的列表
     */
    public List<Teacher> findAllTeacher();

    /**
     * 教师信息分页查询
     * <p>根据页面起始位置和页面大小返回[offset,offset+pagesize]范围内的数据</p>
     * <p><pre>{@code
     * 例子：查询所有教师记录的第十条到第二十条记录
     * List<Teacher> list = findStaffLimit(10,10) ;
     * }</pre></p>
     * @param offset    页面起始位置
     * @param pagesize  页面大小
     * @return  返回指定页面的教师信息数据
     */
    public List<Teacher> findTeacherLimit(int offset , int pagesize);

    /**
     * 模糊分页查询教师信息
     * <p>根据教师名称模糊查询，返回第[offset,offset+pagsize]的数据</p>
     * <p><pre>{@code
     * 例子: 返回教师名称带有"张"的第十条到第二十条记录
     * List<Teacher> list =  findTeacherLimitAndSearch(10,10,"张") ;
     * }</pre></p>
     * @param offset    页面起始位置
     * @param pagesize  页面大小
     * @param search    教师名称模糊查询关键字
     * @return  返回满足指定查询条件的[offset,offset+pagesize]的数据
     */
    public List<Teacher> findTeacherLimitAndSearch(int offset , int pagesize,String search);

    /**
     * 教师的模糊查询
     * <p>根据教师名称模糊查询关键词进行模糊查询</p>
     * <p><pre>{@code
     * 例子:查询所有的名字带有"张"的老师
     *  List<Teacher> list = findAllTeacherSearch("张") ;
     * }</pre></p>
     * @param search    模糊查询关键词
     * @return 返回满足模糊查询条件的所有教师记录
     */
    public List<Teacher> findAllTeacherSearch(int offset,int pagesize,String search) ;

    /**
     * 管理员教师课程信息的模糊分页查询
     * <p>管理员对教师信息的模糊分页查询，教师信息包含教师的薪资等数据</p>
     * <p><pre>
     * 例子：管理员查询教师信息的第十条到第二十条数据.
     * HashMap s = findAdminTeacherCourseInfo("",10,10) ；
     * List<TeacherPayVo> teacherPatVo = (List<TeacherPayVo>)s.get("rows") ;
     * </pre></p>
     * @param search    教师名称模糊查询关键词
     * @param offset    页面起始位置
     * @param pageSize  页面大小
     * @return  返回hashMap对象，包含两组数据 map.get("rows”)获取教师信息列表，map.get("total")获取信息总数
     */
    public HashMap findAdminTeacherCourseInfo(String search , int offset , int pageSize) ;

    /**
     * 管理员教师课程信息查询
     * <p>管理员对教师信息的查询，教师信息包含教师的薪资等数据</p>
     * <p><pre>
     * 例子：管理员查询所有教师信息和薪资信息.
     * HashMap s = findAdminTeacherCourseInfo("",10,10) ；
     * List<TeacherPayVo> teacherPatVo = (List<TeacherPayVo>)s.get("rows") ;
     * </pre></p>
     * @return  返回hashMap对象，包含两组数据 map.get("rows”)获取教师信息列表，map.get("total")获取信息总数
     */
    public HashMap findAllTeacherInfo() ;

    /**
     * 增加一条教师记录
     * <p>根据教师的id以及教师的姓名，性别，生日，电话进行增加教师记录</p>
     * <p><pre>{@code
     * 例子：已经增加教师用户id=10,则输入教师基本信息teacher
     * boolean resulte = insertTeacher(teacher)  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param teacher 具有用户的id以及教师的基本信息
     * @return 返回增加结果
     */
    public HashMap insertTeacher(Teacher teacher) ;

    /**
     * 删除教师
     * <p>根据教师id删除教师</p>
     * 例子：删除张三id=7
     * boolean resulte = deleteTeacher(new Teacher(7))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * @param teacher   需要具有教师的唯一id
     * @return  返回删除结果，成功返回true，失败返回false
     */
    public boolean deleteTeacher(Teacher teacher) ;

    /**
     * 批量删除教师
     * <p>根据教师id列表删除教师信息</p>
     * <p><pre>{@code
     * 例子：删除教师id为1，2，3，4的教师信息
     * boolean resulte = deleteTeachers({1,2,3,4})  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param lst   存放需要删除的教师的id列表
     * @return  成功返回true，失败返回false
     */
    public boolean deleteTeachers(List<Long> lst) ;

    /**
     * 更新教师信息
     * <p>根据教师id定位记录然后更新基本信息</p>
     * 例子：更新张三id=7的基本信息 teacher
     * boolean resulte = updateTeacher(teacher)  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * @param teacher   需要具有教师id以及更新后的教师基本信息
     * @return   成功返回true，失败返回false
     */
    public boolean updateTeacher(Teacher teacher) ;
}
