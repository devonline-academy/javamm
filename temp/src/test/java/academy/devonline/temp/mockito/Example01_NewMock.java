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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@Disabled
@ExtendWith(MockitoExtension.class)
class Example01_NewMock {

    @Mock(
        stubOnly = true,
        answer = Answers.RETURNS_DEFAULTS,
        extraInterfaces = {Cloneable.class},
        serializable = true
    )
    private List<Integer> list1;

    @Mock
    private List<Integer> list2;

    private List<Integer> list3 = Mockito.mock(List.class);

    private List<Integer> list4;

    @BeforeEach
    void beforeEach() {
        list4 = Mockito.mock(List.class, Mockito.withSettings().name("qwerty"));
    }

    @Test
    void test() {
        final List<Integer> list5 = Mockito.mock(List.class);

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
        System.out.println(list5);
    }
}
