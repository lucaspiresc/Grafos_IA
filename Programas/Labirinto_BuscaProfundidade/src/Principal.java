import Labirinto.Labirinto;

public class Principal {

    public static void main(String[] args) {
        Labirinto labirinto = new Labirinto();

        labirinto.gerarLabirinto("C:\\Users\\Lucas\\Documents\\Grafos\\Programas\\Labirinto_BuscaProfundidade\\src\\lab.txt");

        labirinto.mostrarLabiritno();

        System.out.println();

        EncontrarRota busca = new EncontrarRota(labirinto,0,0,4,3);

        busca.buscarRota();
    }

}
