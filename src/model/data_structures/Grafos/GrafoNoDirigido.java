package model.data_structures.Grafos;

import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

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
		vertices = new HTLPGraphs<>(n);

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
		if(V1.buscarArcoA(V2)==null&&V2.buscarArcoA(V1)==null)
		{
			V1.anadirArco(V2, distancia, tiempo);
			V2.anadirArco(V1, distancia, tiempo);
			numArcos++;
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
	public double[] getInfoVertex(K idVertex)
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
	public void setInfoVertex(K idVertex, int id, double lon, double lat, int mov_id)
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
	public double[] getCostArc(K idVertexIni, K idVertexFin)
	{
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin = (Vertice) vertices.get(idVertexFin);
		double aRetornar[] = new double[3];
		aRetornar[0] = ini.darPesoDistancia(fin);
		aRetornar[1] = ini.darPesoTiempo(fin);
		aRetornar[2] = ini.darPesoVelocidad(fin);
		return aRetornar;
	}

	/**
	 * Modificar el costo del arco entre los v�rtices idVertexIni e idVertexFin
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	@SuppressWarnings("unchecked")
	public void setCostEdge(K idVertexIni, K idVertexFin, double Tiempo, double Distancia)
	{
		Vertice ini = (Vertice) vertices.get(idVertexIni);
		Vertice fin =  (Vertice) vertices.get(idVertexFin);

		ini.setDistanciaArco(fin, Distancia);
		ini.setTiempoArco(fin, Tiempo);
		fin.setDistanciaArco(ini, Distancia);
		fin.setTiempoArco(ini, Tiempo);
	}

	/**
	 * Adiciona un v�rtice con un Id �nico. El v�rtice tiene la informaci�n InfoVertex.
	 * @param idVertex
	 * @param infoVertex
	 */
	@SuppressWarnings("unchecked")
	public void addVertex(K idVertex, double lat, double lon, int mov_id)
	{
		vertices.put(idVertex, new Vertice((int) idVertex, lon, lat, mov_id));
	}
	
	public void addVertex(K idVertex, Vertice V)
	{
		vertices.put(idVertex, V);
	}

	/**
	 * Retorna los identificadores de los v�rtices adyacentes a idVertex.
	 * @param idVertex
	 * @return
	 */
	public Iterable <K> adj (K idVertex)
	{
		Iterable<int[]> lista = Arrays.asList(((Vertice) vertices.get(idVertex)).adj());
		return (Iterable<K>) lista;
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
	public void DepthFirstSearch(Vertice s)   
	{        
		((Vertice) vertices.get(s)).marcar();
		dfs(s);
	}   
	//Caso recursivo
	@SuppressWarnings("unchecked")
	private int dfs(Vertice v)   
	{      
		int cantMarcada = 0;
		((Vertice) vertices.get(v)).marcar();      
		cantMarcada++;   
		Iterable<K> list = adj((K)((Integer) v.darId()));

		for (K w : list)
		{
			Vertice actual = (Vertice) vertices.get(w);
			if (!actual.isMarked())
			{
				dfs(actual);   
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
					DepthFirstSearch(v);
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
	public Iterable<K> getCC(K idVertex)
	{
		Vertice x = (Vertice) vertices.get(idVertex);
		DepthFirstSearch(x);
		Vertice[] todos = vertices.darData();
		HTLPGraphs<K, V> ccDat = new HTLPGraphs<>(5);
		for(Vertice vertice: todos)
		{
			if(vertice.isMarked())
			{
				ccDat.put((K)((Integer)vertice.darId()),(Vertice) vertice);
			}
		}
		K[] ccL = (K[]) ccDat.darKeys();
		Iterable<K> cc = Arrays.asList(ccL);
		return cc;
	}
	
	public Vertice[] darVertices()
	{
		return vertices.darData();
	}
	
	public void desmarcarTodosLosArcos()
	{
		for(Vertice vertice : darVertices())
		{
			if(vertice!=null)
			vertice.desmarcarTodosArcos();
		}
	}

	

}