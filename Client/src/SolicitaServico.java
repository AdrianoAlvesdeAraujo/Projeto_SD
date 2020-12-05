import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SolicitaServico{

    Socket connection;





    public SolicitaServico(Socket connection) {
        this.connection = connection;

    }


    public void selection() {

        System.out.println(connection.getLocalPort());



        try{
            //recepção do menu enviada pelo servidor
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            BufferedReader bf = new BufferedReader(input);
            String str = bf.readLine();
            System.out.println(str);


            //o sistema pega a informação digitada pelo cliente(Scanner faz isso)
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String option = myObj.nextLine();



            switch (option) {
                case "1" -> {
                    adiciona();
                    break;
                }
                case "2" -> {
                    lista();

                    break;
                }
                case "3"-> {
                    sairSistema();
                    break;
                }
                default -> {
                    valorInexistente();
                    break;

                }




            }
        } catch (IOException e) {
            System.out.println("erro ao enviar e receber informações dos erver: "+ e);
        }


        selection();
    }

    public void adiciona() {

        try {
            //envio da informação de opção 1 para o servidor - adicionar
            String option = "1";
            PrintWriter pr;
            pr = new PrintWriter(connection.getOutputStream());
            pr.println(option);
            pr.flush();

        } catch (IOException e) {
            System.out.println("Erro na adição de anúncio no cliente");
        }
        try{

            //Envio de informações do usuário e veiculo para adicinar anúncio no servidor
            String str2;
            InputStreamReader input2;
            PrintWriter pr1;
            BufferedReader bf2;
            for (int i = 0; i < 8; i++) {

                //recebimento da informação dos dados para inserir no servidor
                input2 = new InputStreamReader(connection.getInputStream());
                bf2 = new BufferedReader(input2);
                str2 = bf2.readLine();
               System.out.println("Menu: " + str2);

                Scanner myObj2 = new Scanner(System.in);
                String infoCar = myObj2.nextLine();

                pr1 = new PrintWriter(connection.getOutputStream());
                pr1.println(infoCar);
                pr1.flush();
                //selection();

            }


        } catch (IOException e) {
            System.out.println("Erro na adição de anúncio no cliente");
        }


    }

    public void lista() {


        try {
            //envio da informação de opção 2 - listar
            String option = "2";
            PrintWriter pr;
            pr = new PrintWriter(connection.getOutputStream());
            pr.println(option);
            pr.flush();




            InputStreamReader input9 = new InputStreamReader(connection.getInputStream());
            BufferedReader bf9 = new BufferedReader(input9);
            String str9 = bf9.readLine();
            System.out.println(str9);


            int tamanho = Integer.parseInt(str9);
            InputStreamReader input1;
            BufferedReader bf1;
            String str1;



            for (int i = 0; i < tamanho; i++) {


                // try {

                //recebe as informações do servidor(dados da pessoa e do veículo para o cliente digitar)
                input1 = new InputStreamReader(connection.getInputStream());
                bf1 = new BufferedReader(input1);
                str1 = bf1.readLine();
                System.out.println(str1);



            }

        } catch (IOException e) {
            System.out.println("problema no envio da opção 2 do menu ");
        }

    }

    public void sairSistema(){
        String option = "3";
        PrintWriter pr;
        try {
            pr = new PrintWriter(connection.getOutputStream());
            pr.println(option);
            pr.flush();
            connection.close();
        } catch (IOException e) {
            System.out.println("Problema na parte de sair do Sistema");
        }


    }
    public void valorInexistente(){
        try{
            String s= "4";
            PrintWriter prDefault = new PrintWriter(connection.getOutputStream());
            prDefault.println(s);
            prDefault.flush();

            InputStreamReader input5 = new InputStreamReader(connection.getInputStream());
            BufferedReader bf5 = new BufferedReader(input5);
            String str5 = bf5.readLine();

            System.out.println(str5);


        } catch (IOException ioException) {
            System.out.println("problema no informe de valor não padrão");
        }

    }

}
