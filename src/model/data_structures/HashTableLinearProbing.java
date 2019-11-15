package model.data_structures;

import java.util.Iterator;

import model.logic.NoExisteException;

public class HashTableLinearProbing <T extends Comparable<Integer>,V extends Comparable<UBERTrip>>
{
	int capacidad;
	int cantKeys;
	Integer[] keys;
	Nodo[] data;

	public HashTableLinearProbing(int m)
	{
		capacidad = m;
		keys = new Integer[m];
		data = new Nodo[m];
	}

	public int hash(T key)
	{
		int hash = (key.hashCode()&0x7fffffff)%capacidad;
		return hash;
	}

	public void put(T Key,V  value)
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
					data[i] = new Nodo(value);
					return;
				}
			}
			keys[i] = (Integer) Key;
			data[i] = new Nodo(value);
			cantKeys++;


		}
	}


	public V get(T Key)
	{
		for(int i = hash(Key);keys[i] != null; i = (i+1)%capacidad )
		{
			if(keys[i].equals(Key))
			{
				return (V) data[i].darItem();
			}
		}

		return null;
	}


	public V delete(T Key) throws NoExisteException
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
			T keyChange = (T) keys[i];
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



	private boolean contains(T Key) {
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
		HashTableLinearProbing<T, V> t;
		t = new HashTableLinearProbing<>(cap);
		for (int i = 0; i < capacidad ; i++)
		{
			if(keys[i] != null)
			{
				T llave = (T) keys[i];
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
	
	public Nodo[] darData()
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
