package wxl.lt.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A unified definition of entity's id.
 * 统一定义id的entity基类.
 * The base class have a unified definition of id's attribute name、data type、column name mapping and sequence.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * When Oracle need every Entity's id has Independent definition SEQUCENCE,don't extends this class to Implements a interface named Idable.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author Yuan you lin 袁友林 
 */
@MappedSuperclass
public abstract class IdEntity {

	protected Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
