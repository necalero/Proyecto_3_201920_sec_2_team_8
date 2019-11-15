package test.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import model.data_structures.Grafos.GrafoNoDirigido;;

/**
 * Clase de prueba para el grafo no dirigido implementado con listas de adyacencia.
 * Se usan Integers como llaves y sus versiones en String como los valores.
 */
public class TestGrafoNoDirigido
{
	/**
	 * Referencia al grafo.
	 */
	private GrafoNoDirigido<Integer, String> graph;

	/**
	 * Inicialización del grafo. Se ejecuta automáticamente antes de cada prueba.
	 */
	@Before
	public void setUp1()
	{
		graph = new GrafoNoDirigido<Integer, String>(10);
	}

	/**
	 * Escenario 2.
	 */
	public void setUp2()
	{
		setUp1();
		graph.addEdge(3, 2, 300);
		graph.addEdge(6, 5, 555);
		graph.addEdge(2, 8, 200);
		graph.addEdge(5, 4, 300);

	}


	/**
	 * Escenario 3
	 */
	public void setUp3()
	{
		setUp1();
		graph.addVertex(1, "Calle 100");
		graph.addVertex(2, "Carrera 9");
		graph.addVertex(3, "Calle 21");
		graph.addVertex(4, "Carrera9");
		graph.addVertex(5, "Bogota");
		graph.addEdge(1, 4, 100);
		graph.addEdge(2, 3, 100);
		graph.addEdge(3, 2, 100);
		graph.addEdge(4, 1, 100);
	}
	@Test
	/**
	 * Prueba el método addEdge().
	 */
	public void testAddEdge()
	{
		setUp1();
		graph.addEdge(1, 2, 200);
		assertEquals(1,graph.E());

	}

	@Test
	/**
	 * Prueba el método getInfoVertex().
	 */
	public void testGetInfoVertex()
	{
		setUp2();
		assertEquals("Bogota",graph.getInfoVertex(5));

	}

	@Test
	/**
	 * Prueba el método setInfoVertex().
	 */
	public void testSetInfoVertex()
	{
		setUp2();
		graph.setInfoVertex(5, "Bogota");
		assertEquals("Bogota",graph.getInfoVertex(5));

	}


	@Test
	/**
	 * Prueba el método getCostArc().
	 */
	public void testGetCostArc()
	{
		setUp2();
		double costo= graph.getCostArc(2, 5);
		assertEquals(555,costo,1);

	}

	@Test
	/**
	 * Prueba el método setCostArc().
	 */
	public void testSetCostArc()
	{
		setUp2();
		graph.setCostArc(5, 4, 300);
		double cost= graph.getCostArc(5, 4);
		assertEquals(300,cost,1);

	}

	@Test
	/**
	 * Prueba el método addVertex().
	 */
	public void testAddVertex()
	{
		setUp1();
		graph.addVertex(2, "Calle 21");
		assertEquals("Calle 21",graph.getInfoVertex(2));

	}

	@Test
	public void testAdj()
	{
		setUp3();
		Iterable<Integer> lista = graph.adj(1);
		Iterator<Integer> it = lista.iterator();
		int vertice=it.next();
		assertEquals(1,vertice);
	}

	@Test
	public void testUncheck()
	{
		setUp3();
		graph.uncheck();
		Iterable<Integer> lista=graph.getCC(1);
		int cont=0;
		for(Integer vertices:lista) {
			cont++;
		}
		assertEquals(0,cont);

	}

	@Test
	public void testDfs()
	{
		setUp3();
		Iterable<Integer> lista=graph.getCC(1);
		int cont=0;
		for(Integer vertices:lista) {
			cont++;
		}
		assertEquals(5,cont);
	}

	@Test
	public void testCc()
	{
		setUp3();
		Iterable<Integer> lista=graph.getCC(1);
		int cont=0;
		for(Integer vertices:lista) {
			cont++;
		}
		assertEquals(5,cont);

	}
}