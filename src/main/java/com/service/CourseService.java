package com.service;

import com.domain.Course;
import com.domain.Lecture;
import com.domain.Record;
import com.domain.Staff;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 用于提供课程业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface CourseService {

    /**
     * 查找课程
     * <p>根据课程id查找课程</p>
     * <p><pre>{@code
     * 例如：查找课程id=7的课程信息
     * Course course = findCourseById(7) ;
     * }</pre></p>
     * @param id   课程id
     * @return  返回根据课程id查找的结果
     * {@link com.dao.CourseDao#findCourseById(long)}
     */
    public Course  findCourse(long id);

    /**
     * 查找所有的课程信息
     * <p>查找所有的课程信息</p>
     * <p><pre>{@code
     * 例如：管理员查看所有的课程信息
     * List<Course> list = findAllCourse() ;
     * }</pre></p>
     * @return  返回所有的课程信息
     */
    public List<Course>  findAllCourse();

    /**
     * 分页查询
     * <p>课程的分页查询，根据offset定位数据的起始位置，根据pagesize确定需要查询的数据量</p>
     * <p><pre>{@code
     * 例子：查询从第十个数据开始的十个元素
     * List<Course> list = findCourseLimit(10,10) ;
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pagesize  选取的数据量大小
     * @return  返回查询的指定位置后的pagesize个数据
     */
    public List<Course> findCourseLimit(int offset , int pagesize);

    /**
     * 带有课程名模糊查询的分页查询课程信息
     * <p>根据用户传入的数据起始位置，数量量，模糊查询关键词进行查询</p>
     * <p><pre>{@code
     * 例子：查询课程名带有”C“的第十个数据开始的十个课程记录
     * List<Course> list = findCourseLimitAndSearch(10,10,"C");
     * }
     * </pre></p>
     * @param offset    数据起始位置
     * @param pagesize     数据量大小
     * @param search    用户名模糊查询关键字
     * @return  返回模糊查询结果记录中[offset,offset+pagesize]的结果
     */
    public List<Course> findCourseLimitAndSearch(int offset , int pagesize,String search);

    /**
     * 课程名模糊查询
     * <p>根据模糊查询关键字查询所有满足条件的课程</p>
     * <p><pre>{@code
     * 例子：查询所有课程名带有'C'的记录
     * List<Course> = findAllCourseSearch("C") ;
     * }</pre></p>
     * @param search    模糊查询关键词
     * @return  返回所有的模糊查询匹配的结果
     */
    public List<Course> findAllCourseSearch(String search) ;

    /**
     * 查询教师的所有课程
     * <p>根据教师的tid进行查询教师所教授的所有课程</p>
     * <p><pre>{@code
     * 例子:  查询张老师的所有课程,已知张老师的id为 10
     * List<Course> list = findTeacherAllCourse(10);
     * }
     * </pre></p>
     * @param valueOf   教师唯一标识符
     * @return  返回教师所教授的所有课程
     */
    List<Course> findTeacherAllCourse(Long valueOf);

    /**
     * 教师课程的分页查询
     * <p>根据传入的教师tid以及数据起始位置和数据量来返回查询结果</p>
     * <p><pre>{@code
     * 例子：查询张老师的课程记录的第十条记录到第二十条记录,已知张老师的id为 10
     *  List<Course> list =  findTeacherCourseLimit(10,10,10) ;
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pageSize  查询数据的记录数
     * @param valueOf       教师唯一id
     * @return  返回教师查询记录位于[offset,offset+pagesize]的结果
     */
    List<Course> findTeacherCourseLimit(int offset, int pageSize, Long valueOf);

    /**
     * 教师课程的模糊查询
     * <p>根据传入的教师id以及模糊查询关键词查询教师的课程信息</p>
     * <p><pre>{@code
     * 例子：查询
     * }</pre></p>
     * @param search    模糊查询关键词
     * @param valueOf   教师唯一标识符
     * @return  返回符合教师模糊查询的所有结果
     */
    List<Course> findTeacherAllCourseSearch(int offset, int pageSize, String search, Long valueOf);

    /**
     * 教师带有模糊关键词(课程名)的课程分页查询
     * <p>根据传入的教师id以及分页的起始位置和数据量，模糊查询关键词进行信息查询</p>
     * <p><pre>{@code
     * 例子：已知张老师的id为10，需要查询张老师查询所教授课程名带有"C"的第十条到第二十条的记录信息
     * List<Course> list = findTeacherCourseLimitAndSearch(10,10,"C",10);
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pageSize  查询记录数
     * @param search    课程名的模糊查询关键词
     * @param valueOf       教师唯一标识符
     * @return  返回教师符合模糊查询位于[offset,offset+pagesize]的记录
     */
    List<Course> findCourseTeacherLimitAndSearch(int offset, int pageSize, String search, Long valueOf);

    /**
     * 查询员工未选择的课程
     * <p>根据传入的员工唯一标识符查询员工所有未选课程记录</p>
     * <p><pre>{@code
     * 例子：查询张三的所有未选课信息,张三id为10
     * List<Course> list = findStaffAllCourse(10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @return  返回员工所有未选课信息
     */
    List<Course> findStaffAllCourse(Long valueOf);

    /**
     * 分页查询员工未选择课程信息
     * <p>根据传入的员工唯一标识符以及数据起始位置和查询数据数进行员工未选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的未选课程信息的第十条到第二十条的记录,张三id为10
     * List<Course> list = findStaffAllCourse(10,10,10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param offset   数据起始位置
     * @param pageSize  数据量大小
     * @return  返回员工位于[offset,offset+pagesize]的记录
     */
    List<Course> findStaffCourseLimit(int offset, int pageSize, Long valueOf);

    /**
     *  员工未选课程的模糊查询
     * <p>根据传入的员工唯一标识符以及课程名的模糊查询关键词进行员工未选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的未选课程带有"c"的记录,张三id为10
     * List<Course> list = findStaffAllCourseSearch("c",10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param search 课程名的模糊查询关键词
     * @return  返回员模糊查询结果
     */
    List<Course> findStaffAllCourseSearch( String search, Long valueOf);

    /**
     *  员工未选课程的分页和模糊查询
     * <p>根据传入的员工唯一标识符以及数据起始位置和查询数据数以及课程名的模糊查询关键词进行员工未选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的未选课程带有"c"的第十条到第二十条的记录,张三id为10
     * List<Course> list = findStaffCourseLimitAndSearch(10,10,"c",10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param offset   数据起始位置
     * @param pageSize  数据量大小
     * @param search 课程名的模糊查询关键词
     * @return  返回员模糊查询结果中位于[offset,offset+pagesize]的记录
     */
    List<Course> findCourseStaffLimitAndSearch(int offset, int pageSize, String search, Long valueOf);

    /**
     * 查询员工已选择的课程
     * <p>根据传入的员工唯一标识符查询员工所有已选课程记录</p>
     * <p><pre>{@code
     * 例子：查询张三的所有选课信息,张三id为10
     * List<Course> list = findStaffAllSelectCourse(10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @return  返回员工所有选课信息
     */
    List<Course> findStaffAllSelectCourse(Long valueOf);

    /**
     * 分页查询员工已选择课程信息
     * <p>根据传入的员工唯一标识符以及数据起始位置和查询数据数进行员工已选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的已选课程信息的第十条到第二十条的记录,张三id为10
     * List<Course> list = findStaffSelectCourseLimit(10,10,10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param offset   数据起始位置
     * @param pageSize  数据量大小
     * @return  返回员工位于[offset,offset+pagesize]的记录
     */
    List<Course> findStaffSelectCourseLimit(int offset, int pageSize, Long valueOf);

    /**
     *  员工已选课程的模糊查询
     * <p>根据传入的员工唯一标识符以及课程名的模糊查询关键词进行员工已选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的已选课程带有"c"的记录,张三id为10
     * List<Course> list = findStaffAllSelectCourseSearch("c",10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param search 课程名的模糊查询关键词
     * @return  返回员工模糊查询结果
     */
    List<Course> findStaffAllSelectCourseSearch( String search, Long valueOf);

    /**
     *  员工已选课程的分页和模糊查询
     * <p>根据传入的员工唯一标识符以及数据起始位置和查询数据数以及课程名的模糊查询关键词进行员工已选课程信息的查询</p>
     * <p><pre>{@code
     * 例子：查询张三的已选课程带有"c"的第十条到第二十条的记录,张三id为10
     * List<Course> list = findStaffSelectCourseLimitAndSearch(10,10,"c",10)
     * }</pre></p>
     * @param valueOf   员工唯一标识符
     * @param offset   数据起始位置
     * @param pageSize  数据量大小
     * @param search 课程名的模糊查询关键词
     * @return  返回员模糊查询结果中位于[offset,offset+pagesize]的记录
     */
    List<Course> findCourseStaffSelectLimitAndSearch(int offset, int pageSize, String search, Long valueOf);


    /**
     * 新增课程
     * <p>根据课程的课程名称，授课地点，授课时间，课程介绍，课程开课人数信息新增课程信息</p>
     * <p><pre>{@code
     * 例子：需要新增一个课程,其中course包含所有课程基本信息，需要获取到课程新增后的id。
     * Course course = new Course(课程名称，授课地点，授课时间，课程介绍，课程开课人数);
     * //由于采用了自增长主键，通过mybaits注解可以获取插入数据后的主键值
     * System.out.println(course.getId()) ; //插入数据后会自动设置课程的id属性
     * }</pre></p>
     * @param course    新增课程信息
     * @return  返回增加新课程信息的结果,成功返回true,失败返回false
     */
    public boolean insertCourse(Course course);

    /**
     * 删除课程信息
     * <p>根据课程的id进行课程的删除操作</p>
     * <p><pre>{@code
     * 例子：需要删除课程id为10的课程记录
     * boolean resulte = deleteCourse(new Course(10))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param course 只需要对象包含需要删除的课程id属性即可。
     * @return 返回删除结果，成功返回True,失败返回false
     */
    public boolean deleteCourse(Course course);


    /**
     * 更新课程信息
     * <p>根据课程id更新对应课程记录的相关信息</p>
     * <p><pre>{@code
     * 例子：已知需要更新课程id=7的课程，已经设置好了更新后的课程信息course
     * boolean resulte = updateCourse(course)  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param course    需要更新的课程，包含课程id，以及其它所有的课程信息
     * @return  返回更新结果
     */
    public boolean updateCourse(Course course) ;

    /**
     * 删除课程信息
     * <p>根据课程id进行课程的删除</p>
     * <p><pre>{@code
     * 例子：需要删除课程编号为1,2,3,4的课程。
     * boolean resulte = deleteCourses({1,2,3,4})  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param lst  课程id列表
     * @return  删除成功返回true ,删除失败返回false
     */
    public boolean deleteCourses(List<Long> lst) ;

    /**
     * 查询课程下的员工成绩
     * <p>根据课程id和分页信息以及模糊查询关键字进行课程下员工成绩的查询</p>
     * @param cid 课程id
     * @param search 模糊查询关键词(员工姓名)
     * @param pageSize （页面大小）
     * @param offset    （起始位置）
     * @return  hashMap  其中map.("rows“)存放查询的员工成绩信息数据,map.("total")存放员工成绩记录总数
     */
    HashMap findCourseStaffGrade(long cid , String search,int pageSize , int offset) ;

    /**
     * 增加课程信息
     * <p>增加一条课程信息同时增加授课记录和薪资信息</p>
     * @param course    课程基本信息
     * @param phone     授课老师电话
     * @param qualifield    员工合格薪资/人
     * @param basic     课程基础薪资
     * @return  map.("state“) = 0 表明增加失败，map.("state")=1表示增加成功
     */
    HashMap insertCourse(Course course,String phone,String qualifield , String basic);

    /**
     * 更新课程信息
     * <p>更新一条课程信息同时更新授课记录和薪资信息</p>
     * @param course    课程基本信息
     * @param phone     授课老师电话
     * @param qualifield    员工合格薪资/人
     * @param basic     课程基础薪资
     * @return  map.("state“) = 0 表明更新失败，map.("state")=1表示更新成功
     */
    HashMap updateCourseAndPayAndLecture(Course course,String phone,String qualifield , String basic) ;
}
