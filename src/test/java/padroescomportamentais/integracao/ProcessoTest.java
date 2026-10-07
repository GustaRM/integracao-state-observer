package padroescomportamentais.integracao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessoTest {

    private Processo processo;
    private Advogado advogado;
    private Parte parte;
    private RegistroAuditoria auditoria;

    @BeforeEach
    void setUp() {
        processo = new Processo("0001234-56.2026.8.13.0702");
        advogado = new Advogado("Dra. Helena");
        parte = new Parte("Sr. Carlos");
        auditoria = new RegistroAuditoria("Auditoria");
        processo.adicionarObservador(advogado);
        processo.adicionarObservador(parte);
        processo.adicionarObservador(auditoria);
    }

    // ---------- State ----------

    @Test
    void estadoInicialEhProtocolado() {
        assertEquals("Protocolado", processo.getNomeEstado());
    }

    @Test
    void fluxoCompletoAteArquivamento() {
        assertTrue(processo.distribuir());
        assertEquals("Em andamento", processo.getNomeEstado());
        assertTrue(processo.julgar());
        assertEquals("Julgado", processo.getNomeEstado());
        assertTrue(processo.arquivar());
        assertEquals("Arquivado", processo.getNomeEstado());
    }

    @Test
    void suspenderERetomar() {
        processo.distribuir();
        assertTrue(processo.suspender());
        assertEquals("Suspenso", processo.getNomeEstado());
        assertTrue(processo.retomar());
        assertEquals("Em andamento", processo.getNomeEstado());
    }

    @Test
    void recursoReabreProcessoJulgado() {
        processo.distribuir();
        processo.julgar();
        assertTrue(processo.recorrer());
        assertEquals("Em andamento", processo.getNomeEstado());
    }

    @Test
    void transicoesInvalidasRetornamFalseEMantemEstado() {
        assertFalse(processo.julgar());      // Protocolado não pode ser julgado
        assertFalse(processo.suspender());
        assertFalse(processo.arquivar());
        assertEquals("Protocolado", processo.getNomeEstado());
    }

    @Test
    void processoArquivadoNaoAceitaNenhumaTransicao() {
        processo.distribuir();
        processo.julgar();
        processo.arquivar();
        assertFalse(processo.distribuir());
        assertFalse(processo.suspender());
        assertFalse(processo.retomar());
        assertFalse(processo.julgar());
        assertFalse(processo.recorrer());
        assertFalse(processo.arquivar());
        assertEquals("Arquivado", processo.getNomeEstado());
    }

    // ---------- Observer ----------

    @Test
    void semTransicaoNaoHaNotificacao() {
        assertTrue(advogado.getNotificacoes().isEmpty());
        assertNull(parte.getUltimaNotificacao());
    }

    @Test
    void transicaoValidaNotificaTodosOsObservadores() {
        processo.distribuir();

        assertEquals(1, advogado.getNotificacoes().size());
        assertEquals(1, parte.getNotificacoes().size());
        assertEquals(1, auditoria.getNotificacoes().size());

        assertEquals("Dra. Helena, o Processo 0001234-56.2026.8.13.0702 mudou de \"Protocolado\" para \"Em andamento\"",
                advogado.getUltimaNotificacao());
        assertEquals("Sr. Carlos, seu Processo 0001234-56.2026.8.13.0702 agora está: Em andamento",
                parte.getUltimaNotificacao());
        assertEquals("[AUDITORIA] 0001234-56.2026.8.13.0702: Protocolado -> Em andamento",
                auditoria.getUltimaNotificacao());
    }

    @Test
    void transicaoInvalidaNaoNotificaNinguem() {
        processo.julgar(); // inválida em Protocolado
        assertTrue(advogado.getNotificacoes().isEmpty());
        assertTrue(parte.getNotificacoes().isEmpty());
        assertTrue(auditoria.getNotificacoes().isEmpty());
    }

    @Test
    void cadaTransicaoGeraUmaNotificacaoNaOrdemCorreta() {
        processo.distribuir();
        processo.suspender();
        processo.retomar();
        processo.julgar();
        processo.arquivar();

        assertEquals(5, auditoria.getNotificacoes().size());
        assertEquals("[AUDITORIA] 0001234-56.2026.8.13.0702: Protocolado -> Em andamento",
                auditoria.getNotificacoes().get(0));
        assertEquals("[AUDITORIA] 0001234-56.2026.8.13.0702: Em andamento -> Suspenso",
                auditoria.getNotificacoes().get(1));
        assertEquals("[AUDITORIA] 0001234-56.2026.8.13.0702: Julgado -> Arquivado",
                auditoria.getNotificacoes().get(4));
    }

    @Test
    void observadorRemovidoNaoRecebeMaisNotificacoes() {
        processo.distribuir();
        processo.removerObservador(parte);
        processo.suspender();

        assertEquals(1, parte.getNotificacoes().size());
        assertEquals(2, advogado.getNotificacoes().size());
    }

    @Test
    void observadorNaoEhRegistradoDuasVezes() {
        processo.adicionarObservador(advogado);
        processo.distribuir();
        assertEquals(1, advogado.getNotificacoes().size());
        assertEquals(3, processo.getObservadores().size());
    }

    @Test
    void observadorAdicionadoDepoisSoRecebeNotificacoesFuturas() {
        processo.distribuir();
        Advogado novo = new Advogado("Dr. Paulo");
        processo.adicionarObservador(novo);
        processo.julgar();

        assertEquals(1, novo.getNotificacoes().size());
        assertTrue(novo.getUltimaNotificacao().contains("\"Julgado\""));
    }

    @Test
    void processosDiferentesTemObservadoresIndependentes() {
        Processo outro = new Processo("0009999-00.2026.8.13.0702");
        outro.distribuir();

        assertEquals("Em andamento", outro.getNomeEstado());
        assertEquals("Protocolado", processo.getNomeEstado());
        assertTrue(advogado.getNotificacoes().isEmpty());
    }
}
