package spaceinvaders2;

import javafx.scene.image.Image;

/**
 * Classe responsavel pelo Canhao, o personagem do player
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Canhao extends Elemento{
    
    private static double velocidade = 1;
    
    /**
     * Construtor da classe Canhao
     * 
     * @param coord_colunas     posicao horizontal na tela
     * @param coord_linhas      posicao vertical na tela
     * @param largura           largura da representacao grafica
     * @param altura            altura da representacao grafica
     * @param vidas             quantidade de vidas
     * @param imagem            representacao grafica do elemento
     */
    Canhao(double coord_colunas, double coord_linhas, int largura, int altura, int vidas, Image imagem){
        //construtor vindo da classe mae
        super(coord_colunas, coord_linhas, largura, altura, vidas, imagem);
        
    }
    
    /**
     * Retorna a velocidade do canhao
     * @return double
     */
    public double getVelocidade(){
        return this.velocidade;
    }
    
    /**
     * Reduz a vida do canhao
     */
    public void perder_vida(){
        this.vidas--;
    }
    
    /**
     * Regular a velocidade
     */
    public static void modo_rapido(){
        velocidade = 6;
    }
    
    /**
     * Regular a velocidade
     */
    public static void modo_lento(){
        velocidade = 1;
    }
}
