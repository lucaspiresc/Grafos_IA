package Labirinto;

import Enums.StatusPonto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Classe representando o labirinto
 */
public class Labirinto {

    public PontoLabirinto[][] labirinto;
    public int alturaLabirinto;
    public int larguraLabirinto;

    /**
     * Metodo que le o arquivo de entrada e gera o labirinto
     */
    public void gerarLabirinto(String caminhoArq){

        int altura = 0;
        int largura = 0;

        StatusPonto[][] tmpLab = new StatusPonto[100][100];

        try {
            String encoding="GBK";

            File file = new File(caminhoArq);

            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);

                String linha = null;

                while((linha = bufferedReader.readLine()) != null){
                    largura = linha.length();

                    for (int i=0; i<largura; i++){
                        tmpLab[altura][i] = ParseStatus(Integer.parseInt(String.valueOf(linha.charAt(i))));
                    }
                    altura++;
                }
                read.close();
            }else{
                System.out.println("Nao foi localizado o arquivo");
            }
        } catch (Exception e) {
            System.out.println("Erro de leitura");
            e.printStackTrace();
        }

        this.alturaLabirinto = altura;
        this.larguraLabirinto = largura;


        this.labirinto = new PontoLabirinto[altura][largura];
        for (int i = 0; i < altura; i++){
            for (int j = 0; j < largura; j++){
                this.labirinto[i][j] = new PontoLabirinto(tmpLab[i][j]);
            }
        }
    }

    /**
     * Metodo que converte o valor inteiro do arquivo em um ponto do labirinto
     * 0 - Ponto livre
     * 1-  Ponto visitado
     * 2-  Parede
     */
    public StatusPonto ParseStatus(int valor){
        switch (valor){
            case 0:
                return StatusPonto.LIVRE;
            case 1:
                return StatusPonto.VISITADO;
            default:
                return StatusPonto.PAREDE;
        }
    }

    public void alterarStatusMapa(int i, int j, StatusPonto novoStatus){
        labirinto[i][j].alteraStatus(novoStatus);
    }

    public void mostrarLabiritno(){
        for (int i =0; i < alturaLabirinto; i++){
            for (int j = 0; j< larguraLabirinto; j++){
                if (labirinto[i][j].getStatus().equals(StatusPonto.PAREDE)){
                    System.out.print("X"+"\t");
                }else {
                    System.out.print("-"+"\t");
                }

            }
            System.out.println();
        }
    }

}
