package Puzzle8;

import java.util.ArrayList;
import java.util.Arrays;

public class InstanciaPuzzle {

    private final int tamanhoPuzzle = 9;

    public int blocosForaDoLugar = 0;

    public int distanciaManhattan = 0;

    public final int[] puzzleFinal = new int[]
            {1, 2, 3, 4, 5, 6, 7, 8, 0};

    public int[] tabuleiro;

    /*
     * Construtor da classe
     */
    public InstanciaPuzzle(int[] board) {
        tabuleiro = board;
        distanciaManhattan();
    }

    /**
     * Obtem custo para chegar nesta instancia
     */
    public double obterCusto() {
        int cost = 0;
        for (int i = 0; i < tabuleiro.length; i++) {
            int puzzleFinalNumber = puzzleFinal[i] == 0 ? 9 : puzzleFinal[i];
            cost += Math.abs(tabuleiro[i] - puzzleFinalNumber);
        }
        return cost;
    }

    /*
     * Distancia de Manhattan para esta instancia
     */
    private void distanciaManhattan() {
        //variavel que vai garantir que percorremos o vetor linearmente,
        //indepente do metodo, que considera linhas e colunas
        int indice = 0;

        for (int i = 0; i < 3;i++) {
            for (int j = 0; j < 3; j++) {

                //o indice correto de um valor do puzzle no vetor, com a execao do buraco, que sera tratada depois,
                //deve ser o valor - 1, ou seja:
                // o valor 1 deve ficar na posicao 0, o 2 deve ficar na posicao 2, e assim por diante
                int posicaoCerta = tabuleiro[indice] - 1;

                //Se o valor da posicao certo for -1, e porque estamos olhando o buraco,
                //como o buraco nao pode ser movido diretamente(so muda de lugar quando movemos uma peca),
                //nao iremos considera-lo no calcula da distancia
                if(posicaoCerta != -1){
                    //fazendo os calculos para obter a linha e a coluna do
                    //valor atual no plano
                    int posicaoCertaVertical = posicaoCerta / 3;
                    int posicaoCertaHorizontal= posicaoCerta % 3;

                    distanciaManhattan += Math.abs(posicaoCertaVertical - i ) + Math.abs(posicaoCertaHorizontal - j);
                }
                indice++;
            }
        }
    }

    /*
     * Metodo que buscar o "buraco" do puzzle
     */
    private int acharVazio() {
        int posVazio = -1;

        for (int i = 0; i < tamanhoPuzzle; i++) {
            if (tabuleiro[i] == 0)
                posVazio = i;
        }
        return posVazio;
    }

    /*
     * Faz um clone da instancia do tabuleiro
     */
    private int[] clonarTabuleiro(int[] state) {
        int[] clone = new int[tamanhoPuzzle];
        for (int i = 0; i < tamanhoPuzzle; i++) {
            clone[i] = state[i];
        }
        return clone;
    }

    /**
     * Verifica as direcoes possiveis para fazer os movimentos, e chama o metodo
     * que faz este movimento, quando possivel
     */
    public ArrayList<InstanciaPuzzle> gerarEstados() {
        ArrayList<InstanciaPuzzle> sucessores = new ArrayList<InstanciaPuzzle>();
        int buraco = acharVazio();

        /*
         * Verifica se é possível mover uma peça a esqueda do buraco
         */
        if (buraco != 0 && buraco != 3 && buraco != 6) {
            sucessores.add(trocaPosicao(buraco - 1, buraco));
        }

        /*
         * Verifica se é possível mover uma peça acima do buraco
         */
        if (buraco != 6 && buraco != 7 && buraco != 8) {
            sucessores.add(trocaPosicao(buraco + 3, buraco));
        }

        /*
         * Verifica se é possível mover uma peça abaixo do buraco
         */
        if (buraco != 0 && buraco != 1 && buraco != 2) {
            sucessores.add(trocaPosicao(buraco - 3, buraco));
        }

        /*
         * Verifica se é possível mover uma peça a direita do buraco
         */
        if (buraco != 2 && buraco != 5 && buraco != 8) {
            sucessores.add(trocaPosicao(buraco + 1, buraco));
        }

        return sucessores;
    }

    /*
     * Reliza o movimento de uma peca para o buraco
     */
    private InstanciaPuzzle trocaPosicao(int d1, int d2) {
        int[] clone = clonarTabuleiro(tabuleiro);

        int tmp = clone[d1];

        clone[d1] = tabuleiro[d2];
        clone[d2] = tmp;

        return new InstanciaPuzzle(clone);
    }

    /**
     * Verifica se esta instancia e a instancia final do puzzle8
     */
    public boolean isPuzzleFinal() {
        return Arrays.equals(tabuleiro, puzzleFinal);
    }

    /**
     * Imprime o estado atual
     */
    public void mostrarPuzzle() {
        System.out.println(tabuleiro[6] + " | " + tabuleiro[7] + " | "
                + tabuleiro[8]);
        System.out.println("---------");
        System.out.println(tabuleiro[3] + " | " + tabuleiro[4] + " | "
                + tabuleiro[5]);
        System.out.println("---------");
        System.out.println(tabuleiro[0] + " | " + tabuleiro[1] + " | "
                + tabuleiro[2]);

    }

    /**
     * Compara duas instacias do puzzle
     */
    public boolean equals(InstanciaPuzzle s) {
        return Arrays.equals(tabuleiro, s.tabuleiro);

    }

}
