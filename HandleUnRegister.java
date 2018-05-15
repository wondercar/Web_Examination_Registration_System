package myservlet.controls;
import java.io.*;
import mybean.data.*;
import myclass.bol.*;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HandleUnRegister extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		loginBean log = (loginBean)session.getAttribute("login");
		if( log==null ) {
			response.sendRedirect("login.jsp");
		}
		else {
			String loginName = log.getLoginName();
			unRegister(loginName,request,response);
		}
	}
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void unRegister(String loginName,HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		HttpSession session = request.getSession(true);
		String password = request.getParameter("password");
		unregisterBean unreg = new unregisterBean();
		request.setAttribute("unregister", unreg);
		unreg.setLoginName(loginName);
		int result;
		String backMessage = null;
		
		if( password==null ) {
			password = "";
		}
		examineeBll exambll = new examineeBll();
		try {
			result = exambll.examineeLogin(loginName, password);
			if( result==1 ) {
				if( exambll.deleteExmineeByID(loginName)==1 ) {
					backMessage = "注销考试成功！";
					unreg.setBackMessage(backMessage);
					unreg.setFlag(true);
					session.invalidate();
				}
				else {
					backMessage = "未删除该考生，注销失败！";
					unreg.setBackMessage(backMessage);
					unreg.setFlag(false);
				}
			}
			else {
				backMessage = "输入的密码不正确，注销失败！";
				unreg.setBackMessage(backMessage);
				unreg.setFlag(false);
			}
		} catch (Exception e) {
			unreg.setBackMessage("发生异常:"+e);
			unreg.setFlag(false);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showUnRegister.jsp");
		dispatcher.forward(request, response);
	}
}
