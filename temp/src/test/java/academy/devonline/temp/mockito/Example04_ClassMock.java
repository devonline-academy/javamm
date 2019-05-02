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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@ExtendWith(MockitoExtension.class)
@Disabled
class Example04_ClassMock {

    @Mock
    private ArrayList<Integer> list;

    @Test
    void class_mock_example() {

        list.add(1);
        list.add(2);

        assertEquals(0, list.size());

        verify(list, times(2)).add(any());
    }
}
