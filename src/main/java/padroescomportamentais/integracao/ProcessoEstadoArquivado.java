package padroescomportamentais.integracao;

public class ProcessoEstadoArquivado extends ProcessoEstado {

    private static final ProcessoEstadoArquivado instance = new ProcessoEstadoArquivado();

    private ProcessoEstadoArquivado() {}

    public static ProcessoEstadoArquivado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Arquivado";
    }

}
