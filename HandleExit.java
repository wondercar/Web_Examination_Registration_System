package myservlet.controls;
import mybean.data.*;
import java.io.*;
import myclass.bol.*;
import sun.rmi.log.LogOutputStream;
import myclass.bll.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.rowset.serial.SerialException;

public class HandleExit extends HttpServlet{
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");
			s= new String (bb);
		} catch (Exception e) {}
		return s;
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		loginBean log = (loginBean)session.getAttribute("login");
		
		if( log==null ) {
			response.sendRedirect("login.jsp");
		}
		else {
			try {
				logoutExam(request,response);
			} catch (SerialException e) {
				e.printStackTrace();
			}
		}
	}
	public void logoutExam(HttpServletRequest request,HttpServletResponse response) throws SerialException,IOException{
		HttpSession session = request.getSession(true);
		session.invalidate();
		response.sendRedirect("index.jsp");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
