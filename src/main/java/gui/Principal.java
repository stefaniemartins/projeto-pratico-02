package gui;

import elementos.Carro;
import elementos.Elemento;
import util.Teclado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;


/**
 * Esse é um esqueleto (feito com IntelliJ 2018.3 + gradle) para indicar como fazer uma aplicação gráfica com
 * uma lógica de atualização semelhante a um jogo em J2D.
 *
 * Nesse projeto também um exemplo de como criar uma Thread específica para atualizar as coordenadas de um elemento
 * gráfico que depois é redesenhado periodicamente pelo Timer que está associado a área de desenho (Tela)
 *
 * @author Emerson Ribeiro de Mello - 2018
 */
public class Principal {
    private JPanel painelPrincipal;
    private JPanel painelEsquerdo;
    private JButton iniciarButton;
    private JPanel painelDireito;
    private JTextArea console;
    private JPanel baixo;
    private JPanel cima;
    private JButton carregarArquivoButton;
    private JButton desceButton;

    private Teclado teclado;

    public Principal() {

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iniciarButton.getText().equals("Iniciar")) {
                    ((Tela) painelEsquerdo).iniciaSimulacao();
                    iniciarButton.setText("Parar");

                    carregarArquivoButton.setEnabled(false);
                    desceButton.setEnabled(true);

                    console.append("<-Iniciou\n");


                }else {
                    ((Tela) painelEsquerdo).pararTimer();
                    iniciarButton.setText("Iniciar");

                    carregarArquivoButton.setEnabled(true);
                    desceButton.setEnabled(false);

                    console.append("->Parou\n");
                }

                // devolvendo o foco para o painel Tela capturar as teclas
                ((Tela)painelEsquerdo).requestFocus();

            }
        });

        /**
         * Exemplo de como usar o JFileChooser para selecionar um arquivo no sistema de arquivos
         */
        carregarArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                int retorno = fc.showOpenDialog(painelPrincipal);


                // se um arquivo foi selecionado
                if (retorno == JFileChooser.APPROVE_OPTION) {
                    File arquivo = fc.getSelectedFile();
                    JOptionPane.showMessageDialog(painelPrincipal, arquivo.getName(), "O arquivo selecionado foi:", JOptionPane.INFORMATION_MESSAGE);

                    console.append("Arquivo " + arquivo.getName() + " foi carregado");
                }

                // devolvendo o foco para o painel Tela poder capturar as teclas
                ((Tela)painelEsquerdo).requestFocus();
            }
        });

        /**
         * Um pequeno exemplo de como um componente da JPanel principal poderia invocar
         * objetos que estão contidos dentro da Tela
         *
         */
        desceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Elemento> elementos = ((Tela) painelEsquerdo).getElementos();
                // atualizando as coordenadas manualmente
                elementos.forEach(elemento -> {
                    if (elemento instanceof Carro){
                        ((Carro)elemento).movimentaParaBaixo();
                    }
                });

                // devolvendo o foco para o painel Tela poder capturar as teclas
                ((Tela)painelEsquerdo).requestFocus();
            }
        });

        /**
         * Um pequeno exemplo para verificar se o usuário clicou sobre um Elemento que está desenhado na tela.
         *
         * No caso, é verificado se as coordenadas do clique (x,y), estão dentro da área retangular do elemento
         */
        painelEsquerdo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((Tela)painelEsquerdo).clicouSobreElemento(e.getX(), e.getY());
            }
        });
    }

    private void createUIComponents() {

        this.teclado = new Teclado();

        painelPrincipal = new JPanel();
        console = new JTextArea();

        painelEsquerdo = new Tela(teclado, console);
        painelEsquerdo.addKeyListener(teclado);
        painelPrincipal.addKeyListener(teclado);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulador J2D");
        frame.setContentPane(new Principal().painelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null); // centralizando o JFrame na tela
        frame.setResizable(false); // não permitindo que o JFrame seja redimensionado
        frame.setVisible(true);
    }
}
