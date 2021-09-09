package com.controller;

import com.domain.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.service.*;
import com.vo.ResulteVo;
import com.vo.StaffPageCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用于员工模块的前后端交互
 * @author : zzc
 * @version 1.1.0
 **/
@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private ResulteService resulteService;
    @Autowired
    private CourseService courseService ;
    @Autowired
    private RecordService recordService;
    @Autowired
    private TeacherService teacherService ;
    @Autowired
    private LectureService lectureService ;

    /**
     * 分页查询员工信息
     * <p>根据传入的页码，页面大小和查询关键词进行员工信息的查询</p>
     * <p><pre>{@code
     * 访问url: /staff/limitPageStaff
     * 访问方式: {GET,POST}
     * 请求参数：page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap  其中map.("rows“)存放查询的员工信息数据,map.("total")存放员工记录总数
     */
    @ResponseBody
    @RequestMapping("/limitPageStaff")
    public HashMap getPageStaff(HttpServletRequest request) {
        //1. 获取页码，页码大小，查询关键词
        HashMap s = new HashMap();
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取数据起始位置和数据大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 根据是否带有查询关键词进行分页搜索员工信息
        if (search == null || search.trim().equals("")) {
            //不带关键词的查询
            s.put("total", staffService.findAllStaff().size());   //返回数据总数
            s.put("rows", staffService.findStaffLimit(offset, pageSize)); //返回页面数据
        } else {  //带有关键词的模糊查询
            s.put("total", staffService.findAllStaffSearch( search).size());   //返回数据总数
            s.put("rows", staffService.findStaffLimitAndSearch(offset, pageSize, search)); //返回页面数据
        }
        return s;
    }

    /**
     * 分页查询课程下的选课成员信息
     * <p>根据传入的页码，页面大小和查询关键词进行员工信息的查询</p>
     * <p><pre>{@code
     * 访问url: /staff/limitTeacherPageStaff
     * 访问方式: {GET,POST}
     * 请求参数：tid（教师id）,cid（课程id），page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap  其中map.("rows“)存放查询的员工信息数据,map.("total")存放员工记录总数
     * */
    @ResponseBody
    @RequestMapping("/limitTeacherPageStaff")
    public HashMap limitTeacherPageStaff(HttpServletRequest request) {
        //1. 获取教师id，课程id,页码，页面大小，模糊查询关键词
        HashMap s = new HashMap();
        String tid = request.getParameter("tid");
        String cid = request.getParameter("cid");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 确定数据起始位置和数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 根据是否带有模糊查询关键词进行查询课程下成员信息
        if (search == null || search.trim().equals("")) {//不带关键词的查询
            s.put("total", staffService.findAllCourceStaff(Long.valueOf(cid)).size());   //返回数据总数
            s.put("rows", staffService.findCourceStaffLimit(offset, pageSize, Long.valueOf(cid))); //返回页面数据
        } else {  //带有关键词的模糊查询
            s.put("total", staffService.findAllCourceStaffSearch(  search, Long.valueOf(cid)).size());   //返回数据总数
            s.put("rows", staffService.findStaffCourceLimitAndSearch(offset, pageSize, search, Long.valueOf(cid))); //返回页面数据
        }
        return s;
    }

    /**
     * 分页查询课程下所有员工的成绩
     * <p>根据传入的页码，页面大小和查询关键词进行员工信息的查询</p>
     * <p><pre>{@code
     * 访问url: /staff/limitPageStaffResulte
     * 访问方式: {GET,POST}
     * 请求参数：cid（课程id），page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap  其中map.("rows“)存放查询的员工信息数据,map.("total")存放员工记录总数
     * */
    @ResponseBody
    @RequestMapping("/limitPageStaffResulte")
    public HashMap getPageStaffResulte(HttpServletRequest request) {
        //1. 获取查询课程的id,页码，页面大小，模糊查询关键词
        HashMap s = new HashMap();
        String cid = request.getParameter("cid");
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取数据起始位置和选取的数据量大小
        int offset = 0;   //偏移值
        int pageSize = 10; // 页面大小
        //设置页面大小和偏移值
        if (pageNumber == null) {
            offset = 0;
        } else {
            offset = Integer.valueOf(pageNumber);
        }
        if (pageSizes == null) {
            pageSize = 10;
        } else {
            pageSize = Integer.valueOf(pageSizes);
        }

        //3. 获取课程下的员工成绩信息
        return courseService.findCourseStaffGrade(Long.valueOf(cid),search,pageSize,offset);
    }

    /**
     * 删除员工信息
     * <p>根据传入的员工id列表删除员工</p>
     * <p><pre>{@code
     * 访问url: /staff/deleteStaff
     * 访问方式: {GET,POST}
     * 请求参数：userIds(员工id列表)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap  map.("state“) = 0 表明删除失败，map.("state")=1表示删除成功
     */
    @ResponseBody
    @RequestMapping("/deleteStaff")
    public HashMap deleteStaff(HttpServletRequest request) {
        //1. 获取需要删除的员工id列表
        HashMap s = new HashMap();
        s.put("state",0) ;
        String userIds = request.getParameter("userIds"); //获取删除用户uids
        //判断传入数据是否为空
        if (userIds == null || userIds.trim().equals("")) {
            s.put("state", 0);//返回错误信息
            return s;
        }

        //分割请求的id字符串列表
        String[] userIdArr = userIds.split(",");
        //判断转化是否成功
        if (userIdArr == null) {
            s.put("state", 0);//返回错误信息
            return s;
        }

        //获取需要删除的id列表
        List<Long> userIdList = new ArrayList<Long>();
        for (String userId : userIdArr) {
            userIdList.add(Long.valueOf(userId));
        }

        //2. 根据id列表删除对应id的员工信息
        try {
            if(staffService.deleteStaffs(userIdList))  //保存删除结果
                s.put("state", 1); //删除成功，返回状态1
            return s; //返回删除结果
        } catch (Exception e) {
            e.printStackTrace();
            s.put("state", 0); //删除失败，返回状态0
        }
        return s;
    }

    /**
     * 更新员工信息
     * <p>根据传入的员工id和基本信息更新员工的信息</p>
     * <p><pre>{@code
     * 访问url: /staff/updateStaff
     * 访问方式: {GET,POST}
     * 请求参数：id(员工id)，username（员工姓名）,sex(员工性别),birthday（员工生日）,phone（员工电话）,department（员工部门）
     * }</pre></p>
     * @param request   用户发送的请求
     * @return hashMap  map.("state“) = 0 表明更新失败，map.("state")=1表示更新成功
     */
    @ResponseBody
    @RequestMapping("updateStaff")
    public HashMap updateStaff(HttpServletRequest request) {
        HashMap s = new HashMap();
        s.put("state", 0);
        try {
            //1. 获取需要更新的员工id和基础信息
            request.setCharacterEncoding("utf-8");
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String sex = request.getParameter("sex");
            String birthdays = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            String department = request.getParameter("department");
            Staff staff = new Staff(Long.valueOf(id), username, birthdays, sex, phone, department);

            //2. 进行更新操作
            if(staffService.updateStaff(staff))
                s.put("state", 1);//删除成功，返回状态1
        } catch (Exception e) {
            e.printStackTrace();
            s.put("state", 0);//删除失败，返回状态0
        } finally {
            return s;
        }
    }

//    @ResponseBody
//    @RequestMapping("/updateStaffInfo")
//    public HashMap updateStaffInfo(HttpServletRequest request) {
//        HashMap s = new HashMap();
//        String id = request.getParameter("id");
//        String username = request.getParameter("username");
//        String sex = request.getParameter("sex");
//        String birthday = request.getParameter("birthday");
//        String phone = request.getParameter("phone");
//        String department = request.getParameter("department");
//        try {
//            staffService.updateStaff(new Staff(Long.valueOf(id), username, birthday, sex, phone, department));
//            s.put("state", "1");
//        } catch (Exception e) {
//            e.printStackTrace();
//            s.put("state", "0");
//        } finally {
//            return s;
//        }
//    }

    /**
     * 获取员工信息
     * <p>根据传入的员工id列表搜索员工信息</p>
     * <p><pre>{@code
     * 访问url: /staff/getStaff
     * 访问方式: {GET,POST}
     * 请求参数：id(员工id)
     * }</pre></p>
     * @param request   用户发送的请求
     * @return 返回查询到的员工信息
     */
    @ResponseBody
    @RequestMapping("/getStaff")
    public Staff getStaff(HttpServletRequest request) {
        return staffService.findStaff(Long.valueOf(request.getParameter("id")));
    }


    /**
     * 用于员工下载课程成绩表
     * <p>根据传入的员工id打印其课程表的成绩单pdf</p>
     * <p><pre>{@code
     * 访问url: /staff/downloadStaffCourseGrade
     * 访问方式: {GET,POST}
     * 请求参数： id(员工唯一标识符)
     * }</pre></p>
     * @param request   用户请求对象
     * @param response  服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadStaffCourseGrade")
    public void  downloadStaffCourseGrade(HttpServletRequest request , HttpServletResponse response) throws IOException, DocumentException {
        //1. 设置响应头部信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*数据初始化*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/
        //根据员工id查找其所选的所有课程
        //再统计各个课程的成绩封装成StaffPageCourseVo视图对象
        List<Course> courseList =courseService.findStaffAllSelectCourse(Long.valueOf(request.getParameter("id"))) ;
        List<StaffPageCourseVo> courseBeanList = new ArrayList<>() ;
        Staff staff = staffService.findStaff(Long.valueOf(request.getParameter("id"))) ;
        for(Course course : courseList){
            Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
            //封装教师课程信息
            Resulte resulte = resulteService.findResulte(Long.valueOf(request.getParameter("id")),course.getId());
            Record record = recordService.findRecordByUidAndCid(Long.valueOf(request.getParameter("id")),course.getId()) ;
            StaffPageCourseVo staffCourseBean = new StaffPageCourseVo() ;
            staffCourseBean.setUsername(teacher.getUsername());
            staffCourseBean.setId(course.getId());
            staffCourseBean.setAddr(course.getAddr());
            staffCourseBean.setIntrduce(course.getIntrduce());
            staffCourseBean.setTime(course.getTime());
            staffCourseBean.setCname(course.getCname());
            if(resulte==null || record.getState()==0){
                staffCourseBean.setGrade(String.valueOf("暂未录入"));
            }else{
                staffCourseBean.setGrade(String.valueOf(resulte.getGrade()));
            }

            courseBeanList.add(staffCourseBean);
        }


        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/"+new Date().getTime();// 文件夹路径
        File myPath = new File( filePar );
        if (!myPath.exists()){
            myPath.mkdirs();
        }
        String filePath = null ;
        filePath = filePar+"/"+ new Date().getTime()+".pdf" ;
        file = new File(filePath);
        file.createNewFile() ;
        FileOutputStream outputStream = new FileOutputStream(file) ;
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph(staff.getUsername()+"成绩单", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建3列的表格
        int colNumber = 3;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {  0.55f,0.2f, 0.55f }; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("课程名称",f12));
        t.addCell(c1);
        PdfPCell c5 = new PdfPCell(new Paragraph("授课老师",f12));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Paragraph("分数",f12));
        t.addCell(c6);
        //表格数据填充
        for (StaffPageCourseVo resulteVo : courseBeanList){
            t.addCell(new Paragraph(resulteVo.getCname(),f10));
            t.addCell(new Paragraph(resulteVo.getUsername(),f10));
            t.addCell(new Paragraph(resulteVo.getGrade(),f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename =  staff.getUsername()+"的成绩单";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }

    /**
     * 用于员工下载课程表
     * <p>根据传入的员工id打印其课程表的pdf</p>
     * <p><pre>{@code
     * 访问url: /staff/downloadStaffCourse
     * 访问方式: {GET,POST}
     * 请求参数： id(员工唯一标识符)
     * }</pre></p>
     * @param request   用户请求对象
     * @param response  服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadStaffCourse")
    public void  downloadStaffCourse(HttpServletRequest request , HttpServletResponse response) throws IOException, DocumentException {
        //1.设置响应头
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*数据初始化*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/
        //根据员工的id找到其所有选修的课程
        //将课程信息封装成StaffPageCourseVo对象
        List<Course> courseList =courseService.findStaffAllSelectCourse(Long.valueOf(request.getParameter("id"))) ;
        List<StaffPageCourseVo> courseBeanList = new ArrayList<>() ;
        Staff staff = staffService.findStaff(Long.valueOf(request.getParameter("id"))) ;
        for(Course course : courseList){
            Teacher teacher = teacherService.findTeacher(lectureService.findLectureByCid(course.getId()).getTid());
            //封装教师课程信息
            Resulte resulte = resulteService.findResulte(Long.valueOf(request.getParameter("id")),course.getId());
            Record record = recordService.findRecordByUidAndCid(Long.valueOf(request.getParameter("id")),course.getId()) ;
            StaffPageCourseVo staffCourseBean = new StaffPageCourseVo() ;
            staffCourseBean.setUsername(teacher.getUsername());
            staffCourseBean.setId(course.getId());
            staffCourseBean.setAddr(course.getAddr());
            staffCourseBean.setIntrduce(course.getIntrduce());
            staffCourseBean.setTime(course.getTime());
            staffCourseBean.setCname(course.getCname());
            if(resulte==null || record.getState()==0){
                staffCourseBean.setGrade(String.valueOf("暂未录入"));
            }else{
                staffCourseBean.setGrade(String.valueOf(resulte.getGrade()));
            }

            courseBeanList.add(staffCourseBean);
        }


        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/"+new Date().getTime();// 文件夹路径
        File myPath = new File( filePar );
        if (!myPath.exists()){
            myPath.mkdirs();
        }
        String filePath = null ;
        filePath = filePar+"/"+ new Date().getTime()+".pdf" ;
        file = new File(filePath);
        file.createNewFile() ;
        FileOutputStream outputStream = new FileOutputStream(file) ;
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph(staff.getUsername()+"课表", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建5列的表格
        int colNumber = 5;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {  0.55f,0.2f, 0.55f,0.55f,0.25f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("课程名称",f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("地点",f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("时间",f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("介绍",f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("授课老师",f12));
        t.addCell(c5);
        //数据填充
        for (StaffPageCourseVo resulteVo : courseBeanList){
            t.addCell(new Paragraph(resulteVo.getCname(),f10));
            t.addCell(new Paragraph(resulteVo.getAddr(),f10));
            t.addCell(new Paragraph(resulteVo.getTime(),f10));
            t.addCell(new Paragraph(resulteVo.getIntrduce(),f10));
            t.addCell(new Paragraph(resulteVo.getUsername(),f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename =  staff.getUsername()+"的课表";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }

    /**
     * 用于管理员下载员工表
     * <p>管理员打印所有员工信息的pdf</p>
     * <p><pre>{@code
     * 访问url: /staff/downloadStaff
     * 访问方式: {GET,POST}
     * }</pre></p>
     * @param request   用户请求对象
     * @param response  服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadStaff")
    public void  downloadStaff(HttpServletRequest request , HttpServletResponse response) throws IOException, DocumentException {
        //1. 设置响应头信息
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "must-revalidate, no-transform");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/x-download");

        /*数据初始化*/
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;

        /*数据准备*/
        List<Staff> staff = staffService.findAllStaff() ;
        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/"+new Date().getTime();// 文件夹路径
        File myPath = new File( filePar );
        if (!myPath.exists()){
            myPath.mkdirs();
        }
        String filePath = null ;
        filePath = filePar+"/"+ new Date().getTime()+".pdf" ;
        file = new File(filePath);
        file.createNewFile() ;
        FileOutputStream outputStream = new FileOutputStream(file) ;
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        //设置中文字体
        BaseFont bfChinese = null;// FontFactory.getFont(FontFactory.COURIER,
        // 14, Font.BOLD, new CMYKColor(0, 255, 0, 0);//大小，粗细，颜色
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        Font f10 = new Font(bfChinese, 10, Font.NORMAL);
        Font f12 = new Font(bfChinese, 12, Font.NORMAL);
        Font f26 = new Font(bfChinese, 26, Font.NORMAL);//一号字体
        //创建标题
        Paragraph title1 = new Paragraph( "管理员的员工名单", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建5列的表格
        int colNumber = 5;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {  0.55f,0.2f, 0.25f,0.55f,0.55f }; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("用户名",f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("性别",f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("生日",f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("电话",f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("部门",f12));
        t.addCell(c5);
        //数据填充
        for (Staff staff1 : staff){
            t.addCell(new Paragraph(staff1.getUsername(),f10));
            t.addCell(new Paragraph(staff1.getSex(),f10));
            t.addCell(new Paragraph(staff1.getBirthday(),f10));
            t.addCell(new Paragraph(staff1.getPhone(),f10));
            t.addCell(new Paragraph(staff1.getDepartment(),f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename =   "管理员的员工名单";
            // 设置浏览器以下载的方式处理该文件
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");//下载使用
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
            if (myPath != null)// 删除临时文件夹
                myPath.delete();
        }
    }



}
