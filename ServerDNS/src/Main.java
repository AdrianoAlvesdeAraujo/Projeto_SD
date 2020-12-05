import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {



    public static void main(String[] args) throws IOException{

        // Inicia o servidor DNS e abre um socket
        ServerSocket dnsserver = new ServerSocket(23);
        System.out.println("DNS: Esperando conexao...");


        while(true){
            // Cria uma thread passando a onexao e inicia aceitando conexoes

            Socket conexao = dnsserver.accept();
            System.out.println("DNS: Conectado.");

            DnsThread tconexao = new DnsThread(conexao);
           // AliveServer alive = new AliveServer(conexao);

            tconexao.start();
           // alive.start();
        }
    }
}
