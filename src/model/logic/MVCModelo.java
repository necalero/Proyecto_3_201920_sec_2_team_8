package model.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.management.Query;
import model.data_structures.Grafos.Arco;
import model.data_structures.Grafos.GrafoNoDirigido;
import model.data_structures.Grafos.Informacion;
import model.data_structures.Grafos.Interseccion;
import model.data_structures.Queue;

/**
 * Definicion del modelo del mundo
 *
 */

public class MVCModelo<K> {

	private GrafoNoDirigido<Integer,Informacion> grafo;

	public MVCModelo() throws IOException
	{
		String txtArcos = "./data/bogota_arcos.txt";
		String txtVertices = "./data/bogota_vertices.txt";
		Queue colaDeNodos = new Queue();
		int cantidad = 0;
		int contador = 0;

		FileReader lector2 = new FileReader(txtVertices);
		BufferedReader leer2 = new BufferedReader(lector2);
		String lineaActual2 = leer2.readLine();
		while(lineaActual2 != "" && lineaActual2 != null)
		{
			String[] valores = lineaActual2.split(";");
			if(valores[0].equals("id"))
			{

			}
			else
			{
				cantidad++;
			}
			lineaActual2 = leer2.readLine();
		}
		int cantidadVertices = 0;

		grafo = new GrafoNoDirigido<Integer,Informacion>(cantidad);
		FileReader lector = new FileReader(txtVertices);
		BufferedReader leer = new BufferedReader(lector);
		String lineaActual = leer.readLine();
		while(lineaActual != "" && lineaActual != null)
		{
			String[] valores = lineaActual.split(";");
			if(valores[0].equals("id"))
			{

			}
			else
			{
				int id = Integer.parseInt(valores[0]); 
				double longi = Double.parseDouble(valores[1]);
				double lati = Double.parseDouble(valores[2]);
				int mov = Integer.parseInt(valores[3]);
				Informacion info = new Informacion(lati, longi, mov);
				grafo.addVertex(id, info);
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
			String[] valores = lineaActual3.split(" ");
			for(int i=1 ; i<valores.length ; i++)
			{
				int val1 = Integer.parseInt(valores[0]);
				int val2 = Integer.parseInt(valores[i]);
				Informacion info1 = grafo.getInfoVertex(val1);
				Informacion info2 = grafo.getInfoVertex(val2);
				if(info1 != null && info2 != null)
				{
					double sin1 = Math.pow(Math.sin((info2.darLatitud()-info1.darLatitud())/2), 2);
					double cos1 = Math.cos(info1.darLatitud());
					double cos2 = Math.cos(info2.darLatitud());
					double sin2 = Math.pow((Math.sin((info2.darLongitud()-info1.darLongitud())/2)), 2);
					double interno = Math.asin(Math.sqrt(sin1+(cos1*cos2*sin2)));
					double cost = 2*6371*interno;
					grafo.setCostArc(val1, val2, cost);
					cantidadArcos++;
				}
			}

			lineaActual3 = leer3.readLine();
		}
		System.out.println("Se crearon " + cantidadArcos + " arcos");
		crearArchivo();
	}

	public void crearArchivo() throws IOException
	{
		String ruta = "./data/mapa.html";
		int contador = 0;
		PrintWriter writter = null;
		try
		{
			writter = new PrintWriter(ruta);
		}catch (Exception e) {
			e.printStackTrace();
		}
		writter.println("<html>");
		writter.println("<head>");
		writter.println("<meta name=\"view\" content=\"initial-scale=1.0, user-scalable=no\">");
		writter.println("<meta charset=\"utf-8\">");
		writter.println("<title>Mapa</title>");
		writter.println("<style>");
		writter.println("#map {");
		writter.println("height: 100%;");
		writter.println("}");
		writter.println("html,");
		writter.println("body {");
		writter.println("height: 100%;");
		writter.println("margin: 0;");
		writter.println("padding: 0;");
		writter.println("}");
		writter.println("</style>");
		writter.println("</head>");
		writter.println("<body>");
		writter.println("<div id=\"map\"></div>");
		writter.println("<script>");
		writter.println("function initMap() {");
		writter.println("var map = new google.maps.Map(document.getElementById('map'), {");
		writter.println("zoom: 4,");
		writter.println("center: {");
		writter.println("lat: 40.1835287,");
		writter.println("lng: -3.537393");
		writter.println("},");
		writter.println("mapTypeId: 'roadmap'");
		writter.println("});");
		writter.println("var line;");
		writter.println("var path;");
		for(Interseccion<Integer,Informacion> inter: grafo.darVertices())
		{
			for(Arco<Integer> arcos : inter.darArcos())
			{
				if(arcos != null)
				{
					Informacion info = (Informacion) grafo.getInfoVertex(arcos.darDestino());
					if(info != null)
					{
						Informacion informa = (Informacion) inter.darInfo();
						writter.println("line = [");
						writter.println("{");
						writter.println("lat: " + info.darLatitud() + ",");
						writter.println("lng: " + info.darLongitud());
						writter.println("},");
						writter.println("{");
						writter.println("lat: " + info.darLatitud()+ ",");
						writter.println("lng: " + info.darLongitud());
						writter.println("}");
						writter.println("];");
						writter.println("path = new google.maps.Polyline({");
						writter.println("path: line,");
						writter.println("strokeColor: '#FF0000',");
						writter.println("strokeWeight: 2");
						writter.println("});");
						writter.println("path.setMap(map);");
						contador++;
						System.out.println(contador);
					}
				}


			}

		}

		writter.println("}");
		writter.println("</script>");
		writter.println("<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=&callback=initMap\">");
		writter.println("</script>");
		writter.println("</body>");
		writter.println("</html>");
		writter.close();
		System.out.println("Carga completada");

	}



	public static void main(String[] args) throws IOException {
		MVCModelo modelo = new MVCModelo();
	}
}