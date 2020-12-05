
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HelloWorld extends Thread implements Hello{
    Socket connection;
    Cars carros = new Cars();
   String pRemota;

    static List<Cars> kar = new ArrayList<>();
    Autenticacao autentica=new Autenticacao();


    public HelloWorld(Socket connection) {

        this.connection = connection;

    }

    public void run() {

        autenticacao();

       /*InputStreamReader in;
        try {
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            pRemota = bf.readLine();

        } catch (IOException e) {
            System.out.println("problema no recebimento da portaRemota do DNS ");
        }




        //entra no if de acordo com a porta remota do DNS
        int porta= connection.getPort();
        if(porta == Integer.parseInt(this.pRemota)) {


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
            String frase = "server 2 -- ## 1 - Adicionar anuncio ## 2 - Listar  veiculos ## 3 - Sair do sistema ## 4 - Listar veiculos ## 5 - Deletar veiculos ##";
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
                        System.out.println("Cliente desconectado: " + e);

                    }
                    break;
                case "4":
                    //Recepção da opção do menu enviada pelo cliente
                    String s4= "Digite a marca do veiculo";
                    PrintWriter pr4 = new PrintWriter(connection.getOutputStream());
                    pr4.println(s4);
                    pr4.flush();
                    InputStreamReader input4 = new InputStreamReader(connection.getInputStream());
                    BufferedReader bf4 = new BufferedReader(input4);
                    String str4 = bf4.readLine();
                    System.out.println(str4);
                    listOne(str4);
                    break;
                case "5":
                    //Recepção da opção do menu enviada pelo cliente
                    String s5= "Digite o cpf do usuario que deseja deletar";
                    PrintWriter pr5 = new PrintWriter(connection.getOutputStream());
                    pr5.println(s5);
                    pr5.flush();
                    InputStreamReader input5 = new InputStreamReader(connection.getInputStream());
                    BufferedReader bf5 = new BufferedReader(input5);
                    String str5 = bf5.readLine();
                    System.out.println(str5);
                    delete(str5);
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

    public void delete(String cpf){
        int count=-1;
        for (Cars car: kar) {
            count++;
            String cpf0=car.getCpf();
            if(cpf0.equals(cpf)){

                kar.remove(count);

                }
            }

        }

    

    public void listOne(String marcaC){

        System.out.println("passou por aqui");
        int count=-1;
        for (Cars car:kar) {
            System.out.println("passou por aqui 2");
            count++;
            String mark=car.getMarca();
            if(mark.equals(marcaC)){
                System.out.println("passou por aqui 3");
                String nome = this.kar.get(count).getNomeUsuario();
                String fone = this.kar.get(count).getFone();
                String marca = this.kar.get(count).getMarca();
                String modelo = this.kar.get(count).getModelo();
                String ano = this.kar.get(count).getAno();
                String cor = this.kar.get(count).getCor();
                String valor = this.kar.get(count).getDescricao();


                String dadosVeiculo = "Nome: " + nome.concat(" - Fone: " + fone.concat(" - Marca: " + marca.concat(" - Modelo: " + modelo.concat(" - Ano: " + ano.concat(" - Cor: " + cor.concat(" - Valor: " + valor))))));
                System.out.println(dadosVeiculo);


                try {
                    PrintWriter pr = new PrintWriter(this.connection.getOutputStream());
                    pr.println(dadosVeiculo);
                    pr.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }

    }

    public void autenticacao(){

        try {
        String login = "digite seu email";
        PrintWriter pr = new PrintWriter(connection.getOutputStream());
        pr.println(login);
        pr.flush();

            InputStreamReader input1 = new InputStreamReader(connection.getInputStream());
            BufferedReader bf1 = new BufferedReader(input1);
            String str1 = bf1.readLine();
            carros.setAutenticacao(autentica.setEmail(str1));

            String frase1 = "digite sua senha";
            PrintWriter pr1 = new PrintWriter(connection.getOutputStream());
            pr1.println(frase1);
            pr1.flush();

            InputStreamReader input0 = new InputStreamReader(connection.getInputStream());
            BufferedReader bf0 = new BufferedReader(input0);
            String str0 = bf0.readLine();
            System.out.println(str0);
            carros.setAutenticacao(autentica.setSenha(str0));

        } catch (IOException e) {
            System.out.println("Erro ao receber a mensagem do cliente");
        }


    }


}


