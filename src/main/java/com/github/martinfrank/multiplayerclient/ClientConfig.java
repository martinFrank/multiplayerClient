package com.github.martinfrank.multiplayerclient;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:ClientConfig.properties"})
public interface ClientConfig extends Config {

    @Key("meta.server.address")
    String metaServerAddress();

    @Key("meta.server.port")
    int metaServerPort();

}
