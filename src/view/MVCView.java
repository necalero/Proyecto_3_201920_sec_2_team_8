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
	 * Imprime el men� de servicios con sus respectivos n�meros.
	 */
	public void printMenu()
	{
		System.out.println("");
		System.out.println("1. Cargar los datos.");
		System.out.println("2. Calcular pesos de arcos.");
		System.out.println("3. Guardar grafo con los datos");
		System.out.println("4. Cargar grafo con los datos");
		System.out.println("5. Genera el grafo en html.");
		System.out.println("6. Acaba.");
		System.out.print("Dar el numero de opci�n a resolver. Luego oprimir tecla <RETURN>: ");
	}

	/**
	 * Imprime el mensaje dado por par�metro.
	 * @param mensaje. Mensaje a imprimir.
	 */
	public void printMessage(String mensaje)
	{
		System.out.println(mensaje);
	}		
}