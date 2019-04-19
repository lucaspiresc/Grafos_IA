import java.util.*;
import java.io.*;

public class conectivitySolver {
   
   static int tipo; //grafo ou digrafo
   static boolean isWhite[]; //coloracao dos vertices
   static int descoberta[];
   static int menor[];
   static int parent[];
   static int time;
   static List<String> cutArestas;
   static boolean cutVertices[]; // To store articulation points
   
   public static void main( String[]args ) {
     
      List<vertice> grafo = readGrafo( "grafo.in" );
      
      initialize( grafo );
      for( String i : cutArestas ) {
         System.out.println( i );
      }
      for( int i = 0; i < cutVertices.length; i++ ) {
         if( cutVertices[i] ) {
            System.out.println(i);
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
      isWhite = new boolean[n]; //coloracao dos vertices
      descoberta = new int[n]; //tempo de descoberta do vértice 
      
      //menor valor de d[v] para um vértice v alcançável 
      //na busca em profundidade a partir de u:
      menor = new int[n];
      
      parent = new int[n];
      cutVertices = new boolean[n]; //pontos de articulacao
      cutArestas = new ArrayList<String>(); //pontes
      time = 0;
      
      for (int i = 0; i < n; i++) {
         parent[i] = -1;
         isWhite[i] = false;
         cutVertices[i] = false;
      }
      
      for (int i = 0; i < n; i++) {
         if ( !isWhite[i] ) {
            tarjanMod( grafo, grafo.get(i) );
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
      int u = index.value;
      isWhite[u] = true; // 'Pinta' o vertice de branco
   
      time++;
      descoberta[u] = time;
      menor[u] = time;
   
      for( int i : index.arestas ) {
         vertice v = grafo.get( i );
         
         if (!isWhite[v.value]) {
            filhos++;
            parent[v.value] = u;
            tarjanMod(grafo, v);
            menor[u]  = Math.min(menor[u], menor[v.value]);
            
            // vertice u e um cutVertice em um dos casos:
         
            // (1) vertice u e raiz da arvore e possui dois ou mais filhos.
            if (parent[u] == -1 && filhos > 1){
               cutVertices[u] = true;
            }
               
            // (2) vertice u nao e a raiz, e o menor valor de um de seus filhos
            // e maior que o seu tempo de descoberta.
            if (parent[u] != -1 && menor[v.value] >= descoberta[u]){
               cutVertices[u] = true;
            }
         
            // If the lowest vertex reachable from subtree
            // under v is bemenor u in DFS tree, then u-v is
            // a bridge
            if (menor[v.value] > descoberta[u]) {
               cutArestas.add(u+","+v.value);
            }
         } else if (v.value != parent[u]) {
            menor[u]  = Math.min(menor[u], descoberta[v.value]);
         }
      }
      
   }
}


class vertice {

   int value;
   List<Integer> arestas;
   
   public vertice( int x ) {
      this.value = x;
      this.arestas = new ArrayList<Integer>();
   }
   
   public void addAresta( int x ) {
      arestas.add(x);
   }

}