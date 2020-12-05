import java.io.*;
import java.net.Socket;


public class DnsThread extends Thread{

    Socket socket;

    private final Socket conexao;
    private String nome;    // Guarda nome do qual o cliente pediu o IP
   // AliveServer alive;
    static String nameHost;
    String ipRemoto;

    private String ipAndPort;

    static String[][] nomes = new String[3][2];

    public DnsThread(Socket c){
        this.conexao = c;
    }

    @Override
    public void run(){


        try {
            InputStream is = conexao.getInputStream();
            OutputStream os = conexao.getOutputStream();

            String ip = null;


            /*le o nome recebido em bytes[] depois cria uma String a partir
                do array de bytes na codificacao UTF-8 chamado "nome"*/

            InputStreamReader in = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(in);
            nome = bf.readLine();

            nameHost=verificaSituacao();
            nomes= new String[][]{{"autoanuncios.com", "localhost:4500"}, {"Server2", "localhost:4502"}, {"Server3", "localhost:4503"}};
            System.out.println("Nome recebido: " + nome);

            //Itera sobre a matriz de nome/ip
            for (int row = 0; row < nomes.length; row++) {
                for (int col = 0; col < nomes[row].length; col++) {
                    //checa se cada elemento nome e igual ao que o cliente pediu
                    if (nome.equals(nomes[row][col])) {
                    /*se e igual entao pega o elemento da coluna a direita
                      que e o IP correspondente e joga no outpustream*/
                        ip = nomes[row][col+1];
                        PrintWriter pr = new PrintWriter(os);
                        pr.println(ip);
                        pr.flush();

                        System.out.println("Ip enviado: " + ip + "\r");

                        break;
                        // Se nao encontrar um nome mostra o erro no console
                    }else{break;}
                }
            }

            if (ip == null){
                PrintWriter pr = new PrintWriter(os);
                pr.println("DNS Error: 404 - Nao encontrado");
                pr.flush();

                System.out.println("DNS Error: 404 - Nome nao encontrado");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(DnsThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public String verificaSituacao () {

        try {
            this.socket = new Socket("127.0.0.1", 4500);

            //conhece a porta local e envia para o server intermediário
            int pLocal=socket.getLocalPort();
            System.out.println(pLocal);
            String portaLocal=Integer.toString(pLocal);
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println(portaLocal);
            pr.flush();

        } catch (IOException e) {
            System.out.println("Servidor Intermediario Inacessivel: " + e);

            }




        try {



            //recebe do servidor o ip e a porta

            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            ipRemoto = bf.readLine();

            System.out.println("PASSOU AQUI: " + ipRemoto);


        } catch (IOException e) {
            System.out.println("Problema na recepção do nome do servidor");
        }


        return ipRemoto;
    }

}
