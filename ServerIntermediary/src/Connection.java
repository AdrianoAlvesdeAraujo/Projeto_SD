import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;


public class Connection extends Thread {

    Socket connection;
    Socket serverOne;
    Socket serverTwo;
    Socket serverThree;
    String pRemota;


    private String s1;



    public Connection(Socket connection) {

        this.connection = connection;


    }

    @Override
    public void run() {
//       int count=0;
//        ++count;
//        if(count < 2) {
          InputStreamReader in;
            try {
                in = new InputStreamReader(connection.getInputStream());
                BufferedReader bf = new BufferedReader(in);
                pRemota = bf.readLine();

                System.out.println(pRemota);

            } catch (IOException e) {
                System.out.println("problema no recebimento da portaRemota do DNS ");
            }


            //entra no if de acordo com a porta remota do DNS
            if (connection.getPort() == Integer.parseInt(pRemota)) {

            int count=0;
            count++;
                System.out.println("contagem: "+count);
                //
                int port = connection.getLocalPort();
                String door = Integer.toString(port);
                InetAddress ip = connection.getLocalAddress();

                //String ipLocal=ip.toString();
                String ipLocal = "localhost";

                System.out.println("Dentro da função do DNS: "+Thread.currentThread());
                System.out.println(ipLocal);
                System.out.println(door);


                String nome = ipLocal.concat(":" + door);

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
                    System.out.println("Problema ao fechar conexão DNS");
                }

            }

        System.out.println("Fora da função do DNS: "+currentThread());


        //Imprime os nomes das threads ativas no momento
        System.out.println(Thread.currentThread());

        int counter = Thread.activeCount();
        //Thread.activeCount verifica quantas Threads estão ativas
        System.out.println("currently active threads = " + counter);

        //Se a quantidade de threads(representa a quantidade de clientes) ativas for maior que 5 entra no if.
        if ((Thread.activeCount() >= 7)) {
            //chama a função server3 para acionar o servidor 3 e dividir a carga
            System.out.println(Thread.currentThread());
            servidor3();

            //enquanto a quantidade de Threads seja menor que 5, entra no else.
        } else {

            //chama a função server1 caso tenha menos de 5
            System.out.println(Thread.currentThread());
            servidor1();

        }


    }

    public void servidor1() {

        serverOne = null;

        String hostName = "127.0.0.1";
        int server = 4501;
        try {
            serverOne = new Socket(hostName, server);
            server(serverOne);


        } catch (IOException e) {
            System.out.println("Erro ao iniciar o socket server1");
            servidor2();
        }

    }

    public void servidor2() {

        serverTwo = null;
        String hostName = "127.0.0.1";
        int server = 4502;
        try {
            serverTwo = new Socket(hostName, server);
            if(serverTwo!=null) {
                server(serverTwo);
            }

        } catch (IOException e) {
            System.out.println("Erro ao iniciar o socket server2");
            servidor3();
        }
    }

    public void servidor3() {

        serverThree = null;

        String hostName = "127.0.0.1";
        int server = 4503;
        try {
            serverThree = new Socket(hostName, server);
            server(serverThree);


        } catch (IOException e) {
            System.out.println("Erro ao iniciar o socket server1");
            servidor2();
        }
    }


    public void server(Socket server) {


        try {
            //Recepção de menu do servidor
            InputStreamReader input = new InputStreamReader(server.getInputStream());
            BufferedReader bf = new BufferedReader(input);
            String str = bf.readLine();
            System.out.println(str);

            PrintWriter pr = new PrintWriter(connection.getOutputStream());
            pr.println(str);
            pr.flush();
        }catch(IOException e3){
                System.out.println("problema no menu: "+e3);

            }
        try{

            //Recepção de qual opção do menu o cliente escolheu
            InputStreamReader input0 = new InputStreamReader(connection.getInputStream());
            BufferedReader bf0 = new BufferedReader(input0);
            String str0 = bf0.readLine();

            System.out.println("server intermediario: " + str0);

            switch (str0) {
                case "1":

                        if(serverOne==null) {
                            try {
                                serverOne = new Socket("127.0.0.1", 4501);
                            } catch (IOException ioException) {
                                System.out.println("Problema na conexão do server1");
                            }

                        }
                        if(serverTwo==null) {
                            try {
                                serverTwo = new Socket("127.0.0.1", 4502);
                            } catch (IOException ioException) {
                                System.out.println("Problema na conexão do server2");
                            }
                        }
                        if(serverThree==null){
                            try {
                                serverThree = new Socket("127.0.0.1", 4503);
                            } catch (IOException ioException) {
                                System.out.println("Problema na conexão do server3");
                            }
                        }
                    adiciona(server);
                    break;

                case "2":
                    lista(server);
                    break;

                case "3":
                    String menu3 = "3";
                    PrintWriter pr4 = new PrintWriter(server.getOutputStream());
                    pr4.println(menu3);
                    pr4.flush();
                    try {
                        //fechar conexão socket
                        connection.close();
                    } catch (IOException e) {
                        System.out.println("Cliente desconectado: " + e);

                    }
                    break;

                default:
                    String fraseDefault = "4";
                    //PrintWriter prDefault = null;
                    try {
                        PrintWriter prDefault = new PrintWriter(server.getOutputStream());
                        prDefault.println(fraseDefault);
                        prDefault.flush();

                        InputStreamReader input6 = new InputStreamReader(server.getInputStream());
                        BufferedReader bf5 = new BufferedReader(input6);
                        String str6 = bf5.readLine();

                        System.out.println(str6);

                        //envia as informações passadas pelo servidor(dados da pessoa e do veículo para o cliente digitar)

                        pr4 = new PrintWriter(connection.getOutputStream());
                        pr4.println(str6);
                        pr4.flush();


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


            }


        } catch (IOException e) {
            System.out.println("Problema no recebimento do menu/envio para o cliente: " + e);
        }


        server(server);
    }

    public void adiciona(Socket server) {
        try {
            //envio da opção do menu do cliente para o servidor

            String menu = "1";
            PrintWriter pr2 = new PrintWriter(serverOne.getOutputStream());
            pr2.println(menu);
            pr2.flush();

            PrintWriter pr1 = new PrintWriter(serverTwo.getOutputStream());
            pr1.println(menu);
            pr1.flush();

            PrintWriter pr3 = new PrintWriter(serverThree.getOutputStream());
            pr3.println(menu);
            pr3.flush();
        } catch (IOException e) {
            System.out.println("problema no envio de frase[3]: " + e);
        }


        for (int i = 0; i < 8; i++) {
            try {
                //recebe as informações do servidor(dados da pessoa e do veículo para o cliente digitar)
                InputStreamReader input1 = new InputStreamReader(server.getInputStream());
                BufferedReader bf1 = new BufferedReader(input1);
                String str1 = bf1.readLine();

                System.out.println(str1);

                //envia as informações passadas pelo servidor ao cliente(dados da pessoa e do veículo para o cliente digitar)

                PrintWriter pr4;
                pr4 = new PrintWriter(connection.getOutputStream());
                pr4.println(str1);
                pr4.flush();
            } catch (IOException e1) {
                System.out.println("problema no envio de frase[3]: " + e1);
            }

            try {
                //recebe as informações do servidor(dados da pessoa e do veículo para o cliente digitar)
                InputStreamReader input1 = new InputStreamReader(connection.getInputStream());
                BufferedReader bf1 = new BufferedReader(input1);
                String str2 = bf1.readLine();

                System.out.println(str2);

                //recebe as informações passadas pelo cliente(dados da pessoa e do veículo para o cliente digitar)
                PrintWriter pr5 = new PrintWriter(serverOne.getOutputStream());
                pr5.println(str2);
                pr5.flush();
                PrintWriter pr6 = new PrintWriter(serverTwo.getOutputStream());
                pr6.println(str2);
                pr6.flush();
                PrintWriter pr7 = new PrintWriter(serverThree.getOutputStream());
                pr7.println(str2);
                pr7.flush();

            } catch (IOException e) {
                System.out.println("problema no envio de frase[3]: " + e);
            }

        }
        server(server);

    }


    public void lista(Socket server) {
        try {
            String menu2 = "2";
            PrintWriter pr3 = new PrintWriter(server.getOutputStream());
            pr3.println(menu2);
            pr3.flush();


            //recepção do tamanho do arrayList para sincronismo do server
            InputStreamReader input9 = new InputStreamReader(server.getInputStream());
            BufferedReader bf9 = new BufferedReader(input9);
            String str9 = bf9.readLine();
            setS1(str9);
            int tamanho = Integer.parseInt(str9);

            String auto = getS1();
            //envio de tamanho do ArrayList para sincronismo do server
            PrintWriter pr9 = new PrintWriter(connection.getOutputStream());
            pr9.println(auto);
            pr9.flush();


            PrintWriter pr4;
            String str5;
            InputStreamReader input5;
            BufferedReader bf5;

            for (int i = 0; i < tamanho; i++) {

                //recebe as informações do servidor(dados da pessoa e do veículo para o cliente digitar)
                input5 = new InputStreamReader(server.getInputStream());
                bf5 = new BufferedReader(input5);
                str5 = bf5.readLine();
                setS1(str5);

                System.out.println(str5);

                //envia as informações passadas pelo servidor(dados da pessoa e do veículo para o cliente digitar)
                String autom = getS1();
                pr4 = new PrintWriter(connection.getOutputStream());
                pr4.println(autom);
                pr4.flush();
            }

        } catch (IOException e) {
            System.out.println("problema no recebimento de mensagem server1/envio para o cliente: " + e);
        }
        server(server);
    }

    public synchronized String getS1() {
        return s1;
    }

    public synchronized void setS1(String s1) {
        this.s1 = s1;
    }
}

