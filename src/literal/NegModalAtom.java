package literal;

/**
 * This class represents a negated modal literal.
 */
public class NegModalAtom extends Literal {

    //CONSTRUCTORS

    /**
     * @param name the name of negated modal atom.
     */
    public NegModalAtom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new ModalAtom(this.getName());
    }

    private boolean equals(NegModalAtom nmatm) {
        return this.getName().equals(nmatm.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof NegModalAtom) && (this.equals( (NegModalAtom) obj ));
    }

    @Override
    public String toString() {
        return "~#" + this.getName();
    }
}
