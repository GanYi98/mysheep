package com.ganyi.model;

/**
 * 图层的基本单元-单元格
 */
public class Cell {
    //成员变量
    private Integer state = 0;//0表示空,1表示有牌
    private Card card = null;

    //构造方法
    public Cell(Card card) {
        this.card = card;
        this.state = 1;
    }

    //成员方法
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
