/**
 * 
 */
package wxl.lt.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wxl.lt.dao.GetUserInfDao;
import wxl.lt.form.UserForm;
import wxl.lt.framework.constant.GlobalConstant;
import wxl.lt.model.TbUser;
import wxl.lt.service.GetUerInfService;
import wxl.lt.utils.StringUtil;

/**
 * @author wxlHonest
 *
 */
@Service
public class GetUserInfServiceImpl implements GetUerInfService {
	@Autowired
	private GetUserInfDao getUserInfDao;

	private TbUser user = new TbUser();

	/*
	 * (non-Javadoc)
	 * 
	 * @see wxl.lt.service.GetUerInf#getUserLoginInf(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Integer getUserLoginInf(UserForm form) {
		if (StringUtil.isNotEmpty(form.getUserName()) && StringUtil.isNotEmpty(form.getPassword())) {
			TbUser userLogin = getUserInfDao.getUserLoginInf(form);
			if (user != null) {
				if (userLogin.getPassword().equals(form.getPassword())) {
					user = userLogin;
					userLogin.setLastLoginTime(new Date());
					getUserInfDao.updateUser(userLogin);
					return GlobalConstant.LOGIN_OK;
				}else{
					return GlobalConstant.PASSWORD_ERROR;
				}
			}
		}
		return GlobalConstant.LOGINNAME_ERROR;
	}

	@Override
	public int userRegister(TbUser user) {
		return getUserInfDao.userRegister(user);
	}

	@Override
	public TbUser checkUserUser(UserForm form) {
		TbUser user = getUserInfDao.checkUserUser(form);
		return user;
	}

	@Override
	public TbUser selectUserLogin() {
		// TODO Auto-generated method stub
		return user;
	}

}
