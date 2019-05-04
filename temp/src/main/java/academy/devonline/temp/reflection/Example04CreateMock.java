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

package academy.devonline.temp.reflection;

import academy.devonline.javamm.code.fragment.SourceCode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example04CreateMock {

    private Example04CreateMock() {
    }

    public static void main(final String[] args) {

        final SourceCode sourceCode = (SourceCode) Proxy.newProxyInstance(
            SourceCode.class.getClassLoader(),
            new Class[] {SourceCode.class},
            new InvocationHandler() {
                private final Object monitor = new Object();

                @Override
                public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                    switch (method.getName()) {
                        case "getModuleName":
                            return "ProxyModuleName";
                        case "getLines":
                            return List.of(
                                "function main(){",
                                "println('Hello world')",
                                "}");
                        case "equals":
                            return proxy == args[0];
                        case "hashCode":
                            return System.identityHashCode(proxy);
                        case "toString":
                            return "SourceCode proxy";
                        default:
                                /*
                                protected finalize() throws java.lang.Throwable
                                public wait(long) throws java.lang.InterruptedException
                                public wait(long, int) throws java.lang.InterruptedException
                                public wait() throws java.lang.InterruptedException
                                protected clone() throws java.lang.CloneNotSupportedException
                                public notify()
                                public notifyAll()
                                 */
                            return method.invoke(monitor, args);
                    }
                }
            });

        System.out.println(sourceCode.getModuleName());
        System.out.println(sourceCode.getLines());
        System.out.println(sourceCode.hashCode());
        System.out.println(sourceCode.equals(sourceCode));
        System.out.println(sourceCode.toString());
        System.out.println(sourceCode.getClass());
    }
}
