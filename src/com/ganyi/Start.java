package com.ganyi;

import com.ganyi.model.*;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 启动类 继承自JFrame类--能够用来创建一个窗体对象
 */
public class Start extends JFrame {

    private Integer width = 450;
    private Integer height = 800;
    public static Map map = SetMap.buildMap();

    private Card remove = new Card("移出道具");
    private Card withdraw = new Card("撤回道具");
    private Card shuffle = new Card("洗牌道具");
    private Card setting = new Card("设置");
    private Card slotZone = new Card("消除区域");
    private Card background = new Card("背景草地");

    public Start() throws HeadlessException {

        init();//窗口初始化
        renderMap();//各卡牌组件可视化
        initFunction();//各功能组件可视化
        refresh();//启动另一个新线程,开始执行间隔刷新组件任务


        try {
            new Music();//开始播放背景音乐
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

    //进行窗口初始化
    public void init() {
        this.setVisible(true);//显示或隐藏组件
        this.setSize(width, height);//设置窗体大小
        this.setTitle("羊了个羊(有手就行版).v1.0——干易出品");
        //设置用户在此窗体上发起 "close" 时默认执行的操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);//设置局部管理器,如果设置为null表示不使用
        this.setBounds(0, 0, 450, 800);//设置边界
        this.setLocationRelativeTo(null);//根据特定组件设置窗口位置
    }

    //进行功能组件可视化
    public void initFunction() {
        //狭槽可视化
        slotZone.setBounds(5, 575, 430, 110);
        this.getContentPane().add(slotZone);

        //设置可视化
        setting.setBounds(20, 20, 30, 30);
        this.getContentPane().add(setting);

        //移出道具可视化
        remove.setBounds(50, 700, 75, 63);
        this.getContentPane().add(remove);

        //撤回道具可视化
        withdraw.setBounds(175, 700, 75, 63);
        this.getContentPane().add(withdraw);

        //洗牌道具可视化
        shuffle.setBounds(300, 700, 75, 63);
        this.getContentPane().add(shuffle);

        //背景可视化--背景最后可视化,否则会覆盖其他
        background.setBounds(0, 0, 450, 800);
        this.getContentPane().add(background);
    }


    //使layer各层的组件可视化并且合理置灰
    public void renderMap() {
        List<Layer> list = map.getLayers();
        for (int i = 0; i < list.size(); i++) {
            renderLayer(this.getContentPane(), list.get(i));
        }
        map.ifTurnGray();//置灰判定
    }

    //使一层layer的组件(cards)可视化
    //getContentPane()返回此窗口的容器对象,放入组件,使之呈现出来
    private void renderLayer(Container container, Layer layer) {
        Cell[][] cells = layer.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                if (cell.getState() == 1) {
                    Card card = cell.getCard();
                    int cardX = j * 50 + layer.getOffset() + 10;//**动态调整阈值
                    int cardY = i * 50 + layer.getOffset() + 100;
                    card.setReturnX(cardX);//保存撤回位置
                    card.setReturnY(cardY);
                    card.setBounds(cardX, cardY, 50, 50);//设置组件边界
                    this.getContentPane().add(card);//每张card都加入窗口的容器
                }
            }
        }
    }

    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();//每隔一段时间重新绘制各组件
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Start();
    }

}
