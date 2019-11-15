package model.data_structures.BSTRojoNegro;
import java.util.NoSuchElementException;
import model.data_structures.BSTRojoNegro.NodoBSTRojoNegro;
import model.data_structures.Queue;
public class ArbolesRYN<K extends Comparable<K>, V>
{
	private static final boolean RED   = true;
	private static final boolean BLACK = false;
	private NodoBSTRojoNegro root;     // root of the BST


	/**
	 * Initializes an empty symbol table.
	 */
	public ArbolesRYN() {
	}

	/***************************************************************************
	 *  Node helper methods.
	 ***************************************************************************/
	// is node x red; false if x is null ?
	private boolean isRed(NodoBSTRojoNegro x) 
	{
		if (x == null) return false;
		return x.darColor() == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int size(NodoBSTRojoNegro x) {
		if (x == null) return 0;
		return x.darSize();
	} 


	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Is this symbol table empty?
	 * @return {@code true} if this symbol table is empty and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}


	/***************************************************************************
	 *  Standard BST search.
	 ***************************************************************************/

	/**
	 * Returns the value associated with the given key.
	 * @param key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public V darValor(K key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		return darValor(root, key);
	}

	// value associated with the given key in subtree rooted at x; null if no such key
	private V darValor(NodoBSTRojoNegro x, K key) {
		while (x != null) {
			int cmp = key.compareTo((K) x.darKey());
			if      (cmp < 0) x = x.darLeft();
			else if (cmp > 0) x = x.darRight();
			else              return (V) x.darValue();
		}
		return null;
	}

	/**
	 * Does this symbol table contain the given key?
	 * @param key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *     {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(K key) {
		return darValor(key) != null;
	}

	/***************************************************************************
	 *  Red-black tree insertion.
	 ***************************************************************************/

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param key the key
	 * @param val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(K key, V val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}

		root = put(root, key, val);
		root.setColor(BLACK);
		// assert check();
	}

	// insert the key-value pair in the subtree rooted at h
	private NodoBSTRojoNegro put(NodoBSTRojoNegro h, K key, V val) { 
		if (h == null)
		{
			NodoBSTRojoNegro aDevolver = new NodoBSTRojoNegro(key, val, RED, 1);
			return new NodoBSTRojoNegro((K) key, val,RED,1);
		}

		int cmp = key.compareTo((K) h.darKey());
		if      (cmp < 0) h.setLeft(put(h.darLeft(),  key, val) ); 
		else if (cmp > 0) h.setRight(put(h.darRight(),  key, val) ); 
		else              h.setValue(val);   

		// fix-up any right-leaning links
		if (isRed(h.darRight()) && !isRed(h.darLeft()))      h = rotateLeft(h);
		if (isRed(h.darLeft())  &&  isRed(h.darLeft().darLeft())) h = rotateRight(h);
		if (isRed(h.darLeft())  &&  isRed(h.darRight()))     flipColors(h);
		h.setSize(size(h.darLeft()) + size(h.darRight()) + 1);

		return h;
	}

	/***************************************************************************
	 *  Red-black tree deletion.
	 ***************************************************************************/

	/**
	 * Removes the smallest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.darLeft()) && !isRed(root.darRight()))
			root.setColor(RED);

		root = deleteMin(root);
		if (!isEmpty()) root.setColor(BLACK); 
		// assert check();
	}

	// delete the key-value pair with the minimum key rooted at h
	private NodoBSTRojoNegro<K, V> deleteMin(NodoBSTRojoNegro<K, V>h) { 
		if (h.darLeft() == null)
			return null;

		if (!isRed(h.darLeft()) && !isRed(h.darLeft().darLeft()))
			h = moveRedLeft(h);

		h.setLeft(deleteMin(h.darLeft())); 
		return balance(h);
	}


	/**
	 * Removes the largest key and associated value from the symbol table.
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.darLeft()) && !isRed(root.darRight()))
			root.setColor(RED);

		root = deleteMax(root);
		if (!isEmpty()) root.setColor(BLACK); 
		// assert check();
	}

	// delete the key-value pair with the maximum key rooted at h
	private NodoBSTRojoNegro<K, V> deleteMax(NodoBSTRojoNegro<K , V> h) { 
		if (isRed(h.darLeft()))
			h = rotateRight(h);

		if (h.darRight() == null)
			return null;

		if (!isRed(h.darRight()) && !isRed(h.darRight().darLeft()))
			h = moveRedRight(h);

		h.setRight(deleteMax(h.darRight())); 

		return balance(h);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(K key) { 
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		if (!contains(key)) return;

		// if both children of root are black, set root to red
		if (!isRed(root.darLeft()) && !isRed(root.darRight()))
			root.setColor(RED); 

		root = delete(root, key);
		if (!isEmpty()) root.setColor(BLACK); 
		// assert check();
	}

	// delete the key-value pair with the given key rooted at h
	private NodoBSTRojoNegro<K, V> delete(NodoBSTRojoNegro<K , V>h, K key) { 
		// assert get(h, key) != null;

		if (key.compareTo(h.darKey()) < 0)  {
			if (!isRed(h.darLeft()) && !isRed(h.darLeft().darLeft()))
				h = moveRedLeft(h);
			h.setLeft(delete(h.darLeft(), key));  
		}
		else {
			if (isRed(h.darLeft()))
				h = rotateRight(h);
			if (key.compareTo(h.darKey()) == 0 && (h.darRight()== null))
				return null;
			if (!isRed(h.darRight()) && !isRed(h.darRight().darLeft()))
				h = moveRedRight(h);
			if (key.compareTo(h.darKey()) == 0) {
				NodoBSTRojoNegro<K, V> x = min(h.darRight());
				h.setKey(x.darKey());
				h.setValue(x.darValue()); 
				// h.val = get(h.right, min(h.right).key);
				// h.key = min(h.right).key;
				h.setRight(deleteMin(h.darRight())); 
			}
			else h.setRight(delete(h.darRight(), key)); 
		}
		return balance(h);
	}

	/***************************************************************************
	 *  Red-black tree helper functions.
	 ***************************************************************************/

	// make a left-leaning link lean to the right
	private NodoBSTRojoNegro<K, V> rotateRight(NodoBSTRojoNegro<K, V> h) {
		// assert (h != null) && isRed(h.left);
		NodoBSTRojoNegro<K, V> x = h.darLeft();
		h.setLeft(x.darRight()); 
		x.setRight(h);
		x.setColor(x.darRight().darColor()); 
		x.darRight().setColor(RED);
		x.setSize(h.darSize());
		h.setSize(size(h.darLeft()) + size(h.darRight()) + 1); 
		return x;
	}

	// make a right-leaning link lean to the left
	private NodoBSTRojoNegro<K , V> rotateLeft(NodoBSTRojoNegro<K , V> h) {
		// assert (h != null) && isRed(h.right);
		NodoBSTRojoNegro<K, V> x = h.darRight();
		h.setRight(x.darLeft()); 
		x.setLeft(h);
		x.setColor(x.darLeft().darColor()); 
		x.darLeft().setColor(RED);
		x.setSize(h.darSize());
		h.setSize(size(h.darLeft()) + size(h.darRight()) + 1);
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(NodoBSTRojoNegro<K, V> h) {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
		//    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
		h.setColor(!h.darColor()); 
		h.darLeft().setColor(!h.darLeft().darColor());
		h.darRight().setColor(!h.darRight().darColor());
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private NodoBSTRojoNegro<K, V> moveRedLeft(NodoBSTRojoNegro<K , V> h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.darRight().darLeft())) { 
			h.setRight(rotateRight(h.darRight()));
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private NodoBSTRojoNegro<K, V> moveRedRight(NodoBSTRojoNegro<K , V>h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.darLeft().darLeft())) { 
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private NodoBSTRojoNegro<K, V> balance(NodoBSTRojoNegro<K, V> h) {
		// assert (h != null);

		if (isRed(h.darRight()))                      h = rotateLeft(h);
		if (isRed(h.darLeft()) && isRed(h.darLeft().darLeft())) h = rotateRight(h);
		if (isRed(h.darLeft()) && isRed(h.darLeft()))     flipColors(h);

		h.setSize(size(h.darLeft()) + size(h.darRight()) + 1);
		return h;
	}


	/***************************************************************************
	 *  Utility functions.
	 ***************************************************************************/

	/**
	 * Returns the height of the BST (for debugging).
	 * @return the height of the BST (a 1-node tree has height 0)
	 */
	public int height() {
		return height(root);
	}
	private int height(NodoBSTRojoNegro<K, V> x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.darLeft()), height(x.darRight()));
	}

	/***************************************************************************
	 *  Ordered symbol table methods.
	 ***************************************************************************/

	/**
	 * Returns the smallest key in the symbol table.
	 * @return the smallest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public K min() {
		if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
		return (K) min(root).darKey();
	} 

	// the smallest key in subtree rooted at x; null if no such key
	private NodoBSTRojoNegro<K, V> min(NodoBSTRojoNegro<K, V> x) { 
		// assert x != null;
		if (x.darLeft() == null) return x; 
		else                return min(x.darLeft()); 
	} 

	/**
	 * Returns the largest key in the symbol table.
	 * @return the largest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public K max() {
		if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
		return (K) max(root).darKey();
	} 

	// the largest key in the subtree rooted at x; null if no such key
	private NodoBSTRojoNegro<K, V> max(NodoBSTRojoNegro<K, V>x) { 
		// assert x != null;
		if (x.darRight() == null) return x; 
		else                 return max(x.darRight()); 
	} 


	/**
	 * Returns the largest key in the symbol table less than or equal to {@code key}.
	 * @param key the key
	 * @return the largest key in the symbol table less than or equal to {@code key}
	 * @throws NoSuchElementException if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public K floor(K key) {
		if (key == null) throw new IllegalArgumentException("argument to floor() is null");
		if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
		NodoBSTRojoNegro<K, V> x = floor(root, key);
		if (x == null) return null;
		else           return x.darKey();
	}    

	// the largest key in the subtree rooted at x less than or equal to the given key
	private NodoBSTRojoNegro<K , V> floor(NodoBSTRojoNegro< K, V > x, K key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.darKey());
		if (cmp == 0) return x;
		if (cmp < 0)  return floor(x.darLeft(), key);
		NodoBSTRojoNegro<K , V >t = floor(x.darRight(), key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Returns the smallest key in the symbol table greater than or equal to {@code key}.
	 * @param key the key
	 * @return the smallest key in the symbol table greater than or equal to {@code key}
	 * @throws NoSuchElementException if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public K ceiling(K key) {
		if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
		if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
		NodoBSTRojoNegro<K, V> x = ceiling(root, key);
		if (x == null) return null;
		else           return x.darKey();  
	}

	// the smallest key in the subtree rooted at x greater than or equal to the given key
	private NodoBSTRojoNegro< K , V > ceiling(NodoBSTRojoNegro<K , V> x, K key) {  
		if (x == null) return null;
		int cmp = key.compareTo(x.darKey());
		if (cmp == 0) return x;
		if (cmp > 0)  return ceiling(x.darRight(), key);
		NodoBSTRojoNegro<K , V> t = ceiling(x.darLeft(), key);
		if (t != null) return t; 
		else           return x;
	}

	/**
	 * Return the key in the symbol table whose rank is {@code k}.
	 * This is the (k+1)st smallest key in the symbol table. 
	 *
	 * @param  k the order statistic
	 * @return the key in the symbol table of rank {@code k}
	 * @throws IllegalArgumentException unless {@code k} is between 0 and
	 *        <em>n</em>–1
	 */
	public K select(int k) {
		if (k < 0 || k >= size()) {
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		}
		NodoBSTRojoNegro<K , V> x = select(root, k);
		return x.darKey();
	}

	// the key of rank k in the subtree rooted at x
	private NodoBSTRojoNegro<K , V> select(NodoBSTRojoNegro<K, V> x, int k) {
		// assert x != null;
		// assert k >= 0 && k < size(x);
		int t = size(x.darLeft()); 
		if      (t > k) return select(x.darLeft(),  k); 
		else if (t < k) return select(x.darRight(), k-t-1); 
		else            return x; 
	} 

	/**
	 * Return the number of keys in the symbol table strictly less than {@code key}.
	 * @param key the key
	 * @return the number of keys in the symbol table strictly less than {@code key}
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public int rank(K key) {
		if (key == null) throw new IllegalArgumentException("argument to rank() is null");
		return rank(key, root);
	} 

	// number of keys less than key in the subtree rooted at x
	private int rank(K key, NodoBSTRojoNegro<K , V>x) {
		if (x == null) return 0; 
		int cmp = key.compareTo(x.darKey()); 
		if      (cmp < 0) return rank(key, x.darLeft()); 
		else if (cmp > 0) return 1 + size(x.darLeft()) + rank(key, x.darRight()); 
		else              return size(x.darLeft()); 
	} 

	/***************************************************************************
	 *  Range count and range search.
	 ***************************************************************************/

	/**
	 * Returns all keys in the symbol table as an {@code Iterable}.
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 * @return all keys in the symbol table as an {@code Iterable}
	 */
	public Iterable<K> keys() {
		if (isEmpty()) return new Queue<K>();
		return keys(min(), max());
	}

	/**
	 * Returns all keys in the symbol table in the given range,
	 * as an {@code Iterable}.
	 *
	 * @param  lo minimum endpoint
	 * @param  hi maximum endpoint
	 * @return all keys in the sybol table between {@code lo} 
	 *    (inclusive) and {@code hi} (inclusive) as an {@code Iterable}
	 * @throws IllegalArgumentException if either {@code lo} or {@code hi}
	 *    is {@code null}
	 */
	public Iterable<K> keys(K lo, K hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
		if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

		Queue<K> queue = new Queue<K>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		keys(root, queue, lo, hi);
		return queue;
	} 

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
	private void keys(NodoBSTRojoNegro<K, V> x, Queue<K> queue, K lo, K hi) { 
		if (x == null) return; 
		int cmplo = lo.compareTo(x.darKey()); 
		int cmphi = hi.compareTo(x.darKey()); 
		if (cmplo < 0) keys(x.darLeft(), queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.darKey()); 
		if (cmphi > 0) keys(x.darRight(), queue, lo, hi); 
	} 

	/**
	 * Returns the number of keys in the symbol table in the given range.
	 *
	 * @param  lo minimum endpoint
	 * @param  hi maximum endpoint
	 * @return the number of keys in the sybol table between {@code lo} 
	 *    (inclusive) and {@code hi} (inclusive)
	 * @throws IllegalArgumentException if either {@code lo} or {@code hi}
	 *    is {@code null}
	 */
	public int size(K lo, K hi) {
		if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
		if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

		if (lo.compareTo(hi) > 0) return 0;
		if (contains(hi)) return rank(hi) - rank(lo) + 1;
		else              return rank(hi) - rank(lo);
	}


	/***************************************************************************
	 *  Check integrity of red-black tree data structure.
	 ***************************************************************************/
	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order is strict
	private boolean isBST() {
		return isBST(root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(NodoBSTRojoNegro<K, V> x, K min, K max) {
		if (x == null) return true;
		if (min != null && x.darKey().compareTo(min) <= 0) return false;
		if (max != null && x.darKey().compareTo(max) >= 0) return false;
		return isBST(x.darLeft(), min, x.darKey()) && isBST(x.darRight(), x.darKey(), max);
	} 

	// are the size fields correct?
	private boolean isSizeConsistent() { return isSizeConsistent(root); }
	private boolean isSizeConsistent(NodoBSTRojoNegro<K , V> x) {
		if (x == null) return true;
		if (x.darSize() != size(x.darLeft()) + size(x.darRight()) + 1) return false;
		return isSizeConsistent(x.darLeft()) && isSizeConsistent(x.darRight());
	} 

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < size(); i++)
			if (i != rank(select(i))) return false;
		for (K key : keys())
			if (key.compareTo(select(rank(key))) != 0) return false;
		return true;
	}

	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() { return is23(root); }
	private boolean is23(NodoBSTRojoNegro<K, V> x) {
		if (x == null) return true;
		if (isRed(x.darRight())) return false;
		if (x != root && isRed(x) && isRed(x.darLeft()))
			return false;
		return is23(x.darLeft()) && is23(x.darRight());
	} 

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() { 
		int black = 0;     // number of black links on path from root to min
		NodoBSTRojoNegro<K, V> x = root;
		while (x != null) {
			if (!isRed(x)) black++;
			x = x.darLeft();
		}
		return isBalanced(root, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(NodoBSTRojoNegro<K, V> x, int black) {
		if (x == null) return black == 0;
		if (!isRed(x)) black--;
		return isBalanced(x.darLeft(), black) && isBalanced(x.darRight(), black);
	} 

}

