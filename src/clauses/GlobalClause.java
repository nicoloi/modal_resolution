package clauses;

import java.util.List;
import literal.Literal;
import java.util.Objects;

public class GlobalClause extends Clause {

    //CONSTRUCTORS

    public GlobalClause() {
        super();
    }

    public GlobalClause(Literal l) {
        super(l);
    }

    public GlobalClause(List<Literal> list) {
        super(list);
    }

    //METHODS

    @Override
    public Clause union(Clause c) {
        Objects.requireNonNull(c);

        if (c instanceof LocalClause) {
            throw new IllegalArgumentException("the clause in input is local.");
        }

        Clause result = new GlobalClause();

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
        if (super.isEmpty()) return "G({})";

        StringBuilder res = new StringBuilder("G(" + super.literals.toString());
        res.setCharAt(2, '{');
        res.setCharAt(res.length() - 1, '}');
        res.append(")");

        return res.toString();
    }

    private boolean equals(GlobalClause c) {
        return super.literals.equals(c.literals);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GlobalClause) && (this.equals( (GlobalClause)obj ));
    }
}
