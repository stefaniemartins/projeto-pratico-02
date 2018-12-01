package elementos;

public class Contador
{
    private int valor = 0;

    public int inc(int valor)
    {
        valor = valor + 1;
        return valor;
    }

    public int getValor()
    {
        return valor;
    }
}
