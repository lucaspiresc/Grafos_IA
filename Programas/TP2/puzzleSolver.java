/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.util.*;
import java.io.*;

public class puzzleSolver {

   public static void main ( String[] args ) {
   
      try {
         BufferedReader file = new BufferedReader(new InputStreamReader( System.in ));
         int size = Integer.parseInt( file.readLine() );
         
         for( int i = 0; i < size; i++ ) {
            String teste = file.readLine();
         
            puzzle8 test = new puzzle8 ( teste ,  "" );
            vertice add = new vertice( test );
            List<vertice> grafo = new ArrayList<vertice>();      
            grafo.add( add );
            
            if( test.isSolvable() ) {
               buscaLargura( grafo );
            } else {
               System.out.println("NAO");
            }
         }
      } catch (Exception e ) {
      }
   }

   /**
    * Retorna o timestamp atual
    * @return timestamp atual
    */
   public static long now(){
      return new Date().getTime();
   }

   /*
    * Metodo que faz a busca em largura, ao mesmo tempo que preenche o grafo.
    * @param List<> grafo - grafo na qual sera realizada a busca.
    */
   public static void buscaLargura ( List<vertice> grafo ) {
      try {
      
         ArvoreBinaria pesquisa = new ArvoreBinaria( ); //arvore onde vamos fazer as validacoes para inserir os vertices.
         vertice origem = grafo.get(0);
         pesquisa.inserir( origem.puzzleAtual.puzzle );
      
         origem.cor = "GREY";
         origem.distancia = 0;
      
         List<vertice> tmp = new ArrayList<vertice>(); //fila de tarefas da busca em largura.
         tmp.add( 0, origem );
      
         while( tmp.size() > 0 ) {
            //desenfileira:
            vertice u = tmp.get(0);
            tmp.remove(0);
            //gera os filhos, e insere no grafo:
            List<puzzle8> entradas = u.puzzleAtual.makeMoves( );
         
            for( puzzle8 x : entradas ) {
               //Valida a instancia atual do puzzle a entrar no grafo:
               if( !pesquisa.pesquisar( x.puzzle ) ) {
               
                  u.addAresta( x ); //aresta da instancia atual.
                  vertice adiciona = new vertice( x ); //novo vertice.
               
                  if( adiciona.cor.equals( "WHITE" ) ){
                     adiciona.cor = "GREY";
                     adiciona.distancia = u.distancia+1;
                     adiciona.pai = u;
                  
                     grafo.add( adiciona );
                     pesquisa.inserir( adiciona.puzzleAtual.puzzle );
                     tmp.add( adiciona );
                  }
               }
            }
         //Aqui vamos verificar se ele esta completo, e mostrar seu caminho.
            if( u.puzzleAtual.isComplete() ) {
               System.out.println(u.distancia);
               printPath(u);
               tmp.clear();
            }
            u.cor = "BLACK";
         }
      } catch ( Exception e ) {
      }
   }

   /*
    * Metodo que mostra o caminho de um dado vertice, ate a origem.
    * @param vertice i - vertice atual a ser mostrado.
    */
   public static void printPath( vertice i ) {
   
      if( i.pai != null ) {
         printPath(i.pai);
         System.out.println( i.puzzleAtual.puzzle );
      } 
   
   }

}

class vertice {

   String cor;
   vertice pai;
   int distancia;

   //Instancia do puzzle8 deste vertice:
   puzzle8 puzzleAtual;
   //Lista contendo as instancias do puzzle que este vertice gera, ou seja, suas arestas:
   List<puzzle8> arestas;

   /**
     * Construtor da classe.
     * @param puzzle8 puzzle - instancia do puzzle8 que sera esta vertice.
     */
   public vertice( puzzle8 puzzle ) {
   
      this.puzzleAtual = puzzle;
      this.arestas = new ArrayList<puzzle8>();
   
      this.distancia = -1;
      this.cor = "WHITE";
      this.pai = null;
   }

   /**
     * Metodo que adiciona uma aresta a este vertice.
     * @param puzzle8 aresta - aresta a ser adicionada.
     */
   public void addAresta( puzzle8 aresta ) {
      arestas.add( aresta );
   }

}

class puzzle8 {

   String puzzle; //Instacia atual do puzzle.
   String orign; //De onde veio o espaco vazio dessa instancia.

    /**
     * Construtor da classe.
     * @param String puz - instancia do puzzle8.
     * @param String code - movimento que gerou esta instancia.
     */
   public puzzle8 ( String puz, String code ) {
   
      this.puzzle = puz + "";
      this.orign = code;
   
   }

  /*
   * Metodo que encontra a posicao do zero (posicao vazia) dentro do puzzle.
   * @return int resp - posicao do 0 na matriz.
   */
   public int findZero ( ) {
   
      int resp = -1;
      for( int i = 0; i < puzzle.length( ); i++) {
         if( puzzle.charAt(i) == '0' ) {
            resp = i;
            i = puzzle.length( );
         }
      }
      return resp;
   }

  /*
   * Metodo que descobre quais movimentos o puzzle e capaz de fazer na posicao atual.
   * @return List<> moves - Lista contendo os movimentos validos para aquela posicao.
   */
   public List<String> getMoves ( ) {
   
      List<String> moves = new ArrayList<String>();
   
      int pos = findZero( );
   
      if( pos > 2 && !orign.equals("DOWN") ) {
         moves.add("UP");
      }
      if( pos < 6 && !orign.equals("UP") ) {
         moves.add("DOWN");
      }
      if( ( pos % 3 != 0 ) && !orign.equals("RIGHT") ) {
         moves.add("LEFT");
      }
      if( ( pos % 3 != 2 ) && !orign.equals("LEFT") ) {
         moves.add("RIGHT");
      }
      return moves;
   }

  /*
   * Metodo que coordena todas as trocas a serem feitas nesse instante do puzzle.
   * @return List<puzzle8> possibleMoves - Lista contendo puzles com todas as trocas possiveis do instante "original".
   */
   public List<puzzle8> makeMoves ( ) {
   
      List<String> moves = getMoves( );
      List<puzzle8> possibleMoves = new ArrayList<puzzle8>();
      int zero = findZero( );
   
      for( String i : moves ) {
      
         String newPuzzle = switchPieces( zero, puzzle, i );
         puzzle8 move = new puzzle8( newPuzzle , i );
         possibleMoves.add ( move );
      }
   
      return possibleMoves;
   }

  /*
   * Metodo que faz a troca das pecas dentro do puzzle.
   * @param int pos - posicao do zero,ou peca vazia, dentro do puzzle.
   * @param String matrix[][] - puzzle "original" antes de ser alterado.
   * @param String move - comando que vai determinar a direcao do movimento do puzzle.
   * @return String resp - puzzle com as pecas trocadas.
   */
   public String switchPieces ( int pos, String matrix, String move ) {
   
      char[] resp = matrix.toCharArray();
      char tmp = resp[pos];
   
      if ( move.equals("UP") ){
      
         resp[pos] = resp[pos-3];
         resp[pos-3] = tmp;
      
      } else if ( move.equals("DOWN") ) {
      
         resp[pos] = resp[pos+3];
         resp[pos+3] = tmp;
      
      } else if ( move.equals("LEFT") ) {
      
         resp[pos] = resp[pos-1];
         resp[pos-1] = tmp;
      
      } else if ( move.equals("RIGHT") ) {
      
         resp[pos] = resp[pos+1];
         resp[pos+1] = tmp;
      
      }
      return new String( resp );
   }

  /*
   * Metodo que verifica se o puzzle foi solucionado.
   * @return estado atual do puzzle(solucionado ou nao).
   */
   public boolean isComplete ( ) {
   
      if( puzzle.equals("123456780") ) {
         return true;
      }
      return false;
   
   }

  /*
   * Metodo que verifica se e possivel resolver o puzzle.
   * @return boolean - possibilidade(ou nao) de resolucao do puzzle.
   */
   public boolean isSolvable( ) {
      int num = 0;
   
      for( int i = 0; i < puzzle.length( ); i++ ) {
         num += checkSmaller( i );
      }
   
      if( num % 2 == 0 ) {
         return true;
      }
      return false;
   
   }

  /*
   * Metodo que conta quantos elementos que deveriam estar atras do atual,
   * estao na verdade na sua frente.
   * @return int resp - numero de elementos.
   */
   public int checkSmaller ( int i ) {
      int resp = 0;
      for( int a = i; a < puzzle.length( ); a++ ) {
      
         if( ( puzzle.charAt( a ) != '0' ) && ( puzzle.charAt( a ) < puzzle.charAt( i ) ) ){
            resp++;
         }
      }
      return resp;
   }

}


/**
 * No da arvore binaria
 * @author Max do Val Machado
 */
class No {
   public String elemento; // Conteudo do no.
   public No esq, dir;  // Filhos da esq e dir.

    /**
     * Construtor da classe.
     * @param elemento Conteudo do no.
     */
   public No(String elemento) {
      this(elemento, null, null);
   }

    /**
     * Construtor da classe.
     * @param elemento Conteudo do no.
     * @param esq No da esquerda.
     * @param dir No da direita.
     */
   public No(String elemento, No esq, No dir) {
      this.elemento = elemento;
      this.esq = esq;
      this.dir = dir;
   }
}

/**
 * Arvore binaria de pesquisa
 * @author Max do Val Machado
 */
class ArvoreBinaria {
   private No raiz; // Raiz da arvore.

    /**
     * Construtor da classe.
     */
   public ArvoreBinaria() {
      raiz = null;
   }

    /**
     * Metodo publico iterativo para pesquisar elemento.
     * @param x Elemento que sera procurado.
     * @return <code>true</code> se o elemento existir,
     * <code>false</code> em caso contrario.
     */
   public boolean pesquisar(String x) {
      return pesquisar(x, raiz);
   }

    /**
     * Metodo privado recursivo para pesquisar elemento.
     * @param x Elemento que sera procurado.
     * @param i No em analise.
     * @return <code>true</code> se o elemento existir,
     * <code>false</code> em caso contrario.
     */
   private boolean pesquisar(String x, No i) {
      boolean resp;
      if (i == null) {
         resp = false;
      
      } else if (x.equals(i.elemento)) {
         resp = true;
      
      } else if ( ( x.compareTo( i.elemento ) ) > 0) {
         resp = pesquisar(x, i.esq);
      
      } else {
         resp = pesquisar(x, i.dir);
      }
      return resp;
   }

    /**
     * Metodo publico iterativo para inserir elemento.
     * @param x Elemento a ser inserido.
     * @throws Exception Se o elemento existir.
     */
   public void inserir(String x) throws Exception {
      raiz = inserir(x, raiz);
   }

    /**
     * Metodo privado recursivo para inserir elemento.
     * @param x Elemento a ser inserido.
     * @param i No em analise.
     * @return No em analise, alterado ou nao.
     * @throws Exception Se o elemento existir.
     */
   private No inserir(String x, No i) throws Exception {
      if (i == null) {
         i = new No(x);
      
      } else if (( x.compareTo( i.elemento ) ) > 0) {
         i.esq = inserir(x, i.esq);
      
      } else if (( x.compareTo( i.elemento ) ) < 0) {
         i.dir = inserir(x, i.dir);
      
      } else {
         throw new Exception("Erro ao inserir!");
      }
   
      return i;
   }
}
