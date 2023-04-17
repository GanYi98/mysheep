package com.ganyi.model;

import java.util.Random;

/**
 * 图层--由单元格组成,各个图层组合起来形成map
 * 每个图层左上角位置留有随机生成的偏移量
 * 从而形成堆叠,露出部分下层图层的图案
 */
public class Layer {

    //成员变量
    private Integer offset = 0;//偏移量
    private Integer x;//加上偏移量的左上角x坐标
    private Integer y;//加上偏移量的左上角y坐标
    private Integer cellNumX;//本图层中横向有多少单元格
    private Integer cellNumY;//本图层中竖向有多少单元格
    private Cell[][] cells = null;//包含的各个cell
    private Integer capacity;//容量
    //当前图层有多少cell:为0说明当前图层组件被消除完,可以从map中删除
    private Integer size = 0;
    private Layer upperLayer;//上层图层

    //构造方法
    public Layer(Integer cellNumX, Integer cellNumY) {
        this.cellNumX = cellNumX;
        this.cellNumY = cellNumY;
        this.capacity = this.cellNumX * this.cellNumY;
        this.cells = new Cell[cellNumX][cellNumY];
        //设置该图层偏移量
        this.offset = new Random().nextInt(120);
    }

    //成员方法
    public Cell getIndex(int index) {
        int index_x = index / this.getCellNumY();
        int index_y = index % this.getCellNumY();
        return this.cells[index_x][index_y];
    }

    public void show() {//TestLayer测试用
        if (cells == null) return;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[i][j].getCard().getName());
            }
            System.out.println();
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
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

    public Integer getCellNumX() {
        return cellNumX;
    }

    public void setCellNumX(Integer cellNumX) {
        this.cellNumX = cellNumX;
    }

    public Integer getCellNumY() {
        return cellNumY;
    }

    public void setCellNumY(Integer cellNumY) {
        this.cellNumY = cellNumY;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Layer getUpperLayer() {
        return upperLayer;
    }

    public void setUpperLayer(Layer parentLayer) {
        this.upperLayer = parentLayer;
    }
}
