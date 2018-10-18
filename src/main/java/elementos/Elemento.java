package elementos;

import gui.Principal;
import gui.Tela;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
 * Para desenhar uma imagem na Tela é necessário saber o caminho da imagem, dimensões da imagem
 * e coordenadas atuais da imagem na Tela. Aqui também tem um algo que indica a velocidade de
 * deslocamento da imagem na Tela (p. ex. quando um usuário pressiona uma tecla).
 *
 *
 * @author Emerson Ribeiro de Mello - 2011
 */
public abstract class Elemento {

    protected final static int VELOCIDADE_INICIAL = 20; // movimentará 20 pixels
    protected Tela pai;
    protected Image icone; // imagem
    protected String nomeDaImagem; // nome da imagem
    protected int largura; // largura da imagem
    protected int altura;  // altura da imagem
    protected int posX;    // coordenada X no Canvas
    protected int posY;    // coordenada Y no Canvas

    protected int velocidade; // indica a taxa de deslocamento em pixel

    public Elemento(Tela pai, String imagemNome, int posX, int posY) {
        this.pai = pai;
        this.nomeDaImagem = imagemNome;
        this.icone = this.carregarImagem(this.nomeDaImagem);
        this.largura = this.icone.getWidth(null);
        this.altura = this.icone.getHeight(null);

        this.posX = posX;
        this.posY = posY;

        this.velocidade = VELOCIDADE_INICIAL;
    }


    /**
     * Para atualizar as coordenadas e outros atributos do elemento
     * Esse é método deve ser invocado periodicamente (por um Timer ou Thread).
     */
    public abstract void atualizar();


    /**
     * Verifica se as coordenadas do clique do mouse estão contidas dentro dos
     * limites do elemento
     * @param px -- coordenada X do clique
     * @param py -- coordenada Y do clique
     * @return true se o clique foi dentro da area do elemento
     */
    public boolean clicouDentro(int px, int py) {
        return ((px > this.posX && px < (this.posX + largura))
                && (py > this.posY && py < (this.posY + altura))) ? true : false;
    }


    /**
     * Para desenhar o Elemento na tela
     * @param g contexto gráfico
     */
    public void desenhar(Graphics2D g) {
        //desenhando o ícone do elemento na Tela
        g.drawImage(this.icone, this.posX, this.posY, this.pai);
    }


    /**
     * Para carregar a imagem do disco. Esse método não pode ser sobreescrito.
     * Veja a palavra final.
     * @param imagem Nome da imagem. Ex: bomba.png
     * @return Retorna um objeto Image
     */
    public final Image carregarImagem(String imagem) {
        URL url = Principal.class.getResource("../../resources/" + imagem);

        ImageIcon iicon = new ImageIcon(url);
        return iicon.getImage();
    }

}
