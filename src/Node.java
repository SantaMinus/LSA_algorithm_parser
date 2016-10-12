
public class Node {
	String name;
	int ID, n;
	private Node next;
	
	public Node(String name, int ID, int n){
		this.ID = ID;
		this.name = name;
		this.n = n;
	}
	
	public Node getNext() {
	  return next;
	}
	
	public void setNext(Node n) {
	  next = n;
	}
}