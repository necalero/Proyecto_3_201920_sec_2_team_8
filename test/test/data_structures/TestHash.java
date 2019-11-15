package test.data_structures;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.sun.media.jfxmedia.events.NewFrameEvent;
import model.data_structures.HashTableLinearProbing;
import model.data_structures.Queue;
import model.data_structures.UBERTrip;
import model.logic.NoExisteException;
public class TestHash 
{
	private HashTableLinearProbing<Comparable<Integer>, Comparable<UBERTrip>> hash;
	private final static int CANTIDAD=10;

	@Before
	public void setUp1()
	{
		hash = new HashTableLinearProbing<Comparable<Integer>, Comparable<UBERTrip>>(5);
	}


	@Test
	public void insertSize()
	{
		assertEquals(0, hash.darCapacidad());
	}

	@Test
	public void insertTest1()
	{
		hash.put(1, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		hash.put(2, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		hash.put(3, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		Queue queue=new Queue();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		assertEquals(queue, hash.darKeys());
		assertEquals(3, hash.darCapacidad());
	}

	@Test
	public void insertTest2()
	{
		setUp1();
		assertEquals(10, hash.darCapacidad());
	}

	@Test
	public void delete() throws NoExisteException
	{
		hash.put(3, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		hash.put(5, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		hash.put(2, new UBERTrip("1", "345", "57458", "84356", "0845", "34657","",""));
		hash.delete(2);
		assertEquals(2, hash.darCapacidad());
	}
}
