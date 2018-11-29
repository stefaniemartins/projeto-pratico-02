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

        ArrayList<Integer> auxiliarDestino = new ArrayList<>();

        for (int i = 0; i < auxiliarPessoas.size(); i++)
        {
            Pessoa pes = auxiliarPessoas.get(i);

            origem = pes.getOrigem();
            destino = pes.getDestino();

            auxiliarDestino.add(destino);
            System.out.println(auxiliarDestino.get(i));
            pessoasAndar.put(origem,auxiliarDestino);
        }

        System.out.println(pessoasAndar.size());

        ArrayList<Integer> aux1 = new ArrayList<>();

        for (int j = 0; j < pessoasAndar.size(); j++)
        {
            if (pessoasAndar.containsKey(j))
            {
                aux1.addAll(pessoasAndar.get(j));
            }


//
//            System.out.println(aux1.get(1));
//
//            for (int k = 0; k < aux1.size(); k++)
//            {
//                System.out.println(" ori " + j + " dest " + k);
//            }
        }
    }

    //public HashMap ordernarFila()
}
