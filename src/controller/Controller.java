package controller;

import java.io.IOException;
import java.util.*;

//import model.data_structures.Queue;
import model.logic.MVCModelo;
import view.MVCView;
import model.data_structures.Queue;
import model.data_structures.Grafos.Arco;
import model.data_structures.Grafos.GrafoNoDirigido;
import model.data_structures.Grafos.Vertice;;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
					Scanner lector5 = new Scanner(System.in);
					String nombreArchivo = lector5.nextLine();
					try {
						modelo.crearArchivoHTML(nombreArchivo, null, -1, -1, -1, -1);
					} catch (IOException e) 
					{

						e.printStackTrace();
					}
					lector5.close();
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
				view.printMessage("Usted ha escogido la opción 6. Encontrar camino tiempo minimo.");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 7:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 7. Determinar n vertices menor velocidad promedio");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 8:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 8. Calcular arbol expansion minima con criterio distancia aplicado al subrago más grande (Prim)");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 9:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 9. Encontrar el camino de distancia minima");
				if(cargado)
				{
					Scanner lector9 = new Scanner(System.in);
					view.printMessage("Ingrese latitud origen");
					double latOrigen = lector9.nextDouble();
					view.printMessage("Ingrese longitud origen");
					double longOrigen = lector9.nextDouble();
					view.printMessage("Ingrese latitud destino");
					double latDestino = lector9.nextDouble();
					view.printMessage("Ingrese longitud destino");
					double longDestino = lector9.nextDouble();
					try {
						GrafoNoDirigido distanciaMinima = modelo.encontrarGrafoDistanciaMinimo(latOrigen, longOrigen, latDestino, longDestino);
						Iterable caminoDistanciaMinima = modelo.encontrarCaminoDistanciaMinimo(latOrigen, longOrigen, latDestino, longDestino);
						
						double totalTiempo = 0;
						double totalDistancia = 0;
						int cantidadVertices=0;
						if(caminoDistanciaMinima==null)
						{
							view.printMessage("No existe un camino entre las coordenadas dadas.");
						}
						else
						{
							Iterator it = caminoDistanciaMinima.iterator();
							view.printMessage("El camino a seguir es:");
							Arco a;
							while(it.hasNext())
							{					
								a = (Arco) it.next();
								if(a!=null)
								{
									
									view.printMessage(a.darIdOrigen()+"->"+a.darIdDestino());
									totalTiempo+=a.darTiempo();
									totalDistancia+=a.darDistancia();
									cantidadVertices++;

								}
								
							}
							view.printMessage("El total de vertices es: "+cantidadVertices);
							view.printMessage("Sus vertices son: ");
							Vertice[] vertices = distanciaMinima.darVertices();
							for(Vertice v : vertices)
							{
								if(v!=null)
								{
									view.printMessage("ID: "+ v.darId());
									view.printMessage("Latitud: "+ v.darLatitud());
									view.printMessage("Longitud: " +v.darLongitud());

								}
							}
							view.printMessage("El tiempo estimado es: "+totalTiempo);
							view.printMessage("La distancia total del recorrido es: "+ totalDistancia);
							modelo.crearArchivoHTML("caminoMenorDistancia", distanciaMinima, 1, 6, -80, -70);	
						}

					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				else
				{
					view.printMessage("No se han cargado los datos, cargue los datos antes.");
				}


				view.printMessage("Escoja su siguiente accion:");
				break;
			case 10:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 10. Vertices alcanzables para tiempo T (segundos) a partir de localizacion dada");
				Scanner lector10 = new Scanner(System.in);
				view.printMessage("Ingrese latitud origen");
				double latOrigen = lector10.nextDouble();
				view.printMessage("Ingrese longitud origen");
				double longOrigen = lector10.nextDouble();
				view.printMessage("Ingrese el tiempo t (En segundos)");
				int t = lector10.nextInt();
				GrafoNoDirigido alcanzables = modelo.verticesAlcanzablesParaTiempoT(latOrigen, longOrigen, t);
				try {
					modelo.crearArchivoHTML("Vertices Alcanzables Para Tiempo " +t+"s", alcanzables, 1, 6, -80, -70);
				} catch (IOException e) {
					
					e.printStackTrace();
				}	



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 11:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 11. Calcular arbol expansion minima con criterio distancia aplicado al subgrafo más grande (Kruskal)");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 12:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 12. Construir grafo simplificado ");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 13: 
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 13. Calcular camino costo minimo (Dijkstra) entre dos zonas");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 14:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 14. Calcula camino mas largo");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 20:
				
				int x = modelo.encontrarIdVerticeMasCercano(-10, -90);
				view.printMessage(""+x);
				
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
