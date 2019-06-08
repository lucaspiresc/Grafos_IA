import MetodosBusca.AEstrela;
import MetodosBusca.BuscaLargura;
import MetodosBusca.BuscaProfundidade;

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

        System.out.println("Digte A para rodar a busca com A*, P para rodar a busca em profundade, L para rodar a busca em largura\n" +
                            "Qualquer outra coisa parar sair");
        String metodo = reader.nextLine();
        while (metodo.equals("A") || metodo.equals("P") || metodo.equals("L")){
            switch (metodo){
                case "A":
                    System.out.println("EXECUTANDO A*");
                    new AEstrela().buscaAEstrela(instanciaInicial);
                    break;
                case "L":
                    System.out.println("EXECUTANDO BUSCA EM LARGURA");
                    new BuscaLargura().buscaLargura(instanciaInicial);
                    break;
                case "P":
                    System.out.println("EXECUTANDO BUSCA EM PROFUNDIDADE");
                    new BuscaProfundidade().buscaProfundidade(instanciaInicial);
                    break;
            }
            System.out.println();
            System.out.println();

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