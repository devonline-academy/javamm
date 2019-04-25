package academy.devonline.temp.nullobject;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example00Problem {

    private Example00Problem() {
    }

    public static void main(final String[] args) {

        final Service<String> service = null;

        final Supplier<String> supplier = service.getCurrentSupplier();
        if (supplier != null) {
            supplier.get();
        }
        // or
        supplier.get();
        //-------------------------------------------------------------------------
        final List<Supplier<String>> allSuppliers = service.getAllSuppliers();
        if (allSuppliers != null) {
            for (final Supplier<String> sup : allSuppliers) {
                sup.get();
            }
        }
        // or
        for (final Supplier<String> sup : allSuppliers) {
            sup.get();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public interface Service<T> {

        Supplier<T> getCurrentSupplier();

        List<Supplier<T>> getAllSuppliers();
    }
}
