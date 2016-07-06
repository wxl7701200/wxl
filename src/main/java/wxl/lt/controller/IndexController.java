/**
 * 
 */
package wxl.lt.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import wxl.lt.form.UserForm;
import wxl.lt.framework.constant.GlobalConstant;
import wxl.lt.model.TbUser;
import wxl.lt.pageModel.PageMessage;
import wxl.lt.service.GetUerInfService;
import wxl.lt.utils.IpUtils;
import wxl.lt.utils.MD5Util;

/**
 * @author wxlHonest
 *
 */
@Controller("IndexController")
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private GetUerInfService gtUerInfService;

	/**
	 * @Description: 打开登录页面
	 * @param
	 * @return String url
	 */
	@RequestMapping(value = "/login")
	public String indexPage(HttpSession session) {
		return "login";
	}

	/**
	 * 用户登陆
	 * @param form
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage userLogin(@Validated UserForm form, HttpSession session) {
		PageMessage pm = new PageMessage();
		form.setPassword(MD5Util.md5(form.getPassword()));
		int flag = gtUerInfService.getUserLoginInf(form);
		if(flag == GlobalConstant.LOGIN_OK){
			session.setAttribute("systemUser", gtUerInfService.selectUserLogin());
			//pm.setSuccess(false);
		}else{
			pm = getPageMessage(pm, flag);
		}
		return pm;
	}
	
	/**
	 * @Title: getPageMessage test
	 * @Description: 封装私有方法
	 * @param:    
	 * @return: PageMessage
	 */
	private PageMessage getPageMessage(PageMessage pm,int flag){
		if(flag==GlobalConstant.PASSWORD_ERROR){
			pm.setSuccess(false);
			pm.setErrorMsg("密码错误");
		}
		else if(flag==GlobalConstant.LOGINNAME_ERROR){
			pm.setSuccess(false);
			pm.setErrorMsg("用户名不存在");
		}
		return pm;
	}

	/**
	 * 注册
	 * 
	 * @param form
	 * @param pm
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage userRegister(@Validated UserForm form, PageMessage pm, HttpSession session,
			HttpServletRequest request) {
		TbUser userCheck = checkUser(form);
		if (userCheck != null) {
			if (userCheck.getUserName().equals(form.getUserName())) {
				pm.setSuccess(false);
				pm.setErrorMsg("该用户名已注册");// ("该用户名已注册");
			} else if (userCheck.getUserEmail().equals(form.getUserEmail())) {
				pm.setSuccess(false);
				pm.setErrorMsg("该邮箱已注册");// ("该邮箱已注册");
			} else if (userCheck.getUserMobileNo().equals(form.getUserMobileNo())) {
				pm.setSuccess(false);
				pm.setErrorMsg("该手机号已注册");// ("该手机号已注册");
			}
		} else {
			TbUser user = new TbUser();
			BeanUtils.copyProperties(form, user);
			// 注册时间
			user.setRegisterTime(new Date());
			// 用户IP
			user.setUserIp(IpUtils.getIpAddress(request));
			user.setPassword(MD5Util.md5(form.getPassword()));
			int ret = gtUerInfService.userRegister(user);
			if (ret == GlobalConstant.SUCCESS) {
				pm.setSuccess(true);
				pm.setSuccessMsg("申请成功");
			} else if (ret == GlobalConstant.FAILED) {
				pm.setSuccess(false);
				pm.setErrorMsg("申请失败");
			}
		}
		return pm;
	}

	/**
	 * 查询用户名,手机号,邮箱---判断是否可注册
	 * 
	 * @param form
	 * @return
	 */
	public TbUser checkUser(UserForm form) {
		TbUser user = gtUerInfService.checkUserUser(form);
		return user;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test(){
		System.out.println(11111);
	}

}
