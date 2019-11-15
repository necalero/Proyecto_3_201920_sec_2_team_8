package model.data_structures;



import java.io.Serializable;

import model.logic.NoExisteException;



public class DoublyLinkedList<r> implements Serializable
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	/**
	 * Constante para la serializaci�n
	 */
	private static final long serialVersionUID = 1L;

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Cabeza de la lista encadenada
	 */
	private Nodo<r> primerNodo;

	/**
	 * �ltimo elemento de la lista encadenada
	 */
	private Nodo<r> ultimoNodo;

	private int size;


	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la lista vac�a. <br>
	 * <b>post: </b> Se construy� una lista vac�a.
	 */
	public DoublyLinkedList( )
	{
		primerNodo = null;
		ultimoNodo = null;
		size = 0;

	}

	// -----------------------------------------------------------------
	// Metodos
	// -----------------------------------------------------------------

	/**
	 * Inserta el elemento en la cabeza (inicio) de la lista. <br>
	 * <b>post: </b> Se insert� el elemento en la cabeza de la lista. La cantidad de elementos aumenta <br>
	 * En caso que la lista no sea vac�a, el nuevo elemento queda como el primero. Si la lista es vac�a el nuevo elemento es el primero y el �ltimo<br>
	 * @param nodo Nodo a insertar
	 * @return 
	 */
	public void anadirPrimero(r item) 
	{
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Nodo<r> nodo = new Nodo(item);	
		if( primerNodo == null)
		{
			primerNodo = nodo;
			ultimoNodo = nodo;
			
		}
		else if(primerNodo != null)
		{
			anadirAntes(primerNodo, nodo);

			primerNodo = nodo;
			
		}
		

	}

	/**
	 * Inserta el elemento en la cola (final) de la lista. <br>
	 * <b>post: </b> Se insert� el elemento en la cola de la lista. La cantidad de elementos aumenta <br>
	 * En caso que la lista no sea vac�a, el nuevo elemento queda como el �ltimo. Si la lista es vac�a el nuevo elemento es el primero y el �ltimo<br>
	 * @param elemento Elemento a insertar<br>
	 */
	public void anadirUltimo(r item)
	{
		Nodo<r> nodo = new Nodo<r>( item );
		if( primerNodo == null )
		{
			primerNodo = nodo;
			ultimoNodo = nodo;
		}
		else
		{
			anadirDespues(ultimoNodo, nodo);
			ultimoNodo = nodo;
		}
	}

	@SuppressWarnings("unchecked")
	public void anadirDespues(Nodo nodoPrevio, Nodo nodoNuevo)
	{
		if(nodoPrevio.darSiguiente()!=null)
		{
			@SuppressWarnings("rawtypes")
			Nodo aux = nodoPrevio.darSiguiente();
			nodoNuevo.cambiarSiguiente(aux);
			aux.cambiarAnterior(nodoNuevo);
			nodoPrevio.cambiarSiguiente(nodoNuevo);
			nodoNuevo.cambiarAnterior(nodoPrevio);
		}
		else
		{
			nodoPrevio.cambiarSiguiente(nodoNuevo);
			nodoNuevo.cambiarAnterior(nodoPrevio);
		}
		size++;
	}

	@SuppressWarnings("unchecked")
	public void anadirAntes(Nodo nodoPosterior, Nodo nodoNuevo)
	{
		if(nodoPosterior.darAnterior()!=null)
		{
			Nodo aux = nodoPosterior.darAnterior();
			nodoNuevo.cambiarAnterior(aux);
			aux.cambiarSiguiente(nodoNuevo);
			nodoPosterior.cambiarAnterior(nodoNuevo);
			nodoNuevo.cambiarSiguiente(nodoPosterior);
		}
		else
		{
			nodoPosterior.cambiarAnterior(nodoNuevo);
			nodoNuevo.cambiarSiguiente(nodoPosterior);
		}
		size++;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void anadir(r item)
	{
		Nodo nuevoNodo = new Nodo<r>(item);
		if(primerNodo==null)
		{
			primerNodo = nuevoNodo;
			ultimoNodo = nuevoNodo;
		}
		else
		{
			Nodo actual = primerNodo;
			boolean seAnadio = false;
			while(actual!=null&&!seAnadio)
			{
				if(actual.darSiguiente()==null)
				{
					anadirDespues(actual, nuevoNodo);
					seAnadio= true;
				}
			}
		}
	}


	/**
	 * Retorna el primer nodo de la lista. <br>
	 * <b>post: </b> Se retorn� el primer nodo de la lista.
	 * @return Primer nodo de la lista. Null en caso que la lista sea vac�a.
	 */
	public Nodo<r> darPrimero( )
	{
		return primerNodo;
	}

	/**
	 * Retorna el �ltimo nodo de la lista. <br>
	 * <b>post: </b> Se retorn� el �ltimo nodo de la lista.
	 * @return �ltimo nodo de la lista. Null en caso que la lista sea vac�a.
	 */
	public Nodo<r> darUltimo( )
	{
		return ultimoNodo;
	}

	public int darSize()
	{
		return size;
	}


	/**
	 * Retorna el nodo de la lista que contiene el item proporcionado. <br>
	 * @return nodo de la lista que contiene el item proporcionado. Null en caso de que no se haya encontrado.
	 */
	public Nodo<r> buscarNodo(r item)
	{
		Nodo actual = primerNodo;
		while(actual!=null)
		{
			if(actual.darItem().equals(item)) return actual;
			else actual = actual.darSiguiente();
		}
		return null;
	}

	/**
	 * Eval�a si la lista contiene el elemento que se pasa por par�metro<br>
	 * @param modelo Modelo del elemento a buscar.<br>
	 * @return True en caso que el elemento dado exista en la lista, false de lo contrario.<br>
	 */
	public boolean contiene( r item )
	{
		return buscarNodo( item ) != null;
	}




	/**
	 * Elimina el nodo de la lista encadenada. <br>
	 * <b>post: </b> Se elimin� el nodo especificado de la lista. La cantidad de elementos de la lista se reduce.<br>
	 * @param nodo Nodo a ser eliminado de la lista<br>
	 * @throws NoExisteException El nodo especificado no pertenece a la lista<br>
	 */
	public void eliminar( Nodo<r> nodo ) throws NoExisteException
	{
		if( buscarNodo( nodo.darItem() ) == null ) throw new NoExisteException( "El nodo especificado no pertenece a la lista" );
		else if( primerNodo == nodo )
		{
			if(primerNodo.darSiguiente()!=null) 
			{
				primerNodo = primerNodo.darSiguiente();
				primerNodo.cambiarAnterior(null);
			}
			else primerNodo = null;
		}
		else if(ultimoNodo == nodo ) 
		{
			Nodo aux = ultimoNodo.darAnterior();
			aux.cambiarSiguiente(null);
			ultimoNodo = aux;
		}
		else
		{
			Nodo<r> y = buscarNodo(nodo.darItem());
			Nodo<r> x = y.darSiguiente();
			Nodo<r> z = y.darAnterior();

			x.cambiarSiguiente(z);
			z.cambiarAnterior(x);
		}
		size--;

	}

	/**
	 * Elimina el primer nodo (cabeza) de la lista y devuelve el elemento que conten�a. <br>
	 * <b>post: </b> Se elimin� el primer nodo de la lista.
	 */
	public void eliminarPrimero( )
	{
		if(primerNodo!=null)
		{
			if( primerNodo.darSiguiente() == null )
			{
				vaciar();
			}
			else
			{
				Nodo<r> x = primerNodo.darSiguiente();
				primerNodo = x;
				x.cambiarAnterior(null);

			}
			size--;
		}


	}

	/**
	 * Elimina el ultimo nodo y devuelve el elemento que conten�a. <br>
	 * <b>post: </b> Se elimin� el �ltimo nodo de la lista. Se retorn� el elemento contenido en el nodo eliminado. La cantidad de elementos se reduce en uno. Si la lista no
	 * tiene nodos se retorna null.
	 * @return Elemento del nodo eliminado
	 */
	public void eliminarUltimo( )
	{
		if(ultimoNodo!=null)
		{
			ultimoNodo = ultimoNodo.darAnterior();
			ultimoNodo.cambiarSiguiente(null);
			size--;
		}
	}




	/**
	 * Elimina todos los elementos de la lista.<br>
	 * <b>post: </b> La lista ahora es vac�a. primero = null, ultimo=null, numElems = 0<br>
	 */
	public void vaciar( )
	{
		primerNodo = null;
		ultimoNodo = null;
		size = 0;

	}




}