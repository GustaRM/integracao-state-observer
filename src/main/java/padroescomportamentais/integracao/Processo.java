package padroescomportamentais.integracao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Processo {

    private final String numero;
    private ProcessoEstado estado;
    private final List<ProcessoObserver> observadores = new ArrayList<>();

    public Processo(String numero) {
        this.numero = numero;
        this.estado = ProcessoEstadoProtocolado.getInstance();
    }

    // ---------- Observer (Subject) ----------

    public void adicionarObservador(ProcessoObserver observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void removerObservador(ProcessoObserver observador) {
        observadores.remove(observador);
    }

    public List<ProcessoObserver> getObservadores() {
        return Collections.unmodifiableList(observadores);
    }

    private void notificarObservadores(String estadoAnterior, String estadoNovo) {
        // cópia defensiva: um observador pode se remover durante a notificação
        for (ProcessoObserver o : new ArrayList<>(observadores)) {
            o.atualizar(this, estadoAnterior, estadoNovo);
        }
    }

    // ---------- State (Context) ----------

    /** Visibilidade de pacote: só os estados concretos trocam o estado. */
    void setEstado(ProcessoEstado novoEstado) {
        String anterior = this.estado.getEstado();
        this.estado = novoEstado;
        notificarObservadores(anterior, novoEstado.getEstado());
    }

    public boolean distribuir() {
        return estado.distribuir(this);
    }

    public boolean suspender() {
        return estado.suspender(this);
    }

    public boolean retomar() {
        return estado.retomar(this);
    }

    public boolean julgar() {
        return estado.julgar(this);
    }

    public boolean recorrer() {
        return estado.recorrer(this);
    }

    public boolean arquivar() {
        return estado.arquivar(this);
    }

    public String getNomeEstado() {
        return estado.getEstado();
    }

    public ProcessoEstado getEstado() {
        return estado;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Processo " + numero;
    }
}
