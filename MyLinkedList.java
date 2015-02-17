import java.util.HashSet;

public class MyLinkedList{
	Node head;
	Node tail;
	
	public MyLinkedList(){
		head = null;
		tail = null;
	}
	
	public void add(Node node){
		// if list is empty
		if (head == null) head = node;
		// else append to list
		else tail.next = node;
		
		// update tail
		if (node.next == null) tail = node;
		else tail = null;	// exception for when loop appears
	}
	
	// reverses the list in O(N) time with O(1) space
	public void reverseList(){
		tail = head;					// update tail
		Node prev = null;
		Node current = head;
		while (current != null){
			Node next = current.next;	// the next node in sequence
			current.next = prev;		// point in reverse to previous node
			prev = current;				// update previous node
			
			if (next == null)
				head = current;			// update head
			current = next;				// update current
		}
	}
	
	// checks to see if linked list is palindrome
	boolean isPalindrome(){

		// reverse linked list and store original in new list
		
		// pointers for original list
		Node originalHead = new Node(head.value);
		Node originalCurrent = originalHead;
		
		// pointers for reversed list
		Node prev = null;
		Node current = head;
		
		while(current != null){
			Node next = current.next;
			current.next = prev;

			if (next == null){
				head = current;
				originalCurrent.next = null;
			}
			else originalCurrent.next = new Node(next.value);
			
			// update pointers
			prev = current;
			current = next;
			originalCurrent = originalCurrent.next;
		}
		
		// compares original linked list with reversed linked list
		Node original = originalHead;
		Node reversed = head;
		
		while (original != null){
			if (original.value != reversed.value) return false;
			
			// update pointers
			original = original.next;
			reversed = reversed.next;
		}
		
		return true;
	}
	
	public Node getLoopStart(){
		Node collisionNode = null;
		Node fastPointer = head;	// advances by 2
		Node slowPointer = head;	// advances by 1
	
		// determine collision node
		while (collisionNode == null){
			if (fastPointer == slowPointer && fastPointer != head && slowPointer != head)
				collisionNode = fastPointer;
			
			// update fastPointer
			if (fastPointer.next == null || fastPointer.next.next == null) break;
			else fastPointer = fastPointer.next.next;
			
			// update slowPointer
			if (slowPointer.next == null) break;
			else slowPointer = slowPointer.next;
		}
		
		// catch if list has no loop
		if (collisionNode == null) return null;
		
		// determine loop start node
		Node one = head;
		Node two = collisionNode;
		
		while (one != two){
			one = one.next;
			two = two.next;
		}
		return one;
	}
	
	// implemented using hashset
	public Node getLoopStart2(){
		Node iterator = head;
		HashSet<Node> hashset = new HashSet<Node>();
		
		while (!hashset.contains(iterator)){
			hashset.add(iterator);
			iterator = iterator.next;
		}
		return iterator;
	}
	
	
	// O(N)
	public void printList(){
		Node current = head;
		while(current != null){
			System.out.println(current.value);
			current = current.next;
		}
	}
	
	// O(3N)
	public void printReverse(){
		reverseList();	// reverses list
		printList();
		reverseList();	// reverses list
	}
	
	// print reverse list
	public void run1(){
		add(new Node("a"));
		add(new Node("b"));
		add(new Node("c"));
		add(new Node("d"));
		add(new Node("e"));
		add(new Node("f"));
		add(new Node("g"));
		add(new Node("h"));
		printReverse();
	}
	
	// test for palindrome
	public void run2(){
		add(new Node("a"));
		add(new Node("b"));
		add(new Node("b"));
		add(new Node("a"));
		System.out.println(isPalindrome());
	}
	
	// test for cycle
	public void run3(){
		add(new Node("a"));
		add(new Node("b"));
		add(new Node("c"));
		add(new Node("d"));
		add(new Node("e"));
		add(new Node("f"));
		
		Node startLoop = new Node("g");
		add(startLoop);
		add(new Node("h"));
		add(new Node("i"));
		add(new Node("j"));
		add(new Node("k"));
		add(startLoop);
		System.out.println(getLoopStart2().value);
	}
	
	public static void main(String[] args){
		MyLinkedList list = new MyLinkedList();
		list.run3();
	}
	
	public static class Node{
		String value;
		Node next;
		public Node(String value){
			this.value = value;
			this.next = null;
		}
	}
}
