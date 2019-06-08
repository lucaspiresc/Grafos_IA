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

        System.out.println("EXECUTANDO A*");
        new AEstrela().buscaAEstrela(instanciaInicial);

        System.out.println("EXECUTANDO BUSCA EM LARGURA");
        new BuscaLargura().buscaLargura(instanciaInicial);

        System.out.println("EXECUTANDO BUSCA EM PROFUNDIDADE");
        new BuscaProfundidade().buscaProfundidade(instanciaInicial);
    }


    private static int[] montarInstacia(String[] a) {
        int[] initState = new int[9];
        for (int i = 0; i < a.length; i++) {
            initState[i] = Integer.parseInt(a[i]);
        }
        return initState;
    }

}