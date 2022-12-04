package main;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.controllers.MenuGameController;
import main.controllers.MenuTestController;
import main.database.ManageData;
import main.database.QueryData;
import main.models.Board;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    private static Board board;
    private Firestore db;
    private static boolean testMode;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader;
        if (!testMode) {
            fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/menu_game.fxml"));
        }
        else{
            fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/menu_test.fxml"));
        }
        Parent rootNode = fxmlLoader.load();
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hashi");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Main(String projectId) throws Exception {
        // [START firestore_setup_client_create]
        // Option 1: Initialize a Firestore client with a specific `projectId` and
        //           authorization credential.

        InputStream serviceAccount = new FileInputStream("hashi-da6a0-efdcc453f09c.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // [START fs_initialize_project_id]
        // [START firestore_setup_client_create_with_project_id]
        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setProjectId(projectId)
                        .setCredentials(credentials)
                        .build();
        Firestore db = firestoreOptions.getService();
        ManageData manageData = new ManageData(db);
        QueryData queryData = new QueryData(db);
        // [END fs_initialize_project_id]
        // [END firestore_setup_client_create_with_project_id]
        // [END firestore_setup_client_create]
        this.db = db;
    }

//    /**
//     * Initialize Firestore using default project ID.
//     */
    public Main() {
        // [START firestore_setup_client_create]

        // Option 2: Initialize a Firestore client with default values inferred from
        //           your environment.
        // [START fs_initialize]
        Firestore db = FirestoreOptions.getDefaultInstance().getService();
        ManageData manageData = new ManageData(db);
        QueryData queryData = new QueryData(db);


        // [END firestore_setup_client_create]
        // [END fs_initialize]
        this.db = db;
    }

    Firestore getDb() {
        return db;
    }

    void run() throws Exception {
        String[] docNames = {"alovelace", "aturing", "cbabbage"};

    }


    public static void main(String[] args) throws Exception {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        testMode = appProps.getProperty("testMode").equals("true");

        String projectId = "hashi-da6a0";
        Main quickStart = new Main(projectId);
        quickStart.run();
        quickStart.close();
        launch(args);
    }

    void close() throws Exception {
        db.close();
    }
}
