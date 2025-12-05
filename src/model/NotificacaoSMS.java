public class NotificacaoSMS implements Notificavel {
    private String telefone;

    public NotificacaoSMS(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void notificar(String mensagem) {
        System.out.println("\n[📱 SMS para " + telefone + "]");
        System.out.println("   " + mensagem);
    }
}