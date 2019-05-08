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

package academy.devonline.javamm.code.fragment.function;

import academy.devonline.javamm.code.fragment.FunctionName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AbstractFunctionName_UnitTest {

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
        "f(a,b,c);                                                          3",
        "f(a,b,c,d,e,f,g,h,i,j);                                            10",
        "f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,a,b,c,d);    30",
        "f(a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,a,b,c,d," +
            "e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,a);                53"
    })
    @Order(1)
    void toString_should_generate_an_infinite_count_of_fake_params_using_the_English_alphabet(final String expectedValue,
                                                                                              final int argumentCount) {
        assertEquals(expectedValue, new AbstractFunctionNameImpl("f", argumentCount).toString());
    }

    @Test
    @Order(2)
    void Should_be_comparable_using_unique_names_if_AbstractFunctionName_compares_with_AbstractFunctionName() {
        final FunctionName functionName1 = new AbstractFunctionNameImpl("function1", 2);
        final FunctionName functionName2 = new AbstractFunctionNameImpl("function1", 2);
        final FunctionName functionName3 = new AbstractFunctionNameImpl("function1", 3);

        assertEquals(functionName1.hashCode(), functionName2.hashCode());
        assertEquals(functionName1, functionName2);
        assertEquals(0, functionName1.compareTo(functionName2));

        assertNotEquals(functionName1, functionName3);
        assertNotEquals(0, functionName1.compareTo(functionName3));
    }

    @Test
    @Order(3)
    void Should_be_comparable_using_names_if_AbstractFunctionName_compares_with_FunctionName() {
        final FunctionName functionName1 = new AbstractFunctionNameImpl("function1", 2);
        final FunctionName functionName2 = new FunctionNameImpl("function1");
        final FunctionName functionName3 = new FunctionNameImpl("function2");

        assertEquals(functionName1.hashCode(), functionName2.hashCode());
        assertEquals(functionName1, functionName2);
        assertEquals(0, functionName1.compareTo(functionName2));

        assertNotEquals(functionName1, functionName3);
        assertNotEquals(0, functionName1.compareTo(functionName3));
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class AbstractFunctionNameImpl extends AbstractFunctionName {

        private AbstractFunctionNameImpl(final String name, final int argumentCount) {
            super(name, argumentCount);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class FunctionNameImpl implements FunctionName {

        private final String name;

        private FunctionNameImpl(final String name) {
            this.name = requireNonNull(name);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean equals(final Object o) {
            return getName().equals(((FunctionName) o).getName());
        }

        @Override
        public int hashCode() {
            return getName().hashCode();
        }

        @Override
        public int compareTo(final FunctionName o) {
            return name.compareTo(o.getName());
        }
    }
}