/**
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * <p>
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * <p>
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * <p>
 * Prof. Emerson Ribeiro de Mello (2011)
 * mello@ifsc.edu.br
 */
package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Solução para trabalhar com múltiplas teclas pressionadas
 * http://www.gamedev.net/page/resources/_/technical/general-programming/java-games-keyboard-and-mouse-r2439
 *
 * @author Emerson Ribeiro de Mello - 2011
 */
public class Teclado implements KeyListener {

    private final int totalDeTeclas = 256;
    private boolean[] estadoAtualDoTeclado; // estado atual do teclado
    private EstadoDaTecla[] teclas; // estado de cada tecla


    public Teclado() {
        this.estadoAtualDoTeclado = new boolean[totalDeTeclas];
        this.teclas = new EstadoDaTecla[totalDeTeclas];

        // iniciando o estado de todas as teclas como soltas
        for (int i = 0; i < totalDeTeclas; i++) {
            teclas[i] = EstadoDaTecla.SOLTA;
        }

    }

    /**
     * Transfere o estado atual das teclas para o vetor de EstadoDaTecla
     */
    public synchronized void poll() {

        for (int i = 0; i < totalDeTeclas; ++i) {
            // Define o estado da tecla
            if (estadoAtualDoTeclado[i] == true) {
                // Se o estado atual é PRESSIONADA, mas nao estava
                // PRESSIONADA no ultimo quadro, entao defina com PRIMEIRAVEZ
                // SENAO, como PRESSIONADA
                if (teclas[i] == EstadoDaTecla.SOLTA) {
                    teclas[i] = EstadoDaTecla.PRIMEIRAVEZ;
                } else {
                    teclas[i] = EstadoDaTecla.PRESSIONADA;
                }
            } else {
                teclas[i] = EstadoDaTecla.SOLTA;
            }
        }
    }

    /**
     * Retorna true se a tecla não estiver como SOLTA
     * @param codigoDaTecla - codigo da tecla que sera' testada
     * @return
     */
    public boolean teclaApertada(int codigoDaTecla) {
        return teclas[codigoDaTecla] == EstadoDaTecla.PRIMEIRAVEZ
                || teclas[codigoDaTecla] == EstadoDaTecla.PRESSIONADA;
    }

    /**
     * Retorna true se a tecla estiver como PRIMEIRAVEZ
     * @param codigoDaTecla - codigo da tecla que sera' testada
     * @return
     */
    public boolean teclaApertadaPrimeiraVez(int codigoDaTecla) {
        return teclas[codigoDaTecla] == EstadoDaTecla.PRIMEIRAVEZ;
    }

    /**
     * Retorna TRUE se a tecla estiver SOLTA
     * @param codigoDaTecla
     * @return
     */
    public boolean teclaSolta(int codigoDaTecla) {
        return teclas[codigoDaTecla] == EstadoDaTecla.SOLTA;
    }

    /**
     * Esse método é invocado sempre que uma tecla é pressionada pela primeira vez
     * Porém, não é invocado se a tecla continuar pressionada
     * @param e evento da tecla
     */
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Deixa como true a tecla que foi apertada
        if ((keyCode >= 0) && (keyCode < totalDeTeclas)) {
            estadoAtualDoTeclado[keyCode] = true;
        }
    }

    /**
     * Esse método é invocado sempre que uma tecla for solta
     * @param e evento da tecla
     */
    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Deixa como false a tecla que foi solta
        if ((keyCode >= 0) && (keyCode < totalDeTeclas)) {
            estadoAtualDoTeclado[keyCode] = false;
        }
    }

    /**
     * Não será usada, mas teve que ser implementado pois essa classe implementa a
     * interface KeyListener
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // nao é usado
    }

    /**
     * Tipo Enum - Veja documentação oficial
     * https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
     */
    private enum EstadoDaTecla {

        SOLTA, // tecla solta
        PRESSIONADA, // tecla pressionada
        PRIMEIRAVEZ // pressionada pela 1a. vez
    }
}
