/*  Program: SchubsArc
 *  Author : Wade Linder
 *  Date   : Fall 2020
 *  Course : CS375 Software Engineering II
 *  Compile: javac SchubsArc.java
 *  Execute: java SchubsArc archive-name file1 file2 .. filen
 *  Note   : archives n number of files
 */

import java.io.IOException;
import java.io.File;

public class SchubsArc
{
    static void tarit (String filename, String outName, BinaryOut out)
	{
		char separator = (char) 255; //All ones 11111111
		
		try {
			File in1 = new File(filename);
			if (!in1.exists() || !in1.isFile()) return;
			long filesize = in1.length();
			int filenamesize = filename.length();
			
			out.write(filenamesize);
			out.write(separator);
			
			out.write(filename);
			out.write(separator);
			
			out.write(filesize);
			out.write(separator);
			
			System.out.println("archive " + outName);
			System.out.println("adding file " + filename + " (" + filesize + ")");
			BinaryIn bin1 = new BinaryIn(filename);
			while(! bin1.isEmpty() ) {
				out.write( bin1.readChar( ));
			}
		}	catch( RuntimeException ex ){
				System.out.println(ex.toString());
				System.out.println("File not found " + filename);
			}
	}
	
	public static void main(String[] args) {	//Expect minimum 2 arguments, archive, file1
		BinaryOut out = new BinaryOut(args[0]);
		int count = args.length;
		int i = 1;
		try {
			while ( i < count )
			{
				tarit( args[i], args[0], out);
				i++;
			}
		}	finally {
			if(out != null)
				out.close();
		}
	}

}

