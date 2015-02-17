import java.util.LinkedList;

/*
 * Implementation of the AVL tree. Please note that it can only hold data of comparable types
 * 		insertion is done in O[N log(N)]
 * 		search is done in O[log (N)]
 * 		deletion is currently NOT supported
 * 	
 * Contains the following BST traversal methods:
 * 		Pre-order traversal
 * 		In-order traversal
 * 		Post-order traversal
 * 		Level-order traversal
 */

/**
 * @author Jin Zhe
 */
class AVLTree <T extends Comparable<T>>{
	private TreeNode root;
	/**
	 * constructor: create an empty tree
	 */
	public AVLTree(){
		root = null;
	}

	/**
	 * search binary tree for given data
	 */
	public boolean contains(T data){
		return contains(root, data);
	}
	public boolean contains(TreeNode node, T data){
		// base case: when we have found data
		if (data.compareTo(node.data) == 0)
			return true;
		
		// if data > node.data
		if (data.compareTo(node.data) > 0){
			if (!node.rightChild.isEmpty())	return contains(node.rightChild, data);	// recurse right
			return false;	// else base case: tree does not contain data
		}
		else{
			if (!node.leftChild.isEmpty())	return contains(node.leftChild, data);	// recurse left
			return false;	// else base case: tree does not contain data
		}
	}
	
	
	/**
	 * Inserts a data in the AVL tree
	 */
	public void insert(T data){
		TreeNode node = new TreeNode(data);
		if (isEmpty()) root = node;		// if AVL tree is empty, assign to root node
		else root = insert(root, data);	// else, insert to tree and update root node
	}

	/**
	 * Inserts a list of data values in the AVL tree
	 */
	public void insert(T[] dataList){
		for (T data: dataList) insert(data);
	}

	/**
	 * Recursively inserts a node in the subtree
	 * @return updated root node of resulting AVL subtree
	 */
	public TreeNode insert(TreeNode node, T data){
		// base case: if subtree is empty, insert data as node
		if (node.isEmpty()){
			return new TreeNode(data);
		}
		// else if subtree isn't empty
		else{
			// if data is greater or equal, insert right
			if (data.compareTo(node.data) >= 0){        
				node.rightChild = insert(node.rightChild, data);
				node.rightChild.parent = node;
			}
			// else if data is smaller, insert left
			else{         
				node.leftChild = insert(node.leftChild, data);
				node.leftChild.parent = node;
			}

			node.height = Math.max(node.leftChild.height, node.rightChild.height) + 1; 	// update height
			node = balance(node);                             // balances the BST for each insertion and update
			return node;
		}
	}

	/**
	 *  Balances the BST via rotations
	 *  Constant time operation
	 */
	public TreeNode balance(TreeNode node){
		int balanceFactor = getBalanceFactor(node);                  // balance factor of current node
		int leftBalanceFactor = getBalanceFactor(node.leftChild);    // balance factor of left node
		int rightBalanceFactor = getBalanceFactor(node.rightChild);  // balance factor of right node

		// if AVL property is violated (balance factor = 2 or -2), rotate
		if (balanceFactor == 2){
			if (leftBalanceFactor == 1)         // LL case
				return rotateRight(node);
			else if (leftBalanceFactor == -1){  // LR case
				rotateLeft(node.leftChild);
				return rotateRight(node);
			}
			else return null; // should no hit this step
		}

		else if (balanceFactor == -2){
			if (rightBalanceFactor == -1)       // RR case
				return rotateLeft(node);
			else if (rightBalanceFactor == 1){  // RL case
				rotateRight(node.rightChild);
				return rotateLeft(node);
			}
			else return null; // should not hit this step
		}

		// else no re-balancing is needed, just return the node itself
		else return node;
	}

	/**
	 * Returns the balance factor of the current subtree
	 */
	public int getBalanceFactor(TreeNode node){
		if (node.isEmpty()) return 0;
		int leftHeight = (node.leftChild.isEmpty())? 0: node.leftChild.height;
		int rightHeight = (node.rightChild.isEmpty())? 0: node.rightChild.height;
		return (leftHeight - rightHeight);
	}

	/**
	 * Left rotate the given subtree and return the new root
	 * Constant time operation
	 */
	public TreeNode rotateLeft(TreeNode node){
		// rotation steps
		TreeNode temp = node.rightChild;
		temp.parent = node.parent;
		node.parent = temp;
		node.rightChild = temp.leftChild;
		temp.leftChild.parent = node;
		temp.leftChild = node;

		// link back to the parent node connecting to this node
		if (!temp.parent.isEmpty() && temp.parent.leftChild == node)
			temp.parent.leftChild = temp;
		else if (!temp.parent.isEmpty() && temp.parent.rightChild == node)
			temp.parent.rightChild = temp;

		// update height, node first followed by temp
		node.height = Math.max(node.leftChild.height, node.rightChild.height) + 1; 
		temp.height = Math.max(temp.leftChild.height, temp.rightChild.height) + 1;
		return temp; // return the new root node of new node
	}

	/**
	 * Right rotate the given subtree and return the new root
	 * Constant time operation
	 */
	public TreeNode rotateRight(TreeNode node){
		// rotation steps
		TreeNode temp = node.leftChild;
		temp.parent = node.parent;
		node.parent = temp;
		node.leftChild = temp.rightChild;
		temp.rightChild.parent = node;
		temp.rightChild = node;

		// link back to the parent node connecting to this node
		if (!temp.parent.isEmpty() && temp.parent.leftChild == node)
			temp.parent.leftChild = temp;
		else if (!temp.parent.isEmpty() && temp.parent.rightChild == node)
			temp.parent.rightChild = temp;

		// update height, node first followed by temp
		node.height = Math.max(node.leftChild.height, node.rightChild.height) + 1;
		temp.height = Math.max(temp.leftChild.height, temp.rightChild.height) + 1;
		return temp; // return the new root node of new node
	}

	/**
	 * Returns true if AVL tree is empty, else false
	 */
	public boolean isEmpty(){
		return (root == null);
	}

	/**
	 * Returns height of tree
	 */
	public int getHeight(){
		return root.height;
	}

	/**
	 * Returns weight
	 * O[N]
	 */
	public int getWeight(){
		return getWeight(root);
	}
	public int getWeight(TreeNode node){
		if (node.isEmpty()) return 0;
		return getWeight(node.leftChild) + getWeight(node.rightChild) + 1;
	}
	/**
	 * Preorder traversal
	 */
	public void printPreorder(){
		printInorder(root);
	}
	public void printPreorder(TreeNode node){
		System.out.print(node.data + ", ");
		if (!node.leftChild.isEmpty()) printPreorder(node.leftChild);
		if (!node.rightChild.isEmpty()) printPreorder(node.rightChild);
	}
	
	/**
	 * Inorder traversal
	 */
	public void printInorder(){
		printInorder(root);
	}
	public void printInorder(TreeNode node){
		if (!node.leftChild.isEmpty()) printInorder(node.leftChild);
		System.out.print(node.data + ", ");
		if (!node.rightChild.isEmpty()) printInorder(node.rightChild);
	}
	
	/**
	 * Postorder traversal
	 */
	public void printPostorder(){
		printInorder(root);
	}
	public void printPostorder(TreeNode node){
		if (!node.leftChild.isEmpty()) printPostorder(node.leftChild);
		if (!node.rightChild.isEmpty()) printPostorder(node.rightChild);
		System.out.print(node.data + ", ");
	}
	
	/**
	 * Levelorder traversal
	 * prints the binary tree level order using BFS
	 */
	public void printLevelorder(){
		LinkedList<Pair<TreeNode, Integer>> queue = new LinkedList<Pair<TreeNode, Integer>>();	// queue for BFS
		Integer level = 0;	// level starts at root node's height
		queue.offer(new Pair<TreeNode, Integer>(root, level));
		while (!queue.isEmpty()){
			Pair<TreeNode, Integer> item = queue.poll();
			TreeNode node = item.first;
			Integer currentLevel = item.second;
			// if advancing to new level
			if (currentLevel != level){
				System.out.println();
				level = currentLevel;
			}
			System.out.print(node.data + " ");
//			if (!node.leftChild.isEmpty()) queue.offer(new Pair<TreeNode, Integer>(node.leftChild, currentLevel + 1));
//			if (!node.rightChild.isEmpty()) queue.offer(new Pair<TreeNode, Integer>(node.rightChild, currentLevel + 1));
			if (!node.isEmpty() && currentLevel + 1 < root.height){
				queue.offer(new Pair<TreeNode, Integer>(node.leftChild, currentLevel + 1));
				queue.offer(new Pair<TreeNode, Integer>(node.rightChild, currentLevel + 1));
			}
		}
		System.out.println();
	}
	
	// Pair class
	private class Pair <K, V>{
		K first;
		V second;
		public Pair(K first, V second){
			this.first = first;
			this.second = second;
		}
	}
	
	// TreeNode class
	private class TreeNode{
		protected T data;                 // data contained in node
		protected TreeNode parent, leftChild, rightChild;
		protected int height;	// height of subtree with node as root

		/**
		 * Constructor for TreeNode
		 */
		// creates empty node
		public TreeNode(){
			parent = leftChild = rightChild = null;
			height = 0;
		}
		// creates node with data
		public TreeNode(T data){
			this();
			this.data = data;
			parent = new TreeNode();
			leftChild = new TreeNode();
			rightChild = new TreeNode();
			height = 1;
		}

		public boolean isEmpty(){
			return (data == null);
		}
	}
}