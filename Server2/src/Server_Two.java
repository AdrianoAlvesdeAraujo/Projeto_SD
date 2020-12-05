import java.io.IOException;
import java.net.*;

public class Server_Two {
    public static void main(String[] args) {


        ServerSocket server = null;
        try {
            //instancia o servidor socket na porta 4502
            server = new ServerSocket(4502);


            System.out.println("Servidor Socket iniciado");

            while (true) {
                System.out.println("Esperando por conexão");
                Socket connection = server.accept();
                System.out.println("Chegou conexão");

                //instancia a classe HelloWorld e passa a conexão do cliente como parâmetro
                HelloWorld t = new HelloWorld(connection);
                //inicializa a Thread


                t.start();


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //fecha a conexão do servidor
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

