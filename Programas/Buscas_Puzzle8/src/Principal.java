import MetodosBusca.AEstrela;
import MetodosBusca.BuscaLargura;
import MetodosBusca.BuscaProfundidade;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Principal {

    /*
     * Puzzle8 no formato:
     * 0 1 2 3 4 5 6 7 8
     *
     */
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Digite o estado inicial do puzzle(exemplo: 0 1 2 3 4 5 6 7 8) : \n");

        int[] instanciaInicial = montarInstacia(reader.nextLine().split(" "));

        Date inicioBusca; //Variaveis de tempo que serao usadas para
        Date fimBusca;    //medir o tempo de execucao dos algoritimos

        System.out.println("Digte A para rodar a busca com A*, P para rodar a busca em profundade, L para rodar a busca em largura\n" +
                            "Qualquer outra coisa parar sair");
        String metodo = reader.nextLine();
        while (metodo.equals("A") || metodo.equals("P") || metodo.equals("L")){
            switch (metodo){
                case "A":
                    System.out.println("EXECUTANDO A*");
                    inicioBusca = Calendar.getInstance().getTime();
                    new AEstrela().buscaAEstrela(instanciaInicial);
                    fimBusca = Calendar.getInstance().getTime();

                    System.out.println("\n3Tempo de execucao da busca A*: " +(fimBusca.getTime() - inicioBusca.getTime())+ "ms\n");
                    break;
                case "L":
                    System.out.println("EXECUTANDO BUSCA EM LARGURA");
                    inicioBusca = Calendar.getInstance().getTime();
                    new BuscaLargura().buscaLargura(instanciaInicial);
                    fimBusca = Calendar.getInstance().getTime();

                    System.out.println("\nTempo de execucao da busca em largura: " +(fimBusca.getTime() - inicioBusca.getTime())+ "ms\n");
                    break;
                case "P":
                    System.out.println("EXECUTANDO BUSCA EM PROFUNDIDADE");
                    inicioBusca = Calendar.getInstance().getTime();
                    new BuscaProfundidade().buscaProfundidade(instanciaInicial);
                    fimBusca = Calendar.getInstance().getTime();

                    System.out.println("\nTempo de execucao da busca em profundidade: " +(fimBusca.getTime() - inicioBusca.getTime())+ "ms\n");
                    break;
            }

            System.out.println("Digte A para rodar a busca com A*, P para rodar a busca em profundade, L para rodar a busca em largura\n" +
                    "Qualquer outra coisa parar sair");
            metodo = reader.nextLine();
        }
    }


    private static int[] montarInstacia(String[] a) {
        int[] initState = new int[9];
        for (int i = 0; i < a.length; i++) {
            initState[i] = Integer.parseInt(a[i]);
        }
        return initState;
    }

}