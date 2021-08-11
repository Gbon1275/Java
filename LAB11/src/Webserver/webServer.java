package Webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class webServer {

    private static final int LISTENING_PORT = 8080;

    private static final String ROOT_DIR = ".\\files\\rootDir";

    private static ArrayBlockingQueue<Socket> connectionQueue;

    private static final int THREAD_POOL_SIZE = 6;

    private static final int CONNECTION_QUEUE_SIZE = 3;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket connection;

        connectionQueue = new ArrayBlockingQueue<>(CONNECTION_QUEUE_SIZE);

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            ConnectionHandler worker = new ConnectionHandler();
            worker.start();
        }

        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
        }
        catch (Exception e) {
            System.out.println("Failed to create listening socket.");
            return;
        }
        
        System.out.println("Listening on port " + LISTENING_PORT);
        
        try {
            while (true) {
                connection = serverSocket.accept();
                System.out.println("\nConnection from "
                        + connection.getRemoteSocketAddress());
                handleConnection(connection);
            }
        }
        catch (Exception e) {
            System.out.println("Server socket shut down unexpectedly!");
            System.out.println("Error: " + e);
            System.out.println("Exiting.");
        }
    }

    private static class ConnectionHandler extends Thread {

        ConnectionHandler() {
            setDaemon(true);
        }
        public void run() {
            while (true) {
                try {
                    Socket connection = connectionQueue.take();
                    handleConnection(connection);
                }
                catch (Exception e) {
                }
            }
        }
    }

    private static void handleConnection(Socket connection) {
        Scanner in;
        PrintWriter printout;
        OutputStream out;

        String cmd = "";
        String fileName = "";
        String prctl = "";

        try{
            in = new Scanner(connection.getInputStream());
            printout = new PrintWriter(connection.getOutputStream());
            out = connection.getOutputStream();
            
            cmd = in.next();
            
            if (cmd.equalsIgnoreCase("index")){
                returnIndex(new File(ROOT_DIR), printout);
            }
            else if (!cmd.equalsIgnoreCase("get")){
                 System.out.println("ERROR: Unknown CMD");
                 returnErrorResponse(501, out);
            }
            else {
                fileName = in.next();
                prctl = in.next();

                if (!prctl.equalsIgnoreCase("HTTP/1.1") && !prctl.equalsIgnoreCase("HTTP/1.0")){
                    System.out.println("BAD REQUEST: check your protocol");
                    returnErrorResponse(400, out);
                }
                else {
                    File file = new File(ROOT_DIR + fileName);
                    System.out.println("STANDBY: We are getting your file: " + file);

                    if (file.isDirectory()) {
                        returnDirectoryList(file, printout);
                    }
                    else if (file.exists() && file.canRead()){
                        String okResponse = prctl + " 200 OK\r\n";
                        printout.print(okResponse);
                        printout.print("Terminating Connection\r\n");

                        String type  = getMimeType(file.getName());
                        printout.print("Content-Type: " + type + "\r\n" );

                        long contentLength = file.length();

                        printout.print("Content-Length: " + contentLength + "\r\n");
                        printout.print("\r\n");
                        printout.flush();

                        returnFile(file, out);
                    }
                    else {
                        if (file.exists() && !file.canRead()){
                            System.out.println("ERROR: Permission denied");
                            returnErrorResponse(403, out);
                        }
                        else if (!file.exists()){
                            System.out.println("ERROR: No File Found!");
                            returnErrorResponse(404, out);
                        }
                        printout.flush();
                    }

                }
            }


        } catch(Exception e){
            System.out.println("ERROR: " + connection.getInetAddress() + " " + cmd + " " + e);

        } finally {
            try {
                connection.close();
            }
            catch (IOException e) {
                System.out.println("ERROR: Closing Connection");
            }
        }
    }

    private static void returnFile(File file, OutputStream out) {

        InputStream in_stream = null;
        try {
            in_stream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStream out_stream = new BufferedOutputStream(out);
        while (true) {
            int i  = 0;
            try {
                assert in_stream != null;
                i = in_stream.read();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if (i < 0)
                break;
            try {
                out_stream.write(i);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out_stream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in_stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)
            return "x-application/x-unknown";
        String ext = fileName.substring(pos + 1).toLowerCase();
        return switch (ext) {
            case "txt" -> "text/plain";
            case "html" -> "text/html";
            case "htm" -> "text/html";
            case "css" -> "text/css";
            case "js" -> "text/javascript";
            case "java" -> "text/x-java";
            case "jpeg" -> "image/jpeg";
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "ico" -> "image/x-icon";
            case "class" -> "application/java-vm";
            case "jar" -> "application/java-archive";
            case "zip" -> "application/zip";
            case "xml" -> "application/xml";
            case "xhtml" -> "application/xhtml+xml";
            default -> "x-application/x-unknown";
        };
    }

    private static void returnIndex(File directory, PrintWriter outgoing)
            throws Exception {
        String[] fileList = directory.list();
        assert fileList != null;
        for (String s : fileList) outgoing.println(s);
        outgoing.flush();
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("Error while transmitting data.");
    }

    private static void returnErrorResponse(int errorCode, OutputStream socketOut) {

        String protocol = "HTTP/1.1";
        String statusDescription = " ";
        String statusMessage = "";

        switch (errorCode) {
            case 400 -> {
                statusDescription += "400 Bad Request";
                statusMessage += "The syntax of the request is bad.";
            }
            case 403 -> {
                statusDescription += "403 Forbidden";
                statusMessage += "The server does not have permission to read the file.";
            }
            case 404 -> {
                statusDescription += "404 Not Found";
                statusMessage += "The resource that you requested does not exist on this server.";
            }
            case 501 -> {
                statusDescription += "501 Not Implemented";
                statusMessage += "The command received has not been implemented.";
            }
            default -> {
                statusDescription += "500 Internal Server Error";
                statusMessage += "There has been an error in handling the connection.";
            }
        }

        try {
            PrintWriter out = new PrintWriter(socketOut);

            out.print(protocol + statusDescription + "\r\n");
            out.print("Connection: close\r\n");
            out.print("Content-Type: text/html\r\n");
            out.print("\r\n");
            out.print("<html><head><title>Error</title></head><body>\r\n");
            out.print("<h2>Error:" + statusDescription + "</h2>\r\n");
            out.print("<p>" + statusMessage + "</p>\r\n");
            out.print("</body></html>\r\n");
            out.flush();

            out.close();
        } catch (Exception e) {
            System.out.println("Error Occurred in Event response");
        }
    }

    private static void returnDirectoryList(File directory, PrintWriter printWriter) {

        File[] files = directory.listFiles();

        printWriter.print("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n"
                + "\r\n" + "<h1>Directory Listing</h1>" + "<h3>"
                + directory.getPath() + "</h3>"
                + "<table border=\"0\" cellspacing=\"8\">"
                + "<tr><td><b>Filename</b><br></td><td align=\"right\"><b>Size</b></td>"
                + "<td><b>Last Modified</b></td></tr>"
                + "<tr><td><b><a href=\"../\">../</b><br></td><td></td><td></td></tr>");

        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            directory = files[i];

            if (directory.isDirectory()) {
                printWriter.print("<tr><td><b><a href=\"" + directory.getPath()
                        + directory.getName() + "/\">"
                        + directory.getName()
                        + "/</a></b></td><td></td><td></td></tr>");
            } else {
                printWriter.print("<tr><td><a href=\"" + directory.getPath()
                        + directory.getName() + "\">"
                        + directory.getName()
                        + "</a></td><td align=\"right\">"
                        + directory.length() + "</td><td>"
                        + new Date(directory.lastModified())
                        + "</td></tr>");
            }
        }
        printWriter.print("</table><hr>\r\n");
        printWriter.flush();
    }

}
