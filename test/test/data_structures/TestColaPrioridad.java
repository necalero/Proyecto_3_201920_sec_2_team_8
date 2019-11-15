package test.data_structures;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.data_structures.MaxPQ;
public class TestColaPrioridad 
{
	private MaxPQ ColaPQ;
	private final static int cantidad=20;
	private int tamano;

	@Before
	public void setUp1() 
	{
		ColaPQ=new MaxPQ<>();
		tamano++;
	}

	public void setUp2()
	{
		for(int i =0; i<cantidad; i++){
			ColaPQ.insert(i);
			tamano++;
		}
	}
	@Test
	public void TestQueue()
	{
		tamano=0;
		assertTrue(ColaPQ!=null);
		assertEquals(0, ColaPQ.size());
	}

	public void TestDelMax1(){
		setUp1();
		assertTrue(" no se saco el maximo",(Integer)ColaPQ.delMax()==9);
	}
	public void TestDelMax2(){
		setUp2();
		assertTrue(" no se saco el maximo",(Integer)ColaPQ.delMax()==9);
	}

	@Test
	public void TestInsert1()
	{
		setUp1();
		ColaPQ.insert(10);
		tamano++;
		assertTrue("no se modifico el tamaño.",tamano==ColaPQ.size());
		int dato=0;
		while(tamano!=0){
			dato=(Integer)ColaPQ.delMax();
		}
		assertTrue("no se pudo agrerar correctamente.", dato==20);
	}

	@Test
	public void TestInsert2()
	{
		setUp1();
		ColaPQ.insert(11);
		tamano++;
		assertTrue("no se modifico el tamaño.",tamano==ColaPQ.size());
		int dato=0;
		while(tamano!=0){
			dato=(Integer)ColaPQ.delMax();
		}
		assertTrue("no se pudo agrerar correctamente.", dato==20);
	}

	public void TestPush1()
	{
		setUp1();
		ColaPQ.insert(17);
		ColaPQ.insert(8);
		ColaPQ.insert(13);
		ColaPQ.insert(11);
		assertTrue("no se pudo modificar el tamaño.",tamano==ColaPQ.size());
		assertFalse("no se agrego correctamente.", (Integer)ColaPQ.delMax()==17);
	}

	@Test
	public void TestPush2()
	{
		setUp2();
		ColaPQ.insert(10);
		ColaPQ.insert(15);
		ColaPQ.insert(18);
		ColaPQ.insert(4);
		assertTrue("no se pudo modificar el tamaño.",tamano==ColaPQ.size());
		assertFalse("no se agrego correctamente.", (Integer)ColaPQ.delMax()==8);
	}

	public void TestDequeue2(){
		setUp2();
		ColaPQ.insert(1);
		ColaPQ.insert(3);
		ColaPQ.insert(10);
		assertTrue("no pudo hacer Dequeue el elemento.", (Integer)ColaPQ.delMax()==10);
		assertFalse("La pila esta vacia.",ColaPQ.isEmpty());
	}
	public void TestDequeue()
	{
		setUp1();
		int j=6;
		while(tamano!=0){
			assertTrue("no salio elimino correctamente.",(Integer) ColaPQ.delMax()==j);

			j++;
		}
	}
	public void TestCrear1(){
		setUp1();
		ColaPQ.insert(1);
		ColaPQ.insert(2);
		ColaPQ.insert(3);
		ColaPQ.insert(4);
		ColaPQ.insert(5);
		ColaPQ.insert(6);
		ColaPQ.insert(7);
		ColaPQ.insert(8);

		assertTrue("No se pudieron agregar.",ColaPQ.size()==8);
	}

	public void TestCrear2(){
		setUp2();
		ColaPQ.insert(1);
		ColaPQ.insert(2);
		ColaPQ.insert(3);
		ColaPQ.insert(4);
		ColaPQ.insert(5);
		ColaPQ.insert(6);
		ColaPQ.insert(7);
		ColaPQ.insert(8);
		ColaPQ.insert(9);
		ColaPQ.insert(10);
		ColaPQ.insert(11);
		ColaPQ.insert(12);
		assertTrue("No se pudieron agregar.",ColaPQ.size()==12);
	}
}
