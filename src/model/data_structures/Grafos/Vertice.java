package model.data_structures.Grafos;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertice
{
	private LinkedList<Arco> edgeTo;
	private int id;
	private double lon;
	private double lat;
	private int MOVEMENT_ID;
	private boolean marked;
	private int componenteConectada;
	

	public Vertice(int pId, double plon, double plat, int mov_id)
	{
		lon = plon;
		lat = plat;
		MOVEMENT_ID = mov_id;
		marked = false;
		id = pId;
		edgeTo = new LinkedList<>();
		componenteConectada = -1;
	}
	
	public Vertice(int pId, double plon, double plat, int mov_id, boolean pMarked, LinkedList pEdge)
	{
		lon = plon;
		lat = plat;
		MOVEMENT_ID = mov_id;
		marked = pMarked;
		id = pId;
		edgeTo = pEdge;
		
	}
	public void setComponenteConectada(int n)
	{
		componenteConectada = n;
	}
	public int darComponenteConectada()
	{
		return componenteConectada;
	}
	
	public void anadirArco(int pVId, double pDistancia, double pTiempo)
	{
		Arco arco = new Arco(id, pVId, pDistancia, pTiempo,false);
		edgeTo.add(arco);
	}
	public void eliminarArco(int pVId)
	{
		edgeTo.remove(buscarArcoA(pVId));
	}
	public int darId()
	{
		return id;
	}
	public double darLongitud()
	{
		return lon;
	}
	public double darLatitud()
	{
		return lat;
	}
	public int darMOVEMENT_ID()
	{
		return MOVEMENT_ID;
	}
	public double darPesoDistancia(int pVId)
	{
		return (double) buscarArcoA(pVId).darDistancia();
	}
	public double darPesoTiempo(int pVId)
	{
		return (double) buscarArcoA(pVId).darTiempo();
	}
	public double darPesoVelocidad(int pVId)
	{
		return (double) buscarArcoA(pVId).darVelocidad();
	}
	
	public void setInfo(int pId, double plon, double plat, int pMovId)
	{
		id = pId;
		lon = plon;
		lat = plat;
		MOVEMENT_ID = pMovId;
	}
	
	public void setDistanciaArco(int pIDV, double pDistancia)
	{
		buscarArcoA(pIDV).setDistancia(pDistancia);
		buscarArcoA(pIDV).actualizarVelocidad();
	}
	public void setTiempoArco(int pIDV, double pTiempo)
	{
		buscarArcoA(pIDV).setTiempo(pTiempo);
		buscarArcoA(pIDV).actualizarVelocidad();
	}
	
	

	public Arco buscarArcoA(int pIDV)
	{
		boolean seEncontro = false;
		Arco aRetornar = null;
		for(int i = 0; i < edgeTo.size()&&!seEncontro; i++)
		{
			if(edgeTo.get(i).darIdDestino()==pIDV)
			{
				aRetornar = edgeTo.get(i);
			}
		}
		return aRetornar;
	}

	public void desmarcar()
	{
		marked = false;
	}
	public void marcar()
	{
		marked = true;
	}
	public boolean isMarked()
	{
		return marked;
	}
	/**
	 * Retorna la lista de IDs a los que es adyacente el vertice.
	 * @return
	 */
	public int[] adj()
	{
		int[] listaAdyacentes = new int[edgeTo.size()];
		for(int i = 0; i<edgeTo.size(); i++)
		{
			listaAdyacentes[i] = edgeTo.get(i).darIdDestino();
		}
		return listaAdyacentes;
	}
	
	public LinkedList darArcos()
	{
		return edgeTo;
	}
	
	public Arco[] darArcosD()
	{
		Arco[] arcos = new Arco[edgeTo.size()];
		Iterator it = edgeTo.iterator();
		int i = 0;
		while(it.hasNext())
		{
			
			Arco a = (Arco) it.next();
			
			arcos[i] = a;
			
			i++;
		}
		return arcos;
	}
	
	
	public void desmarcarTodosArcos()
	{
		Iterator it = edgeTo.iterator();
		
		
		while(it.hasNext())
		{
			Arco actual =  (Arco) it.next();
			actual.desmarcar();
		}
	}
	
}
