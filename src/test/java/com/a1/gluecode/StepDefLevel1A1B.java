package com.a1.gluecode;

import com.a1.game.Game;
import com.a1.game.GameMode;
import com.a1.player.Player;
import com.a1.util.Card;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class StepDefLevel1A1B extends TestCase {

    String[] dieRoll = {};
    Card card;
    Player player = new Player(" ");
    int earnedScore = -1;  // the score a player earned in one turn (earnedScore can be negative, 0 or positive number)
    String inputString = "";  // user's mock input in a real game
    int[] reRollIndexes = {};  // the indexes of dice that player wants to re-roll

    @Given("a dieRoll {string}")
    public void a_dieRoll(String strDieRoll) {
        dieRoll = strDieRoll.replaceAll("\\s", "").split(",");
    }

    @Given("a fortune card {string}")
    public void a_fortune_card(String cardName) {
        card = new Card(cardName);
    }

    @When("player start a turn with given dieRoll and fortune card")
    public void player_start_a_turn_with_given_dieRoll_and_fortune_card() {
        inputString = "";
    }

    @Then("player will die")
    public void player_will_die() {
        GameMode.mode = GameMode.CUCUMBER_TEST;
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        earnedScore = player.playTurn(dieRoll, card, new Scanner(in));
        assertTrue(player.playerIsDead(dieRoll, card));
    }

    @Then("player ends his or her turn")
    public void player_ends_his_or_her_turn() {
        // see output in console
    }

    @When("player press button {int} to re-roll")
    public void player_press_button_to_re_roll(Integer button) {
        inputString += button.toString() + " ";
    }

    @When("player select die at index {string} to re-roll")
    public void player_select_die_at_index_to_re_roll(String strReRollIndexes) {
        inputString += strReRollIndexes + " ";
    }

    @When("player get {string}")
    public void player_get(String riggedDieRoll) {
        inputString += riggedDieRoll + "\n";
    }

    @When("player press button {int} to score")
    public void player_press_button_to_score(Integer button) {
        inputString += button.toString() + " ";
        GameMode.mode = GameMode.CUCUMBER_TEST;
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        earnedScore = player.playTurn(dieRoll, card, new Scanner(in));
    }

    @Then("player get {int} points in this turn")
    public void player_get_points_in_this_turn(int expectedScore) {
        assertEquals(expectedScore, earnedScore);
    }

    @When("player wants to re-roll the number {int} die")
    public void player_wants_to_re_roll_the_number_die(Integer reRollIndex) {
        reRollIndexes = new int[1];
        reRollIndexes[0] = reRollIndex;
    }

    @Then("interface report re-roll is not allowed")
    public void interface_report_re_roll_is_not_allowed() {
        assertFalse(Game.isReRollIndexValid(dieRoll, reRollIndexes, card));
    }
}
