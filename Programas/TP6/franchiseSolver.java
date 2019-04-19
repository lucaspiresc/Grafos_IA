import java.util.*;
import java.io.*;


/* 
 * TP6: GRAFO BRUGUER
 * Grupo: 
 * LUCAS PIRES CICUTTI
 * DANIEL VINICIUS
 * GUILHERME GALVAO
 * GUSTAVO RUAS
 */

public class franchiseSolver {

   static int tipo; //grafo ou digrafo
   static List<vertice> sobra;
   
   public static void main( String[]args ) {
      
      List<vertice> grafo = readGrafo( );
      
      //APENAS 1 FRANQUIA:
      List<vertice> cI = cI( grafo );
      
      int[] f1 = getSortedArray( cI );
      
      for( int i = 0; i < f1.length; i++ ) {
         System.out.print( f1[i] );
         if( i != f1.length-1 ) {
            System.out.print( ", " );
         }
      }
      //VARIAS FRANQUIAS:
      colorNaive( grafo );
      int maxCor = getMaxCor( grafo );
      
      for ( int i = 0; i <= maxCor; i++ ) {
         System.out.print( "\nFranqueado " + (i+1) + ": " );
         for( vertice v : grafo ) {
            if( v.cor == i ) {
               System.out.print( v.value + " " );
            }
         }
      }
      
   }

   /*
    * Metodo para ordernar o vetor do conjunto independente, para ficar de acordo com a 
    * saida do verde.
    * @param List<vertice> grafo - conjunto a ser ordenado
    * @return int[] resp - vetor ordenado
    */
   public static int[] getSortedArray( List<vertice> grafo ) {
      int[] resp = new int[grafo.size()];
      int i = 0;
      for( vertice v : grafo ) {
         resp[i] = v.value;
         i++;
      }
      Arrays.sort( resp );
      return resp;
   }
   
    /**
	 * Puxa as informacoes de um arquivo de texto, e constroi um grafo.
    * @param String nameFile - nome do arquivo.
	 */
   public static List<vertice> readGrafo ( ) {
      
      List<vertice> grafo;
      
      try {
         //BufferedReader file = new BufferedReader(new InputStreamReader( System.in ));
         BufferedReader file = new BufferedReader(new FileReader("grafo.in"));
         int tipo = Integer.parseInt( file.readLine( ) );
         int size = Integer.parseInt( file.readLine( ) );
      
         grafo = new ArrayList<vertice>();
         for( int i = 0; i < size; i++ ) {
            grafo.add( new vertice(i) );
         }
         String ln = file.readLine();
         String[] vertices;
      
         while( ln.equals("FIM") == false ){
            
            vertices = ln.split(",");
            insereAresta( Integer.parseInt( vertices[0] ), Integer.parseInt( vertices[1] ), grafo );
         
            ln = file.readLine( );
         
         }
         
         file.close( );
      
      } 
      catch ( IOException e ) {
         System.out.println("File not found.");
         return null;
      }
      
      return grafo;
   
   }
   
   /**
	 * Insere as arestas de um grafo, levando em consideracao ele ser digrafo ou nao
    * @param int verticeA, verticeB - vertices ligados pela aresta
    * @param List<vertice> grafo - grafo onde sera inserida a aresta
	 */
   public static void insereAresta( int verticeA, int verticeB, List<vertice> grafo ) {
   
      grafo.get(verticeA).addAresta( verticeB );
      
      if( tipo == 0 ) {
         grafo.get(verticeB).addAresta( verticeA );
      }
   }
   
    /**
	 * Algoritimo proposto em sala de aula para encontrar conjuntos independentes.
    * @param List<vertice> grafo - grafo a ser trabalhado.
    * @return List<vertice> cI - conjunto independente do grafo.
	 */
   public static List<vertice> cI ( List<vertice> grafo ) {
      List<vertice> cI = new ArrayList<vertice>();
      sobra = new ArrayList<vertice>();
      for( vertice v : grafo ) {
         if( !v.deleted ) {
            cI.add(v);
            remover( grafo, v );
         }
      }
      
      return cI;
   }
   
   /**
	 * Metodo para "remover" os vertices do grafo.
    * A remocao e feita apenas marcando o vertice como "deletado", para que nas interacoes
    * futuras do algoritimo de conjuntos independentes, este vertice seja ignorado.
    * @param List<vertice> grafo - grafo a ser trabalhado.
    * @param vertice v - vertice do grafo a ser deletado.
	 */
   public static void remover( List<vertice> grafo, vertice v ) {
      
      v.deleted = true;
      for( int i : v.arestas ) {
         vertice tmp = grafo.get(i);
         tmp.deleted = true;
         if( !sobra.contains(tmp) ) {
            sobra.add(tmp);
         }
      }
      
   }
   
   /*
   CARAM, AINDA CONTINUO CONFUSO COM RELACAO A ENTRADA/SAIDA DO VERDE,
   ENTAO EU IMPLEMENTEI O ALGORITIMO DE COLOCARAO, POREM NAO UTILIZEI UMA VEZ QUE
   A SAIDA PUBLICA NAO DA 100% com ELE. 
   POR FAVOR CORRIJA COMO O SENHOR ACHAR CONVENIENTE,
   obrigado, Lucas.
    */
    
   /**
	 * Metodo que faz a colocoracao ingenua de um grafo
    * @param List<vertice> grafo - grafo a ser colorido.
	 */
   public static void colorNaive( List<vertice> grafo ) {
      
      int maxCor = 0;
      grafo.get(0).cor = maxCor;
      
      for( int j = 1; j < grafo.size(); j++ ) {
         vertice i = grafo.get(j);
         int k = getCor( grafo, i );
         if( k == -1 ) {
            maxCor++;
            System.out.println( maxCor );
            i.cor = maxCor;
         } 
         else {
            i.cor = k;
         }
          
      }
   }
   
   /**
	 * Metodo para determinar a cor do vertice atual, com base nas cores de seus vizinhos.
    * @param List<vertice> grafo - grafo a ser trabalhado.
    * @param vertice v - vertice do grafo a ser colorido.
	 */
   public static int getCor ( List<vertice> grafo , vertice v ) {
      
      boolean cores[] = new boolean[grafo.size()];
      for( int k = 0; k < cores.length; k++ ) {
         cores[k] = false;
      }
      for( int i : v.arestas ) {
         vertice u = grafo.get(i);
         if( u.cor != -1 ) {
            cores[u.cor] = true;
         }
      }
      int menorUsado = -1;
      for( int k = 0; k < cores.length; k++ ) {
         if( !cores[k] ) {
            menorUsado = k;
            k = cores.length;
         }
      }
      return menorUsado;
   }
   
   /*
    * Metodo que retorna o numero de cores utilizadas para colorir
    * o grafo.
    * @param List<vertice> grafo - grafo em questao
    * @return int resp - numero de cores do grafo
    */
   public static int getMaxCor ( List<vertice> grafo ) {
      int resp = 0;
      for( vertice v : grafo ) {
         if( v.cor > resp ) {
            resp = v.cor;
         }
      }
      return resp;
   }
}

class vertice {

   int value;
   List<Integer> arestas;
   int cor;
   char corDFS;
   boolean deleted; //indica se o vertice foi deletado
   int componente;
   boolean visited;
   
   public vertice( int x ) {
      this.value = x;
      this.arestas = new ArrayList<Integer>();
      this.cor = -1;
      this.corDFS = 'B';
      this.componente = -1;
      this.deleted = false;
      this.visited = false;
   }
   
   public void addAresta( int x ) {
      arestas.add(x);
   }

}

/*
0
6
0,1,1
0,2,1
1,3,1
2,3,1
3,4,1
3,5,1
4,5,1
FIM

*/