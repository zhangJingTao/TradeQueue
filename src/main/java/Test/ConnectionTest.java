package Test;

import cache.CacheEngine;
import cache.ICacheConn;

public class ConnectionTest {
	public static void main(String[] args) {
		try {
			CacheEngine ce = new CacheEngine();
			ce.init();
			ICacheConn conn = ce.getCacheConn("fund_test");
			conn.add("test", "test"+Math.random());
			System.out.println(conn.get("test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
