package model.data_structures.Grafos;

import model.data_structures.UBERTrip;
import model.logic.NoExisteException;

public class HTLPGraphs <K ,V>
{

	private int capacidad;
	private int cantKeys;
	private Integer[] keys;
	private Vertice[] data;

	public HTLPGraphs(int m)
	{
		capacidad = m;
		keys = new Integer[m];
		data = new Vertice[m];
	}

	public int hash(K key)
	{
		int hash = (key.hashCode()&0x7fffffff)%capacidad;
		return hash;
	}

	public void put(K Key,V  value)
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
					data[i] = new Vertice(value);
					return;
				}
			}
			keys[i] = (Integer) Key;
			data[i] = new Vertice(value);
			cantKeys++;


		}
	}


	public V get(K Key)
	{
		for(int i = hash(Key);keys[i] != null; i = (i+1)%capacidad )
		{
			if(keys[i].equals(Key))
			{
				return (V) data[i].darInfo();
			}
		}

		return null;
	}


	public V delete(K Key) throws NoExisteException 
	{
		if(!contains(Key)) 
		{
			throw new NoExisteException("No existe el elemento a eliminar");
		}
		int i = hash(Key);
		while (!Key.equals(keys[i]))
		{
			i = (i + 1) % capacidad;
		}
		keys[i] = null;
		data[i] = null;
		i = (i+1)% capacidad;
		while(keys[i] != null)
		{
			K keyChange = (K) keys[i];
			V dataChange = (V) data[i];
			keys[i] = null;
			data[i] = null;
			cantKeys--;
			put(keyChange, dataChange);
			i = (i + 1) % capacidad;
		}
		cantKeys--;
		return null;
	}



	private boolean contains(K Key) {
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
		HTLPGraphs<K, V> t;
		t = new HTLPGraphs(cap);
		for (int i = 0; i < capacidad ; i++)
		{
			if(keys[i] != null)
			{
				K llave = (K) keys[i];
				V valor = (V) data[i];
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
