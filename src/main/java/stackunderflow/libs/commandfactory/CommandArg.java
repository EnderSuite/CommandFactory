package stackunderflow.libs.commandfactory;

import lombok.Getter;
import lombok.Setter;


/*
 * Represents a command argument.
 */
@Getter
@Setter
public class CommandArg {


    // ================================     VARS


    // References

    // Settings
    private CommandTokenType type;
    private boolean given;
    private String name;
    private String value;

    // State







    // ================================     CONSTRUCTOR


    public CommandArg(String name, String input) {

        // Check for required.
        setType(CommandToken.getTokenType(name));
        setName(name);
        setValue(input);
        valid();

    }



    // ================================     ARG LOGIC


    public boolean valid() {

        // Set given value.
        if (getValue() != null && !getValue().equals("null")) setGiven(true);
        if (getType().equals(CommandTokenType.REQUIRED) && getValue() == null) {
            return false;
        }
        if (getType().equals(CommandTokenType.OPTIONAL)) {
            return true;
        }

        return true;

    }


}
