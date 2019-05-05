package academy.devonline.temp.creational_patterns;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example07SingletonLazy {

    private static Example07SingletonLazy INSTANCE;

    private Example07SingletonLazy() {
    }

    public static Example07SingletonLazy getInstance1() {
        if (INSTANCE == null) {
            INSTANCE = new Example07SingletonLazy();
        }
        return INSTANCE;
    }

    public static Example07SingletonLazy getInstance2() {
        if (INSTANCE == null) {
            synchronized (Example07SingletonLazy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Example07SingletonLazy();
                }
            }
        }
        return INSTANCE;
    }
}
