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

package academy.devonline.temp.mockito;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@Disabled
@ExtendWith(MockitoExtension.class)
class Example00_Mock {

    @Mock
    private List<Integer> list1;

    @Mock
    private List<Integer> list2;

    @Test
    void test() {
        System.out.println(list1 + " -> " + list1.getClass());
        System.out.println(list2 + " -> " + list2.getClass());
    }

}
