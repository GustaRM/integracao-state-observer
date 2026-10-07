package padroescomportamentais.integracao;

public class ProcessoEstadoProtocolado extends ProcessoEstado {

    private static final ProcessoEstadoProtocolado instance = new ProcessoEstadoProtocolado();

    private ProcessoEstadoProtocolado() {}

    public static ProcessoEstadoProtocolado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Protocolado";
    }

    @Override
    public boolean distribuir(Processo processo) {
        processo.setEstado(ProcessoEstadoEmAndamento.getInstance());
        return true;
    }
}
