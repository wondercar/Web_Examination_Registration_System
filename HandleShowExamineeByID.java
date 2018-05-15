package myservlet.controls;
import java.io.*;
import mybean.data.*;
import myclass.bol.*;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HandleShowExamineeByID extends HttpServlet{

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");
			s = new String(bb);
		} catch (Exception e) {
			//null
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
			showExamineeByID(request,response);
		}
	}
	
	public void showExamineeByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showExamineeByIDBean examineeinfo = new showExamineeByIDBean();
		request.setAttribute("examineeinfo", examineeinfo);
		String examID = request.getParameter("examID");
		examineeBll exambll = new examineeBll();
		examinee exam = new examinee();
		exam = exambll.getExamineeByID(examID);
		if( exam!=null ) {
			examineeinfo.setBackMessage("查询到的考生信息如下：");
			examineeinfo.setExamID(exam.getExamID());
			examineeinfo.setExamType(handleString(exam.getExamType()));
			examineeinfo.setSchool(handleString(exam.getSchool()));
			examineeinfo.setSex(handleString(exam.getSex()));
		}
		else {
			examineeinfo.setBackMessage("未查询到该考生信息！");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showExamineeByID.jsp");
		dispatcher.forward(request, response);
	}
}
