package Assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class HashTable {
	private int size;
	private LinkedList<Node>[] table;
	
	private static class Node{
		// creating key and value associated with it
		String key;
		int value;
		
		Node(String key, int value){
		this.key = key;
		this.value = value;
		}
	}
	
	public HashTable(int size) {
		this.size = size;
		// array of linked lists
		table = new LinkedList[size];
		for (int i = 0; i < size; i++) {
			// creates a list for each slot in the array
			table[i] = new LinkedList<>();
		}
	}
	
	private int hash(String key) {
		// uses hash code of the key and reduces it to an index
		int hash = key.hashCode() % size;
		// checks if negative
		if (hash < 0) {
			// will add size to make it positive
			hash += size;
		}
		return hash;
	}

	// put method will map a key to an index in the hash table
	public void put (String key, int value) {
		// index in the hash table where the pair will be stored
		int index = hash(key);
		// goes through linked list at each index
		for (Node node : table[index]) {
			// if the key exists then updates value and returns
			if (node.key.equals(key)) {
				node.value = value;
				return;
			}
		}
		// if the key is not found, adds a new node to linked list
		table[index].add(new Node(key, value));
	}
	
	// get method will get value associated with each key
	public int get(String key) {
		// index in the hash table where the pair will be stored
		int index = hash(key);
		// goes through linked list at each index
		for (Node node : table[index]) {
			// if found, return value
			if (node.key.equals(key)) {
				return node.value;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		// creates a hash table with a size of 100
		HashTable hashTable = new HashTable(100);
		// reads input from file
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		String line;
		// reads each line of the file
		while ((line = reader.readLine()) != null) {
			// splits the line into individual words
			String[] words = line.split("\\s+");
			// inserts each word into the hash table with a value of 1
			for (String word : words) {
				hashTable.put(word, 1);
			}
		}
		reader.close();
		
		// allows for user input
		Scanner scanner = new Scanner(System.in);
		// loops to search for words 
		while (true) {
			System.out.println("Enter word to search for, enter -1 to stop: ");
			// user input
			String word = scanner.next();
			// program stops if -1 is entered
			if(word.equals("-1")) {
				break;
			}
			// index in the hash table where each pair is stored
			int index = hashTable.hash(word);
			int count = 0;
			boolean found = false;
			// goes through the list at each index
			for (Node node : hashTable.table[index]) {
				count++;
				// if the key is found set to true and break loop
				if (node.key.equals(word)) {
					found = true;
					break;
				}
			}
			
			/* if found is true, prints found and count
			 * otherwise will print not found and count
			 */
			if (found) {
				System.out.println("Word found. Inspected " + count + " elements");
			} else {
				System.out.println("Word not found. Inspected " + count + " elements");
			}
		}
	}
}
