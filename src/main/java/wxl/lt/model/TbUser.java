package wxl.lt.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_user database table.
 * 
 */
@Entity
@Table(name="tb_user")
@NamedQuery(name="TbUser.findAll", query="SELECT t FROM TbUser t")
public class TbUser extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String delFlag;
	private Date lastLoginTime;
	private String password;
	private Date registerTime;
	private String userEmail;
	private String userIp;
	private String userMobileNo;
	private String userName;

	public TbUser() {
	}

	@Column(name="del_flag", length=1)
	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name="last_login_time")
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	@Column(length=50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(name="register_time")
	public Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}


	@Column(name="user_email", length=255)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	@Column(name="user_ip", length=255)
	public String getUserIp() {
		return this.userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}


	@Column(name="user_mobile_no", length=11)
	public String getUserMobileNo() {
		return this.userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}


	@Column(name="user_name", length=40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}