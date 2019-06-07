package Puzzle;

import Enums.IndicadorMovimentos;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {

    public String puzzle; //Instacia atual do puzzle.
    public IndicadorMovimentos origem; //De onde veio o espaco vazio dessa instancia.

    /**
     * Construtor da classe.
     */
    public Puzzle ( String puzzle, IndicadorMovimentos pai ) {

        this.puzzle = puzzle;
        this.origem = pai;

    }

    /*
     * Metodo que encontra a posicao do zero (posicao vazia) dentro do puzzle.
     * @return int resp - posicao do 0 na matriz.
     */
    public int encontrarVazio ( ) {

        int resp = -1;
        for( int i = 0; i < puzzle.length( ); i++) {
            if( puzzle.charAt(i) == '0' ) {
                resp = i;
                i = puzzle.length( );
            }
        }
        return resp;
    }

    /*
     * Metodo que descobre quais movimentos o puzzle e capaz de fazer na posicao atual.
     * @return List<> moves - Lista contendo os movimentos validos para aquela posicao.
     */
    public List<IndicadorMovimentos> obterMovimentos ( ) {

        List<IndicadorMovimentos> moves = new ArrayList<>();

        int pos = encontrarVazio( );

        if( pos > 2 && !origem.equals(IndicadorMovimentos.BAIXO) ) {
            moves.add(IndicadorMovimentos.CIMA);
        }
        if( pos < 6 && !origem.equals(IndicadorMovimentos.CIMA) ) {
            moves.add(IndicadorMovimentos.BAIXO);
        }
        if( ( pos % 3 != 0 ) && !origem.equals(IndicadorMovimentos.DIREITA) ) {
            moves.add(IndicadorMovimentos.ESQUERDA);
        }
        if( ( pos % 3 != 2 ) && !origem.equals(IndicadorMovimentos.ESQUERDA) ) {
            moves.add(IndicadorMovimentos.DIREITA);
        }
        return moves;
    }

    /*
     * Metodo que coordena todas as trocas a serem feitas nesse instante do puzzle.
     * @return List<puzzle8> possibleMoves - Lista contendo puzles com todas as trocas possiveis do instante "original".
     */
    public List<Puzzle> fazerTrocas ( ) {

        List<IndicadorMovimentos> moves = obterMovimentos( );
        List<Puzzle> possibleMoves = new ArrayList<>();
        int zero = encontrarVazio( );

        for( IndicadorMovimentos movimento : moves ) {

            String newPuzzle = trocarPecas( zero, puzzle, movimento );
            Puzzle move = new Puzzle( newPuzzle , movimento );
            possibleMoves.add ( move );
        }

        return possibleMoves;
    }

    /*
     * Metodo que faz a troca das pecas dentro do puzzle.
     *
     * @param int pos - posicao do zero,ou peca vazia, dentro do puzzle.
     * @param String puzzle - puzzle "original" antes de ser alterado.
     * @param IndicadorMovimentos movimento - comando que vai determinar a direcao do movimento do puzzle.
     *
     * @return String resp - puzzle com as pecas trocadas.
     */
    public String trocarPecas ( int pos, String puzzle, IndicadorMovimentos movimento ) {

        char[] resp = puzzle.toCharArray();
        char tmp = resp[pos];

        if ( movimento.equals(IndicadorMovimentos.CIMA) ){

            resp[pos] = resp[pos-3];
            resp[pos-3] = tmp;

        } else if ( movimento.equals(IndicadorMovimentos.BAIXO) ) {

            resp[pos] = resp[pos+3];
            resp[pos+3] = tmp;

        } else if ( movimento.equals(IndicadorMovimentos.ESQUERDA) ) {

            resp[pos] = resp[pos-1];
            resp[pos-1] = tmp;

        } else if ( movimento.equals(IndicadorMovimentos.DIREITA) ) {

            resp[pos] = resp[pos+1];
            resp[pos+1] = tmp;

        }
        return new String( resp );
    }

    /*
     * Metodo que verifica se o puzzle foi solucionado.
     * @return estado atual do puzzle(solucionado ou nao).
     */
    public boolean completo ( ) {

        if( puzzle.equals("123456780") ) {
            return true;
        }
        return false;

    }

    /*
     * Metodo que verifica se e possivel resolver o puzzle.
     * @return boolean - possibilidade(ou nao) de resolucao do puzzle.
     */
    public boolean resolvivel( ) {
        int num = 0;

        for( int i = 0; i < puzzle.length( ); i++ ) {
            num += verificarMenor( i );
        }

        if( num % 2 == 0 ) {
            return true;
        }
        return false;

    }

    /*
     * Metodo que conta quantos elementos que deveriam estar atras do atual,
     * estao na verdade na sua frente.
     * @return int resp - numero de elementos.
     */
    public int verificarMenor ( int i ) {
        int resp = 0;
        for( int a = i; a < puzzle.length( ); a++ ) {

            if( ( puzzle.charAt( a ) != '0' ) && ( puzzle.charAt( a ) < puzzle.charAt( i ) ) ){
                resp++;
            }
        }
        return resp;
    }

}