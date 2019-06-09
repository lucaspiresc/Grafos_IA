package Puzzle8;

public class ArvoreBusca {

    public InstanciaPuzzle estadoAtual;
    public ArvoreBusca pai;

    public double custo; // custo para chegar a este estado
    public double hCusto; // custo heuristica
    public double fCusto; // f(n) custo

    /*
     * Construtor da classe
     */
    public ArvoreBusca(InstanciaPuzzle s) {
        estadoAtual = s;
        pai = null;
        custo = 0;
        hCusto = 0;
        fCusto = 0;
    }

    /*
     * Construtor da classe com pai, e custos
     */
    public ArvoreBusca(ArvoreBusca pai, InstanciaPuzzle s, double c, double h) {
        this.pai = pai;
        this.estadoAtual = s;
        this.custo = c;
        this.hCusto = h;
        this.fCusto = custo + hCusto;
    }

}
