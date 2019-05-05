package academy.devonline.temp.cycle;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02Setter {

    private Example02Setter() {
    }

    public static void main(final String[] args) {
        final Parent parent = new Parent(List.of(new Child(), new Child()));
        for (final Child child : parent.getChildren()) {
            child.doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Child {

        private Parent parent;

        public void doSomething() {
            System.out.println("Child");
            parent.doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Parent {

        private final List<Child> children;

        public Parent(final List<Child> children) {
            this.children = List.copyOf(children);
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            for (final Child child : this.children) {
                child.parent = this;
            }
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

        public List<Child> getChildren() {
            return children;
        }

        public void doSomething() {
            System.out.println("Parent");
        }
    }
}
