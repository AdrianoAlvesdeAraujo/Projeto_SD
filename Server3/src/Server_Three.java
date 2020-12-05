

import java.io.IOException;
import java.net.*;



public class Server_Three {
    public static void main(String[] args) throws IOException {




        ServerSocket server = null;
        try {
            //instancia o servidor na porta 4503
            server = new ServerSocket(4503);


            System.out.println("Servidor Socket iniciado");



            while (true) {
                System.out.println("Esperando por conexão");
                Socket connection = server.accept();
                System.out.println("Chegou conexão");

                //instancia a classe HelloWorld e passa como parametro a conexão do cliente

                HelloWorld t = new HelloWorld(connection);

                //inicializa a Thread
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        server.close();

        System.out.println("Finalizado servidor 1");

    }


}




