package util;

import elementos.Pessoa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LerArquivo
{
    private HashMap<Integer, ArrayList<Pessoa>> vetorPessoas;
    private Pessoa pessoa;

    public LerArquivo(String nomeArq)
    {
        vetorPessoas = new HashMap();

        try {
            FileReader arq = new FileReader(nomeArq);
            BufferedReader lerArq = new BufferedReader(arq);

            // lê a primeira linha a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            String linha = lerArq.readLine();

            while (linha != null)
            {
                ArrayList<Pessoa> listaPessoas = new ArrayList<>();

                String[] separacao = linha.split(",");

                String[] inst = separacao[0].split(":");
                int instante = Integer.parseInt(inst[1]);

                for (int i = 1; i < separacao.length; i++) {
                    String[] origemDestino = separacao[i].split(":");

                    int origem = Integer.parseInt(origemDestino[1]);
                    int destino = Integer.parseInt(origemDestino[2]);

                    Pessoa pessoa = new Pessoa(origem, destino);

                    listaPessoas.add(pessoa);
                    vetorPessoas.put(instante, listaPessoas);
                }



                linha = lerArq.readLine(); // lê da segunda até a última linha
            }

            for (int i = 0; i < vetorPessoas.size(); i++)
            {
                ArrayList teste;

                teste = vetorPessoas.get(i);
                System.out.println("\n Chave: " + i);

                for (int j = 1; j < teste.size(); j++)
                {
                    System.out.println(teste.get(j));
                }
            }

            arq.close();
        }

        catch(IOException e)
        {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
}
