/*
 * Lucas Pires Cicutti
 * Matricula: 561908
 * 2017/2
 */

import java.util.*;
import java.io.*;

public class requirementsSolver3 {

   public static ArrayList<String> ordemTop = new ArrayList<String>();

   public static void main( String[]args ) {
      
      ArrayList<vertice> grafo = buildGraph( );
      ArrayList<vertice> subgrafo = subGrafo( grafo );
      setSemestres( subgrafo );
      
      Khan( subgrafo );
      
      System.out.println( "ORDEM TOPOLOGICA:" );
      for( String i : ordemTop ) {
         System.out.print( " -> " + i );
      }
      int maxSem = 0;
      for( vertice i : subgrafo ) {
         if( i.semestreMin > maxSem ) {
            maxSem = i.semestreMin;
         }
      }
      System.out.println( "\n" + maxSem + " semestres" );
        
   }
   
    /*
    * Metodo que le de um arquivo as materias e os requisitos das mesmas,
    * e modela os dados em uma lista da ajacencias.
    * @return ArrayList<vertice> grafo - Lista de vertices que sera o grafo representado.
    */
   public static ArrayList<vertice> buildGraph ( ) {
      
      ArrayList<String> materias = new ArrayList<String>();
      ArrayList<vertice> grafo = new ArrayList<vertice>();
      
      try {
         //BufferedReader in = new BufferedReader(new InputStreamReader( System.in ));
         BufferedReader in = new BufferedReader( new FileReader( "materias.in" ) );
         
         int size = Integer.parseInt( in.readLine( ) );
         String tmp = in.readLine( );
         
         while ( size > 0 ) {
            
            materias.add(tmp);
            tmp = in.readLine( );
            size--;
            
         }
         in.close( );
         
      } catch ( Exception e ) {
      }
      
      for( String i : materias ) {
         //VERTICES
         String[] firstSplit = i.split(";");
         grafo.add( new vertice(firstSplit[0].trim() ) );       
      }
      for( String i : materias ) {
       
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
    * Metodo que pega as materias faltantes do arquivo, e gera o subgrafo
    * correspondente.
    * @param ArrayList<vertice> grafo - grafo contendo o subgrafo desejado.
    * @return ArrayList<vertice> subgrafo - Lista de vertices que sera o subgrafo representado.
    */
   public static ArrayList<vertice> subGrafo ( ArrayList<vertice> grafo ) {
   
      ArrayList<vertice> subgrafo = new ArrayList<vertice>();
      ArrayList<String> faltantes = new ArrayList<String>();
      
      try {
         //BufferedReader in = new BufferedReader(new InputStreamReader( System.in ));
         BufferedReader in = new BufferedReader( new FileReader( "materias.in" ) );
         
         int size = Integer.parseInt( in.readLine( ) );
         String tmp = in.readLine( );
         
         while ( size > 0 ) {
            
            tmp = in.readLine( );
            size--;
            
         }
         
         size = Integer.parseInt( tmp );
         while ( size > 0 ) {
            faltantes.add( in.readLine() );
            size--;
         }
         in.close( );
         
      } catch ( Exception e ) {
      }
      
      for( String i : faltantes ) {
         vertice tmp = new vertice( grafo.get( getIndex(i,grafo) ).className );
         subgrafo.add( tmp );
      }
      
      for( vertice i : subgrafo ) {
      
         vertice tmp = grafo.get( getIndex(i.className,grafo) );
         for( String j : tmp.requiredFor ) {
         
            if( verificaMateria(subgrafo,j) ) {
               i.addAresta(j);
               subgrafo.get( getIndex(j,subgrafo) ).arcoEntrada++;
            }
         }
      }
      
      return subgrafo;
   
   }
   
   /*
    * Metodo que percorre um grafo a procura de uma materia especifica.
    * @param ArrayList<vertice> grafo - grafo onde sera feita a pesquisa.
    * @param String materia - materia que desejamos encontrar.
    * @return boolean - materia encontrada ou nao.
    */
   public static boolean verificaMateria( ArrayList<vertice> grafo, String materia ) {
      for( vertice i : grafo ) {
         if( i.className.equals(materia) ) {
            return true;
         }
      }
      return false;
   }
   
   /*
    * Inicializacao do metodo recursivo que define quantos semestres sao necessarios
    * para fazer uma materia especifica.
    */
   public static void setSemestres ( ArrayList<vertice> grafo ) {
      for( vertice i: grafo ) {
         if( i.arcoEntrada == 0 ) {
            i.semestreMin = 1;
            entraSemestre( grafo, i ); 
         }
      }
   }
   
   /*
    * Metodo que vai definir quantos semestres se precisa p/ fazer cada materia
    * com base em quantos semestres se precisam para fazer os requisitos da mesma.
    * @param ArrayList<vertice> grafo - grafo a ser trabalhado.
    * @param vertice i- Vertice atual a ser trabalhado.
    */
   public static void entraSemestre ( ArrayList<vertice> grafo, vertice i ) {
   
      for( String j : i.requiredFor ) {
         vertice tmp = grafo.get( getIndex( j,grafo) );
         if( tmp.semestreMin == 0 ) {
            tmp.semestreMin = i.semestreMin+1;
         } else {
            /* Caso a materia tenha mais de um requisito, vai prevalecer o 
             * maior tempo para poder cursar tal materia
             */
            if( i.semestreMin+1 > tmp.semestreMin ) {
               tmp.semestreMin = i.semestreMin+1;
            }
         }
         entraSemestre( grafo, tmp );
      } 
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
   int semestreMin;
   ArrayList<String> requiredFor; //Aresta: Materias necessarias para fazer a materia atual.

   /*
    * Construtor de classe
    */
   public vertice( String className ) {
      
      this.className = className;
      this.arcoEntrada = 0;
      this.semestreMin = 0;
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