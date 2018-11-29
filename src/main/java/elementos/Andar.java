package elementos;

import util.LerArquivo;

import java.util.ArrayList;
import java.util.HashMap;

public class Andar
{
    private HashMap<Integer, ArrayList<Pessoa>> pessoasInstante;
    private HashMap<Integer, Integer> pessoasAndar;

    public Andar(HashMap<Integer, ArrayList<Pessoa>> pessoasInstante)
    {
        this.pessoasInstante = pessoasInstante;
    }

//    public HashMap filaPorAndar(int instante)
//    {
//        pessoasAndar =  pessoasInstante[instante];
//
//    }
//
//    public HashMap ordernarFila()
}
