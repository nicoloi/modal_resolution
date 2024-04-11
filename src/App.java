// import literal.*;
// import clauses.*;
import static connective.Connective.*;
import formula.*;

public class App {
    public static void main(String[] args) {
        Formula p = new AtomicFormula("p");
        Formula q = new AtomicFormula("q");

        Formula phi1 = new CompoundFormula(AND, new CompoundFormula(BOX, p), 
            new CompoundFormula(BOX, q)); // #p & #q
        Formula phi2 = new CompoundFormula(BOX, new CompoundFormula(AND, p, q)); // #(p & q)
        Formula phi = new CompoundFormula(IMPLIES, phi1, phi2);

        Formula not_phi = new CompoundFormula(NOT, phi);

        System.out.println(not_phi + "\n");

        System.out.println(not_phi.toClauseSet());
    }
}



// public class App {
//     public static void main(String[] args) {

//         GlobalClause gc = new GlobalClause();
//         Clause gc2 = new GlobalClause();
//         LocalClause lc = new LocalClause();

//         gc.add(new PropAtom("a"));
//         gc.add(new NegPropAtom("b"));
//         gc.add(new ModalAtom("c"));

//         gc2.add(new NegPropAtom("a"));
//         gc2.add(new NegModalAtom("d"));

//         //System.out.println(gc.union(gc2));

//         lc.add(new ModalAtom("a"));
//         lc.add(new NegPropAtom("b"));

//         ClauseSet cs = new ClauseSet();
//         cs.add(gc);
//         cs.add(gc2);
//         cs.add(lc);

//         System.out.println(cs);
//     }
// }
