/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.io.*;

public class grafoMateria {

   public static void main( String[]args ) {

      Lista grafo[] = readGrafo( );

      mostraGrafo( grafo );


   }

   /**
	 * Puxa as informacoes de um arquivo de texto, e constroi um grafo.
    * @param String nameFile - nome do arquivo.
	 */
   public static Lista[] readGrafo ( ) {

      Lista[] grafo;

      try {
         BufferedReader file = new BufferedReader(new InputStreamReader( System.in ));

         grafo = new Lista[11];
         String[] materias = new String[11];

         for( int i = 0; i < materias.length; i++ ) {
            materias[i] = file.readLine( );
         }

         for( int i = 0; i < grafo.length; i++ ) {
            String[] materiaCriar = materias[i].split(";");
            grafo[i] = new Lista( );
            grafo[i].top.materia = materiaCriar[0];

         }

         for( int i = 0; i < grafo.length; i++ ) {
            String tmp[] = materias[i].split(";");

            if( tmp.length > 1 ) {
               String requisitos[] = tmp[1].split(",");

               for( int j = 0; j < requisitos.length; j++ ) {
                  insereRequisito( i, requisitos[j], grafo );
               }
            }
         }

         file.close( );

      }catch ( IOException e ) {
         System.out.println("File not found.");
         return null;
      }

      return grafo;

   }


   /**
	 * Metodo que mostra as ligacoes do Grafo na tela.
    * @param Lista[] grafo - grafo a ser impresso na tela.
	 */
   public static void mostraGrafo( Lista[] grafo ) {
      Celula tmp;
      for( int i = 0; i < grafo.length; i++ ) {
         System.out.print( grafo[i].top.materia + ";" );
         tmp = grafo[i].top.prox;
         while( tmp != null ) {
            System.out.print( tmp.materia + "," );

            tmp = tmp.prox;
         }
         System.out.println( "" );
      }
   }

   /**
	 * Adiciona uma conexao entre 2 vertices no grafo.
    * @param int verticeA,verticeB - vertices que vao ser conectados.
    * @param int peso - peso da aresta a ser incluida.
    * @param Lista[] grafo - grafo contendo os 2 vertices.
	 */
   public static void insereRequisito( int materia, String requisito, Lista[] grafo ) {

      grafo[materia].inserir( requisito );

   }

}

class Lista {

   Celula top, bottom;
   int size; //grau do vertice

   /**
	 * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
	 */
   public Lista( ) {
      this.top = new Celula( );
      this.bottom = top;
      this.size = 0;
   }

   /**
	 * Insere um elemento na ultima posicao da lista.
    * @param String materia - elemento aresta a ser inclusa no grafo.
    */
   public void inserir( String materia ) {

      bottom.prox = new Celula( materia );
      bottom = bottom.prox;

      size++;
   }

}

class Celula {

   String materia; //a materia da cabeca sera o Vertice, e as seguintes serao as arestas.
   Celula prox;

   /**
	 * Construtor da classe.
	 */
   public Celula( ) {
      this.materia = "";
      this.prox = null;
   }

    /**
	 * Construtor da classe.
    * @param nome - nome da materia em questao.
	 */
   public Celula( String nome ) {
      this.materia = nome;
      this.prox = null;
   }

}
