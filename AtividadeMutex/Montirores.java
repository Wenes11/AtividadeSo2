class RecursoCompartilhado {
    private int leitores = 0;
    private boolean escritorAtivo = false;

    public synchronized void iniciarLeitura() throws InterruptedException {
        while (escritorAtivo) wait();
        leitores++;
    }

    public synchronized void terminarLeitura() {
        leitores--;
        if (leitores == 0) notifyAll();
    }

    public synchronized void iniciarEscrita() throws InterruptedException {
        while (escritorAtivo || leitores > 0) wait();
        escritorAtivo = true;
    }

    public synchronized void terminarEscrita() {
        escritorAtivo = false;
        notifyAll();
    }
}

class LeitorMonitor implements Runnable {
    private RecursoCompartilhado recurso;

    public LeitorMonitor(RecursoCompartilhado recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            recurso.iniciarLeitura();
            System.out.println(Thread.currentThread().getName() + " lendo...");
            Thread.sleep(1000);
            recurso.terminarLeitura();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EscritorMonitor implements Runnable {
    private RecursoCompartilhado recurso;

    public EscritorMonitor(RecursoCompartilhado recurso) {
        this.recurso = recurso;
    }

    @Override
    public void run() {
        try {
            recurso.iniciarEscrita();
            System.out.println(Thread.currentThread().getName() + " escrevendo...");
            Thread.sleep(1000);
            recurso.terminarEscrita();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
