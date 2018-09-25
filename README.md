# sonytest
This project contains code to implement a command line utility in Java that generates a CSV (comma-separated values) report of the sizes and md5 hashes of all files contained in a given directory tree of arbitrary depth. Each line of output has three values: path (relative to the provided input directory), size (in bytes), and md5 hash. The output is suitable for opening in either a text editor or a spreadsheet tool such as Microsoft Excel or Google Sheets.

How to Run 
- Run main method in Java class and provide valid directory input in Console. For example, on my MacBook, I give "/Users/nikhilabhiman/Downloads/sonyfiles" as input in my IntelliJ command line console , where sonyfiles is my input directory and it contains sub-directories and .txt files

Output
- sonytest.csv file is generated which contains relative Input Directory Path, File size and md5 hash

