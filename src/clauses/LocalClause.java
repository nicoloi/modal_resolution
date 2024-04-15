package clauses;

import java.util.List;
import literal.Literal;
import java.util.Objects;

/*
 * this class represents a local clause, i.e. a clause
 * that is true in at least one world in a model.
 */
public class LocalClause extends Clause {

    //CONSTRUCTORS

    public LocalClause() {
        super();
    }

    public LocalClause(Literal l) {
        super(l);
    }

    public LocalClause(List<Literal> list) {
        super(list);
    }

    //METHODS

    @Override
    public Clause union(Clause c) {
        Objects.requireNonNull(c);

        if (c instanceof GlobalClause) {
            throw new IllegalArgumentException("the clause in input is global.");
        }

        Clause result = new LocalClause();

        for (Literal l : super.literals) {
            result.add(l);
        }

        for (Literal l : c.literals) {
            result.add(l);
        }

        return result;
    }

    @Override
    public String toString() {
        if (super.isEmpty()) return "{}"; //the empty clause represents the contradiction.

        StringBuilder res = new StringBuilder(super.literals.toString());
        res.setCharAt(0, '{');
        res.setCharAt(res.length() - 1, '}');

        return res.toString();
    }

    private boolean equals(LocalClause c) {
        return super.literals.equals(c.literals);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof LocalClause) && (this.equals( (LocalClause)obj ));
    }
}
