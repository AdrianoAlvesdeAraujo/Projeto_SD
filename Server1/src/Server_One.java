import java.io.IOException;
import java.net.*;

public class Server_One {
    public static void main(String[] args) throws IOException {


        ServerSocket server = null;
        try {

            server = new ServerSocket(4501);


            System.out.println("Servidor Socket iniciado");


            while (true) {
                System.out.println("Esperando por conexão");
                Socket connection = server.accept();
                System.out.println("Chegou conexão");


                HelloWorld t = new HelloWorld(connection);

                t.start();



            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();

        System.out.println("Finalizado servidor 1");

    }


}



