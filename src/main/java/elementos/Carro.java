package elementos;

import gui.Tela;
import java.awt.event.KeyEvent;


/**
 * Esse carro poderá ser movimentado pelo usuário. Se o usuário pressionar as teclas SETA para CIMA ou SETA para BAIXO
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Carro extends Elemento {

    private int cima;
    private int baixo;


    public Carro(Tela pai, String imagemNome, int posX, int posY) {
        super(pai, imagemNome, posX, posY);

        this.cima = KeyEvent.VK_UP; // tecla SETA para CIMA
        this.baixo = KeyEvent.VK_DOWN; // tecla SETA para BAIXO

    }

    @Override
    public void atualizar() {
        if (pai.getTeclado().teclaApertada(cima)){
            movimentaParaCima();
        }else if (pai.getTeclado().teclaApertada(baixo)){
            movimentaParaBaixo();
        }

        // TODO: Desafio: Que tal implementar métodos para permitir o Carro se movimentar para direita e esquerda?
    }

    /**
     * Move o elemento no eixo Y. Garante que o elemento respeite os limites
     * da Tela
     */
    public void movimentaParaCima() {
        int destinoY = posY - this.velocidade;

        posY = (destinoY > 0) ? destinoY : 0;

    }

    /**
     * Move o elemento no eixo Y. Garante que o elemento respeite os limites
     * da Tela
     */
    public void movimentaParaBaixo() {
        int destinoY = posY + this.velocidade;

        posY = (destinoY < pai.getHeight()-this.altura) ? destinoY : pai.getHeight()-this.altura;

    }
}
