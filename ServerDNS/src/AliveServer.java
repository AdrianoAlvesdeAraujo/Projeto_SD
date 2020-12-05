import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AliveServer extends Thread {

    Socket socket;
    DnsThread dns;

    public AliveServer(Socket socket) {
        this.socket = socket;
    }

/*
    public void run() {


  //          verificaSituacao();


        }

/*
        public void verificaSituacao () {

            try {
                this.socket = new Socket("127.0.0.1", 4500);
            } catch (IOException e) {
                System.out.println("Servidor Intermediario Inacessivel: " + e);
                try {
                    this.socket = new Socket("127.0.0.1", 4501);
                } catch (IOException ioException) {
                    System.out.println("Servidor 1 inacessivel: " + ioException);
                    try {
                        this.socket = new Socket("127.0.0.1", 4502);
                    } catch (IOException exception) {
                        System.out.println("servidor 2 inacessivel: " + exception);
                        try {
                            this.socket = new Socket("127.0.0.1", 4503);
                        } catch (IOException ex) {
                            System.out.println("o servidor está inacessivel: " + ex);
                        }
                    }
                }

            }

            String nome;
            try {

                //recebe do servidor o ip e a porta

                InputStreamReader in = new InputStreamReader(socket.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                nome = bf.readLine();

                System.out.println("PASSOU AQUI: " + nome);

                 dns.setIpAndPort(nome);

            } catch (IOException e) {
                System.out.println("Problema na recepção do nome do servidor");
            }


        }*/

    }


