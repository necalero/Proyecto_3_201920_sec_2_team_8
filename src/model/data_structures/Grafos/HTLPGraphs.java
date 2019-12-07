package model.data_structures.Grafos;


import model.logic.NoExisteException;

public class HTLPGraphs 
{
	private int capacidad;
	private int cantKeys;
	private Integer[] keys;
	private Vertice[] data;

	
	public HTLPGraphs(int m)
	{
		capacidad = m;
		cantKeys = 0;
		keys = new Integer[m];
		data = new Vertice[m];
		for(int j = 0; j<capacidad; j++)
		{
			keys[j]= null;
			data[j]= null;
		}
	}
	
	public HTLPGraphs()
	{
		capacidad = 11;
		cantKeys = 0;
		keys = new Integer[11];
		data = new Vertice[11];
		
		for(int j = 0; j<capacidad; j++)
		{
			keys[j]= null;
			data[j]= null;
		}
		
	}
	
	public HTLPGraphs(int pCapacidad, int pCantKeys, Integer[] pLlaves, Vertice[] pData)
	{
		capacidad = pCapacidad;
		cantKeys = pCantKeys;
		keys = pLlaves;
		data = pData;
	}

	public int hash(int pKey)
	{
		Integer key = pKey;
		int hash = (key.hashCode()&0x7fffffff)%capacidad;
		return hash;
	}

	public void put(int Key, int pId, double pLon, double pLat, int pMovId)
	{
		if(verificarCapacidadCarga())
		{
			rehash(capacidad*2);
		}
		else
		{
			int hash =  hash(Key);
			int i;
			for(i = hash;keys[i] != null; i = (i+1)%capacidad)
			{
				if(keys[i].equals(Key))
				{				
					data[i] = new Vertice((int) Key, pLon, pLat, pMovId);
					return;
				}
			}
			keys[i] = (Integer) Key;
			data[i] = new Vertice((int) Key, pLon, pLat, pMovId);
			cantKeys++;


		}
	}
	
	public void put(int Key, Vertice value)
	{
		if(verificarCapacidadCarga())
		{
			rehash(capacidad*2);
		}
		else
		{
			int hash =  hash(Key);
			int i;
			for(i = hash;keys[i] != null; i = (i+1)%capacidad)
			{
				if(keys[i].equals(Key))
				{				
					data[i] = value;
					return;
				}
			}
			keys[i] = (Integer) Key;
			data[i] = value;
			cantKeys++;


		}
	}

	/**
	 * Retorna el vertice perteneciente a la llave K.
	 * @param Key
	 * @return
	 */
	public Vertice get(int Key)
	{
		for(int i = hash(Key);keys[i] != null; i = (i+1)%capacidad )
		{
			if(keys[i].equals(Key))
			{
				return  data[i];
			}
		}

		return null;
	}


	public Vertice delete(int pKey) throws NoExisteException 
	{
		Integer key = pKey;
		if(!contains(key)) 
		{
			throw new NoExisteException("No existe el elemento a eliminar");
		}
		int i = hash(key);
		while (!key.equals(keys[i]))
		{
			i = (i + 1) % capacidad;
		}
		keys[i] = null;
		data[i] = null;
		i = (i+1)% capacidad;
		while(keys[i] != null)
		{
			Integer keyChange = keys[i];
			Vertice dataChange = (Vertice) data[i];
			keys[i] = null;
			data[i] = null;
			cantKeys--;
			put(keyChange, dataChange);
			i = (i + 1) % capacidad;
		}
		cantKeys--;
		return null;
	}



	private boolean contains(int Key) {
		for(int i = hash(Key);keys[i] != null; i = (i+1)%capacidad )
		{
			if(keys[i].equals(Key))
			{
				return true;
			}
		}
		return false;
	}



	@SuppressWarnings("unchecked")
	public void rehash(int cap)
	{
		HTLPGraphs t;
		t = new HTLPGraphs(cap);
		for (int i = 0; i < capacidad ; i++)
		{
			if(keys[i] != null)
			{
				int llave = keys[i];
				Vertice valor = (Vertice) data[i];
				t.put(llave, valor);
			}
		}
		keys = t.darKeys();
		data = t.darData();
		capacidad = t.darCapacidad(); 
	}

	public Integer[] darKeys()
	{
		return keys;
	}

	public Vertice[] darData()
	{
		
		return data;
	}

	public int darCapacidad()
	{
		return capacidad;
	}

	public boolean verificarCapacidadCarga()
	{
		double numKeysD = (double) cantKeys;
		double capacidadD = (double) capacidad;
		double factorCarga = numKeysD/capacidadD;
		if(factorCarga>0.75)
		{
			return true;
		}
		return false;
	}




}
