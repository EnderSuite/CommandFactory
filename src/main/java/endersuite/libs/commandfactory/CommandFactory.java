package endersuite.libs.commandfactory;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;


/*
 * Can be configured with simple calls
 * and then handles the commands automatically.
 */
@Getter
@Setter
public class CommandFactory implements CommandExecutor {


    // ================================     VARS


    // References

    // State
    private ArrayList<Command> commands;





    // ================================     CONSTRUCTOR


    public CommandFactory() {
        setCommands(new ArrayList<>());
    }



    // ================================     COMMAND FACTORY LOGIC

    /*
     * Adds a command to the handler list.
     */
    public void addCommand(Command command) {
        getCommands().add(command);
    }

    /*
     * Gets a command.
     */
    public Command getCommand(String name) {
        for (Command command : getCommands()) if (command.getName().equals(name)) return command;
        return null;
    }

    /*
     * Send a "Invalid command message" to the player.
     * Overwrite this to implement your own custom logic.
     */
    public void sendInvalidCommandMessage(CommandSender sender, String command) {
        sender.sendMessage("§cInvalid command: §9"+command);
    }


    // ================================     COMMAND EXECUTOR

    /*
     * Handles a bukkit onCommand.
     * This parses the input and calls code if necessary.
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String cmdToken, String[] args) {

        // Generate input token list.
        List<String> inputTokens = Arrays.asList((cmdToken + " " + String.join(" ", args)).split(" "));


        // Iterate over commands.
        for (Command command : getCommands()) {

            // Store collected args.
            Map<String, CommandArg> commandArgMap = new HashMap<>();

            // Iterate over commandTokens.
            int cursor = 0;
            boolean execute = false;
            boolean usageMessage = false;

            String cmdString = args.length!=0 ? cmdToken+" "+String.join(" ", args) : cmdToken;
            if (command.getStringRepresentation().equals(cmdString) || command.getAliasStringRepresentation().equals(cmdString)) {
                execute = true;
            }
            else {

                tokenLoop:
                for (CommandToken commandToken : command.getTokens()) {

                    // Check if STATIC token matches.
                    if (commandToken.getType().equals(CommandTokenType.STATIC)) {
                        if (cursor>=inputTokens.size()) { break tokenLoop; }
                        if (!(commandToken.getName().equals(inputTokens.get(cursor)) || commandToken.getAlias().equals(inputTokens.get(cursor)))) {
                            // NOT Found!
                            execute = false;
                            break tokenLoop;
                        }
                    }

                    // Check if REQUIRED token matches.
                    if (commandToken.getType().equals(CommandTokenType.REQUIRED)) {
                        if (cursor>=inputTokens.size()) { execute = false; usageMessage = true; break tokenLoop; }
                        else {
                            // Add as argument.
                            commandArgMap.put(commandToken.getName(), new CommandArg(commandToken.getName(), inputTokens.get(cursor)));
                        }
                    }

                    // Check if OPTIONAL token matches.
                    if (commandToken.getType().equals(CommandTokenType.OPTIONAL)) {
                        if (cursor>=inputTokens.size()) {
                            // Add as argument.
                            commandArgMap.put(commandToken.getName(), new CommandArg(commandToken.getName(), "null"));
                        }
                        else {
                            // Add as argument.
                            commandArgMap.put(commandToken.getName(), new CommandArg(commandToken.getName(), inputTokens.get(cursor)));
                        }
                    }

                    cursor++;

                    if (cursor>=command.getTokens().size() && commandArgMap.size() != 0) {
                        execute = true;
                        break tokenLoop;
                    }

                }

            }


            if (usageMessage) {
                sendInvalidCommandMessage(sender, command.getStringRepresentation());
                /*new StringFormatter(Config.INSTANCE.getMessage("invalidCommand"))
                        .replace("{cmdString}", "/"+command.getStringRepresentation())
                        .setSuccess(false)
                        .sendMessageTo(sender);*/
                break;
            }
            if (execute) {

                if (sender instanceof Player) {
                    command._onPlayerCall((Player) sender, commandArgMap);
                }
                else {
                    command.onConsoleCall(sender, commandArgMap);
                }

                break;
            }

        }

        return true;

    }

}
