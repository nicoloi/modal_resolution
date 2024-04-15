import java.util.Scanner;
import clauses.*;
// import static connective.Connective.*;
import antlr4.ParseFormula;
import formula.*;

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

        ClauseSet reduction = f.toClauseSet();

        System.out.println("\nThe corresponding clause set is:");
        System.out.println(reduction);
        System.out.println();
    }
}