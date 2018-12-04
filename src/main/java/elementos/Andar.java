package elementos;

import gui.Tela;
import util.LerArquivo;

import java.util.ArrayList;
import java.util.HashMap;

public class Andar extends Elemento
{
    private HashMap<Integer, ArrayList<Integer>> pessoasAndar;
    private String[] figuras = {"azul.png","verde.png"};
    private int posicao;

    public Andar(Tela pai, String imagemNome, int posX, int posY)
    {
        super(pai, imagemNome, posX, posY);
        this.pessoasAndar = new HashMap<>();
    }

    public ArrayList<Integer> getPessoasAndar(int andar)
    {
        return pessoasAndar.get(andar);
    }

    public void setPessoasAndar(int andar, ArrayList<Integer> ListaPessoas)
    {
        this.pessoasAndar = pessoasAndar;
    }

    @Override
    public void atualizar()
    {
        posicao = (posicao + 1) % 2;
        this.icone = this.carregarImagem(figuras[posicao]);
    }
}
