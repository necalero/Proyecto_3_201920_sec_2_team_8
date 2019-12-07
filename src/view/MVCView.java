package view;

import model.logic.MVCModelo;

/**
 * Vista del modelo MVC.
 */
public class MVCView 
{
	/**
	 * Constructor de la vista.
	 */
	public MVCView() {};

	/**
	 * Imprime el menú de servicios con sus respectivos números.
	 */
	public void printMenu()
	{
		System.out.println("");
		System.out.println("1. Cargar los datos.");
		System.out.println("2. Calcular pesos de arcos.");
		System.out.println("3. Guardar grafo con los datos");
		System.out.println("4. Cargar grafo con los datos");
		System.out.println("5. Genera el grafo en .html");
		System.out.println();
		System.out.println("6. Encontrar camino tiempo minimo.");
		System.out.println("7. Determinar n vertices menor velocidad promedio");
		System.out.println("8. Calcular arbol expansion minima con criterio distancia aplicado al subrago más grande (Prim)");
		System.out.println();
		System.out.println("9. Encontrar el camino de distancia minima");
		System.out.println("10. Vertices alcanzables para tiempo T (segundos) a partir de localizacion dada");
		System.out.println("11. Calcular arbol expansion minima con criterio distancia aplicado al subgrafo más grande (Kruskal)");
		System.out.println();
		System.out.println("12. Construir grafo simplificado ");
		System.out.println("13. Calcular camino costo minimo (Dijkstra) entre dos zonas");
		System.out.println("14. Calcular camino mas largo");
		System.out.println("0. Acaba.");
		System.out.print("Dar el numero de opción a resolver. Luego oprimir tecla <RETURN>: ");
	}

	/**
	 * Imprime el mensaje dado por parámetro.
	 * @param mensaje. Mensaje a imprimir.
	 */
	public void printMessage(String mensaje)
	{
		System.out.println(mensaje);
	}		
}