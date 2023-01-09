package spaceinvaders2;

import java.util.Comparator;

/**
 * Classe responsavel por comparar posicoes dos Invasores
 * @author Pedro Lucas Castro de Andrade - 11212289
 */

public class Comparar_Invasores implements Comparator<Invasor>{
    
    //variavel que guarda a posicao horizontal do canhao
    private double coord_colunas_canhao;
    
    /**
     * Construtor da Classe Comparar_invasores
     * @param coord_colunas_canhao guarda a posicao horizontal do canhao
     */
    Comparar_Invasores(double coord_colunas_canhao){
        this.coord_colunas_canhao = coord_colunas_canhao;
    }
    
    /**
     * Verifica qual entre dois invasores qual esta horizontalmente
     * mais perto do canhao
     * @param invasor1      primeiro invasor p/ comparacao
     * @param invasor2      segundo invasor p/ comparacao
     * @return 
     */
    @Override
    public int compare(Invasor invasor1, Invasor invasor2){
        double vetor1 = Math.abs(invasor1.coord_colunas - coord_colunas_canhao);
        double vetor2 = Math.abs(invasor2.coord_colunas - coord_colunas_canhao);
    
        return Double.compare(vetor1, vetor2);
    }
}
