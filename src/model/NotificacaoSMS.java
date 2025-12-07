package model;
public class NotificacaoSMS implements Notificavel {
    private final String telefone;

    public NotificacaoSMS(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println();
        System.out.println("[SMS para " + telefone + "]");
        System.out.println("   " + mensagem);
    }
}