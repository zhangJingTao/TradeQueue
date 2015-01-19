package cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import cache.ICacheConn.CacheEngineType;
import cache.IniFileBean.IniSection;
import cache.MemcachedConn.MemcachedConfigBean;

/**
 * cache瀵洘鎼�.
 * 
 * @author harry.zu
 * 
 */

public class CacheEngine {
	private static CacheEngine instance = new CacheEngine();

	String filename = "conf/cache.properties";

	protected HashMap<String, ICacheConn> cacheEngingMap = new HashMap<String, ICacheConn>();

	public CacheEngine() {

	}

	/**
	 * 閼惧嘲褰嘽ache瀵洘鎼哥�圭偘绶�.
	 * 
	 * @return cache瀵洘鎼哥�圭偘绶�
	 */
	public static CacheEngine getInstance() { 
		return instance; 
	}

	/**
	 * 閼惧嘲褰囬幐鍥х暰cache鏉╃偞甯�.
	 * 
	 * @param conName
	 *            鏉╃偞甯撮崥宥囆�
	 * 
	 * @return cache鏉╃偞甯�
	 */
	public ICacheConn getCacheConn(final String conName) {
		return this.cacheEngingMap.get(conName);
	}

	public HashMap getMap() {
		return cacheEngingMap;
	}

	/**
	 * 閸掓繂顫愰崠鏈縜che瀵洘鎼�.
	 * 
	 * @param input
	 *            闁板秶鐤嗛弬鍥︽鏉堟挸鍙嗛敓锟�?
	 */
	public void init() {
		try {
			System.out
					.println("------------------- init method -------------------");
			System.out.println(filename);

//			String osName = System.getProperty("os.name");
//			ConfigHelper cfg = new ConfigHelper(new CacheEngine());
//			String file = cfg.getCfgPath();
//
//			file += "WEB-INF/classes/" + filename;
//
//			// if (osName.indexOf("Linux") > -1)
//			// file = "/" + file;
//
//			System.out.println(file);
//
//			InputStream input = new FileInputStream(new File(file));

			InputStream input = CacheEngine.class.getClassLoader().getResourceAsStream(filename);
			System.out.println(new File(filename).getAbsolutePath());
			IniFileBean iniBean = new IniFileBean(input);

			for (String section : iniBean.getSections()) {
				if (section != null) {
					ICacheConn cacheEngine = null;

					IniSection sectionBean = iniBean.getSection(section);
					// 鏉烆剚宕瞫ectionbean閸掔櫛acheConfigBean
					CacheEngineType engineType = CacheEngineType
							.valueOf(sectionBean
									.getValue(ICacheConn.CACHE_ENGINE));
					// 閸掓繂顫愰崠鏈縜che engine
					switch (engineType) {
					case MEMCACHED:
						MemcachedConfigBean config = getMemcachedConfigBean(sectionBean);
						cacheEngine = new MemcachedConn(config);
						break;
					case DATABASE:
						break;
					case COHERENCE:
						break;
					default:
						break;
					}

					if (cacheEngine != null) {
						cacheEngingMap.put(section, cacheEngine);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 鐏忓敄ection闁板秶鐤哹ean鏉烆剚宕查幋鎭梕mcached閻ㄥ嫰鍘ょ純鐢an.
	 * 
	 * @param sectionBean
	 *            闁板秶鐤嗛敓锟�?
	 * 
	 * @return memcached閻ㄥ嫰鍘ょ純鐢an
	 */
	private MemcachedConfigBean getMemcachedConfigBean(
			final IniSection sectionBean) {
		MemcachedConfigBean config = new MemcachedConfigBean();
		config.setPoolName(sectionBean.getSectionName());
		config.setServers(sectionBean.getValue(MemcachedConfigBean.SERVERS)
				.split(MemcachedConfigBean.CONFIGSPLIT));

		String[] weights = sectionBean.getValue(MemcachedConfigBean.WEIGHTS)
				.split(MemcachedConfigBean.CONFIGSPLIT);
		if (weights != null && weights.length > 0) {
			Integer[] iWeights = new Integer[weights.length];

			for (int i = 0; i < weights.length; i++) {
				iWeights[i] = Integer.valueOf(weights[i]);
			}

			config.setWeights(iWeights);
		}

		config.setInitConn(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.INICONN)));
		config.setMinConn(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.MINCONN)));
		config.setMaxConn(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.MAXCONN)));
		config.setMaxIdle(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.MAXIDLE)));
		config.setMaintSleep(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.MAINTSLEEP)));
		config.setNagle(Boolean.parseBoolean(sectionBean
				.getValue(MemcachedConfigBean.NAGLE)));
		config.setSocketTO(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.SOCKETTO)));
		config.setSocketConnectTO(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.SOCKETCONNECTTO)));
		config.setCompress(Boolean.parseBoolean(sectionBean
				.getValue(MemcachedConfigBean.COMPRESS)));
		config.setCompressThreshold(Integer.parseInt(sectionBean
				.getValue(MemcachedConfigBean.COMPRESSTHRESHOLD)));
		return config;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
