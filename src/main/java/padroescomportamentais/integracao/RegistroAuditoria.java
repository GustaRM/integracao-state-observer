package padroescomportamentais.integracao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroAuditoria implements ProcessoObserver {

    private final String nome;
    private final List<String> notificacoes = new ArrayList<>();

    public RegistroAuditoria(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(Processo processo, String estadoAnterior, String estadoNovo) {
        notificacoes.add("[AUDITORIA] " + processo.getNumero() + ": " + estadoAnterior + " -> " + estadoNovo);
    }

    public String getNome() {
        return nome;
    }

    public List<String> getNotificacoes() {
        return Collections.unmodifiableList(notificacoes);
    }

    public String getUltimaNotificacao() {
        return notificacoes.isEmpty() ? null : notificacoes.get(notificacoes.size() - 1);
    }
}
