package com.gaalf.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelManager {

    private ArrayList<FileHandle> levels;
    private ArrayList<FileHandle> mapPacks;
    private String levelPack;
    private int level;
    private TmxMapLoader tmxMapLoader;

    public LevelManager(){
        levels = new ArrayList<>();
        mapPacks = new ArrayList<>();
        mapPacks.addAll(Arrays.asList(Gdx.files.internal("levels").list()));
        tmxMapLoader = new TmxMapLoader();
    }

    public TiledMap loadLevel(FileHandle level){
        this.level = Integer.parseInt(level.nameWithoutExtension());
        return tmxMapLoader.load("levels/" + levelPack + "/" + level.name());
    }

    public boolean hasNext(){
        return level < levels.size();
    }

    public TiledMap nextLevel(){
        level++;
        return tmxMapLoader.load("levels/" + levelPack + "/" + level + ".tmx");
    }

    public ArrayList<FileHandle> getMapPacks(){
        return mapPacks;
    }

    public void setLevels(String levelPack){
        this.levelPack = levelPack;
        this.levels.clear();
        this.levels.addAll(Arrays.asList(Gdx.files.internal("levels/" + levelPack + "/").list(".tmx")));
    }

    public ArrayList<FileHandle> getLevels(){
        return levels;
    }


}
