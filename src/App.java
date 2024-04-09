import literal.*;
import clauses.*;

public class App {
    public static void main(String[] args) {

        GlobalClause gc = new GlobalClause();
        Clause gc2 = new GlobalClause();
        LocalClause lc = new LocalClause();

        gc.add(new PropAtom("a"));
        gc.add(new NegPropAtom("b"));
        gc.add(new ModalAtom("c"));

        gc2.add(new NegPropAtom("a"));
        gc2.add(new NegModalAtom("d"));

        //System.out.println(gc.union(gc2));

        lc.add(new ModalAtom("a"));
        lc.add(new NegPropAtom("b"));

        ClauseSet cs = new ClauseSet();
        cs.add(gc);
        cs.add(gc2);
        cs.add(lc);

        System.out.println(cs);
    }
}
