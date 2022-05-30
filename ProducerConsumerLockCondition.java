import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ProducerConsumerLockCondition {
	public static void main(String[] args) throws InterruptedException {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		Item item = new Item(lock, condition);
		Thread producer = new Thread(() -> {
			IntStream.range(0, 10)
			.forEach(i ->{
				try {
					item.put(i+1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}, "Producer");
		
		Thread consumer = new Thread(() -> {
			IntStream.range(0, 10)
			.forEach(i ->{
				try {
					item.take();
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}, "Consumer");
		
		producer.start();
		consumer.start();
		producer.join();
		consumer.join();
	}

}

class Item{
	private int n;
	private boolean available;
	Lock lock;
	Condition condition; 
	
	public Item(Lock lock, Condition condition) {
		super();
		this.lock = lock;
		this.condition = condition;
	}

	public void put(int n) throws InterruptedException {
		lock.lock();
		if(available) {
			condition.await();
		}
		this.n = n;
		System.out.println(Thread.currentThread().getName()+" put "+this.n);
		available = true;
		condition.signal();
		lock.unlock();
	}
	
	public void take() throws InterruptedException {
		lock.lock();
		if(!available) {
			condition.await();
		}
		System.out.println(Thread.currentThread().getName()+" take "+n);
		available = false;
		condition.signal();		
		lock.unlock();
	}
}
