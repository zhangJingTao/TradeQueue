package bean;

public abstract class BaseBean<ID> {
	ID uniqueId;
	
	public abstract ID getUniqueId();
	
	public abstract void setUniqueId(ID id);

	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BaseBean)) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		BaseBean o = (BaseBean)obj;
		return this.uniqueId.equals(o.getUniqueId());
	}
}
