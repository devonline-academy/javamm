/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
