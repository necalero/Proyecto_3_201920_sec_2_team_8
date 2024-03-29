package model.logic;

import java.io.BufferedReader;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;
import javax.management.Query;
import model.data_structures.Grafos.Arco;
import model.data_structures.Grafos.BreadthFirstPaths;
import model.data_structures.Grafos.DijkstraSP;
import model.data_structures.Grafos.GrafoNoDirigido;
import model.data_structures.Grafos.PrimMST;
import model.data_structures.Grafos.Vertice;
import model.data_structures.Grafos.htlpUberTrips;
import model.data_structures.HashTableLinearProbing;
import model.data_structures.Haversine;
import model.data_structures.IndexMinPQ;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.data_structures.UBERTrip;

public class MVCModelo<K> {


	//------------------------------------------------------------------------------
	//               Constantes
	//------------------------------------------------------------------------------

	private static String rutaArcos = "./data/bogota_arcos.txt";
	private static String rutaVertices = "./data/bogota_vertices.txt";
	private static String rutaCSV = "./data/bogota-cadastral-2018-1-WeeklyAggregate.csv";



	//------------------------------------------------------------------------------
	//               Atributos
	//------------------------------------------------------------------------------
	private GrafoNoDirigido grafo;
	private GrafoNoDirigido grafoSimplificado;
	private int cantidad = 0;
	private int contador = 0;
	private int cantidadVertices = 0;
	private int cantidadArcos = 0;
	private short lol;
	private htlpUberTrips uberTripsWeekly;

	//------------------------------------------------------------------------------
	//               Constructor
	//------------------------------------------------------------------------------
	/**
	 * Constructor		
	 * @throws IOException
	 */
	public MVCModelo() throws IOException
	{
		cantidad = 0;
		contador = 0;
		cantidadVertices = 0;
		cantidadArcos = 0;
		lol = 0;
		grafo = null;
		uberTripsWeekly = null;

	}

	//------------------------------------------------------------------------------
	//               Metodos
	//------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public void cargar() throws IOException
	{

		FileReader lector2 = new FileReader(rutaVertices);
		BufferedReader leer2 = new BufferedReader(lector2);
		String lineaActual2 = leer2.readLine();
		while(lineaActual2 != "" && lineaActual2 != null)
		{
			String[] valores = lineaActual2.split(";");
			if(!(valores[0].equals("id"))) cantidad++;

			lineaActual2 = leer2.readLine();
		}


		grafo = new GrafoNoDirigido(cantidad);
		FileReader lector = new FileReader(rutaVertices);
		BufferedReader leer = new BufferedReader(lector);
		String lineaActual = leer.readLine();
		while(lineaActual != "" && lineaActual != null)
		{
			String[] valores = lineaActual.split(";");
			if(!(valores[0].equals("id")))
			{
				int id = Integer.parseInt(valores[0]); 
				double lon = Double.parseDouble(valores[1]);
				double lat = Double.parseDouble(valores[2]);
				int mov_id = Integer.parseInt(valores[3]);
				Vertice vertex = new Vertice(id, lon, lat, mov_id);
				grafo.addVertex(id, vertex);
				cantidadVertices++;
			}
			lineaActual = leer.readLine();
		}
		FileReader lector3 = new FileReader(rutaArcos);
		BufferedReader leer3 = new BufferedReader(lector3);
		String lineaActual3 = leer3.readLine();
		while(lineaActual3 != "" && lineaActual3 != null)
		{
			String[] verticesArcos = lineaActual3.split(" ");
			for(int i=1 ; i<verticesArcos.length ; i++)
			{
				int vert1 = Integer.parseInt(verticesArcos[0]);
				int vert2 = Integer.parseInt(verticesArcos[i]);
				Vertice vertice1 = grafo.getVertex(vert1);
				Vertice vertice2 = grafo.getVertex(vert2);
				if(vertice1 != null && vertice2 != null)
				{
					grafo.addEdge(vert1, vert2, -1, -1);
					cantidadArcos++;
				}
			}

			lineaActual3 = leer3.readLine();
		}

		CSVReader csvreader = null;
		csvreader = new CSVReader(new FileReader(rutaCSV));
		csvreader.readNext();
		uberTripsWeekly = new htlpUberTrips<>(1000);
		int csvCant = 0;


		int i=0;

		for(String[] nextLine: csvreader)
		{
			UBERTrip actual = new UBERTrip(nextLine[0], nextLine[1],nextLine[2],nextLine[3], nextLine[4], nextLine[5], nextLine[6], "weekly");
			int key = Integer.parseInt(nextLine[0]);

			uberTripsWeekly.put(key, actual);
			csvCant++;
			i++;


		}
		csvreader.close();


		System.out.println("Se crearon " + cantidadVertices + " v�rtices");
		System.out.println("Se crearon " + cantidadArcos + " arcos");
		System.out.println("Se leyeron "+csvCant+" lineas del archivo csv.");
	}


	/**Calcula los pesos de los arcos
	 * 
	 */
	public void calcularPesos()
	{
		for(Vertice vertice : grafo.darVertices())
		{

			if(vertice !=null)
			{		

				LinkedList arcos = vertice.darArcos();

				Iterator it = arcos.iterator();


				while(it.hasNext())
				{
					Arco arco =  (Arco) it.next();

					if(arco !=null)
					{
						Vertice vDestino = grafo.getVertex(arco.darIdDestino());

						double lat1 = vertice.darLatitud();
						double lat2= vDestino.darLatitud();
						double lon1= vertice.darLongitud();
						double lon2= vDestino.darLongitud();
						Haversine haversineC = new Haversine();
						double haversineDistance = haversineC.distance(lat1, lon1, lat2, lon2);

						double costoTiempo = 0;
						double sumaTiempos = 0;
						double cantidadTiempos = 0;

						//Calcula el promedio de tiempo	de los viajes Uber reportados en el	trimestre donde	la zona origen y destino es la misma.	
						for(int i = 0; i < uberTripsWeekly.darData().length; i++)
						{
							if(uberTripsWeekly.darData()[i]!=null)
							{
								UBERTrip actual = uberTripsWeekly.darData()[i];
								if(actual.darSourceid()==vertice.darMOVEMENT_ID()&&actual.darDstid()==vDestino.darMOVEMENT_ID())
								{
									sumaTiempos += actual.darMean_travel_time();
									cantidadTiempos++;
								}
							}
						}

						if(sumaTiempos==0)
						{
							if(vertice.darMOVEMENT_ID()==vDestino.darMOVEMENT_ID())
							{
								costoTiempo = 10;	
							}
							else
							{
								costoTiempo = 100;	
							}
						}						
						else 
						{
							costoTiempo = sumaTiempos/cantidadTiempos;
						}
						vertice.setDistanciaArco(arco.darIdDestino(), haversineDistance);
						vertice.setTiempoArco(arco.darIdDestino(), costoTiempo);

					}

				}

			}
		}

	}


	public void persistirGrafoJSON(String pNombreArchivo)
	{
		String ruta = "./data/"+pNombreArchivo+".json";
		Gson gson = new Gson();
		String json = gson.toJson(grafo);
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(ruta);
		}catch (Exception e) {
			e.printStackTrace();
		}
		writer.println(json);
	}

	public void cargarGrafoJSON(String pRutaJSON) throws IOException
	{
		Gson gson = new Gson();
		FileReader reader = new FileReader(pRutaJSON);
		BufferedReader read = new BufferedReader(reader);
		String json = read.readLine();

		gson.fromJson(json, GrafoNoDirigido.class);
		read.close();
		reader.close();

	}



	public void crearArchivoHTML(String pNombreArchivo, GrafoNoDirigido pGrafo, double pLatMin, double pLatMax, double pLongMin, double pLongMax, String pColor, Vertice[] pAux) throws IOException
	{
		GrafoNoDirigido grafoAGraficar;
		if(pGrafo==null)
		{
			grafoAGraficar = grafo;
		}
		else
		{
			grafoAGraficar = pGrafo;
		}
		String ruta = "./data/"+pNombreArchivo+".html";
		int contador = 0;
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(ruta);
		}catch (Exception e) {
			e.printStackTrace();
		}
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("  <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
		writer.println("  <meta charset=\"utf-8\">");
		writer.println("  <title>"+pNombreArchivo+"</title>");
		writer.println("  <style>");
		writer.println("    #map {");
		writer.println("      height: 100%;");
		writer.println("    }");
		writer.println("    html,");
		writer.println("    body {");
		writer.println("      height: 100%;");
		writer.println("      margin: 0;");
		writer.println("      padding: 0;");
		writer.println("    }");
		writer.println("  </style>");
		writer.println("</head>");



		writer.println("<body>");
		writer.println("  <div id=\"map\"></div>");
		writer.println("  <script>");
		writer.println("    function initMap() {");
		writer.println("      var map = new google.maps.Map(document.getElementById('map'), {");
		writer.println("        zoom: 15.5,");
		writer.println("center: {");
		writer.println("lat: 4.609537,");
		writer.println("lng: -74.078715");
		writer.println("},");
		writer.println("mapTypeId: 'roadmap'");
		writer.println("});");
		writer.println("var line;");
		writer.println("var path;");
		String color = "blue";
		if(!pColor.equals("bruh")||!pColor.equals("especial"))
		{
			color = pColor;	
		}

		if(pAux!=null)
		{
			for(Vertice vertice: pAux)
			{
				if(vertice!=null)
				{

					double latV= vertice.darLatitud();
					double longV= vertice.darLongitud();


					writer.println("	  var circle = new google.maps.Circle ({");
					writer.println("		map: map,");
					writer.println("		center: new google.maps.LatLng("+latV+","+longV+"),");
					writer.println("		radius : 100,");
					writer.println("		strokeColor : '#000000',");

					writer.println("		fillColor : 'red'");


					writer.println("		});");
					writer.println("	  var circle = new google.maps.Circle ({");
					writer.println("		map: map,");
					writer.println("		center: new google.maps.LatLng("+latV+","+longV+"),");
					writer.println("		radius : 10,");
					writer.println("		strokeColor : '#000000',");

					writer.println("		fillColor : 'red'");


					writer.println("		});");

				}
			}
		}
		for(Vertice vertice: grafoAGraficar.darVertices())
		{
			if(vertice!=null)
			{

				double latV= vertice.darLatitud();
				double longV= vertice.darLongitud();
				//Default values: 4.621360||4.597714||-74.062707||-74.094723

				double latMin = 0;
				double latMax = 0;
				double longMin = 0;
				double longMax = 0;

				if(pLatMax == -1&&pLatMin==-1&&pLongMax==-1&&pLongMin==-1)
				{
					latMin = 4.597714;
					latMax = 4.621360;
					longMin = -74.094723;
					longMax = -74.062707;
				}
				else
				{
					latMin = pLatMin;
					latMax = pLatMax;
					longMin = pLongMin;
					longMax = pLongMax;
				}

				if(latV<=latMax&&latV>=latMin&&longV>=longMin&&longV<=longMax)
				{

					writer.println("	  var circle = new google.maps.Circle ({");
					writer.println("		map: map,");
					writer.println("		center: new google.maps.LatLng("+latV+","+longV+"),");
					writer.println("		radius : 10,");
					writer.println("		strokeColor : '#000000',");

					writer.println("		fillColor : '"+color+"'");


					writer.println("		});");

					Arco[] arcos = vertice.darArcosD();




					for(Arco arco : arcos)
					{
						if(arco!=null)
						{
							Vertice vDestino;
							if(pColor.equals("especial"))
							{
								int idDest = arco.darIdDestino();
								Vertice vDest = grafo.getVertex(arco.darIdDestino());
								int moviddest = vDest.darMOVEMENT_ID();
								
								vDestino = grafoAGraficar.getVertex(moviddest);
							}
							else
							{
								vDestino = grafoAGraficar.getVertex(arco.darIdDestino());
							}
							
							if(vDestino!=null)
							{
								double latVDest= vDestino.darLatitud();
								double longVDest= vDestino.darLongitud();

								if(!arco.isMarked()&&(latVDest<=latMax&&latVDest>=latMin&&longVDest>=longMin&&longVDest<=longMax))
								{
									writer.println("line = [{");
									writer.println("lat: " + latV + ",");
									writer.println("lng: " + longV);
									writer.println("},");
									writer.println("{");
									writer.println("lat: " + latVDest + ",");
									writer.println("lng: " + longVDest);
									writer.println("}");
									writer.println("];");
									writer.println("path = new google.maps.Polyline({");
									writer.println("path: line,");
									writer.println("strokeColor: '#FF0000',");
									writer.println("strokeWeight: 1");
									writer.println("});");
									writer.println("path.setMap(map);");
									contador++;
									if(vDestino.buscarArcoA(vertice.darId())!=null)
									{
										vDestino.buscarArcoA(vertice.darId()).marcar();
									}
								}
							}
							
						}

					}
				}
			}

		}

		writer.println("}");
		writer.println("</script>");
		writer.println("<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=&callback=initMap\">");
		writer.println("</script>");
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
		grafo.desmarcarTodosLosArcos();
		System.out.println("Se genero el archivo, lo podr� encontrar en la carpeta data.");

	}



	/**
	 * Dada una localizaci�n geogr�fica con latitud y longitud, encontrar el Id del V�rtice de la malla  vial  m�s  cercano  por  distancia  Haversine. 
	 * @param pLat	Latitud
	 * @param pLong Longitud
	 * @return Vertice m�s cercano a ubicacion dada
	 */
	public int encontrarIdVerticeMasCercano(double pLat, double pLong)
	{
		Vertice[] vertices = grafo.darVertices();
		double menorDistanciaHaversine = Double.POSITIVE_INFINITY;
		Vertice vertMasCercano = null;
		for(Vertice vertice: vertices)
		{
			if(vertice!=null)
			{
				Haversine haversine = new Haversine();
				double distanciaActual = haversine.distance(vertice.darLatitud(), vertice.darLongitud(), pLat, pLong);
				if(distanciaActual<menorDistanciaHaversine)
				{
					menorDistanciaHaversine = distanciaActual;
					vertMasCercano = vertice;
				}
			}

		}

		return vertMasCercano.darId();
	}

	/**                                         A4
	 * Encontrar el camino de costo m�nimo (menor tiempo promedio seg�n Uber en la ruta) para 
	 * un viaje entre dos localizaciones geogr�ficas de la ciudad ((lat,long) origen, (lat, long) destino),
	 * ingresados por el usuario. 
	 * 
	 * Mapa creado: camino  resultante  en  Google  Maps  (incluyendo  la ubicaci�n de inicio y la ubicaci�n de destino).
	 * 
	 * @param pLatOrigen
	 * @param pLongOrigen
	 * @param pLatDestino
	 * @param pLongDestino
	 * @return Array con los ids de los vertices a seguir.
	 */
	public GrafoNoDirigido encontrarGrafoTiempoMinimo(double pLatOrigen,double pLongOrigen, double pLatDestino, double pLongDestino)
	{
		//TODO : metodo
		int vIdOrigen = encontrarIdVerticeMasCercano(pLatOrigen, pLongOrigen);
		int vIdDestino = encontrarIdVerticeMasCercano(pLatDestino, pLongDestino);
		GrafoNoDirigido camino = grafo.grafoMenorDistanciaA(vIdOrigen, vIdDestino);
		return camino;
	}
	public Iterable encontrarCaminoTiempoMinimo(double pLatOrigen,double pLongOrigen, double pLatDestino, double pLongDestino)
	{
		int vIdOrigen = encontrarIdVerticeMasCercano(pLatOrigen, pLongOrigen);
		int vIdDestino = encontrarIdVerticeMasCercano(pLatDestino, pLongDestino);
		Iterable camino = grafo.caminoMenorDistanciaA(vIdOrigen, vIdDestino);
		return camino;
	}


	/**											A5
	 * Determinar los nv�rtices con menor velocidadpromedio en la ciudad de Bogot�.
	 * Siendo la velocidad promedio de un v�rtice v,el promedio de las velocidadesde todos sus arcos.
	 * 
	 * @param pN nv�rtices con menor velocidadpromedio en la ciudad de Bogot�.
	 * 
	 * 
	 * @return Max priority queue con los n vertices con menor velocidad promedio.
	 * 
	 * Mapa creado: marca la localizaci�n de los n v�rtices resultantes en un mapa en Google Maps usando un color 1. 
	 * Destaque la componente conectada m�s grande (con m�s v�rtices) usando un color 2. 
	 * Para esta componente muestra sus v�rtices y sus arcos.
	 * 
	 * 
	 */
	public Vertice[] verticesConMenorVelocidad(int pN)
	{
		//TODO: metodo
		IndexMinPQ velocidades = new IndexMinPQ<>(grafo.V());
		Vertice[] vertices = grafo.darVertices();
		Vertice[] nMenorVel = new Vertice[pN];
		for(Vertice v : vertices)
		{
			if(v!=null)
			{
				double sumaVelocidades = 0;
				double cantVelocidades = 0;
				Arco[] arcos = v.darArcosD();
				for(Arco arco : arcos)
				{
					if(arco!=null)
					{
						double vel = arco.darVelocidad();
						if(vel>0)
						{
							sumaVelocidades+=vel;
							cantVelocidades++;
						}
					}
				}
				double promedioVelocidades = sumaVelocidades/cantVelocidades;
				velocidades.insert(v.darId(), promedioVelocidades);
			}
		}

		for(int i = 0; i< nMenorVel.length; i++)
		{
			nMenorVel[i] = grafo.getVertex(velocidades.delMin());
		}

		return nMenorVel;
	}



	/**											A6
	 * Calcula  un  �rbol  de  expansi�n  m�nima  (MST)  con  criterio  distancia,  utilizando 
	 * el algoritmo de Prim, aplicado al componente conectado (subgrafo) m�s grande de la malla
	 * vial de Bogot�.
	 * @return
	 */
	public GrafoNoDirigido distanciaMSTSubgrafoMayor()
	{
		GrafoNoDirigido compMayor = componenteConectadoMayor(grafo.darIDVertices());
		PrimMST mstPrim = new PrimMST(compMayor, "distancia");
		return mstPrim.arbolMST(grafo);
	}


	/**                                         A7
	 * Encontrar el camino de costo m�nimo (menor distancia Haversine) para 
	 * un viaje entre dos localizaciones geogr�ficas de la ciudad ((lat,long) origen, (lat, long) destino),
	 * ingresados por el usuario. 
	 * 
	 * Mapa creado: camino  resultante  en  Google  Maps  (incluyendo  la ubicaci�n de inicio y la ubicaci�n de destino).
	 * 
	 * @param pLatOrigen
	 * @param pLongOrigen
	 * @param pLatDestino
	 * @param pLongDestino
	 * @return Array con los ids de los vertices a seguir.
	 */
	public GrafoNoDirigido encontrarGrafoDistanciaMinimo(double pLatOrigen,double pLongOrigen, double pLatDestino, double pLongDestino)
	{
		//TODO : metodo
		int vIdOrigen = encontrarIdVerticeMasCercano(pLatOrigen, pLongOrigen);
		int vIdDestino = encontrarIdVerticeMasCercano(pLatDestino, pLongDestino);
		GrafoNoDirigido camino = grafo.grafoMenorDistanciaA(vIdOrigen, vIdDestino);
		return camino;
	}
	public Iterable encontrarCaminoDistanciaMinimo(double pLatOrigen,double pLongOrigen, double pLatDestino, double pLongDestino)
	{
		int vIdOrigen = encontrarIdVerticeMasCercano(pLatOrigen, pLongOrigen);
		int vIdDestino = encontrarIdVerticeMasCercano(pLatDestino, pLongDestino);
		Iterable camino = grafo.caminoMenorDistanciaA(vIdOrigen, vIdDestino);
		return camino;
	}

	/**											A8
	 * 
	 * @param pLatOrigen
	 * @param pLongOrigen
	 * @param pTiempo
	 * @return
	 */
	public GrafoNoDirigido verticesAlcanzablesParaTiempoT(double pLatOrigen, double pLongOrigen, double pTiempo)
	{
		int vIdOrigen = encontrarIdVerticeMasCercano(pLatOrigen, pLongOrigen);
		Vertice vOrigen = grafo.getVertex(vIdOrigen);
		GrafoNoDirigido alcanzables = new GrafoNoDirigido<>();
		alcanzables.addVertex(vOrigen.darId(), vOrigen);
		double tiempo = pTiempo;
		grafo.uncheck();
		alcanzables = alcanzable(alcanzables, vIdOrigen, tiempo);
		return alcanzables;
	}

	public GrafoNoDirigido alcanzable(GrafoNoDirigido pAlcanzables, int vId, double pTiempoRestante) 
	{
		GrafoNoDirigido alcanzables = pAlcanzables;
		if(pTiempoRestante >= 0)
		{

			Vertice actual = grafo.getVertex(vId);
			actual.marcar(); 
			double tiempoRestante = pTiempoRestante;



			int[] aExplorar = actual.adj();


			for (int idAdy: aExplorar)
			{
				Vertice adyActual = (Vertice) grafo.getVertex(idAdy);
				Arco a = actual.buscarArcoA(idAdy);
				tiempoRestante -= a.darTiempo();
				if(adyActual!=null)
				{
					if (!adyActual.isMarked())
					{
						alcanzables.addVertex(idAdy, adyActual);
						alcanzables.addEdge(vId, idAdy, a.darDistancia(), a.darTiempo());
						alcanzables = alcanzable(alcanzables, idAdy, tiempoRestante); 

					}
				}

			}

		}
		return alcanzables;

	}



	/**
	 * Retorna el componente conectado de mayor tama�o dados unos vertices de entrada
	 * @param idVertices Los id de los vertices de entrada
	 * @return
	 */
	public GrafoNoDirigido componenteConectadoMayor(Iterable idVertices)
	{

		GrafoNoDirigido compMayor = null;
		int mayorNumeroDeVertices = 0;
		Iterator it = idVertices.iterator();
		double mayorIdTotal = 0;
		while(it.hasNext())
		{
			int i = (int) it.next();
			Vertice v = (Vertice) grafo.getVertex(i);
			if(mayorIdTotal<v.darId())
			{
				System.out.println("-----------Mayor id total "+v.darId());
			}

			int idV = v.darId();
			if(!grafo.getVertex(idV).isMarked())
			{
				GrafoNoDirigido compActual = grafo.getCC(idV);
				grafo.marcarCC(compActual);
				if(compActual.V()>mayorNumeroDeVertices)
				{
					compMayor = compActual;
					mayorNumeroDeVertices = compActual.V();
				}
			}

		}
		grafo.uncheck();
		return compMayor;

	}
	public int cantComponentesConectados(Iterable idVertices)
	{

		int compConectados = 0;	
		Iterator it = idVertices.iterator();
		while(it.hasNext())
		{
			int idV = (int) it.next();
			if(!grafo.getVertex(idV).isMarked())
			{
				GrafoNoDirigido compActual = grafo.getCC(idV);
				grafo.marcarCC(compActual);
				compConectados++;
			}

		}
		grafo.uncheck();
		return compConectados;

	}


	public GrafoNoDirigido crearGrafoSimplificado()
	{

		grafoSimplificado = new GrafoNoDirigido<>();
		for(Vertice v : grafo.darVertices())
		{
			if(v!=null)
			{
				Vertice x = new Vertice(v.darId(), v.darLongitud(), v.darLatitud(), v.darMOVEMENT_ID());
				x.cleanArcos();
				grafoSimplificado.addVertex(x.darMOVEMENT_ID(), x);
			}
		}
		for(Vertice w : grafoSimplificado.darVertices())
		{
			if(w!=null)
			{
				Vertice vert = grafo.getVertex(w.darId());
				for(Arco a : vert.darArcosD())
				{
					if(!(grafo.getVertex(a.darIdDestino()).darMOVEMENT_ID()==w.darMOVEMENT_ID()))
					{
						double costoTiempo = 0;
						double sumaTiempos = 0;
						double cantidadTiempos = 0;
						
						for(UBERTrip ut : uberTripsWeekly.darData())
						{
							if(ut!=null)
							{
								if((ut.darSourceid()==w.darMOVEMENT_ID()&&ut.darDstid()==grafo.getVertex(a.darIdDestino()).darMOVEMENT_ID())||(ut.darDstid()==w.darMOVEMENT_ID()&&ut.darSourceid()==grafo.getVertex(a.darIdDestino()).darMOVEMENT_ID()))
								{
									sumaTiempos += ut.darMean_travel_time();
									cantidadTiempos++;
								}
							}
						}
						costoTiempo = sumaTiempos/cantidadTiempos;
						
						grafoSimplificado.addEdge(w.darMOVEMENT_ID(), grafo.getVertex(a.darIdDestino()).darMOVEMENT_ID(), 1, costoTiempo);
					}
				}
			}

		}
		return grafoSimplificado;



		/*
	grafoSimplificado = new GrafoNoDirigido<>();
	Vertice representante = null;
	Vertice[] vertices = grafo.darVertices();
	boolean seEncontro = false;
	for(UBERTrip ut : uberTripsWeekly.darData())
	{
		if(ut!=null)
		{
			if(!grafoSimplificado.contains(ut.darSourceid()))
			{
				for(int i=0; i<vertices.length&&!seEncontro; i++)
				{
					if(vertices[i]!=null)
					{
						if(vertices[i].darMOVEMENT_ID()==ut.darSourceid())
						{
							representante = vertices[i];
							seEncontro=true;
						}
					}
				}

			}
			if(!grafoSimplificado.contains(ut.darDstid()))
			{
				for(int i=0; i<vertices.length&&!seEncontro; i++)
				{
					if(vertices[i]!=null)
					{
						if(vertices[i].darMOVEMENT_ID()==ut.darDstid())
						{
							representante = vertices[i];
							seEncontro=true;
						}
					}
				}

			}
			Vertice v = new Vertice(representante.darMOVEMENT_ID(), representante.darLongitud(), representante.darLatitud(), representante.darMOVEMENT_ID());
			grafoSimplificado.addVertex(representante.darMOVEMENT_ID(), v);
		}


	}
	double costoTiempo = 0;
	double sumaTiempos = 0;
	double cantidadTiempos = 0;


	for(Vertice v : grafo.darVertices())
	{

		if(v!=null)
		{
			v.marcar();
			for(Arco a : v.darArcosD())
			{

				if(a!=null)
				{
					if(!a.isMarked())
					{
						a.marcar();
						//Calcula el promedio de tiempo	de los viajes Uber reportados en el	trimestre donde	la zona origen y destino es la misma.
						int idOrigen = a.darIdOrigen();
						Vertice origen = grafo.getVertex(idOrigen);
						int idDestino = a.darIdDestino();
						Vertice destino = grafo.getVertex(idDestino);
						for(int i = 0; i < uberTripsWeekly.darData().length; i++)
						{
							if(uberTripsWeekly.darData()[i]!=null)
							{
								UBERTrip actual = uberTripsWeekly.darData()[i];
								if(actual.darSourceid()==origen.darMOVEMENT_ID()&&actual.darDstid()==destino.darMOVEMENT_ID())
								{
									sumaTiempos += actual.darMean_travel_time();
									cantidadTiempos++;
								}
							}
						}

						if(sumaTiempos==0)
						{
							costoTiempo = 200;
						}						
						else 
						{
							costoTiempo = sumaTiempos/cantidadTiempos;
						}
						grafoSimplificado.addEdge(v.darMOVEMENT_ID(), grafo.getVertex(a.darIdDestino()).darMOVEMENT_ID(), 0, costoTiempo);

					}

				}
			}
		}
	}
	return grafoSimplificado;
		 */




	}


	public GrafoNoDirigido DijkstraTiempoGrafoSimplificado(int pZonaOrigen, int pZonaDestino)
	{
		DijkstraSP dijk = new DijkstraSP(grafoSimplificado, pZonaOrigen, "tiempoEspecial");
		GrafoNoDirigido respuesta = dijk.grafoTiempoEspecialMinimo(pZonaDestino, grafoSimplificado, grafo);
		return respuesta;
	}

	public GrafoNoDirigido grafoMasLargoGrafoSimplificado(int pIdZonaOrigen)
	{
		BreadthFirstPaths bfpaths = new BreadthFirstPaths(grafoSimplificado, pIdZonaOrigen);
		GrafoNoDirigido longestPath = bfpaths.graphToFurthest(grafoSimplificado);
		return longestPath;

	}
	public Iterable caminoMasLargoGrafoSimplificado(int pIdZonaOrigen)
	{
		BreadthFirstPaths bfpaths = new BreadthFirstPaths(grafoSimplificado, pIdZonaOrigen);
		Iterable longestPath = bfpaths.pathToFurthest(grafoSimplificado);
		return longestPath;

	}







	//------------------------------------------------------------------------------
	//               Main
	//------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException {
		MVCModelo modelo = new MVCModelo();
	}
}