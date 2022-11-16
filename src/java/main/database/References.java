package main.database;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import main.models.Level;

public class References {
    private Firestore db;
    private final static String EASY7 = "hashi_puzzles/size_7/easy_7";
    private final static String EASY10 = "hashi_puzzles/size_10/easy_10";
    private final static String EASY15 = "hashi_puzzles/size_15/easy_15";
    private final static String EASY25 = "hashi_puzzles/size_25/easy_25";
    private final static String MEDIUM7 = "hashi_puzzles/size_7/medium_7";
    private final static String MEDIUM10 = "hashi_puzzles/size_10/medium_10";
    private final static String MEDIUM15 = "hashi_puzzles/size_15/medium_15";
    private final static String MEDIUM25 = "hashi_puzzles/size_25/medium_25";
    private final static String HARD7 = "hashi_puzzles/size_7/hard_7";
    private final static String HARD10 = "hashi_puzzles/size_10/hard_10";
    private final static String HARD15 = "hashi_puzzles/size_15/hard_15";
    private final static String HARD25 = "hashi_puzzles/size_25/hard_25";


    public References(Firestore db) {
        this.db = db;
    }

    /**
     * Return a reference to collection.
     *
     * @return collection reference
     */
    public CollectionReference getCollectionReference(int size, Level level) {
        String collectionName;
        switch (size) {
            case 7:
                switch (level) {
                    case EASY:
                        collectionName = EASY7;
                        break;
                    case MEDIUM:
                        collectionName = MEDIUM7;
                        break;
                    case HARD:
                        collectionName = HARD7;
                        break;
                    default:
                        collectionName = EASY7;
                }
                break;
            case 10:
                switch (level) {
                    case EASY:
                        collectionName = EASY10;
                        break;
                    case MEDIUM:
                        collectionName = MEDIUM10;
                        break;
                    case HARD:
                        collectionName = HARD10;
                        break;
                    default:
                        collectionName = EASY10;
                }
                break;
            case 15:
                switch (level) {
                    case EASY:
                        collectionName = EASY15;
                        break;
                    case MEDIUM:
                        collectionName = MEDIUM15;
                        break;
                    case HARD:
                        collectionName = HARD15;
                        break;
                    default:
                        collectionName = EASY15;
                }
                break;
            case 25:
                switch (level) {
                    case EASY:
                        collectionName = EASY25;
                        break;
                    case MEDIUM:
                        collectionName = MEDIUM25;
                        break;
                    case HARD:
                        collectionName = HARD25;
                        break;
                    default:
                        collectionName = EASY25;
                }
                break;
            default:
                collectionName = EASY10;
        }
        return db.collection(collectionName);
    }
}
