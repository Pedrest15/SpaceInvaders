package spaceinvaders2;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Classe responsavel pela interface grafica
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Tela extends Application{
    protected GraphicsContext gc;
    protected Scene scene;
    
    protected int total_linhas = 600, total_colunas = 700;
    
    protected Engine engine;
    
    protected ArrayList<Elemento> elementos;
    
    Audio musicas = new Audio();
    
    Canhao canhao;
    
    //carrega a imagem do logo usada
    Image imagem_logo = new Image("Imagens/logo_star_wars.png", 400, 400, true, true);
    
    //carrega os tamanhos de fonte que serao usadas
    Font fonte_grande = new Font("Arial", 100);
    Font fonte_media = new Font("Arial", 50);
    Font fonte_pequena = new Font("Arial", 35);
    Font fonte_status = new Font("Arial", 20);
    
    /**
     * Inicia o jogo
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage){
        //configuracoes iniciais, tamnho de tela, cor de fundo...
        Group root = new Group();
        scene = new Scene(root, total_colunas, total_linhas);
        scene.setFill(Color.rgb(0, 0, 0));
        Canvas canvas = new Canvas(total_colunas, total_linhas);        
        gc = canvas.getGraphicsContext2D();          
        root.getChildren().add(canvas);
        
        primaryStage.getIcons().add(imagem_logo);
        primaryStage.setTitle("Space Invaders - Star Wars");
        
        primaryStage.show();
        ler_teclado();
        
        musicas.tema_abertura();
        loop(primaryStage);
    }
    
    /**
     * Cria a tela base usada sempre
     * @param primaryStage
     * @param scene
     * @param gc 
     */
    public void tela_base(Stage primaryStage, Scene scene, GraphicsContext gc){
        gc.clearRect(0, 0, total_colunas, total_linhas);
        primaryStage.setScene(scene);
        
    }
    
    /**
     * Cria a tela inicial do jogo
     * @param primaryStage
     * @param scene
     * @param gc 
     */
    public void tela_inicial(Stage primaryStage, Scene scene, GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.setFont(fonte_grande);
        gc.fillText("SPACE INVADERS", 200, 100, 320);
        
        gc.drawImage(imagem_logo, 150, 102);
        
        gc.setFont(fonte_pequena);
        
        gc.fillText("Press ENTER to start", 260, 450, 200);
        gc.fillText("Press H to open commands list", 230, 485, 260);
        gc.fillText("Press O to open speed options", 230, 520, 250);
              
        if(engine.abrir_instrucoes() || engine.abrir_opcoes()){
            return;
        }
        
        if(engine.comecar()){
            musicas.parar_tema_abertura();
            if(engine.getNivel() % 2 == 0){
                musicas.tema_jogo2();
            } else {   
                musicas.tema_jogo1();
            }
        }
    }
    
    public void tela_instrucoes(Stage primaryStage, Scene scene, GraphicsContext gc){      
        gc.setFont(fonte_media);
        gc.fillText("Use the LEFT and the RIGHT ARROW KEYS to move", 80, 280, 550);
        gc.fillText("Use the SPACE to fire", 240, 350, 230);
        
        gc.setFont(fonte_pequena);
        gc.fillText("Press ENTER to start", 260, 450, 200);
        gc.fillText("Press O to open speed options", 230, 485, 250);
        
        //verifica se o player quer abrir tela de opcoes
        if(engine.abrir_opcoes()){
            engine.fechar_instrucoes();
            return;
        }
        
        //verifica se o player quer iniciar o jogo
        if(engine.comecar()){
            engine.fechar_instrucoes();
            musicas.parar_tema_abertura();
            if(engine.getNivel() % 2 == 0){
                musicas.tema_jogo2();
            } else {   
                musicas.tema_jogo1();
            }
        }
    }
    
    /**
     * Cria a tela de opcoes de velocidade
     * @param primaryStage
     * @param scene
     * @param gc 
     */
    public void tela_opcoes(Stage primaryStage, Scene scene, GraphicsContext gc){      
        //opcoes de velocidade disponiveis (recomendade R p/ windows e I p/ Linux)
        gc.setFont(fonte_media);
        gc.fillText("Press R to reduce the game's speed", 80, 280, 550);
        gc.fillText("Press I to increase the games's speed", 80, 350, 550);
        
        //comecar jogo ou ver comandos
        gc.setFont(fonte_pequena);
        gc.fillText("Press ENTER to start", 260, 450, 200);
        gc.fillText("Press H to open commands list", 230, 485, 260);
        
        engine.setOpcoes();
        
        //verifica se o player quer abrir instrucoes
        if(engine.abrir_instrucoes()){
            engine.fechar_opcoes();
            return;
        }
        
        //verifica se o player quer iniciar o jogo
        if(engine.comecar()){
            engine.fechar_opcoes();
            musicas.parar_tema_abertura();
            
            //coloca a musica adequada ao nivel de jogo
            if(engine.getNivel() % 2 == 0){
                musicas.tema_jogo2();
            } else {   
                musicas.tema_jogo1();
            }
        }
    }
    
    /**
     * Construtor da classe Tela
     */
    public Tela(){
        
        this.engine = new Engine();
        this.elementos = new ArrayList<>();
        
        //cria os elementos do jogo
        this.elementos = engine.criar_elementos(total_colunas, total_linhas);
        canhao = (Canhao) elementos.get(elementos.size() - 1);
        
        //carrega todas as musicas que serao usadas
        musicas.abrir_tema_abertura();
        musicas.abrir_tema_jogo1();
        musicas.abrir_tema_jogo2();
        musicas.abrir_tema_vitoria();
        musicas.abrir_tema_derrota();
    
    }
    
    /**
     * Laco onde de fato roda o jogo
     * @param primaryStage 
     */
    protected void loop(Stage primaryStage){
        AnimationTimer at;
        at = new AnimationTimer()
        {
            //pega o tempo de iteracao p/ congelar a tela caso o canhao seja atingido
            private long timer_msg_atingido = 0;
            
            @Override
            public void handle(long now){
                tela_base(primaryStage, scene, gc);
                
                //abrir tela inicial
                if(!engine.comecou && !engine.instrucoes && !engine.opcoes){
                    tela_inicial(primaryStage, scene, gc);
                
                //abrir tela de comandos
                } else if(!engine.comecou && engine.instrucoes){
                    gc.clearRect(0 , 0, total_colunas, total_linhas);
                    tela_instrucoes(primaryStage, scene, gc);
                
                //abrir tela de opcoes de velocidade
                } else if(!engine.comecou && engine.opcoes){
                    gc.clearRect(0 , 0, total_colunas, total_linhas);
                    tela_opcoes(primaryStage, scene, gc);    
                    
                //processa o jogo
                } else if(engine.jogando && !engine.atingido){
                    elementos = engine.roda_jogo(total_colunas, total_linhas, musicas);
                    
                    gc.clearRect(0 , 0, total_colunas, total_linhas);
                    
                    desenhar_status();
                    
                    //desenha na janela de jogo todos os elementos
                    for(int i = 0; i < elementos.size(); i++){
                        Elemento elemento = elementos.get(i);
                        gc.drawImage(elemento.getImagem(), elemento.coord_colunas, elemento.coord_linhas);
                    }
                    
                    //reinicia o tempo p/ uma nova iteracao do laco
                    timer_msg_atingido = System.currentTimeMillis();
                    
                //abre tela de aviso de canhao atingido
                } else if(engine.jogando && engine.atingido){
                    gc.setFont(fonte_grande);
                    gc.fillText("Shooted", 180, 300, 320);
                    gc.setFont(fonte_media);
                    gc.fillText("Lives: " + canhao.getVidas(), 250, 380, 250);
                    
                    //jogo fica 2 seg congelado
                    if(System.currentTimeMillis() - timer_msg_atingido >= 2000){
                        engine.atingido = false;
                    }
                } else {
                    //abre tela de vitoria
                    if(engine.venceu){
                        tela_base(primaryStage, scene, gc);
                       
                        gc.setFont(fonte_grande);
                        gc.fillText("Congrats, you win", 140, 300, 400);
                        
                        gc.setFont(fonte_media);
                        gc.fillText("Your score was: " + engine.getPontos(), 220, 375, 250);
                        
                        gc.setFont(fonte_pequena);
                        gc.fillText("Press ENTER to play the next level", 175, 430, 350);
                        
                        //verifica se o player quer avancar p/ o proximo nivel
                        if(engine.jogar_novamente()){
                            engine.subir_nivel();
                            
                            elementos.clear();
                            elementos = engine.criar_elementos(total_colunas, total_linhas);
                            canhao = (Canhao) elementos.get(elementos.size() - 1);
                            
                            engine.venceu = false;
                            engine.jogando = true;
                            musicas.parar_tema_vitoria();
                            
                            //coloca a musica adequada ao nivel de jogo
                            if(engine.getNivel() % 2 == 0){
                                musicas.tema_jogo2();
                            } else {   
                                musicas.tema_jogo1();
                            }
                        }
                        
                    //abre tela de game over
                    } else {
                        tela_base(primaryStage, scene, gc);
                        
                        gc.setFont(fonte_grande);
                        gc.fillText("Game Over", 180, 300, 320);
                        
                        gc.setFont(fonte_media);
                        gc.fillText("Your score was: " + engine.getPontos(), 220, 370, 250);
                        
                        gc.setFont(fonte_pequena);
                        gc.fillText("Press ENTER to play again", 200, 430, 300);
                        gc.fillText("Press O to open speed options", 230, 485, 250);
                        
                        //verifica se o player quer reiniciar o jogo
                        if(engine.jogar_novamente()){
                            //retorna ao nivel 1
                            engine.nivel1();
                            
                            //apaga os elementos usados e cria novos
                            elementos.clear();
                            elementos = engine.criar_elementos(total_colunas, total_linhas);
                            canhao = (Canhao) elementos.get(elementos.size() - 1);
                            
                            musicas.parar_tema_derrota();
                            
                            //coloca a musica adequada ao nivel de jogo
                            if(engine.getNivel() % 2 == 0){
                                musicas.tema_jogo2();
                            } else {   
                                musicas.tema_jogo1();
                            }
                            
                        //verifica se o player quer alterar a velocidade do jogo
                        }else if(engine.abrir_opcoes()){
                            engine.comecou = false;
                            
                            //retorna ao nivel 1
                            engine.nivel1();
                            
                            
                            //apaga os elementos usados e cria novos
                            elementos.clear();
                            elementos = engine.criar_elementos(total_colunas, total_linhas);
                            canhao = (Canhao) elementos.get(elementos.size() - 1);
                            
                            musicas.parar_tema_derrota();
                            musicas.tema_abertura();
                        }
                    }
                }
            }
        };
        
        at.start();
        primaryStage.show();
    }
    
    /**
     * Imprime a pontuacao, o nivel e as vidas do canhao no topo da janela
     */
    public void desenhar_status(){
        gc.setFont(fonte_status);
        
        gc.fillText("Score: ", 10, 25, 100);
        gc.fillText(engine.getPontos()+ "", 90, 25, 100);
        
        gc.fillText("Level " + engine.getNivel(), 320, 25, 100);
        
        gc.fillText("Lives: ", 610, 25, 100);
        gc.fillText(canhao.getVidas()+ "", 680, 25, 100);
    
    }
    
    /**
     * Pega as teclas pressionadas pelo player
     */
    public void ler_teclado(){
        scene.setOnKeyPressed((KeyEvent e) -> {
            engine.add_input(e.getCode());
        });
        
        scene.setOnKeyReleased((KeyEvent e) -> {
            engine.remove_input(e.getCode());
        });
    }
}
