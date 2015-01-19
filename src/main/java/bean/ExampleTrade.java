package bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**************************************************************
 **
 ** @author Zhang Jingtao
 **
 ** @date:2015��1��8�� ����1:14:18
 ** 
 ** @describe:һ��������Ľ���
 **
 **************************************************************
 */
public class ExampleTrade extends BaseBean<Long> implements Serializable{
	public static Long serialVersionUID = 123213213894792L;
	
	private Long id;//����ˮ��
	private BigDecimal amount;
	private String custno;
	private Integer type;
	
	
	
	@Override
	public Long getUniqueId() {
		return this.id;
	}

	@Override
	public void setUniqueId(Long id) {
		this.uniqueId = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
