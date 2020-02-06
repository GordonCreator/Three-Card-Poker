package handCalculationsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import calculations.Card;
import calculations.CardShuffler;
import calculations.HandCalculator;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testing hand calculations")
class HandCalculationTest {
		
	HandCalculator handCalculator;
	TestReporter testReporter;
	
	@BeforeAll
	void init(TestReporter testReporter) {
		handCalculator = new HandCalculator();
		this.testReporter = testReporter;
		
		testReporter.publishEntry("Running hand calculation tests");
	}
	
	@Test
	@DisplayName("Pair")
	void testHandCalculationPair() {

		Card card0 = new Card(5,0);		// 7 of Clubs
		Card card1 = new Card(5,1);		// 7 of Diamonds
		Card card2 = new Card(3,0);		// 5 of Clubs
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(1, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("Three Face Cards")
	void testHandCalculationThreeFaceCards() {

		Card card0 = new Card(11,0);		// King of Clubs
		Card card1 = new Card(12,1);		// Ace of Diamonds
		Card card2 = new Card(9,0);			// Jack of Clubs
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(4, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("Flush")
	void testHandCalculationFlush() {

		Card card0 = new Card(5,1);		// 7 of Diamonds
		Card card1 = new Card(11,1);	// King of Diamonds
		Card card2 = new Card(7,1);		// 9 of Diamonds
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(4, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("Straight")
	void testHandCalculationStraight() {

		Card card0 = new Card(5,0);		// 7 of Clubs
		Card card1 = new Card(6,1);		// 8 of Diamonds
		Card card2 = new Card(7,2);		// 9 of Hearts
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(5, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("Three of a Kind")
	void testHandCalculationThreeOfAKind() {

		Card card0 = new Card(0,1);		// 2 of Clubs
		Card card1 = new Card(0,1);		// 2 of Diamonds
		Card card2 = new Card(0,2);		// 2 of Hearts
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(50, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("StraightFlush")
	void testHandCalculationStraightFlush() {

		Card card0 = new Card(0,3);		// 2 of Spades
		Card card1 = new Card(1,3);		// 3 of Spades
		Card card2 = new Card(12,3);	// Ace of Spades
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(60, handCalculator.calculateHandStrength(board));
	}
	
	@Test
	@DisplayName("Royal Flush")
	void testHandCalculationRoyalFlush() {

		Card card0 = new Card(10,2);	// Queen of Hearts
		Card card1 = new Card(11,2);	// King of Hearts
		Card card2 = new Card(12,2);	// Ace of Hearts
		
		Card [] board = {card0,card1,card2};
		
		assertEquals(750, handCalculator.calculateHandStrength(board));
	}
	
	
}
