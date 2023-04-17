package com.ganyi.model;

/**
 * level作为关卡是游戏最顶层数据结构
 * 一个关卡包含地图map,map包含图层layer,layer包含单元格cell
 * cell又包含消除的基本组件 component (card)
 *
 */
public class Level {

    //成员变量
    private Integer count;//当前为第几关
    private Integer difficulty;//游戏难度:1简单 2一般 3困难
    private Integer eliminateBoxSize;//消除区域长度,默认7
    private Map map;//生成的地图

    //构造方法
    public Level() {
    }

    public Level(Integer count, Integer difficulty, Integer eliminateBoxSize, Map map) {
        this.count = count;
        this.difficulty = difficulty;
        this.eliminateBoxSize = eliminateBoxSize;
        this.map = map;
    }

    //成员方法

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getEliminateBoxSize() {
        eliminateBoxSize = 7;
        return eliminateBoxSize;
    }

    public void setEliminateBoxSize(Integer eliminateBoxSize) {
        this.eliminateBoxSize = eliminateBoxSize;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
