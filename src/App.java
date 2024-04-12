// import literal.*;
// import clauses.*;
import static connective.Connective.*;
import formula.*;

public class App {
    public static void main(String[] args) {
        Formula a = new AtomicFormula("a");
        Formula b = new AtomicFormula("b");

        Formula phi = new CompoundFormula(BOX, new CompoundFormula(AND, a, b)); // #(p | q)

        // Formula not_phi = new CompoundFormula(NOT, phi);

        System.out.println(phi + "\n");

        System.out.println(phi.toClauseSet());
    }
}