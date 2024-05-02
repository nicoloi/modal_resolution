package resolution;

import clauses.*;
import literal.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Resolution {

    private static Map<Integer, Set<Integer>> visited;
    private static List<Step> trace;
    private static boolean enableSteps = false;

    public static boolean isSatisfiable(ClauseSet s) {
        Objects.requireNonNull(s);
        if (s.isEmpty()) throw new IllegalArgumentException("the clause set in input is empty");

        s.removeTautologies();

        if (s.isEmpty()) {
            //in this case s contains only tautologies.
            return true;
        }

        visited = new HashMap<>();
        trace = new ArrayList<>();
        List<Clause> listCl = new ArrayList<>(); 

        for (Clause c : s) {
            //initializes the map, with an empty set for each index of the clauses in s
            visited.put(c.getIndex(), new HashSet<>());
            listCl.add(c);

            //apply "G2L" rule for each global clause
            if (c instanceof GlobalClause) {
                LocalClause local = G2L(c);
                visited.put(local.getIndex(), new HashSet<>());
                listCl.add(local);
            }
        }
        
        //visit each pair of clauses to apply the inference rules.
        for (int i = 0; i < listCl.size(); i++) {

            Clause c1 = listCl.get(i);
            int index1 = c1.getIndex();

            for (int j = 0; j < listCl.size(); j++) {

                Clause c2 = listCl.get(j);
                int index2 = c2.getIndex();

                if ((i != j) && (c1.getClass().equals(c2.getClass())) && !alreadyVisited(c1, c2)) {

                    Literal complemLit = getComplementaryLiterals(c1, c2);
                    
                    if (complemLit != null) {

                        addCoupleInMap(index1, index2);
                        Clause resolvent = null;
                        Step step = null;

                        if ((c1 instanceof GlobalClause) && (c2 instanceof GlobalClause)) {
                            //apply GRES rule
                            resolvent = GRES((GlobalClause) c1, (GlobalClause) c2, complemLit);

                            if (enableSteps) {
                                step = new Step(c1, c2, resolvent, complemLit, "GRES");
                                trace.add(step);
                            }
                        } else {
                            //apply LRES rule
                            resolvent = LRES((LocalClause) c1, (LocalClause) c2, complemLit);

                            if (enableSteps) {
                                step = new Step(c1, c2, resolvent, complemLit, "LRES");
                                trace.add(step);
                            }
                        }

                        //check if the resolvent is empty, and if not, check if it can be added 
                        //to the list of clauses.

                        if (resolvent.isEmpty()) {
                            if (enableSteps) printTrace();
                            return false;
                        }

                        if (resolvent.isTautology()) {
                            if (enableSteps)
                                step.setTautology();
                        } else if (listCl.contains(resolvent)) {
                            if (enableSteps)
                                step.setAlreadyPresent();
                        } else {
                            visited.put(resolvent.getIndex(), new HashSet<>());
                            listCl.add(resolvent);
                        }

                    } else {
                        // check if it is possible to apply the LERES or GERES rules
                        Literal[] literals = getModalLiterals(c1, c2, listCl);

                        if (literals.length != 0) {   // if array "literals" is not empty

                            addCoupleInMap(index1, index2);
                            Clause resolvent = null;
                            Step step = null;

                            if ((c1 instanceof GlobalClause) && (c2 instanceof GlobalClause)) {
                                //apply GERES rule
                                resolvent = GERES((GlobalClause) c1, (GlobalClause) c2, literals[0], literals[1]);

                                if (enableSteps) {
                                    step = new Step(c1, c2, resolvent, literals[0], literals[1], "GERES");
                                    trace.add(step);
                                }
                            } else {
                                //apply LERES rule
                                resolvent = LERES((LocalClause) c1, (LocalClause) c2, literals[0], literals[1]);

                                if (enableSteps) {
                                    step = new Step(c1, c2, resolvent, literals[0], literals[1], "LERES");
                                    trace.add(step);
                                }
                            } 

                            //check if the resolvent is empty, and if not, check if it can be added 
                            //to the list of clauses.
                            if (resolvent.isEmpty()) {
                                if (enableSteps) printTrace();
                                return false;
                            }


                            if (resolvent.isTautology()) {
                                if (enableSteps)
                                    step.setTautology();
                            } else if (listCl.contains(resolvent)) {
                                if (enableSteps)
                                    step.setAlreadyPresent();
                            } else {
                                visited.put(resolvent.getIndex(), new HashSet<>());
                                listCl.add(resolvent);
                            }
                        }
                    }
                }
            }
        }
        
        if (enableSteps) printTrace();

        /*
         * if after analyzing all the pairs of clauses in s, 
         * the contradiction is not found, then s is satisfiable
         */
        return true;
    }

    
    private static Literal getComplementaryLiterals(Clause c1, Clause c2) { 
        for (Literal l1 : c1) {
            for (Literal l2 : c2) { 
                if (l1.equals(l2.getOpposite())) return l1;
            }
        }
        
        return null;
    }
    
    private static void addCoupleInMap(int index1, int index2) {
        if (index1 < index2) {
            (visited.get(index1)).add(index2);
        } else {
            (visited.get(index2)).add(index1);
        }
    }

    private static boolean alreadyVisited(Clause c1, Clause c2) {
        int i1 = c1.getIndex();
        int i2 = c2.getIndex();

        if (i1 < i2) {
            return (visited.get(i1)).contains(i2);
        }

        //case i1 > i2
        return (visited.get(i2)).contains(i1);
    }

    private static Literal[] getModalLiterals(Clause c1, Clause c2, List<Clause> listCl) {

        for (Literal l1 : c1) {
            if (l1 instanceof ModalAtom) { //#p
                for (Literal l2 : c2) {
                    if (l2 instanceof NegModalAtom) { //~#q
                        GlobalClause c_1 = new GlobalClause(new PropAtom(l2.getName())); //G({l2})
                        GlobalClause c_prime_1 = new GlobalClause(new PropAtom(l1.getName())); //G({l1})
                        GlobalClause c_2 = new GlobalClause(new NegPropAtom(l1.getName())); //G({~l1})
                        GlobalClause c_prime_2 = new GlobalClause(new NegPropAtom(l2.getName())); //G({~l2})
                        GlobalClause union1 = (GlobalClause) c_1.union(c_2);
                        GlobalClause union2 = (GlobalClause) c_prime_1.union(c_prime_2);

                        if ((listCl.contains(c_1) && listCl.contains(c_prime_1)) ||
                            (listCl.contains(c_2) && listCl.contains(c_prime_2)) ||
                            (listCl.contains(union1) && listCl.contains(union2)) ||
                            (listCl.contains(union1) && listCl.contains(c_prime_1)) ||
                            (listCl.contains(union1) && listCl.contains(c_prime_2)) ||
                            (listCl.contains(c_2) && listCl.contains(union2)) ||
                            (listCl.contains(c_1) && listCl.contains(union2)))
                        {
                           Literal[] lit = new Literal[2];
                           lit[0] = l1;
                           lit[1] = l2;
                           return lit;
                        }
                    }
                }
            } 
        }
        
        //return an empty array.
        return new Literal[0];
    }





    //methods that implement inference rules

    private static LocalClause LRES(LocalClause lc1, LocalClause lc2, Literal lit) {
        LocalClause result = (LocalClause) lc1.union(lc2);

        result.remove(lit);
        result.remove(lit.getOpposite());
        
        return result;
    }

    private static GlobalClause GRES(GlobalClause gc1, GlobalClause gc2, Literal lit) {
        GlobalClause result = (GlobalClause) gc1.union(gc2);

        result.remove(lit);
        result.remove(lit.getOpposite());
        
        return result;
    }

    private static LocalClause G2L(Clause gc) {

        if (gc instanceof LocalClause) {
            throw new IllegalArgumentException("The clause " + gc + " is local.");
        }

        List<Literal> list = new ArrayList<>();

        for (Literal l : gc) {
            list.add(l);
        }

        return new LocalClause(list);
    }

    private static LocalClause LERES(LocalClause lc1, LocalClause lc2, Literal ma, Literal nma) {
        LocalClause result = (LocalClause) lc1.union(lc2);

        result.remove(ma);
        result.remove(nma);
        
        return result;
    }

    private static GlobalClause GERES(GlobalClause gc1, GlobalClause gc2, Literal ma, Literal nma) {
        GlobalClause result = (GlobalClause) gc1.union(gc2);

        result.remove(ma);
        result.remove(nma);
        
        return result;
    }

    public static void setEnableSteps(boolean enableStepsValue) {
        enableSteps = enableStepsValue;
    }

    /**
     * prints the trace of the steps that have been performed by the resolution method.
     */
    private static void printTrace() {
        for (Step st : trace) {
            System.out.println(st.toString());
        }
    }
}