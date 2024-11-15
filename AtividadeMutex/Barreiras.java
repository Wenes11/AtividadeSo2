import java.util.concurrent.CyclicBarrier;

class LeitoresEscritoresBarreira {
    private static CyclicBarrier barreira = new CyclicBarrier(2, () -> System.out.println("Barreira alcançada!"));

    public static void main(String[] args) {
        Runnable leitor = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " pronto para ler.");
                barreira.await();
                System.out.println(Thread.currentThread().getName() + " lendo...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable escritor = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " pronto para escrever.");
                barreira.await();
                System.out.println(Thread.currentThread().getName() + " escrevendo...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(leitor, "Leitor").start();
        new Thread(escritor, "Escritor").start();
    }
}
