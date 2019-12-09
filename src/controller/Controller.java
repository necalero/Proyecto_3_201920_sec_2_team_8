package controller;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.time.StopWatch;

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
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
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
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				try
				{

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
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
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
						modelo.crearArchivoHTML(nombreArchivo, null, -1, -1, -1, -1, "blue",null);
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
				view.printMessage("Usted ha escogido la opción 6. Encontrar camino tiempo minimo.");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}

				if(cargado)
				{
					Scanner lector6 = new Scanner(System.in);
					view.printMessage("Ingrese latitud origen");
					double latOrigen = lector6.nextDouble();
					view.printMessage("Ingrese longitud origen");
					double longOrigen = lector6.nextDouble();
					view.printMessage("Ingrese latitud destino");
					double latDestino = lector6.nextDouble();
					view.printMessage("Ingrese longitud destino");
					double longDestino = lector6.nextDouble();
					try {
						GrafoNoDirigido tiempoMinimo = modelo.encontrarGrafoTiempoMinimo(latOrigen, longOrigen, latDestino, longDestino);
						Iterable caminoDistanciaMinima = modelo.encontrarCaminoTiempoMinimo(latOrigen, longOrigen, latDestino, longDestino);

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
							Vertice[] vertices = tiempoMinimo.darVertices();
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
							modelo.crearArchivoHTML("caminoMenorDistancia", tiempoMinimo, 1, 6, -80, -70, "green",null);	
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




			case 7:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 7. Determinar n vertices menor velocidad promedio");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				Scanner lector7 = new Scanner(System.in);
				view.printMessage("Ingrese el numero de vertices a retornar");
				int n = lector7.nextInt();
				if(n<=0)
				{
					view.printMessage("Por favor ingrese una cantidad valida.");
					break;
				}

				Vertice[] menorVel = modelo.verticesConMenorVelocidad(n);
				Queue ids = new Queue<>();
				view.printMessage("Los vertices "+n+" con menor velocidad promedio son:" );
				int i = 1;
				for(Vertice vertice : menorVel)
				{
					ids.enqueue(vertice.darId());
					view.printMessage(i+".)");
					view.printMessage("ID: "+vertice.darId());
					view.printMessage("Latitud: "+ vertice.darLatitud());
					view.printMessage("Longitud: " +vertice.darLongitud());
					i++;
				}
				view.printMessage("La cantidad de componentes conectados definidos por estos vertices es: "+ modelo.cantComponentesConectados(ids));
				GrafoNoDirigido mayorCC = modelo.componenteConectadoMayor(ids);
				try {
					modelo.crearArchivoHTML("Mayor CC min velocidad", mayorCC, -1, -1, -1, -1, "bruh", menorVel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				view.printMessage("Escoja su siguiente accion:");
				break;
			case 8:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 8. Calcular arbol expansion minima con criterio distancia aplicado al subgrafo más grande (Prim)");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				
				
				
				GrafoNoDirigido g = modelo.distanciaMSTSubgrafoMayor();
				view.printMessage("El total de vertices pertenecientes es: "+g.V());
				double costoTotal = 0;
				for(Vertice v : g.darVertices())
				{
					if(v!=null)
					{
						view.printMessage("ID Vertice: "+v.darId());
						for(Arco arco : v.darArcosD())
						{
							if(arco!=null)
							{
								view.printMessage("Arco de "+arco.darIdOrigen()+" a "+arco.darIdDestino());
								costoTotal+=arco.darDistancia();
							}
						}
					}
				}
				view.printMessage("El costo total del arbol es: " + costoTotal);
				
				try {
					modelo.crearArchivoHTML("MSTPrim", g, -1, -1, -1, -1, "red", null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 9:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 9. Encontrar el camino de distancia minima");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
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
							modelo.crearArchivoHTML("caminoMenorDistancia", distanciaMinima, 1, 6, -80, -70, "red",null);	
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
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				Scanner lector10 = new Scanner(System.in);
				view.printMessage("Ingrese latitud origen");
				double latOrigen = lector10.nextDouble();
				view.printMessage("Ingrese longitud origen");
				double longOrigen = lector10.nextDouble();
				view.printMessage("Ingrese el tiempo t (En segundos)");
				int t = lector10.nextInt();

				GrafoNoDirigido alcanzables = modelo.verticesAlcanzablesParaTiempoT(latOrigen, longOrigen, t);
				try {
					modelo.crearArchivoHTML("Vertices Alcanzables Para Tiempo " +t+"s", alcanzables, 1, 6, -80, -70, "red", null);
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
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 12:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 12. Construir grafo simplificado ");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}

				GrafoNoDirigido simpl = modelo.crearGrafoSimplificado();

				view.printMessage("Se creo el grafo simplificado.");

				int x = simpl.V();
				int y = simpl.E();
				view.printMessage("Se crearon "+x+" vertices y "+y+" arcos.");
				try {
					modelo.crearArchivoHTML("Simplificado", simpl, 1, 6, -80, -70, "especial", null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				view.printMessage("Puede encontrar el mapa generado en la carpeta data");



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 13: 
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 13. Calcular camino costo minimo (Dijkstra) entre dos zonas");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				else
				{
					Scanner lector13 = new Scanner(System.in);
					view.printMessage("Ingrese zona origen");
					int pZonaOrigen = lector13.nextInt();
					view.printMessage("Ingrese zona destino");
					int pZonaDestino = lector13.nextInt();
					Timer timer = new Timer();
					StopWatch sw = new StopWatch();
					sw.start();
					GrafoNoDirigido g13 = modelo.DijkstraTiempoGrafoSimplificado(pZonaOrigen, pZonaDestino);
					sw.stop();
					view.printMessage("Se tomo :"+(sw.getNanoTime())/1000000+" milisegundos");
					try {
						modelo.crearArchivoHTML("dijkspec", g13, -1, -1, -1, -1, "especial", null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}



				view.printMessage("Escoja su siguiente accion:");
				break;
			case 14:
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("");
				view.printMessage("Usted ha escogido la opción 14. Calcula camino mas largo");
				if(!cargado)
				{
					view.printMessage("Antes de esto debe cargar los datos");
					break;
				}
				else
				{
					Scanner lector14 = new Scanner(System.in);
					view.printMessage("Ingrese zona origen");
					int pIdZonaOrigen = lector14.nextInt();
					Timer timer = new Timer();
					StopWatch sw = new StopWatch();
					sw.start();
					GrafoNoDirigido g14 = modelo.grafoMasLargoGrafoSimplificado(pIdZonaOrigen);
					Iterable i14 = modelo.caminoMasLargoGrafoSimplificado(pIdZonaOrigen);
					Iterator it = i14.iterator();
					sw.stop();
					view.printMessage("Se tomo :"+(sw.getNanoTime())/1000000+" milisegundos");
					view.printMessage("El camino a tomar es: ");
					while(it.hasNext())
					{
						int v = (int) it.next();

						view.printMessage("->"+v);

					}
					try {
						modelo.crearArchivoHTML("Camino mas largo", g14, 0, 10, -80, -70, "especial", null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}


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
