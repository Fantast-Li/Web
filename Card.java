package Poker;
// 扑克牌类  具有大小和花色属性
public class Card {
    public String suit;
    public int rank;

    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("[%s %d]", suit, rank);
    }
}
