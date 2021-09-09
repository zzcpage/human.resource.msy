package com.service;

import com.domain.Record;
import com.domain.Resulte;

import java.util.HashMap;
import java.util.List;

/**
 * 用于提供选课记录的业务逻辑操作
 * @author : zzc
 * @date: 2021-08-31 07:54
 **/
public interface RecordService {

    /**
     * 查询员工选课记录
     * <p>根据员工的id值进行搜索，搜索员工所有选课信息</p>
     * <p><pre>{@code
     * 例子：需要查询张三(id=10)所有的选课记录
     * List<Record> list = findRecordByUid(10) ;
     * }</pre></p>
     * @param uid  员工唯一标识符
     * @return  返回员工所有选课信息（包含课程id值）
     */
    public List<Record> findRecordByUid(long uid) ;

    /**
     * 根据课程id和员工id查询选课记录
     * <p>根据提供的课程id和员工id进行选课记录的查询</p>
     * @param uid   员工id
     * @param cid   课程id
     * @return  返回查询结果，查询成功返回记录，查询失败返回null
     */
    public Record findRecordByUidAndCid(long uid , long cid);

    /**
     * 查询课程选课成员信息
     * <p>根据课程的id值进行搜索，搜索选择该课程的所有员工信息</p>
     * <p><pre>{@code
     * 例子：需要查询C语言课程(id=10)所有的选课成员
     * List<Record> list = findRecordByCid(10) ;
     * }</pre></p>
     * @param cid  课程唯一标识符
     * @return  返回课程选课成员信息（包含员工id值）
     */
    public List<Record> findRecordByCid(long cid) ;

    /**
     * 查询所有选课记录
     * <p></p>
     * <p><pre>{@code
     * 例子：查找所有的选课记录
     * List<Record> record = findRecord() ;
     * }</pre></p>
     * @return  返回所有的选课记录
     */
    public List<Record> findRecord() ;

    /**
     * 新增选课记录
     * <p>根据课程id,用户id，选课状态增加一条课程记录，默认选课状态为0，当教师录入成绩后状态变为1</p>
     * <p><pre>{@code
     * 例子：张三(id=7)选了一门c语言课(id=9)
     * boolean resulte = insertRecord(new Record(7,9,0))  ;
     * if(resulte) System.out.println("增加成功");
     * else System.out.println("增加失败");
     * }</pre></p>
     * @param record    增加的选课记录，包含选课课程的id和员工id
     * @return  返回增加结果，增加成功为true，失败为false
     */
    public boolean inserRecord(Record record);

    /**
     * 更新选课记录
     * <p>根据记录id更新记录的信息，包含员工id，课程id，以及新的状态</p>
     * <p><pre>{@code
     * 例子：一般用于教师录入成绩后修改选课状态信息.如给张三(id=7)选c语言课(id=9)录入成绩
     * boolean resulte = updateRecord(new Record(7,9,1))  ;
     * if(resulte) System.out.println("更新成功");
     * else System.out.println("更新失败");
     * }</pre></p>
     * @param record  需要更新的选课记录，包含员工id，课程id以及状态信息
     * @return  返回更新结果，成功返回true,失败返回false
     */
    public boolean updateRecord(Record record) ;

    /**
     * 删除选课记录
     * <p>根据员工id和课程id删除选课记录</p>
     * <p><pre>{@code
     * 例子：删除张三(id=7)选c语言课(id=9)的记录
     * boolean resulte = deleteRecord(new Record(7,9))  ;
     * if(resulte) System.out.println("删除成功");
     * else System.out.println("删除失败");
     * }</pre></p>
     * @param record   需要删除的课程记录包含课程id和员工id
     * @return  成功删除返回true，失败返回false
     */
    public boolean deleteRecord(Record record) ;

    /**
     * 更新选课记录
     * <p>更新选课记录的同时更新成绩记录</p>
     * @param record    选课记录
     * @param resulte   课程记录
     * @return  更新成功返回true 更新失败返回false
     */
    boolean  updateRecordAndResulte(Record record , Resulte resulte)  ;

    /**
     * 删除选课记录
     * <p>删除选课记录的同时删除成绩记录</p>
     * @param record    选课记录
     * @param resulte   课程记录
     * @return  删除成功返回true 删除失败返回false
     */
    boolean deleteRecordAndResulte(Record record , Resulte resulte)  ;

    /**
     * 增加选课记录信息
     * <p>增加一条课程信息同时增加授课记录和薪资信息</p>
     * @param cid 增加课程的id
     * @param uid 增加员工的uid
     * @return  map.("state“) = 0 表明增加失败，map.("state")=1表示增加成功，map.("state")=2表示课程人数已满
     */
    HashMap insertStaffRecord(long cid ,long uid) ;
}
