package edu.nyu.cs.tby208;

import java.util.ArrayList;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class HuffmanConverter {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
//		Scanner in = new Scanner(System.in);
//		System.out.println("Enter the file name: ");
//		String fileName = in.nextLine();


		
		String contents = readContents(args[0]);
		System.out.println("Original text: \n");
		System.out.println(contents);



		HuffmanConverter huff = new HuffmanConverter(contents);
		System.out.println("Character Frequencies: \n");
		huff.recordFrequencies();
		
		huff.frequenciesToTree();
		System.out.println("\nCharacter Codes: \n");
		huff.treeToCode();
		System.out.println();
		System.out.println("\nEncoded Message: \n");
		String encodedMessage = huff.encodeMessage();
		
		System.out.println(encodedMessage.replaceAll(".{100}", "$0\n") + "\n");
		System.out.println("Number of bits needed to encode message in ASCII: " + contents.length()*8);
		System.out.println("Number of bits in huffman encoding: " + encodedMessage.length());
		System.out.println("\nDecoded Message: \n");
		String decodedMessage = huff.decodeMessage(encodedMessage);
		System.out.println(decodedMessage);

		System.out.println("Is the encoded method equal to the original(using the string.equals method)?: " + contents.equals(decodedMessage));
		System.out.println("\nThanks for using my software.");
	}
	// The # of chars in the ASCII table dictates
	// the size of the count[] & code[] arrays.
	public static final int NUMBER_OF_CHARACTERS = 256;
	
	// the contents of our message...
	private String contents;
	
	// the tree created from the msg
	private HuffmanTree huffmanTree;
	
	// tracks how often each character occurs
	private int count[];
	
	// the huffman code for each character
	private String code[];
	
	// stores the # of unique chars in contents
	private int uniqueChars = 0; //(optional)
	
	/** Constructor taking input String to be converted */
	
	public HuffmanConverter(String input)
	{
	this.contents = input;
	this.count = new int[NUMBER_OF_CHARACTERS];
	this.code = new String[NUMBER_OF_CHARACTERS];
	}
	
	/**
	* Records the frequencies that each character of our
	* message occurs...
	* I.e., we use 'contents' to fill up the count[] list...
	*/
	public void recordFrequencies() {
		char c;
		for (int i = 0; i < contents.length()-1; i++) {
			c = contents.charAt(i);
			this.count[(int) c]+=1;
		}
	}
	/**
	* Converts our frequency list into a Huffman Tree. We do this by
	* taking our count[] list of frequencies, and creating a binary
	* heap in a manner similar to how a heap was made in HuffmanTree's
	* fileToHeap method. Then, we print the heap, and make a call to
	* HuffmanTree.heapToTree() method to get our much desired
	* HuffmanTree object, which we store as huffmanTree.
	*/
	public void frequenciesToTree() {
		String l = "";
		double f;
		ArrayList<HuffmanNode> nodeList = new ArrayList<HuffmanNode>();
		//main conversion loop
		for (int i = 0; i < count.length; i++) {
				if (count[i]>0) {
					uniqueChars++;
					l=Character.toString((char) i);
					f=(double) count[i];
					HuffmanNode n = new HuffmanNode(l,f);
					nodeList.add(n);
				}
		}
		HuffmanNode[] nodeArr = new HuffmanNode[nodeList.size()];
		//convert arraylist to array
		for (int i = 0; i < nodeList.size(); i++) {
			nodeArr[i] = nodeList.get(i);
		}
		//construct heap
		BinaryHeap<HuffmanNode> heap = new BinaryHeap<HuffmanNode>(nodeArr);
		//print heap
		heap.printHeap();
		this.huffmanTree = HuffmanTree.createFromHeap(heap);
	}
	/**
	* Iterates over the huffmanTree to get the code for each letter.
	* The code for letter i gets stored as code[i]... This method
	* behaves similarly to HuffmanTree's printLegend() method...
	* Warning: Don't forget to initialize each code[i] to ""
	* BEFORE calling the recursive version of treeToCode...
	*/
	public void treeToCode() {
		for (int i = 0; i<code.length; i++) {
			this.code[i] = "";
		}
		treeToCode(huffmanTree.root, "");
	}
	/*
	* A private method to iterate over a HuffmanNode t using s, which
	* contains what we know of the HuffmanCode up to node t. This is
	* called by treeToCode(), and resembles the recursive printLegend
	* method in the HuffmanTree class. Note that when t is a leaf node,
	* t's letter tells us which index i to access in code[], and tells
	* us what to set code[i] to...
	*/
	private void treeToCode(HuffmanNode t, String s) {
		if (t.letter.length()>1){
			treeToCode(t.left, s+"0");
			treeToCode(t.right, s+"1");
			}
		else if (t.letter.length() == 1){
			this.code[(int) t.letter.charAt(0)] = s;
			System.out.println(t.letter+"="+s);
			}
	}
	/**
	* Using the message stored in contents, and the huffman conversions
	* stored in code[], we create the Huffman encoding for our message
	* (a String of 0's and 1's), and return it...
	*/
	public String encodeMessage() {
		char c;
		String huffmanEncoding = "";
		for (int i=0; i<contents.length(); i++) {
			c = contents.charAt(i);
			huffmanEncoding+=code[(int) c];
		}
		return huffmanEncoding;
	}
	/**
	* Reads in the contents of the file named filename and returns
	* it as a String. The main method calls this method on args[0]...
	*/
	public static String readContents(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner scan = new Scanner(file);
		String contents = "";
		while (scan.hasNextLine()) {
			contents+= scan.nextLine() + "\n";
		}
		scan.close();
		return contents;
	}
	/**
	* Using the encoded String argument, and the huffman codings,
	* re-create the original message from our
	* huffman encoding and return it...
	*/
	public String decodeMessage(String encodedStr) {
		String output = "";
		String tempString = "";
		  
		// loop through encodedStr, parsing off one character at a time until the String of binary digits is a recognizable Huffman Code for a character
		while (!encodedStr.isEmpty()){
		tempString = tempString + encodedStr.charAt(0);
		//System.out.println(tempString);
		  
		//loop through the code[] array to check if tempString is a recognizable character in Huffman Code
			for(int i =0; i<code.length; i++){
		//if tempString is a recognizable character, reset tempString, and add the character into our output string
				String stringHuffCode = code[i];
		  
		  
					if (stringHuffCode.equals(tempString)){
						tempString = "";
						String c = (char) i +"";
						output = output+c;
						break;
						}
		  
		  
			}
		  
		  
		  
		//cut off the first character of encodedStr
		encodedStr = encodedStr.substring(1);
		}
		  
		return output;
	}
		  
	
}

