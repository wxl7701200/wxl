/**
 * 
 */
package wxl.lt.form;

import java.util.List;

/**
 * @author wxlHonest
 *
 */
public class UserForm {

	private String userEmail;

	private String userName;

	private String password;
	
	private String userMobileNo;

	/**
	 * 返回给前台页面的信息
	 */
	private String errorMsg;// 错误信息; error message

	private boolean success = true; // 是否成; success or not

	private List<?> list;// 对象的List

	private int flag = 0;// 状态码标记; status flag

	
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
