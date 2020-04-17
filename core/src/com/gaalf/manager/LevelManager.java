package com.gaalf.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LevelManager {

    private ArrayList<FileHandle> levels;
    private ArrayList<FileHandle> mapPacks;
    private String mapPack;
    private int level;
    private TmxMapLoader tmxMapLoader;
    private Random random;

    public LevelManager(){
        levels = new ArrayList<>();
        mapPacks = new ArrayList<>();
        mapPacks.addAll(Arrays.asList(Gdx.files.internal("levels").list()));
        tmxMapLoader = new TmxMapLoader();
        random = new Random();
    }

    public TiledMap loadLevel(FileHandle level){
        this.level = Integer.parseInt(level.nameWithoutExtension());
        return tmxMapLoader.load("levels/" + mapPack + "/" + level.name());
    }

    public boolean hasNext(){
        return level < levels.size();
    }

    public TiledMap nextLevel(){
        level++;
        return tmxMapLoader.load("levels/" + mapPack + "/" + level + ".tmx");
    }

    public FileHandle getRandomLevel(){
        String mapPack = mapPacks.get(random.nextInt(mapPacks.size())).name();
        this.mapPack = mapPack;
        FileHandle[] levels = Gdx.files.internal("levels/" + mapPack + "/").list(".tmx");
        return levels[random.nextInt(levels.length)];
    }

    public ArrayList<FileHandle> getMapPacks(){
        return mapPacks;
    }

    public FileHandle getFirstMapPackLevel(){
        return Gdx.files.internal("levels/" + mapPack + "/" + "1.tmx");
    }

    public void setLevels(String levelPack){
        this.mapPack = levelPack;
        this.levels.clear();
        this.levels.addAll(Arrays.asList(Gdx.files.internal("levels/" + levelPack + "/").list(".tmx")));
    }

    public ArrayList<FileHandle> getLevels(){
        return levels;
    }

    public int getRemainingLevels(){
        return levels.size() - level;
    }

    public int getLevelInt(){
        return level;
    }


}
