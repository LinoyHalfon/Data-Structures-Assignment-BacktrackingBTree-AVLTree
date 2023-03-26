import java.util.LinkedList;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;

public class BacktrackingAVL extends AVLTree {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingAVL() {
		super();
	}

	// You are to implement the function Backtrack.
	public void Backtrack() {
		if (!dq.isEmpty()) {
			while (dq.peekLast() instanceof String) { // rotation was made
				String rotation = (String) dq.pollLast();
				Node node = (Node) dq.pollLast();
				if (rotation.equals("left")){ //left case
					if (node.parent.parent == null) // root should change
						root = rotateLeft(node.parent);
					else { // root shouldn't change
							// check if new node should be left/right child
						if (node.parent.value < node.parent.parent.value)
							node.parent.parent.left = rotateLeft(node.parent);
						else
							node.parent.parent.right = rotateLeft(node.parent);
					}
				}
				else { //right case
					if (node.parent.parent == null) // root should change
						root = rotateRight(node.parent);
					else { // root shouldn't change
							// check if new node should be left/right child
						if (node.parent.value < node.parent.parent.value)
							node.parent.parent.left = rotateRight(node.parent);
						else
							node.parent.parent.right = rotateRight(node.parent);
					}
				}			
			}
			Node inserted = (Node) dq.pollLast();
			if (inserted.parent == null) { // tree has only root
				root = null;
			} else {
				Node insertedParent = inserted.parent;
				if (insertedParent.value > inserted.value) // left child
					insertedParent.left = null;
				else // right child
					insertedParent.right = null;
				Node currNode = insertedParent;
				while (currNode != null) {
					currNode.updateHeight();
					currNode.size--;
					currNode = currNode.parent;
				}
			}
		}
	}	
			
			
			
//			Object[] info = (Object[]) dq.pollLast();
//			Node inserted = (Node) info[0];
//			if (inserted.parent == null) {
//				root = null;
//			} else {
//				if (inserted.parent.value > inserted.value) // left child
//					inserted.parent.left = null;
//				else // right child
//					inserted.parent.right = null;
//				if (info[1].equals("left")) { // left cases
//					Node node1 = (Node) info[3];
//					if (node1.parent.parent == null) // root should change
//						root = rotateLeft(node1.parent);
//					else { // root shouldn't change
//							// check if new node should be left/right child
//						if (node1.parent.value < node1.parent.parent.value)
//							node1.parent.parent.left = rotateLeft(node1.parent);
//						else
//							node1.parent.parent.right = rotateLeft(node1.parent);
//					}
//
//					if (info[2] != null) { // double rotation was made
//						Node node2 = (Node) info[4];
//						if (node2.parent.value < node2.parent.parent.value)
//							node2.parent.parent.left = rotateRight(node2.parent);
//						else
//							node2.parent.parent.right = rotateRight(node2.parent);
//					}
//				}
//
//				else if (info[1].equals("right")) { // right cases
//					Node node1 = (Node) info[3];
//					if (node1.parent.parent == null) // root should change
//						root = rotateRight(node1.parent);
//					else { // root shouldn't change
//							// check if new node should be left/right child
//						if (node1.parent.value < node1.parent.parent.value)
//							node1.parent.parent.left = rotateRight(node1.parent);
//						else
//							node1.parent.parent.right = rotateRight(node1.parent);
//					}
//
//					if (info[2] != null) { // double rotation was made
//						Node node2 = (Node) info[4];
//						if (node2.parent.value < node2.parent.parent.value)
//							node2.parent.parent.left = rotateLeft(node2.parent);
//						else
//							node2.parent.parent.right = rotateLeft(node2.parent);
//					}
//				}
//				Node currNode = (Node) info[3];
//				currNode = currNode.parent;
//				while (currNode != null) {
//					currNode.updateHeight();
//					currNode = currNode.parent;
//				}
//			}
//		}


	// Change the list returned to a list of integers answering the requirements
	public static List<Integer> AVLTreeBacktrackingCounterExample() {
		List<Integer> insertions = new LinkedList<>();
		insertions.add(10);
		insertions.add(8);
		insertions.add(15);
		insertions.add(7);
		insertions.add(9);
		insertions.add(20);
		insertions.add(25);
		return insertions;
	}

	public int Select(int index) {
		return Select(index,root);

	}
	
	private int Select(int index, Node node) {
		int currRank;
		if (node.left != null) 
			currRank = node.left.size+1;
		else
			currRank = 1;
		if (currRank == index)
			return node.value;
		else if (index<currRank) 
			return Select(index, node.left);
		else
			return Select(index-currRank, node.right);
		}

	public int Rank(int value) {
		if (root == null)
			return 0;
		return Rank(value,root);
	}
	
	private int Rank(int value, Node node) {
		if (node.value == value) {
			if (node.left != null)
				return node.left.size;
			else
				return 0;
		}
		else if (value < node.value) {
			if (node.left != null)
				return Rank(value, node.left);
			else
				return 0;
		}
		else{ //value > node.value
			if (node.right != null)
				if (node.left != null)
					return Rank(value, node.right) + node.left.size + 1;
				else
					return Rank(value, node.right) + 1;
			else if (node.left != null)
				return node.left.size + 1;
			else
				return 1;
		}
	}
}
