package spaceinvaders2;

import javafx.scene.image.Image;

/**
 * Classe Abstrata responsavel pelos Elementos do jogo
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public abstract class Elemento {
    //posicoes no eixo das linhas e no eixo das colunas
    public double coord_linhas, coord_colunas;
    
    public int largura, altura;
    
    //quantidade de vidas do elemento
    protected int vidas;
    
    //representacao do elemento
    protected Image imagem;
    
    /**
     * Construtor da classe Elemento
     * 
     * @param coord_colunas     posicao horizontal na tela
     * @param coord_linhas      posicao vertical na tela
     * @param largura           largura da representacao grafica
     * @param altura            altura da representacao grafica
     * @param vidas             quantidade de vidas
     * @param imagem            representacao grafica do elemento
     */
    Elemento(double coord_colunas, double coord_linhas, int largura, int altura, int vidas, Image imagem){
        this.coord_linhas = coord_linhas;
        this.coord_colunas = coord_colunas;
        this.largura = largura;
        this.altura = altura;
        this.vidas = vidas;
        this.imagem = imagem;
    }
    
    /**
     * Retorna quantidade de vidas do elemento
     * @return int
     */
    public int getVidas(){
        return this.vidas;
    }
    
    /**
     * 
     * @return representacao grafica do elemento
     */
    public Image getImagem(){
        return this.imagem;
    }
    
    /**
     * Altera a imagem do elemento
     * @param imagem representacao grafica do elemento
     */
    public void setImagem(Image imagem){
        this.imagem = imagem;
    }
}