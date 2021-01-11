import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;
    private int chip;

    public Player() {
        this.hand = new ArrayList<>();
        this.chip = 0;
    }
    public List<Card> getHand() {
        return hand;
    }
    public int getChips() {
        return chip;
    }
    public void deal(Card card) {
        hand.add(card);
    }
    public void removeCard(int index) {
        hand.remove(index);
    }
    public void setCard(int index, Card card) {
        hand.set(index, card);
    }
    public void clearHand() {
        while (hand.size() > 0) {
            hand.remove(0);
        }
    }
    public void addChips(int addend) {
        chip += addend;
    }

    public void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());
            }

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());
        });
    }

    public int evaluateHand() {
        if (royalFlush(hand)) return 100;
        else if (straightFlush(hand)) return 50;
        else if (fourOfAKind(hand)) return 25;
        else if (fullHouse(hand)) return 15;
        else if (flush(hand)) return 10;
        else if (straight(hand)) return 5;
        else if (threeOfAKind(hand)) return 3;
        else if (twoPair(hand)) return 2;
        else if (pairOfJacksOrBetter(hand)) return 1;
        else return 0;
    }
    public static boolean royalFlush(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(0).getRank().equals("T") && Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1 && hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
        }
        return (count == 4);
    }

    public static boolean straightFlush(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1 && hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
        }
        return (count == 4);
    }

    public static boolean fourOfAKind(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 3);
    }

    public static boolean fullHouse(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < 3; i++) {
            if (count == 1 && !hand.get(i).getRank().matches(hand.get(i - 1).getRank())) break;
            else if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
        }
        if (count == 1) {
            count = 0;
            for (int i = 3; i < hand.size(); i++) {
                if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
                else count = 0;
            }
            return (count == 2);
        }
        else if (count == 2) {
            if (hand.get(4).getRank().matches(hand.get(3).getRank())) return true;
        }
        return false;
    }

    public static boolean flush(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 4);
    }
    public static boolean straight(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1) count++;
        }
        return (count == 4);
    }
    public static boolean threeOfAKind(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 2);
    }
    public static boolean twoPair(List<Card> hand) {
        int pairCount = 0;
        String lastPairRank = "";
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) {
                pairCount++;
                lastPairRank = hand.get(i).getRank();
                break;
            }
        }
        if (pairCount == 1) {
            for (int i = 1; i < hand.size(); i++) {
                if (hand.get(i).getRank().matches(hand.get(i - 1).getRank()) && !hand.get(i).getRank().matches(lastPairRank)) {
                    pairCount++;
                    break;
                }
            }
        }
        return (pairCount == 2);
    }
    public static boolean pairOfJacksOrBetter(List<Card> hand) {
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank()) && Card.getOrderedRank(hand.get(i).getRank()) >= 11) {
                return true;
            }
        }
        return false;
    }
}