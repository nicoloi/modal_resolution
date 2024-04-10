// import literal.*;
// import clauses.*;
import static connective.Connective.*;
import formula.*;

public class App {
    public static void main(String[] args) {
        Formula a = new AtomicFormula("a");
        Formula b = new AtomicFormula("b");
        CompoundFormula cp1 = new CompoundFormula(OR, new CompoundFormula(NOT, a), b);
        CompoundFormula cp2 = new CompoundFormula(OR, b, new CompoundFormula(NOT, a));
        CompoundFormula or = new CompoundFormula(AND, cp1, cp2);
        System.out.println(cp1.equals(cp2));

        Eta eta = new Eta(or);

        System.out.println(eta);
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
