
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HelloWorld extends Thread {
    Socket connection;
    Cars carros = new Cars();
    //String pRemota;

    static List<Cars> kar = new ArrayList<>();

    public HelloWorld(Socket connection) {

        this.connection = connection;

    }

    public void run() {
     /*   InputStreamReader in;
        try {
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            pRemota = bf.readLine();

        } catch (IOException e) {
            System.out.println("problema no recebimento da portaRemota do DNS ");
        }




        //entra no if de acordo com a porta remota do DNS
        if(connection.getPort() == Integer.parseInt(this.pRemota)) {


            //
            int port=connection.getLocalPort();
            String door = Integer.toString(port);
            InetAddress ip=connection.getLocalAddress();

            //String ipLocal=ip.toString();
            String ipLocal="localhost";

            System.out.println(ipLocal);
            System.out.println(door);


            String nome=ipLocal.concat(":"+door);

            PrintWriter pr;
            try {
                pr = new PrintWriter(connection.getOutputStream());
                pr.println(nome);
                pr.flush();
            } catch (IOException e) {
                System.out.println("Problema na requisição do servidor DNS");
            }

            try {
                connection.close();
            } catch (IOException e) {
                System.out.println("problema no DNS: "+e);
            }


        }*/

        streamer();
    }

    public void streamer() {


        try {
            //Envio do menu para o cliente
            String frase = "server 1 -- ## 1 - Adicionar anuncio ## 2 - Listar  veiculos ## 3 - Sair do sistema ##";
            PrintWriter pr = new PrintWriter(connection.getOutputStream());
            pr.println(frase);
            pr.flush();
        } catch (
                IOException e1) {
            System.out.println("Problema no envio do menu: " + e1);
        }
        InputStreamReader input;
        String str0;

        try {
            //Recepção da opção do menu enviada pelo cliente
            input = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(input);
            str0 = bf.readLine();
            System.out.println(str0);

            switch (str0) {

                case "1":
                    adicionaCar();
                    break;

                case "2":
                    listAll();
                    break;

                case "3":
                    try {
                        //fechar conexão socket
                        connection.close();
                    } catch (IOException e) {
                        System.out.println("Cliente desconectado");

                    }
                    break;

                default:
                    try{
                        String s= "Valor inválido. Digite um número válido";
                        PrintWriter prDefault = new PrintWriter(connection.getOutputStream());
                        prDefault.println(s);
                        prDefault.flush();

                    } catch (IOException ioException) {
                        System.out.println("problema no informe de valor não padrão");
                    }
                   break;

            }
        } catch (
                IOException e2) {
            System.out.println("Problema no menu(switch): " + e2);
        }
        streamer();
    }


    public synchronized void adicionaCar() {
        PrintWriter[] pr1 = new PrintWriter[8];
        String[] frase = new String[8];
        String[] frase3 = new String[]{"Digite seu nome", "Digite seu cpf", "Digite o seu contato", "Digite a marca do veiculo", "Digite o modelo do veiculo", "Digite a cor do veiculo", "Digite o ano do veiculo", "Digite o valor do veiculo"};

        int i;
        for (i = 0; i < frase3.length; i++) {
            try {
                //envio de frase[3] para que o cliente digite as informações do anuncio
                pr1[i] = new PrintWriter(this.connection.getOutputStream());
                pr1[i].println(frase3[i]);
                pr1[i].flush();

            } catch (IOException e) {
                System.out.println("problema no envio de frase[3]: " + e);
            }


            try {
                InputStreamReader input1 = new InputStreamReader(connection.getInputStream());
                BufferedReader bf1 = new BufferedReader(input1);
                String str1 = bf1.readLine();
                frase[i] = str1;
                System.out.println(str1);

            } catch (IOException e) {
                System.out.println("Erro ao receber a mensagem do cliente");
            }
        }


        carros = new Cars(frase[0], frase[1], frase[2], frase[3], frase[4], frase[5], frase[6], frase[7]);
        kar.add(carros);


    }


    public void listAll() {
        String tamanho;
        PrintWriter[] pr = new PrintWriter[kar.size()];
        String dadosVeiculo;
        System.out.println("-------Listar veículos--------");

        int tam = kar.size();
        tamanho = Integer.toString(tam);

        PrintWriter pr6;
        try {
            pr6 = new PrintWriter(connection.getOutputStream());
            pr6.println(tamanho);
            pr6.flush();
        } catch (IOException e) {
            System.out.println("erro no envio do tamanho do ArrayList");

        }


        for (int i = 0; i < kar.size(); i++) {


            String nome = this.kar.get(i).getNomeUsuario();
            String fone = this.kar.get(i).getFone();
            String marca = this.kar.get(i).getMarca();
            String modelo = this.kar.get(i).getModelo();
            String ano = this.kar.get(i).getAno();
            String cor = this.kar.get(i).getCor();
            String valor = this.kar.get(i).getDescricao();


            dadosVeiculo = "Nome: " + nome.concat(" - Fone: " + fone.concat(" - Marca: " + marca.concat(" - Modelo: " + modelo.concat(" - Ano: " + ano.concat(" - Cor: " + cor.concat(" - Valor: " + valor))))));
            System.out.println(dadosVeiculo);

            try {
                pr[i] = new PrintWriter(this.connection.getOutputStream());
                pr[i].println(dadosVeiculo);
                pr[i].flush();


            } catch (IOException e) {
                System.out.println("Problema no envio da lista de Anúncios para o cliente: " + e);
            }
        }

        // streamer();

    }


}

