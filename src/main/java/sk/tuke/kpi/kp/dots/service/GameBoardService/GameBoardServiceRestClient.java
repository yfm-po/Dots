package sk.tuke.kpi.kp.dots.service.GameBoardService;

import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;
import sk.tuke.kpi.kp.dots.service.DataRest.DataRest;

import java.util.List;

public class GameBoardServiceRestClient implements GameBoardService {

    private final String url = DataRest.getGameBoardsUrl();

    private final RestTemplate restTemplate;

    public GameBoardServiceRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Adds a new game board.
     *
     * @param gameBoard the game board to be added
     */
    @Override
    public void addGameBoard(GameBoard gameBoard) {
        restTemplate.postForEntity(url, gameBoard, GameBoard.class);
    }

    /**
     * Retrieves the game boards for a specified game.
     *
     * @param game the game for which game boards should be retrieved
     * @return a list of game boards
     */
    @Override
    public List<GameBoard> getGameBoards(String game) {
        return restTemplate.getForEntity(DataRest.getGameBoardsWithGameUrl(), List.class).getBody();
    }

    /**
     * Retrieves the game board for a specified game and player.
     *
     * @param game the game for which game board should be retrieved
     * @param player the player for which game board should be retrieved
     * @return a game board
     */
    @Override
    public GameBoard getGameBoard(String game, String player) {
        return restTemplate.getForEntity(url + '/' + game + '/' + player, GameBoard.class).getBody();
    }

    /**
     * Reset the game boards - Not supported via web service.
     *
     * @throws UnsupportedOperationException always
     */
    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service.");
    }
}
