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
		System.out.println("2. crea el grafo grafo.");
		System.out.println("3. Cargar grafo con los datos");
		System.out.println("4. Reportar componentes conectados.");
		System.out.println("5. muestra el grafo en html.");
		System.out.println("6. acaba.");
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