package literal;

public class PropAtom extends Literal {

    //CONSTRUCTORS

    /**
     * @param name the name of the propositional atom.
     * @throws NullPointerException if the "name" parameter is null
     * @throws IllegalArgumentException if the "name" parameter is a empty string.
     */
    public PropAtom(String name) {
        super(name);
    }
    
    //METHODS

    @Override
    public Literal getOpposite() {
        return new NegPropAtom(this.getName());
    }

    private boolean equals(PropAtom atm) {
        return this.getName().equals(atm.getName());
    }

    @Override
    public boolean equals(Object obj) {
	    return (obj instanceof PropAtom) && (this.equals( (PropAtom) obj));
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
