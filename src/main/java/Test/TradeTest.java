package Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cache.CacheEngine;
import cache.KeyManager;
import receive.ExampleReceive;
import bean.ExampleTrade;

public class TradeTest {
	public static Queue<Object> prepare = new LinkedList<Object>();
	public static Queue<Object> running = new LinkedList<Object>();
	static CacheEngine ce = CacheEngine.getInstance();

	public static void main(String[] args) {
		ce.init();
		ExampleReceive receiver = new ExampleReceive();
		//3s move一次
		Timer runningTimer = new Timer(true);
		runningTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("move timer 执行");
				synchronized (TradeTest.prepare) {
					System.out.println("prepare资源释放");
					synchronized (TradeTest.running) {
						ExampleReceive receiver = new ExampleReceive();
						List<ExampleTrade> trades = receiver.listAllTrade(KeyManager.ExamplePrepare.getKey());
						if (trades == null) {
							return ;
						}
						receiver.saveAll(trades,KeyManager.ExampleRunning.getKey());
						receiver.clear(KeyManager.ExamplePrepare.getKey());
						System.out.println(trades.size()+"笔交易被移到running队列中...");
					}
				}
			}
		}, 3000,3000);
		//10s 提交一次
		Timer commitTimer = new Timer(true);
		commitTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("running timer 执行");
				synchronized (TradeTest.running) {
					ExampleReceive receiver = new ExampleReceive();
					List<ExampleTrade> running = receiver.listAllTrade(KeyManager.ExampleRunning.getKey());
					if (running == null) {
						return ;
					}
					for (ExampleTrade trade : running) {
						double random = Math.random();
						//模拟随机成功90%
//						receiver.save(trade,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
//						System.out.println("commit...id:"+trade.getUniqueId());
						receiver.saveAll(running,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
					}
					receiver.clear(KeyManager.ExampleRunning.getKey());
					System.out.println(running.size()+"笔交易被提交....");
				}
			}
		}, 10000,10000);
		//20s后将队列移到
//				Timer rePrepareTimer = new Timer(true);
//				commitTimer.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						synchronized (TradeTest.running) {
//							ExampleReceive receiver = new ExampleReceive();
//							List<ExampleTrade> running = receiver.listAllTrade(KeyManager.ExampleRunning.getKey());
//							if (running == null) {
//								return ;
//							}
//							for (ExampleTrade trade : running) {
//								double random = Math.random();
//								//模拟随机成功90%
////								receiver.save(trade,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
////								System.out.println("commit...id:"+trade.getUniqueId());
//								receiver.saveAll(running,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
//							}
//							receiver.clear(KeyManager.ExampleRunning.getKey());
//							System.out.println(running.size()+"笔交易被提交....");
//						}
//					}
//				}, 10000);
		
		
		
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextInt()) {
			synchronized (prepare) {
				try {
					Integer count = scanner.nextInt();
					for (int i = 0; i < count; i++) {
						ExampleTrade trade = new ExampleTrade();
						trade.setAmount(new BigDecimal(Math.random()*10021));
						trade.setCustno("100"+i);
						trade.setId((long) i);
						trade.setType(1);
						trade.setUniqueId(trade.getId());
						Boolean result = receiver.save(trade,KeyManager.ExamplePrepare.getKey());
						System.out.println("save result:"+result);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}	
			}
		}
		
		ExampleTrade trade = receiver.findByEntityUniqueId(1L);
		System.out.println(trade.getId());
	}
}
