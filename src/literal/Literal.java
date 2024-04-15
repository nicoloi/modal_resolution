package literal;

import java.util.Objects;

/**
 * This abstract class represents a literal in modal logic,
 * which can be a propositional atom or modal atom, and their
 * opposites
 */
public abstract class Literal {

    //FIELDS
    private final String name;

    //CONSTRUCTORS

    /**
     * @param name the name of literal
     * @throws NullPointerException if the "name" parameter is null
     * @throws IllegalArgumentException if the "name" parameter is a empty string.
     * 
     * this constructor is used by the subclasses to instantiate the literal, 
     * with the name given as a parameter.
     */
    protected Literal(String name) {
        Objects.requireNonNull(name);

        if (name.equals("")) {
            throw new IllegalArgumentException("the name cannot be empty");
        }
        
        this.name = name;
    }

    //METHODS
    
    /**
     * @return the name of this literal.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the opposite of the literal this. if the literal is an instance of PropAtom class 
     * it returns the corresponding NegPropAtom instance, and vice versa. If the literal is an instance
     * of ModalAtom class it returns the corresponding NegModalAtom instance, and vice versa.
     */
    public abstract Literal getOpposite();

    @Override
    public int hashCode() {
        return 1;
    }
}