package queue;

import java.io.Serializable;

import common.BaseQueue;

import bean.ExampleTrade;

/**************************************************************
 **
 ** @author Zhang Jingtao
 **
 ** @date:2015年1月8日 下午1:11:46
 ** 
 ** @describe:示例队列
 **
 **************************************************************
 */
public class ExampleQueue extends BaseQueue<ExampleTrade> implements Serializable{
	private static final long serialVersionUID = 231354325321L;
}
