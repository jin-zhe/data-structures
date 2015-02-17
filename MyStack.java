
public class MyStack {
	Node top;
	
	MyStack(){
		top = null;
	}
	
	void push(Object item){
		Node node = new Node(item);
		Node temp = top;
		top = node;
		top.next = temp;
	}
	
	Object pop(){
		if (top != null){
			Node temp = top;
			top = top.next;
			return temp.data;
		}
		return null;
	}
	
	Node peek(){
		return top;
	}
	
	public static void main(String[] args) {
		MyStack stack = new MyStack();
		stack.push(new String("haha"));
		stack.push(new Integer(5));
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}
	
	static class Node{
		Object data;
		Node next;
		
		Node(Object data){
			this.data = data;
		}
	}
}