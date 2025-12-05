public class NotificacaoEmail implements Notificavel {
    private String emailDestino;

    public NotificacaoEmail(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("\n[📧 EMAIL para " + emailDestino + "]");
        System.out.println("   " + mensagem);
    }
}