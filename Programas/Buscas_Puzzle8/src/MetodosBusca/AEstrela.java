package MetodosBusca;

import Puzzle8.ArvoreBusca;
import Puzzle8.InstanciaPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AEstrela {

    public static void buscaAEstrela(int[] tabuleiro)
    {
        ArvoreBusca raiz = new ArvoreBusca(new InstanciaPuzzle(tabuleiro));
        Queue<ArvoreBusca> q = new LinkedList<ArvoreBusca>();
        q.add(raiz);

        int contador = 1;

        while (!q.isEmpty())
        {
            ArvoreBusca tmp = q.poll();

            // if the tempNode is not the goal state
            if (!tmp.estadoAtual.isPuzzleFinal())
            {
                // generate tempNode's immediate successors
                ArrayList<InstanciaPuzzle> sucessores = tmp.estadoAtual.gerarEstados();

                ArrayList<ArvoreBusca> nosSuccessores = new ArrayList<ArvoreBusca>();

                /*
                 * Itera sobre os sucessores desta instacia, verifica se ja foram avalizados,
                 * e caso nao tenham sido, adiciona-os na fila
                 */
                for (int i = 0; i < sucessores.size(); i++)
                {
                    ArvoreBusca novoNo;

                    novoNo = new ArvoreBusca(tmp, sucessores.get(i), tmp.custo + sucessores.get(i).obterCusto(), sucessores.get(i).distanciaManhattan);

                    if (!jaAvaliado(novoNo))
                    {
                        nosSuccessores.add(novoNo);
                    }
                }

                if (nosSuccessores.size() == 0) {
                    continue;
                }

                ArvoreBusca menorNo = nosSuccessores.get(0);

                /*
                 * Encontra o menor f(n) em um no, e seta ele como o menor.
                 */
                for (int i = 0; i < nosSuccessores.size(); i++)
                {
                    if (menorNo.fCusto > nosSuccessores.get(i).fCusto) {
                        menorNo = nosSuccessores.get(i);
                    }
                }

                int menorValor = (int)menorNo.fCusto;

                // Adiciona na fila todos os nos com o mesmo custo do no de menor valor
                for (int i = 0; i < nosSuccessores.size(); i++)
                {
                    if (nosSuccessores.get(i).fCusto == menorValor)
                    {
                        q.add(nosSuccessores.get(i));
                    }
                }
                contador++;
            }else {
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
                System.out.println("Custo A*: " + tmp.custo);
                return;
            }
        }
        System.out.println("Nao foi encontrada uma solucao");

    }

    /*
     * Verifica se um estado ja foi avaliado anteriormente
     * Returns true if it has, false if it hasn't.
     */
    private static boolean jaAvaliado(ArvoreBusca n) {
        boolean avaliado = false;
        ArvoreBusca checkNode = n;

        // Sobe na arvore verificando se ja temos o estado atual
        while (n.pai != null && !avaliado) {
            if (n.pai.estadoAtual.equals(checkNode.estadoAtual)) {
                avaliado = true;
            }
            n = n.pai;
        }

        return avaliado;
    }

}
