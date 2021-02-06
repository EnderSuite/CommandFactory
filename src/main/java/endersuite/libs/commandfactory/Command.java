package endersuite.libs.commandfactory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
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
    private List<String> requiredPermissions;






    // ================================     CONSTRUCTOR


    public Command(String name) {
        setTokens(new ArrayList<CommandToken>());
        setName(name);
        setRequiredPermissions(new ArrayList<String>());
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

    /*
     * Adds a permission to the required permissions.
     */
    public Command addRequiredPermission(String permission) {
        getRequiredPermissions().add(permission);
        return this;
    }



    // ================================     CUSTOM LOGIC


    /*
     * Custom logic for when a player calls the cmd.
     */
    public void _onPlayerCall(Player sender, Map<String, CommandArg> args) {

        // RET: No perms needed.
        if (getRequiredPermissions().size() <= 0) { onPlayerCall(sender, args); return; };


        // Check perms.
        for (String permission : getRequiredPermissions()) {
            if (!sender.hasPermission(permission)) {
                onNoPermission(sender, permission);
                return;
            }
        }

        // Continue.
        onPlayerCall(sender, args);


    }
    public void onPlayerCall(Player sender, Map<String, CommandArg> args) {}


    /*
     * Custom logic for when the console calls the cmd.
     */
    public void onConsoleCall(CommandSender sender, Map<String, CommandArg> args) {}


    /*
     * Custom logic for when the sender does not have the necessary permission.
     * ! Only applicable for players !
     */
    public void onNoPermission(Player sender, String permission) {}


}
