package wxl.lt.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_test database table.
 * 
 */
@Entity
@Table(name="tb_test")
@NamedQuery(name="TbTest.findAll", query="SELECT t FROM TbTest t")
public class TbTest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userAge;
	private String userName;

	public TbTest() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(name="user_age", length=255)
	public String getUserAge() {
		return this.userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}


	@Column(name="user_name", length=255)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}