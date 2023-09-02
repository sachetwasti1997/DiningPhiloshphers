import java.util.Random;

public class Philosopher implements Runnable{

    private int id;
    private ChopSticks leftChopStick;
    private ChopSticks rightChopSticks;
    private Random random;
    private boolean full;
    private int eatingCount;

    public Philosopher(int id, ChopSticks leftChopStick, ChopSticks rightChopSticks) {
        this.id = id;
        this.leftChopStick = leftChopStick;
        this.rightChopSticks = rightChopSticks;
        eatingCount = 0;
        full = false;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!full) {
                think();
                if (leftChopStick.pickUp(this, State.LEFT)) {
                    if (rightChopSticks.pickUp(this, State.RIGHT)) {
                        eat();
                        rightChopSticks.putDown(this, State.RIGHT);
                    }
                    leftChopStick.putDown(this, State.LEFT);
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Philosopher is going to think for time range in between [0, 1000]
     */
    private void think() throws InterruptedException {
        System.out.println(this+" is thinking...");
        Thread.sleep(random.nextInt(0,1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(this+" is eating...");
        eatingCount++;
        Thread.sleep(random.nextInt(0,1000));
    }

    private boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public int getEatingCount() {
        return eatingCount;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
