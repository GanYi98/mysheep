package com.ganyi.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 数据结构地图由多个图层堆叠而成,层高为3就是有三层图层
 */
public class Map {

    //成员变量
    private Integer x;//绘制地图的左上角x坐标
    private Integer y;//绘制地图的左上角y坐标
    private Integer floorHeight;//层高
    private List<Layer> layers = new ArrayList<>();

    //构造方法
    public Map() {
    }

    //成员方法
    //判定当前map的所有图案是否置灰
    //调用时机: 1.第一次map构建时 2.某张牌移动时
    public void ifTurnGray() {
        List<Layer> list = this.getLayers();
        //最上层索引为0,不用判断是否置灰--但是为了洗牌时能洗最上层,这里也判断
        for (int i = 1; i < list.size(); i++) {
            Layer layer = list.get(i);
            for (int j = 0; j < layer.getCapacity(); j++) {//遍历每层的单元格
                Cell cell = layer.getIndex(j);
                if (cell.getState() == 1) {//如果该单元格有一张牌,才能进行置灰判断
                    Card card = cell.getCard();
                    //和上层的所有牌进行是否有交集的判定
                    boolean flag = this.cardToLayer(card, layer.getUpperLayer());
                    card.setGray(flag);//由flag判断是否进行置灰
                }
            }
        }
    }

    private boolean cardToLayer(Card card, Layer layer) {
        for (int j = 0; j < layer.getCapacity(); j++) {
            Cell cell = layer.getIndex(j);
            if (cell.getState() == 1) {//有牌
                Card upperCard = cell.getCard();
                //intersects方法 确定此矩形是否与指定的矩形相交,若交点非空，则返回true
                boolean flag = card.getBounds().intersects(upperCard.getBounds());
                if (flag) return true;
            }
        }
        //若整个for循环都结束,说明当前牌和上层的图层没有相交,继续比较上上层
        if (layer.getUpperLayer() == null) return false;
        else return cardToLayer(card, layer.getUpperLayer());
    }

    //进行各层卡牌随机打乱
    public void shuffleCards() {
        List<Layer> list = this.getLayers();
        for (int i = 0; i < list.size(); i++) {
            //1.获得该层需要进行打乱的所有牌
            Layer layer = list.get(i);
            List<Card> tmpCards = new ArrayList<>();
            for (int j = 0; j < layer.getCapacity(); j++) {
                Cell cell = layer.getIndex(j);
                if (cell.getState() == 1) {
                    Card card = cell.getCard();
                    card.setGray(false);
                    tmpCards.add(card);
                }
            }
            //2.将这些牌打乱--**洗牌算法
            Random random = new Random();
            Card tmp;
            for (int m = 0; m < tmpCards.size(); m++) {
                int n = m + random.nextInt(tmpCards.size() - m);
                tmp = tmpCards.get(m);
                tmpCards.set(m, tmpCards.get(n));
                tmpCards.set(n, tmp);
            }
            //3.将tmpCards各牌放回原来的layer
            int count = 0;
            for (int k = 0; k < layer.getCapacity(); k++) {
                Cell cell = layer.getIndex(k);
                if (cell.getState() == 1) {
                    Card card = tmpCards.get(count);
                    cell.setCard(card);
                    card.setCell(cell);
                    count++;
                }
            }
        }
        paint();//4.放好各层后重新绘制卡牌
    }

    public void show() {//该方法仅测试用
        List<Layer> testList = this.getLayers();
        for (int i = 0; i < testList.size(); i++) {
            Layer layer = testList.get(i);
            for (int j = 0; j < layer.getCapacity(); j++) {
                Cell cell = layer.getIndex(j);
                if (cell.getState() == 1) {
                    System.out.print(cell.getCard().getName() + ", ");
                    System.out.print(cell.getCard().getGray() + ", ");
                }
            }
        }
        System.out.println();
        System.out.println("========================================");
    }

    //重新绘制各Layer
    public void paint() {
        List<Layer> list = this.getLayers();
        for (int k = 0; k < list.size(); k++) {
            Layer layer = list.get(k);
            Cell[][] cells = layer.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = cells[i][j];
                    if (cell.getState() == 1) {
                        Card card = cell.getCard();
                        int cardX = j * 50 + layer.getOffset() + 10;//**动态调整阈值
                        int cardY = i * 50 + layer.getOffset() + 100;
                        card.setReturnX(cardX);//重新设置撤回坐标
                        card.setReturnY(cardY);
                        card.setBounds(cardX, cardY, 50, 50);//设置组件边界
                    }
                }
            }
        }
        this.ifTurnGray();//置灰判定
    }


    public void withdraw(Card card){
        int originX = card.getReturnX();
        int originY = card.getReturnY();
        List<Layer> list = this.getLayers();
        for (int k = 0; k < list.size(); k++) {
            Layer layer = list.get(k);
            Cell[][] cells = layer.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = cells[i][j];
                    if (cell.getState() == 0) {
                        int cardX = j * 50 + layer.getOffset() + 10;//**动态调整阈值
                        int cardY = i * 50 + layer.getOffset() + 100;
                        if(cardX == originX && cardY == originY){
                            cell.setState(1);
                            cell.setCard(card);
                            card.setCell(cell);
                            //2.重新获得卡牌的鼠标监听器
                            card.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    Card card = (Card) e.getSource();
                                    System.out.println(card.getName() + "被收集！");
                                    card.eliminateBox.addToSlot(card);//加入消除区域
                                    //当前图层位置的单元格置空,让牌消失
                                    cell.setState(0);
                                    cell.setCard(null);
                                    card.setCell(null);
                                    //进行置灰判定
                                    ifTurnGray();
                                }
                            });
                        }
                    }
                }
            }
        }
        paint();
    }



    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(Integer floorHeight) {
        this.floorHeight = floorHeight;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }
}
