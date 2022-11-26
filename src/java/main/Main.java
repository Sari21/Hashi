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
import main.view.BoardView;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main extends Application {

    static Board board;
    private static BoardView boardView;
    private Firestore db;


    @Override
    public void start(Stage primaryStage) throws Exception {
//        todo if jatek/test
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/views/menu_game.fxml"));
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

    /**
     * Initialize Firestore using default project ID.
     */
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

//    void addDocument(String docName) throws Exception {
//        switch (docName) {
//            case "alovelace": {
//                // [START fs_add_data_1]
//                // [START firestore_setup_dataset_pt1]
//                DocumentReference docRef = db.collection("hashi_puzzles").document("alovelace");
//                // Add document data  with id "alovelace" using a hashmap
//                Map<String, Object> data = new HashMap<>();
//                data.put("first", "Ada");
//                data.put("last", "Lovelace");
//                data.put("born", 1815);
//                //asynchronously write data
//                ApiFuture<WriteResult> result = docRef.set(data);
//                // ...
//                // result.get() blocks on response
//                System.out.println("macska");
//                // [END firestore_setup_dataset_pt1]
//                // [END fs_add_data_1]
//                break;
//            }
//            case "aturing": {
//                // [START fs_add_data_2]
//                // [START firestore_setup_dataset_pt2]
//                DocumentReference docRef = db.collection("hashi_puzzles").document("aturing");
//                // Add document data with an additional field ("middle")
//                Map<String, Object> data = new HashMap<>();
//                data.put("first", "Alan");
//                data.put("middle", "Mathison");
//                data.put("last", "Turing");
//                data.put("born", 1912);
//
//                ApiFuture<WriteResult> result = docRef.set(data);
//                // [END firestore_setup_dataset_pt2]
//                // [END fs_add_data_2]
//                break;
//            }
//            case "cbabbage": {
//                DocumentReference docRef = db.collection("hashi_puzzles").document("cbabbage");
//                Map<String, Object> data =
//                        new ImmutableMap.Builder<String, Object>()
//                                .put("first", "Charles")
//                                .put("last", "Babbage")
//                                .put("born", 1791)
//                                .build();
//                ApiFuture<WriteResult> result = docRef.set(data);
//                break;
//            }
//            default:
//        }
//    }

//    void runQuery() throws Exception {
//        // [START fs_add_query]
//        // asynchronously query for all users born before 1900
//        ApiFuture<QuerySnapshot> query =
//                db.collection("hashi_puzzles").whereLessThan("born", 1900).get();
//        // ...
//        // query.get() blocks on response
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println("User: " + document.getId());
//            System.out.println("First: " + document.getString("first"));
//            if (document.contains("middle")) {
//                System.out.println("Middle: " + document.getString("middle"));
//            }
//            System.out.println("Last: " + document.getString("last"));
//            System.out.println("Born: " + document.getLong("born"));
//        }
//        // [END fs_add_query]
//    }

//    void retrieveAllDocuments() throws Exception {
//        // [START fs_get_all]
//        // asynchronously retrieve all users
//        ApiFuture<QuerySnapshot> query = db.collection("hashi_puzzles").get();
//        // ...
//        // query.get() blocks on response
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println("User: " + document.getId());
//            System.out.println("First: " + document.getString("first"));
//            if (document.contains("middle")) {
//                System.out.println("Middle: " + document.getString("middle"));
//            }
//            System.out.println("Last: " + document.getString("last"));
//            System.out.println("Born: " + document.getLong("born"));
//        }
//        // [END fs_get_all]
//    }

    void run() throws Exception {
        String[] docNames = {"alovelace", "aturing", "cbabbage"};

//        // Adding document 1
//        System.out.println("########## Adding document 1 ##########");
//        addDocument(docNames[0]);
//
//        // Adding document 2
//        System.out.println("########## Adding document 2 ##########");
//        addDocument(docNames[1]);
//
//        // Adding document 3
//        System.out.println("########## Adding document 3 ##########");
//        addDocument(docNames[2]);
//
//        // retrieve all users born before 1900
//        System.out.println("########## users born before 1900 ##########");
//        runQuery();
//
//        // retrieve all users
//        System.out.println("########## All users ##########");
//        retrieveAllDocuments();
//        System.out.println("###################################");
    }


    public static void main(String[] args) throws Exception {

        String projectId = "hashi-da6a0";
        Main quickStart = new Main(projectId);
        quickStart.run();
        quickStart.close();
        launch(args);
    }

    void close() throws Exception {
        db.close();
    }

//
//        public static void main(String[] args) throws Exception {
//            System.out.println("Hello TensorFlow " + TensorFlow.version());
//
//            try (ConcreteFunction dbl = ConcreteFunction.create(Main::dbl);
//                 TInt32 x = TInt32.scalarOf(10);
//                 Tensor dblX = dbl.call(x)) {
//                System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt());
//            }
////            TensorflowClassifier tfc = new TensorflowClassifier();
////            tfc.loadModel();
//        }
//
//        private static Signature dbl(Ops tf) {
//            Placeholder<TInt32> x = tf.placeholder(TInt32.class);
//            Add<TInt32> dblX = tf.math.add(x, x);
//            return Signature.builder().input("x", x).output("dbl", dblX).build();
//        }


}
