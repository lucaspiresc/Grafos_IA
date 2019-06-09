package MetodosBusca;

import Puzzle8.ArvoreBusca;
import Puzzle8.InstanciaPuzzle;

import java.util.ArrayList;
import java.util.Stack;

public class BuscaProfundidade {

    public static void buscaProfundidade(int[] tabuleiro)
    {
        ArvoreBusca raiz = new ArvoreBusca(new InstanciaPuzzle(tabuleiro));
        Stack<ArvoreBusca> pilha = new Stack<ArvoreBusca>();

        pilha.add(raiz);

        fazerBusca(pilha);
    }

    /*
     * Verifica se um estado ja foi avaliado anteriormente
     * Retorna true se ele ja foi, false se nao
     */
    private static boolean jaAvaliado(ArvoreBusca n) {
        boolean avaliado = false;
        ArvoreBusca noAvaliado = n;

        // Sobe na arvore verificando se ja temos o estado atual
        while (n.pai != null && !avaliado) {
            if (n.pai.estadoAtual.equals(noAvaliado.estadoAtual)) {
                avaliado = true;
            }
            n = n.pai;
        }

        return avaliado;
    }

    /*
     * Realiza a busca em profundidade
     */
    public static void fazerBusca(Stack<ArvoreBusca> s)
    {
        int contador = 1;

        while (!s.isEmpty())
        {
            ArvoreBusca tmp = s.pop();

            if (!tmp.estadoAtual.isPuzzleFinal())
            {
                //Se o estado atual nao e o final, expande-o
                ArrayList<InstanciaPuzzle> sucessores = tmp.estadoAtual.gerarEstados();

                /*
                 * Itera sobre os sucessores desta instacia, verifica se ja foram avalizados,
                 * e caso nao tenham sido, adiciona-os na fila
                 */
                for (int i = sucessores.size()-1; i >= 0; i--)
                {
                    ArvoreBusca novoNo = new ArvoreBusca(tmp,sucessores.get(i), tmp.custo + sucessores.get(i).obterCusto(), 0);

                    if (!jaAvaliado(novoNo)) {
                        s.add(novoNo);
                    }
                }
                contador++;
            }
            else{
                //Se achou o estado final, mostra-o

                //Empilha os estados ate chegar a raiz
                Stack<ArvoreBusca> caminho = new Stack<ArvoreBusca>();
                caminho.push(tmp);
                tmp = tmp.pai;

                while (tmp.pai != null) {
                    caminho.push(tmp);
                    tmp = tmp.pai;
                }
                caminho.push(tmp);

                int tamanhoPilha = caminho.size();

                for (int i = 0; i < tamanhoPilha; i++) {
                    tmp = caminho.pop();
                    tmp.estadoAtual.mostrarPuzzle();
                    System.out.println();
                }
                System.out.println("Custo busca profundidade: " + tmp.custo);
                return;
            }
        }

        System.out.println("Nao foi encontrada uma solucao");
    }

}
