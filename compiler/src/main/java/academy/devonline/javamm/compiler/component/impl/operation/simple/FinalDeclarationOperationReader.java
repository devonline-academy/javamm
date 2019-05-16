package academy.devonline.javamm.compiler.component.impl.operation.simple;

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.VariableBuilder;

import static academy.devonline.javamm.code.syntax.Keywords.FINAL;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class FinalDeclarationOperationReader extends VariableDeclarationOperationReader {

    public FinalDeclarationOperationReader(final VariableBuilder variableBuilder,
                                           final ExpressionResolver expressionResolver) {
        super(variableBuilder, expressionResolver);
    }

    @Override
    public boolean canRead(final SourceLine sourceLine) {
        return FINAL.equals(sourceLine.getFirst());
    }

    @Override
    protected boolean isConstant() {
        return true;
    }
}
