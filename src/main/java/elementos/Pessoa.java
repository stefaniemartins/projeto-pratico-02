package elementos;

import java.util.ArrayList;

public class Pessoa extends ArrayList<Pessoa>
{
    private int origem;
    private int destino;

    public Pessoa(int origem, int destino)
    {
        this.origem = origem;
        this.destino = destino;
    }

    public int getOrigem()
    {
        return origem;
    }

    public int getDestino()
    {
        return destino;
    }
}