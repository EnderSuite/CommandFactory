


<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Discord][discord-shield]][discord-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![GPLv3 License][license-shield]][license-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/EnderSuite/CommandFactory">
    <img src="https://raw.githubusercontent.com/EnderSuite/Resources/master/commandfactory_logo_transparent.svg" alt="Logo" width="80" height="80">
  </a>

<h2 align="center">CommandFactory</h2>

  <p align="center">
    A better way to handle commands</a>.
    <br />
    <a href="https://github.com/EnderSuite/CommandFactory/wiki"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://www.spigotmc.org/resources/endersync-1-8-1-13-sync-playerdata-between-servers-inventory-health-xp.64344/">SpigotMC Page</a>
    ·
    <a href="https://github.com/EnderSuite/CommandFactory/issues">Report Bug</a>
    ·
    <a href="https://github.com/EnderSuite/CommandFactory/issues">Request Feature</a>
  </p>
</p>


![Banner][banner]
<img src="https://www.spigotmc.org/attachments/beforeafter-png.404246/">


<!-- TABLE OF CONTENTS -->
## Table of Contents

- [Table of Contents](#table-of-contents)
- [About The Project](#about-the-project)
    - [Features](#features)
- [Getting Started](#getting-started)
    - [Installation](#installation)
    - [Usage](#usage)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [Contact](#contact)



<br></br>
<!-- ABOUT THE PROJECT -->
## About The Project

Are you tired of endless onCommand methods, with several if, else statements to check all the arguments a player entered?

> CommandFactory is a simple library which abstracts onCommand methods and argument parsing.

### Features:

TODO

- [x] Static Tokens
- [x] Required Tokens
- [x] Optional Tokens
- [x] Permission Support
- [x] **Open Source** *(GPL-3.0, see [License info](https://choosealicense.com/licenses/gpl-3.0/) for further details)*


<br></br>
<!-- GETTING STARTED -->
## Getting Started

### Installation

#### gradle.build

```text
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}
dependencies {
    implementation 'com.github.EnderSuite:CommandFactory:1.1'
}
```

#### pom.xml

```text
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
...
<dependency>
    <groupId>com.github.EnderSuite</groupId>
    <artifactId>CommandFactory</artifactId>
    <version>1.1</version>
</dependency>
```


### Concepts

#### Token

A token is a single string inside a full command. Multiple tokens are separated by a whitespace.

#### Static Token

Static tokens are used to build unique commands.

Example: `/myplugin reload`

#### Required Tokens

When you want to force the user to input some values, you can use a required token.
The command will error if no value is specified.

Example: `/myplugin echo <message>`

#### Optional Tokens

When you want to give the user the option to input additional values, you can use an optional token.
The command won't error if no values is specified.

Example: `/myplugin echo [optionalMessage]`


### Usage

1. Create a Class for your own command that extends the `Command` class.

```java
public class SetMoneyCommand extends Command {
    ...
}
```


2. Configure your command.

```java
...
    public SetMoneyCommand(String name) {
        super(name);

        // Setup tokens.
        this.addToken("myplugin", "mp")
            .addToken("set")
            .addToken("money")
            .addToken("<player>")
            .addToken("<amount>");

        // Setup permissions.
        this.addRequiredPermission("myplugin.permission");
    }
...
```

> *What does this code do?*
> 
> `super(name)` needs to be called to ensure internal logic. Then `addToken()` is used, to add a token to the CommandPattern. As
one can see, it is possible to use static tokens like myplugin, set, money, as well as required (`<player>`) and optional tokens (`[player]`) depending on your needs.
Lastly, there also is a required permission added. This will cause the `onNoPermission` method to be called, if a player wants to use this command without the right permission(s).


4. Implement your command logic.

Now, that you have a basic framework, you can implement your custom command logic.
To do this, there are 3 methods you can override:

`onPlayerCall(Player sender, Map<String, CommandArg> args)`
`onConsoleCall(CommandSender sender, Map<String, CommandArg> args)`
`onNoPermission(Player sender, String permission)`

`onPlayerCall()` Gets called when a player enters the command (args are the provided arguments).

`onConsoleCall()` Gets called when the console enters the command (args are the provided arguments).

`onNoPermission()` Gets called when a player enters the command but does not have all the required permissions (permission is the first one that's missing if multiple are configured).

```java
...
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
...
```


5. Register your command.

Finally, you just need to register your command inside your main plugin class (the class that extends JavaPlugin).
> **!** *It is assumed, that you already registered your command inside plugin.yml*

```java
CommandFactory cmdFac = new CommandFactory();
cmdFac.addCommand(new SetMoneyCommand("setMoney"));
this.getCommand("myplugin").setExecutor(cmdFac);
```


<br></br>
<!-- ROADMAP -->
## Roadmap


See the [open issues](https://github.com/EnderSuite/CommandFactory/issues) for a list of proposed features (and known issues).


<br></br>
<!-- CONTRIBUTING -->
## Contributing

If you want to improve the project, feel free to follow the following steps:

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<br></br>
<!-- CONTACT -->
## Contact


Maximilian Heidenreich - github@maximilian-heidenreich.de

Project Link: [https://github.com/EnderSuite/CommandFactory](https://github.com/EnderSuite/CommandFactory)





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[discord-shield]: https://img.shields.io/discord/313303575558356993?label=Discord&logo=discord&logoColor=white&style=flat-square
[discord-url]: https://discord.gg/sgRMJrZcZE
[forks-shield]: https://img.shields.io/github/forks/EnderSuite/CommandFactory?style=flat-square
[forks-url]: https://github.com/EnderSuite/CommandFactory/network
[stars-shield]: https://img.shields.io/github/stars/EnderSuite/CommandFactory?style=flat-square
[stars-url]: https://github.com/EnderSuite/CommandFactory/stargazers
[issues-shield]: https://img.shields.io/github/issues/EnderSuite/CommandFactory?style=flat-square
[issues-url]: https://github.com/EnderSuite/CommandFactory/issues
[license-shield]: https://img.shields.io/github/license/EnderSuite/CommandFactory?style=flat-square
[license-url]: https://github.com/EnderSuite/CommandFactory/blob/master/LICENSE.txt
[banner]: https://www.spigotmc.org/attachments/beforeafter-png.404246/

