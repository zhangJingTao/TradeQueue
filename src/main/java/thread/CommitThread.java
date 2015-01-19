package thread;
import java.util.List;

import cache.KeyManager;
import receive.ExampleReceive;
import bean.ExampleTrade;
import Test.TradeTest;

public class CommitThread implements Runnable{

	@Override
	public void run() {
		synchronized (TradeTest.running) {
			ExampleReceive receiver = new ExampleReceive();
			List<ExampleTrade> running = receiver.listAllTrade(KeyManager.ExampleRunning.getKey());
			if (running == null) {
				return ;
			}
			for (ExampleTrade trade : running) {
				double random = Math.random();
//				receiver.saveAll(running,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
				//模拟随机成功90%
				receiver.save(trade,random>0.1? KeyManager.ExampleCommited.getKey():KeyManager.ExampleFailed.getKey());
				System.out.println("commit...id:"+trade.getUniqueId());
			}
			receiver.clear(KeyManager.ExampleRunning.getKey());
			System.out.println(running.size()+"笔交易被提交....");
		}
	}
	
}
