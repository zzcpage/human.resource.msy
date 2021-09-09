package com.service;

import com.domain.Resulte;

import java.util.Collection;
import java.util.List;
/**
 * 用于提供课程成绩业务逻辑操作接口
 * @author zzc
 * @version 1.1.0
 */
public interface ResulteService {

    /**
     * 查询员工课程成绩
     * <p>根据员工id和课程id查询课程成绩</p>
     * <p><pre>{@code
     * 例子：查询张三(id=7)的c语言课程成绩(id=9)
     * Resulte resulte = findResulte(7,9) ;
     * }</pre></p>
     * @param uid   员工id
     * @param cid   课程id
     * @return  返回查询结果，查询成功返回成绩记录，查询失败返回null
     */
    public Resulte findResulte(long uid , long cid );

    /**
     * 查询员工所有课程成绩
     * <p>根据员工id查询员工所有课程的成绩</p>
     * <p><pre>{@code
     * 例子：查询张三(id=7)的所有课程成绩
     * List<Resulte> list = findResultByUid(7) ;
     * }</pre></p>
     * @param uid   员工的id
     * @return  返回员工课程成绩列表
     */
    public List<Resulte> findResultByUid(long uid) ;

    /**
     * 查询课程的成绩表
     * <p>根据课程的id查询课程下所有选课成员的成绩</p>
     * <p><pre>{@code
     * 例子：查询C语言课程(id=7)下所有员工的成绩
     * List<Resulte> list  = findResultByCid(7) ;
     * }</pre></p>
     * @param cid   课程的id
     * @return  返回该课程下所有员工的成绩
     */
    public List<Resulte> findResultByCid(long cid) ;

    /**
     * 分页查询课程选课成员成绩
     * <p>根据cid确定选课成员成绩信息再由offset和pagesize确定选取的记录</p>
     * <p><pre>{@code
     * 例子：查询课程(id=9)的第十条到第二十条的记录
     *  List<Resulte> list =  findStaffResulteLimit(10,10,9) ;
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pageSize  选取的数据量
     * @param valueOf       课程id
     * @return  返回课程（cid）成员成绩查询结果的第[offset,offset+pagesize]的记录
     */
    List<Resulte> findResulteLimit(int offset, int pageSize, Long valueOf);

    /**
     * 模糊查询课程选课成员成绩
     * <p>根据cid和模糊查询关键词确定选课成员成绩信息</p>
     * <p><pre>{@code
     * 例子：查询课程(id=9)成员姓名包含"张"的所有记录
     *  List<Resulte> list =  findAllStaffResulteSearch("张",9) ;
     * }</pre></p>
     * @param search     用户名模糊查询关键词
     * @param valueOf       课程id
     * @return  返回课程（cid）成员成绩模糊查询结果
     */
    List<Resulte> findAllResulteSearch(  String search, Long valueOf);

    /**
     * 分页模糊查询课程选课成员成绩
     * <p>根据cid和模糊查询关键词确定选课成员成绩信息再由offset和pagesize确定选取的记录</p>
     * <p><pre>{@code
     * 例子：查询课程(id=9)成员名字包含'张'的第十条到第二十条的记录
     *  List<Resulte> list =  findStaffResulteLimit(10,10,"张",9) ;
     * }</pre></p>
     * @param offset    数据起始位置
     * @param pageSize  选取的数据量
     * @param search    员工名字的模糊查询关键词
     * @param valueOf       课程id
     * @return  返回课程（cid）成员成绩模糊查询结果的第[offset,offset+pagesize]的记录
     */
    List<Resulte> findResulteLimitAndSearch(int offset, int pageSize, String search, Long valueOf);

    /**
     * 增加课程成绩信息
     * <p>根据传入的课程id，员工id以及成绩信息增加课程成绩信息</p>
     * <p><pre>{@code
     * 例子：张三id=9,课程id=7,成绩=100
     * boolean resulte = insertResult(new Resulte(9,7，100))  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param result  需要具有课程成绩的基本信息
     * @return  返回增加的结果
     */
    public boolean inserResult(Resulte result);

    /**
     * 更新课程成绩的信息
     * <p>根据课程id以及员工id以及成绩进行更新操作</p>
     * <p><pre>{@code
     * 例子：需要将张三id=7,课程id=9,成绩=100变为成绩=90
     * boolean resulte = updateLecture(new Resulte(7,9,100)  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param result   需要更新的成绩记录，包含课程id，员工id,以及成绩
     * @return  返回更新结果成功返回true,失败返回false
     */
    public boolean updateResult(Resulte result) ;

    /**
     * 删除授课信息
     * <p>根据传入的课程id以及员工id删除成绩记录</p>
     * <p><pre>{@code
     * 例子：删除张三id=7,C语言课程id=9的成绩记录
     * boolean resulte = deleteResult(new Resulte(7,9))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param result   需要删除的成绩信息包含课程id以及员工id
     * @return 返回删除结果，成功返回true,失败返回false
     */
    public boolean deleteResult(Resulte result) ;


   }
