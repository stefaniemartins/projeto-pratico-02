package exemplo;

//import elementos.Andar;
import elementos.Elemento;
import gui.Tela;
import util.LerArquivo;

/**
 *
 * Um exemplo de como seria possível atualizar as coordenadas de um elemento por uma outra Thread
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class ExemploDeThread extends Thread{

    private Elemento elemento;
    private  boolean  executando;
    private Tela pai;
    //private Andar andar;

    public ExemploDeThread(Elemento elemento, Tela pai){
            this.elemento = elemento;
            this.executando = false;
            this.pai = pai;
    }


    @Override
    public void run() {
        pai.escreverNoConsole("Thread iniciada\n");
        this.executando = true;

        //int i = 1;
        // Isso vai repetir até que o usuário mande parar a simulação
        while (executando)
        {
            // Atualize as coordenadas do elemento, que no caso, fará a troca de imagens do OutroCarro
            elemento.atualizar();
            //andar.filaPorAndar(i);
            //i++;

            try {

                // Durma por 1 segundo
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pai.escreverNoConsole("Thread finalizada \n");
    }

    /**
     * Usado basicamente para fazer a thread ser finalizada, uma vez que o uso do método Thread.stop() não é seguro e foi descontinuado
     * @param executando
     */
    public synchronized void setExecutando(boolean executando) {
        this.executando = executando;
    }
}
