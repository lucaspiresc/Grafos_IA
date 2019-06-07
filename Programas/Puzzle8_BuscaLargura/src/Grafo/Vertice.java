package Grafo;

import Enums.IndicadorCores;
import Puzzle.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Vertice {

    public IndicadorCores cor;
    public Vertice pai;
    public int distancia;

    //Instancia do puzzle8 deste vertice:
    public Puzzle puzzleAtual;
    //Lista contendo as instancias do puzzle que este vertice gera, ou seja, suas arestas:
    public List<Puzzle> arestas;

    /**
     * Construtor da classe.
     */
    public Vertice( Puzzle puzzle ) {

        this.puzzleAtual = puzzle;
        this.arestas = new ArrayList<>();

        this.distancia = -1;
        this.cor = IndicadorCores.BRANCO;
        this.pai = null;
    }

    /**
     * Metodo que adiciona uma aresta a este vertice.
     * @param Puzzle aresta - aresta a ser adicionada.
     */
    public void addAresta( Puzzle aresta ) {
        arestas.add( aresta );
    }

}
