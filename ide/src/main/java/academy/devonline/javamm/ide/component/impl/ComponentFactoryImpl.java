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

package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.code.component.Console;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.ide.component.AsyncSyntaxHighlighter;
import academy.devonline.javamm.ide.component.CodeTemplateHelper;
import academy.devonline.javamm.ide.component.CodeTemplateStorage;
import academy.devonline.javamm.ide.component.ComponentFactory;
import academy.devonline.javamm.ide.component.VirtualMachineRunner;
import academy.devonline.javamm.vm.VirtualMachine;
import academy.devonline.javamm.vm.VirtualMachineBuilder;
import org.fxmisc.richtext.CodeArea;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static academy.devonline.javamm.ide.model.CodeTemplate.CURSOR;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ComponentFactoryImpl implements ComponentFactory {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final CodeTemplateStorage codeTemplateStorage = new CodeTemplateStorageImpl.Builder()
        .addTemplate(format("function main() {\n\t%s\n}", CURSOR), "m", "ma", "mai", "main")
        .addTemplate(format("function %s() {\n\t\n}", CURSOR), "fn", "fu", "fun", "func", "function")

        .addTemplate(format("if(%s) {\n\t\n}", CURSOR), "i", "if")
        .addTemplate(format("if(%s) {\n\t\n}\nelse {\n\t\n}", CURSOR), "ifel", "ife", "ie")
        .addTemplate(format(
            "switch(%s) {\n\tcase 1:{\n\t\tbreak\n\t}\n\tcase 2:{\n\t\tbreak\n\t}\n\tdefault:{\n\t\t\n\t}\n}",
            CURSOR), "s", "sw", "swi", "swit", "switc", "switch")
        .addTemplate(format("default:{\n\t%s\n}", CURSOR), "de", "df", "def", "defa", "defau", "defaul", "default")
        .addTemplate(format("case %s:{\n\tbreak\n}", CURSOR), "ca", "cas", "case")
        .addTemplate(format("else {\n\t%s\n}", CURSOR), "e", "el", "els", "else")
        .addTemplate(format("while(%s) {\n\t\n}", CURSOR), "wh", "whi", "whil", "while")
        .addTemplate(format("do {\n\t\n}\nwhile(%s)", CURSOR), "do", "dw", "dowhile", "dwh", "dwhl", "dwl")
        .addTemplate(format("for(var i = 0; i < %s; i ++) {\n\t\n}", CURSOR), "fo", "for", "fr")
        .addTemplate("break", "br", "bre", "brea")
        .addTemplate("return ", "re", "rt", "ret", "retu", "retur")
        .addTemplate("continue", "co", "ct", "con", "cont", "conti", "contin", "continu")
        .addTemplate(format("println(%s)", CURSOR), "p", "pr", "pri", "prin", "print", "printl", "println")
        .addTemplate(format("var %s = ", CURSOR), "v", "va", "var")
        .addTemplate(format("final %s = ", CURSOR), "fi", "fl", "fin", "fina", "final")

        .addTemplate("null", "n", "nu", "nul")
        .addTemplate("true", "t", "tr", "tru")
        .addTemplate("false", "f", "fa", "fal", "fals")
        .addTemplate(format("%s typeof ", CURSOR), "ty", "tp", "tf", "typ", "type", "typeo", "typeof")
        .addTemplate(format("%s typeof integer", CURSOR), "tyi", "tpi", "tfi", "typi", "typei")
        .addTemplate(format("%s typeof string", CURSOR), "tys", "tps", "tfs", "typs", "types")
        .addTemplate(format("%s typeof double", CURSOR), "tyd", "tpd", "tfd", "typd", "typed")
        .addTemplate(format("%s typeof boolean", CURSOR), "tyb", "tpb", "tfb", "typb", "typeb")
        .addTemplate(format("%s typeof void", CURSOR), "tyv", "tpv", "tfv", "typv", "typev")
        .build();

    private final CodeTemplateHelper codeTemplateHelper = new CodeTemplateHelperImpl(codeTemplateStorage);

    @Override
    public AsyncSyntaxHighlighter createAsyncSyntaxHighlighter(final CodeArea codeArea) {
        return new AsyncSyntaxHighlighterImpl(codeArea, executorService);
    }

    @Override
    public VirtualMachineRunner createVirtualMachineRunner(final Console console,
                                                           final List<SourceCode> sourceCodes) {
        final VirtualMachine virtualMachine = new VirtualMachineBuilder()
            .setConsole(console)
            .build();
        return new VirtualMachineRunnerImpl(console, virtualMachine, sourceCodes);
    }

    @Override
    public CodeTemplateHelper getCodeTemplateHelper() {
        return codeTemplateHelper;
    }

    @Override
    public void release() {
        executorService.shutdownNow();
    }
}
