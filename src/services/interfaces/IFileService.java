package services.interfaces;

import models.Board;
import solver.solvingTechniques.Levels;

import java.io.File;

public interface IFileService {
    File saveNewBoard(Board board, Levels level);
    Board ReadSolution(File file);
    Board ReadGame(File file);

    }
