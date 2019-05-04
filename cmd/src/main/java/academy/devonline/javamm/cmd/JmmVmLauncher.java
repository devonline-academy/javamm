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

package academy.devonline.javamm.cmd;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.CompilerConfigurator;
import academy.devonline.javamm.interpreter.Interpreter;
import academy.devonline.javamm.interpreter.InterpreterConfigurator;

import java.io.IOException;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class JmmVmLauncher {

    private JmmVmLauncher() {
    }

    public static void main(final String[] args) throws IOException {
        final Compiler compiler = new CompilerConfigurator().getCompiler();
        final Interpreter interpreter = new InterpreterConfigurator().getInterpreter();
        final ByteCode byteCode = compiler.compile(new FileSourceCode("cmd/src/main/resources/test.javamm"));
        //System.out.println(byteCode.getCode());
        interpreter.interpret(byteCode);
    }
}
