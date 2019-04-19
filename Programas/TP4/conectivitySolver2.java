import java.util.*;
import java.io.*;

public class conectivitySolver2 {
   
   static int tipo; //grafo ou digrafo
   static int descoberta[];
   static int menor[];
   static int time;
   static boolean cutArestas[][];
   static boolean cutVertices[];
   
   public static void main( String[]args ) {
     
      List<vertice> grafo = readGrafo( "grafo.in" );
      
      initialize( grafo );
      System.out.print("VERTICES ");
      for( int i = 0; i < cutVertices.length; i++ ) {
         if( cutVertices[i] ) {
            System.out.print(i+" ");
         }
      }
      System.out.print("\nARESTAS ");
      for( int i = 0; i < cutArestas.length; i++ ) {
         for( int j = 0; j < cutArestas.length; j++ ) {
            if( cutArestas[i][j] ) {
               System.out.print( i + "" + j + " " );
            }
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
   
   /*
    * Inicializacao das variaveis e da recursidade do algoritimo
    * para encontrar pontos de articulacao e pontes.
    * @param List<vertice> grafo - grafo a ser realizada a busca
    */
   public static void initialize( List<vertice> grafo ) {
      
      int n = grafo.size();
      descoberta = new int[n]; //tempo de descoberta do vértice 
      
      //menor valor de d[v] para um vértice v alcançável 
      //na busca em profundidade a partir de u:
      menor = new int[n];
      int flag = 0;
      
      cutVertices = new boolean[n]; //pontos de articulacao
      cutArestas = new boolean[n][n]; //pontes
      time = 0;
      
      for (int i = 0; i < n; i++) {
         cutVertices[i] = false;
      }
      
      for (vertice i : grafo) {
         if ( i.cor == 'B' ) {
            if( flag == 0 ) {
               i.raiz = true;
               flag++;
            }
            tarjanMod( grafo, i );
         }
      }
   
   }
   
   /*
    * Algorimo de tarjan modificado para encontrar pontes e pontos de articulacao
    * @param List<vertice> grafo - grafo a ser realizada a busca
    * @param vertice index - vertice a ser trabalhado nesta instancia
    */
   public static void tarjanMod( List<vertice> grafo, vertice index ) {
   
      int filhos = 0; //Quantidade de filhos da arvore DFS
      index.cor = 'C';
      int u = index.value;
   
      time++;
      descoberta[u] = time;
      menor[u] = time;
   
      for( int i : index.arestas ) {
         vertice v = grafo.get( i );
         
         if ( v.cor == 'B' ) {
            filhos++;
            v.pai = u;
            tarjanMod(grafo, v);
            menor[u]  = Math.min(menor[u], menor[v.value]);
            
            // vertice u e um cutVertice em um dos casos:
         
            // (1) vertice u e raiz da arvore e possui dois ou mais filhos.
            if ( (index.raiz) && filhos > 1){
               cutVertices[u] = true;
            }
               
            // (2) vertice u nao e a raiz, e o menor valor de um de seus filhos
            // e maior que o seu tempo de descoberta.
            if ( (!index.raiz) && menor[v.value] >= descoberta[u]){
               cutVertices[u] = true;
            }
         
            // If the lowest vertex reachable from subtree
            // under v is below u in DFS tree, then u-v is
            // a bridge
            if (menor[v.value] > descoberta[u]) {
               cutArestas[u][v.value] = true;
            }
         } else if (v.value != index.pai) {
            menor[u]  = Math.min(menor[u], descoberta[v.value]);
         }
      }
      index.cor = 'P';
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