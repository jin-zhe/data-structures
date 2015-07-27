import java.util.ArrayList;
/**
 * Implementation of a max heap. i.e. ancestors value > descendants value
 * @author Kilroy
 * @param <T>	generic type for the type of objects to be inserted
 */
class MyHeap <T extends Comparable<T>> {
	private ArrayList<T> heap;
	
	public MyHeap() {
		heap = new ArrayList<T> (); 
	}

	/**
	 * Swaps the two items in heap at indices x and y
	 * @param x
	 * @param y
	 */
	private void swap(int x, int y) {
		/* swaps reference */
		T temp = heap.get(x);
		heap.set(x, heap.get(y));
		heap.set(y, temp);
	}

	/**
	 * Getter for item at index i
	 * @param i		index of item
	 * @return		item at index
	 */
	private T get(int i) {
		return heap.get(i);
	}
	
	/**
	 * Determines the parent index
	 * @param i		index of given item
	 * @return		the index for parent of item at i
	 */
	private int getParentIndex(int i) {
		return i/2;
	}
	/**
	 * Gets the parent item
	 * @param i		index of given item
	 * @return		the parent for item at i
	 */
	private T getParent(int i) {
		return heap.get(getParentIndex(i));
	}
	
	/**
	 * Determines the left child index
	 * @param i		index of given item
	 * @return		the index for left child of item at i
	 */
	private int getLeftIndex(int i) {
		return i*2;
	}
	
	/**
	 * Gets the left child item
	 * @param i		index of given item
	 * @return		the left child for item at i
	 */
	private T getLeft(int i) {
		return heap.get(getLeftIndex(i));
	}
	
	/**
	 * Determines the right child index
	 * @param i		index of given item
	 * @return		the index for right child of item at i
	 */
	private int getRightIndex(int i) {
		return i*2+1;
	}
	
	/**
	 * Gets the right child item
	 * @param i		index of given item
	 * @return		the right child for item at i
	 */
	private T getRight(int i) {
		return heap.get(getRightIndex(i));
	}

	/**
	 * Bubble up the current item until it is no longer larger than its parent.
	 * O(log N) operation
	 * @param i		index of item to be bubbled up
	 */
	private void heapifyUp(int i) {
		 /* while index != root and item is larger than parent */
		while (i>0 && getParent(i).compareTo(get(i))<0) {
			swap(getParentIndex(i), i);
			i = getParentIndex(i);
		}
	}

	/**
	 * Bubble down the current item until it reaches its stable spot
	 * Bubble down. O(log N)
	 * @param i		index of item to be bubbled down
	 */
	private void heapifyDown(int i) {
		/* while index is still within heap */
		while (i < size()) {
			T max = get(i); // max(item at i, left child, right child)
			int max_i = i;	// heap index for max

			/* compare left and right and decide the larger one to swap with */
			
			/* if left child exists and is greater */
			if (getLeftIndex(i)<size() && max.compareTo(getLeft(i))<0) {
				max = getLeft(i);
				max_i = getLeftIndex(i);
			}
			/* if current < right(if exist), assign */
			if (getRightIndex(i)<size() && max.compareTo(getRight(i))<0) {
				max = getRight(i);
				max_i = getRightIndex(i);
			}
			/* if either left child or right child is larger */
			if (max_i != i) {
				swap(i, max_i);	// swap 
				i = max_i;      // update i
			}
		    /* else if element has reached to its stable spot */
			else {
				break;
			}
		}
	}
	
	/**
	 * Remove item at given index
	 * worst case delete(1) runs O(log N)
	 * @param i		index of item to be removed
	 * @return		the item removed from heap
	 */
	private T remove(int i) {
		T item = get(i);
		swap(i, size()-1);		// swaps item to be deleted with last heap item
		heap.remove(size()-1);	// remove from heap
		heapifyDown(i); 		// O(log N)
		return item;
	}
	
	/**
	 * Getter for current heap size
	 * @return	the size of heap
	 */
	public int size() {
		return heap.size();
	}

	/**
	 * Inserts item to the heap
	 * worst case O(log N) when item needs to be bubbled to the root
	 * @param item	item to be inserted
	 */
	public void offer(T item) {
		heap.add(item); 		// add to rear of heap
		heapifyUp(size() - 1); 	// O(log N)
	}
	
	/**
	 * Retrieves and removes the head of this queue
	 * @return	head of queue or null if queue is empty
	 */
	public T poll() {
		if (size() > 0) {
			return remove(0);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Retrieves, but does not remove, the head of queue
	 * @return	head of queue or null if queue is empty
	 */
	public T peek() {
		if (size() > 0) {
			return heap.get(0);
		}
		else {
			return null;
		}
	}

	public static void main(String[] args) {
		MyHeap<Integer> heap = new MyHeap<Integer>();
		
		/* Simple tests */
		heap.offer(1);
		heap.offer(2);
		heap.offer(9);
		heap.offer(4);
		heap.offer(5);
		System.out.println("Peek: " + heap.peek());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Peek: " + heap.peek());
		heap.offer(6);
		System.out.println("Peek: " + heap.peek());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Poll: " + heap.poll());
		System.out.println("Peek: " + heap.peek());
	}
}
