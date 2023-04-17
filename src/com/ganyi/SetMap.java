package com.ganyi;

import com.ganyi.model.Layer;
import com.ganyi.model.Level;
import com.ganyi.model.Map;

/**
 * 设置生成的地图类型
 */
public class SetMap {

    public static Map buildMap(){
        //第一关
        Level firstLevel = new Level();
        firstLevel.setCount(1);//第一关
        firstLevel.setDifficulty(1);//简单难度
        firstLevel.setEliminateBoxSize(7);//消除栏长度

        //构建地图
        Map map = new Map();
        map.setFloorHeight(5);//层高为三层
//        map.setX(100);
//        map.setY(200);//map绘制的左上角坐标为(100,,200)

        firstLevel.setMap(map);

        //构建图层
        Layer layer1 = SetLayer.buildLayer(3, 5);
        Layer layer2 = SetLayer.buildLayer(6, 6);
        Layer layer3 = SetLayer.buildLayer(3, 5);
        Layer layer4 = SetLayer.buildLayer(3, 3);
        Layer layer5 = SetLayer.buildLayer(5, 3);

        layer5.setUpperLayer(layer4);
        layer4.setUpperLayer(layer3);
        layer3.setUpperLayer(layer2);
        layer2.setUpperLayer(layer1);
        layer1.setUpperLayer(null);

        map.getLayers().add(layer1);
        map.getLayers().add(layer2);
        map.getLayers().add(layer3);
        map.getLayers().add(layer4);
        map.getLayers().add(layer5);

        return map;
    }
}
