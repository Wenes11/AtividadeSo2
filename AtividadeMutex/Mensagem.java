import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Mensagem {
    String tipo;
    String conteudo;

    Mensagem(String tipo, String conteudo) {
        this.tipo = tipo;
        this.conteudo = conteudo;
    }
}

class TrocaDeMensagens {
    private BlockingQueue<Mensagem> fila = new LinkedBlockingQueue<>();

    public void enviarMensagem(Mensagem mensagem) throws InterruptedException {
        fila.put(mensagem);
    }

    public Mensagem receberMensagem() throws InterruptedException {
        return fila.take();
    }
}
