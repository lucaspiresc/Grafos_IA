package Labirinto;

import Enums.StatusPonto;

/**
 * Classe que representa um ponto do labirinto
 */
public class PontoLabirinto {

    private boolean ocupado = false;
    private StatusPonto status;

    /**
     * Construtor da classe.
     */
    public PontoLabirinto(StatusPonto tipo){
        this.status = tipo;
        if (status.equals(StatusPonto.PAREDE) || status.equals(StatusPonto.VISITADO)){
            this.ocupado = true;
        }else {
            this.ocupado = false;
        }
    }

    public void alteraStatus(StatusPonto novoStatus){
        this.status = novoStatus;
        if (status.equals(StatusPonto.PAREDE) || status.equals(StatusPonto.VISITADO)){
            this.ocupado = true;
        }else {
            this.ocupado = false;
        }
    }

    public StatusPonto getStatus(){
        return status;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

}
