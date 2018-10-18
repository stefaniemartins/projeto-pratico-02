package gui;

import elementos.Carro;
import elementos.Elemento;
import elementos.Semaforo;
import exemplo.ExemploDeThread;
import exemplo.OutroCarro;
import util.Teclado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Essa classe é responsável pela toda lógica de desenho de objetos na tela
 * Um objeto Timer fará a atualização periódica da tela, isto é, periodicamente os elementos
 * serão redesenhados por esse Timer.
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Tela extends JPanel implements ActionListener {

    // usado para redesenhar a tela periodicamente
    private Timer timer;
    private int taxaDeAtualizacao;

    // Para capturar as teclas pressionadas pelo usuário
    private Teclado teclado;
    private JTextArea console;


    // Elementos que serão desenhados na tela
    private ArrayList<Elemento> elementos;


    // Exemplo de uma Thread. Essa Thread será responsável por atualizar as coordenadas de um elemento específico
    private Thread atualizaUmCarroViaThread;


    /**
     * É necessário ver quais teclas foram pressionadas e ter uma referência do componente onde serão escritas
     * mensagens para o usuário
     *
     * @param teclado responsável por tratar as teclas pressionadas pelo usuário
     * @param console para imprimir as mensagens para o usuário
     */
    public Tela(Teclado teclado, JTextArea console) {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);
        this.repaint();

        this.elementos = new ArrayList<>();
        this.teclado = teclado;
        this.console = console;

        this.taxaDeAtualizacao = 120;

        // Criando os elementos que serão desenhados na tela
        this.criarElementos();
    }


    /**
     * Aqui são criados 3 elementos de exemplo. Cada elemento é de uma subclasse distinta
     */
    public void criarElementos(){

        // Limpando o ArrayList
        elementos.clear();

        // Adicionando um Carro e um Semáforo
        elementos.add(new Carro(this,"carrov.png",100,400));
        elementos.add(new Semaforo(this, "traffic.png", 10, 50));

        // Esse mesmo objeto é adicionado no ArrayList e é enviado para a Thread que será responsável por
        // atualizar suas coordenadas
        OutroCarro outroCarro = new OutroCarro(this,"azul.png",300,10);

        elementos.add(outroCarro);
        this.atualizaUmCarroViaThread = new ExemploDeThread(outroCarro, this);

    }


    /**
     * Quando a simulação é iniciada cria-se os elementos, dispara o Timer para redesenho da tela periodicamente e
     * dispara a Thread que será responsável por atualizar as coordenadas do OutroCarro
     */
    public void iniciaSimulacao(){
        this.setIgnoreRepaint(true);
        this.timer = new Timer(taxaDeAtualizacao, this);

        this.criarElementos();

        // disparando timer que ficará redesenhando os elementos na tela
        this.timer.start();

        // disparando Thread que ficará atualizando atributos de um elemento específico
        atualizaUmCarroViaThread.start();
    }


    /**
     * Este método é invocado a cada taxaDeAtualizacao pelo Timer
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Verifica a situação das teclas (pressionadas, soltas, etc)
        this.teclado.poll();

        // Atualiza as coordenadas dos elementos, respeitando a lógica de cada um
        this.processarLogica();

        // desenha os elementos na tela novamente
        this.renderizar();
    }


    /**
     * Aqui é feita a atualização das coordenadas de todos os elementos
     * gráficos que estão na área de desenho da Tela
     */
    public void processarLogica(){

        // atualizando as coordenadas manualmente
        elementos.forEach(elemento -> {

            // A ideia desse exemplo é que o OutroCarro
            // seja atualizado por uma outra thread e não por aqui.
            if (!(elemento instanceof OutroCarro)) {

                // Semáforo tem uma lógica de atualização diferente daquela de Carro
                // Semáforo fica frequentemente trocando suas cores
                // Carro só atualiza coordenadas se o usuário tive pressionado a tecla SETA para CIMA ou SETA para Baixo
                // OutroCarro só é atualizado pela Thread "ExemploDeThread"
                elemento.atualizar();
            }
        });
    }


    /**
     * Limpa a tela e desenha todos elementos novamente já com suas coordenadas atualizadas
     */
    public void renderizar(){
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        //Limpando a tela, isto é, desenhando um retângulo preenchido com dimensões iguais a da Tela
        g2.setColor(Color.gray);
        g2.fillRect(0, 0, getWidth(), getHeight());


        // Desenhando o cenário de fundo
        this.renderizarCenario();


        // desenhando os elementos na tela
        elementos.forEach(elemento -> elemento.desenhar(g2));

        //liberando os contextos gráficos
        g2.dispose();
    }


    /**
     * O cenário de fundo tem elementos estáticos. Aqui um pequeno exemplo de como desenhar retângulo,
     * mas é possível desenhar imagens, elipse, string, etc.
     *
     */
    public void renderizarCenario(){
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        // desenhando retângulos que poderiam ser janelas?
        g2.setColor(Color.darkGray);
        g2.fillRect(200,10,40,40);
        g2.fillRect(200,100,40,40);

        g2.dispose();
    }


    /**
     * Fim de jogo/rodada
     */
    public void pararTimer() {

        // Interrompe o timer que atualiza a tela
        this.timer.stop();
        this.setIgnoreRepaint(false);

        // Desenha a última posição dos elementos
        this.renderizar();

        // Interrompe a Thread que estava atualizando as coordenadas do OutroCarro
        ((ExemploDeThread)this.atualizaUmCarroViaThread).setExecutando(false);
    }


    public ArrayList<Elemento> getElementos() {
        return elementos;
    }


    public Teclado getTeclado() {
        return teclado;
    }

    /**
     * Console é um JTextArea do JPanel Principal. A ideia aqui é permitir escrever naquele componente
     *
     * @param mensagem mensagem que aparecerá no console
     */
    public void escreverNoConsole(String mensagem){
        this.console.append(mensagem);
    }


    /**
     * Para verificar se as coordenadas informadas estão dentro da área de algum elemento que esteja na tela
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void clicouSobreElemento(int x, int y){

        // Varrendo lista de elementos para ver se o usuário clicou sobre um elemento. Se sim, então escreva no console
        elementos.forEach(elemento ->{
            if (elemento.clicouDentro(x,y)){
                this.escreverNoConsole("Clicou sobre elemento: " + elemento.getClass() + "\n");
            }

        });

    }


}
