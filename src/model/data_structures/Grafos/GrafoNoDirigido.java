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
	 * Crea un grafo No dirigido de tamaño V vértices y sin arcos
	 * @param V
	 */
	public GrafoNoDirigido(int V) {
		numVertices = V;
		vertices = new HTLPGraphs<>(V);

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
	@SuppressWarnings("unchecked")
	public void addEdge(int source, int destination, int weight) 
	{
		((Vertice) vertices.get(source)).anadirArco((Vertice) vertices.get(destination),weight);
		((Vertice) vertices.get(destination)).anadirArco((Vertice) vertices.get(source),weight);

	}

	/**
	 * Obtener la información de un vértice. Si el vértice no existe retorna null
	 * @param idVertex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public V getInfoVertex(K idVertex)
	{
		return (V) ((Vertice) vertices.get(idVertex)).darInfo();
	}

	/**
	 * Modificar la información del vértice idVertex
	 * @param idVertex
	 * @param infoVertex
	 */
	@SuppressWarnings("unchecked")
	public void setInfoVertex(K idVertex, V infoVertex)
	{
		((Vertice) vertices.get(idVertex)).setInfo(infoVertex);
	}

	/**
	 * Obtener el costo de un arco, si el arco no existe, retorna -1
	 * @param idVertexIni
	 * @param idVertexFin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public double getCostArc(K idVertexIni, K idVertexFin)
	{
		return ((Vertice) vertices.get(idVertexIni)).darPesoArco((Vertice) vertices.get(idVertexFin));
	}

	/**
	 * Modificar el costo del arco entre los vértices idVertexIni e idVertexFin
	 * @param idVertexIni
	 * @param idVertexFin
	 * @param cost
	 */
	public void setCostArc(K idVertexIni, K idVertexFin, double cost)
	{
		((Vertice) vertices.get(idVertexIni)).setPesoArco((Vertice) idVertexFin,cost);
		((Vertice) vertices.get(idVertexFin)).setPesoArco((Vertice) idVertexIni,cost);
	}

	/**
	 * Adiciona un vértice con un Id único. El vértice tiene la información InfoVertex.
	 * @param idVertex
	 * @param infoVertex
	 */
	public void addVertex(K idVertex, V infoVertex)
	{
		vertices.put(idVertex, new Vertice(infoVertex,(int) idVertex));
	}

	/**
	 * Retorna los identificadores de los vértices adyacentes a idVertex.
	 * @param idVertex
	 * @return
	 */
	public Iterable <K> adj (K idVertex)
	{
		Iterable<int[]> lista = Arrays.asList(((Vertice) vertices.get(idVertex)).adj());
		return (Iterable<K>) lista;
	}

	/**
	 * Desmarca todos los vértices del grafo
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
	 * Ejecuta la búsqueda de profundidad sobre el grafo con el vértice s como origen.
	 * Los vértices resultado de la búsqueda quedan marcados y deben tener información 
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
	 * Obtiene la cantidad de componentes conectados del grafo. Cada vértice debe quedar
	 * marcado y debe reconocer a cuál componente conectada pertenece. En caso de que el
	 * grafo esté vacío, retorna 0.
	 * @return
	 */
	public int cc()
	{
		Vertice todos[] = vertices.darData();
		int cantidad = 0;
		for(Vertice v : todos)
		{
			if(!v.isMarked())
			{
				cantidad++;
				DepthFirstSearch(v);
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
				ccDat.put((K)((Integer)vertice.darId()),(V) vertice);
			}
		}
		K[] ccL = (K[]) ccDat.darKeys();
		Iterable<K> cc = Arrays.asList(ccL);
		return cc;
	}

	public Informacion getInfoVertex(Vertice darDestino) {
		// TODO Auto-generated method stub
		return null;
	}


	public Object darVertices() {
		// TODO Auto-generated method stub
		return null;
	}

}