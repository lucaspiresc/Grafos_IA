import java.util.*;
import java.io.*;

public class conectivitySolver3 {
   
   static int cnt; // counter
   static int tempo;
   static int tipo; //grafo ou digrafo
   static int descoberta[];
   static int menor[];
   static boolean cutArestas[][];
   static boolean cutVertices[];
   static int sucessor[];
   
   public static void main( String[]args ) {
     
      List<vertice> grafo = readGrafo( "grafo.in" );
      Bridge( grafo );
      
      for( int i = 0; i < cutVertices.length; i++ ) {
         if( cutVertices[i] ) {
            System.out.print( i + "  " );
         }
      }
   }
   
   /**
	 * Puxa as informacoes de um arquivo de texto, e constroi um grafo.
    * @param String nameFile - nome do arquivo.
	 */
   public static List<vertice> readGrafo ( String nameFile ) {
      
      List<vertice> grafo;
      
      try {
         BufferedReader file = new BufferedReader(new FileReader(nameFile));
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
      
      } catch ( IOException e ) {
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
   
   public static void Bridge(List<vertice> grafo) {
      menor = new int[grafo.size()];
      sucessor = new int[grafo.size()];
      cutVertices = new boolean[grafo.size()];
      cutArestas = new boolean[grafo.size()][grafo.size()];
      descoberta = new int[grafo.size()];
      tempo = 0;
      
      for ( vertice v : grafo ) {
         if ( v.cor == 'B' ) {
            cnt = 0;
            dfs( grafo, v, v );
            if( cnt >= 2 ) {
               cutVertices[v.value] = true;
            }
         }
      }
   }

   public static void dfs(List<vertice> grafo, vertice vu, vertice vv) {
      int u = vu.value;
      int v = vv.value;
      tempo++;
      descoberta[u] = tempo;
      menor[u] = tempo;
      
      vu.cor = 'C';
      for( int w : vu.arestas ) {
         vertice vw = grafo.get(w);
         if( vw.cor == 'B' ) {
            if( u == v ) {
               cnt++;
            }
            dfs( grafo, vv, vw );
         }
      }
      vu.cor = 'P';
      
   }
      
   
}


class vertice {

   int value;
   List<Integer> arestas;
   char cor;
   int pai;
   boolean raiz; //indica se o vertice e a raiz da arvore do DFS
   
   public vertice( int x ) {
      this.value = x;
      this.arestas = new ArrayList<Integer>();
      this.cor = 'B';
      this.pai = -1;
      this.raiz = false;
   }
   
   public void addAresta( int x ) {
      arestas.add(x);
   }

}