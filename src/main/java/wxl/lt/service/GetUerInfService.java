/**
 * 
 */
package wxl.lt.service;

import wxl.lt.form.UserForm;
import wxl.lt.model.TbUser;

/**
 * @author wxlHonest
 *
 */
public interface GetUerInfService {
	
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	public Integer getUserLoginInf(UserForm form);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public int userRegister(TbUser user);
	
	
	/**
	 * 注册验证用户名邮箱手机是否重复
	 * @param form
	 * @return
	 */
	public TbUser checkUserUser(UserForm form);
	//public TbUser checkUserByUserEmail(UserForm form);
	//public TbUser checkUserByUserMobile(UserForm form);
	
	public TbUser selectUserLogin();
}
