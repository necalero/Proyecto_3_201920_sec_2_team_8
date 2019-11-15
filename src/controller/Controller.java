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
	
	private Queue<Integer> llaves = new Queue<>();
	private Queue<String> vals = new Queue<>();

	/**
	 * Crear la vista y el modelo del proyecto
	 * @throws IOException 
	 */
	public Controller() throws IOException {
		view = new MVCView();
		modelo = new MVCModelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String[] datos;

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				System.out.println(" Adios");
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
