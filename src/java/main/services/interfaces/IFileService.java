package main.services.interfaces;

import main.models.Board;
import main.models.Level;

import java.io.File;

public interface IFileService {
    String PATH = "resources\\hashiBoards\\";
    File saveNewBoard(Board board);
    Board readSolution(File file);
    Board readSolution(Board board);
    Board readGame(File file);
    void writeDifficulty(String fileToWrite, String fileName, float features[], Level level);
    }
