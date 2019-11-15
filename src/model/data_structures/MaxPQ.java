	package model.data_structures;
	import java.util.Comparator;
	import java.util.Iterator;
	import java.util.NoSuchElementException;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
public class MaxPQ <Key, Value> implements Iterable<Key> 
{
	private Key[] pqKeys;                    // store items at indices 1 to n
	private Value[] pqValues;
	private int n;                       // number of items on priority queue
	private Comparator<Key> comparator;  // optional comparator

	/**
	 * Initializes an empty priority queue with the given initial capacity.
	 *
	 * @param  initCapacity the initial capacity of this priority queue
	 */
	public MaxPQ(int initCapacity) {
		pqKeys = (Key[]) new Object[initCapacity + 1];
		pqValues = (Value[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * Initializes an empty priority queue.
	 */
	public MaxPQ() {
		this(1);
	}

	/**
	 * Initializes an empty priority queue with the given initial capacity,
	 * using the given comparator.
	 *
	 * @param  initCapacity the initial capacity of this priority queue
	 * @param  comparator the order in which to compare the keys
	 */
	public MaxPQ(int initCapacity, Comparator<Key> comparator) {
		this.comparator = comparator;
		pqKeys = (Key[]) new Object[initCapacity + 1];
		pqValues = (Value[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * Initializes an empty priority queue using the given comparator.
	 *
	 * @param  comparator the order in which to compare the keys
	 */
	public MaxPQ(Comparator<Key> comparator) {
		this(1, comparator);
	}

	/**
	 * Initializes a priority queue from the array of keys.
	 * Takes time proportional to the number of keys, using sink-based heap construction.
	 *
	 * @param  keys the array of keys
	 */
	public MaxPQ(Key[] keys, Value[] values)
	{
		n = keys.length;
		pqKeys = (Key[]) new Object[keys.length + 1];
		pqValues = (Value[]) new Object[keys.length + 1];
		for (int i = 0; i < n; i++)
		{
			pqKeys[i+1] = keys[i];
			pqValues[i+1] = values[i];
		}
			
		for (int k = n/2; k >= 1; k--)
			sink(k);
		assert isMaxHeap();
	}
	
	/**
	 * Crea tiempos de viaje con los datos suministrados y los carga a la cola de prioridad entregada.
	 * @param datos. Arreglo de enteros con los tiempos a crear.
	 * @param cola. Cola de prioridad en donde se van a cargar los datos.
	 */
	private void cargarDatos(int[] datos,MaxPQ<Key,Integer> cola)
	{
		for (int tiempo: datos)
		{
			cola.insert(null, tiempo);
		}
	}


	/**
	 * Returns true if this priority queue is empty.
	 *
	 * @return {@code true} if this priority queue is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns a largest key on this priority queue.
	 *
	 * @return a largest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public Key max() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		return pqKeys[1];
	}
	public Value maxValue() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		return pqValues[1];
	}
	
	public String[] maxValues(int N)
	{
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		else
		{
			String[] valores = new String[N];
			for(int i = 0; i<N; i++)
			{
				valores[i]=pqValues[i+1].toString();
			}
			return valores;
		}
	}
	// helper function to double the size of the heap array
	private void resize(int capacity) {
		assert capacity > n;
		Key[] temp = (Key[]) new Object[capacity];
		Value[] tempVal = (Value[]) new Object[capacity];
		for (int i = 1; i <= n; i++) {
			temp[i] = pqKeys[i];
			tempVal[i] = pqValues[i];
		}
		pqKeys = temp;
		pqValues = tempVal;
	}


	/**
	 * Adds a new key to this priority queue.
	 *
	 * @param  x the new key to add to this priority queue
	 */
	public void insert(Key x, Value y) {

		// double size of array if necessary
		if (n == pqKeys.length - 1) resize(2 * pqKeys.length);

		// add x, and percolate it up to maintain heap invariant
		pqKeys[++n] = x;
		pqValues[++n] = y;
		swim(n);
		assert isMaxHeap();
	}

	/**
	 * Removes and returns a largest key on this priority queue.
	 *
	 * @return a largest key on this priority queue
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public Key delMax() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		Key max = pqKeys[1];
		exch(1, n--);
		sink(1);
		pqKeys[n+1] = null;     // to avoid loitering and help with garbage collection
		pqValues[n+1] = null; 
		if ((n > 0) && (n == (pqKeys.length - 1) / 4)) resize(pqKeys.length / 2);
		assert isMaxHeap();
		return max;
	}
	
	public boolean contains(Value x)
	{
		boolean contiene = false;
		if(buscarIndiceValor(x)>=0)
			contiene = true;
		return contiene;
	}
	
	public void setPriority(Key y, Value x) 
	{
		int  i = buscarIndiceValor(x);
		pqKeys[i] = y;
		
	}
	
	public Key darPrioridad(Value x)
	{
		return pqKeys[buscarIndiceValor(x)];
	}
	
	public int buscarIndiceValor(Value x)
	{
		int valor = -1;
		boolean seEncontro = false;
		Iterator<Key> llaves = iterator();
		int i = 1;
		while(llaves.hasNext()&&!seEncontro)
		{
			if(pqValues[i].equals(x));
			{
				seEncontro=true;
				valor = i;
			}
			i++;
			llaves.next();
		}
		return valor;
	}
	
	public Value[] darValues()
	{
		return pqValues;
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= n) {
			int j = 2*k;
			if (j < n && less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<Key>) pqKeys[i]).compareTo(pqKeys[j]) < 0;
		}
		else {
			return comparator.compare(pqKeys[i], pqKeys[j]) < 0;
		}
	}

	private void exch(int i, int j) {
		Key swap = pqKeys[i];
		pqKeys[i] = pqKeys[j];
		pqKeys[j] = swap;
		Value swapVal = pqValues[i];
		pqValues[i] = pqValues[j];
		pqValues[j] = swapVal;
	}

	// is pq[1..n] a max heap?
	private boolean isMaxHeap() {
		for (int i = 1; i <= n; i++) {
			if (pqKeys[i] == null) return false;
		}
		for (int i = n+1; i < pqKeys.length; i++) {
			if (pqKeys[i] != null) return false;
		}
		if (pqKeys[0] != null) return false;
		return isMaxHeapOrdered(1);
	}

	// is subtree of pq[1..n] rooted at k a max heap?
	private boolean isMaxHeapOrdered(int k) {
		if (k > n) return true;
		int left = 2*k;
		int right = 2*k + 1;
		if (left  <= n && less(k, left))  return false;
		if (right <= n && less(k, right)) return false;
		return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
	}
	
	


	/***************************************************************************
	 * Iterator.
	 ***************************************************************************/

	/**
	 * Returns an iterator that iterates over the keys on this priority queue
	 * in descending order.
	 * The iterator doesn't implement {@code remove()} since it's optional.
	 *
	 * @return an iterator that iterates over the keys in descending order
	 */
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Key> {

		// create a new pq
		private MaxPQ<Key, Value> copy;

		// add all items to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			if (comparator == null) copy = new MaxPQ<Key, Value>(size());
			else                    copy = new MaxPQ<Key, Value>(size(), comparator);
			for (int i = 1; i <= n; i++)
				copy.insert(pqKeys[i],pqValues[i]);
		}

		public boolean hasNext()  { return !copy.isEmpty();                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Key next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMax();
		}
	}


}

