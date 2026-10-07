package padroescomportamentais.integracao;

public class ProcessoEstadoJulgado extends ProcessoEstado {

    private static final ProcessoEstadoJulgado instance = new ProcessoEstadoJulgado();

    private ProcessoEstadoJulgado() {}

    public static ProcessoEstadoJulgado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Julgado";
    }

    @Override
    public boolean recorrer(Processo processo) {
        processo.setEstado(ProcessoEstadoEmAndamento.getInstance());
        return true;
    }

    @Override
    public boolean arquivar(Processo processo) {
        processo.setEstado(ProcessoEstadoArquivado.getInstance());
        return true;
    }
}
