package sk.tuke.kpi.kp.dots;

import net.dv8tion.jda.api.entities.Message;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;

public class GameSession {
    private final GameBoard game;
    private Message gameMessage;

    public GameSession(GameBoard game) {
        this.game = game;
    }

    public GameBoard getGame() {
        return game;
    }

    public Message getGameMessage() {
        return gameMessage;
    }

    public void setGameMessage(Message gameMessage) {
        this.gameMessage = gameMessage;
    }
}
