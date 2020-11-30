/*  Program: SchubsH
 *  Author : Wade Linder
 *  Date   : Fall 2020
 *  Course : CS375 Software Engineering II
 *  Compile: javac SchubsH.java
 *  Execute: java SchubsH - < file-name
 *	Dependencies: BinaryIn.java BinaryOut.java
 *  Note   : Encrypts the text in a file.
 *  Needs  : input from user
 */
 
 import java.io.File;
 import java.io.IOException;
 import org.apache.commons.io.FilenameUtils;


public class SchubsH {

    private static final int R = 256;
    public static boolean logging = true;

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

    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++)
            freq[input[i]]++;

        Node root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st, root, "");

        writeTrie(root);
	err_println("writeTrie");

        BinaryStdOut.write(input.length);
	err_println("writing input length " + input.length);

	err_println("Encrypting your data... ");
        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
	    err_print("Char " + input[i] + " ");
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
		    err_print("0");
                }
                else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
		    err_print("1");
                }
                else throw new RuntimeException("Illegal state");
            }
	    err_println("");
        }

        BinaryStdOut.flush();
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

    public static void main(String[] args) {
		String filename = args[2] + ".hh";
	try {
		File myObj = new File(filename);
		if (myObj.createNewFile()) {
			System.out.println("File created: " + myObj.getName());
		} else {
			System.out.println("File already exists.");
		}
    } catch (IOException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
    }
		if      (args[0].equals("-")) compress();
        else throw new RuntimeException("Illegal command line argument.");
    }

}
