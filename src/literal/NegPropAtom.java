package literal;

public class NegPropAtom extends Literal {
    
    //CONSTRUCTORS

    /**
     * @param name the name of negated propositional atom.
     */
    public NegPropAtom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new PropAtom(this.getName());
    }

    private boolean equals(NegPropAtom natm) {
        return this.getName().equals(natm.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NegPropAtom) && (this.equals( (NegPropAtom) obj ));
    }

    @Override
    public String toString() {
        return "~" + this.getName();
    }
}
