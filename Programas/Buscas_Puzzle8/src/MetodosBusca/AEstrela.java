package MetodosBusca;

import ArvorePesquisa.ArvoreBinaria;
import Puzzle8.ArvoreBusca;
import Puzzle8.InstanciaPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AEstrela {

    public static void buscaAEstrela(int[] tabuleiro){
        try {
            ArvoreBusca raiz = new ArvoreBusca(new InstanciaPuzzle(tabuleiro));
            Queue<ArvoreBusca> q = new LinkedList<ArvoreBusca>();

            /*
             * Arvore binaria sera utlizada para verificar se uma instancia do puzzle8
             * já foi obtida. Isto será feito para melhorar a peformance e evitar que
             * o algortimo fique preso em um loop(faz um movimento, e depois volta para o outro estado infinitamente)
             */
            ArvoreBinaria pesquisa = new ArvoreBinaria();
            pesquisa.inserir(raiz.estadoAtual.tabuleiroString);

            q.add(raiz);

            int contador = 1;

            while (!q.isEmpty()) {
                ArvoreBusca tmp = q.poll();

                if (!tmp.estadoAtual.isPuzzleFinal()) {
                    //Gera os sucessores caso o no nao seja o final do puzzle
                    ArrayList<InstanciaPuzzle> sucessores = tmp.estadoAtual.gerarEstados();

                    ArrayList<ArvoreBusca> nosSuccessores = new ArrayList<ArvoreBusca>();

                    /*
                     * Itera sobre os sucessores desta instacia, verifica se ja foram avalizados,
                     * e caso nao tenham sido, adiciona-os na fila
                     */
                    for (int i = 0; i < sucessores.size(); i++) {
                        ArvoreBusca novoNo;

                        novoNo = new ArvoreBusca(tmp, sucessores.get(i), tmp.custo + sucessores.get(i).obterCusto(), sucessores.get(i).distanciaManhattan);

                        if (!pesquisa.pesquisar(novoNo.estadoAtual.tabuleiroString)) {
                            nosSuccessores.add(novoNo);
                            pesquisa.inserir(novoNo.estadoAtual.tabuleiroString);
                        }
                    }

                    if (nosSuccessores.size() == 0) {
                        continue;
                    }

                    ArvoreBusca menorNo = nosSuccessores.get(0);

                    /*
                     * Encontra o menor f(n) em um no, e seta ele como o menor.
                     */
                    for (int i = 0; i < nosSuccessores.size(); i++) {
                        if (menorNo.fCusto > nosSuccessores.get(i).fCusto) {
                            menorNo = nosSuccessores.get(i);
                        }
                    }

                    int menorValor = (int) menorNo.fCusto;

                    // Adiciona na fila todos os nos com o mesmo custo do no de menor valor
                    for (int i = 0; i < nosSuccessores.size(); i++) {
                        if (nosSuccessores.get(i).fCusto == menorValor) {
                            q.add(nosSuccessores.get(i));
                        }
                    }
                    contador++;
                } else {
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
                    System.out.println("Numero de nos visitados: " + contador);
                    System.out.println("Numero de movimentos para a solucao: " + (tamanhoPilha-1));
                    return;
                }
            }
            System.out.println("Nao foi encontrada uma solucao");
        } catch(Exception e){
            System.out.println("Erro ao realizar a busca" + e.getMessage());
        }
    }
}
