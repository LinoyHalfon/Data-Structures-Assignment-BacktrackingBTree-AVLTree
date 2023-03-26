import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {
	// For clarity only, this is the default ctor created implicitly.
	public BacktrackingBTree() {
		super();
	}

	public BacktrackingBTree(int order) {
		super(order);
	}
	
	//You are to implement the function Backtrack.
	public void Backtrack() {
		if (!DQ.isEmpty()) {
			Deque dq = DQ.pollLast();
			size--; 
			if (DQ.isEmpty()) //only one insertion was made
				root = null;
			else { //the tree is not empty
				T value = (T) dq.pollFirst();
				Node<T> inserted = getNode(value);
				inserted.removeKey(value);
				while (!dq.isEmpty()) { // splits were made
					T splitted = (T) dq.pollLast();
					Node<T> currNode = getNode(splitted);
					int idx = currNode.indexOf(splitted);
					Node<T> leftChild =  currNode.children[idx];
					Node<T> rightChild = currNode.children[idx+1];
					leftChild.addKey(splitted);
					for (int i = 0; i < rightChild.getNumberOfKeys(); i++) { //copy keys from right child to left
						leftChild.addKey(rightChild.keys[i]);
					}
					for (int i = 0; i < rightChild.getNumberOfChildren(); i++) { //copy children from right child to left
						leftChild.addChild(rightChild.children[i]);
					}
					if (currNode.parent == null & currNode.numOfKeys == 1) { //split process created new root with 1 key
						root = leftChild;
						leftChild.parent = null;
					}
					else { //split process didn't created new root
						currNode.removeChild(rightChild);
						currNode.removeKey(splitted);
					}
				}
				
				
				
			}
		}
    }
	
	//Change the list returned to a list of integers answering the requirements
	public static List<Integer> BTreeBacktrackingCounterExample(){
		List<Integer> insertions = new LinkedList<>();
		insertions.add(10);
		insertions.add(40);
		insertions.add(100);
		insertions.add(200);
		insertions.add(300);
		insertions.add(7);
		insertions.add(8);
		insertions.add(9);
		insertions.add(15);
		insertions.add(20);
		insertions.add(25);
		insertions.add(30);
		insertions.add(35);
		insertions.add(45);
		insertions.add(50);
		insertions.add(55);
		insertions.add(150);
		insertions.add(170);
		insertions.add(180);
		insertions.add(250);
		insertions.add(270);
		insertions.add(280);
		insertions.add(350);
		insertions.add(370);
		insertions.add(380);
		insertions.add(37);
		return insertions;
	}
	
	public static void main (String [] args) {
        BacktrackingBTree<Integer> tree = new BacktrackingBTree<>();
        for (int i = 0; i < 3; i++) {
                tree.insert(i);
        }
        System.out.println(tree);
        tree.Backtrack();
	}
}
