import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerBlockingQueue {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1); 
		Thread producer = new Thread(() -> {
			for(int i=1;i<=10;i++) {
				try {
					queue.put(i);
					System.out.println(Thread.currentThread()+" put "+i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread consumer = new Thread(() ->{
			for(int i=0;i<10;i++) {
				try {
					Thread.sleep(1000);
					int n = queue.take();
					System.out.println(Thread.currentThread()+" got "+ n);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		producer.start();
		consumer.start();	
		producer.join();
		consumer.join();
	}
}

