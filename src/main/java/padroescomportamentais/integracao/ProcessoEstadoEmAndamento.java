package padroescomportamentais.integracao;

public class ProcessoEstadoEmAndamento extends ProcessoEstado {

    private static final ProcessoEstadoEmAndamento instance = new ProcessoEstadoEmAndamento();

    private ProcessoEstadoEmAndamento() {}

    public static ProcessoEstadoEmAndamento getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Em andamento";
    }

    @Override
    public boolean suspender(Processo processo) {
        processo.setEstado(ProcessoEstadoSuspenso.getInstance());
        return true;
    }

    @Override
    public boolean julgar(Processo processo) {
        processo.setEstado(ProcessoEstadoJulgado.getInstance());
        return true;
    }
}
