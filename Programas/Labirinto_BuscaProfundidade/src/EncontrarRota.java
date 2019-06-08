import Enums.StatusPonto;
import Labirinto.Labirinto;
import Labirinto.PontoLabirinto;

import java.util.LinkedList;

public class EncontrarRota {

    private int profundidadeMinima = 9999999;
    private Labirinto labirinto;
    private int inicioX;
    private int inicioY;
    private int fimX;
    private int fimY;
    private PontoLabirinto[][] bestMap;

    private LinkedList listaTmp = new LinkedList();
    private LinkedList melhorCaminho = new LinkedList();

    public EncontrarRota(Labirinto labirinto, int inicioX, int inicioY, int fimX, int fimY){

        this.labirinto = labirinto;
        this.inicioX = inicioX;
        this.inicioY = inicioY;
        this.fimX = fimX;
        this.fimY = fimY;

        labirinto.alterarStatusMapa(inicioX, inicioY, StatusPonto.VISITADO);
    }

    private void buscaProfundidade(int x,int y,int pronfundidade){
        int[][] next = {
                {0,1},//Right
                {1,0},//Down
                {0,-1},//Left
                {-1,0}//Up
        };

        int nextX,nextY;

        if (x == fimX && y == fimY){

            if (pronfundidade < profundidadeMinima){
                profundidadeMinima = pronfundidade;
                bestMap = labirinto.labirinto;

                melhorCaminho.clear();

                melhorCaminho.addAll(listaTmp);
            }
            return;
        }

        for (int k = 0; k <= 3; k++){
            nextX = x + next[k][0];
            nextY = y + next[k][1];

            if (nextX < 0 || nextX > labirinto.alturaLabirinto-1 || nextY < 0 || nextY > labirinto.larguraLabirinto-1){
                continue;
            }

            if (!labirinto.labirinto[nextX][nextY].estaOcupado()){

                listaTmp.add(new Ponto(nextX,nextY));
                labirinto.alterarStatusMapa(nextX,nextY,StatusPonto.VISITADO);

                buscaProfundidade(nextX,nextY,pronfundidade+1);

                listaTmp.removeLast();
                labirinto.alterarStatusMapa(nextX,nextY,StatusPonto.LIVRE);
            }

        }
        return;
    }

    public void buscarRota(){

        buscaProfundidade(inicioX,inicioY,0);
        mostrarRota();

    }

    private void mostrarRota(){

        System.out.println("Melhor rota: ");

        while (!melhorCaminho.isEmpty()){
            Ponto ponto = (Ponto) melhorCaminho.poll();
            System.out.print("("+ponto.x+","+ponto.y+") ");
            bestMap[ponto.x][ponto.y].alteraStatus(StatusPonto.VISITADO);
        }

        System.out.println();

        for (int i =0; i < labirinto.alturaLabirinto; i++){
            for (int j = 0; j< labirinto.larguraLabirinto; j++){
                if (bestMap[i][j].getStatus().equals(StatusPonto.VISITADO)){
                    System.out.print("*"+"\t");
                }else if (bestMap[i][j].getStatus().equals(StatusPonto.PAREDE)){
                    System.out.print("X"+"\t");
                }else {
                    System.out.print(" "+"\t");
                }

            }
            System.out.println();
        }
    }

    class Ponto{
        int x;
        int y;

        public Ponto(int x,int y){
            this.x = x;
            this.y = y;
        }
    }

}
