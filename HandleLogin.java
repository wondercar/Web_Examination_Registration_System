package myservlet.controls;
import mybean.data.*;
import java.io.*;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HandleLogin extends HttpServlet{
	public void init(ServletConfig config) throws ServletException{
		super.init(config);//初始化
	}
	//字符转换为汉字编码
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");//用数组bb装载汉字字符
			s = new String(bb);//汉字编码数组对象赋值给s
		}
		catch(Exception e) {}
		return s;
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginBean log = null;
		String backMessage = "";
		HttpSession session = request.getSession(true);
		try {
			log = (loginBean)session.getAttribute("login");
			if( log == null ) {
				log = new loginBean();
				session.setAttribute("login", log);
			}
		}
		catch(Exception ee){
			log = new loginBean();
			session.setAttribute("login", log);
		}
		String loginName = request.getParameter("loginName").trim(),
				password = request.getParameter("password").trim();
		loginName = handleString(loginName);
		boolean ok = log.isSuccess();
		if( ok=true && loginName.equals(log.getLoginName()) ) {
			backMessage = "已经登录了！";
			log.setBackMessage(backMessage);
		}
		else {
			examineeBll exambll = new examineeBll();
			int result = exambll.examineeLogin(loginName, password);
			if( result==1 ) {
//				result结果为1
				backMessage = "登陆成功！";
				log.setBackMessage(backMessage);
				log.setSuccess(true);
				log.setLoginName(loginName);
			}
			else if( result==0 ){
//				result结果为0
				backMessage = "你输入的身份证号码或者密码不符合要求！";
				log.setBackMessage(backMessage);
				log.setSuccess(false);
				log.setLoginName(loginName);
				log.setPassword(password);
			}
			else {
//				result结果为-1
				backMessage = "你输入的身份证号码不存在或密码不正确！";
				log.setBackMessage(backMessage);
				log.setSuccess(false);
				log.setLoginName(loginName);
				log.setPassword(password);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showLoginMess.jsp");
		dispatcher.forward(request, response);
	}
}
