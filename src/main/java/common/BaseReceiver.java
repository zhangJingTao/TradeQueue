package common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import util.SerializationUtil;
import cache.CacheEngine;
import cache.CacheName;
import cache.ICacheConn;
import bean.BaseBean;

@SuppressWarnings("rawtypes")
public class BaseReceiver<T extends BaseQueue<BaseBean<ID>>, ID> implements BaseReceiverI{
	private static BaseReceiver instance = new BaseReceiver();

	public static BaseReceiver getInstance() {
		return instance;
	}

	ICacheConn cacheConn = CacheEngine.getInstance().getCacheConn(CacheName.FUND_TEST);
	
	@SuppressWarnings("unchecked")
	@Override
	public BaseBean findByEntityUniqueId(Object id,String key) {
		BaseQueue<BaseBean> queue = (BaseQueue<BaseBean>) cacheConn.get(key);
		Queue<BaseBean> all = queue.getQueue();
		for (BaseBean bean : all) {
			if (bean.getUniqueId().equals(id)) {
				return bean;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean save(BaseBean t, String key) {
		BaseQueue<BaseBean> queue = null;
		try {
			queue = (BaseQueue<BaseBean>) cacheConn.get(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (queue == null) {
			queue = new BaseQueue<BaseBean>();
			queue.add(t);
			return cacheConn.add(key, queue);
		}
		queue.add(t);
		return cacheConn.replace(key, queue);
	}

	@Override
	public List listAll(String... keys) {
		try {
			List<BaseBean<ID>> all = new ArrayList<>();
			for (String s : keys) {
				T t = (T) cacheConn.get(s);
				Queue<BaseBean<ID>> queue = t.getQueue();
				BaseBean<ID> bean = null;
				while ((bean = queue.poll())!=null) {
					all.add(bean);
				}
			}
			return all;
		} catch (Exception e) {
			
		}
		return null;
	}


	@Override
	public Boolean saveAll(List list, String key) {
		BaseQueue<BaseBean> queue = null;
		try {
			queue = (BaseQueue<BaseBean>) cacheConn.get(key);
			if (queue == null) {
				queue = new BaseQueue<BaseBean>();
				for (Object object : list) {
					queue.add((BaseBean<ID>)object);
				}
				return cacheConn.add(key, queue);
			}
			for (Object object : list) {
				queue.add((BaseBean<ID>)object);
			}
			return cacheConn.replace(key, queue);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean clear(String key) {
		return cacheConn.replace(key, null);
	}

}
