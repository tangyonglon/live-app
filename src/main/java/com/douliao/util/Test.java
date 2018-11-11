package com.douliao.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch的使用
 * @author tyl
 *
 */
public class Test {

	private static final int count = 5;
	
	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> map=new HashMap<String,Object>();
		CountDownLatch countDownLatch = new CountDownLatch(count);
		for (int i = 0; i < count; ++i) // create and start threads
		{
			new Thread(new Worker(countDownLatch,i)).start();
		}
		
		countDownLatch.await();		//阻塞线程，不继续往下执行
		doLastSomething();
	}
	

	private static void doLastSomething() {
		System.out.println("等待所有子线程完成之后继续做我需要做的事情");
	}
	static class Worker implements Runnable {
		private CountDownLatch countDownLatch;
		private int i;
		public Worker( CountDownLatch doneSignal,int i) {
			this.countDownLatch = doneSignal;
			this.i=i;
		}
		public void run() {
			try {
				doWork();
			} catch (Exception e) {
	 
			} finally {
				countDownLatch.countDown();
			}
		}
		
		private void doWork() {
			System.out.println("创建子线程工作，开始工作.....，子线程ID为："+i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
