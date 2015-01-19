package thread;

import java.util.List;

import cache.KeyManager;
import bean.ExampleTrade;
import receive.ExampleReceive;
import Test.TradeTest;

public class MoveThread implements Runnable{

	@Override
	public void run() {
		synchronized (TradeTest.prepare) {
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

}
