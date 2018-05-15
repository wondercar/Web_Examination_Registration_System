package myservlet.controls;
import mybean.data.*;
import java.io.*;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HandleModifPwd extends HttpServlet{

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		loginBean log = (loginBean)session.getAttribute("login");
		if( log==null ) {
			response.sendRedirect("login.jsp");
		}
		else {
			modifyPassword(request,response);
		}
	}
	
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		loginBean log = (loginBean)session.getAttribute("login");
		String loginName = log.getLoginName();
		passwordBean pwd = new passwordBean();
		request.setAttribute("password", pwd);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		examineeBll exambll = new examineeBll();
		int result = exambll.setExamineePwd(loginName, newPassword,oldPassword);
		if( result==0 ) {
			pwd.setBackMessage("密码不符合要求，修改不成功！");
		}
		else if( result==-1 ) {
			pwd.setBackMessage("旧密码不正确，密码更新失败！");
		}
		else {
			pwd.setBackMessage("密码修改成功！");
			pwd.setNewPassword(newPassword);
			pwd.setOldPassword(oldPassword);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showModifyPassword.jsp");
		dispatcher.forward(request,response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}
