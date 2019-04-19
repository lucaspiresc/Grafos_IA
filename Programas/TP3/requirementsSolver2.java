/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.util.*;
import java.io.*;

public class requirementsSolver2 {

   public static ArrayList<String> ordemTop = new ArrayList<String>();

   public static void main( String[]args ) {

      ArrayList<vertice> grafo = buildGraph( );
      Khan( grafo );

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
               grafo.get( getIndex(secondSplit[j], grafo) ).addAresta( firstSplit[0] );
               grafo.get( getIndex( firstSplit[0], grafo ) ).arcoEntrada++;
            }
         }
      }
      return grafo;
   }

   /*
    * Algoritimo de ordenacao topologica de Khan, onde vamos pegar os vertices sem entradas,
    * remover suas saidas, e caso dita saida agora nao tenha mais entradas, pegaremos ela tambem,
    * adicionando os vertices sem entrada na lista topologica,
    * e repentindo o processo ate que o grafico fique completamente desconexo.
    * @param ArrayList<vertice> grafo - grafo aonde sera feita a ordenacao.
    */
   public static void Khan ( ArrayList<vertice> grafo ) {

      ArrayList<vertice> s = new ArrayList<vertice>();

      for( vertice i : grafo ) {
         if( i.arcoEntrada == 0 ) {
            s.add( i );
         }
      }
      while( s.size() > 0 ) {
         vertice v = s.get(0);
         s.remove( 0 );

         ordemTop.add( v.className );
         for( String i : v.requiredFor ) {
            vertice tmp = grafo.get( getIndex( i, grafo ) );

            tmp.arcoEntrada--;
            if( tmp.arcoEntrada == 0 ) {
               s.add( tmp );
            }
         }
         v.requiredFor.clear( );
      }
      if( edgeCount( grafo ) > 0 ) {
         System.out.println("Grafo inserido nao pode ser topologicamente ordenado.");
      }

   }

   /*
    * Metodo que conta e retorna a soma das arestas restantes no grafo,
    * apos as remocoes do algoritimo de Khan.
    * @param ArrayList<vertice> grafo - grafo a ser verificado.
    * @retunr int resp - numero de arestas restantes no grafo.
    */
   public static int edgeCount( ArrayList<vertice> grafo ) {

      int resp = 0;
      for( vertice i : grafo ) {
         resp += i.arcoEntrada;
      }
      return resp;
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

   int arcoEntrada;
   String className; //Vertice: Materia.
   ArrayList<String> requiredFor; //Aresta: Materias necessarias para fazer a materia atual.

   /*
    * Construtor de classe
    */
   public vertice( String className ) {

      this.arcoEntrada = 0;
      this.className = className;
      this.requiredFor = new ArrayList<String>();

   }

   /*
    * Adiciona uma aresta na vertice atual.
    * @param String requirement - aresta a ser adicionada.
    */
   public void addAresta( String requirement ) {
      requiredFor.add( requirement );
   }

}
