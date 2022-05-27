public class ProducerConsumerClassicStyle {
	public static void main(String[] args) throws InterruptedException {
		Item item = new Item();
		Thread producer = new Thread(new Producer(item));
		Thread consumer = new Thread(new Consumer(item));
		producer.start();
		consumer.start();	
		producer.join();
		consumer.join();
	}
}

class Producer implements Runnable{
	private Item item;
	public Producer(Item item) {
		this.item = item;
	}
	
	@Override
	public void run() {
		for(int i=1;i<=10;i++) {
			try {
				item.put(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		synchronized (item) {//Last iteration of item.get() waits
			item.notify();
		}
	}
}

class Consumer implements Runnable{
	private Item item;
	public Consumer(Item item) {
		this.item = item;
	}
	
	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			try {
				item.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Item{
	private int n;
	private volatile boolean available;
	
	public synchronized void put(int n) throws InterruptedException {
		if(available) {
			wait();
		}else {
			this.n = n;
			available = true;
			System.out.println(Thread.currentThread()+" put "+n);
			notify();
			wait();
		}
	}
	
	public synchronized void get() throws InterruptedException {
		if(available) {
			System.out.println(Thread.currentThread()+" got "+ n);
			available = false;
			notify();
			wait();
		}else {
			wait();
		}
	}
}
