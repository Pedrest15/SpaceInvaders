package spaceinvaders2;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Classe responsavel pelas musicas tocadas durante o jogo
 * @author Pedro Lucas Castro de Andrade - 11212289
 */
public class Audio {
    /**
    * Variaveis necessarias p/ reproduzir os temas sonoros
    */
    Clip clip_abertura;
    Clip clip_jogo1;
    Clip clip_jogo2;
    Clip clip_vitoria;
    Clip clip_derrota;
    
    /**
     * Responsavel por abrir o arquivo da musica de abertura
     */
    public void abrir_tema_abertura(){
        try{
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("star_wars_theme.wav"));
            DataLine.Info info = new DataLine.Info(Clip.class, musica.getFormat());
            
            clip_abertura = (Clip)AudioSystem.getLine(info);
            clip_abertura.open(musica);
         } catch(Exception e) {
            System.out.println("Erro ao abrir audio " + e.getMessage());
        }
    }
    
    /**
     * Reproduz a musica de abertura em loop
     */
    public void tema_abertura(){
        try{
            clip_jogo1.setMicrosecondPosition(0);
            clip_abertura.start();
            clip_abertura.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Erro ao reproduzir audio " + e.getMessage());
        }
    }
    
    /**
     * Interrompe a reproducao da musica de abertura
     */
    public void parar_tema_abertura(){
        clip_abertura.stop();
    }
    
    /**
     * Responsavel por abrir o arquivo da musica dos niveis impares
     */
    public void abrir_tema_jogo1(){
        try{
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("star_wars_space_battle_theme.wav"));
            DataLine.Info info = new DataLine.Info(Clip.class, musica.getFormat());
            
            clip_jogo1 = (Clip)AudioSystem.getLine(info);
            clip_jogo1.open(musica);
         } catch(Exception e) {
            System.out.println("Erro ao abrir audio " + e.getMessage());
        }
    }
    
    /**
     * Reproduz a musica dos niveis impares
     */
    public void tema_jogo1(){
        try{
            clip_jogo1.setMicrosecondPosition(0);
            clip_jogo1.start();
            clip_jogo1.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Erro ao reproduzir audio " + e.getMessage());
        }
    }
    
    /**
     * Interrompe a reproducao da musica dos niveis impares
     */
    public void parar_tema_jogo1(){
        clip_jogo1.stop();
    }
    
    /**
     * Responsavel por abrir o arquivo da musica dos niveis pares
     */
    public void abrir_tema_jogo2(){
        try{
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("duel_of_the_fates.wav"));
            DataLine.Info info = new DataLine.Info(Clip.class, musica.getFormat());
            
            clip_jogo2 = (Clip)AudioSystem.getLine(info);
            clip_jogo2.open(musica);
         } catch(Exception e) {
            System.out.println("Erro ao abrir audio " + e.getMessage());
        }
    }
    
    /**
     * Reproduz a musica dos niveis pares
     */
    public void tema_jogo2(){
        try{
            clip_jogo2.setMicrosecondPosition(0);
            clip_jogo2.start();
            clip_jogo2.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Erro ao reproduzir audio " + e.getMessage());
        }
    }
    
    /**
     * Interrompe a reproducao da musica dos niveis pares
     */
    public void parar_tema_jogo2(){
        clip_jogo2.stop();
    }
    
    /**
     * Responsavel por abrir o arquivo da musica de vitoria
     */
    public void abrir_tema_vitoria(){
        try{
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("star_wars_throne_theme.wav"));
            DataLine.Info info = new DataLine.Info(Clip.class, musica.getFormat());
            
            clip_vitoria = (Clip)AudioSystem.getLine(info);
            clip_vitoria.open(musica);
         } catch(Exception e) {
            System.out.println("Erro ao abrir audio " + e.getMessage());
        }
    }
    
    /**
     * Reproduz a musica de vitoria
     */
    public void tema_vitoria(){
        try{
            clip_vitoria.setMicrosecondPosition(0);
            clip_vitoria.start();
            clip_vitoria.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Erro ao reproduzir audio " + e.getMessage());
        }
    }
    
    /**
     * Interrompe a reproducao da musica de vitoria
     */
    public void parar_tema_vitoria(){
        clip_vitoria.stop();
    }
    
    /**
     * Responsavel por abrir o arquivo da musica de game over
     */
    public void abrir_tema_derrota(){
        try{
            AudioInputStream musica = AudioSystem.getAudioInputStream(new File("star_wars_darth_vader_theme.wav"));
            DataLine.Info info = new DataLine.Info(Clip.class, musica.getFormat());
            
            clip_derrota = (Clip)AudioSystem.getLine(info);
            clip_derrota.open(musica);
         } catch(Exception e) {
            System.out.println("Erro ao abrir audio " + e.getMessage());
        }
    }
    
    /**
     * Reproduz a musica de game over
     */
    public void tema_derrota(){
        try{
            clip_derrota.setMicrosecondPosition(0);
            clip_derrota.start();
            clip_derrota.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(Exception e) {
            System.out.println("Erro ao reproduzir audio " + e.getMessage());
        }
    }
    
    /**
     * Interrompe a reproducao da musica de game over
     */
    public void parar_tema_derrota(){
        clip_derrota.stop();
    }
    
}
