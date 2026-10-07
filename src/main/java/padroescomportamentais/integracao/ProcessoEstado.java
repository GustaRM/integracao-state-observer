package padroescomportamentais.integracao;

public abstract class ProcessoEstado {

    public abstract String getEstado();

    public boolean distribuir(Processo processo) {
        return false;
    }

    public boolean suspender(Processo processo) {
        return false;
    }

    public boolean retomar(Processo processo) {
        return false;
    }

    public boolean julgar(Processo processo) {
        return false;
    }

    public boolean recorrer(Processo processo) {
        return false;
    }

    public boolean arquivar(Processo processo) {
        return false;
    }
}
