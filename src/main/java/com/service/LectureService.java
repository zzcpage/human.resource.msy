package com.service;

import com.domain.Lecture;

import java.util.List;
/**
 * 用于提供教师授课业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface LectureService {

    /**
     * 查询教师的所有授课信息
     * <p>根据教师的id值进行搜索，搜索教师所有的授课信息</p>
     * <p><pre>{@code
     * 例子：需要查询张老师(id=10)所有的授课记录
     * List<Lecture> list = findLectureByTid(10) ;
     * }</pre></p>
     * @param tid   教师的唯一标识符
     * @return  返回教师的所有授课记录
     */
    public List<Lecture> findLectureByTid(long tid) ;

    /**
     * 查询课程教师信息
     * <p>根据课程id查询授课老师信息</p>
     * <p><pre>{@code
     * 例子：查询课程id=10的授课老师信息,
     * Lecture lecture = findLectureByCid(10) ;
     * Teacher teacher = findTeacher(lecture.getTid()) ;
     * }</pre></p>
     * @param cid   课程id
     * @return     返回对应的授课记录
     */
    public Lecture findLectureByCid(long cid) ;

    /**
     *  查询所有授课记录
     * <p>返回所有的授课记录信息</p>
     * <p><pre>{@code
     * 例子：管理员需要查看所有的课程以及授课教师信息
     * List<Lecture> list  = findLecture() ;
     * }
     * </pre></p>
     * @return  返回所有的授课记录
     */
    public List<Lecture> findLecture() ;

    /**
     * 增加授课信息
     * <p>根据传入的课程id，教师id增加授课记录</p>
     * <p><pre>{@code
     * 例子：张老师(tid=9)教授C语言（cid=7）
     * boolean resulte = insertLecture(new Lecture(9,10))  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param lecture   需要增加的授课记录包含授课教师id以及课程id
     * @return  返回增加的结果
     */
    public boolean inserLecture(Lecture lecture);

    /**
     * 更新课程的信息
     * <p>根据授课记录的id值更新对应记录的教师id和课程id</p>
     * <p><pre>{@code
     * 例子：需要将课程(cid=7)的老师由张老师变为李老师(tid=7)
     * boolean resulte = updateLecture(new Lecture(7,7))  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param lecture   需要更新的教师记录，包含记录id以及教师id和课程id
     * @return  返回更新结果成功返回true,失败返回false
     */
    public boolean updateLecture(Lecture lecture) ;

    /**
     * 删除授课信息
     * <p>根据传入的课程id删除授课记录</p>
     * <p><pre>{@code
     * 例子：张老师(tid=9)教授C语言（cid=7），需要删除张老师的授课记录
     * boolean resulte = deleteLectureByCid(new Lecture(9,10))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param lecture   需要删除的授课记录，包含课程的cid
     * @return 返回删除结果，成功返回true,失败返回false
     */
    public boolean deleteLectureByCid(Lecture lecture) ;
}
