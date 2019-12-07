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
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opcion 1. Cargar los datos");
				view.printMessage("Por favor espere mientras se cargan los datos.");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				try 
				{
					modelo.cargar();
					cargado = true;
				} 
				catch (IOException e) 
				{				
					e.printStackTrace();
				}
				view.printMessage("Escoja su siguiente accion:");
				view.printMessage("");
				
				break;
			case 2:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opcion 2. Calcular los pesos de los arcos");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
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
				
				view.printMessage("Escoja su siguiente accion:");
				view.printMessage("");
				view.printMessage("");
				break;
				
			case 3:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opcion 3. Guardar el grafo en archivo JSON");
				try
				{
					//TODO: Persistir grafo json
					if(!pesosCalculados)
					{
						view.printMessage("No se han modificado los pesos de los arcos, por lo que este paso será inutil.");
					}
					view.printMessage("Por favor inserte el nombre con el que desea guardar el archivo");
					Scanner lectorJSON = new Scanner(System.in);
					String nombreArchivo = lectorJSON.nextLine();
					modelo.persistirGrafoJSON(nombreArchivo);
					view.printMessage("Se guardo el grafo en la carpeta data.");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 4:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
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
				view.printMessage("Escoja su siguiente accion:");
				break;

			case 5:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opcion 5. Generar archivo html");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				if(cargado==true)
				{
					view.printMessage("Ingrese el nombre del archivo a generar.");
					Scanner lector2 = new Scanner(System.in);
					String nombreArchivo = lector2.nextLine();
					try {
						modelo.crearArchivoHTML(nombreArchivo, null, -1, -1, -1, -1);
					} catch (IOException e) 
					{
						
						e.printStackTrace();
					}
				}
				else
				{
					view.printMessage("Se deben cargar los archivos antes de generar el HTML");
				}
				view.printMessage("Escoja su siguiente accion:");
				view.printMessage("");
				
				break;
			case 6:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 7:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 8:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 9:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 10:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 11:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 12:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 13: 
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 14:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción ");
				
				
				
				view.printMessage("Escoja su siguiente accion:");
				break;
			case 0:
				lector.close();
				fin = true;
				break;
			default:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("No es valido, intente otra vez.");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				break;
			}
		}

	}
}
