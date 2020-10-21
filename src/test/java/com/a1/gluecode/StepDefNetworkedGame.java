package com.a1.gluecode;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.mock.MockServer;
import com.a1.player.Player;
import com.a1.util.Card;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class StepDefNetworkedGame extends TestCase {

    private MockServer server;
    private Player[] players;
    private String[] dieRoll = {};
    private Card card;
    private String inputString = "";  // user's mock input in a real game
    private int earnedScore = -1;     // earned score in one turn

    @Given("server has been started")
    public void server_has_been_started() {
        server = new MockServer();
        players = new Player[3];
        Thread mockServerThread = new Thread(server);
        mockServerThread.start();
    }

    @Given("player {int} input his or her name as {string}")
    public void player_input_his_or_her_name_as(int playerId, String name) {
        players[playerId - 1] = new Player(name);
    }

    @Given("player {int} initialize local players")
    public void player_initialize_local_players(int playerId) {
        players[playerId - 1].initializeGamePlayers();
    }

    @Given("player {int} connect to server")
    public void player_connect_to_server(int playerId) {
        players[playerId - 1].connectToServer();
    }

    @Given("player {int} receive three players' info")
    public void player_receive_three_players_info(int playerId) {
        Player[] pls = players[playerId - 1].getPlayerConnection().receivePlayers();
        players[playerId - 1].setPlayers(pls);
    }

    @When("player {int} receive turn number {int}")
    public void player_receive_turn_number(int playerId, int exceptedTurnNo) {
        int turnNo = players[playerId - 1].getPlayerConnection().receiveTurnNo();
        assertEquals(exceptedTurnNo, turnNo);
    }

    @When("player {int} receive three players' score")
    public void player_receive_three_players_score(int playerId) {
        int[] scores = players[playerId - 1].getPlayerConnection().receiverScores();
        Player[] pls = players[playerId - 1].getPlayers();
        for (int i = 0; i < scores.length; ++i) {
            pls[i].setScore(scores[i]);
        }
    }

    @When("player {int} print out three players' score")
    public void player_print_out_three_players_score(int playerId) {
        Player[] pls = players[playerId - 1].getPlayers();
        players[playerId - 1].printPlayerScores(pls);
    }

    @When("player {int} roll {string} with fortune card {string}")
    public void player_roll_with_fortune_card(int playerId, String strDieRoll, String cardName) {
        dieRoll = strDieRoll.replaceAll("\\s", "").split(",");
        card = new Card(cardName);
    }

    @When("player {int} start a turn")
    public void player_start_a_turn(int playerId) {
        inputString = "";
    }

    @When("player {int} press button {int} to score")
    public void player_press_button_to_score(int playerId, Integer button) {
        inputString += button.toString();
        GameMode.mode = GameMode.CUCUMBER_TEST;
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        earnedScore = players[playerId - 1].playTurn(dieRoll, card, new Scanner(in));
        System.out.println("Turn is End. You earn " + earnedScore + " points.");
    }

    @When("player {int} get {int} points in this turn")
    public void player_get_points_in_this_turn(int playerId, int expectedScore) {
        assertEquals(expectedScore, earnedScore);
    }

    @When("player {int} send server he or she didn't go to island of skull")
    public void player_send_server_he_or_she_didn_t_go_to_island_of_skull(int playerId) {
        players[playerId - 1].getPlayerConnection().sendIslandOfSkull(false);
    }

    @When("player {int} send server the score he or she earned in this turn")
    public void player_send_server_the_score_he_or_she_earned_in_this_turn(int playerId) {
        players[playerId - 1].getPlayerConnection().sendScore(earnedScore);
    }

    @Then("server print out three players' score after one turn")
    public void server_print_out_three_players_score_after_one_turn() {
        Player[] players = server.getServer().getPlayers();  // players stored in server
        for (Player player : players) {
            Game.printScore(player);
        }
    }

    @When("player {int} and player {int} can still play one more turn")
    public void player_and_player_can_still_play_one_more_turn(int playerId2, int playerId3) {
        // player 2 and player 3 can still player one more turn
    }

    @Then("stop server")
    public void stop_server() {
        server.stop();
    }

    @Then("player {int} receive winner")
    public void player_receive_winner(int playerId) {
        Player winner = players[playerId - 1].getPlayerConnection().receivePlayer();
        if (playerId == winner.getPlayerId()) {
            System.out.println("You win!");
        } else {
            System.out.println("The winner is " + winner.getName());
        }
    }

    @When("player {int} press button {int} to re-roll")
    public void player_press_button_to_re_roll(int playerId, Integer button) {
        inputString += button.toString() + " ";
    }

    @When("player {int} select die at index {string} to re-roll")
    public void player_select_die_at_index_to_re_roll(int playerId, String strReRollIndexes) {
        inputString += strReRollIndexes + " ";
    }

    @When("player {int} get {string}")
    public void player_get(int playerId, String riggedDieRoll) {
        inputString += riggedDieRoll + "\n";
    }

    @When("player {int} will die")
    public void player_will_die(int playerId) {
        GameMode.mode = GameMode.CUCUMBER_TEST;
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        earnedScore = players[playerId - 1].playTurn(dieRoll, card, new Scanner(in));
        assertTrue(players[playerId - 1].playerIsDead(dieRoll, card));
    }

    @When("player {int} has not used Sorceress Card")
    public void player_has_not_used_Sorceress_Card(int playerId) {
        assertFalse(card.isUsed());
    }

    @When("player {int} press button {int} to use Sorceress Card")
    public void player_press_button_to_use_Sorceress_Card(int playerId, Integer button) {
        inputString += button.toString() + " ";
    }

}
