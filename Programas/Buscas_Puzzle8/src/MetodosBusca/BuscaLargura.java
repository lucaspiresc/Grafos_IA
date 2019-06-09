package MetodosBusca;

import ArvorePesquisa.ArvoreBinaria;
import Puzzle8.ArvoreBusca;
import Puzzle8.InstanciaPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BuscaLargura {

    public static void buscaLargura(int[] tabuleiro) {
        try {
            ArvoreBusca raiz = new ArvoreBusca(new InstanciaPuzzle(tabuleiro));

            /*
             * Arvore binaria sera utlizada para verificar se uma instancia do puzzle8
             * já foi obtida. Isto será feito para melhorar a peformance e evitar que
             * o algortimo fique preso em um loop(faz um movimento, e depois volta para o outro estado infinitamente)
             */
            ArvoreBinaria pesquisa = new ArvoreBinaria();
            pesquisa.inserir(raiz.estadoAtual.tabuleiroString);

            Queue<ArvoreBusca> fila = new LinkedList<ArvoreBusca>();

            fila.add(raiz);

            fazerBusca(fila, pesquisa);
        } catch(Exception e){
            System.out.println("Erro ao realizar a busca" + e.getMessage());
        }
    }

    /*
     * Realiza a busca em largura
     */
    public static void fazerBusca(Queue<ArvoreBusca> q, ArvoreBinaria pesquisa) {
        try {
            int contador = 1;

            while (!q.isEmpty()) {
                ArvoreBusca tmp = q.poll();

                if (!tmp.estadoAtual.isPuzzleFinal()) //
                {
                    //Se o estado atual nao e o final, expande-o
                    ArrayList<InstanciaPuzzle> sucessores = tmp.estadoAtual.gerarEstados();

                    /*
                     * Itera sobre os sucessores desta instacia, verifica se ja foram avalizados,
                     * e caso nao tenham sido, adiciona-os na fila
                     */
                    for (int i = 0; i < sucessores.size(); i++) {

                        ArvoreBusca novoNo = new ArvoreBusca(tmp, sucessores.get(i), tmp.custo + sucessores.get(i).obterCusto(), 0);

                        if (!pesquisa.pesquisar(novoNo.estadoAtual.tabuleiroString)) {
                            q.add(novoNo);
                            pesquisa.inserir(novoNo.estadoAtual.tabuleiroString);
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
                    System.out.println("Custo busca largura: " + tmp.custo);
                    System.out.println("Numero de nos visitados: " + contador);
                    return;
                }
            }
            System.out.println("Nao foi encontrada uma solucao");
        } catch (Exception e){
            System.out.println("Erro ao realizar a busca" + e.getMessage());
        }
    }

}
