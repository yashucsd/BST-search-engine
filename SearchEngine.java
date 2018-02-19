

package hw7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class SearchEngine {

	/**
	 * Populate a BST from a file
	 * 
	 * @param searchTree
	 *            BST to be populated
	 * @param fileName
	 *            name of the input file
	 * @returns false if file not found, true otherwise
	 */
	public static boolean populateSearchTree(BSTree<String> searchTree,
			String fileName) {
		File file = new File(fileName);
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				// read two lines - one for document and the next line for the
				// list of keywords
				String document = scanner.nextLine().trim();
				String keywords[] = scanner.nextLine().split(" ");

				if (!searchTree.findKey(document))
					searchTree.insert(document.toLowerCase());

				for (String a : keywords)
					searchTree.insertInformation(document, a.toLowerCase());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("\nFile not found!!");
			return false;
		}
		return true;
	}

	/**
	 * Search a query in a BST
	 * 
	 * @param searchTree
	 *            BST to be searched
	 * @param query
	 *            query string
	 */
	public static void searchMyQuery(BSTree<String> searchTree, String query) {
		String[] keys = new String[searchTree.getSize()];
		searchTree.inorderTraversal(keys); // stores key array
		LinkedList<String>[] info = moreInfoInOrderArray(searchTree);
		// stores information array

		String[] queries = query.split(" ");
		LinkedList<String> multi = new LinkedList<>();
		// stores elements for multi parameter search results
		LinkedList<String>[] responses = new LinkedList[queries.length];
		// stores list for each search element in array

		for (int j = 0; j < queries.length; j++) {
			// surveys each query
			for (int i = 0; i < keys.length; i++) {
				// surveys each key
				if (queries.length > 1 && info[i].containsAll(
						Arrays.asList(queries))) {
					if (!multi.contains(keys[i]))
						multi.add(keys[i]); // adds keys that match all search
											// queries
				} else if (info[i].contains(queries[j])) {
					if (responses[j] == null)
						responses[j] = new LinkedList<>();
					responses[j].add(keys[i]);
					// adds keys that have queried items to appropriate response
					// array list
				}
			}
		}

		if (queries.length > 1) {
			// prints the multiple query option, and only the lists that have
			// matches after the multi response
			print(query, multi);

			// prints empty for each if all are empty
			for (int i = 0; i < queries.length; i++)
				if (responses[i] != null || multi.isEmpty())
					print(queries[i], responses[i]);
		} else // prints responses for single query
			print(queries[0], responses[0]);
	}

	/**
	 * returns an array of LinkedLists of more information for each key in
	 * search tree
	 * 
	 * @param searchTree
	 *            the tree to traverse
	 * @return the array of informational linkedlists
	 */
	private static LinkedList<String>[] moreInfoInOrderArray(
			BSTree<String> searchTree) {
		String[] keys = new String[searchTree.getSize()];
		searchTree.inorderTraversal(keys); // stores array of keys

		LinkedList<String>[] info = new LinkedList[keys.length];
		for (int i = 0; i < keys.length; i++)
			info[i] = searchTree.findMoreInformation(keys[i]);
		// fills LL array with information from each key

		return info;
	}

	/**
	 * Print method
	 * 
	 * @param query
	 *            input
	 * @param documents
	 *            result of SearchMyQuery
	 */
	public static void print(String query, LinkedList<String> documents) {
		if (documents == null || documents.isEmpty())
			System.out.println("The search yielded no results for " + query);
		else {
			Object[] converted = documents.toArray();
			Arrays.sort(converted);
			System.out.println("Documents related to " + query
					+ " are: " + Arrays.toString(converted));
		}
	}

	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println("Invalid number of arguments passed");
			return;
		}

		BSTree<String> searchTree = new BSTree<>();

		String fileName = args[0];
		String query = args[1];

		// Create my BST from file
		boolean check = populateSearchTree(searchTree, fileName);
		if (check == false) {
			System.out.println("\nUnable to create search tree");
		}

		searchMyQuery(searchTree, query);
	}
}
