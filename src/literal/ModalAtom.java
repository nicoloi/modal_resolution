package literal;

/**
 * The ModalAtom class represents a modal literal in modal logic.
 * A modal literal is a propotitional variable prefixed by the
 * modal operator '#'.
 */
public class ModalAtom extends Literal {

    //CONSTRUCTORS

    /**
     * @param name the name of the modal atom
     * @throws NullPointerException if the "name" parameter is null
     * @throws IllegalArgumentException if the "name" parameter is a empty string.
     */
    public ModalAtom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new NegModalAtom(this.getName());
    }

    private boolean equals(ModalAtom matm) {
        return this.getName().equals(matm.getName());
    }

    @Override
    public boolean equals(Object obj) {
	    return (obj instanceof ModalAtom) && (this.equals( (ModalAtom) obj));
    }


    @Override
    public String toString() {
        return "#" + this.getName();
    }
}
