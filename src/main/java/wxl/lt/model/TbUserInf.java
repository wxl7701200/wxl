package wxl.lt.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the tb_user_inf database table.
 * 
 */
@Entity
@Table(name = "tb_user_inf")
@NamedQuery(name = "TbUserInf.findAll", query = "SELECT t FROM TbUserInf t")
public class TbUserInf extends IdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userAge;
	private String userCity;
	private String userProvince;

	public TbUserInf() {
	}

	@Column(name = "user_age")
	public int getUserAge() {
		return this.userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	@Column(name = "user_city", length = 255)
	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	@Column(name = "user_province", length = 255)
	public String getUserProvince() {
		return this.userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

}