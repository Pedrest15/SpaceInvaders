package spaceinvaders2;

import javafx.scene.image.Image;

/**
 * Classe responsavel pelas Barreiras, elas protegem o player
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Barreira extends Elemento{
    
    /**
     * Construtor da classe Barreira
     * 
     * @param coord_colunas     posicao horizontal na tela
     * @param coord_linhas      posicao vertical na tela
     * @param largura           largura da representacao grafica
     * @param altura            altura da representacao grafica
     * @param vidas             quantidade de vidas
     * @param imagem            representacao grafica do elemento
     */
    Barreira(double coord_colunas, double coord_linhas, int largura, int altura, int vidas, Image imagem){
        //construtor vindo da classe mae 
        super(coord_colunas, coord_linhas, largura, altura, vidas, imagem);
    }
    
    /**
     * Reduz a vida da barreira e troca sua imagem quando necessario
     * @return booleano para indicar se a barreira ainda possui vidas ou nao
     */
    public boolean perder_vida(){
        vidas--;
        
        if(vidas == 0){
            return false;
        } else if(vidas < 7 && vidas > 3){
            this.setImagem(new Image("Imagens/barreira1.png", 70, 70, true, true));
        } else if(vidas <= 3){
            this.setImagem(new Image("Imagens/barreira2.png", 70, 70, true, true));
        }
        
        return true;
    }
}
