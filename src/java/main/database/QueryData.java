package main.database;

import com.google.cloud.firestore.*;
import main.models.Level;

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
        // retrieve  query results asynchronously using query.get()
//        ApiFuture<QuerySnapshot> querySnapshot = query.get();
//        Map<String, Object> data = querySnapshot.get().getDocuments().get(0).getData();
//
//        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//            System.out.println(document.getId());
//        }
        return collection.limit(1);
    }
}
