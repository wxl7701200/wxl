package wxl.lt.dao;

import wxl.lt.form.UserForm;
import wxl.lt.model.TbUser;

public interface GetUserInfDao {

	public TbUser getUserLoginInf(UserForm form);
	
	public int userRegister (TbUser user);
	
	public TbUser checkUserUser(UserForm form);
	
	public int updateUser(TbUser user);
	//public TbUser checkUserByUserEmail(UserForm form);
	//public TbUser checkUserByUserMobile(UserForm form);
}
