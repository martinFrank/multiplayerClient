package com.github.martinfrank.multiplayerclient.model;

import com.github.martinfrank.multiplayerprotocol.area.Monster;
import com.github.martinfrank.multiplayerprotocol.area.Player;
import org.tiledreader.TiledMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AreaModel {

    private final TiledMap map;

    private List<Monster> monster = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    public AreaModel(TiledMap map) {
        this.map = map;
    }

    public String getBackgroundFilename() {
        return new File(map.getPath()).getParent() + "/TempleTest.png";
    }
}
