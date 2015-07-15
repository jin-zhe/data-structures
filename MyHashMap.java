import java.util.Arrays;


public class MyHashMap<K, V> {
	private Entry<K,V>[] table;
	private int capacity;
	private int size;
	private static final float LOAD_FACTOR = 0.75f; // size/capacity
	
	/**
	 * Constructors
	 */
	public MyHashMap(){
		this(128);	// defaults capacity at 128
	}
	public MyHashMap(int capacity){
		this.capacity = capacity;
		table = new Entry[capacity];
		size = 0;
	}
	
	/**
	 * Puts the given entry into hashmap
	 * @param entry
	 */
	public void put(K key, V value){
		int index = getTableIndex(key);	// index in table to be inserted at
		
		/* inserts entry into table */
		Entry<K,V> head = table[index];	// gets the head of list at table[index]
		if (head == null){
			head = new Entry<K,V>(key, value, null);
			table[index] = head;
		}
		else{
			Entry<K, V> prev = null;
			Entry<K, V> curr = head;
			boolean isInserted = false;
			/* while not reached end of list */
			while(curr != null){
				K currKey = curr.key;
				/* if entry is found, update value */
				if (currKey == key){
					curr.value = value;
					isInserted = true;
					break;
				}
				/* else progress down the list */
				else{
					prev = curr;
					curr = curr.next;
				}
			}
			/* if entry not already inserted, append it to back of list */
			if (!isInserted){
				prev.next = new Entry<K, V>(key, value, null);
			}
		}
		size++; 		// increment size
		checkRehash();	// checks current load factor and rehash if necessary
	}
	
	/**
	 * Gets the value associated with the given key
	 * @param key	Key to be searched
	 * @return		Associated value
	 */
	public V get(K key){
		int index = getTableIndex(key);
		Entry<K, V> curr = table[index];
		/* if list is empty, key does not exist */
		if (curr == null)
			return null;
		
		/* while not reached end of list */
		while (curr != null){
			/* if key is found, return associating value */
			if (curr.key == key){
				return curr.value;
			}
			/* else, progress down the list */
			else{
				curr = curr.next;
			}
		}
		return null;
	}
	
	/**
	 * Removes entry for the given key if present
	 * @param key	Key for intended entry
	 * @return		Value associated with key
	 */
	public V remove(K key){
		V value = null;
		int index = getTableIndex(key);
		Entry<K, V> curr = table[index];
		/* if entry does not exist */
		if (curr == null){
			return null;
		}
		/* if entry is head item */
		if (curr.key == key){
			table[index] = curr.next;
			value = curr.value;
		}
		/* else search through list */
		else{
			Entry<K, V> prev = curr;
			curr = curr.next;
			/* while not end of list */
			while (curr != null){
				/* remove entry if found */
				if (curr.key == key){
					value = curr.value;
					prev.next = curr.next;	// remove incoming pointers
					curr.next = null;		// remove outgoing pointers
					break;
				}
				curr = curr.next; // progress down list 
			}
		}
		size--;	// decrement size
		return value;
	}
	
	/**
	 * @return The hashmap as a list of all its constituent entries
	 */
	public Entry<K, V>[] asList(){
		Entry<K, V>[] list = new Entry[size];
		int index = 0;
		for (Entry<K, V> entry: table){
			while (entry != null){
				list[index] = entry;
				index++;
				entry = entry.next;
			}
		}
		return list;
	}
	
	/**
	 * Get string representation of hashmap in tuples. e.g. "(key1, value1), (key2, value2), ..."
	 * Note: not in the order of insertion
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (Entry<K, V> entry: table){
			while (entry != null){
				sb.append(entry + ", ");
				entry = entry.next;
			}
		}
		if (sb.length() != 0){
			sb.delete(sb.length()-2, sb.length()); // remove trailing ", "
		}
		return sb.toString();
	}
	
	/**
	 * Checks if hashmap needs to rehash and does so if necessary
	 */
	public void checkRehash(){
		float currentLoadFactor = size/(float) capacity;
		if (currentLoadFactor > LOAD_FACTOR){
			rehash();
		}
	}
	
	/**
	 * Rehashes the current hashmap by doubling capacity
	 */
	public void rehash(){
		// TODO
	}
	
	/**
	 * @return Current size of hashmap
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Checks if hashtable contains key
	 * @param key	Key to be searched
	 * @return		True if key exists, false otherwise
	 */
	public boolean containsKey(K key){
		V value = get(key);
		return (value != null);
	}
	
	/**
	 * Gets the index in table from given key
	 * @param key	Key of interest
	 * @return		Index in table for key
	 */
	private int getTableIndex(K key){
		int hashCode = getHashCode(key);
		return hashCode%capacity;
	}
	
	/**
	 * @param key	Key to be hashed
	 * @return 		Generated hashcode for given key object
	 */
	private int getHashCode(K key){
		return Math.abs(key.hashCode());
	}
	
	/**
	 * Entry class for each item in hashmap 
	 * @param <K> Object type of key
	 * @param <V> Object type of value
	 */
	static class Entry<K, V>{
		K key;
		V value;
		Entry<K, V> next;
		
		/**
		 * Constructor
		 */
		public Entry(K key, V value, Entry<K, V> next){
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * String representation of entry as a tuple
		 */
		public String toString(){
			return "(" + key.toString() + ", " + value.toString() + ")";
		}
	}
	
	/**
	 * Main method to test the data structure
	 */
	public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<Integer, String>();
        /* test insertions */
        map.put(42, "Answer to The Ultimate Question of Life, the Universe, and Everything");
        map.put(529, "Five hundred and twenty-nine");
        map.put(15, "Fifteen");
        map.put(87, "Eighty Seven");
        map.put(6, "Six");

        System.out.println("value for key == 42: " + map.get(42));	// test valid key
        System.out.println("value for key == 1: " + map.get(1));	// test invalid key

        System.out.print("Current hashmap : ");
        System.out.print(Arrays.toString(map.asList()));			// test asList method
        
        System.out.println();
        System.out.println("value for key == 42 removed: " + map.remove(42));	// test removal of valid key
        System.out.println("value for key == 1 removed: " + map.remove(1));		// test removal of invalid key

        System.out.print("Current hashmap : ");
        System.out.print(map);	// test toString method
	}
}