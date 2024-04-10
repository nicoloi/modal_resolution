package connective;

public enum Connective {
    NOT,
    AND,
    OR,
    IMPLIES,
    IFF,
    BOX;    // modal connective '#'


    @Override
    public String toString() {
        String res = "";

        switch (this) {
            case NOT:
                res = "~";
                break;
            case AND:
                res = "&";
                break;
            case IFF:
                res = "<->";
                break;
            case IMPLIES:
                res = "->";
                break;
            case OR:
                res = "|";
                break;
            case BOX:
                res = "#";
                break;
        }

        return res;
    }
}
