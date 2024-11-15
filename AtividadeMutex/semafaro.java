import java.util.concurrent.Semaphore;

class LeitoresEscritoresSemaforos {
    private static Semaphore mutex = new Semaphore(1); // Controle para leitores
    private static Semaphore writeLock = new Semaphore(1); // Controle para escritores
    private static int readCount = 0;

    static class Leitor implements Runnable {
        @Override
        public void run() {
            try {
                mutex.acquire();
                readCount++;
                if (readCount == 1) writeLock.acquire(); // Primeiro leitor bloqueia escritores
                mutex.release();

                System.out.println(Thread.currentThread().getName() + " lendo...");
                Thread.sleep(1000);

                mutex.acquire();
                readCount--;
                if (readCount == 0) writeLock.release(); // Último leitor libera escritores
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Escritor implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();
                System.out.println(Thread.currentThread().getName() + " escrevendo...");
                Thread.sleep(1000);
                writeLock.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread[] leitores = new Thread[5];
        Thread[] escritores = new Thread[2];

        for (int i = 0; i < leitores.length; i++) {
            leitores[i] = new Thread(new Leitor(), "Leitor " + (i + 1));
        }

        for (int i = 0; i < escritores.length; i++) {
            escritores[i] = new Thread(new Escritor(), "Escritor " + (i + 1));
        }

        for (Thread leitor : leitores) leitor.start();
        for (Thread escritor : escritores) escritor.start();
    }
}
