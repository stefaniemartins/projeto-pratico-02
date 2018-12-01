package elementos;

import gui.Tela;
import util.LerArquivo;

import java.util.ArrayList;
import java.util.HashMap;

public class Andar extends Elemento
{
    private HashMap<Integer, ArrayList<Integer>> pessoasAndar;

    public Andar(Tela pai, String imagemNome, int posX, int posY)
    {
        super(pai, imagemNome, posX, posY);
        this.pessoasAndar = new HashMap<>();
    }

    public HashMap<Integer, ArrayList<Integer>> getPessoasAndar()
    {
        return pessoasAndar;
    }

    public void setPessoasAndar(HashMap<Integer, ArrayList<Integer>> pessoasAndar)
    {
        this.pessoasAndar = pessoasAndar;
    }

    @Override
    public void atualizar()
    {

    }
}
