package endersuite.libs.commandfactory;

import lombok.Getter;
import lombok.Setter;


/*
 * Represents a command token.
 */
@Getter
@Setter
public class CommandToken {

    // ================================     VARS


    // References

    // Settings
    private CommandTokenType type;
    private String name;
    private String alias;

    // State



    // ================================     CONSTRUCTOR

    public CommandToken(String name) {
        setType(getTokenType(name));
        setName(name);
        setAlias("");
    }
    public CommandToken(String name, String alias) {
        setType(getTokenType(name));
        setName(name);
        setAlias(alias);
    }



    // ================================     LOGIC

    /*
     * Returns the Token type at a specific index.
     */
    public static CommandTokenType getTokenType(String token) {
        if (token.startsWith("<") && token.endsWith(">")) { return CommandTokenType.REQUIRED; }
        if (token.startsWith("[") && token.endsWith("]")) { return CommandTokenType.OPTIONAL; }
        return CommandTokenType.STATIC;
    }


}
