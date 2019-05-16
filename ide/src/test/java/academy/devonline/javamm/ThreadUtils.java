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

package academy.devonline.javamm;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ThreadUtils {

    private static final long WAIT_FOR_THREAD_TO_DIE_TIMEOUT_IN_MILLIS = 2500L;

    private ThreadUtils() {
    }

    public static void waitForThreadToDie(final Thread thread) throws InterruptedException {
        thread.join(WAIT_FOR_THREAD_TO_DIE_TIMEOUT_IN_MILLIS);
        if (thread.isAlive()) {
            fail("Thread still alive.");
        }
    }
}
