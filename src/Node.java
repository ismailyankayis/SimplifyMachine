
public class Node {
	
	private String binary;
	private Node next;
	private boolean isUsed;
	
	public Node(String b){
		binary = b;
		next = null;
		isUsed = false;
	}

	public String getBinary() {
		return binary;
	}

	public void setBinary(String binary) {
		this.binary = binary;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	
	
}
