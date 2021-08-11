import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class infofetch {
    private static void copyStream(InputStream in, OutputStream out)
            throws IOException {
        int oneByte = in.read();
        while (oneByte >= 0) { // negative value indicates end-of-stream
            out.write(oneByte);
            oneByte = in.read();
        }
    }
    public static void main(String[] args) {
        InputStream fileIn = null;
        FileOutputStream fileOut = null;
        System.out.println("Please provide a URL Complete with http://");
        String urlString = TextIO.getln();
        System.out.println("Please provide the file name");
        String localFile = TextIO.getln();
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("Your URL has formatted wrongly please make sure you included http://");
            System.exit(1);
        }
        try {
            fileOut = new FileOutputStream(localFile);
        } catch (FileNotFoundException e) {
            File tempObj = new File(localFile);
            try {
                if (tempObj.createNewFile()) {
                    System.out.println("Creating new file: " + localFile);
                }
            } catch (IOException ioException) {
                System.out.println("An Error occurred when creating a new file, Check Directory permissions");
                ioException.printStackTrace();
            }
            if (tempObj.exists()) {
                System.out.println("This File Exist are you sure you want to overwrite it? (Y/n)");
                String Answer = TextIO.getln();
                if (Answer == "n" | Answer == "N") {
                    System.out.println("Exiting program please enter a new file name");
                    // realistically we would probably just append a 1 to the filename and continue but its late and feature creep.
                    System.exit(1);
                } else {
                    return;
                }
            }
            return;
        }
        try {
            if (url != null) {
                fileIn = url.openStream();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong, check your url is valid.");
            System.exit(1);
        }

        if (fileIn == null) {
            System.out.println("File In Stream never initialized");
            System.exit(1);
        }
        try {
            copyStream(fileIn, fileOut);
        } catch (IOException e) {
            System.out.println("Something went wrong in with the copy stream function.");
            e.printStackTrace();
            System.exit(1);
        }
        finally{
            try{
                fileIn.close();
            } catch (IOException e) {
                System.out.println("Something went wrong closing the streams");
                e.printStackTrace();
            }
            try{
                fileOut.close();
            } catch (IOException e) {
                System.out.println("Something went wrong closing the streams");
                e.printStackTrace();
            }
        }

    }
}
