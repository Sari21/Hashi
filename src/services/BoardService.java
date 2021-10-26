package services;

import models.Board;
import services.interfaces.IBridgeService;

public class BoardService implements IBridgeService {
    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
