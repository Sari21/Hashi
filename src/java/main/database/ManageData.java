package main.database;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import main.database.dto.BoardDTO;
import main.models.Board;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ManageData {
   private static Firestore db;
    private References references;

    public ManageData(Firestore db) {
        ManageData.db = db;
        references = new References(db);
    }


    public ManageData() {
    }

    public Board addSimpleDocumentAsEntity(Board board) throws ExecutionException, InterruptedException {
        references = new References(db);

        BoardDTO boardDTO = BoardModelDTOConverter.ModelToDTOConverter(board);
        CollectionReference collection = references.getCollectionReference(board.getWidth(), board.getLevel());
        Date date = new Date();
        String fileName = board.getWidth() + "_" + board.getHeight() + "_" + board.getLevel().toString() + "_" + date.getTime() + ".txt";
        board.setFileName(fileName);
        ApiFuture<WriteResult> future = collection.document(board.getFileName()).set(boardDTO);
        // block on response if required
        System.out.println("Update time : " + future.get().getUpdateTime());
        return board;
    }
}
