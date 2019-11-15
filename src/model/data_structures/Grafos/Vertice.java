package model.data_structures.Grafos;

import java.util.LinkedList;

public class Vertice<I>
{
	private LinkedList<Arco> edgeTo;
	private I info;
	private boolean marked;
	private int id;

	public Vertice(I info, int id)
	{
		this.info = info;
		marked = false;
		this.id = id;
	}
	public void anadirArco(Vertice V, double pPeso)
	{
		edgeTo.add(new Arco(this, V, pPeso));
	}
	public void eliminarArco(Vertice V)
	{
		edgeTo.remove(buscarArcoA(V));
	}
	public I darInfo()
	{
		return info;
	}

	public void setInfo(I pInfo)
	{
		info = pInfo;
	}

	public double darPesoArco(Vertice V)
	{
		return (double) buscarArcoA(V).darPeso();
	}
	public void setPesoArco(Vertice V, double pPeso)
	{
		buscarArcoA(V).setPeso(pPeso);
	}

	public Arco buscarArcoA(Vertice V)
	{
		boolean seEncontro = false;
		Arco aRetornar = null;
		for(int i = 0; i < edgeTo.size()&&!seEncontro; i++)
		{
			if(edgeTo.get(i).darDestino().equals(V))
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
	public int[] adj()
	{
		int[] listaAdyacentes = new int[edgeTo.size()];
		for(int i = 0; i<edgeTo.size(); i++)
		{
			listaAdyacentes[i] = edgeTo.get(i).darDestino().darId();
		}
		return listaAdyacentes;
	}
	public int darId()
	{
		return id;
	}
}
