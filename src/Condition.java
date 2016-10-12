
public class Condition extends Node{
	private Node falseway;
	
	public Condition(String name, int ID, int n) {
		super(name, ID, n);
	}
	
	public void setFalseWay(Node n){
		falseway = n;
	}
	
	public Node getFalseWay(){
		return falseway;
	}
}
