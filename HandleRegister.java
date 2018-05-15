package myservlet.controls;
import mybean.data.*;//导入javabean:mybean包
import java.io.*;
import myclass.bll.*;//引入业务逻辑层包
import javax.servlet.*;
import javax.servlet.http.*;

public class HandleRegister extends HttpServlet{
	public void init(ServletConfig config) throws ServletException{
		super.init(config);//初始化
	}
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");
			s = new String(bb); 
		}
		catch(Exception ee){
			System.out.println(ee.toString());}
		return s;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
//		System.out.println("registerservlet");
		
//		调用JavaBean registerBean
		registerBean reg = new registerBean();
//		保存一个变量register
		request.setAttribute("register", reg);
		/*
		 * @trim 去除得到字符串的前后空格
		 * 下面获取JSP页面接收到的数据
		 */
		String examID = request.getParameter("examID").trim(),
				examName = request.getParameter("examName").trim(),
				sex = request.getParameter("sex").trim(),
				examType = request.getParameter("examType").trim(),
				password = request.getParameter("password").trim(),
				school = request.getParameter("school").trim(),
				backMessage = "";
				int result = 0;
		try {
			//调用业务逻辑组件
			examineeBll exambll = new examineeBll();
			
			//servlet转换到jsp页面的数据
			result = exambll.CreateExaminee(examID, examName, sex, password, examType, school);
			
			/*
			 * result
			 * 0:身份证为空	-1:身份证或者密码不符合要求	1:报名成功
			 */
			
			if( result == 0 ) {	//身份证号或者密码不符合要求
				backMessage = "身份证号或密码不符合要求！请重新报考！";
				reg.setBackMessage(backMessage);
			}
			if( result == -1 ) { //身份证为空
				backMessage = "数据库访问发生错误！";
				reg.setBackMessage(backMessage);
			}
			if( result == -2 ) { //身份证号码已存在
				backMessage = "身份证号码已存在！";
				reg.setBackMessage(backMessage);
			}
			if( result == 1 ) { //给bean赋值
				backMessage = "报名成功！";
				reg.setBackMessage(backMessage);
				reg.setExamID(examID);
				reg.setExamName(handleString(examName));
				reg.setSex(handleString(sex));
				reg.setExamType(handleString(examType));
				reg.setPassword(password);
				
			}
		}
		catch(Exception ex){
			backMessage = "发生错误!" + ex.toString();
			reg.setBackMessage(backMessage);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showRegisterMess.jsp");	//页面跳转
		dispatcher.forward(request, response);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException,IOException{
		doPost(request,response);
	}
}
