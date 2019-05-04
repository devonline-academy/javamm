package academy.devonline.temp.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01CorrectThreadImplementation {

    private Example01CorrectThreadImplementation() {
    }

    public static void main(final String[] args) throws InterruptedException {
        final Thread thread = new Thread(() -> {
            long sum = 0;
            while (!Thread.interrupted()) {
                sum += 1;
            }
            System.out.println("Interrupted: " + sum);
        });

        thread.start();
        System.out.println("Started");

        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
        System.out.println("Send interrupt signal");
    }
}
