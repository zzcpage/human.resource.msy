package com.controller;

import com.domain.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.service.*;
import com.utils.MD5Util;
import com.vo.ResulteVo;
import com.vo.TeacherPayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用于教师模块的前后端交互
 *
 * @author : zzc
 * @version 1.1.0
 **/
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ResulteService resulteService;

    /**
     * 分页查询教师信息
     * <p>根据传入的页码，页面大小和查询关键词进行教师信息的查询</p>
     * <p><pre>{@code
     * 访问url: /teacher/limitPageTeacher
     * 访问方式: {GET,POST}
     * 请求参数：page(页数),pageSize（页面大小）,searchText（模糊查询关键词）
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  其中map.("rows“)存放查询的教师信息数据,map.("total")存放教师模糊查询记录总数
     */
    @ResponseBody
    @RequestMapping("/limitPageTeacher")
    public HashMap getPageTeacher(HttpServletRequest request) {
        // 1. 提取传递的参数信息
        HashMap s = new HashMap();
        String pageNumber = request.getParameter("page"); //获取页面起始偏移值
        String pageSizes = request.getParameter("pageSize");   //获取页面大小
        String search = request.getParameter("searchText");    //获取搜索参数 用户信息搜索

        //2. 获取数据起始位置和数据量的大小
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

        //3. 返回教师的数据信息
        return teacherService.findAdminTeacherCourseInfo(search, offset, pageSize);
    }

    /**
     * 删除教师
     * <p>根据传入的教师id列表删除教师</p>
     * <p><pre>{@code
     * 访问url: /teacher/deleteTeacher
     * 访问方式: {GET,POST}
     * 请求参数：userIds(教师id列表)
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  map.("state“) = 0 表明删除失败，map.("state")=1表示删除成功
     */
    @ResponseBody
    @RequestMapping("/deleteTeacher")
    public HashMap deleteTeacher(HttpServletRequest request) {
        //1. 获取删除用户的id列表
        HashMap s = new HashMap();
        s.put("state",0) ;
        String userIds = request.getParameter("userIds");
        //判断传入数据是否为空
        if (userIds == null || userIds.trim().equals("")) {
            s.put("state", 0);//删除失败，设置失败状态0
            return s;
        }

        //分割传入的id列表字符串
        String[] userIdArr = userIds.split(",");
        //判断转化是否成功
        if (userIdArr == null) {
            s.put("state", 0);//删除失败，设置失败状态0
            return s;
        }

        List<Long> userIdList = new ArrayList<Long>();
        for (String userId : userIdArr) {
            userIdList.add(Long.valueOf(userId));
        }

        //2. 根据删除教师的id列表删除教师
        try {
            if (teacherService.deleteTeachers(userIdList))
                s.put("state", 1);   //删除成功，设置成功状态1
            return s; //返回删除结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 更新教师信息
     * <p>根据传入的教师id和基本信息更新教师的信息</p>
     * <p><pre>{@code
     * 访问url: /teacher/updateTeacher
     * 访问方式: {GET,POST}
     * 请求参数：id(教师id)，username（教师姓名）,sex(教师性别),birthday（教师生日）,phone（教师电话）
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return hashMap  map.("state“) = 0 表明更新失败，map.("state")=1表示更新成功
     */
    @ResponseBody
    @RequestMapping("/updateTeacher")
    public HashMap updateTeacher(HttpServletRequest request) {
        HashMap s = new HashMap();
        s.put("state", 0);
        try {
            //1. 获取教师基本信息
            request.setCharacterEncoding("utf-8");
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String sex = request.getParameter("sex");
            String birthdays = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            Teacher teacher = new Teacher(Long.valueOf(id), username, birthdays, sex, phone);

            //2. 更新教师信息
            if (teacherService.updateTeacher(teacher))
                s.put("state", 1);   //更新成功返回1
        } catch (Exception e) {
            e.printStackTrace();
            s.put("state", 0);  //失败返回0
        } finally {
            return s;
        }
    }


//
//    @ResponseBody
//    @RequestMapping("/updateTeacherInfo")
//    public HashMap updateTeacherInfo(HttpServletRequest request){
//        HashMap s = new HashMap() ;
//        String id = request.getParameter("id");
//        String username = request.getParameter("username") ;
//        String sex = request.getParameter("sex") ;
//        String birthday = request.getParameter("birthday") ;
//        String phone = request.getParameter("phone") ;
//        try{
//            teacherService.updateTeacher(new Teacher(Long.valueOf(id),username,birthday,sex,phone));
//            s.put("state","1");
//        }catch (Exception e){
//            e.printStackTrace();
//            s.put("state","0");
//        }finally {
//            return s ;
//        }
//    }


    /**
     * 获取教师信息
     * <p>根据传入的教师id列表搜索教师信息</p>
     * <p><pre>{@code
     * 访问url: /teacher/getTeacher
     * 访问方式: {GET,POST}
     * 请求参数：id(教师id)
     * }</pre></p>
     *
     * @param request 用户发送的请求
     * @return 返回查询到的教师信息
     */
    @ResponseBody
    @RequestMapping("/getTeacher")
    public Teacher getTeacher(HttpServletRequest request) {
        Teacher teacher = teacherService.findTeacher(Long.valueOf(request.getParameter("id")));
        return teacher;
    }

    /**
     * 用于教师下载教授课程下的选课成员名单
     * <p>教师下载课程选课成员的pdf</p>
     * <p><pre>{@code
     * 访问url: /teacher/downloadStaff
     * 访问方式: {GET,POST}
     * 参数：cid（课程id）,tid（教师id）
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadStaff")
    public void downloadCourseStaff(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
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
        //根据教师id和课程id查询对应课程以及选课成员名单
        Course course = courseService.findCourse(Long.valueOf(request.getParameter("cid")));
        List<Staff> staff = staffService.findAllCourceStaff(course.getId());
        Teacher teacher = teacherService.findTeacher(Long.valueOf(request.getParameter("tid")));
        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
            System.out.println("file is in");
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
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
        Paragraph title1 = new Paragraph(course.getCname() + "选课名单", f26);
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
        float[] cellsWidth = {0.55f, 0.2f, 0.25f, 0.55f, 0.55f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("用户名", f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("性别", f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("生日", f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("电话", f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("部门", f12));
        t.addCell(c5);
        //数据填充
        for (Staff staff1 : staff) {
            t.addCell(new Paragraph(staff1.getUsername(), f10));
            t.addCell(new Paragraph(staff1.getSex(), f10));
            t.addCell(new Paragraph(staff1.getBirthday(), f10));
            t.addCell(new Paragraph(staff1.getPhone(), f10));
            t.addCell(new Paragraph(staff1.getDepartment(), f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = teacher.getUsername() + "的" + course.getCname() + "选课名单";
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
     * 用于教师下载教授课程下的选课成员成绩表
     * <p>教师下载课程选课成员的成绩pdf</p>
     * <p><pre>{@code
     * 访问url: /teacher/downloadStaff
     * 访问方式: {GET,POST}
     * 参数：cid（课程id）,tid（教师id）
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadStaffGrade")
    public void downloadCourseStaffGrade(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
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
        //根据课程id以及教师id查询课程信息和教师信息以及课程下所有选课成员的成绩信息
        //再将信息封装为ResulteVo对象
        Course course = courseService.findCourse(Long.valueOf(request.getParameter("cid")));
        Teacher teacher = teacherService.findTeacher(Long.valueOf(request.getParameter("tid")));
        List<Resulte> resulteList = resulteService.findResultByCid(Long.valueOf(request.getParameter("cid")));
        List<ResulteVo> resulteVos = new ArrayList<>();
        for (Resulte resulte : resulteList) {

            //1. 根据成绩记录的uid，找到员工
            System.out.println(resulte.getUid());
            Staff staff = staffService.findStaff(resulte.getUid());
            System.out.println(staff);

            //2. 将员工信息和成绩封装成resulteBean对象
            ResulteVo resulteVo = new ResulteVo();
            resulteVo.setGrade(resulte.getGrade());
            resulteVo.setId(staff.getId());
            resulteVo.setBirthday(staff.getBirthday());
            resulteVo.setDepartment(staff.getDepartment());
            resulteVo.setPhone(staff.getPhone());
            resulteVo.setSex(staff.getSex());
            resulteVo.setUsername(staff.getUsername());

            //3. 将对象添加到结果集列表
            resulteVos.add(resulteVo);
        }


        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
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
        Paragraph title1 = new Paragraph(course.getCname() + "成绩单", f26);
        title1.setAlignment(Element.ALIGN_CENTER);
        Chapter chapter1 = new Chapter(title1, 1);
        chapter1.setNumberDepth(0);
        Section section1 = chapter1;

        // 创建6列的表格
        int colNumber = 6;
        PdfPTable t = new PdfPTable(colNumber);
        //设置段落上下空白
        t.setSpacingBefore(25);
        t.setSpacingAfter(25);
        t.setHorizontalAlignment(Element.ALIGN_CENTER);// 居左
        float[] cellsWidth = {0.55f, 0.2f, 0.25f, 0.55f, 0.55f, 0.25f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("用户名", f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("性别", f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("生日", f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("电话", f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("部门", f12));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Paragraph("分数", f12));
        t.addCell(c6);
        //填充数据信息
        for (ResulteVo resulteVo : resulteVos) {
            t.addCell(new Paragraph(resulteVo.getUsername(), f10));
            t.addCell(new Paragraph(resulteVo.getSex(), f10));
            t.addCell(new Paragraph(resulteVo.getBirthday(), f10));
            t.addCell(new Paragraph(resulteVo.getPhone(), f10));
            t.addCell(new Paragraph(resulteVo.getDepartment(), f10));
            t.addCell(new Paragraph(String.valueOf(resulteVo.getGrade()), f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = teacher.getUsername() + "的" + course.getCname() + "成绩单";
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
     * 用于管理员下载教师表
     * <p>管理员打印所有教师信息的pdf</p>
     * <p><pre>{@code
     * 访问url: /teacher/downloadTeacher
     * 访问方式: {GET,POST}
     * }</pre></p>
     *
     * @param request  用户请求对象
     * @param response 服务器响应对象，包含响应的pdf数据
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping("/downloadTeacher")
    public void downloadTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
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
        //查询所有的教师信息，封装成TeacherPayVo视图对象
        List<TeacherPayVo> teacherPayVos = (List<TeacherPayVo>) teacherService.findAllTeacherInfo().get("rows");

        /*******pdf文件内容生成开始*********/
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);

        //创建文件夹
        String filePar = null;
        filePar = "G:/systemTest/" + new Date().getTime();// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {
            myPath.mkdirs();
        }
        String filePath = null;
        filePath = filePar + "/" + new Date().getTime() + ".pdf";
        file = new File(filePath);
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
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
        Paragraph title1 = new Paragraph("教师名单", f26);
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
        float[] cellsWidth = {0.55f, 0.2f, 0.25f, 0.55f, 0.55f}; // 定义表格的宽度
        t.setWidths(cellsWidth);// 单元格宽度
        t.setTotalWidth(500f);//表格的总宽度
        t.setWidthPercentage(100);// 表格的宽度百分比
        //设置表头
        PdfPCell c1 = new PdfPCell(new Paragraph("教师姓名", f12));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("性别", f12));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("生日", f12));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("电话", f12));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("薪资", f12));
        t.addCell(c5);
        //表格数据填充
        for (TeacherPayVo teacherPayVo : teacherPayVos) {
            t.addCell(new Paragraph(teacherPayVo.getUsername(), f10));
            t.addCell(new Paragraph(teacherPayVo.getSex(), f10));
            t.addCell(new Paragraph(teacherPayVo.getBirthday(), f10));
            t.addCell(new Paragraph(teacherPayVo.getPhone(), f10));
            t.addCell(new Paragraph(String.valueOf(teacherPayVo.getPay()), f10));
        }

        section1.add(t);
        document.add(chapter1);
        document.close();
        /*******pdf文件生成结束*********/

        try {
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf");
            String filename = "教师名单";
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
