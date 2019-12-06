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

import javax.management.Query;
import model.data_structures.Grafos.Arco;
import model.data_structures.Grafos.GrafoNoDirigido;

import model.data_structures.Grafos.Vertice;
import model.data_structures.Grafos.htlpUberTrips;
import model.data_structures.HashTableLinearProbing;
import model.data_structures.Haversine;
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

		int i=0;

		for(String[] nextLine: csvreader)
		{
			UBERTrip actual = new UBERTrip(nextLine[0], nextLine[1],nextLine[2],nextLine[3], nextLine[4], nextLine[5], nextLine[6], "weekly");
			int key = Integer.parseInt(nextLine[0]);
			uberTripsWeekly.put(key, actual);
			i++;

		}
		csvreader.close();


		System.out.println("Se crearon " + cantidadVertices + " vértices");
		System.out.println("Se crearon " + cantidadArcos + " arcos");
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



	public void crearArchivoHTML(String pNombreArchivo) throws IOException
	{
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
		writer.println("  <title>cale</title>");
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


		for(Vertice vertice: grafo.darVertices())
		{
			if(vertice!=null)
			{

				double latV= vertice.darLatitud();
				double longV= vertice.darLongitud();
				if(latV<=4.621360&&latV>=4.597714&&longV<=-74.062707&&longV>=-74.094723)
				{

					writer.println("	  var circle = new google.maps.Circle ({");
					writer.println("		map: map,");
					writer.println("		center: new google.maps.LatLng("+latV+","+longV+"),");
					writer.println("		radius : 10,");
					writer.println("		strokeColor : '#000000',");
					writer.println("		fillColor : 'blue'");
					writer.println("		});");

					LinkedList arcos = vertice.darArcos();

					Iterator it = arcos.iterator();


					while(it.hasNext())
					{

						Arco actual =  (Arco) it.next();
						
						Vertice vDestino = grafo.getVertex(actual.darIdDestino());
						
						double latVDest= vDestino.darLatitud();
						double longVDest= vDestino.darLongitud();

						if(it != null&&!actual.isMarked()&&(latVDest<=4.621360&&latVDest>=4.597714&&longVDest<=-74.062707&&longVDest>=-74.094723))
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
							double porcentajeCarga = (((double)contador)/((double)grafo.E()))*100;
							DecimalFormat perc = new DecimalFormat("###.##");
							if(lol==0)
							{
								System.out.println(perc.format(porcentajeCarga)+"%");
							}
							lol = (short) ((short) (lol+1)%100);
							if(vDestino.buscarArcoA(vertice.darId())!=null)
							{
								vDestino.buscarArcoA(vertice.darId()).marcar();
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
		System.out.println("Se genero el archivo, lo podrá encontrar en la carpeta data.");

	}


	/**
	 * Dada una localización geográfica con latitud y longitud, encontrar el Id del Vértice de la malla  vial  más  cercano  por  distancia  Haversine. 
	 * @param pLat	Latitud
	 * @param pLong Longitud
	 * @return Vertice más cercano a ubicacion dada
	 */
	public Vertice encontrarVerticeMasCercano(double pLat, double pLong)
	{
		//TODO: metodo
		return null;
	}

	/**                                         A4
	 * Encontrar el camino de costo mínimo (menor tiempo promedio según Uber en la ruta) para 
	 * un viaje entre dos localizaciones geográficas de la ciudad ((lat,long) origen, (lat, long) destino),
	 * ingresados por el usuario. 
	 * 
	 * Mapa creado: camino  resultante  en  Google  Maps  (incluyendo  la ubicación de inicio y la ubicación de destino).
	 * 
	 * @param pLatOrigen
	 * @param pLongOrigen
	 * @param pLatDestino
	 * @param pLongDestino
	 * @return Array con los ids de los vertices a seguir.
	 */
	public int[] encontrarCaminoCostoMinimo(double pLatOrigen,double pLongOrigen, double pLatDestino, double pLongDestino)
	{
		//TODO : metodo
		return null;
	}


	/**											A5
	 * Determinar los nvértices con menor velocidadpromedio en la ciudad de Bogotá.
	 * Siendo la velocidad promedio de un vértice v,el promedio de las velocidadesde todos sus arcos.
	 * 
	 * @param pN nvértices con menor velocidadpromedio en la ciudad de Bogotá.
	 * 
	 * 
	 * @return Max priority queue con los n vertices con menor velocidad promedio.
	 * 
	 * Mapa creado: marca la localización de los n vértices resultantes en un mapa en Google Maps usando un color 1. 
	 * Destaque la componente conectada más grande (con más vértices) usando un color 2. 
	 * Para esta componente muestra sus vértices y sus arcos.
	 * 
	 * 
	 */
	public MaxPQ verticesConMenorVelocidad(int pN)
	{
		//TODO: metodo
		return null;
	}



	/**											A6
	 * Calcula  un  árbol  de  expansión  mínima  (MST)  con  criterio  distancia,  utilizando 
	 * el algoritmo de Prim, aplicado al componente conectado (subgrafo) más grande de la malla
	 * vial de Bogotá.
	 * @return
	 */
	public Iterable<K> distanciaMSTSubgrafoMayor()
	{
		//TODO: metodo
		return null;
	}












	//------------------------------------------------------------------------------
	//               Main
	//------------------------------------------------------------------------------
	public static void main(String[] args) throws IOException {
		MVCModelo modelo = new MVCModelo();
	}
}