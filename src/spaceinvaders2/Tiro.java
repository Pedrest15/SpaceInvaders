package spaceinvaders2;

import javafx.scene.image.Image;

/**
 * Classe responsavel pelos Tiros tanto do player quanto dos invasores
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Tiro extends Elemento{
    
    //saber se o tiro foi dado pelo canhao ou invasor
    public boolean tiro_canhao;
    
    //velocidade do tiro do invasor
    protected static double velocidade_invasor = 0.4;
    
    //velocidade do tiro do canhao
    protected static double velocidade_canhao = 1;
    
    /**
     * Construtor da classe Tiro
     * 
     * @param coord_colunas     posicao horizontal na tela
     * @param coord_linhas      posicao vertical na tela
     * @param largura           largura da representacao grafica
     * @param altura            altura da representacao grafica
     * @param vidas             quantidade de vidas
     * @param tiro_canhao       indica se o tiro pertence ao canhao ou a um invasor
     * @param imagem            representacao grafica do elemento
     */
    Tiro(double coord_colunas, double coord_linhas, int largura, int altura, int vidas,
         boolean tiro_canhao, Image imagem){
        //construtor da classe mae
        super(coord_colunas, coord_linhas, largura, altura, vidas, imagem);
        
        this.tiro_canhao = tiro_canhao;
    }
    
    /**
     * Realiza a movimentacao do tiro
     * @param total_linhas  tamanho vertical da tela de jogo
     * @return booleano indicando se a movimentacao eh valida
     */
    public boolean movimentacao(int total_linhas){
        if(this.tiro_canhao){
            if(this.coord_linhas > 0){
                this.coord_linhas -= this.velocidade_canhao;
            } else {
                return false;
            }
        } else { //tiro de invasor
            if(this.coord_linhas < total_linhas){
                this.coord_linhas += this.velocidade_invasor;
            } else {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Acelera o tiro do invasor em 20% conforme o nivel de jogo
     * @param aceleracao fator de acelerar equivalente ao nivel de jogo
     */
    public static void acelerar_tiro_invasor(int aceleracao){
        velocidade_invasor += 0.2*(velocidade_invasor)*(aceleracao-1);
    }
    
    /**
     * Regular a velocidade
     */
    public static void modo_rapido(){
        velocidade_invasor = 2;
        velocidade_canhao = 6;
    }
    
    /**
     * Regular a velocidade
     */
    public static void modo_lento(){
        velocidade_invasor = 0.2;
        velocidade_canhao = 1;
    }
}