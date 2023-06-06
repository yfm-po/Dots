package sk.tuke.kpi.kp.dots.service.DataRest;

public abstract class DataRest {
    private static final String URL = "http://localhost:8080/api/";

    private static final String COMMENT = "comment";
    private static final String SCORE = "score";
    private static final String RATING = "rating";
    private static final String GAMEBOARD = "gameboard";

    private static final String AVERAGE = "average";

    private static final String GAME = "dots";

    public static String getCommentsUrl() {
        return URL + COMMENT;
    }

    public static String getScoresUrl() {
        return URL + SCORE;
    }

    public static String getRatingsUrl() {
        return URL + RATING;
    }

    public static String getGameBoardsUrl() {
        return URL + GAMEBOARD;
    }

    public static String getCommentsWithGameUrl() {
        return getCommentsUrl() + "/" + GAME;
    }

    public static String getScoresWithGameUrl() {
        return getScoresUrl() + "/" + GAME;
    }

    public static String getRatingsWithGameUrl() {
        return getRatingsUrl() + "/" + GAME;
    }

    public static String getAverageRatingUrl() {
        return getRatingsUrl() + "/" + AVERAGE + "/" + GAME;
    }

    public static String getGameBoardsWithGameUrl() {
        return getGameBoardsUrl() + "/" + GAME;
    }
}
