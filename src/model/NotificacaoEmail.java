package model;

public class NotificacaoEmail implements Notificavel {
    private final String emailDestino;

    public NotificacaoEmail(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("[EMAIL para " + emailDestino + "]");
        System.out.println("   " + mensagem);
    }
}