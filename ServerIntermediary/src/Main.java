import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {


    public static void main(String[] args) {

        ServerSocket server=null;
        try {
            //Inicializando o socket com a porta 4500
            server = new ServerSocket(4500);






            System.out.println("Servidor Socket iniciado");


            while (true) {
                System.out.println("Esperando por conexão");
                //esperando conexão
                Socket connection = server.accept();
                System.out.println("Chegou conexão");




               // BlockingQueue<String> c = new ArrayBlockingQueue<>(1);
                Connection t = new Connection(connection);

                System.out.println(connection.getInetAddress().getHostName());
                t.start();







            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //fechar conexão socket
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finalizado Conexão");
    }

}

