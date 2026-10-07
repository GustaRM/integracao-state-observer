package padroescomportamentais.integracao;

public class ProcessoEstadoSuspenso extends ProcessoEstado {

    private static final ProcessoEstadoSuspenso instance = new ProcessoEstadoSuspenso();

    private ProcessoEstadoSuspenso() {}

    public static ProcessoEstadoSuspenso getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Suspenso";
    }

    @Override
    public boolean retomar(Processo processo) {
        processo.setEstado(ProcessoEstadoEmAndamento.getInstance());
        return true;
    }
}
