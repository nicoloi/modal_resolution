import java.util.Scanner;
import clauses.*;
import antlr4.ParseFormula;
import formula.*;
import static connective.Connective.*;
import resolution.Resolution;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String formulaStr = sc.nextLine();
        sc.close();

        Formula f = ParseFormula.parse(formulaStr);

        if (f == null) {
            System.out.println("\nYour formula in input is not a well-formed formula");
            return;
        }

        System.out.println("\nYour formula in input:");
        System.out.println(f);

        //negate formula f
        Formula not_f = new CompoundFormula(NOT, f);

        ClauseSet reduction = not_f.toClauseSet();

        System.out.println("\nThe corresponding clause set of the negation is:");
        System.out.println(reduction);
        System.out.println();

        if (args.length != 0 && args[0].equals("-v")) {
            Resolution.setEnableSteps(true);
        }

        if (Resolution.isSatisfiable(reduction)) {
            System.out.println("The formula is NOT a tautology");
        } else {
            System.out.println("The formula is a tautology");
        }
    }

    // public static void main(String[] args) {
    //     Clause c1 = new LocalClause(); //{a, b, #c}
    //     c1.add(new PropAtom("a"));
    //     c1.add(new PropAtom("b"));
    //     c1.add(new ModalAtom("c"));

    //     Clause c2 = new LocalClause(); //{d, e, ~#f}
    //     c2.add(new PropAtom("d"));
    //     c2.add(new PropAtom("e"));
    //     c2.add(new NegModalAtom("f"));

    //     GlobalClause c = new GlobalClause();
    //     c.add(new NegPropAtom("c"));
    //     c.add(new PropAtom("f"));

    //     GlobalClause c_prime = new GlobalClause();
    //     c_prime.add(new PropAtom("c"));
    //     // c_prime.add(new NegPropAtom("f"));

    //     ClauseSet cs = new ClauseSet();
    //     cs.add(c1);
    //     cs.add(c2);
    //     cs.add(c);
    //     cs.add(c_prime);

    //     System.out.println("Prima clausola: " + c1);
    //     System.out.println("Seconda clausola: " + c2);
    //     System.out.println("C: " + c);
    //     System.out.println("C primo: " + c_prime);
    //     System.out.println();

    //     Resolution.setEnableSteps(true);

    //     System.out.println(Resolution.isSatisfiable(cs));
    // }
}