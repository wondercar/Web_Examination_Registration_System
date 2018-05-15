package myclass.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import myclass.dal.DBAccess;
import myclass.bol.examinee;

//	examineeDal类实现数据库增删改查操作
public class examineeDal {
	
	/* 
	 * 创建考生对象
	 * return 
	 * 成功：1 失败：0 
	 */
	public int CreateExaminee(String examID,String examName,String sex,String password,String examType,String school) {
		int result = 0;
		String createSql = "insert into examinee(id,name,sex,password,examType,school)"+"values('"+examID+"','"+examName+"','"+sex+"','"+password+"','"+examType+"','"+school+"')";
//		DBAccess对象实例化，将使用DBAccess
		DBAccess dba = new DBAccess();
		try {
			if(dba.getConn()!=null) {//是否成功连接
				System.out.println("录入考生信息成功！");
				result = dba.executeSql(createSql);//result存储无参查询结果
			}
		}
		catch(Exception e){
			System.out.println("examineedal出现如下错误：<br>");
			System.out.println(e);
		}
		finally {
			dba.closeConnection();//执行成功关闭连接
		}
		return result;
	}
	
	/* @param examID
	 * 按照考生身份证号获得一个考生对象
	 * @return
	 * 成功返回examinee对象，失败返回null	 
	 */
	public examinee getExamineeByID(String examID) {
		DBAccess dba = new DBAccess();
		examinee exam = null;
		try {
			if( dba.getConn()!=null&&examID!=null ) {
				System.out.println("查询数据库成功！");
				String str = "select * from examinee where id = '"+examID+"'";
				ResultSet rst = dba.query(str);
				if( rst != null&&rst.next() ) {
//					将数据赋值给实体
					exam = new examinee(rst.getString(1),
							rst.getString(2),
							rst.getString(3),
							rst.getString(4),
							rst.getString(5),
							rst.getString(6));
				}
			}
		}
		catch(Exception ne){
		System.out.println("examineedal:getExamineeByID发生错误");
		}
		finally {
		dba.closeConnection();
		}
		return exam;
		}

		/* 
		 * 返回examinee对象数组
		 * return
		 * 成功：examinee对象数组，失败：null
		 */
	public ArrayList<examinee> getExammineeAll(){
		DBAccess dba = new DBAccess();
		ArrayList<examinee> examList = new ArrayList<examinee>();
		try {
			if(dba.getConn() != null) {
				String str = "select * from examinee";
				ResultSet rst = dba.query(str);//从一个已经存在的表中执行查询
			while(rst != null&&rst.next())//循环赋值
			{
				if(rst.getString("id") != null) {
					examinee exam = new examinee(rst.getString(1),rst.getString(2),
							rst.getString(3),
							rst.getString(4),
							rst.getString(5),
							rst.getString(6));
//					System.out.println(rst.getString(5));
						}
					}
				}
			}
			catch(Exception e) {
				System.out.println("examineedal:getExamineeByID发生错误");
			}
			finally {
				dba.closeConnection();
			}
			return examList;
		}
	
		/*
		 * 功能：返回examinee对象数组	
		 * @return 
		 * 成功：examinee对象数组，失败：null
		 */
		public ArrayList<examinee> getExamineeAll(){
			DBAccess dba = new DBAccess();
			ArrayList<examinee> examList = new ArrayList<examinee>();
			try {
				if(dba.getConn() != null) {
					String str = "select * from examinee";//查询语句
					ResultSet rst = dba.query(str);//从一个已存在的表中读取数据
				while( rst!=null&&rst.next() ) {//循环赋值
					if(rst.getString("id")!=null) {
						examinee exam = new examinee(rst.getString(1),
								rst.getString(2),
								rst.getString(3),
								rst.getString(4),
								rst.getString(5),
								rst.getString(6));
						examList.add(exam);
					}
				}
			}
			}
			catch(Exception e) {
				System.out.println("examineedal:getExamineeall发生错误");
			}
			finally {
				dba.closeConnection();
			}
			return examList;
		}
		/*
		 * 根据身份证和密码返回考生对象	
		 * @return 
		 * 成功：examinee对象，失败：null
		 */
		public examinee getExamineeByIdPwd(String examID,String password) {
			DBAccess dba = new DBAccess();
			examinee exam = null;
			try {
				if( dba.getConn()!=null && examID!=null && password!=null ) {
					System.out.println("获取考生信息成功！");
					String str = "select * from examinee where id=? and password =?";
					PreparedStatement prpSql;
					prpSql = dba.getConn().prepareStatement(str);
					prpSql.setString(1,examID);//考生ID
					prpSql.setString(2,password);//考生密码
					ResultSet rst = dba.query(prpSql);//查询数据
				if(rst!=null&&rst.next()) {
					exam = new examinee(
							rst.getString(1),
							rst.getString(2),
							rst.getString(3),
							rst.getString(4),
							rst.getString(5),
							rst.getString(6));
				}
				if(prpSql != null) {
					prpSql.close();
					prpSql = null;
					}
				}
			}
			catch(Exception ne) {
				System.out.println("examineedal:getexamineebyIDpwd发生错误");
			}
			finally {
				dba.closeConnection();
			}
			return exam;
		}
		/*
		 * 函数功能修改用户密码	
		 * @return 
		 * 成功：1，失败：0
		 */
		public int setExaminePwd(String examID,String newPassword) {
			DBAccess dba = new DBAccess();
			int result = 0;
			try {
				if( dba.getConn() != null ) {
					PreparedStatement prpSql;
					System.out.println("修改考生密码成功！");
					String strSql = "Update examinee set password=? where id=?";
					prpSql = dba.getConn().prepareStatement(strSql);
					prpSql.setString(1, newPassword);
					prpSql.setString(2, examID);
					result = dba.executeSql(prpSql);
				if( prpSql!=null ) {
					prpSql.close();
					prpSql = null;
				}
			}
		}
			catch(Exception ne) {
				System.out.println("examineedal:修改用户密码发生错误");
				result = -1;
			}
			finally {
				dba.closeConnection();
			}
			return result;
		}
		
		/*
		 *  @param examID
		 *  按身份证号删除考生	
		 *  @return 
		 *  成功：1，失败：0，出现异常：exception
		 */
		public int deleteExmineeByID(String examID) {
			DBAccess dba = new DBAccess();
			int result = 0;
			try {
				if( dba.getConn()!=null ) {
					PreparedStatement prpSql;
					System.out.println("删除考生信息成功！");
					String strSql = "delete from examinee where id=?";
					prpSql = dba.getConn().prepareStatement(strSql);
					prpSql.setString(1, examID);
					result = prpSql.executeUpdate();
				if(prpSql != null) {
					prpSql.close();
					prpSql = null;
				}
				}
			}
			catch(Exception ne) {
				ne.printStackTrace();
				System.out.println(ne.toString());
				return -1;
			}
			finally {
				dba.closeConnection();
			}
			return result;
		}
	}
