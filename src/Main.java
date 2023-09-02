import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = null;
        Philosopher[] philosophers = null;
        ChopSticks[] chopSticks = null;

        try {
            philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
            chopSticks = new ChopSticks[Constants.NUMBER_OF_CHOPSTICKS];

            for (int i=0; i<Constants.NUMBER_OF_CHOPSTICKS; i++) {
                chopSticks[i] = new ChopSticks(i);
            }
            service = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);
            for (int i=0; i<Constants.NUMBER_OF_PHILOSOPHERS; i++) {
                philosophers[i] = new Philosopher(i, chopSticks[i],
                        chopSticks[(i+1) % Constants.NUMBER_OF_CHOPSTICKS]);
                service.execute(philosophers[i]);
            }

            Thread.sleep(Constants.SIMULATION_RUNNING_TIME);

            for (Philosopher philosopher: philosophers) {
                philosopher.setFull(true);
            }
        } finally {
            assert service != null;
            service.shutdown();
            while (!service.isTerminated()) {
                Thread.sleep(1000);
            }
            for (Philosopher philosopher: philosophers) {
                System.out.println(philosopher+" eats #"+philosopher.getEatingCount()+" times");
            }
        }
    }
}