package spaceinvaders2;

import java.util.ArrayList;
import java.util.Collections;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 * Classe responsavel pela Engine que comanda a mecanica do jogo
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Engine {
    
    public boolean comecou;
    public boolean instrucoes;
    public boolean opcoes;
    public boolean venceu;
    public boolean jogando;
    public boolean atingido;
    protected int pontos; 
    protected int nivel = 1;

    ArrayList<Elemento> elementos = new ArrayList<>();
    private ArrayList<Barreira> barreiras = new ArrayList<>();
    private ArrayList<Invasor> invasores = new ArrayList<>();
    private ArrayList<Tiro> tiro_canhao = new ArrayList<>();
    private ArrayList<Tiro> tiro_invasor = new ArrayList<>();
    
    ArrayList<Invasor> remover_invasores = new ArrayList<>();
    ArrayList<Tiro> remover_tiro_canhao = new ArrayList<>();
    ArrayList<Tiro> remover_tiro_invasor = new ArrayList<>();
    ArrayList<Barreira> remover_barreira = new ArrayList<>();
        
    private Canhao canhao;
    
    private ArrayList<KeyCode> inputs = new ArrayList<>();
    
    private long tempo_tiro_canhao;
    private long tempo_tiro_invasor;
    
    Image img_invasor = new Image("Imagens/tiefighter.png", 35, 35, true, true);
    Image img_canhao = new Image("Imagens/xwing.png", 80, 80, true, true);    
    Image img_barreira = new Image("Imagens/barreira.png", 80, 70, true, true);
    Image img_tiro_canhao = new Image("Imagens/tiro_vermelho.png", 40, 40, true, true);
    Image img_tiro_invasor = new Image("Imagens/tiro_verde.png", 30, 30, true, true);
    
    public Engine(){
        comecou = false;
        instrucoes = false;
        opcoes = false;
        venceu = false;
        jogando = true;
        atingido = false;
        pontos = 0;
        
        tempo_tiro_canhao = System.currentTimeMillis();
        tempo_tiro_invasor = System.currentTimeMillis();
    }
    
    /**
     * Player precisa pressionar ENTER p/ iniciar o jogo
     * @return booleano se a tecla foi pressionada ou nao
     */
    public boolean comecar(){
        if(inputs.contains(KeyCode.ENTER)){
            comecou = true;
        }
        return comecou;
    }
    
    /**
     * Player precisa pressionar H p/ abrir tela de instrucoes
     * @return booleano se a tecla foi pressionada ou nao
     */
    public boolean abrir_instrucoes(){
        if(inputs.contains(KeyCode.H)){
            instrucoes = true;
        }
        return instrucoes;
    }
    
    /**
     * Fecha a tela de instrucoes
     */
    public void fechar_instrucoes(){
        instrucoes = false;
    }
    
    /**
     * Player precisa pressionar O p/ abrir tela de opcoes
     * @return booleano se a tecla foi pressionada ou nao
     */
    public boolean abrir_opcoes(){
        if(inputs.contains(KeyCode.O)){
            opcoes = true;
        }
        return opcoes;
    }
    
    /**
     * Player precisa pressionar R p/ reduzir ou I p/ aumentar a velocidade do jogo
     */
    public void setOpcoes(){
        if(inputs.contains(KeyCode.R)){
            canhao.modo_lento();
            Invasor.modo_lento();
            Tiro.modo_lento();
        } else if(inputs.contains(KeyCode.I)){
            canhao.modo_rapido();
            Invasor.modo_rapido();
            Tiro.modo_rapido();
        }
    }
    
    /**
     * Fechar tela de opcoes
     */
    public void fechar_opcoes(){
        opcoes = false;
    }
    
    /**
     * Player precisa pressionar ENTER p/ reiniciar o jogo
     * @return 
     */
    public boolean jogar_novamente(){
        if(inputs.contains(KeyCode.ENTER)){
            venceu = false;
            jogando = true;
            
            //remove os elementos usados na ultima partida
            elementos.clear();
            barreiras.clear();
            invasores.clear();
            tiro_canhao.clear();
            tiro_invasor.clear();
    
            remover_invasores.clear();
            remover_tiro_canhao.clear();
            remover_tiro_invasor.clear();
            remover_barreira.clear();
            
            return true;
        }
        return false;
    }
    
    /**
     * Passa p/ 1 nivel acima
     */
    public void subir_nivel(){
        nivel++;
    }
    
    /**
     * Retorna o jogo p/ o nivel 1
     */
    public void nivel1(){
        pontos = 0;
        nivel = 1;
    }
    
    /**
     * Retorna o nivel atual
     * @return int
     */
    public int getNivel(){
        return nivel;
    }
    
    /**
     * Cria os elementos de jogo em suas posicoes iniciais
     * @param total_colunas     tamanho horizontal da janela de jogo
     * @param total_linhas      tamanho vertical da janela de jogo
     * @return 
     */
    public ArrayList<Elemento> criar_elementos(int total_colunas, int total_linhas){
        for(double i = 10; i < 380; i += 35){
            for (double j = 30; j < 180; j += 35){
                invasores.add(new Invasor(i, j, 35, 35, 1, img_invasor));
            }
        }
        //acelera os invasores e seus tiros conforme o nivel de jogo
        Invasor.acelerar(nivel);
        Tiro.acelerar_tiro_invasor(nivel);
        
        Collections.shuffle(invasores);
        
        barreiras.add(new Barreira( 50, 450, 80,70, 10, img_barreira));
        barreiras.add(new Barreira( 210, 450, 80, 70, 10, img_barreira));
        barreiras.add(new Barreira( 380, 450, 80, 70, 10, img_barreira));
        barreiras.add(new Barreira( 550, 450, 80, 70, 10, img_barreira));
        
        canhao = new Canhao(45, 500, 80, 80,3, img_canhao);
        
        elementos.addAll(invasores);
        elementos.addAll(barreiras);
        elementos.addAll(tiro_canhao);
        elementos.addAll(tiro_invasor);
        elementos.add(canhao);
        
        return elementos;
    }
    
    /**
     * Processa todos os movimentos e colisoes do jogo
     * @param total_colunas     tamanho horizontal da janela de jogo
     * @param total_linhas      tamanho vertical da janela de jogo
     * @param musicas           musicas usadas
     * @return ArrayList de elementos
     */
    public ArrayList<Elemento> roda_jogo(int total_colunas, int total_linhas, Audio musicas){
        
        //se nao ha invasores restantes, o player venceu
        if(invasores.isEmpty()){
            venceu = true;
            jogando = false;
            
            //encerra a musica tocada naquele nivel
            if(nivel % 2 == 0){
                musicas.parar_tema_jogo2();
            } else {
                musicas.parar_tema_jogo1();
            }
            musicas.tema_vitoria();
        }
        
        //se o canhao nao tem vida, o player perdeu
        if(canhao.getVidas() <= 0){
            venceu = false;
            jogando = false;
            
            //encerra a musica tocada naquele nivel
            if(nivel % 2 == 0){
                musicas.parar_tema_jogo2();
            } else {
                musicas.parar_tema_jogo1();
            }
            musicas.tema_derrota();
        }
        
        elementos.clear();
        
        //chama todos os metodos de movimentos e colisoes
        mov_invasores(total_colunas);
        mov_tiro_canhao(total_linhas, remover_invasores, remover_tiro_canhao);
        mov_tiro_invasor(total_linhas, remover_tiro_invasor);
        col_tiro_canhao_barreira(remover_tiro_canhao);
        col_tiro_invasor_barreira(remover_tiro_invasor, remover_barreira);
        col_invasor_barreira(remover_barreira);
        processa_mov_canhao(total_colunas);
        criar_tiro_invasores();
        
        //junta os elementos do jogo em um unico ArrayList
        elementos.addAll(invasores);
        elementos.addAll(barreiras);
        elementos.addAll(tiro_canhao);
        elementos.addAll(tiro_invasor);
        elementos.add(canhao);
    
        return elementos;
    }
    
    /**
     * Faz a movimentacao dos invasores
     * @param total_colunas tamanho horizontal da tela de jogo
     */
    public void mov_invasores(int total_colunas){
        Invasor aux_invasor;
        boolean flag = false;
        
        int tam = invasores.size();
        for(int i = 0; i < tam; i++){
            
            //se ha um invasor restante, ele se move mais rapido
            if(tam == 1){
                invasores.get(i).movimentacao_ultimo(total_colunas);
            
            //movimentacao padrao
            } else {
                invasores.get(i).movimentacao(total_colunas);
            }
            
            //se os invasores chegarem na posicao vertical do canhao, o player perde
            aux_invasor = invasores.get(i);
            if(aux_invasor.coord_linhas == 500){
                venceu = false;
                jogando = false;
                return;
            }
            
            //checa se os invasores estao na borda da tela
            if((Invasor.getDireita() && (aux_invasor.coord_colunas + aux_invasor.largura >= total_colunas))
            || (Invasor.getDireita() == false && aux_invasor.coord_colunas <= 0)
            || (aux_invasor.coord_colunas + aux_invasor.largura + aux_invasor.getVelocidade() > total_colunas - 1 
            && Invasor.getDireita())
            || (Invasor.getDireita() == false && (aux_invasor.coord_colunas - aux_invasor.getVelocidade() < 0))){
                flag = true;
            }
        }
        
        //checa se os invasores devem se mover na vertical
        if(flag){
            Invasor.inverte_movimento();
            //move cada um dos invasores
            for(int i = 0; i < invasores.size(); i++){
                invasores.get(i).movimentacao(total_colunas);
            }
        }
    }
    
    /**
     * Faz a movimentacao do tiro de canhao
     * @param total_linhas          tamanho vertical da janela de jogo
     * @param remover_invasores     array que guarda o invasor a ser removido, se houver
     * @param remover_tiro_canhao   array que guarda o tiro de canhao a ser removido, se houver
     */
    public void mov_tiro_canhao(int total_linhas, ArrayList<Invasor> remover_invasores, ArrayList<Tiro> remover_tiro_canhao){
        Invasor aux_invasor;
        Tiro aux_tiro_canhao;
        
        //movimenta cada tiro de canhao
        for(int i = 0; i < tiro_canhao.size(); i++){
            aux_tiro_canhao = tiro_canhao.get(i);
            
            //checa se houve movimento valido
            if(aux_tiro_canhao.movimentacao(total_linhas)){
                for(int j = 0; j < invasores.size(); j++){
                    aux_invasor = invasores.get(j);
                    
                    //checa colisao entre tiro de canhao e invasor
                    if(checar_colisao(tiro_canhao.get(i), invasores.get(j))){
                        remover_tiro_canhao.add(aux_tiro_canhao);
                        remover_invasores.add(aux_invasor);
                    
                        pontuar();
                    }
                }
            } else {
                remover_tiro_canhao.add(aux_tiro_canhao);
            }
        }
        
        //checa se ha invasores a serem removidos e, se sim, os remove
        if(!remover_invasores.isEmpty()){
            invasores.removeAll(remover_invasores);
            remover_invasores.clear();
        }
        
        //checa se ha tiros de canhao a serem removidos e, se sim, os remove
        if(!remover_tiro_canhao.isEmpty()){
            tiro_canhao.removeAll(remover_tiro_canhao);
            remover_tiro_canhao.clear();
        }
    }
    
    /**
     * Faz a movimentacao do tiro de invasor
     * @param total_linhas           tamanho vertical da janela de jogo
     * @param remover_tiro_invasor   array que guarda o tiro de invasor a ser removido, se houver
     */
    public void mov_tiro_invasor(int total_linhas, ArrayList<Tiro> remover_tiro_invasor){
        Tiro aux_tiro_invasor;
        
        //movimenta cada tiro de invasor
        for(int i = 0; i < tiro_invasor.size(); i++){
            aux_tiro_invasor = tiro_invasor.get(i);
            
            //verifica se foi um movimento valido
            if(aux_tiro_invasor.movimentacao(total_linhas)){
                
                //checa colisao entre tiro de invasor e canhao
                if(checar_colisao(canhao, tiro_invasor.get(i))){
                    canhao.perder_vida();
                    remover_tiro_invasor.add(aux_tiro_invasor);
                    atingido = true;
                }
            } else {
                remover_tiro_invasor.add(aux_tiro_invasor);
            }
        }
        
        //checa se ha tiros de invasor a serem removidos e, se sim, os remove
        if(!remover_tiro_invasor.isEmpty()){
            tiro_invasor.removeAll(remover_tiro_invasor);
            remover_tiro_invasor.clear();
        }
    }
    
    /**
     * Verifica colisao entre tiro de canhao e barreira,
     * tiro de canhao nao reduz a vida das barreiras
     * @param remover_tiro_canhao  array que guarda o tiro de canhao a ser removido, se houver
     */
    public void col_tiro_canhao_barreira(ArrayList<Tiro> remover_tiro_canhao){
        Tiro aux_tiro_canhao;
        Barreira aux_barreira;
        
        //verifica cada tiro de canhao com cada barreira
        for(int i = 0; i < tiro_canhao.size(); i++){
            aux_tiro_canhao = tiro_canhao.get(i);
            for(int j = 0; j < barreiras.size(); j++){
                aux_barreira =  barreiras.get(j);
                if(checar_colisao(aux_tiro_canhao, aux_barreira)){
                    remover_tiro_canhao.add(aux_tiro_canhao);
                }
            }
        }
        
        //checa se ha tiros de canhao a serem removidos e, se sim, os remove
        if(!remover_tiro_canhao.isEmpty()){
            tiro_canhao.removeAll(remover_tiro_canhao);
            remover_tiro_canhao.clear();
        }
        
    }
    
    /**
     * Verifica colisao entre tiro de invasor e barreira
     * @param remover_tiro_invasor  array que guarda o tiro de invasor a ser removido, se houver
     * @param remover_barreira      array que guarda a barreira a ser removida, se houver
     */
    public void col_tiro_invasor_barreira(ArrayList<Tiro> remover_tiro_invasor, ArrayList<Barreira> remover_barreira){
        Tiro aux_tiro_invasor;
        Barreira aux_barreira;
        
        //verifica cada tiro de invasor com cada barreira
        for(int i = 0; i < tiro_invasor.size(); i++){
            aux_tiro_invasor = tiro_invasor.get(i);
            
            for(int j = 0; j < barreiras.size(); j++){
                aux_barreira = barreiras.get(j);
                
                if(checar_colisao(aux_tiro_invasor, aux_barreira)){
                    remover_tiro_invasor.add(aux_tiro_invasor);
                    
                    if(!aux_barreira.perder_vida()){
                        remover_barreira.add(aux_barreira);
                    }
                }
            }
        }
        
        //checa se ha tiros de invasores a serem removidos e, se sim, os remove
        if(!remover_tiro_invasor.isEmpty()){
            tiro_invasor.removeAll(remover_tiro_invasor);
            remover_tiro_invasor.clear();
        }
        
        //checa se ha barreiras a serem removidas e, se sim, as remove
        if(!remover_barreira.isEmpty()){
            barreiras.removeAll(remover_barreira);
            remover_barreira.clear();
        }
        
    }
    
    /**
     * Verifica colisao entre invasor e barreira
     * @param remover_barreira array que guarda a barreira a ser removida, se houver
     */
    public void col_invasor_barreira(ArrayList<Barreira> remover_barreira){
        Invasor aux_invasor;
        Barreira aux_barreira;
        
        //verifica cada invasor com cada barreira
        for(int i = 0; i < invasores.size(); i++){
            aux_invasor = invasores.get(i);
            
            for(int j = 0; j < barreiras.size(); j++){
                aux_barreira = barreiras.get(j);
                if(checar_colisao(aux_barreira, aux_invasor)){
                    remover_barreira.add(aux_barreira);
                }
            }
        }
        
        //checa se ha barreiras a serem removidas e, se sim, as remove
        if(!remover_barreira.isEmpty()){
            barreiras.removeAll(remover_barreira);
            remover_barreira.clear();
        }
        
    }
    
    /**
     * Verifica se ha colisao entre dois elementos
     * @param elem1     primeiro elemento
     * @param elem2     segundo elemento
     * @return booleando indicando se houve ou nao colisao
     */
    public boolean checar_colisao(Elemento elem1, Elemento elem2){
        Rectangle2D elem_1 = new Rectangle2D(elem1.coord_colunas, elem1.coord_linhas - elem1.altura, elem1.largura, elem1.altura);
        Rectangle2D elem_2 = new Rectangle2D(elem2.coord_colunas, elem2.coord_linhas - elem2.altura, elem2.largura, elem2.altura);
    
        return elem_1.intersects(elem_2);
    }
    
    /**
     * Faz a movimentacao do canhao
     * @param total_colunas tamanho horizontal da janela de jogo
     */
    private void processa_mov_canhao(int total_colunas){
        //movimento p/ esquerda
        if(inputs.contains(KeyCode.LEFT) && canhao.coord_colunas > 0){
            canhao.coord_colunas -= canhao.getVelocidade();
        
        //movimento p/ direita
        } else if(inputs.contains(KeyCode.RIGHT) &&
                (canhao.coord_colunas + canhao.largura < total_colunas)){
            canhao.coord_colunas += canhao.getVelocidade();
        
        //tiro
        } else if(inputs.contains(KeyCode.SPACE)){
            criar_tiro_canhao();
        }
    }
    
    /**
     * Cria tiro p/ o canhao
     */
    public void criar_tiro_canhao(){
        //canhao pode atirar a cada 0.3 seg
        if(System.currentTimeMillis() - tempo_tiro_canhao > 300){
            tiro_canhao.add(new Tiro(canhao.coord_colunas + (canhao.largura)/2 - 29, canhao.coord_linhas - 10, 
                    40,40, 1, true, img_tiro_canhao));
            
            tempo_tiro_canhao = System.currentTimeMillis();
        }
    }
    
    /**
     * Cria dois tiros p/ dois invasores diferentes
     */
    public void criar_tiro_invasores(){
        Invasor aux_invasor;
        
        int valor1 = (int) Math.floor(Math.random() * 301) + 2000;
        int valor;
        
        //tempo pseudoaleatorio p/ que os invasores atirem
        if(System.currentTimeMillis() - tempo_tiro_invasor > valor1){
            //ordena os invasores pela distancia horizontal do canhao
            Collections.sort(invasores, new Comparar_Invasores(canhao.coord_colunas));
            
            //pega o invasor mais proximo do canhao na horizontal
            aux_invasor = invasores.get(0);
            tiro_invasor.add(new Tiro(aux_invasor.coord_colunas + 20,
                    aux_invasor.coord_linhas + aux_invasor.altura + 10, 30, 30, 1, false, img_tiro_invasor));
            
            //pega um invasor aleatoriamente
            valor = (int) Math.floor(Math.random() * (invasores.size()));
            aux_invasor = invasores.get(valor);
            tiro_invasor.add(new Tiro(aux_invasor.coord_colunas + 20,
                    aux_invasor.coord_linhas + aux_invasor.altura + 10, 30, 30, 1, false, img_tiro_invasor));
            
            tempo_tiro_invasor = System.currentTimeMillis();
            
            //embaralha o array de invasores
            Collections.shuffle(invasores);
        }
    }
    
    /**
     * Insere a tecla pressionada pelo player
     * @param code tecla pressionada
     */
    public void add_input(KeyCode code){
        if(!inputs.contains(code)){
            inputs.add(code);
        }
    }
    
    /**
     * Remove a tecla pressionada pelo player
     * @param code tecla pressionada
     */
    public void remove_input(KeyCode code){
        if(inputs.contains(code)){
            inputs.remove(code);
        }
    }
    
    /**
     * Um invasor morto representa um ponto
     */
    public void pontuar(){
        pontos += 1;
    }
    
    /**
     * Retorna a pontuacao do jogo
     * @return int
     */
    public int getPontos(){
        return pontos;
    }

}
