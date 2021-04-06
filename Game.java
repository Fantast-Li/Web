package Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//先洗牌
// 然后在发牌
// 然后在玩家的手牌.
public class Game {
    public static void main(String[] args) {
        //定义五名玩家  使用list
        List<Player> players = new ArrayList<>();
        players.add(new Player("李四"));
        players.add(new Player("张三"));
        players.add(new Player("王五"));
        players.add(new Player("赵六"));
        players.add(new Player("胖八"));




        //List 作为线性表，表现生活中有很多前后关系的物品都可以
        // 例如这里的牌组/鞋柜/衣柜.
        List<Card> cards = new ArrayList<>();
        //初始化扑克牌
        initializeCard(cards);    //初始化扑克牌
        // 进行洗牌操作
        // 调用 Collections(jdk 提供的类)下的shuffle(洗牌)方法进行洗牌.
        Collections.shuffle(cards);
        System.out.println("抽牌前，牌组里的牌：");
        System.out.println(cards);
        //发牌操作
        int n = 3 ; //每个玩家发几张牌
        for (int i = 0; i < n ; i++) {
            for (Player p : players) {
                //从牌组上抽取一张牌 把他放到玩家手里
                Card card = cards.remove(0); //拿到抽出来的牌
                p.cards.add(card);

            }
        }
        System.out.println("现在牌组还剩的牌:");
        System.out.println(cards);
        for (Player p : players) {
            System.out.println(p.name+"的手牌是:"+ p.cards);

        }

    }

    private static void initializeCard(List<Card> cards) {
        for (String suit: new String[]{"♠","♥","♣","♦"}) {
            for (int rank = 1 ; rank <= 13 ; rank++) {
                Card card = new Card(suit,rank);
                cards.add(card);
            }
        }
    }
}
