package elementos;

import gui.Tela;

/**
 * Um exemplo de como fazer um elemento que atualiza constantemente sem a necessidade de interação com o usuário
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Semaforo extends Elemento {

    private final int INTERVALO_DE_TEMPO = 10;
    private int contador;
    private boolean sentidoDesce;
    private int posicao;
    // As figuras que o elemento poderá assumir
    private String[] figuras = {"traffic-red.png","traffic-yellow.png", "traffic-green.png"};


    public Semaforo(Tela pai, String imagemNome, int posX, int posY) {
        super(pai, imagemNome, posX, posY);
        this.contador = 2;
        this.posicao = 0;
        this.sentidoDesce = true;
    }

    @Override
    public void atualizar() {

        contador--;

        // Semáforo começa em vermelho e desce para amarelo e verde. Por fim, sobe novamente.
        if (contador <0){
            this.icone = this.carregarImagem(figuras[posicao]);

            if (sentidoDesce){
                posicao++;
                if (posicao == 2){
                    sentidoDesce = false;
                }
            }else {
                posicao--;
                if (posicao == 0){
                    sentidoDesce = true;
                }
            }
            contador = INTERVALO_DE_TEMPO;
        }
    }
}
