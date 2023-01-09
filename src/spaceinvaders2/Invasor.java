package spaceinvaders2;

import javafx.scene.image.Image;

/**
 * Classe responsavel pelo Invasores, os inimigos do player
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Invasor extends Elemento{
    //indica se o invasor se move p/ direita (true) ou p/ esquerda (false)
    private static boolean direita;
    
    //indica se os invasores devem avancar no eixo vertical
    private static boolean descer;
    
    //velocidade com que o invasor move
    private static double velocidade = 0.1;
    
    /**
     * Construtor da classe Invasor
     * 
     * @param coord_colunas     posicao horizontal na tela
     * @param coord_linhas      posicao vertical na tela
     * @param largura           largura da representacao grafica
     * @param altura            altura da representacao grafica
     * @param vidas             quantidade de vidas
     * @param imagem            representacao grafica do elemento
     */
    Invasor(double coord_colunas, double coord_linhas, int largura, int altura, int vidas,
            Image imagem){
        //construtor da classe mae
        super(coord_colunas, coord_linhas, largura, altura, vidas, imagem);
        
        this.direita = true;
        this.descer = false;
    }
    
    /**
     * Retorna a velocidade o invasor
     * @return velocidade do invasor 
     */
    public double getVelocidade(){
        return velocidade;
    }
    
    /**
     * Retorns se o invasor se move p/ direita ou esquerda
     * @return booleano
     */
    public static boolean getDireita(){
        return Invasor.direita;
    }
    
    /**
     * Muda o sentido de movimento do invasor
     */
    public static void inverte_movimento(){
        if(!Invasor.descer){
            Invasor.descer = true;
        } else {
            Invasor.descer = false;
            if(!Invasor.direita){
                Invasor.direita = true;
            } else {
                Invasor.direita = false;
            }
        }
        
    }
    
    /**
     * Realiza a movimentacao dos invasores
     * @param total_colunas tamanho horizontal da janela de jogo
     */
    public void movimentacao(int total_colunas){
        if(Invasor.descer){
            this.coord_linhas += this.altura/2;
        } else {
            if(Invasor.direita){
                if(this.coord_colunas >= 1 && (this.coord_colunas - this.velocidade < 0)){
                    this.coord_colunas += velocidade/2;
                } else {
                    this.coord_colunas += velocidade;
                }
            } else {
                if(this.coord_colunas < total_colunas && (this.coord_colunas + velocidade >= total_colunas)){
                    this.coord_colunas -= velocidade/2;
                } else {
                    this.coord_colunas -= velocidade;
                }
            }
        }
    }
    
    /**
     * Realiza a movimentacao do ultimo invasor, ele se movimenta 40%
     * mais rapido p/ direita e 305 mais lento p/ esquerda
     * @param total_colunas tamanho horizontal da janela de jogo
     */
    public void movimentacao_ultimo(int total_colunas){
        if(Invasor.descer){
            this.coord_linhas += this.altura/2;
        } else {
            if(Invasor.direita){
                if(this.coord_colunas >= 1 && (this.coord_colunas - velocidade < 0)){
                    this.coord_colunas += velocidade;
                } else {
                    this.coord_colunas += 2*(velocidade);
                }
            } else {
                if(this.coord_colunas < total_colunas && (this.coord_colunas + velocidade >= total_colunas)){
                    this.coord_colunas -= 0.7*(velocidade);
                } else {
                    this.coord_colunas -= 1.4*velocidade;
                }
            }
        }
    }
    
    /**
     * Acelera os invasores em 30% conforme o nivel de jogo
     * @param aceleracao fator de acelerar equivalente ao nivel de jogo
     */
    public static void acelerar(int aceleracao){
        Invasor.velocidade += 0.3*(velocidade)*(aceleracao-1);
    }

    /**
     * Regular a velocidade
     */
    public static void modo_rapido(){
        velocidade = 0.7;
    }
    
    /**
     * Regular a velocidade
     */
    public static void modo_lento(){
        velocidade = 0.1;
    }
    
}