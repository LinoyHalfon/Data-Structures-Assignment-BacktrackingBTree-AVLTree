import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;

public class AVLTree implements Iterable<Integer> {
    // You may edit the following nested class:
    protected class Node {
    	public Node left = null;
    	public Node right = null;
    	public Node parent = null;
    	public int height = 0;
    	public int value;
    	public int size = 1;
    	

    	public Node(int val) {
            this.value = val;
        }

        public void updateHeight() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        public int getBalanceFactor() {
            int leftHeight = (left == null) ? -1 : left.height;
            int rightHeight = (right == null) ? -1 : right.height;

            return leftHeight - rightHeight;
        }
    }
    
    protected Node root;
    protected Deque dq = new ArrayDeque<Object>();
    
    //You may add fields here.
    
    public AVLTree() {
    	this.root = null;
    }
    
    /*
     * IMPORTANT: You may add code to both "insert" and "insertNode" functions.
     */
	public static void main(String[] args){
		BacktrackingAVL tree = new BacktrackingAVL();
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        tree.insert(8);
        tree.insert(9);
        tree.insert(11);
        tree.insert(22);
        tree.insert(33);
        tree.insert(44);
        tree.insert(55);
        tree.insert(66);
        tree.insert(77);
        tree.insert(12);
        tree.insert(13);
        tree.insert(14);
        tree.insert(15);
        tree.insert(16);
        tree.insert(17);
        tree.insert(18);
        tree.insert(19);
        tree.insert(20);
        tree.insert(21);
        tree.insert(23);
        tree.insert(24);
        tree.insert(25);
        tree.insert(26);
        tree.insert(27);
        tree.Backtrack();
        System.out.println(tree.root.height);
	}
    
    public void insert(int value) {
    	root = insertNode(root, value);
    }
	
	
	protected Node insertNode(Node node, int value) {
	    // Perform regular BST insertion
        if (node == null) {
        	Node insertedNode = new Node(value);
        	dq.addLast(insertedNode);
            return insertedNode;
        }

        if (value < node.value) {
            node.left  = insertNode(node.left, value);
            node.left.parent = node;
        }
        else {
            node.right = insertNode(node.right, value);
            node.right.parent = node;
        }
            
        node.updateHeight();
        node.size = node.size+1;

        /* 
         * Check For Imbalance, and fix according to the AVL-Tree Definition
         * If (balance > 1) -> Left Cases, (balance < -1) -> Right cases
         */
        
        int balance = node.getBalanceFactor();
        
        
        if (balance > 1) {
            if (value > node.left.value) { 
            	 dq.addLast(node.left); dq.addLast("right");
                node.left = rotateLeft(node.left);
                
            }       
            dq.addLast(node); dq.addLast("left");
            node = rotateRight(node); 
            
        } else if (balance < -1) {
            if (value < node.right.value) {
            	 dq.addLast(node.right); dq.addLast("left");
                node.right = rotateRight(node.right);
            }            
            dq.addLast(node); dq.addLast("right"); 
            node = rotateLeft(node);
        }
        return node;
    }
    
	// You may add additional code to the next two functions.
    protected Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;
        
      //update size
        if (y.left != null) {
        	if (y.right != null) 
        		y.size = y.left.size + y.right.size + 1;
        	else
        		y.size = y.left.size + 1;
        }
        else { // y.left == null
        	if (y.right != null)
        		y.size = y.right.size + 1;
        	else
        		y.size = 1;
        }
        
        if (x.left != null) {
        	if (x.right != null) 
        		x.size = x.left.size + x.right.size + 1;
        	else
        		x.size = x.left.size + 1;
        }
        else { // x.left == null
        	if (x.right != null)
        		x.size = x.right.size + 1;
        	else
        		x.size = 1;
        }
        
        //Update parents
        if (T2 != null) {
        	T2.parent = y;
        }

        x.parent = y.parent;
        y.parent = x;
        
        y.updateHeight();
        x.updateHeight();

        // Return new root
        return x;
    }

    protected Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;
        
        
        //update size
        if (x.left != null) {
        	if (x.right != null) 
        		x.size = x.left.size + x.right.size + 1;
        	else
        		x.size = x.left.size + 1;
        }
        else { // y.left == null
        	if (x.right != null)
        		x.size = x.right.size + 1;
        	else
        		x.size = 1;
        }
        
        if (y.left != null) {
        	if (y.right != null) 
        		y.size = y.left.size + y.right.size + 1;
        	else
        		y.size = y.left.size + 1;
        }
        else { // x.left == null
        	if (y.right != null)
        		y.size = y.right.size + 1;
        	else
        		y.size = 1;
        }
        
        //Update parents
        if (T2 != null) {
        	T2.parent = x;
        }
        
        y.parent = x.parent;
        x.parent = y;
        

        x.updateHeight();
        y.updateHeight();

        // Return new root
        return y;
    }
    
    public void printTree() {
    	TreePrinter.print(this.root);
    }

    /***
     * A Printer for the AVL-Tree. Helper Class for the method printTree().
     * Not relevant to the assignment.
     */
    private static class TreePrinter {
        private static void print(Node root) {
            if(root == null) {
                System.out.println("(XXXXXX)");
            } else {    
                final int height = root.height + 1;
                final int halfValueWidth = 4;
                int elements = 1;
                
                List<Node> currentLevel = new ArrayList<Node>(1);
                List<Node> nextLevel    = new ArrayList<Node>(2);
                currentLevel.add(root);
                
                // Iterating through the tree by level
                for(int i = 0; i < height; i++) {
                    String textBuffer = createSpaceBuffer(halfValueWidth * ((int)Math.pow(2, height-1-i) - 1));
        
                    // Print tree node elements
                    for(Node n : currentLevel) {
                        System.out.print(textBuffer);
        
                        if(n == null) {
                            System.out.print("        ");
                            nextLevel.add(null);
                            nextLevel.add(null);
                        } else {
                            System.out.printf("(%6d)", n.value);
                            nextLevel.add(n.left);
                            nextLevel.add(n.right);
                        }
                        
                        System.out.print(textBuffer);
                    }
        
                    System.out.println();
                    
                    if(i < height - 1) {
                        printNodeConnectors(currentLevel, textBuffer);
                    }
        
                    elements *= 2;
                    currentLevel = nextLevel;
                    nextLevel = new ArrayList<Node>(elements);
                }
            }
        }
        
        private static String createSpaceBuffer(int size) {
            char[] buff = new char[size];
            Arrays.fill(buff, ' ');
            
            return new String(buff);
        }
        
        private static void printNodeConnectors(List<Node> current, String textBuffer) {
            for(Node n : current) {
                System.out.print(textBuffer);
                if(n == null) {
                    System.out.print("        ");
                } else {
                    System.out.printf("%s      %s",
                            n.left == null ? " " : "/", n.right == null ? " " : "\\");
                }
    
                System.out.print(textBuffer);
            }
    
            System.out.println();
        }
    }

    /***
     * A base class for any Iterator over Binary-Search Tree.
     * Not relevant to the assignment, but may be interesting to read!
     * DO NOT WRITE CODE IN THE ITERATORS, THIS MAY FAIL THE AUTOMATIC TESTS!!!
     */
    private abstract class BaseBSTIterator implements Iterator<Integer> {
        private List<Integer> values;
        private int index;
        public BaseBSTIterator(Node root) {
            values = new ArrayList<>();
            addValues(root);
            
            index = 0;
        }
        
        @Override
        public boolean hasNext() {
            return index < values.size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return values.get(index++);
        }
        
        protected void addNode(Node node) {
            values.add(node.value);
        }
        
        abstract protected void addValues(Node node);
    }
    
    public class InorderIterator extends BaseBSTIterator {
        public InorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(Node node) {
            if (node != null) {
                addValues(node.left);
                addNode(node);
                addValues(node.right);
            }
        }    
      
    }
    
    public class PreorderIterator extends BaseBSTIterator {

        public PreorderIterator(Node root) {
            super(root);
        }

        @Override
        protected void addValues(AVLTree.Node node) {
            if (node != null) {
                addNode(node);
                addValues(node.left);
                addValues(node.right);
            }
        }        
    }
    
    @Override
    public Iterator<Integer> iterator() {
        return getInorderIterator();
    }
    
    public Iterator<Integer> getInorderIterator() {
        return new InorderIterator(this.root);
    }
    
    public Iterator<Integer> getPreorderIterator() {
        return new PreorderIterator(this.root);
    }
}
