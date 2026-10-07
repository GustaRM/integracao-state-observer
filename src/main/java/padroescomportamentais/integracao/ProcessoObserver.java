package padroescomportamentais.integracao;

public interface ProcessoObserver {

    void atualizar(Processo processo, String estadoAnterior, String estadoNovo);
}
