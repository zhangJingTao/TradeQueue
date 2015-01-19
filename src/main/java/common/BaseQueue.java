package common;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import bean.BaseBean;

public class BaseQueue<T extends BaseBean> implements Serializable{
	private static final long serialVersionUID = 12214213245333L;
	Queue<T> queue = new LinkedList<T>();
	public Queue<T> getQueue(){
		return queue;
	}
	public <A extends T>Boolean add(A e){
		return queue.offer(e);
	}
	public T next(){
	    return queue.poll();
	}
}
