package academy.devonline.temp.cycle;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03Supplier {

    private static Parent parent;

    private Example03Supplier() {
    }

    public static void main(final String[] args) {
        final Supplier<Parent> parentSupplier = () -> parent;
        parent = new Parent(List.of(new Child(parentSupplier), new Child(parentSupplier)));
        for (final Child child : parent.getChildren()) {
            child.doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Child {

        private final Supplier<Parent> parentSupplier;

        public Child(final Supplier<Parent> parentSupplier) {
            this.parentSupplier = parentSupplier;
        }

        public void doSomething() {
            System.out.println("Child");
            parentSupplier.get().doSomething();
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
        }

        public List<Child> getChildren() {
            return children;
        }

        public void doSomething() {
            System.out.println("Parent");
        }
    }
}
