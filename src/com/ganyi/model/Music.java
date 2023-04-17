package com.ganyi.model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 播放背景音乐
 */
public class Music {

    public Music() throws FileNotFoundException, JavaLayerException {
        //获得播放器
        Player player;
        //获得资源地址
        String urlStr = System.getProperty("user.dir") + "/music/music.mp3";
        BufferedInputStream name = new BufferedInputStream(new FileInputStream(urlStr));
        player = new Player(name);
        //播放
        player.play();
    }
}
