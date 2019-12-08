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
	 * Crea un grafo No dirigido de tama�o n v�rtices y sin arcos
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
	 * N�mero de v�rtices
	 * @return
	 */
	public int V()
	{
		return numVertices;
	}

	/**
	 * N�mero de arcos. Cada arco No dirigido debe contarse una �nica vez.
	 * @return
	 */
	public int E()
	{
		return numArcos;
	}

	/**
	 * Adiciona el arco No dirigido entre el v�rtice IdVertexIni y el v�rtice IdVertexFin.
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
	 * Obtener la informaci�n de un v�rtice en formato de arreglo [id, lon, lat, mov_id]. Si el v�rtice no existe retorna null. 
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
	 * Modificar la informaci�n del v�rtice idVertex
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
	 * Modificar el costo del arco entre los v�rtices idVertexIni e idVertexFin
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
	 * Adiciona un v�rtice con un Id �nico. El v�rtice tiene la informaci�n InfoVertex.
	 * @param idVertex
	 * @param infoVertex
	 */
	@SuppressWarnings("unchecked")
	public void addVertex(int idVertex, double lat, double lon, int mov_id)
	{
		if(!contains(idVertex))
		vertices.put(idVertex, new Vertice((int) idVertex, lon, lat, mov_id));
	}
	
	public void addVertex(int idVertex, Vertice V)
	{
		if(!contains(idVertex)&&V!=null)
		{
			vertices.put(idVertex, V);
		}
		
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
	 * Retorna los identificadores de los v�rtices adyacentes a idVertex.
	 * @param idVertex
	 * @return
	 */
	public int[] adj(int idVertex)
	{
		Vertice v = getVertex(idVertex);
		return v.adj();
	}

	/**
	 * Desmarca todos los v�rtices del grafo
	 */
	public void uncheck()
	{
		Vertice[] todos = vertices.darData();
		for(Vertice vertice : todos)
		{
			vertice.desmarcar();
		}
	}


	/**
	 * Ejecuta la b�squeda de profundidad sobre el grafo con el v�rtice s como origen.
	 * Los v�rtices resultado de la b�squeda quedan marcados y deben tener informaci�n 
	 * que pertenecen a una misma componente conectada.
	 * @param s
	 */
	//Caso base
	public void DepthFirstSearch(int vID)   
	{        
		((Vertice) vertices.get(vID)).marcar();
		dfs(vID);
	}   
	//Caso recursivo
	@SuppressWarnings("unchecked")
	private int dfs(int vID)   
	{      
		int cantMarcada = 0;
		((Vertice) vertices.get(vID)).marcar();      
		cantMarcada++;   
		int[] list = adj(vID);

		for (int w : list)
		{
			Vertice adyActual = (Vertice) vertices.get(w);
			if (!adyActual.isMarked())
			{
				dfs(w);   
			}
		}
		return cantMarcada;

	}

	/**
	 * Obtiene la cantidad de componentes conectados del grafo. Cada v�rtice debe quedar
	 * marcado y debe reconocer a cu�l componente conectada pertenece. En caso de que el
	 * grafo est� vac�o, retorna 0.
	 * @return
	 */
	public int cc()
	{
		Vertice todos[] = vertices.darData();
		int cantidad = 0;
		for(Vertice v : todos)
		{
			if(v!=null)
			{
				if(!v.isMarked())
				{
					cantidad++;
					DepthFirstSearch(v.darId());
				}
			}
			
		}
		uncheck();
		return cantidad;
	}

	/**
	 * Obtiene los v�rtices alcanzados a partir del v�rtice idVertex despu�s de la ejecuci�n
	 * de los metodos dfs(K) y cc().
	 * @param idVertex
	 * @return
	 */
	public Iterable<K> getCC(int idVertex)
	{
		DepthFirstSearch(idVertex);
		Vertice[] todos = vertices.darData();
		HTLPGraphs ccDat = new HTLPGraphs(5);
		for(Vertice vertice: todos)
		{
			if(vertice.isMarked())
			{
				ccDat.put(((Integer)vertice.darId()),(Vertice) vertice);
			}
		}
		K[] ccL = (K[]) ccDat.darKeys();
		Iterable<K> cc = Arrays.asList(ccL);
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