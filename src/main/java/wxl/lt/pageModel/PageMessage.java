/**
 * 
 */
package wxl.lt.pageModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author wxlHonest
 *
 */
public class PageMessage implements Serializable {

	private static final long serialVersionUID = 2146623581096766735L;

	/**
	 * 返回给前台页面的信息
	 */
	private String errorMsg;//错误信息; error message
	
	private String successMsg;//成功信息
	
	private boolean success=true;  //是否成; success or not
	
	private List<?> list;//对象的List
	
	private int flag=0;//状态码标记; status flag

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

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	
}
