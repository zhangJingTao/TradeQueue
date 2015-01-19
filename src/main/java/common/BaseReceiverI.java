package common;

import java.util.List;

import bean.BaseBean;

public interface BaseReceiverI<T extends BaseBean<ID>,ID> {
	public Boolean save(T t,String key);
	@SuppressWarnings("rawtypes")
	public BaseBean findByEntityUniqueId(ID id,String key);
	
	List listAll(String[] keys);
	Boolean saveAll(List<BaseBean<ID>> list, String key);
	
	public Boolean clear(String key);
}
