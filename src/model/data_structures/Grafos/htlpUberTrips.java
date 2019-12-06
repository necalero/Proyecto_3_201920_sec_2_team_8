package model.data_structures.Grafos;

import model.data_structures.UBERTrip;
import model.logic.NoExisteException;

public class htlpUberTrips<K,V>
{


	private int capacidad;
	private int cantKeys;
	private Integer[] keys;
	private UBERTrip[] data;

	public htlpUberTrips(int m)
	{
		capacidad = m;
		cantKeys = 0;
		keys = new Integer[m];
		data = new UBERTrip[m];
		for(int j = 0; j<capacidad; j++)
		{
			keys[j]= null;
			data[j]= null;
		}
	}
	
	public htlpUberTrips()
	{
		capacidad = 11;
		cantKeys = 0;
		keys = new Integer[11];
		data = new UBERTrip[11];
		
		for(int j = 0; j<capacidad; j++)
		{
			keys[j]= null;
			data[j]= null;
		}
		
	}

	public int hash(K key)
	{
		int hash = (key.hashCode()&0x7fffffff)%capacidad;
		return hash;
	}


	
	public void put(K Key, UBERTrip pUberTrip)
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
					data[i] = pUberTrip;
					return;
				}
			}
			keys[i] = (Integer) Key;
			data[i] = pUberTrip;
			cantKeys++;


		}
	}

	/**
	 * Retorna el vertice perteneciente a la llave K.
	 * @param Key
	 * @return
	 */
	public V get(K Key)
	{
		for(int i = hash(Key);keys[i] != null; i = (i+1)%capacidad )
		{
			if(keys[i].equals(Key))
			{
				return (V) data[i];
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
			UBERTrip dataChange = (UBERTrip) data[i];
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
		htlpUberTrips<K, V> t;
		t = new htlpUberTrips(cap);
		for (int i = 0; i < capacidad ; i++)
		{
			if(keys[i] != null)
			{
				K llave = (K) keys[i];
				UBERTrip valor = (UBERTrip) data[i];
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

	public UBERTrip[] darData()
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
