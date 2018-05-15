package myclass.dal;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

//	DBAccess连接数据库
public class DBAccess {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private PreparedStatement prpSql = null;
	public DBAccess() {}
	
		/*	数据库连接
		 *  @return Connnection
		 */
	public Connection getConn() {
		if ( conn==null ) {//验证连接状态
			getConnection();
		}
		return conn;//连接状态
	}
		
		/* 
		 * 函数功能：获得连接对象statement 
		 */
	public void getConnection(){
		try {
			System.out.println("连接数据库成功！");
			Context ctx = new InitialContext();//使用tomcat的content.xml目录
			javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/dataexam");
			this.conn = ds.getConnection();
			this.stmt = this.conn.createStatement();
//			System.out.println("数据库连接已建立！"); 
		} catch (NamingException e) {
			System.out.println("请检查数据库连接池配置是否正确！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("请检查数据库是否启动！");
			e.printStackTrace();
		}
	}
	
		/*
		 * 返回结果集
		 * @param strSql 查询语句，无参数查询
		 * @return
		 * 成功返回结果集，失败返回null
		 */
		public ResultSet query(String strSql) {
//			System.out.println("sql"+strSql);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(strSql);
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return rs;
			}
		}
		
		/*
		 * 返回结果集
		 * param prpSql 查询预处理，参数查询
		 * @return
		 * 成功返回结果集，失败返回null
		 */
		public ResultSet query(PreparedStatement prpSql) {
//			 System.out.println(prpSql.toString());
			 this.prpSql = prpSql;
			 ResultSet rs= null;
			 try {
				rs = this.prpSql.executeQuery();
				 return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return rs;
			}
		 }
		 /*
		  * 插入多条数据
		  * @param sqls insert语句数组
		  * @return
		  * 成功返回true，失败返回false
		  */
		public boolean insert(String[] sqls) {
			boolean breturn = false;
			try {
				conn.setAutoCommit(false);
				for( int i=0;i<sqls.length;i++ ) {
					if( sqls[i]!=null ) {
						stmt.addBatch(sqls[i]);
					}
				}
				stmt.executeBatch();
				conn.commit();
				conn.setAutoCommit(true);
				breturn = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return breturn;
		} 
		/*
		 * @param strSql 无参查询，执行固定语句|获取查询结果
		 * @return
		 * 成功返回影响行数，失败返回0，数据库访问错误返回-1
		 */
		public int executeSql(String strSql) {
			int result = 0;
			try {
				stmt = conn.createStatement();//无参（静态）查询，使用createStatement方法
				result = stmt.executeUpdate(strSql);
			} catch (SQLException e) {
				System.out.println("产生异常，：at DBAccess.executeSql()数据库执行无参查询");
				e.printStackTrace();//输出详细异常
				result = -1;
			}
			return result;
		}
		/*
		 * @param prpSql 有参查询，包含in函数（范围）
		 * @return
		 * 成功返回影响行数，失败返回0
		 */
		public int executeSql(PreparedStatement prpSql) {
			int result = 0;
			try {
				this.prpSql = prpSql;//传入的参数传给类本身
				result = this.prpSql.executeUpdate();//更新查询
			} catch (SQLException e) {
				System.out.println("产生异常，：at DBAccess.executeSql()数据库执行有参查询");
				e.printStackTrace();//输出详细异常
				result = -1;
			}
			return result;
		}
		/*
		 * 返回结果true或false
		 * @param sqls查询语句数组
		 * @return
		 * 成功true，失败false
		 */
		public boolean executeSql(String[] sqls) {
			boolean breturn = false;
			try {
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				for( int i=0;i<sqls.length;i++ ) {
					if( sqls[i]!=null ) {
//					System.out.println("sqls[0]="+sqls[i]);
						stmt.addBatch(sqls[i]);
					}
				}
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			breturn = true;
			} catch (SQLException e) {
				System.out.println("产生异常，：at DBAccess.executeSql()数据库执行");
				e.printStackTrace();
			}
			return breturn;
		}
		/*
		 * 关闭连接
		 */
		public void closeConnection() {
			try {
				if( rs!=null ) {
					rs.close();
					rs = null;
				}
				if( stmt!=null ) {
					stmt.close();
					stmt = null;
				}
				if( conn!=null ) {
					conn.close();
					conn = null;
				}
				if( prpSql!=null ) {
					prpSql.close();
					prpSql = null;
				}
//			System.out.println("数据库连接关闭！");
			} catch (SQLException e) {
				System.out.println("产生异常，：at DBAccess.closeConnection()");
				e.printStackTrace();
			}
		}
		/*
		 * 获取系统时间
		 * 静态方法，直接用类名调用
		 */
		public static String getSysDate() {
			DBAccess dba = new DBAccess();
			String sql = "select sysdate() sysdate;";
			try {
				dba.getConnection();
				ResultSet rs = dba.query(sql);
				String currentDate = null;
				if( rs.next() ) {
					currentDate = rs.getString("sysdate");
				}
				return currentDate;
			} catch (SQLException e) {
				System.out.println("产生异常：at DBAccess.getSysDate()");
				e.printStackTrace();
				return null;
			} finally {
				dba.closeConnection();
			}
		}
}
