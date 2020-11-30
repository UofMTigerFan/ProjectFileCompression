# ProjectFileCompression

This project features two forms of compression, each of which has their own strengths. SchubsH.java uses Huffman encoding while SchubsL.java uses LZW compression. Huffman encoding predicts the repeated usage of characters, assigning binary values to the characters based on how often they are used. Ones that are used more often have shorter binary values than ones that are used less often. LZW compression meanwhile is a highly commonly used compression algorithm that is commonly used with image or gif compression.

# How to run
1. Download the files
2. Place in whatever directory you seem fit
3. Open your favorite editor
4. Navigate to the files
5. Compile with maven: mvn compile
6. Compress with java SchubsH <filename> OR java SchubsL <filename> based on preference for compression algorithm
  
# How to test
1. Navigate to the ProjectFileCompression directory on your computer
2. Simply test with mvn test
