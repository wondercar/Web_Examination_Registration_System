package myclass.bol;

public class examinee {
//	实体层
	private String examID = null;//身份证号
	private String examName = null;//姓名
	private String sex = null;//性别
	private String password = null;//密码
	private String examType = null;//考试类型
	private String school = null;//工作单位
	
	/*
	 * 无参构造函数
	 */
	public examinee() {
		
	}
	/*
	 * 有参构造函数
	 */
	public examinee(String examID,String examName,String sex,String password,String examType,String school) {
		this.examID = examID;
		this.examName = examName;
		this.sex = sex;
		this.password = password;
		this.examType = examType;
		this.school = school;
	}
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	
}
