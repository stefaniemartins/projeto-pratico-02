package elementos;

import util.LerArquivo;

import java.util.ArrayList;
import java.util.HashMap;

public class Andar
{
    private HashMap<Integer, ArrayList<Pessoa>> pessoasInstante;

    public Andar(HashMap<Integer, ArrayList<Pessoa>> pessoasInstante)
    {
        this.pessoasInstante = pessoasInstante;
    }

    public void filaPorAndar(int instante)
    {
        HashMap<Integer, ArrayList<Integer>> pessoasAndar = new HashMap<>();
        ArrayList<Pessoa> auxiliarPessoas =  pessoasInstante.get(instante);

        int origem, destino;

        for (int i = 0; i < auxiliarPessoas.size(); i++)
        {
            ArrayList<Integer> auxiliarDestino = new ArrayList<>();
            Pessoa pes = auxiliarPessoas.get(i);

            origem = pes.getOrigem();
            destino = pes.getDestino();

            auxiliarDestino.add(destino);
            pessoasAndar.put(origem,auxiliarDestino);
        }

        for (int j = 0; j < pessoasAndar.size(); j++)
        {
            ArrayList<Integer> aux1 = pessoasAndar.get(j);

            System.out.println(aux1.get(0));

//            int k;
//            for (k = 0; k < aux1.size(); k++)
//            {
//                System.out.println(" ori " + j + " dest " + k);
//            }
        }
    }

    //public HashMap ordernarFila()
}
