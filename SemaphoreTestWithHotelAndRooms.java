import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreTestWithHotelAndRooms {
	public static void main(String[] args) {
		Hotel hotel = new Hotel();
		Customer[] customers = new Customer[10];
		IntStream.range(0, customers.length).forEach(index ->{
			customers[index] = new Customer(hotel);
			new Thread(customers[index]).start();
		});
	}

}

class Customer implements Runnable{
	private Hotel hotel;
	
	public Customer(Hotel hotel) {
		super();
		this.hotel = hotel;
	}

	public void run() {
		try {
			Integer key = hotel.getKey(); 
			System.out.println(Thread.currentThread().getName()+" succesfully booked "+ key);
			Thread.sleep(10000);
			System.out.println(Thread.currentThread().getName()+" is going to return "+ key);
			hotel.returnKey(key);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Hotel {
    private static final int MAX_AVAILABLE = 5;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
 
    public Integer getKey() throws InterruptedException {
      available.acquire();
      return getNextAvailableRoom();
    }
 
    public void returnKey(Integer x) {
      if (markAsUnused(x))
        available.release();
    }
 
    // Not a particularly efficient data structure; just for demo
 
    protected Map<Integer, Boolean> rooms = new HashMap<>() {
		private static final long serialVersionUID = 1L;

	{
    	put(1, false);
    	put(2, false);
    	put(3, false);
    	put(4, false);
    	put(5, false);
    }};
 
    protected synchronized Integer getNextAvailableRoom() {
      for (int i = 1; i <= MAX_AVAILABLE; ++i) {
        if (!rooms.get(i)) {
          rooms.put(i, true);
          return i;
        }
      }
      return null; // not reached
    }
 
    protected synchronized boolean markAsUnused(Integer room) {
		if (rooms.containsKey(room) && rooms.get(room)) {
	    	rooms.put(room, false);
	        return true;
	    } else
	        return false;
    }
}