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

	private boolean cargado;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @throws IOException 
	 */
	public Controller() throws IOException {
		view = new MVCView();
		modelo = new MVCModelo();
		cargado = false;
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

			case 5:
				view.printMessage("Usted ha escogido la opcion 5. Generar archivo html");
				if(cargado==true)
				{
					try {
						modelo.crearArchivoHTML();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					view.printMessage("Se deben cargar los archivos antes de generar el HTML");
				}

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
