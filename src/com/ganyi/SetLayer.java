package com.ganyi;

import com.ganyi.model.Card;
import com.ganyi.model.Cell;
import com.ganyi.model.Layer;

import java.util.Random;

/**
 * 设置随机生成的每一图层
 */
public class SetLayer {

    public static String[] cardNames = {//卡牌种类
            "刷子", "剪刀", "叉子",
            "手套", "水桶", "火",
            "玉米", "球", "瓶子",
            "白菜", "稻草", "肉腿",
            "胡萝卜", "苹果", "铃铛",
            "青草", "鸡"
    };

    //cellNumX,cellNumY表示构建的图层 每行多少单元格,每列多少单元格
    //**注意**:两个参数的乘积结果需要被3整除,不然会出现异常(这样也就导致了各个层其实是分裂的)
    public static Layer buildLayer(int cellNumX, int cellNumY) {
        Layer layer = new Layer(cellNumX, cellNumY);
        Cell[][] cells = layer.getCells();
        //往cells中填充cards,每个cell一个card
        //为了实现,先new一个card数组
        Card[] cards = new Card[layer.getCapacity()];
        //1.从牌的种类中随机取一个牌名
        //2.根据牌名创建3个相同的牌对象
        //3.把对象放入card数组
        for (int i = 0; i < cards.length; i += 3) {
            //生成随机数
            int randomValue = new Random().nextInt(cardNames.length);
            String cardName = cardNames[randomValue];
            Card card1 = new Card(cardName);
            Card card2 = new Card(cardName);
            Card card3 = new Card(cardName);
            cards[i] = card1;
            cards[i + 1] = card2;
            cards[i + 2] = card3;
        }
        //4.把cards数组的牌打乱顺序--**洗牌算法**
        Random random = new Random();
        Card tmp;
        for (int i = 0; i < cards.length; i++) {
            int j = i + random.nextInt(cards.length - i);
            tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp;
        }
        //5.将牌往图层的各个单元格里塞,进行互相绑定
        int count = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Card card = cards[count];
                Cell cell = new Cell(card);
                card.setCell(cell);
                cells[i][j] = cell;
                count++;
            }
        }
        //6.设置当前图层包含的单元格数
        layer.setSize(count);
        return layer;
    }

}
