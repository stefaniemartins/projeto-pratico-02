package exemplo;

import elementos.Elemento;
import gui.Tela;

/**
 * Exemplo de um elemento bem simples que só troca a imagem
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class OutroCarro extends Elemento {

    private String[] figuras = {"azul.png","verde.png"};
    private int posicao;

    public OutroCarro(Tela pai, String imagemNome, int posX, int posY) {
        super(pai, imagemNome, posX, posY);
        posicao = 0;
    }


    @Override
    public void atualizar() {
        // Toda vez que esse método for invocado a imagem do elemento é trocada entre azul e verde

        posicao = (posicao + 1) % 2;
        this.icone = this.carregarImagem(figuras[posicao]);
    }
}
