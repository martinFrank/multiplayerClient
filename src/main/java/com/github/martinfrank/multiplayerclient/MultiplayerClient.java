package com.github.martinfrank.multiplayerclient;

import com.github.martinfrank.multiplayerclient.map.MapProvider;
import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerprotocol.area.PlayerRegistration;
import com.github.martinfrank.multiplayerprotocol.meta.PlayerMetaData;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tiledreader.TiledMap;

public class MultiplayerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiplayerClient.class);
    private MultiPlayerAreaClient areaClient;
    private MultiPlayerMetaClient metaClient;
    private PlayerMetaData playerMetaData;

    public MultiplayerClient() {
        ClientConfig clientConfig = ConfigFactory.create(ClientConfig.class);
        String server = clientConfig.metaServerAddress();
        int port = clientConfig.metaServerPort();
        metaClient = new MultiPlayerMetaClient(server, port);
    }

    private void connect() {
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

}
