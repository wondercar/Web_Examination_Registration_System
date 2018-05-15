package myclass.bll;

import java.util.ArrayList;

import myclass.bol.examinee;
import myclass.dal.examineeDal;

public class examineeBll {
	
	/*
	 * 创建考生对象
	 * @return 
	 * 0：身份证号或密码不符合要求，1：报名成功，-1：数据库访问发生错误，-2：身份证号已存在
	 */
	public int CreateExaminee(
			String examID,
			String examName,
			String sex,
			String password,
			String examType,
			String school) {
		examineeDal examDal = new examineeDal();//实例化考生数据库对象examineeDal
		int result = 0;//初始化结果为身份证号或密码不符合要求
		int usernumber = 3;//输入的身份证位数为3位
		
//		检验输入的账号位数是否为usernumber位数
		if( ( examID.length()==usernumber ) ){
			boolean isLD = true;
			
//			遍历输入的账号字符类型，为a-z，A-Z，0-9
			for( int i=0;i<examID.length();i++ ) {
				char c = examID.charAt(i);
				if( !(( c<='z' )&&( c>='a' ))||( c<='Z' )&&( c>='A' )||( c<='9' )&&( c>='0' )){
					isLD = false;
				}
			}
//			查询数据库中examID存在情况
			if( isLD==true&&examDal.getExamineeByID(examID)!=null ) {
				return result = -2;
			}
//			数据库中不存在该数据
			if( isLD = true ) {
//				条件成立，result对象接受
				result = examDal.CreateExaminee(examID, examName, sex, password, examType, school);
			}
		}
		else {
//			result为0，遍历方法不通过的结果
			System.out.println("身份证号或密码不符合要求！");
			System.out.println("函数返回值："+result+"你输入的身份证号或密码不符合要求");
		}
		return result;
	}
	
	/*
	 * 考生登录方法	
	 * @param 
	 * examID 身份证号	@param password 密码
	 * return	0:身份证号或者密码不符合要求	-1:身份证号或者密码不正确		1:登陆成功
	 */
	public int examineeLogin(String examID,String password) {
		examineeDal examDal = new examineeDal();
		int result = 0;//初始返回结果为身份证号或者密码不符合要求
		int usernumber = 3;//输入的身份证位数为3位
		
		if( examID.length()==usernumber && password.length()>0 ) {
			examinee exam = null;
			exam = examDal.getExamineeByIdPwd(examID, password);
			if( exam!=null ) {
				result = 1;//登录成功
			}
			else {
				result = -1;//身份证号或者密码不正确
			}
		}
		return result;
	}
	
	/*
	 * 查询考生信息，单页显示考生信息
	 * param examID
	 * return
	 * 成功：examinee对象，失败：null
	 */
	public examinee getExamineeByID(String examID) {
		examinee exam = null;
		examineeDal examdal = new examineeDal();
		exam = examdal.getExamineeByID(examID);
		return exam;//返回结果，结束
	}
	/*
	 * examinee对象集，全部考生信息
	 * return
	 * 返回对象集:成功，null:失败
	 */
	public ArrayList<examinee> getExamineeAll(){
		examineeDal examdal = new examineeDal();
		return examdal.getExamineeAll();
	}
	/*
	 * 修改用户密码
	 * return
	 * 0:密码为空或者新旧密码相同，1:成功设置密码，-1:旧密码不正确
	 */
	public int setExamineePwd(String examID,String newPassword,String oldPassword) {
		int result = 0;
		examineeDal examdal = new examineeDal();
		if( newPassword =="" || newPassword.equals(oldPassword) ) {
			return result;
		}
		if( examdal.getExamineeByIdPwd(examID, oldPassword) != null ) {
			result = examdal.setExaminePwd(examID, newPassword);
		}
		else {
			result = -1;
		}
		return result;
	}
	/*
	 * 删除考生数据
	 * return
	 * -2:考生号为空，1:删除成功，-1:出现异常，0:删除失败
	 */
	public int deleteExmineeByID(String examID) {
		examineeDal examdal = new examineeDal();
		int result = 0;
		if( examID == null ) {
			result = -2;
		}
		if( examID.length() > 0 ) {
			result = examdal.deleteExmineeByID(examID);
		}
		return result;
	}
}
