package receive;

import java.util.List;

import cache.KeyManager;
import bean.BaseBean;
import bean.ExampleTrade;
import common.BaseReceiver;

public class ExampleReceive{
	BaseReceiver baseReceiver = BaseReceiver.getInstance();
	
	public Boolean save(ExampleTrade t,String key) {
		// TODO Auto-generated method stub
		return baseReceiver.save(t,key);
	}
	
	public Boolean clear(String key) {
		// TODO Auto-generated method stub
		return baseReceiver.clear(key);
	}
	
	public Boolean saveAll(List<ExampleTrade> ts,String key) {
		// TODO Auto-generated method stub
		return baseReceiver.saveAll(ts, key);
	}

	public ExampleTrade findByEntityUniqueId(Object id) {
		Object o = baseReceiver.findByEntityUniqueId(id, KeyManager.ExamplePrepare.getKey());
		if (o == null) {
			o = baseReceiver.findByEntityUniqueId(id, KeyManager.ExampleRunning.getKey());
		}
		if (o == null) {
			o = baseReceiver.findByEntityUniqueId(id, KeyManager.ExampleFailed.getKey());
		}
		if (o == null) {
			o = baseReceiver.findByEntityUniqueId(id, KeyManager.ExampleCommited.getKey());
		}
		return (ExampleTrade) o;
	}
	
	public List<ExampleTrade> listAllTrade(String... keys){
		return baseReceiver.listAll(keys);
	}
	
}
