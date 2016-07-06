/**
 * 
 */
package wxl.lt.dao.impl;

import org.springframework.stereotype.Repository;

import wxl.lt.dao.GetUserInfDao;
import wxl.lt.form.UserForm;
import wxl.lt.framework.constant.GlobalConstant;
import wxl.lt.model.TbUser;

/**
 * @author wxlHonest
 *
 */
@Repository
public class GetUserInfDaoImpl extends BaseDao<TbUser> implements GetUserInfDao {

	/* 登陆验证
	 * (non-Javadoc)
	 * @see wxl.lt.dao.GetUserInfDao#getUserLoginInf(java.lang.String, java.lang.String)
	 */
	@Override
	public TbUser getUserLoginInf(UserForm form) {
//		String hql = "from TbUser u where (u.userName='"+form.getUserName()+"' or u.userEmail='"+form.getUserName()
//		+"' or u.userMobileNo='"+form.getUserName()+"') and u.password='"+form.getPassword()+"'";
		
		String hql = "from TbUser u where u.userName='"+form.getUserName()+"' or u.userEmail='"+form.getUserName()
		+"' or u.userMobileNo='"+form.getUserName()+"'";
		
		return this.get(hql);
	}

	/* 用户注册
	 * (non-Javadoc)
	 * @see wxl.lt.dao.GetUserInfDao#userRegister(wxl.lt.form.UserForm)
	 */
	@Override
	public int userRegister(TbUser user) {
		try {
			this.save(user);
			return GlobalConstant.SUCCESS;
		} catch (Exception e) {
			System.out.println("注册失败");
		}
		return GlobalConstant.FAILED;
	}

	@Override
	public TbUser checkUserUser(UserForm form) {
		//String hql = "from SystemUser s where s.loginName='"+loginName+"' and s.status="+GlobalConstant.ENABLE;
		String hql = "from TbUser u where u.userName='"+form.getUserName()+"' or u.userEmail='"+form.getUserEmail()+"' or u.userMobileNo='"+form.getUserMobileNo()+"'";
		return this.get(hql);
	}

	@Override
	public int updateUser(TbUser user) {
		try {
			this.update(user);
			return GlobalConstant.LOGIN_OK;
		} catch (Exception e) {
			return GlobalConstant.FAILED;
		}
		
	}

	

}
