
import java.io.*;
import java.net.Socket;




public class ClientSocket {

    public static void main(String[] args) {


     try {
        //Se conecta com o DNS e solicia o ip do servidor de anuncios
        Socket clientSocket = new Socket("localhost", 23);

        OutputStream os = clientSocket.getOutputStream();
        PrintWriter pr = new PrintWriter(os);
        pr.println("autoanuncios.com\r");
        pr.flush();

        System.out.println("Solicitou ao DNS");

        //Recebe o ip que o DNS mandou
        InputStream is = clientSocket.getInputStream();
        InputStreamReader in = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(in);
        String iprecebido = bf.readLine();
        System.out.println("DNS mandou: " + iprecebido);

        // Traduz o IP em formato de String e conecta ao servidor desse ip
        String[] hostname = iprecebido.split(":");
        int port = Integer.parseInt(hostname[1]);

        System.out.println("hostname: " + hostname[0] + "; port: " + hostname[1]);

        //Socket servidor = new Socket(hostname[0], port);
        Socket servidor = new Socket("localhost", port);
        //System.out.println("Conectado com: " + iprecebido);

        SolicitaServico solicita = new SolicitaServico(servidor);

         solicita.selection();




    } catch (IOException e) {
        e.printStackTrace();
    }
}




}
