package sk.tuke.kpi.kp.dots.server.webservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;

@RestController
@RequestMapping("/api/dots/gameboard")
public class DotsRestService {
    private GameBoard gameBoard = new GameBoard(8, 8, 16);

    @GetMapping()
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @GetMapping("/newGame")
    public GameBoard newGameBoard(@RequestParam int level) {
        if (level < 1 || level > 5) {
            return gameBoard;
        }

        switch (level) {
            case 1:
                gameBoard = new GameBoard(4, 4, 20);
                return gameBoard;
            case 2:
                gameBoard = new GameBoard(5, 5, 15);
                return gameBoard;
            case 3:
                gameBoard = new GameBoard(6, 6, 10);
                return gameBoard;
            case 4:
                gameBoard = new GameBoard(7, 7, 12);
                return gameBoard;
            case 5:
                gameBoard = new GameBoard(8, 8, 16);
                return gameBoard;
        }
        return gameBoard;
    }

    @GetMapping("/selectDot")
    public GameBoard select(@RequestParam int row, @RequestParam int column) {
        if (gameBoard.getDot(row, column).isSelected()) {
            gameBoard.unselectDot(row, column);
        } else {
            gameBoard.selectDot(row, column);
        }
        return gameBoard;
    }

    @GetMapping("/executeDots")
    public GameBoard execute() {
        gameBoard.executeDots();
        return gameBoard;
    }

    @GetMapping("/buyPowerUp")
    public GameBoard buyPowerUp(@RequestParam String powerUpName) {
        gameBoard.buyPowerUp(powerUpName);
        return gameBoard;
    }
    @GetMapping("/applyPowerUp")
    public GameBoard applyPowerUp(@RequestParam String powerUpName, @RequestParam int row, @RequestParam int column) {
        gameBoard.applyPowerUp(powerUpName, row, column);
        return gameBoard;
    }
}
