/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.io.*;

public class grafoMatrizObjeto {

   static int tipo; //Grafo ou Digrafo

   public static void main( String[]args ) {

      Vertice grafo[][] = readGrafo( );

      System.out.println( contaArestas( grafo ) );

      boolean completo = isComplete( grafo );
      if( completo ) {
         System.out.println( "SIM" );
      } else {
         System.out.println( "NAO" );
      }

      Vertice comp[][] = criaComplementar( grafo );
      mostraGrafo( comp );

   }

   /**
	 * Puxa as informacoes de um arquivo de texto, e constroi um grafo.
    * @param String nameFile - nome do arquivo.
	 */
   public static Vertice[][] readGrafo( ) {

      Vertice[][] grafo;

      try {
         BufferedReader file = new BufferedReader(new InputStreamReader( System.in ));
         tipo = Integer.parseInt( file.readLine( ) );
         int size = Integer.parseInt( file.readLine( ) );

         grafo = new Vertice[size][size];
         for( int i = 0; i < size; i++ ) {
            for( int j = 0; j < size; j++ ) {
               grafo[i][j] = new Vertice( );
            }
         }
         String ln = file.readLine();
         String[] vertices;

         while( ln.equals("FIM") == false ){

            vertices = ln.split(",");
            adicionaAresta( Integer.parseInt( vertices[0] ), Integer.parseInt( vertices[1] ), Integer.parseInt( vertices[2] ), grafo );

            ln = file.readLine( );

         }

         System.out.println( descobreGrau( Integer.parseInt( file.readLine() ), grafo ) );

         ln = file.readLine();
         vertices = ln.split(",");
         procuraAresta( Integer.parseInt( vertices[0] ), Integer.parseInt( vertices[1] ), grafo );

         file.close( );

      } catch ( IOException e ) {
         System.out.println("File not found.");
         return null;
      }

      return grafo;

   }

    /**
	 * Recebe um grafo, e gera o seu grafo complementar.
    * @param Vertice[][] grafo - Grafo no qual sera gerado o grafo complementar.
    * @return Vertice[][] tmp - Grafo complementar preenchido.
	 */
   public static Vertice[][] criaComplementar( Vertice[][] grafo ) {

      Vertice[][] tmp = new Vertice[grafo.length][grafo.length];

      for( int i = 0; i < tmp.length; i++ ) {
         for( int j = 0; j < tmp.length; j++ ) {
            tmp[i][j] = new Vertice( );
         }
      }


      for( int i = 0; i < grafo.length; i++ ) {
         for( int j = 0; j < grafo[i].length; j++ ) {
            if( i != j ) {
               if( grafo[i][j].aresta == 1 ) {
                  tmp[i][j].aresta = 0;
               } else {
                  tmp[i][j].aresta = 1;
               }
            }
         }
      }
      return tmp;
   }

   /**
	 * Metodo que mostra as ligacoes do Grafo na tela.
    * @param Vertice[][] grafo - grafo a ser impresso na tela.
	 */
   public static void mostraGrafo( Vertice[][] grafo ) {

      if( tipo == 0 ) {
         for( int i = 0; i < grafo.length; i++ ) {
            for( int j = 0; j < grafo[0].length; j++ ) {
               if( j > i ) {
                  if( grafo[i][j].aresta == 1 ) {
                     System.out.println( i + "," + j + "," + grafo[i][j].peso );
                  }
               }
            }
         }
      } else {
         for( int i = 0; i < grafo.length; i++ ) {
            for( int j = 0; j < grafo[0].length; j++ ) {
               if( grafo[i][j].aresta == 1 ) {
                  System.out.println( i + "," + j + "," + grafo[i][j].peso );
               }
            }
         }
      }
   }

   /**
	 * Adiciona uma conexao entre 2 vertices no grafo.
    * @param int verticeA,verticeB - vertices que vao ser conectados.
    * @param int peso - peso da aresta a ser incluida.
    * @param Vertice[][] grafo - grafo contendo os 2 vertices.
	 */
   public static void adicionaAresta( int verticeA, int verticeB, int peso, Vertice[][] grafo ) {

      grafo[verticeA][verticeB].aresta = 1;
      grafo[verticeA][verticeB].peso = peso;

      if( tipo == 0 ) {

         grafo[verticeB][verticeA].aresta = 1;
         grafo[verticeB][verticeA].peso = peso;

      } else {
         grafo[verticeB][verticeA].aresta = -1;
      }


   }

    /**
	 * Remove uma conexao entre 2 vertices no grafo.
    * @param int verticeA,verticeB - vertices que vao ser desconectados.
    * @param Vertice[][] grafo - grafo contendo os 2 vertices.
	 */
   public static void removeAresta( int verticeA, int verticeB, Vertice[][] grafo ) {

      grafo[verticeA][verticeB].aresta = 0;
      grafo[verticeA][verticeB].peso = 0;

      if( tipo == 0 ) {
         grafo[verticeB][verticeA].aresta = 0;
         grafo[verticeB][verticeA].peso = 0;
      }
   }

   /**
	 * Verifica se existe uma aresta entra 2 vertices especificos.
    * @param int verticeA,verticeB - vertices a serem consultados.
    * @param Vertice[][] grafo - grafo contendo os 2 vertices.
	 */
   public static void procuraAresta( int verticeA, int verticeB, Vertice[][] grafo ) {

      if( (grafo[verticeA][verticeB].aresta == 1) ) {
         System.out.println( "SIM" );
      } else {
         System.out.println( "NAO" );
      }

   }

   /**
	 * Descobre o grau de um determinado vertice de um grafo.
    * @param int vertice - vertice a ser verificado.
    * @param Vertice[][] grafo - grafo a ser verificado.
    * @return int resp - grau do vertice.
	 */
   public static int descobreGrau( int vertice, Vertice[][] grafo ) {

      int resp = 0;
      for( int i = 0; i < grafo.length; i++ ) {
         if( grafo[vertice][i].aresta == 1 ) {
            resp++;
         }
      }
      return resp;
   }

   /**
	 * Verifica se um dado grafo e completo ou nao.
    * @param Vertice[][] grafo - grafo a ser verificado.
	 */
   public static boolean isComplete( Vertice[][] grafo ) {

      for ( int i = 0; i < grafo.length; i++ ) {
         for( int j = 0; j < grafo.length; j++ ) {
            if( grafo[i][j].aresta != 1 ) {
               return false;
            }
         }
      }

      return true;
   }

   /**
	 * Conta o numero de arestas contidas em um grafo.
    * @param Vertice[][] grafo - grafo a ser verificado.
    * @return int resp - numero de arestas contidas no grafo.
	 */
   public static int contaArestas( Vertice[][] grafo ) {

      int resp = 0;
      if( tipo == 0 ) {
         for ( int i = 0; i < grafo.length; i++ ) {
            for( int j = 0; j < grafo.length; j++ ) {
               if( j > i ) {
                  if( grafo[i][j].aresta == 1 ) {
                     resp++;
                  }
               }
            }
         }
      } else {
         for ( int i = 0; i < grafo.length; i++ ) {
            for( int j = 0; j < grafo.length; j++ ) {
               if( grafo[i][j].aresta == 1 ) {
                  resp++;
               }
            }
         }
      }
      return resp;

   }

}

class Vertice {

   int aresta;
   int peso;

   /**
	 * Construtor da classe.
	 */
   public Vertice( ) {

      this.aresta = 0;
      this.peso = 1;

   }

}
