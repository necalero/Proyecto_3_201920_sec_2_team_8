package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.management.Query;
import model.data_structures.Grafos.Arco;
import model.data_structures.Grafos.GrafoNoDirigido;

import model.data_structures.Grafos.Vertice;
import model.data_structures.Haversine;
import model.data_structures.Queue;

/**
 * Definicion del modelo del mundo
 *
 */

public class MVCModelo<K> {

	private GrafoNoDirigido grafo;

	@SuppressWarnings({ "static-access", "unchecked" })
	public MVCModelo() throws IOException
	{
		String txtArcos = "./data/bogota_arcos.txt";
		String txtVertices = "./data/bogota_vertices.txt";

		int cantidad = 0;
		int contador = 0;

		FileReader lector2 = new FileReader(txtVertices);
		BufferedReader leer2 = new BufferedReader(lector2);
		String lineaActual2 = leer2.readLine();
		while(lineaActual2 != "" && lineaActual2 != null)
		{
			String[] valores = lineaActual2.split(";");
			if(!(valores[0].equals("id"))) cantidad++;

			lineaActual2 = leer2.readLine();
		}
		int cantidadVertices = 0;

		grafo = new GrafoNoDirigido(cantidad);
		FileReader lector = new FileReader(txtVertices);
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
		System.out.println("Se crearon " + cantidadVertices + " vértices");

		int cantidadArcos = 0;
		FileReader lector3 = new FileReader(txtArcos);
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
		System.out.println("Se crearon " + cantidadArcos + " arcos");
		crearArchivoHTML();
	}
	
	public void calcularPesos()
	{
		for(Vertice vertice : grafo.darVertices())
		{
			
			if(vertice !=null)
			{
				Arco arcos[] = new Arco[vertice.darArcos().size()];
				arcos = (Arco[]) vertice.darArcos().toArray();
				for(Arco arco : arcos)
				{
					if(arco !=null)
					{
						double lat1 = vertice.darLatitud();
						double lat2= arco.darDestino().darLatitud();
						double lon1= vertice.darLongitud();
						double lon2= arco.darDestino().darLongitud();
						Haversine haversineC = new Haversine();
						double haversineDistance = haversineC.distance(lat1, lon1, lat2, lon2);
						
						double costoTiempo = 0;
						
						if(vertice.darMOVEMENT_ID()==arco.darDestino().darMOVEMENT_ID())
						{
							costoTiempo = 10;
						}
						else 
						{
							costoTiempo = 100;
						}
						vertice.setDistanciaArco(arco.darDestino(), haversineDistance);
						vertice.setTiempoArco(arco.darDestino(), costoTiempo);
					}
					
				}
				
			}
		}
		
	}
	
	
	public void persistirGrafoJSON()
	{
		
	}
	
	public void cargarGrafoJSON(String pRutaJSON)
	{
		
	}
	
	

	public void crearArchivoHTML() throws IOException
	{
		String ruta = "./data/mapa.html";
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
		writer.println("<meta name=\"view\" content=\"initial-scale=1.0, user-scalable=no\">");
		writer.println("<meta charset=\"utf-8\">");
		writer.println("<title>Mapa Proyecto 3</title>");
		writer.println("<style>");
		writer.println("#map {");
		writer.println("height: 100%;");
		writer.println("}");
		writer.println("html,");
		writer.println("body {");
		writer.println("height: 100%;");
		writer.println("margin: 0;");
		writer.println("padding: 0;");
		writer.println("}");
		writer.println("</style>");
		writer.println("</head>");
		
		
		writer.println("<body>");
		writer.println("<div id=\"map\"></div>");
		writer.println("<script>");
		writer.println("function initMap() {");
		writer.println("var map = new google.maps.Map(document.getElementById('map'), {");
		writer.println("zoom: 11,");
		writer.println("center: {");
		writer.println("lat: 4.65,");
		writer.println("lng: -74.1");
		writer.println("},");
		writer.println("mapTypeId: 'roadmap'");
		writer.println("});");
		writer.println("var line;");
		writer.println("var path;");
		for(Vertice vertice: grafo.darVertices())
		{
			if(vertice!=null)
			{
				LinkedList arcos = vertice.darArcos();
				Iterator it = arcos.iterator();
				while(it.hasNext())
				{
					Arco actual =  (Arco) it.next();
					if(it != null)
					{
						
						writer.println("line = [");
						writer.println("{");
						writer.println("lat: " + vertice.darLatitud() + ",");
						writer.println("lng: " + vertice.darLongitud());
						writer.println("},");
						writer.println("{");
						writer.println("lat: " + actual.darDestino().darLatitud()+ ",");
						writer.println("lng: " + actual.darDestino().darLongitud());
						writer.println("}");
						writer.println("];");
						writer.println("path = new google.maps.Polyline({");
						writer.println("path: line,");
						writer.println("strokeColor: '#FF0000',");
						writer.println("strokeWeight: 2");
						writer.println("});");
						writer.println("path.setMap(map);");
						contador++;
						System.out.println(contador);

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
		System.out.println("Carga completada");

	}



	public static void main(String[] args) throws IOException {
		MVCModelo modelo = new MVCModelo();
	}
}