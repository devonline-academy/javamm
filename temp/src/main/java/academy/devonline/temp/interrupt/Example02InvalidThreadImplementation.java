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

package academy.devonline.temp.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02InvalidThreadImplementation {

    private Example02InvalidThreadImplementation() {
    }

    public static void main(final String[] args) throws InterruptedException {
        final Thread thread = new Thread(() -> {
            long sum = 0;
            while (true) {
                sum += 1;
            }
        });

        thread.start();
        System.out.println("Started");

        TimeUnit.SECONDS.sleep(1);

        thread.interrupt();
        System.out.println("Send interrupt signal");
    }
}
