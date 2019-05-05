package academy.devonline.temp.cycle;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example04Mediator {

    private Example04Mediator() {
    }

    public static void main(final String[] args) {
        final Mediator mediator = new Mediator();
        final Parent parent = new Parent(mediator, List.of(new Child(mediator), new Child(mediator)));
        for (final Child child : parent.getChildren()) {
            child.doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Mediator {

        private Parent parent;

        public Parent getParent() {
            return parent;
        }

        public void registerParent(final Parent parent) {
            this.parent = parent;
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Child {

        private final Mediator mediator;

        public Child(final Mediator mediator) {
            this.mediator = requireNonNull(mediator);
        }

        public void doSomething() {
            System.out.println("Child");
            mediator.getParent().doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Parent {

        private final Mediator mediator;

        private final List<Child> children;

        public Parent(final Mediator mediator,
                      final List<Child> children) {
            this.mediator = requireNonNull(mediator);
            this.children = List.copyOf(children);
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            this.mediator.registerParent(this);
        }

        public List<Child> getChildren() {
            return children;
        }

        public void doSomething() {
            System.out.println("Parent");
        }
    }
}
