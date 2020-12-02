/*  Program: Deschubs
 *  Author : Wade Linder
 *  Date   : 11/06/20
 *  Course : CS375 Software Engineering II
 *  Compile: javac Deschubs.java
 *  Execute: java Deschubs archive-name
 *  Note   : Extract files from an archive
 */

import java.io.IOException;
import java.io.File;

public class Deschubs
{
	private static final int R = 256;
	private static final int L = 4096;
	private static final int W = 12;
	public static boolean logging = true;
	
  public static void expandArc(String[] args) throws IOException
  {
  	
	
	BinaryOut out = null;
	BinaryIn in = null;
	
	in = new BinaryIn(args[0]);
  	char sep =  (char) 255;  // all ones 11111111

  	// nerf through archive, extracting files
  	// int lengthoffilename, sep, filename, sep, lengthoffile, sep, bits

		if(in.isEmpty()) {
			System.out.println("Archive is empty."); }
		else
		{
			while(! in.isEmpty()){
			  try {
				int filenamesize = in.readInt();
				sep = in.readChar();
				String filename="";
				for(int i = 0; i < filenamesize; i++)
				  filename += in.readChar();

				sep = in.readChar();
				long filesize = in.readLong();
				sep = in.readChar();
				System.out.println("Extracting file: " + filename + " ("+ filesize +").");
				out = new BinaryOut(filename);
				for(int i = 0; i < filesize; i++)
				out.write(in.readChar());

			  } finally {
				if (out != null)
				out.close();
			  }
			}
		}
  }
		
      public static void expandL() {
        String[] st = new String[L];
        int i; 

        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = ""; 

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }

	public static void expandH() {

        Node root = readTrie(); 

        int length = BinaryStdIn.readInt();

        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.flush();
    }
	    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }


    public static void err_print(String msg)
    {
	if (logging)
	    System.err.print(msg);
    }

    public static void err_println(String msg)
    {
	if (logging)
	    {
		System.err.println(msg);
	    }
    }

    private static Node buildTrie(int[] freq) {

        MinPQ<Node> pq = new MinPQ<Node>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        while (pq.size() > 1) {
            Node left  = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
	    err_println("buildTrie parent " + left.freq + " " + right.freq);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
	    err_println("T" + x.ch);
            return;
        }
        BinaryStdOut.write(false);
	err_print("F");

        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st[x.ch] = s;
	    err_println("buildCode " + x.ch + " " + s);
        }
    }
    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf) {
	    char x = BinaryStdIn.readChar();
	    err_println("t: " + x );
            return new Node(x, -1, null, null);
        }
        else {
	    err_print("f");
            return new Node('\0', -1, readTrie(), readTrie());
        }
    }


	public static void main(String[] args) throws IOException{
		String filename = args[0];
        if      (FilenameUtils.getExtension(filename) == "hh") expandH();
        else if (FilenameUtils.getExtension(filename) == "ll") expandL();
		else if (FilenameUtils.getExtension(filename) == "zl" || FilenameUtils.getExtension(filename) == "zh") expandArc(args);
        else throw new RuntimeException("Illegal command line argument");
    }
}
