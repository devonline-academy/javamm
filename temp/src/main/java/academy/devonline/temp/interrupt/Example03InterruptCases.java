package academy.devonline.temp.interrupt;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03InterruptCases {

    private Example03InterruptCases() {
    }

    public static void main(final String[] args) throws InterruptedException {
        final List<Thread> threads = List.of(
            new Thread(() -> {
                long sum = 0;
                while (!Thread.interrupted()) {
                    sum += System.currentTimeMillis() / 1_000_000;
                }
                System.out.println("Interrupted 1");
            }),
            new Thread(() -> {
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (final InterruptedException e) {
                    // do nothing
                }
                System.out.println("Interrupted 2");
            }),
            new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    try {
                        wait();
                    } catch (final InterruptedException e) {
                        // do nothing
                    }
                    System.out.println("Interrupted 3");
                }
            }),
            new Thread(() -> {
                long sum = 0;
                while (!Thread.interrupted()) {
                    sum += System.currentTimeMillis() / 1_000_000;
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (final InterruptedException e) {
                        break;
                    }
                }
                System.out.println("Interrupted 4");
            })
        );

        threads.forEach(Thread::start);
        System.out.println("Started " + threads.size() + " threads");

        TimeUnit.SECONDS.sleep(1);

        threads.forEach(Thread::interrupt);
        System.out.println("Send interrupt signal");
    }
}
