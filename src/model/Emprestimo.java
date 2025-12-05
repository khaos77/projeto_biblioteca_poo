import java.util.Date;
import java.text.SimpleDateFormat;

public class Emprestimo {
    private Usuario usuario;
    private ItemBiblioteca item;
    private Date dataEmprestimo;
    private Date dataPrevista;
    private Date dataDevolucao;
    private boolean finalizado = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private double multa = 0;

    public Emprestimo(Usuario usuario, ItemBiblioteca item) {
        this.usuario = usuario;
        this.item = item;
        this.dataEmprestimo = new Date();
        
        long dias = item.getDiasEmprestimo();
        this.dataPrevista = new Date(this.dataEmprestimo.getTime() + (dias * 24 * 60 * 60 * 1000));
        
        item.setDisponivel(false);
        usuario.adicionarEmprestimo(this);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ItemBiblioteca getItem() {
        return item;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public double getMulta() {
        return multa;
    }

    public void devolver(Date dataDev) {
        this.dataDevolucao = dataDev;
        this.finalizado = true;
        this.item.setDisponivel(true);

        long diff = dataDev.getTime() - dataPrevista.getTime();
        long diasAtraso = diff / (1000 * 60 * 60 * 24);
        
        if (diasAtraso > 0) {
            double multaBase = diasAtraso * item.calcularMultaPorDia();
            // Aplica desconto do tipo de usuário
            multa = usuario.aplicarDescontoMulta(multaBase);
        }
    }

    @Override
    public String toString() {
        String texto = item.getTitulo() + " | Empréstimo: " + sdf.format(dataEmprestimo) + " | Previsão: " + sdf.format(dataPrevista);
        if (finalizado) {
            texto += " | Devolvido: " + sdf.format(dataDevolucao);
            if (multa > 0) {
                texto += " | Multa: R$ " + String.format("%.2f", multa);
            }
        } else {
            texto += " | [EM ABERTO]";
        }
        return texto;
    }
}