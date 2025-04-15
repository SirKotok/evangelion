package eva.evangelion;

import eva.evangelion.view.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;


public class EvaApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        MainMenu manager = new MainMenu();
        stage = manager.getMainStage();
        stage.setTitle("Evangelion");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }



}

/*


<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <executions>
                    <execution>
                        <!-- Default configuration for running with: mvn clean javafx:run -->
                        <id>default-cli</id>
                        <configuration>
                            <mainClass>eva.evangelion.EvaApplication</mainClass>
                            <launcher>app</launcher>
                            <jlinkZipName>app</jlinkZipName>
                            <jlinkImageName>app</jlinkImageName>
                            <noManPages>true</noManPages>
                            <stripDebug>true</stripDebug>
                            <noHeaderFiles>true</noHeaderFiles>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
 */