package com.test.sony.util;


import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * A command line utility in Java that generates a CSV (comma-separated values) report of the sizes and md5 hashes of
 * all files contained in a given directory tree of arbitrary depth.
 * <p>
 * Each line of output has three values:
 * path(relative to the provided input directory), size (in bytes), and md5 hash.
 * The output CSV file is suitable for opening in either a text editor or a spreadsheet tool such as Microsoft Excel
 * or Google Sheets.
 *
 * @author nikhilabhiman
 */
public class SonyFileUtility {

    private static MessageDigest messageDigest;

    private static String directoryPath;

    private static StringBuilder sb;


    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot initialize SHA-512 hash function", e);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Enter directory path: ");
        Scanner scanner = new Scanner(System.in);
        directoryPath = scanner.nextLine();
        System.out.println("Directory path is " + directoryPath);

        File dir = new File(directoryPath);

        if (!dir.isDirectory()) {
            System.out.println("Supplied Directory does not exist.");
            return;
        }

        // Generates the sample CSV file - sonytest.csv
        PrintWriter pw = new PrintWriter(new File("sonytest.csv"));
        sb = new StringBuilder();
        sb.append("path");
        sb.append(',');
        sb.append("size");
        sb.append(',');
        sb.append("md5 hash");
        sb.append('\n');
        SonyFileUtility.generateCSVFile(dir);
        pw.write(sb.toString());
        pw.close();
        System.out.println("Done writing to CSV file!");
        System.out.println("\n");
    }

    /**
     * This method generates CSV file containing all the files based on given directory path and scans through all
     * the sub-directories inside the directory tree
     *
     * @param directory
     * @throws FileNotFoundException
     */
    public static void generateCSVFile(File directory) throws FileNotFoundException {
        for (File dirChild : directory.listFiles()) {
            // Iterate all file sub directories recursively
            if (dirChild.isDirectory()) {
                generateCSVFile(dirChild);
            } else {
                try {
                    // Read file as bytes
                    FileInputStream fileInput = new FileInputStream(dirChild);
                    byte fileData[] = new byte[(int) dirChild.length()];
                    fileInput.read(fileData);
                    fileInput.close();
                    String inputDir = directoryPath.substring(directoryPath.lastIndexOf("/") + 1, directoryPath.length());
                    String outputPath = dirChild.getAbsolutePath();
                    String finalRelativePath = outputPath.substring(outputPath.lastIndexOf(inputDir), outputPath.length());
                    //This gives the size of the file
                    long fileSize = dirChild.length();
                    // Create unique hash for current file
                    String uniqueFileHash = new BigInteger(1, messageDigest.digest(fileData)).toString(16);
                    sb.append(finalRelativePath);
                    sb.append(',');
                    sb.append(fileSize);
                    sb.append(',');
                    sb.append(uniqueFileHash);
                    sb.append('\n');
                } catch (IOException e) {
                    throw new RuntimeException("Cannot read file " + dirChild.getAbsolutePath(), e);
                }
            }
        }
    }

}