
package hw7;

import java.util.LinkedList;

public class BSTree<T extends Comparable<? super T>> {

	private int nelems;
	private BSTNode root;

	protected class BSTNode {
		T key;
		LinkedList<T> relatedInfo;
		BSTNode lChild;
		BSTNode rChild;

		/**
		 * A constructor that initializes the BSTNode instance variables.
		 * 
		 * @param lChild
		 *            left child
		 * @param rChild
		 *            right child
		 * @param relatedInfo
		 *            list o info
		 * @param key
		 *            node name/key
		 */
		public BSTNode(BSTNode lChild, BSTNode rChild,
				LinkedList<T> relatedInfo, T key) {
			this.lChild = lChild;
			this.rChild = rChild;
			this.relatedInfo = relatedInfo;
			this.key = key;
		}

		/**
		 * A constructor that initializes BSTNode variables. Note: This
		 * constructor is used when you want to add a key with no related
		 * information yet. In this case, you must create an empty LinkedList
		 * for the node.
		 * 
		 * @param lChild
		 *            left child
		 * @param rChild
		 *            right child
		 * @param key
		 *            node name/key
		 */
		public BSTNode(BSTNode lChild, BSTNode rChild, T key) {
			this.lChild = lChild;
			this.rChild = rChild;
			relatedInfo = new LinkedList<>();
			this.key = key;
		}

		/**
		 * Append new info to the end of the existing LinkedList of the node
		 * 
		 * @param info
		 *            the information being added to the node's list
		 */
		public void addNewInfo(T info) {
			relatedInfo.add(info);
		}

		/**
		 * Remove ‘info’ from the LinkedList of the node and return true. If the
		 * LinkedList does not contain the value ‘info’, return false
		 * 
		 * @param info
		 *            the infromation to remove from the node's liset
		 * @return whether or not the infomration was removed
		 */
		public boolean removeInfo(T info) {
			return relatedInfo.remove(info);
		}

		/**
		 * @return the left child of the node
		 */
		public BSTNode getLChild() {
			return lChild;
		}

		/**
		 * @param left
		 *            sets the left child of the node
		 */
		public void setLChild(BSTNode left) {
			lChild = left;
		}

		/**
		 * @return the right child of the node
		 */
		public BSTNode getRChild() {
			return rChild;
		}

		/**
		 * @param right
		 *            sets the right child of the node
		 */
		public void setRChild(BSTNode right) {
			rChild = right;
		}

		/**
		 * @return the node's related informaiton
		 */
		public LinkedList<T> getRelatedInfo() {
			return relatedInfo;
		}

		/**
		 * @param list
		 *            sets tthe ndoe's related infomation
		 */
		public void setRelatedInfo(LinkedList<T> list) {
			relatedInfo = list;
		}

		/**
		 * @return the key or name of the node
		 */
		public T getKey() {
			return key;
		}

		/**
		 * @return whether or not the node has children
		 */
		private boolean hasChildren() {
			return this.getLChild() != null || this.getRChild() != null;
		}
	}

	/**
	 * A 0-arg constructor that initializes root to null and nelems to 0
	 */
	public BSTree() {
		root = null;
		nelems = 0;
	}

	/**
	 * @return the root of BSTree. Returns null if the tree is empty
	 */
	public BSTNode getRoot() {
		return root;
	}

	/**
	 * @return the number of elements
	 */
	public int getSize() {
		return nelems;
	}

	/**
	 * Inserts a key into the BST. Throws NullPointerException if key is null.
	 * 
	 * @param key
	 *            the name of the new node
	 */
	public void insert(T key) {
		if (key == null)
			throw new NullPointerException();
		root = insert(key, root); // recursively adds new node
	}

	/**
	 * recurses the tree to find and add the new node
	 * 
	 * @param key
	 *            the name of the new node
	 * @param parent
	 *            the node being processed
	 * @return the potentially new node for redefinition
	 */
	private BSTNode insert(T key, BSTNode parent) {
		if (parent == null) {
			// creates and adds a new node if this node doesn't exist (yet)
			parent = new BSTNode(null, null, key);
			nelems++;
			return parent;
			// returns parent to redefine some parent's child
		}

		// works toward left child if less than current node, right otherwise
		if (key.compareTo(parent.getKey()) < 0)
			parent.setLChild(insert(key, parent.getLChild()));
		else if (key.compareTo(parent.getKey()) > 0)
			parent.setRChild(insert(key, parent.getRChild()));
		// recurses to following child to see if it's free to add the new node

		return parent;
		// returns parent to redefine some parent's child
	}

	/**
	 * Return true if the ‘key’ is found in the tree, false otherwise. Throw
	 * NullPointerException if key is null
	 * 
	 * @param key
	 *            the name of the node being searched for
	 * @return whether or not node is found
	 */
	public boolean findKey(T key) {
		if (key == null)
			throw new NullPointerException();
		return search(key, root) != null; // recursively searches to find node
	}

	/**
	 * recureses through the BST to find the node w matching value and return
	 * it's node object
	 * 
	 * @param key
	 *            the name of the node in search of
	 * @param parent
	 *            the node being surveyed
	 * @return the node to search for, or that is found
	 */
	private BSTNode search(T key, BSTNode parent) {
		if (parent == null) // node not found
			return null;

		int compare = key.compareTo(parent.getKey());
		if (compare == 0) // node is found!
			return parent;
		else if (compare < 0) // searches appropriate child of parent
			return search(key, parent.getLChild());
		else
			return search(key, parent.getRChild());
	}

	/**
	 * Inserts ‘info’ into the LinkedList of the node whose key is ‘key’. Throw
	 * NullPointerException if ‘key’ or ‘info’ is null Throw
	 * IllegalArgumentException if ‘key’ is not found in the BST
	 * 
	 * @param key
	 *            the name of node being searched for
	 * @param info
	 *            information to be added
	 */
	public void insertInformation(T key, T info) {
		if (key == null || info == null)
			throw new NullPointerException();

		BSTNode node = search(key, root);
		if (node == null)
			throw new IllegalArgumentException();

		node.addNewInfo(info);
	}

	/**
	 * Return the LinkedList of the node with key value ‘key’ Throw
	 * NullPointerException if key is null Throw IllegalArgumentException if
	 * ‘key’ is not found in the BST
	 * 
	 * @param key
	 *            the name of node being searched for
	 * @return information in node
	 */
	public LinkedList<T> findMoreInformation(T key) {
		if (key == null)
			throw new NullPointerException();

		BSTNode node = search(key, root);
		if (node == null)
			throw new IllegalArgumentException();

		return node.getRelatedInfo();
	}

	/**
	 * Performs a recursive inorder traversal of the BST and adds key values to
	 * the keyArray in order. Throws NullPointerException if BST is empty
	 * 
	 * @param keyArray
	 *            the array to add elements to
	 */
	public void inorderTraversal(T[] keyArray) {
		if (nelems == 0)
			throw new NullPointerException();
		traverse(root, keyArray, 0); // recursively traverses BST
	}

	/**
	 * recursively traverses BST in least to most fashion, adding to array a
	 * 
	 * @param parent
	 *            the node being considered
	 * @param a
	 *            the array being updated
	 * @param idx
	 *            the index of array a being updated
	 * @return an updated index
	 */
	private int traverse(BSTNode parent, T[] a, int idx) {
		if (parent == null) // no child, nothing added to array
			return idx;
		idx = traverse(parent.getLChild(), a, idx); // adds lesser elements
		a[idx++] = parent.getKey(); // adds current element to array
		idx = traverse(parent.getRChild(), a, idx); // adds greater elements

		return idx;
	}

	/**
	 * Returns the height of the tree. T he height of a tree is the length of
	 * the longest downward path to a leaf from the root. By convention, height
	 * of an empty tree is -1.
	 * 
	 * @return the height of the BST
	 */
	public int findHeight() {
		if (nelems == 0)
			return -1;
		return getDepth(root); // recursively finds height
	}

	/**
	 * recurses through BST, counting the number of branches
	 * 
	 * @param node
	 *            the node being considered
	 * @return the height of the node
	 */
	private int getDepth(BSTNode node) {
		if (node == null)
			return 0; // no branch
		if (node.hasChildren()) {
			// returns parent's branch to children added to the greatest of the
			// children's heights
			int l = getDepth(node.getLChild());
			int r = getDepth(node.getRChild());
			return Math.max(l, r) + 1;
		} else
			return 0;
	}

	/**
	 * @return the number of leaf nodes in the tree.
	 */
	public int leafCount() {
		return countLeaves(root);
	}

	/**
	 * counts the number of nodes on the tree that have no children
	 * 
	 * @param node the node being considered
	 * @return the number of leaves the node has underneath
	 */
	private int countLeaves(BSTNode node) {
		if (node == null)
			return 0;
		if (!node.hasChildren()) // node is child
			return 1;
		else // node is potential parent, returns sum of children's potential
				// leaves
			return countLeaves(node.getLChild())
					+ countLeaves(node.getRChild());
	}
}