public class MyQueue {
	Node first;
	Node last;
	MyQueue(){
		first = last = null;
	}
	
	void enqueue(Object item){
		Node node = new Node(item);
		if (first == null){
			first = last = node;
		}
		else{
			Node temp = last;
			last = node;
			temp.next = last;
		}
	}
	
	Object dequeue(){
		if (first != null){
			Node temp = first;
			first = first.next;
			if (first == null) last = null;
			return temp.data;
		}
		return null;
	}
	public static void main(String[] args) {
		MyQueue q = new MyQueue();
		q.enqueue(new String("haha"));
		q.enqueue(new Integer(2));
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
	}
	
	static class Node{
		Object data;
		Node next;
		
		Node(Object item){
			data = item;
		}
	}
}
