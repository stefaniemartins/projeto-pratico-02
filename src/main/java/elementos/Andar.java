package elementos;

import util.LerArquivo;

import java.util.ArrayList;
import java.util.HashMap;

public class Andar
{
    private HashMap<Integer, ArrayList<Integer>> pessoasAndar;

    public Andar(HashMap<Integer, ArrayList<Integer>> pessoasAndar)
    {
        this.pessoasAndar = pessoasAndar;
    }

    public HashMap<Integer, ArrayList<Integer>> getPessoasAndar()
    {
        return pessoasAndar;
    }

    public void setPessoasAndar(HashMap<Integer, ArrayList<Integer>> pessoasAndar)
    {
        this.pessoasAndar = pessoasAndar;
    }
}
