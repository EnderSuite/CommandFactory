# CommandFactory

**The nice way to handle commands!**

---

The CommandFactory library increases your productivity whilst writing command logic for your plugin 
by a WHOLE lot! It creates an Object Oriented interface which makes creating many commands easy & tidy.

At the same time, it abstracts the given arguments by applying a "Regex a-like" pattern matcher, 
to extract required and optional arguments.


# Installation

**First of all, you need to import the library into your project. You can use the "traditional" method of downloading the JAR file 
from the Spigot page and adding it as a dependency, or use Maven (I would recommend this). Also, make sure that the dependency gets included in your final plugin JAR!**

**If you imported the library, you can follow the tutorial below the code.**


*For maven users (add the following snippets into your pom.xml):*
```xml
<repositories>
    ...
    <!-- StackUnderflow REPO -->
    <repository>
        <id>stackunderflow-public</id>
        <url>https://repo.maximilian-heidenreich.de/repository/stackunderflow-public/</url>
    </repository>
    ...
</repositories>

<dependencies>
    ...
    <!-- CommandFactory -->
    <dependency>
        <groupId>stackunderflow.libs</groupId>
        <artifactId>commandfactory</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>
    ...
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.2.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <artifactSet>
                            <excludes>
                                <exclude>org.spigotmc</exclude>
                                <!-- You can add dependencies to exclude from the final JAR here -->
                            </excludes>
                        </artifactSet>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

# Usage

There are 3 Steps to using the CommandFactory:
1. Creating a class for your command.
2. Configuring the command (Specifiying the CommandPattern & required permissions).
3. Implementing your command logic.
4. Registering your command.


### 1. Creating a class for your command.

First of all, you need to create a custom class, which extends the `Command` class.
It is recommended, that you call this class "Command"+Functionality (e.g. "CommandHelp", "CommandSetMoney"...).

In this example it is called `CommandSetMoney`.

### 2. Configuring the command.

Now, you can configure the command. This will setup the CommandPattern and the required permissions.

*What is a CommandPattern?*
CommandPattern is the fancy term for a "Regex a-like" string like these ones:
- "/myplugin help"
- "/myplugin get money [player]"
- "/myplugin set money <player> <amount>"

These patterns are used by the factory to decide, which command class's methods to call & to parse the arguments.

---

To configure your command class, put a constructor with the following schema in your command class:
```java
public CommandSetMoney(String name) {
    super(name);

    // Setup tokens.
    this.addToken("myplugin", "mp")
        .addToken("set")
        .addToken("money")
        .addToken("<player>")
        .addToken("<amount>")
    
    // Setup permissions.
    this.addRequiredPermission("myplugin.permission");
}
```

**What this code does:** 

`super(name)` needs to be called to ensure internal logic. Then `addToken()` is used, to add a token to the CommandPattern. As 
one can see, it is possible to use static tokens like `myplugin`, `set`, `money`, as well as required (`<player>`) and optional tokens (`[player]`) depending on your needs.
Lastly, there also is a required permission added. This will cause the `onNoPermission` method to be called, if a player wants to use this command without the right permission(s).


### 3. Implementing your command logic.

Now, that you have a basic framework, you can implement your custom command logic.
To do this, there are 3 methods you can / should override:
1. `onPlayerCall(Player sender, Map<String, CommandArg> args)`
2. `onConsoleCall(CommandSender sender, Map<String, CommandArg> args)`
3. `onNoPermission(Player sender, String permission)`

`onPlayerCall()` Gets called when a player enters the command (args are the provided arguments).
`onConsoleCall()` Gets called when the console enters the command (args are the provided arguments).
`onNoPermission()` Gets called when a player enters the command but does not have all the required permissions (permission is the (first) missing one).

**Example Implementation:**

```java
@Override
public void onPlayerCall(Player sender, Map<String, CommandArg> args) {

    // Get the data from the args.
    String playerName = args.get("<player>").getValue();
    double amount = Double.parse(args.get("<amount>").getValue());

    // Update the players money.
    someAPI.updatePlayerMoeny(playerName, amount);
    
    // Example for an optional argument: /myplugin money get [name]
    
    // Check whether to get own money or other players.
    if (args.get("[name]").isGiven()) {
        // Get other players money.
    }
    else {
        // Get own money.
    }

}

@Override
public void onConsoleCall(CommandSender sender, Map<String, CommandArg> args) {
    // Put some logic here...
}

@Override
public void onNoPermission(Player sender, String permission) {
    sender.sendMessage("§cYou do not have the permission: §e"+permission);
}
```


### 4. Registering your command.

Finally, you just need to register your command inside of your main plugin class (the clas that has `extends JavaPlugin` at the top).
(It is assumed, that you already registered your command in the plugin.yml as well.)

```java
// Register commands.
CommandFactory cmdFac = new CommandFactory();
cmdFac.addCommand(new CommandSetMoney("setMoney"));
this.getCommand("myplugin").setExecutor(cmdFac);
```

**What this code does:**

It creates a CommandFactory instance, adds our command to it and sets the executor of the "base command" (/myplugin) to our CommandFactory.
