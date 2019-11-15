package test.data_structures;
import org.junit.Before;
import org.junit.Test;
import model.data_structures.ArbolesRYN;
import static org.junit.Assert.*;
public class TestArbol 
{
	private ArbolesRYN<Integer,String> vocales;

	/**
	 * escenario de prueba con las vocales.
	 */
	public void setUp1() {
		vocales.put(1,"a");
		vocales.put(2,"e");
		vocales.put(3,"i");
		vocales.put(4,"o");
		vocales.put(5,"u");
	}
	
	public void testPut()
	{
		setUp1();
		assertFalse("Esta perfecto",vocales.get(2).equals("i"));
		assertEquals("Se genero un error",vocales.get(2).equals("e"));
	}
	
	public void testDelete()
	{
		setUp1();
		vocales.delete(3);
		assertFalse("Se encontro un error",vocales.get(3)=="i");
		
	}
	public void testDeleteMin()
	{
		setUp1();
		vocales.deleteMin();
		assertFalse("Se genero un error",vocales.get(1).equals("a"));
		
	}
	public void testDeleteMax()
	{
		setUp1();
		vocales.deleteMax();
		assertFalse("Se genero un error",vocales.get(5).equals("u"));
		
	}
	
}
