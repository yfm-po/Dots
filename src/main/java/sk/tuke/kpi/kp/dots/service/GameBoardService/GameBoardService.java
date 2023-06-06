package sk.tuke.kpi.kp.dots.service.GameBoardService;

import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;

import java.util.List;

public interface GameBoardService {
    void addGameBoard(GameBoard gameBoard);
    List<GameBoard> getGameBoards(String game);
    GameBoard getGameBoard(String game, String player);
    void reset();
}
