package gui;

import elementos.*;
import util.LerArquivo;
import util.Teclado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Essa classe é responsável pela toda lógica de desenho de objetos na tela
 * Um objeto Timer fará a atualização periódica da tela, isto é, periodicamente os elementos
 * serão redesenhados por esse Timer.
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Tela extends JPanel implements ActionListener
{
    private LerArquivo lerArquivo;
    // usado para redesenhar a tela periodicamente
    private Timer timer;
    private int taxaDeAtualizacao;

    private Contador C;
    private int CountTela = 25;
    private int instante = 0;

    // Para capturar as teclas pressionadas pelo usuário
    private Teclado teclado;
    private JTextArea console;

    // Elementos que serão desenhados na tela
    private ArrayList<Elemento> elementos;
    private ArrayList<Elevador> listaElevadores;
    private ArrayList<Andar> listaAndares;

    // Exemplo de uma Thread. Essa Thread será responsável por atualizar as coordenadas de um elemento específico
    private Thread atualizaUmCarroViaThread;

    private HashMap<Integer, ArrayList<Pessoa>> tabelaInstantePessoas;

    private Andar andar;

    /**
     * É necessário ver quais teclas foram pressionadas e ter uma referência do componente onde serão escritas
     * mensagens para o usuário
     *
     * @param teclado responsável por tratar as teclas pressionadas pelo usuário
     * @param console para imprimir as mensagens para o usuário
     */
    public Tela(Teclado teclado, JTextArea console)
    {
        // Ler arquivo.
        lerArquivo = new LerArquivo("/home/stefanie/Área de Trabalho/projeto-pratico-02/src/main/java/util/arquivo.txt");
        tabelaInstantePessoas = lerArquivo.getVetorPessoas();

        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);
        this.repaint();

        this.elementos = new ArrayList<>();
        this.listaElevadores = new ArrayList<>();
        this.listaAndares = new ArrayList<>();
        this.teclado = teclado;
        this.console = console;

        this.C = new Contador();
        this.taxaDeAtualizacao = 120;

        // Criando os elementos que serão desenhados na tela

        this.criarElementos();
    }


    /**
     * Aqui são criados 3 elementos de exemplo. Cada elemento é de uma subclasse distinta
     */
    public void criarElementos(){

        // Limpando o ArrayList
        listaAndares.clear();
        listaElevadores.clear();

        // Adicionando andares.
        listaAndares.add(new Andar(this,"nobody.png",3,596)); // Andar 1.
        listaAndares.add(new Andar(this,"nobody.png",3,486)); // Andar 2.
        listaAndares.add(new Andar(this,"nobody.png",3,376)); // Andar 3.
        listaAndares.add(new Andar(this,"nobody.png",3,266)); // Andar 4.
        listaAndares.add(new Andar(this,"nobody.png",3,156)); // Andar 5.
        listaAndares.add(new Andar(this,"nobody.png",3,46));  // Andar 6

        // Adicionando elevadores
        listaElevadores.add(new Elevador(this,"open.png",123,596)); // Elevador 1.
        listaElevadores.add(new Elevador(this,"open.png",283,596)); // Elevador 2.
        listaElevadores.add(new Elevador(this,"open.png",443,596)); // Elevador 3.

        // Esse mesmo objeto é adicionado no ArrayList e é enviado para a Thread que será responsável por
        // atualizar suas coordenadas
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
        //atualizaUmCarroViaThread.start();
    }

    /**
     * Este método é invocado a cada taxaDeAtualizacao pelo Timer
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        CountTela--;

        if (CountTela < 0)
        {
            C.inc();
            CountTela = 10;
        }

        if (C.getValor() == instante)
        {
            processarLogica(instante);
            instante++;
        }

        // Verifica a situação das teclas (pressionadas, soltas, etc)
        this.teclado.poll();

        // Atualiza as coordenadas dos elementos, respeitando a lógica de cada um
        //this.processarLogica();

        // desenha os elementos na tela novamente
        this.renderizar();
    }



    /**
     * Aqui é feita a atualização das coordenadas de todos os elementos
     * gráficos que estão na área de desenho da Tela
     */
    public void processarLogica(int instante)
    {
        HashMap<Integer, ArrayList<Integer>> tabelaPessoas;
        System.out.println();

        tabelaPessoas = filaPessoas(instante);


        for (int i = 0; i < 6; i++)
        {
            System.out.println("---"+i);
            if (tabelaPessoas.containsKey(i))
            {
                ArrayList<Integer> listaDestino;

                listaDestino = tabelaPessoas.get(i);
                ArrayList<Integer> listaAuxiliar = andar.getPessoasAndar(i);

                if (listaAuxiliar != null)
                {
                    listaDestino.addAll(andar.getPessoasAndar(i));
                    andar.setPessoasAndar(i, listaDestino);
                }
            }
        }
    }

    public HashMap<Integer, ArrayList<Integer>> filaPessoas(int instante)
    {
        ArrayList<Pessoa> listaPessoa;
        HashMap<Integer, ArrayList<Integer>> tabelaOrigemDestino = new HashMap<>();

        // Retorna uma lista
        listaPessoa = tabelaInstantePessoas.get(instante);

        if (listaPessoa != null)
        {
            for (int i = 0; i < listaPessoa.size(); i++)
            {
                Pessoa pessoa = listaPessoa.get(i);
                ArrayList<Integer> auxiliar = new ArrayList();

                if (tabelaOrigemDestino.containsKey(pessoa.getOrigem()))
                {
                    tabelaOrigemDestino.get(pessoa.getOrigem()).add(pessoa.getDestino());
                }

                else
                {
                    auxiliar.add(pessoa.getDestino());
                    tabelaOrigemDestino.put(pessoa.getOrigem(), auxiliar);
                }
            }
        }

        return tabelaOrigemDestino;
    }
    /**
     * Limpa a tela e desenha todos elementos novamente já com suas coordenadas atualizadas
     */
    public void renderizar()
    {
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        //Limpando a tela, isto é, desenhando um retângulo preenchido com dimensões iguais a da Tela
        g2.setColor(Color.CYAN);
        g2.fillRect(0, 0, getWidth(), getHeight());


        // Desenhando o cenário de fundo
        this.renderizarCenario();


        // desenhando os elementos na tela
        //elementos.forEach(elemento -> elemento.desenhar(g2));
        listaElevadores.forEach(elevador -> elevador.desenhar(g2));
        listaAndares.forEach(andares -> andares.desenhar(g2));

        //liberando os contextos gráficos
        g2.dispose();
    }


    /**
     * O cenário de fundo tem elementos estáticos. Aqui um pequeno exemplo de como desenhar retângulo,
     * mas é possível desenhar imagens, elipse, string, etc.
     *
     */
    public void renderizarCenario()
    {
        Graphics2D g2 = (Graphics2D) this.getGraphics();

        // desenhando retângulos que poderiam ser janelas?
        g2.setColor(Color.GRAY);
        g2.fillRect(70,0,508,10);   // Linha horizontal 1.
        g2.fillRect(70,110,488,10); // Linha horizontal 2.
        g2.fillRect(70,220,488,10); // Linha horizontal 3.
        g2.fillRect(70,330,488,10); // Linha horizontal 4.
        g2.fillRect(70,440,488,10); // Linha horizontal 5.
        g2.fillRect(70,550,488,10); // Linha horizontal 6.
        g2.fillRect(70,660,488,10); // Linha horizontal 7.
        g2.fillRect(70,0,10,660);   // Linha vertical 1.
        g2.fillRect(230,10,10,660); // Linha vertical 2.
        g2.fillRect(390,10,10,660); // Linha vertical 3.
        g2.fillRect(550,10,10,660); // Linha vertical 4.

        Graphics2D g3 = (Graphics2D) this.getGraphics();

        g3.setColor(Color.GREEN);
        g3.fillRect(0,670,653,40); // Grama.

        Graphics2D g4 = (Graphics2D) this.getGraphics();

        g4.setColor(Color.blue);
        g4.fillRect(0,0,70,670); // Céu lado esquerdo.
        g4.fillRect(560,0,551,670); // Céu lado direito.


        g2.dispose();
    }


    /**
     * Fim de jogo/rodada
     */
    public void pararTimer()
    {

        // Interrompe o timer que atualiza a tela
        this.timer.stop();
        this.setIgnoreRepaint(false);

        // Desenha a última posição dos elementos
        this.renderizar();

        // Interrompe a Thread que estava atualizando as coordenadas do OutroCarro
        //((ExemploDeThread)this.atualizaUmCarroViaThread).setExecutando(false);
    }

    public ArrayList<Elemento> getElementos()
    {
        return elementos;
    }


    public Teclado getTeclado()
    {
        return teclado;
    }

    /**
     * Console é um JTextArea do JPanel Principal. A ideia aqui é permitir escrever naquele componente
     *
     * @param mensagem mensagem que aparecerá no console
     */
    public void escreverNoConsole(String mensagem)
    {
        this.console.append(mensagem);
    }


    /**
     * Para verificar se as coordenadas informadas estão dentro da área de algum elemento que esteja na tela
     * @param x coordenada X
     * @param y coordenada Y
     */
    public void clicouSobreElemento(int x, int y)
    {
        // Varrendo lista de elementos para ver se o usuário clicou sobre um elemento. Se sim, então escreva no console
        elementos.forEach(elemento ->{
            if (elemento.clicouDentro(x,y))
            {
                this.escreverNoConsole("Clicou sobre elemento: " + elemento.getClass() + "\n");
            }
        });

    }


}
