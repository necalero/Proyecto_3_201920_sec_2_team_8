package model.data_structures.Grafos;

import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GrafoNoDirigido<K, V>
{

	private int numVertices;
	private int numArcos;
	private HTLPGraphs vertices;

	/**
	 * Crea un grafo No dirigido de tamaño n vértices y sin arcos
	 * @param n Numero de Vertices
	 */
	public GrafoNoDirigido(int n) {
		numVertices = n;
		vertices = new HTLPGraphs(n);

	}
	public GrafoNoDirigido(int pNumVertices, int pNumArcos, HTLPGraphs pVertices) {
		numVertices = pNumVertices;
		numArcos = pNumArcos;
		vertices = pVertices;

	}
	public GrafoNoDirigido() 
	{
		numVertices = 0;
		vertices = new HTLPGraphs();
	}


	/**
	 * Número de vértices
	 * @return
	 */
	public int V()
	{
		return numVertices;
	}

	/**
	 * Número de arcos. Cada arco No dirigido debe contarse una única vez.
	 * @return
	 */
	public int E()
	{
		return numArcos;
	}

	/**
	 * Adiciona el arco No dirigido entre el vértice IdVertexIni y el vértice IdVertexFin.
	 * El arco tiene el costo cost
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addEdge(int source, int destination, double distancia, double tiempo ) 
	{
		
		Vertice V1 = (Vertice) vertices.get(source);
		Vertice V2 = (Vertice) vertices.get(destination);
		if(V1!=null&&V2!=null)
		{
			if(V1.buscarArcoA(destination)==null&&V2.buscarArcoA(source)==null)
			{
				V1.anadirArco(destination, distancia, tiempo);
				V2.anadirArco(source, distancia, tiempo);
				numArcos++;
			}
		}
		
		

	}

	public Vertice getVertex(int id)
	{
		return (Vertice) vertices.get((int) id);
	}
	/**
	 * Obtener la información de un vértice en formato de arreglo [id, lon, lat, mov_id]. Si el vértice no existe retorna null. 
	 * @param idVertex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double[] getInfoVertex(int idVertex)
	{
		double aRetornar[] = new double[4];
		aRetornar[0] = ((Vertice) vertices.get(idVertex)).darId();
		aRetornar[1] = ((Vertice) vertices.get(idVertex)).darLongitud();
		aRetornar[2] = ((Vertice) vertices.get(idVertex)).darLatitud();
		aRetornar[3] = ((Vertice) vertices.get(idVertex)).darMOVEMENT_ID();
		return aRetornar;
	}

	/**
	 * Modificar la información del vértice idVertex
	 * @param idVertex
	 * @param infoVertex
	 */
	@SuppressWarnings("unchecked")
	public void setInfoVertex(int idVertex, int id, double lon, double lat, int mov_id)
	{
		((Vertice) vertices.get(idVertex)).setInfo(id, lon, lat, mov_id);;
	}

	/**
	 * Obtener los costos de un arco, si el arco no existe, retorna -1
	 * [Distancia, Tiempo, Velocidad]
	 * @param idVertexIni
	 * @param idVertexFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double[] getCostArc(int idVertexIni, int idVertexFin)
	{
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin = (Vertice) vertices.get(idVertexFin);
		double aRetornar[] = new double[3];
		aRetornar[0] = ini.darPesoDistancia(idVertexFin);
		aRetornar[1] = ini.darPesoTiempo(idVertexFin);
		aRetornar[2] = ini.darPesoVelocidad(idVertexFin);
		return aRetornar;
	}

	/**
	 * Modificar el costo del arco entre los vértices idVertexIni e idVertexFin
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	@SuppressWarnings("unchecked")
	public void setCostEdge(int idVertexIni, int idVertexFin, double Tiempo, double Distancia)
	{
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin =  (Vertice) vertices.get(idVertexFin);

		ini.setDistanciaArco(idVertexFin, Distancia);
		ini.setTiempoArco(idVertexFin, Tiempo);
		fin.setDistanciaArco(idVertexIni, Distancia);
		fin.setTiempoArco(idVertexIni, Tiempo);
	}

	/**
	 * Adiciona un vértice con un Id único. El vértice tiene la información InfoVertex.
	 * @param idVertex
	 * @param infoVertex
	 */
	@SuppressWarnings("unchecked")
	public void addVertex(int idVertex, double lat, double lon, int mov_id)
	{
		if(!contains(idVertex))
		{
			vertices.put(idVertex, new Vertice((int) idVertex, lon, lat, mov_id));
		}
		
		numVertices++;
	}
	
	public void addVertex(int idVertex, Vertice V)
	{
		if(!contains(idVertex)&&V!=null)
		{
			vertices.put(idVertex, V);
		}
		
		numVertices++;
		
	}
	
	
	public boolean contains(int pId)
	{
		boolean contains = false;
		if(getVertex(pId)!=null)
		{
			contains = true;
		}
		return contains;
	}
	
	

	/**
	 * Retorna los identificadores de los vértices adyacentes a idVertex.
	 * @param idVertex
	 * @return
	 */
	public int[] adj(int idVertex)
	{
		Vertice v = getVertex(idVertex);
		return v.adj();
	}

	/**
	 * Desmarca todos los vértices del grafo
	 */
	public void uncheck()
	{
		Vertice[] todos = vertices.darData();
		for(Vertice vertice : todos)
		{
			if(vertice!=null)
			{
				vertice.desmarcar();
				vertice.setComponenteConectada(-1);
			}
			
		}
	}


	/**
	 * Ejecuta la búsqueda de profundidad sobre el grafo con el vértice s como origen.
	 * Los vértices resultado de la búsqueda quedan marcados y deben tener información 
	 * que pertenecen a una misma componente conectada.
	 * @param s
	 */
	//Caso base
	public void DepthFirstSearch(int vID, int cc)   
	{        
		((Vertice) vertices.get(vID)).marcar();
		int i = 0;
		i += dfs(vID, cc);
	}   
	//Caso recursivo
	@SuppressWarnings("unchecked")
	private int dfs(int vID, int cc)   
	{      
		int cantMarcada = 0;
		Vertice actual = vertices.get(vID);
		actual.marcar(); 
		actual.setComponenteConectada(cc);
		cantMarcada++;   
		
		int[] aExplorar = adj(vID);
		

		for (int w : aExplorar)
		{
			Vertice adyActual = (Vertice) vertices.get(w);
			if (!adyActual.isMarked())
			{
				cantMarcada+=dfs(w, cc); 
				
			}
		}
		return cantMarcada;

	}

	/**
	 * Obtiene la cantidad de componentes conectados del grafo. Cada vértice debe quedar
	 * marcado y debe reconocer a cuál componente conectada pertenece. En caso de que el
	 * grafo esté vacío, retorna 0.
	 * @return
	 */
	public int cc()
	{
		Vertice todos[] = vertices.darData();
		int cantidad = 0;
		int cc = 1;
		for(Vertice v : todos)
		{
			if(v!=null)
			{
				if(!v.isMarked())
				{
					cantidad++;
					DepthFirstSearch(v.darId(),cc);
					cc++;
				}
			}
			
			
		}
		uncheck();
		return cantidad;
	}

	/**
	 * Obtiene los vértices alcanzados a partir del vértice idVertex después de la ejecución
	 * de los metodos dfs(K) y cc().
	 * @param idVertex
	 * @return
	 */
	public GrafoNoDirigido getCC(int idVertex)
	{
		DepthFirstSearch(idVertex, -2);
		Vertice[] todos = vertices.darData();
		GrafoNoDirigido cc = new GrafoNoDirigido<>();
		for(Vertice vertice: todos)
		{
			if(vertice!=null)
			{
				if(vertice.isMarked())
				{
					cc.addVertex(vertice.darId(), vertice);
					int[] posiblesArcos = vertice.adj();
					for(int i = 0; i<posiblesArcos.length;i++)
					{
						Vertice posible = getVertex(posiblesArcos[i]);
						if(posible!=null)
						{
							if(posible.isMarked()&&posible.darComponenteConectada()==-2)
							{
								cc.addEdge(vertice.darId(), posible.darId(), vertice.buscarArcoA(posible.darId()).darDistancia(), vertice.buscarArcoA(posible.darId()).darTiempo());
							}
						}
					}
				}
			}
			
		}
		uncheck();
		return cc;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vertice[] darVertices()
	{
		return vertices.darData();
	}
	
	/**
	 * 
	 * @param idVerticeOrigen
	 * @param idVerticeDestino
	 * @return
	 */
	public GrafoNoDirigido grafoMenorDistanciaA(int idVerticeOrigen, int idVerticeDestino)
	{
		DijkstraSP sp = new DijkstraSP(this, idVerticeOrigen, "distancia");
		GrafoNoDirigido aRetornar = sp.grafoDistanciaMinima(idVerticeDestino, this);
		return aRetornar;
	}
	public Iterable caminoMenorDistanciaA(int idVerticeOrigen, int idVerticeDestino)
	{
		DijkstraSP sp = new DijkstraSP(this, idVerticeOrigen, "distancia");
		Iterable aRetornar = sp.pathTo(idVerticeDestino, this);
		return aRetornar;
	}
	
	/**
	 * 
	 * @param idVerticeOrigen
	 * @param idVerticeDestino
	 * @return
	 */
	public GrafoNoDirigido grafoMenorTiempoA(int idVerticeOrigen, int idVerticeDestino)
	{
		DijkstraSP sp = new DijkstraSP(this, idVerticeOrigen, "tiempo");
		GrafoNoDirigido aRetornar = sp.grafoDistanciaMinima(idVerticeDestino, this);
		return aRetornar;
	}
	public Iterable caminoMenorTiempoA(int idVerticeOrigen, int idVerticeDestino)
	{
		DijkstraSP sp = new DijkstraSP(this, idVerticeOrigen, "tiempo");
		Iterable aRetornar = sp.pathTo(idVerticeDestino, this);
		return aRetornar;
	}
	
	
	/**
	 * 
	 */
	public void desmarcarTodosLosArcos()
	{
		for(Vertice vertice : darVertices())
		{
			if(vertice!=null)
			vertice.desmarcarTodosArcos();
		}
	}
	
	
	
	

	

}