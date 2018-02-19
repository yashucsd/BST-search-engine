

package hw7;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.LinkedList;

public class BSTreeTester {
	BSTree<Integer> tree;

	// makes a tree for tests to manipulate
	@Before
	public void setUp() {
		tree = new BSTree<>();
	}

	// tests root retrieval
	@Test
	public void testGetRoot() {
		insert(2);
		int rV = tree.getRoot().getKey(); // root value
		assertEquals("Checks root retrieval", rV, 2);
	}

	// tests size tracking
	@Test
	public void testGetSize() {
		assertEquals("Checks empty tree size", tree.getSize(), 0);

		insert(1);
		insert(2);
		insert(3);

		assertEquals("Checks tree size", tree.getSize(), 3);
	}

	// tests insertion
	@SuppressWarnings("null")
	@Test
	public void testInsert() {
		try {
			insert((Integer) null);
			fail();
		} catch (NullPointerException e) {
			// pass
		}
		insert(3);
		assertEquals("Checks single insert",
				tree.getRoot().getKey(), new Integer(3));

		insert(4);
		insert(5);
		assertTrue("Checks multiple insert",
				tree.findKey(4) && tree.findKey(5));
	}

	// tests finding of key
	@Test
	public void testFindKey() {
		try {
			tree.findKey(null);
			fail();
		} catch (NullPointerException e) {
			// pass
		}

		insert(4);

		assertTrue("Checks after single in", tree.findKey(4));

		insert(5);
		insert(6);

		assertTrue("Checks after multiple in", tree.findKey(5));
	}

	// tests setting and accessing of information of node
	@Test
	public void testInformation() {
		insert(1);
		insert(0);
		insert(99);

		try {
			tree.insertInformation(null, null);
			fail();
		} catch (NullPointerException e) {
			try {
				tree.insertInformation(1, null);
				fail();
			} catch (NullPointerException f) {
				try {
					tree.insertInformation(null, 3);
					fail();
				} catch (NullPointerException g) {
					// pass all
				}
			}
		}

		try {
			tree.findMoreInformation(null);
			fail();
		} catch (NullPointerException e) {
			try {
				tree.findMoreInformation(2);
				fail();
			} catch (IllegalArgumentException f) {
				// pass both
			}
		}

		tree.insertInformation(1, 2);
		LinkedList<Integer> a = tree.findMoreInformation(1);

		assertEquals("Checks single", a.get(0), new Integer(2));

		tree.insertInformation(99, 100);
		tree.insertInformation(99, 101);
		tree.insertInformation(99, 110);
		LinkedList<Integer> b = tree.findMoreInformation(99);

		assertEquals("Checks multiple", b.get(0), new Integer(100));
		assertEquals("Checks multiple", b.get(1), new Integer(101));
		assertEquals("Checks multiple", b.get(2), new Integer(110));
	}

	// tests in order traversal array for order
	@Test
	public void testInOrderTraversal() {
		Integer[] a = new Integer[6];
		try {
			tree.inorderTraversal(a);
			fail();
		} catch (NullPointerException e) {
			// pass
		}

		insert(0);
		tree.inorderTraversal(a);

		assertEquals("Checks single", a[0], new Integer(0));

		insert(2);
		insert(7);
		insert(9);
		insert(4);
		insert(6);
		tree.inorderTraversal(a);

		for (Integer x : a) {
			System.out.print(x + " ");
		}

		assertEquals("Checks multiple", a[0], new Integer(0));
		assertEquals("Checks multiple", a[1], new Integer(2));
		assertEquals("Checks multiple", a[2], new Integer(4));
		assertEquals("Checks multiple", a[3], new Integer(6));
		assertEquals("Checks multiple", a[4], new Integer(7));
		assertEquals("Checks multiple", a[5], new Integer(9));
	}

	// tests height calculation
	@Test
	public void testFindHeight() {
		assertEquals("Checks empty", tree.findHeight(), -1);

		insert(9);
		assertEquals("Checks single", tree.findHeight(), 0);

		insert(2);
		assertEquals("Checks double", tree.findHeight(), 1);

		insert(10);
		assertEquals("Checks triple", tree.findHeight(), 1);

		insert(7);
		insert(4);
		assertEquals("Checks multiple", tree.findHeight(), 3);
	}

	// tests leaf counting
	@Test
	public void testLeafCount() {
		assertEquals("Checks empty", tree.leafCount(), 0);

		insert(9);
		assertEquals("Checks single", tree.leafCount(), 1);

		insert(2);
		assertEquals("Checks double", tree.leafCount(), 1);

		insert(10);
		assertEquals("Checks triple", tree.leafCount(), 2);

		insert(1);
		insert(3);
		insert(11);
		assertEquals("Checks multiple", tree.leafCount(), 3);
	}

	// an insert method the tests can use
	private void insert(int k) {
		tree.insert(k);
	}
}