package cache;

import java.util.Arrays;
import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * memcache的缓存连�?.
 * 
 * @author harry.zu
 * 
 */
public final class MemcachedConn implements ICacheConn {

	/**
	 * Cache相关的配置信�?.
	 * 
	 * @author harry.zu
	 * 
	 */
	public static final class MemcachedConfigBean {
		public static final String CONFIGSPLIT = ";";

		public static final String SERVERS = "servers";
		public static final String WEIGHTS = "weights";
		public static final String INICONN = "iniconn";
		public static final String MINCONN = "minconn";
		public static final String MAXCONN = "maxconn";
		public static final String MAXIDLE = "maxidle";
		public static final String MAINTSLEEP = "maintsleep";
		public static final String NAGLE = "nagle";
		public static final String SOCKETTO = "socketto";
		public static final String SOCKETCONNECTTO = "socketconnectto";
		public static final String COMPRESS = "compress";
		public static final String COMPRESSTHRESHOLD = "compressthreshold";

		private static final int CON_INITCONN = 5;
		private static final int CON_MINCONN = 5;
		private static final int CON_MAXCONN = 250;
		private static final int CON_MAXIDLE = 1000 * 60 * 60 * 6;
		private static final int CON_MAINTSLEEP = 30;
		private static final int CON_SOCKETTO = 3000;
		private static final int CON_SOCKETCONNECTTO = 0;
		private static final int CON_COMPRESSTHRESHOLD = 64 * 1024;

		private String poolName = null;
		private String[] servers = null;
		private Integer[] weights = null;
		private int initConn = CON_INITCONN;
		private int minConn = CON_MINCONN;
		private int maxConn = CON_MAXCONN;
		private int maxIdle = CON_MAXIDLE;
		private int maintSleep = CON_MAINTSLEEP;
		private boolean nagle = false;
		private int socketTO = CON_SOCKETTO;
		private int socketConnectTO = CON_SOCKETCONNECTTO;
		private boolean compress = true;
		private int compressThreshold = CON_COMPRESSTHRESHOLD;

		/**
		 * 获取连接的名�?.
		 * 
		 * @return 连接的名�?
		 */
		public String getPoolName() {
			return this.poolName;
		}

		/**
		 * 设置连接的名�?.
		 * 
		 * @param poolName
		 *            连接的名�?
		 */
		public void setPoolName(final String poolName) {
			this.poolName = poolName;
		}

		/**
		 * 获取连接的服务器地址.
		 * 
		 * @return 服务器地�?
		 */
		public String[] getServers() {
			return servers;
		}

		/**
		 * 设置连接的服务器地址.
		 * 
		 * @param servers
		 *            服务器地�?
		 */
		public void setServers(final String[] servers) {
			this.servers = servers;
		}

		/**
		 * 获取各个服务器之间的权重.
		 * 
		 * @return 服务器的权重
		 */
		public Integer[] getWeights() {
			return weights;
		}

		/**
		 * 设置各个服务器之间的权重.
		 * 
		 * @param weights
		 *            服务器的权重
		 */
		public void setWeights(final Integer[] weights) {
			this.weights = weights;
		}

		/**
		 * 获取初始连接�?.
		 * 
		 * @return 初始连接�?
		 */
		public int getInitConn() {
			return initConn;
		}

		/**
		 * 设置初始连接�?.
		 * 
		 * @param initConn
		 *            初始连接�?
		 */
		public void setInitConn(final int initConn) {
			this.initConn = initConn;
		}

		/**
		 * 获取�?小连接数.
		 * 
		 * @return �?小连接数
		 */
		public int getMinConn() {
			return minConn;
		}

		/**
		 * 设置�?小连接数.
		 * 
		 * @param minConn
		 *            �?小连接数
		 */
		public void setMinConn(final int minConn) {
			this.minConn = minConn;
		}

		/**
		 * 获取�?大连接数.
		 * 
		 * @return �?大连接数
		 */
		public int getMaxConn() {
			return maxConn;
		}

		/**
		 * 设置�?大连接数.
		 * 
		 * @param maxConn
		 *            �?大连接数
		 */
		public void setMaxConn(final int maxConn) {
			this.maxConn = maxConn;
		}

		/**
		 * 获取�?大空闲数.
		 * 
		 * @return �?大空闲数
		 */
		public int getMaxIdle() {
			return maxIdle;
		}

		/**
		 * 设置�?大空闲数.
		 * 
		 * @param maxIdle
		 *            �?大空闲数
		 */
		public void setMaxIdle(final int maxIdle) {
			this.maxIdle = maxIdle;
		}

		/**
		 * 获取维护间隔时间.
		 * 
		 * @return 维护间隔时间
		 */
		public int getMaintSleep() {
			return maintSleep;
		}

		/**
		 * 设置维护间隔时间.
		 * 
		 * @param maintSleep
		 *            维护间隔时间
		 */
		public void setMaintSleep(final int maintSleep) {
			this.maintSleep = maintSleep;
		}

		/**
		 * 是否nagle.
		 * 
		 * @return nagle
		 */
		public boolean isNagle() {
			return nagle;
		}

		/**
		 * 设置nagle.
		 * 
		 * @param nagle
		 *            nagle
		 */
		public void setNagle(final boolean nagle) {
			this.nagle = nagle;
		}

		/**
		 * 获取socketTo.
		 * 
		 * @return socketTo
		 */
		public int getSocketTO() {
			return socketTO;
		}

		/**
		 * 设置socketTo.
		 * 
		 * @param socketTO
		 *            socketTo
		 */
		public void setSocketTO(final int socketTO) {
			this.socketTO = socketTO;
		}

		/**
		 * 获取socketConnectTo.
		 * 
		 * @return socketConnectTo
		 */
		public int getSocketConnectTO() {
			return socketConnectTO;
		}

		/**
		 * 设置socketConnectTo.
		 * 
		 * @param socketConnectTO
		 *            socketConnectTo
		 */
		public void setSocketConnectTO(final int socketConnectTO) {
			this.socketConnectTO = socketConnectTO;
		}

		/**
		 * 获取是否压缩.
		 * 
		 * @return 是否压缩
		 */
		public boolean isCompress() {
			return compress;
		}

		/**
		 * 设置是否压缩.
		 * 
		 * @param compress
		 *            是否压缩
		 */
		public void setCompress(final boolean compress) {
			this.compress = compress;
		}

		/**
		 * 获取压缩门槛.
		 * 
		 * @return 压缩门槛
		 */
		public int getCompressThreshold() {
			return compressThreshold;
		}

		/**
		 * 设置压缩门槛.
		 * 
		 * @param compressThreshold
		 *            压缩门槛
		 */
		public void setCompressThreshold(final int compressThreshold) {
			this.compressThreshold = compressThreshold;
		}
	}

	private MemCachedClient mcc = null;
	private String poolname = null;

	/**
	 * 私有构�?�方�?.
	 * 
	 * @param config
	 *            memcachedConfigBean
	 */
	public MemcachedConn(final MemcachedConfigBean config) {

		StringBuffer sb = new StringBuffer();
		sb.append("getCompressThreshold:")
				.append(config.getCompressThreshold());
		sb.append(" getInitConn:").append(config.getInitConn());
		sb.append(" getMaintSleep:").append(config.getMaintSleep());
		sb.append(" getMaxConn:").append(config.getMaxConn());
		sb.append(" getMaxIdle:").append(config.getMaxIdle());
		sb.append(" getMinConn:").append(config.getMinConn());
		sb.append(" getPoolName:").append(config.getPoolName());
		sb.append(" getSocketConnectTO:").append(config.getSocketConnectTO());
		sb.append(" getServers:").append(Arrays.deepToString(config.getServers()));
		sb.append(" getWeights:").append(Arrays.deepToString(config.getWeights()));
		sb.append(" isCompress: ").append(config.isCompress());
		sb.append(" isNagle: ").append(config.isNagle());

		System.out.println(sb.toString());

		// 获取socke连接池的实例对象
		SockIOPool pool = SockIOPool.getInstance(config.getPoolName());

		// 设置服务器信�?
		pool.setServers(config.getServers());
		pool.setWeights(config.getWeights());

		// 设置初始连接数�?�最小和�?大连接数以及�?大处理时�?
		pool.setInitConn(config.getInitConn());
		pool.setMinConn(config.getMinConn());
		pool.setMaxConn(config.getMaxConn());
		pool.setMaxIdle(config.getMaxIdle());

		// 设置主线程的睡眠时间
		pool.setMaintSleep(config.getMaintSleep());

		// 设置TCP的参数，连接超时�?
		pool.setNagle(config.isNagle());
		pool.setSocketTO(config.getSocketTO());
		pool.setSocketConnectTO(config.getSocketConnectTO());

		// 初始化连接池
		pool.initialize();

		// 初始化连接客户端
		this.poolname = config.getPoolName();
		this.mcc = new MemCachedClient(this.poolname);

		// 压缩设置，超过指定大小（单位为K）的数据都会被压�?		
		//mcc.setCompressEnable(config.isCompress());
		//mcc.setCompressThreshold(config.getCompressThreshold());
	}

	/**
	 * 添加�?个指定的值到缓存�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	public boolean add(final String key, final Object value) {
		return mcc.add(this.poolname + key, value);
	}

	/**
	 * 添加�?个指定的值到缓存中，并设置过期时�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	public boolean add(final String key, final Object value, final Date expiry) {
		return mcc.add(this.poolname + key, value, expiry);
	}

	/**
	 * 替换缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	public boolean replace(final String key, final Object value) {
		return mcc.replace(this.poolname + key, value);
	}

	/**
	 * 替换缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	public boolean replace(final String key, final Object value,
			final Date expiry) {
		return mcc.replace(this.poolname + key, value, expiry);
	}

	/**
	 * 删除缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * 
	 * @return 是否成功
	 */
	public boolean delete(final String key) {
		return mcc.delete(this.poolname + key);
	}

	/**
	 * 在指定时间内删除缓存中的某个�?.
	 * 
	 * @param key
	 *            关键�?
	 * @param date
	 *            删除时间
	 * 
	 * @return 是否成功
	 */
	public boolean delete(final String key, final Date date) {
		return mcc.delete(this.poolname + key, date);
	}

	/**
	 * 根据指定的关键字获取对象.
	 * 
	 * @param key
	 *            关键�?
	 * 
	 * @return �?
	 */
	public Object get(final String key) {
		return mcc.get(this.poolname + key);
	}

	/**
	 * 添加�?个指定的值到缓存中，如果已经存在，则覆写.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * 
	 * @return 是否成功
	 */
	public boolean set(final String key, final Object value) {
		return mcc.set(this.poolname + key, value);
	}

	/**
	 * 添加�?个指定的值到缓存中，如果已经存在，则覆写，并设置过期时间.
	 * 
	 * @param key
	 *            关键�?
	 * @param value
	 *            �?
	 * @param expiry
	 *            过期时间
	 * 
	 * @return 是否成功
	 */
	public boolean set(final String key, final Object value, final Date expiry) {
		return mcc.set(this.poolname + key, value, expiry);
	}
}
