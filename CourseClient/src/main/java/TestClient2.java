import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient2 {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        try (Socket socket = new Socket("localhost", 3345);
             Scanner console = new Scanner(System.in);
             PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
             Scanner inputStream = new Scanner(socket.getInputStream());) {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = outputStream & reading channel = inputStream initialized.");

            while (!socket.isOutputShutdown()) {

                if (console.hasNext()) {
                    System.out.println("Client start writing in channel...");
                    String clientCommand = console.nextLine();

                    outputStream.write(clientCommand);
                    outputStream.flush();
                    System.out.println("Client sent message " + clientCommand + " to server.");

                    if (clientCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);
                        if (inputStream.hasNext()) {
                            System.out.println("reading...");
                            String in = inputStream.nextLine();
                            System.out.println(in);
                        }
                        break;
                    }

                    System.out.println("Client sent message & start waiting for data from server...");
                    Thread.sleep(2000);

                    if (inputStream.hasNext()) {
                        System.out.println("reading...");
                        String in = inputStream.nextLine();
                        System.out.println(in);
                    }
                }
            }
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}