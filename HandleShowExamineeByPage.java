package myservlet.controls;
import java.io.*;
import mybean.data.*;
import myclass.bol.*;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.sun.javafx.collections.IntegerArraySyncer;

public class HandleShowExamineeByPage extends HttpServlet{

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");
			s = new String(bb);
		} catch (Exception ee) {
//			null
		}
		return s;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		loginBean log = (loginBean)session.getAttribute("login");
		if( log==null ) {
			response.sendRedirect("login.jsp");
		}
		else {
			showExamineeByPage(request,response);
		}
	}

	public void showExamineeByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);//创建一个会话状态session
		StringBuffer presentPageResult = new StringBuffer();//常量字符串presentPageResult
		showExamineeByPage showBean = null;
//		如果showBean不存在则创建一个javaBean
		try {
			showBean = (showExamineeByPage)session.getAttribute("examieebypage");//获取examineebypage的属性值
			if (showBean == null) {
				showBean = new showExamineeByPage();//实例化对象showBean
				session.setAttribute("examineebypage", showBean);//给showBean设置属性值
			} 
		} catch (Exception e) {
			showBean = new showExamineeByPage();
			session.setAttribute("examineebypage", showBean);//给showBean设置属性值
		}
//		每页显示三条记录
		showBean.setPageSize(5);
		String strShowPage = request.getParameter("showPage");//获取jsp页面中的页码
//		检查显示页数文本框中输入数据的合法性
		for( int i=0;i<strShowPage.length();i++ ) {
			char c=strShowPage.charAt(i);
			if( !( c>='0' && c<='9' ) ) {//每页中输入的数据小于0大于9的情况
				strShowPage = "1";//初始页码
				break;
			}
		}
		if( strShowPage=="" ) {
			strShowPage = "1";
		}
		int showPage = Integer.parseInt(strShowPage);//设定jsp页面中当前页面的的页码
		int pageSize = showBean.getPageSize();//获取总页码数
//		设置showBean的属性值
		try {
			examineeBll exambll = new examineeBll();
			showBean.setList(exambll.getExamineeAll());
			int m = showBean.getList().size();
			int n = pageSize;
			int pageAllCount = ( (m%n)==0) ? (m/n):(m/n+1);//判断存储的数据所需的页码
			showBean.setPageAllCount(pageAllCount);
			if (showPage > showBean.getPageAllCount()) {
				showPage = 1;//返回第一页
			}
			if (showPage <= 0) {
				showPage = showBean.getPageAllCount();//回到最大页码
			}
			showBean.setShowPage(showPage);//设置当前页面的页码
			presentPageResult = show(showPage, pageSize, showBean);//将页面的页码信息传给presentPageResult
			showBean.setPresentPageResult(presentPageResult);//设置presentPageResult的值
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showExamineeAll.jsp");//存放一个结果转发到jsp页面
		dispatcher.forward(request, response);
	}
	/*
	 * 列表显示考生数据
	 * param page
	 * param pageSize
	 * param showBean
	 * @return
	 */
	public StringBuffer show(int page,int pageSize,showExamineeByPage showBean) {
		StringBuffer str = new StringBuffer();//创建字符串变量，可改变字符串的值
		try {
			for( int i=(page-1)*pageSize;i<=pageSize-1;i++ ) {
			str.append("<tr>");
			str.append("<td>"+handleString(showBean.getList().get(i).getExamName())+"</td>");//考生姓名
			str.append("<td>"+handleString(showBean.getList().get(i).getExamType())+"</td>");//考生考试类型
			str.append("<td>"+handleString(showBean.getList().get(i).getSex())+"</td>");//考生性别
			str.append("<td>"+handleString(showBean.getList().get(i).getSchool())+"</td>");//工作单位
			str.append("</tr>");
			System.out.println("获取了"+(i+1)+"条数据");
			}
			System.out.println("这是第"+page+"页数据");
		}
		catch(Exception e){
//			System.out.println("执行到这里page="+page);
		}
		return str;
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
