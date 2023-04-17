package com.ganyi.model;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 存放移出的牌的区域
 */
public class ExtraBox {
    //成员变量
    private final Integer step = 150;
    private static final List<Card> list = new ArrayList<>();

    public void addToList(Card card) {
        list.add(card);//1.往额外区中添加card
        paint();
        //2.重新获得卡牌的鼠标监听器
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Card card = (Card) e.getSource();
                System.out.println(card.getName() + "被收集！");
                card.eliminateBox.addToSlot(card);//加入消除区域
                //当前图层位置的单元格置空,让牌消失
                list.remove(card);
                //重新绘制
                paint();
            }
        });
    }


    //绘制到额外区域
    public void paint() {
        for (int i = 0; i < list.size(); i++) {
            Card card = list.get(i);
            //每张牌横坐标距离
            int x = step + i * card.getWidth();//可调整
            card.setReturnX(x);//**更新牌的撤回位置**
            card.setReturnY(525);
            card.setBounds(x, 525, 50, 50);
        }
    }
}
