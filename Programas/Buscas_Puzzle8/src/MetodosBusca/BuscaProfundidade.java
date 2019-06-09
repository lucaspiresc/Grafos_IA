package MetodosBusca;

import ArvorePesquisa.ArvoreBinaria;
import Puzzle8.ArvoreBusca;
import Puzzle8.InstanciaPuzzle;

import java.util.ArrayList;
import java.util.Stack;

public class BuscaProfundidade {

    public static void buscaProfundidade(int[] tabuleiro)
    {
        try {
            ArvoreBusca raiz = new ArvoreBusca(new InstanciaPuzzle(tabuleiro));
            Stack<ArvoreBusca> pilha = new Stack<ArvoreBusca>();

            /*
             * Arvore binaria sera utlizada para verificar se uma instancia do puzzle8
             * já foi obtida. Isto será feito para melhorar a peformance e evitar que
             * o algortimo fique preso em um loop(faz um movimento, e depois volta para o outro estado infinitamente)
             */
            ArvoreBinaria pesquisa = new ArvoreBinaria();
            pesquisa.inserir(raiz.estadoAtual.tabuleiroString);

            pilha.add(raiz);

            fazerBusca(pilha, pesquisa);
        } catch (Exception e){
            System.out.println("Erro ao realizar a busca" + e.getMessage());
        }
    }

    /*
     * Realiza a busca em profundidade
     */
    public static void fazerBusca(Stack<ArvoreBusca> s, ArvoreBinaria pesquisa)
    {
        try {
            int contador = 1;

            while (!s.isEmpty()) {
                ArvoreBusca tmp = s.pop();

                if (!tmp.estadoAtual.isPuzzleFinal()) {
                    //Se o estado atual nao e o final, expande-o
                    ArrayList<InstanciaPuzzle> sucessores = tmp.estadoAtual.gerarEstados();

                    /*
                     * Itera sobre os sucessores desta instacia, verifica se ja foram avalizados,
                     * e caso nao tenham sido, adiciona-os na fila
                     */
                    for (int i = sucessores.size() - 1; i >= 0; i--) {
                        ArvoreBusca novoNo = new ArvoreBusca(tmp, sucessores.get(i), tmp.custo + sucessores.get(i).obterCusto(), 0);

                        if (!pesquisa.pesquisar(novoNo.estadoAtual.tabuleiroString)) {
                            s.add(novoNo);
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
                    System.out.println("Custo busca profundidade: " + tmp.custo);
                    System.out.println("Numero de nos visitados: " + contador);
                    System.out.println("Numero de movimentos para a solucao: " + (tamanhoPilha-1));
                    return;
                }
            }

            System.out.println("Nao foi encontrada uma solucao");
        }catch (Exception e){
            System.out.println("Erro ao realizar a busca" + e.getMessage());
        }
    }

}
