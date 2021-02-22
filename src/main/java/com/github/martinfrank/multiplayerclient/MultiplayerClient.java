package com.github.martinfrank.multiplayerclient;

import com.github.martinfrank.multiplayerclient.map.MapProvider;
import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerprotocol.area.PlayerRegistration;
import com.github.martinfrank.multiplayerprotocol.meta.PlayerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tiledreader.TiledMap;

public class MultiplayerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplayerClient.class);
    private MultiPlayerAreaClient areaClient;
    private MultiPlayerMetaClient metaClient;
    private PlayerMetaData playerMetaData;
    private final MultiplayerClientConfig clientConfig;
    private final MapProvider mapProvider;


    public MultiplayerClient(MultiplayerClientConfig clientConfig) throws Exception {
        this.clientConfig = clientConfig;
        metaClient = new MultiPlayerMetaClient(clientConfig.getAddress(), clientConfig.getPort());
        mapProvider = new MapProvider(metaClient);
    }

    //FIXME
    public void connectToArea() {
        playerMetaData = metaClient.getPlayerData("Mosh", "swordFish");
        String areaId = playerMetaData.playerAreaId;
        LOGGER.debug("connect, Player Meta Data: {}", playerMetaData);
        try {
            TiledMap map = new MapProvider(metaClient).getMap();
            AreaModel model = new AreaModel(map);

            areaClient = new MultiPlayerAreaClient("192.168.0.69", 10523, model);
            new Thread(areaClient).start();
            PlayerRegistration playerRegistration = new PlayerRegistration(playerMetaData.userId);
            areaClient.register(playerRegistration);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PlayerMetaData getPlayerData() {
        return metaClient.getPlayerData(clientConfig.getUser(), clientConfig.getPassword());
    }

    public TiledMap getMap() {
        return mapProvider.getMap();
    }

    public MultiPlayerAreaClient getAreaClient() {
        return areaClient;
    }
}
