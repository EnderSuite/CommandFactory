package stackunderflow.libs.commandfactory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;


/*
 * A wrapper for the command data & logic.
 * Handles stateless & stateful logic.
 */
@Getter
@Setter
public class Command {

    // ================================     VARS

    // References
    private ArrayList<CommandToken> tokens;

    // Settings
    private String name;






    // ================================     CONSTRUCTOR


    public Command(String name) {
        setTokens(new ArrayList<CommandToken>());
        setName(name);
    }



    // ================================     COMMAND FACTORY LOGIC

    /*
     * Adds a token to the chain.
     */
    public Command addToken(String name) {
        getTokens().add(new CommandToken(name));
        return this;
    }
    public Command addToken(String name, String alias) {
        getTokens().add(new CommandToken(name, alias));
        return this;
    }
    public String getStringRepresentation() {
        ArrayList<String> parts = new ArrayList<>();
        for (CommandToken token : getTokens()) { parts.add(token.getName()); }
        return String.join(" ", parts);
    }
    public String getAliasStringRepresentation() {
        ArrayList<String> parts = new ArrayList<>();
        for (CommandToken token : getTokens()) {
            if (token.getAlias().equals("")) parts.add(token.getName());
            else parts.add(token.getAlias());
        }
        return String.join(" ", parts);
    }




    public void onPlayerCall(Player sender, Map<String, CommandArg> args) {}
    public void onConsoleCall(CommandSender sender, Map<String, CommandArg> args) {}


}
