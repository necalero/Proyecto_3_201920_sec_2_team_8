package controller;

import java.io.IOException;
import java.util.*;

//import model.data_structures.Queue;
import model.logic.MVCModelo;
import view.MVCView;
import model.data_structures.Queue;;

public class Controller {

	/* Instancia del Modelo */
	private MVCModelo modelo;

	/* Instancia de la Vista */
	private MVCView view;

	private boolean cargado, pesosCalculados;
	
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @throws IOException 
	 */
	public Controller() throws IOException {
		view = new MVCView();
		modelo = new MVCModelo();
		cargado = false;
		pesosCalculados = false;
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			view.printMenu();
			int option = lector.nextInt();
			switch (option) {
			case 1:
				view.printMessage("Usted ha escogido la opcion 1. Cargar los datos");
				try 
				{
					modelo.cargar();
					cargado = true;
				} 
				catch (IOException e) 
				{				
					e.printStackTrace();
				}
				break;
			case 2:
				view.printMessage("Usted ha escogido la opcion 2. Calcular los pesos de los arcos");
				try 
				{
					if(cargado==true)
					{
						modelo.calcularPesos();
						pesosCalculados = true;
						view.printMessage("Se calcularon los pesos de los arcos.");
					}
					else
					{
						view.printMessage("Se deben cargar los archivos antes de calcular los pesos");
					}

				} 
				catch (Exception e) 
				{				
					e.printStackTrace();
				}
				break;
				
			case 3:
				view.printMessage("Usted ha escogido la opcion 3. Guardar el grafo en archivo JSON");
				try
				{
					if(!pesosCalculados)
					{
						view.printMessage("No se han modificado los pesos de los arcos, por lo que este paso será inutil.");
					}
					//TODO: Metodo para guardar en JSON
					view.printMessage("Se guardo el grafo en la carpeta data.");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				break;
			case 4:
				view.printMessage("Usted ha escogido la opcion 4. Cargar el grafo desde archivo JSON");
				try
				{
					//TODO: Metodo para cargar el JSON
					cargado = true;
					
					view.printMessage("Se cargó el grafo de la carpeta data.");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
				break;

			case 5:
				view.printMessage("Usted ha escogido la opcion 5. Generar archivo html");
				if(cargado==true)
				{
					view.printMessage("Ingrese el nombre del archivo a generar.");
					String nombreArchivo = lector.nextLine();
					try {
						modelo.crearArchivoHTML(nombreArchivo);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				else
				{
					view.printMessage("Se deben cargar los archivos antes de generar el HTML");
				}

				break;
			case 6:
				
				
				
				
				break;
			case 10000:
				lector.close();
				fin = true;
				break;
			default:
				System.out.println("No es valido, intente otra vez.");
				break;
			}
		}

	}
}
