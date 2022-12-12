package main.database;

import com.google.cloud.firestore.*;
import main.models.Level;

import java.util.Map;
import java.util.Random;

public class QueryData {
    private static Firestore db;
    private References references;

    public QueryData(Firestore db) {
        QueryData.db = db;
        references = new References(db);
    }

    public QueryData() {

    }

    public Query createQuery(Level level, int size) throws Exception {
        references = new References(db);
        CollectionReference collection = references.getCollectionReference(size, level);
        Map<String, Object> maxIdMap = collection.orderBy("id", Query.Direction.DESCENDING)
                .limit(1).get().get().getDocuments().get(0).getData();
        long maxId = (long) maxIdMap.get("id");
        Random random = new Random();
        long randomValue = (long) (random.nextDouble() * (maxId));
        return collection.whereGreaterThanOrEqualTo("id", randomValue).limit(1);
    }
}
