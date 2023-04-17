package com.ganyi.model;

import java.awt.event.MouseListener;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 进行存放和消除的区域
 */
public class EliminateBox {
    //成员变量
    private final Integer step = 5;
    private Level level;
    private static int count = 0;//对addToSlot操作进行计数
    private static final List<Card> slot = new ArrayList<>();

    //成员方法
    //根据name消除同名的所有牌
    public void deleteByCardName(String name) {
        Iterator<Card> iterator = slot.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getName().equals(name)) {
                card.getParent().remove(card);//移除组件
                iterator.remove();//从基础集合中移除此迭代器返回的最后一个元素
            }
        }
    }

    //移除并返回狭槽内指定索引的牌
    public Card removeByIndex(int index){
        Card card = slot.get(index);
        slot.remove(index);
        paint();//记得重新绘制
        return card;
    }

    //获得当前slot内card数量
    public int getSlotSize(){
        return slot.size();
    }


    public void addToSlot(Card card) {
        //0.对进入狭槽的牌设置权重值和记录槽的进入累计次数--为了撤回操作使用
        setCount(getCount() + 1);
        card.setWeight(getCount());

        slot.add(card);
        MouseListener[] mouseListeners = card.getMouseListeners();
        if (mouseListeners != null) {
            for (MouseListener mouseListener : mouseListeners) {
                card.removeMouseListener(mouseListener);
            }
        }
        slot.sort(Comparator.comparing(Card::getName));
        Map<String, List<Card>> listMap = slot.stream().collect(Collectors.groupingBy(Card::getName));
        Set<String> keys = listMap.keySet();
        for (String key : keys) {
            List<Card> cards = listMap.get(key);
            if (cards.size() == 3) {
                System.out.println(key + "被消除了！");
                deleteByCardName(key);
                break;
            }
        }

        paint();
        //7.判定消除区域是否装满,装满则游戏结束
        if (slot.size() >= 7) {
            //逻辑待完善
            System.out.println("Game Over");
            System.exit(0);
        }
    }

    //获得狭槽内权重值最大的牌的权重值
    public int getMaxWeight(){
        int max = 0;
        for(Card card : slot){
            if(card.getWeight() > max) max = card.getWeight();
        }
        return max;
    }

    //移除并返回狭槽内权重值最大的牌
    public Card removeMaxCard(){
        Card res = null;
        int max = getMaxWeight();
        for(Card card : slot){
            if(max == card.getWeight()){
                res = card;
                slot.remove(card);
                paint();
                break;
            }
        }
        return res;
    }

    //绘制到消除区域--Card类的paint是在牌桌上画,这个是在狭槽
    public void paint() {
        for (int i = 0; i < slot.size(); i++) {
            Card card = slot.get(i);
            //每张牌横坐标距离
            int x = step + i * card.getWidth() + 5 / 2 + 10;//可调整
            card.setBounds(x, 600, 50, 50);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
