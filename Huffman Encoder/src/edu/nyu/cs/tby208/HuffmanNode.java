package edu.nyu.cs.tby208;

public class HuffmanNode implements Comparable {

	public String letter;
	public Double frequency;
	public HuffmanNode left, right;
	
	public HuffmanNode(String letter, Double frequency) {
		this.letter=letter;
		this.frequency=frequency;
	}
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this(left.letter + right.letter, left.frequency + right.frequency);
		this.left = left;
		this.right = right;
		
	}
	public int compareTo(Object o) {
		HuffmanNode huff = (HuffmanNode) o;
		return this.frequency.compareTo(huff.frequency);
	}
	public String toString() {
		return "<"+letter+", "+frequency+">";
	}
	
	
	
	
	
	
	
	
	

}
