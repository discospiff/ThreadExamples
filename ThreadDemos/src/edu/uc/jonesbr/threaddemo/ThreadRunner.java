package edu.uc.jonesbr.threaddemo;

public class ThreadRunner {

	Object lock1 = new Object();
	Object lock2 = new Object();
	Object lock3 = new Object();
	
	public static void main(String[] args) {
		ThreadRunner tr = new ThreadRunner();
		tr.runThreads();
		
	}

	public void runThreads() {
//		System.out.println("Starting Main");
//		WaitThread wt = new WaitThread();
//		Thread twt = new Thread(wt);
//		twt.start();
//		
//
//		SleepyThread st = new SleepyThread();
//		Thread tst = new Thread(st);
//		tst.start();
//		
//		LowPriority lp = new LowPriority();
//		HighPriority hp = new HighPriority();
//		Thread tlp = new Thread(lp);
//		tlp.setPriority(Thread.MIN_PRIORITY);
//		Thread thp = new Thread(hp);
//		thp.setPriority(Thread.MAX_PRIORITY);
//		tlp.start();
//		thp.start();
		
		
		Concurrent1 c1 = new Concurrent1();
		Concurrent2 c2 = new Concurrent2();
		Thread tc1 = new Thread(c1);
		Thread tc2 = new Thread(c2);
		tc1.start();
		tc2.start();
		
//		NotifyThread nt = new NotifyThread();
//		Thread tnt = new Thread(nt);
//		tnt.start();
		
	}
	
	public void forceDeadlock() {
		synchronized (lock2) {
			synchronized(lock1) {
				System.out.println("Do work");
			}
		}
		
	}
	
	class LowPriority implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 100; i++) {
				System.out.println("1: " + i );
			}
		}
		
	}


	class HighPriority implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < 100; i++) {
				System.out.println("10: " + i );
				Thread.yield();
			}
		}
		
	}
	
	class Concurrent1 implements Runnable {

		@Override
		public void run() {
			synchronized(lock1) {
				try {
					Thread.sleep(1000);
					forceDeadlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	class Concurrent2 implements Runnable {

		@Override
		public void run() {
			synchronized(lock2) {
				try {
					Thread.sleep(1000);
					forceDeadlock();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	class WaitThread implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 20; i++) {
				System.out.println("I'm working.");
				if (i == 10) {
					System.out.println("Now I'm waiting");
					try {
						synchronized(lock3) {
							lock3.wait();
						}	
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	class NotifyThread implements Runnable {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(lock3) {
				lock3.notify();
			}
		}
	}

	class SleepyThread implements Runnable {
		public void run() {
			for (int i = 0; i < 20; i++) {
				System.out.println("I'm tired.");
				if (i == 10) {
					System.out.println("Now I'm sleeping");
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
