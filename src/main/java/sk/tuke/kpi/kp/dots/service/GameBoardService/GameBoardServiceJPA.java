package sk.tuke.kpi.kp.dots.service.GameBoardService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;

import java.util.List;

@Transactional
public class GameBoardServiceJPA implements GameBoardService {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Adds a new game board.
     *
     * @param gameBoard the game board to be added
     */
    @Override
    public void addGameBoard(GameBoard gameBoard) {
        entityManager.persist(gameBoard);
    }

    /**
     * Retrieves the game boards for a specified game.
     *
     * @param game the game for which game boards should be retrieved
     * @return a list of game boards
     */
    @Override
    public List<GameBoard> getGameBoards(String game) {
        return entityManager.createQuery("SELECT g FROM GameBoard g WHERE g.game = :game ORDER BY g.playedAt desc")
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
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
        return (GameBoard) entityManager.createQuery("SELECT g FROM GameBoard g WHERE g.player = :player")
                .setParameter("player", player)
                .getSingleResult();
    }

    /**
     * Resets the game boards by deleting all records.
     */
    @Override
    public void reset() {
        entityManager.createQuery("DELETE FROM GameBoard").executeUpdate();
    }
}
