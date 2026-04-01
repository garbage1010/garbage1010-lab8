import java.util.regex.*;
import java.util.Scanner;
import java.io.*;

public class WordCounter {
    public static void main(String[] args) {
        String path;
        if (args.length > 0) {
            path = args[0];
        } else {
            path = null;
        }
        String stopword;
        if (args.length > 1) {
            stopword = args[1];
        } else {
            stopword = null;

        Scanner keyboard = new Scanner(System.in);

        // userprompt
        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.println("Enter 1 to process a file, or 2 to process text:");
            String line = keyboard.nextLine().trim();
            try {
                choice = Integer.parseInt(line);
                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid option. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter 1 or 2.");
            }
        }

        StringBuffer text = null;

        if (choice == 1) {
            // Process a file
            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println(e.toString());
                text = new StringBuffer("");
            }
        } else {
            // Process text from terminal
            text = new StringBuffer(path != null ? path : "");
        }

        // Process
        try {
            int count = processText(text, stopword);
            System.out.println("Found " + count + " words.");
        } catch (TooSmallText e) {
            System.out.println(e.toString());
        } catch (InvalidStopwordException e) {
            // retry once
            System.out.println("Stopword not found. Please enter a new stopword:");
            String newStopword = keyboard.nextLine().trim();
            try {
                int count = processText(text, newStopword);
                System.out.println("Found " + count + " words.");
            } catch (Exception e2) {
                System.out.println(e2.toString());
            }
        }
    }
    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText{
        
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher regexMatcher = regex.matcher(text);
        
        int count = 0;
        int stopcount = 0;
        boolean stopwordFound = false;
        while (regexMatcher.find()) {
            count++;
            if (!stopwordFound) {
                stopcount++;
                if (stopword != null && regexMatcher.group().equals(stopword)) {
                    stopwordFound = true;
                }
            }
        }
        if (count < 5) {
            throw new TooSmallText("Only " + count + " words.");
        }

        if (stopword != null && !stopwordFound) {
            throw new InvalidStopwordException("No stopword found " + stopword);
        }

        return (stopword == null) ? count : stopcount;
    }
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner fileScanner = null;
        String currentPath = path;

        while (true) {
            try {
                fileScanner = new Scanner(new File(currentPath));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please enter a valid filename:");
                Scanner keyboardScanner = new Scanner(System.in);
                currentPath = keyboardScanner.nextLine();
            }
        }

        StringBuilder sb = new StringBuilder();
        while (fileScanner.hasNextLine()) {
            sb.append(fileScanner.nextLine());
        }
        fileScanner.close();

        if (sb.length() == 0) {
            throw new EmptyFileException(currentPath + " was empty");
        }

        return new StringBuffer(sb.toString());
    }
}
