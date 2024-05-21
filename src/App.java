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
}