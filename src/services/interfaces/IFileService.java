package services.interfaces;

import models.Board;
import solver.solvingTechniques.Levels;

import java.io.File;

public interface IFileService {
    String PATH = "resources\\hashiBoards\\";
    File saveNewBoard(Board board, Levels level);
    Board readSolution(File file);
    Board readSolution(Board board);
    Board readGame(File file);
    }
