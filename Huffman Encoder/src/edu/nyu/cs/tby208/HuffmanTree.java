package edu.nyu.cs.tby208;
import java.util.*;
public class HuffmanTree {

	
	
	HuffmanNode root;
	
	// create huffman tree and set its root
	public HuffmanTree(HuffmanNode huff) {
		this.root = huff;
	}
	
	//print tree
	private void printLegend(HuffmanNode t, String s){
		
		if (t.letter.length()>1){
		printLegend(t.left, s+"0");
		printLegend(t.right, s+"1");
		}
		if (t.letter.length() == 1){
		System.out.println(t.letter+"="+s);
		}
	}
	
	//print tree, calls the recursive method printLegend(HuffmanNode t, String s)
	public void printLegend() {
		printLegend(root, "");
	}
	//method takes a legend and returns heap
	public static BinaryHeap legendToHeap(String legend) throws EmptyStringException {
		
		String l = "";
		Double f;
		String[] legendArray = legend.split(" ");
		
		//throw exception if string is empty
		if (legend.isEmpty()) throw new EmptyStringException();
		
		ArrayList<HuffmanNode> nodeList = new ArrayList<HuffmanNode>();
		//main conversion loop
		for (int i = 0; i < legendArray.length; i++) {
			if (Character.isAlphabetic(legendArray[i].charAt(0))) {
				l=legendArray[i];
				
			}
			else {
				f=Double.valueOf(legendArray[i]);
				HuffmanNode n = new HuffmanNode(l,f);
				nodeList.add(n);
				l="";
			}
			
		}
		
		HuffmanNode[] nodeArr = new HuffmanNode[nodeList.size()];
		//convert arraylist to array
		for (int i = 0; i < nodeList.size(); i++) {
			nodeArr[i] = nodeList.get(i);
		}
		//construct heap
		BinaryHeap<HuffmanNode> heap = new BinaryHeap<HuffmanNode>(nodeArr);
		//return heap
		return heap;
	}
	
	public static HuffmanTree createFromHeap(BinaryHeap b) {
		//create tree from heap
		HuffmanTree tree;
		while (b.getSize() > 1) {
			//remove smallest nodes and create new node with them as children
			HuffmanNode n1 = (HuffmanNode) b.deleteMin();
			HuffmanNode n2 = (HuffmanNode) b.deleteMin();
			HuffmanNode node = new HuffmanNode(n1, n2);
			//insert new node to heap
			b.insert(node);
		}
		//create tree with last node as root
		tree = new HuffmanTree((HuffmanNode) b.deleteMin());
		return tree;
	}
	
	public String toString() {
		return root.toString();
	}
	
	
	
}
