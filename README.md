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


For maven users (add the following snippets into your *pom.xml*):
```xml
<repositories>
    ...
    <!-- CommandFactory -->
    <repository>
        <id>commandfactory-repo</id>
        <url>https://gitlab.com/api/v4/projects/10789536/packages/maven</url>
    </repository>
    ...
</repositories>

<dependencies>
    ...
    <!-- CommandFactory -->
    <dependency>
        <groupId>stackunderflow.libs</groupId>
        <artifactId>commandfactory</artifactId>
        <version>1.0-SNAPSHOT</version>
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

TODO: ADD
