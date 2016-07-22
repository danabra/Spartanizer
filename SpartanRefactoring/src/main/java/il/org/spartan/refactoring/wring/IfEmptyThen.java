package il.org.spartan.refactoring.wring;

import static il.org.spartan.refactoring.utils.Funcs.*;
import il.org.spartan.refactoring.preferences.*;
import il.org.spartan.refactoring.preferences.PluginPreferencesResources.WringGroup;
import il.org.spartan.refactoring.utils.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

/**
 * A {@link Wring} to convert <code>if (x) ; else {a;}</code> into <code>if (!x)
 * a;</code>.
 *
 * @author Yossi Gil
 * @since 2015-08-26
 */
public final class IfEmptyThen extends Wring.ReplaceCurrentNode<IfStatement> implements Kind.Simplify {
  @Override Statement replacement(final IfStatement s) {
    final IfStatement $ = Subject.pair(elze(s), null).toNot(s.getExpression());
    return !Is.blockRequiredInReplacement(s, $) ? $ : Subject.statement($).toBlock();
  }
  @Override boolean scopeIncludes(final IfStatement s) {
    return s != null && Is.vacuousThen(s) && !Is.vacuousElse(s);
  }
  @Override String description(final IfStatement s) {
    return "Invert conditional and remove vacuous 'then' branch of if(" + s.getExpression() + ") ...";
  }
  @Override public WringGroup kind() {
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void go(final ASTRewrite r, final TextEditGroup g) {
    // TODO Auto-generated method stub
  }
}