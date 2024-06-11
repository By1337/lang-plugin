# lang-plugin

This plugin facilitates the management of configuration files based on languages.

Imagine you have a single configuration file but with different languages. You can organize your resources as follows:

```
ru/config.yaml
en/config.yaml
```

Then, configure your pom.xml approximately like this:

```xml
<project>
    <pluginRepositories>
        <pluginRepository>
            <id>by1337-repo</id>
            <url>https://repo.by1337.space/repository/maven-releases/</url>
        </pluginRepository>
    </pluginRepositories>
    
    <build>
        <plugin>
            <groupId>org.by1337.plugin.lang</groupId>
            <artifactId>lang-plugin</artifactId>
            <version>1.0</version>
            <configuration>
                <packageList>
                    <resource>
                        <dir>ru/</dir>
                        <relocate>/</relocate>
                        <output>${artifactId}-${version}-ru.jar</output>
                    </resource>
                    <resource>
                        <dir>en/</dir>
                        <relocate>/</relocate>
                        <output>${artifactId}-${version}-en.jar</output>
                    </resource>
                </packageList>
            </configuration>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>package</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </build>
</project>
```

Now, upon building, new builds `${artifactId}-${version}-ru.jar` and `${artifactId}-${version}-en.jar` will appear in the target directory. In the ru build, configuration files from the ru/ directory will be moved to /. Similarly, this applies to the en build.