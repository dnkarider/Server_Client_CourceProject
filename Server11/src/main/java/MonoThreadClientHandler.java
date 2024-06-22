import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        clientDialog = client;
    }

    @Override
    public void run() {

        try {
            PrintWriter out = new PrintWriter(clientDialog.getOutputStream());
            Scanner in = new Scanner(clientDialog.getInputStream());

            System.out.println("InputStream created");
            System.out.println("OutputStream  created");

            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");
                while(true){
                    if(in.hasNext()){
                        break;
                    }
                }
                String entry = in.nextLine();
                System.out.println(entry);

                System.out.println("READ from clientDialog message - " + entry);

                if (entry.equalsIgnoreCase("quit")) {

                    System.out.println("Client initialize connections suicide ...");
                    out.write("Server reply - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }

                System.out.println("Server try writing to channel");
                out.write("Server reply - " + entry + " - OK");
                System.out.println("Server Wrote message to clientDialog.");

                out.flush();

            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}