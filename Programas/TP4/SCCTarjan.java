
import java.util.*;

public class SCCTarjan {

   List<vertice> grafo;
   boolean[] visitado;
   Stack<Integer> pilha;
   int tempo;
   int[] lowlink;
   List<List<Integer>> componentes;
   static boolean[] ponto;
   static boolean[][] ponte;
   int count;
   int d[];
   int m[];


   public List<List<Integer>> scc(List<vertice> grafo) {
      int n = grafo.size();
      this.grafo = grafo;
      visitado = new boolean[n];
      pilha = new Stack<>();
      tempo = 0;
      lowlink = new int[n];
      componentes = new ArrayList<>();
      ponto = new boolean[n];
      ponte = new boolean[n][n];
      d = new int[n];
      m = new int[n];
   
      for (int u = 0; u < n; u++) {
         if (!visitado[u]) {
            count = 0;
            dfs( grafo.get(u), grafo.get(u) );
         }
         if ( count >= 2 ) {
            ponto[u] = true;
         } 
         else {
            ponto[u] = false;
         }
      }
      
      return componentes;
   }

   void dfs( vertice u, vertice k ) {
      lowlink[u.value] = tempo++;
      d[u.value] = tempo;
      m[u.value] = tempo;
      visitado[u.value] = true;
      pilha.add(u.value);
      boolean isComponentRoot = true;
      
      //para cada vertice adjacente faca:
      for ( int v : u.arestas ) {
         if (!visitado[v]) {
            u.sucessor = v;
            if( u.value == v ) {
               count++;
            }
            dfs( grafo.get(v), u );
            if(m[v] >= d[u.value] ) {
               ponto[u.value] = true;
            }
            if(m[v] > d[u.value] ) {
               ponte[u.value][v] = true;
            }
            m[u.value] = Math.min( m[u.value], d[v] );
         } 
         else {
            m[u.value] = Math.min( m[u.value], d[v] );
         }
         if (lowlink[u.value] > lowlink[v]) {
            lowlink[u.value] = lowlink[v];
            isComponentRoot = false;
         }
      }
   
      if (isComponentRoot) {
         List<Integer> component = new ArrayList<>();
         while (true) {
            int x = pilha.pop();
            component.add(x);
            lowlink[x] = Integer.MAX_VALUE;
            if (x == u.value)
               break;
         }
         componentes.add(component);
      }
   }

   // Usage example
   public static void main(String[] args) {
      List<vertice> g = new ArrayList<vertice>();
      for (int i = 0; i < 5; i++){
         vertice tmp = new vertice( i );
         g.add( tmp );
      }
         
      g.get(0).addAresta(3);
      g.get(0).addAresta(2);
      g.get(1).addAresta(0);
      g.get(2).addAresta(1);
      g.get(3).addAresta(4);
   
      List<List<Integer>> componentes = new SCCTarjan().scc(g);
      System.out.println(componentes);
      
   }
}

class vertice {
   
   public int value;
   List<Integer> arestas;
   int sucessor;
   
   public vertice( int x ) {
      this.value = x;
      this.arestas = new ArrayList<Integer>();
      this.sucessor = -1;
   }
   
   public void addAresta( int y ) {
      arestas.add( y );
   }
}