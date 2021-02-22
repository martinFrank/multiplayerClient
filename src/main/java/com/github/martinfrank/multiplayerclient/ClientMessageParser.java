package com.github.martinfrank.multiplayerclient;

import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerprotocol.area.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;

public class ClientMessageParser extends BaseMessageParser<SelectionKey> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMessageParser.class);

    private final AreaModel model;

    public ClientMessageParser(AreaModel model) {
        super();
        this.model = model;
    }

    @Override
    public void handleAreaTotal(AreaTotal areaTotal, SelectionKey key) {
        super.handleAreaTotal(areaTotal, key);
        LOGGER.debug("handle AreaTotal: {}", areaTotal);
    }

    //FIXME wird nicht gebraucht
    @Override
    public void handlePlayerMovementRequest(PlayerMovementRequest playerMovementRequest, SelectionKey key) {
        super.handlePlayerMovementRequest(playerMovementRequest, key);
        LOGGER.debug("handle PlayerMovementRequest: {}", playerMovementRequest);
    }

    //FIXME wird nicht gebraucht
    @Override
    public void handlePlayerRegistration(PlayerRegistration playerRegistration, SelectionKey key) {
        super.handlePlayerRegistration(playerRegistration, key);
        LOGGER.debug("handle PlayerRegistration: {}", playerRegistration);
    }

    @Override
    public void handleMapChanges(MapChanges mapChanges, SelectionKey key) {
        super.handleMapChanges(mapChanges, key);
        LOGGER.debug("handle MapChanges: {}", mapChanges);
    }

    @Override
    public void handleMonsters(Monsters monsters, SelectionKey key) {
        super.handleMonsters(monsters, key);
        LOGGER.debug("handle Monsters: {}", monsters);
    }

    @Override
    public void handlePlayer(Player player, SelectionKey key) {
        super.handlePlayer(player, key);
        LOGGER.debug("handle Player: {}", player);
    }


}
