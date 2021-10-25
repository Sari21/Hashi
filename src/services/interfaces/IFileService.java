package services.interfaces;

import models.Board;

import java.io.File;

public interface IFileService {
    File printBoardToCsv(Board board);
    Board ReadSolution(File file);
    Board ReadGame(File file);

    }
