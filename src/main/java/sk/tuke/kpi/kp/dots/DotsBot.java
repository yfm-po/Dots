package sk.tuke.kpi.kp.dots;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.dv8tion.jda.api.requests.GatewayIntent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sk.tuke.kpi.kp.dots.core.Dot.Dot;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameBoard;
import sk.tuke.kpi.kp.dots.core.GameBoard.GameState;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DotsBot extends ListenerAdapter {

    private final Map<String, GameSession> games = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            JDABuilder.createDefault("MTEwNDU0OTI1Nzk1ODEyOTc2NA.GNk1mu.W7wvpI4i2okaTEhfGzzLPDGJFnvgSY4nwyuPRY")
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new DotsBot())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String messageContent = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        String[] commandParts = messageContent.split("");
        String userId = event.getAuthor().getId();

        if (messageContent.startsWith("play")) {
            startNewGame(userId, channel);
        } else if (messageContent.startsWith("s")) {
            if (commandParts.length < 3) {
                return;
            }

            int row = Integer.parseInt(commandParts[1]) - 1;
            int column = Integer.parseInt(commandParts[2]) - 1;
            selectDot(userId, row, column, channel);
        } else if (messageContent.startsWith("help")) {
            displayHelp(channel);
        } else if (messageContent.startsWith("x")) {
            executeDots(userId, channel);
        }

        event.getMessage().delete().queue();
    }

    private void startNewGame(String userId, MessageChannel channel) {
        GameBoard game = new GameBoard(4, 4, 16);
        GameSession gameSession = new GameSession(game);
        games.put(userId, gameSession);
        channel.sendMessageEmbeds(buildGameEmbed(game)).queue(message -> {
            gameSession.setGameMessage(message);
        });
    }

    private void selectDot(String userId, int row, int column, MessageChannel channel) {
        GameBoard game = games.get(userId).getGame();
        if (game == null) {
            channel.sendMessage("You don't have a game in progress. Start a new game with play.").queue();
            return;
        }

        if (row < 0 || row >= game.getRowCount() || column < 0 || column >= game.getColumnCount()) {
            channel.sendMessage("Invalid row or column.").queue();
            return;
        }

        game.selectDot(row, column);
        game.updateGameState();
        sendGameBoard(userId, channel);
        checkGameState(userId, channel);
    }

    private void executeDots(String userId, MessageChannel channel) {
        GameBoard game = games.get(userId).getGame();
        if (game == null) {
            channel.sendMessage("You don't have a game in progress. Start a new game with play.").queue();
            return;
        }
        game.executeDots();
        game.updateGameState();
        sendGameBoard(userId, channel);
        checkGameState(userId, channel);
    }

    private void checkGameState(String userId, MessageChannel channel) {
        GameBoard game = games.get(userId).getGame();
        if (game.getGameState() == GameState.LOST) {
            channel.sendMessage("You lost! Start a new game with play.").queue();
            games.remove(userId);
        } else if (game.getGameState() == GameState.WON) {
            channel.sendMessage("Congratulations, you won! Start a new game by typing play.").queue();
            games.remove(userId);
        }
    }

    private void sendGameBoard(String userId, MessageChannel channel) {
        GameSession gameSession = games.get(userId);
        GameBoard game = gameSession.getGame();
        Message gameMessage = gameSession.getGameMessage();

        gameMessage.editMessageEmbeds(buildGameEmbed(game)).queue();
    }

    private MessageEmbed buildGameEmbed(GameBoard game) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder gameBoard = new StringBuilder();

        gameBoard.append("```");
        gameBoard.append(" ");
        for (int i = 0; i < game.getColumnCount(); i++) {
            gameBoard.append(String.format("%2d ", i + 1));  // Column headers
        }
        gameBoard.append("\n");
        gameBoard.append("```");

        for (int i = 0; i < game.getRowCount(); i++) {
            if (i == 0) {
                gameBoard.append("|").append(String.format("%2d  |", i + 1));
            } else {
                gameBoard.append("|").append(String.format("%2d |", i + 1)); // Row headers
            }
            for (int j = 0; j < game.getColumnCount(); j++) {
                Dot dot = game.getDot(i, j);
                if (dot == null) {
                    gameBoard.append("   |");
                } else {
                    String dotEmoji = switch (dot.getColor()) {
                        case RED -> dot.isSelected() ? ":red_square:" : ":red_circle:";
                        case GREEN -> dot.isSelected() ? ":green_square:" : ":green_circle:";
                        case YELLOW -> dot.isSelected() ? ":yellow_square:" : ":yellow_circle:";
                        case BLUE -> dot.isSelected() ? ":blue_square:" : ":blue_circle:";
                    };
                    gameBoard.append(dotEmoji).append("|");
                }
            }
            gameBoard.append("\n");
        }

        gameBoard.append("\n");

        gameBoard.append("Current score: ").append(game.getScore()).append("\n");

        if (game.getNeededRedDotsToBeExecuted() - game.getExecutedRedDots() > 0) {
            gameBoard.append(":red_circle: ").append(game.getNeededRedDotsToBeExecuted() - game.getExecutedRedDots()).append("\n");
        }
        gameBoard.append("\n");
        if (game.getNeededGreenDotsToBeExecuted() - game.getExecutedGreenDots() > 0) {
            gameBoard.append(":green_circle: ").append(game.getNeededGreenDotsToBeExecuted() - game.getExecutedGreenDots()).append("\n");
        }
        gameBoard.append("\n");
        if (game.getNeededYellowDotsToBeExecuted() - game.getExecutedYellowDots() > 0) {
            gameBoard.append(":yellow_circle: ").append(game.getNeededYellowDotsToBeExecuted() - game.getExecutedYellowDots()).append("\n");
        }
        gameBoard.append("\n");
        if (game.getNeededBlueDotsToBeExecuted() - game.getExecutedBlueDots() > 0) {
            gameBoard.append(":blue_circle: ").append(game.getNeededBlueDotsToBeExecuted() - game.getExecutedBlueDots()).append("\n");
        }
        gameBoard.append("\n");
        gameBoard.append("Remaining moves: ").append(game.getRemainingMoves()).append("\n");
        gameBoard.append("\n");
        embedBuilder.addField("Game Board", gameBoard.toString(), false);
        return embedBuilder.build();
    }

    private void displayHelp(MessageChannel channel) {
        channel.sendMessage("Available commands:\n" +
                "play - Starts a new game.\n" +
                "s <row><column> - Selects a dot at the specified row and column.\n" +
                "x - executes selected dots\n" +
                "help - Displays this help message.").queue();
    }
}