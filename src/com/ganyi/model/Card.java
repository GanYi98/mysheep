package com.ganyi.model;

import com.ganyi.Start;
import com.ganyi.util.ImageUtil;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 游戏中的各种牌--继承自java.awt包的Component抽象类
 */
public class Card extends Component {
    //成员变量
    private String name;//牌按图案起名,用来定位
    //private String id; 未使用
    private Integer x = 0;//(x,y)为在非消除区域内的左上角坐标
    private Integer y = 0;
    private int returnX = 0;//保存撤回操作的位置 x向左,y向下
    private int returnY = 0;
    private int weight = 0;//撤回操作的权重值
    private int width = 0;//宽度
    private int height = 0;//高度
    private Image image;
    private Image grayImage;
    private Boolean isGray = false;//是否置灰
    private Cell cell;//绑定当前图层单元格
    //card存在于三个区域: 1.普通区    2.消除区   3.额外区   均可进行调用
    EliminateBox eliminateBox = new EliminateBox();
    ExtraBox extraBox = new ExtraBox();

    //构造函数
    public Card() {
    }

    public Card(String name) {
        this.name = name;
        this.image = ImageUtil.get(name + ".png");
        this.grayImage = ImageUtil.get(name + "_gray.png");
        //this.id = UUID.randomUUID().toString();
        //把当前对象的身份标记传给self,以便下面的内部类中调用
        Card self = this;

        //为Card创建鼠标侦听器--MouseAdapter类是一个用于接收鼠标事件的抽象(适配器)类
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//当被点击
                Card card = (Card) e.getSource();
                if (card.getGray() || card.getName().equals("消除区域") || card.getName().equals("背景草地") || card.getName().equals("设置")) {
                    return;
                } else if (card.getName().equals("移出道具")) {
                    if (eliminateBox.getSlotSize() < 3) {
                        System.out.println("当前无法使用移出道具！");
                    } else {
                        System.out.println("使用了" + card.getName());
                        //获取slot中前三张卡牌
                        Card card1 = eliminateBox.removeByIndex(0);
                        Card card2 = eliminateBox.removeByIndex(0);
                        Card card3 = eliminateBox.removeByIndex(0);
                        //加入额外区域list中
                        extraBox.addToList(card1);
                        extraBox.addToList(card2);
                        extraBox.addToList(card3);
                        //移出功能置灰
                        card.setGray(true);
                    }
                } else if (card.getName().equals("撤回道具")) {
                    if (eliminateBox.getMaxWeight() != eliminateBox.getCount() || eliminateBox.getCount() == 0) {
                        System.out.println("当前无法使用撤回道具！");
                    } else {
                        System.out.println("使用了" + card.getName());
                        //得到牌
                        Card card123 = eliminateBox.removeMaxCard();

                        //判断 如果是额外区的牌放回额外区
                        if(card123.getReturnY() == 525){
                            extraBox.addToList(card123);
                        }else{
                            Start.map.withdraw(card123);
                        }
//                        if(card123.getY())
                        //如果是普通区的牌放回普通区
                        card.setGray(true);
                    }

                } else if (card.getName().equals("洗牌道具")) {
                    System.out.println("使用了" + card.getName());
                    //调map进行牌组打乱
                    Start.map.shuffleCards();
                    //洗牌功能置灰
                    card.setGray(true);
                } else {
                    System.out.println(card.getName() + "被收集！");
                    eliminateBox.addToSlot(card);//加入消除区域
                    //当前图层位置的单元格置空,让牌消失
                    self.getCell().setState(0);
                    self.getCell().setCard(null);
                    self.setCell(null);
                    //移牌后判定其他所有牌是否置灰
                    Start.map.ifTurnGray();
                }
            }
        });
    }

    //成员方法
    @Override
    public void paint(Graphics g) {//绘制Card等组件,observer参数为改动时向谁提醒
        if (isGray) g.drawImage(this.getGrayImage(), x, y, null);
        else g.drawImage(this.getImage(), x, y, null);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReturnX() {
        return returnX;
    }

    public void setReturnX(int returnX) {
        this.returnX = returnX;
    }

    public int getReturnY() {
        return returnY;
    }

    public void setReturnY(int returnY) {
        this.returnY = returnY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getGrayImage() {
        return grayImage;
    }

    public void setGrayImage(Image grayImage) {
        this.grayImage = grayImage;
    }

    public Boolean getGray() {
        return isGray;
    }

    public void setGray(Boolean gray) {
        isGray = gray;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }


}
