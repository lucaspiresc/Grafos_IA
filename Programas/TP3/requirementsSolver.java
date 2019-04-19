/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.util.*;
import java.io.*;

public class requirementsSolver {

   public static ArrayList<String> ordemTop = new ArrayList<String>();

   public static void main( String[]args ) {
      
      ArrayList<vertice> grafo = buildGraph( );
      buscaProfundidade( grafo );
      
      System.out.println( "ORDEM TOPOLOGICA: " );
      for( String i : ordemTop ) {
         System.out.print( i + " -> " );
      }
        
   }
   
   /*
    * Metodo que le de um arquivo as materias e os requisitos das mesmas,
    * e modela os dados em uma lista da ajacencias.
    * @return ArrayList<vertice> grafo - Lista de vertices que sera o grafo representado.
    */
   public static ArrayList<vertice> buildGraph ( ) {
      
      ArrayList<vertice> grafo = new ArrayList<vertice>();
      ArrayList<String> classes = new ArrayList<String>();
      
      try {
         BufferedReader in = new BufferedReader( new FileReader( "materias.in" ) );
         
         String tmp = in.readLine( );
         
         while ( tmp != null ) {
            
            classes.add(tmp);
            tmp = in.readLine( );
            
         } 
         in.close( );
         
      } catch ( Exception e ) {
      }
      
      for( String i : classes ) {
         //VERTICES
         String[] firstSplit = i.split(";");
         grafo.add( new vertice(firstSplit[0].trim() ) );       
      }
      for( String i : classes ) {
      
         String[] firstSplit = i.split(";");
         //ARESTAS
         if( firstSplit.length > 1 ) {
            String[] secondSplit = firstSplit[1].split(",");
            for( int j = 0; j < secondSplit.length; j++ ) {
               grafo.get( getIndex(secondSplit[j],grafo) ).addAresta( firstSplit[0] );   
            }
         }
      }
      return grafo;
   }
   
   /*
    * Inicializacao do algoritimo de busca em profundidade adaptado para
    * ordenacao topografica.
    * @param ArrayList<vertice> grafo - grafo aonde sera feita a busca.
    */
   public static void buscaProfundidade( ArrayList<vertice> grafo ) {
      
      for( vertice u : grafo ) {
         if( u.cor == 'B' ) {
            visitar( u, grafo );
         }
      }
      
   }
   
   /*
    * Recursividade da busca em profundidade adaptada, onde, 
    * quando um vertice ficar "preto", ou seja, quando suas interecoes se encerrarem,
    * ele e adicionado no inicio da lista da ordem topologica
    * @param vertice u - vertice a ser trabalhado.
    * @param ArrayList<vertice> grafo - grafo aonde sera trabalhado o vertice.
    */
   public static void visitar( vertice u, ArrayList<vertice> grafo ) {
      u.cor = 'C';
      for( String i : u.requiredFor ) {
         vertice v = grafo.get( getIndex(i, grafo) );
         if( v.cor == 'B' ) {
            v.pai = u;
            visitar( v, grafo );
         }
      }
      u.cor = 'P';
      ordemTop.add( 0, u.className );
   }
   
   /*
    * Metodo que recebe o nome de uma materia, e retorna a posicao da mesma
    * na lista de ajacencias.
    * @param String wanted - materia que queremos achar o indice.
    * @param ArrayList<vertice> grafo - grafo onde vamos procurar a materia.
    * @return index - posicao da materia no grafo.
    */
   public static int getIndex( String wanted, ArrayList<vertice> grafo ) {
      
      int index = 0;
      for( vertice i : grafo ) {
         if( wanted.equals(i.className) ) {
            return index;
         }
         index++;
      }
      return -1;
   }
   
}

class vertice {

   char cor;
   vertice pai;
   
   String className; //Vertice: Materia.
   ArrayList<String> requiredFor; //Aresta: Materias necessarias para fazer a materia atual.

    /*
    * Construtor de classe
    */
   public vertice( String className ) {
      
      this.className = className;
      this.requiredFor = new ArrayList<String>();
      this.cor = 'B';
      this.pai = null;
      
   }
   
   /* 
    * Adiciona uma aresta na vertice atual.
    * @param String requirement - aresta a ser adicionada. 
    */
   public void addAresta( String requirement ) {
      requiredFor.add( requirement );
   }

}