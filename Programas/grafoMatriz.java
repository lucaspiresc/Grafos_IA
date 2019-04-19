/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */
 
import java.io.*;

public class grafoMatriz {

   public static void main( String[]args ) {
      
      String nameFile = "teste.txt";
      int grafo[][] = readGrafo( nameFile );
      
      int comp[][] = criaComplementar( grafo );
     
      mostraGrafo( grafo );
      System.out.println( " ---- " );
      mostraGrafo( comp );
      
      procuraAresta( 2, 4, comp );
      procuraAresta( 1, 4, comp );
      procuraAresta( 3, 4, comp );
      
   }
   
   public static int[][] readGrafo( String nameFile ) {
      
      int[][] grafo;
      
      try {
         BufferedReader file = new BufferedReader(new FileReader(nameFile));
         int size = Integer.parseInt( file.readLine( ) );
      
         grafo = new int[size][size];
         String ln = file.readLine();
         String[] vertices = new String[2];
      
         while( ln.equals("FIM") == false ){
            
            vertices = ln.split(",");
            adicionaAresta( Integer.parseInt( vertices[0] ), Integer.parseInt( vertices[1] ), grafo );
         
            ln = file.readLine( );
         
         }
         file.close( );
      
      } catch ( IOException e ) {
         System.out.println("File not found.");
         return null;
      }
      
      return grafo;
   
   }
   
   public static int[][] criaComplementar( int[][] grafo ) {
      
      int[][] tmp = new int[grafo.length][grafo.length];
      
      for( int i = 0; i < grafo.length; i++ ) {
         for( int j = 0; j < grafo[i].length; j++ ) {
            if( i != j ) {
               if( grafo[i][j] == 1 ) {
                  tmp[i][j] = 0;
               } else {
                  tmp[i][j] = 1;
               }
            }
         }
      }   
      return tmp;
   }
   
   public static void mostraGrafo( int[][] grafo ) {
   
      for( int i = 0; i < grafo.length; i++ ) {
         for( int j = 0; j < grafo[0].length; j++ ) {
            if( j > i ) {
               if( grafo[i][j] == 1 ) {
                  System.out.println( i + "," + j);
               }
            }
         }
      }
      
   }
   
   public static void adicionaAresta( int verticeA, int verticeB, int[][] grafo ) {
      
      grafo[verticeA][verticeB] = 1;
      grafo[verticeB][verticeA] = 1; 
      
   } 
   
   public static void removeAresta( int a, int b, int[][] grafo ) {
      grafo[a][b] = 0;
      grafo[b][a] = 0;
   }
   
   public static void procuraAresta( int verticeA, int verticeB, int[][] grafo ) {
   
      if( (grafo[verticeA][verticeB] == 1) || (grafo[verticeB][verticeA] == 1) ) {
         System.out.println( "Os vertices " + verticeA + " e " + verticeB + " formam uma aresta entre si." );
      } else {
         System.out.println( "Os vertices " + verticeA + " e " + verticeB + " NAO formam uma aresta entre si." );
      } 
   
   }

}

class Vertice {

   int aresta;
   int peso;
   
   public Vertice( ) {
      
      this.aresta = 0;
      this.peso = 1;
   
   }

}