import java.util.*;
import java.io.*;

/*
 * OBS : QUANDO ACABAR NAO ESQUESA DE APAGAR ESSA PARTE 
 * 
 * NOTAS : Entao Daniel eu testei com a entrada dele e o meu grafoburger esta lendo certo 
 * entao oque vc tera que fazer seria adaptar o meu grafoburguer para ele fazer essa parte de 
 * Franqueado 1: e Franqueado 2: que esta na saida 
 * 
 * O meu grafoBurguer e bem baseado no primeiro algoritmo do slide "PARTICIONAMENTO  COBERTURAS EMPARELHAMENTO "
 * eu ate coloquei uns comentarios pra ajudar a saber oque e oque 
 * 
 * Eu tanbem estou te enviado a entrada e saida esperada 
 * 
 * Tanbem nao e pra mostrar a sua raiz mesmo , eu sou estou mostrando para garantir se ta montando certo , pode tirar a vontade  
 */


class Matriz {
    public int matriz[][] ;//Matriz normal que sera construida
    public int V[][];//Matriz segundaria(copia) que sera usada no grafoburguer
    public int cor[];
    public int n ; 
    public boolean digrafo = false ;
  
   public Matriz (int x) {
       setN(x);
       this.matriz = new int [n][n];
       this.V = new int[n][n];
   }
  
   public void setN(int n) {
     this.n = n;
   }
  
   public void setMatriz(int[][] matriz) {
     this.matriz = matriz;
   }

  
  /*
   * Metodo para construir a Matriz 
   */
    public void Construir(String x , String y , String z ){
     int bairro1 = new Integer(x).intValue();
     int vizinho = new Integer(y).intValue();
     int peso = new Integer(z).intValue();
        matriz[bairro1][vizinho] = peso ; 
        matriz[vizinho][bairro1] = peso ;
      
    }
   
   public void grafoBurger (){
      int I [] = new int[n];//Inicialize o conjunto independente I como vazio 
      this.V  = matriz;
      int x = 0 ;
      int i = 0 ;//Serve como um contador para o I
      while(empty(V) == false && x < n ){//Enquanto há vértices em V
        if(existe(x)){//Verifica se o vertice existe 
          I[i] = x ;//Insira v em I;    
          remover (x);//Retire de V o vértice v e todos os seus vizinhos
          i++; 
        }
        x++; 
      }
      for (int n = 0 ; n < i ; n++ ){
        if (n < i-1 ){
          System.out.print(I[n] + ", ");
        }
        else  {
          System.out.print(I[n]);
        }
      }
   }
   
   public void remover( int x ){//Metodo para remover um vertice da matriz
     for(int y = 0 ; y < n; y++){
       if (V[x][y] == 1 ){
        V[x][y] = 0;
        remover2(y);
       }
     }
   }

   public void remover2( int y ){//Metodo segundario de remover um vertice da matriz 
    for(int z = 0 ; z < n ; z++){
      if (V[y][z] == 1 ){
       V[y][z] = 0;
      }
    }
  }

   public boolean empty (int V [][]){//Verificar se a matriz esta vazia 
    boolean resp = true ; 
    for(int x = 0 ; x < n ; x++){
     for(int y = 0 ; y < n ; y++){
      if (V[x][y] != 0){
       resp = false ; 
      }
    }
   }
    return resp ;
  }

  public boolean existe (int x){//Verifica se certo possicao da matriz esta ocupada(conectada a alguma coisa)
    boolean resp = false ; 
     for(int y = 0 ; y < n ; y++){
      if (matriz[x][y] != 0){
       resp = true ; 
      }
    }
    return resp ;
  }

}

public class TP_06 {
 public static void main(String[] args) {
  try{
    //BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    BufferedReader read = new BufferedReader(new FileReader("grafo.in"));
    String linha = "" ;  
    int n = 0 ; //tamanho da matriz 
    read.readLine();//Para ler a primeira linha 
   
    n = Integer.valueOf(read.readLine()) ;
    Matriz matriz = new Matriz(n);//Numeros de elementos
    linha = read.readLine();
    String vertices [] ; 

    while (!linha.equals("FIM")){//Para ler as linhas com "," e pegar os valores  
      vertices  = linha.split(",");
      matriz.Construir(vertices[0], vertices[1] , vertices[2]);//Bairro 1 , vizinho , peso
      linha = read.readLine();
    }
    
    matriz.grafoBurger();
  }     
  catch (IOException e) {
     e.printStackTrace();
  } 
  
 }
}