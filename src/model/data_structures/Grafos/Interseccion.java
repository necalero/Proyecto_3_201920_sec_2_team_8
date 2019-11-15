package model.data_structures.Grafos;

public class Interseccion<K, V> {
	private K id;
	private V info;
	private int CantiArcos;
	private Arco<K>[] arcs;
	private boolean verificar;
	private Interseccion<K, V> conect;

	public Interseccion(K pid, V pinfo,Arco<K>[] parcos)
	{
		id = pid;
		info = pinfo;
		arcs = new Arco[6];
	}

	public int darCantidadArcos()
	{
		if(arcs == null)
		{
			return 0;
		}
		return CantiArcos;
	}

	public Arco<K>[] darArcos()
	{
		return arcs;
	}

	public void agregarArco(Arco i)
	{
		if(arcs == null)
		{
			arcs[0] = i;
			CantiArcos++;
		}
		else
		{
			for(int j = 1; j<6 ; j++)
			{
				if(arcs[j] == null)
				{
					arcs[j] = i;
				}
			}
			CantiArcos++;
		}
	}

	public K darId()
	{
		return id;
	}

	public void cambiarInformacion(V newInfo)
	{
		info = newInfo;
	}

	public void marcar()
	{
		verificar = true;
	}

	public void desmarcar()
	{
		verificar = false;
	}

	public V darInfo()
	{
		return info;
	}

	public boolean estaMarcado()
	{
		return verificar;
	}

	public void conectadoA(Interseccion a)
	{
		conect = a;
	}

	public Interseccion darConexion()
	{
		return conect;
	}
}