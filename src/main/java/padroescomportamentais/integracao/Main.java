package padroescomportamentais.integracao;

public class Main {

    public static void main(String[] args) {
        Processo processo = new Processo("0001234-56.2026.8.13.0702");

        Advogado advogado = new Advogado("Dra. Helena");
        Parte parte = new Parte("Sr. Carlos");
        RegistroAuditoria auditoria = new RegistroAuditoria("Auditoria");

        processo.adicionarObservador(advogado);
        processo.adicionarObservador(parte);
        processo.adicionarObservador(auditoria);

        processo.distribuir();
        processo.suspender();
        processo.retomar();
        processo.julgar();
        processo.arquivar();

        advogado.getNotificacoes().forEach(System.out::println);
        System.out.println("---");
        parte.getNotificacoes().forEach(System.out::println);
        System.out.println("---");
        auditoria.getNotificacoes().forEach(System.out::println);
    }
}
