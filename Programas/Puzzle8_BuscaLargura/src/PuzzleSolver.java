import ArvoreBinaria.ArvoreBinaria;
import Enums.IndicadorCores;
import Enums.IndicadorMovimentos;
import Grafo.Vertice;
import Puzzle.Puzzle;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PuzzleSolver {

    public static void main ( String[] args ) {
        try {
            System.out.println("Insira uma instancia de puzzle8 no formato:");
            System.out.println("XXXXXXXXX, onde X são os números de 0 até 8, ordenados no puzzle de cima para baixo, e da esquerda para direita");

            BufferedReader file = new BufferedReader(new InputStreamReader( System.in ));

            String entradaPuzzle = file.readLine();
            if(InstaciaValida(entradaPuzzle)) {
                Puzzle instanciaInicial = new Puzzle(entradaPuzzle, IndicadorMovimentos.NENHUM);

                Vertice raiz = new Vertice(instanciaInicial);

                List<Vertice> grafo = new ArrayList<>();

                grafo.add(raiz);

                List<Vertice> grafo2 = gerarGrafoCompleto(instanciaInicial);

                if (instanciaInicial.resolvivel()) {
                    buscaLargura(grafo);
                } else {
                    System.out.println("Puzzle não é resolvivel.");
                }
            }
            else{
                System.out.println("Entrada nao e um puzzle8 valido.");
            }
        } catch (Exception e ) {
            System.out.println("Erro ao resolver o Puzzle: " + e.getMessage());
        }
    }

    /*
     * Metodo que faz a busca em largura, ao mesmo tempo que preenche o grafo.
     * @param List<> grafo - grafo na qual sera realizada a busca.
     */
    public static List<Vertice> gerarGrafoCompleto (  Puzzle instanciaInicial ) {
        try {
            Vertice raiz = new Vertice(instanciaInicial);
            List<Vertice> grafo = new ArrayList<>();
            grafo.add(raiz);

            ArvoreBinaria pesquisa = new ArvoreBinaria( ); //arvore onde vamos fazer as validacoes para inserir os vertices.
            Vertice origem = grafo.get(0);
            pesquisa.inserir( origem.puzzleAtual.puzzle );

            origem.cor = IndicadorCores.CINZA;
            origem.distancia = 0;

            List<Vertice> tmp = new ArrayList<>();
            tmp.add( 0, origem );

            while( tmp.size() > 0 ) {
                //desenfileira:
                Vertice u = tmp.get(0);
                tmp.remove(0);

                //gera os filhos, e insere no grafo:
                List<Puzzle> entradas = u.puzzleAtual.fazerTrocas( );

                for( Puzzle x : entradas ) {
                    //Valida a instancia atual do puzzle a entrar no grafo:
                    if( !pesquisa.pesquisar( x.puzzle ) ) {

                        u.addAresta( x ); //aresta da instancia atual.
                        Vertice adiciona = new Vertice( x ); //novo vertice.

                        if( adiciona.cor.equals( IndicadorCores.BRANCO ) ){
                            adiciona.cor = IndicadorCores.CINZA;
                            adiciona.distancia = u.distancia+1;
                            adiciona.pai = u;

                            grafo.add( adiciona );
                            pesquisa.inserir( adiciona.puzzleAtual.puzzle );
                            tmp.add( adiciona );
                        }
                    }
                }
                u.cor = IndicadorCores.PRETO;
            }
            return grafo;
        } catch ( Exception e ) {
            return null;
        }
    }

    /*
     * Metodo que faz a busca em largura, ao mesmo tempo que preenche o grafo.
     * @param List<> grafo - grafo na qual sera realizada a busca.
     */
    public static void buscaLargura ( List<Vertice> grafo ) {
        try {

            ArvoreBinaria pesquisa = new ArvoreBinaria( ); //arvore onde vamos fazer as validacoes para inserir os vertices.
            Vertice origem = grafo.get(0);
            pesquisa.inserir( origem.puzzleAtual.puzzle );

            origem.cor = IndicadorCores.CINZA;
            origem.distancia = 0;

            List<Vertice> tmp = new ArrayList<>(); //fila de tarefas da busca em largura.
            tmp.add( 0, origem );

            while( tmp.size() > 0 ) {
                //desenfileira:
                Vertice u = tmp.get(0);
                tmp.remove(0);

                //gera os filhos, e insere no grafo:
                List<Puzzle> entradas = u.puzzleAtual.fazerTrocas( );

                for( Puzzle x : entradas ) {
                    //Valida a instancia atual do puzzle a entrar no grafo:
                    if( !pesquisa.pesquisar( x.puzzle ) ) {

                        u.addAresta( x ); //aresta da instancia atual.
                        Vertice adiciona = new Vertice( x ); //novo vertice.

                        if( adiciona.cor.equals( IndicadorCores.BRANCO ) ){
                            adiciona.cor = IndicadorCores.CINZA;
                            adiciona.distancia = u.distancia+1;
                            adiciona.pai = u;

                            grafo.add( adiciona );
                            pesquisa.inserir( adiciona.puzzleAtual.puzzle );
                            tmp.add( adiciona );
                        }
                    }
                }
                //Aqui vamos verificar se ele esta completo, e mostrar seu caminho.
                if( u.puzzleAtual.completo() ) {
                    System.out.println(u.distancia);
                    printPath(u);
                    tmp.clear();
                }
                u.cor = IndicadorCores.PRETO;
            }
        } catch ( Exception e ) {
        }
    }

    /*
     * Metodo que mostra o caminho de um dado vertice, ate a origem.
     * @param vertice i - vertice atual a ser mostrado.
     */
    public static void printPath( Vertice i ) {

        if( i.pai != null ) {
            printPath(i.pai);
            System.out.println( i.puzzleAtual.puzzle );
        }

    }

    public static boolean InstaciaValida(String puzzle){
        int a = puzzle.length();

        if(puzzle.length() != 9 || puzzle.contains("9") || !StringUtils.isNumeric(puzzle)){
            return false;
        }

        for(int i = 0; i <= 8; i++){
            if(!puzzle.contains(i+"")){
                return false;
            }
        }
        return true;
    }

}
