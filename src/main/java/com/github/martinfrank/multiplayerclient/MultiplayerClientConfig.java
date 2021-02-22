package com.github.martinfrank.multiplayerclient;

public class MultiplayerClientConfig {

    private final String address;
    private final int port;
    private final String user;
    private final String password;

    public MultiplayerClientConfig(String address, int port, String user, String password) {
        this.address = address;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "MultiplayerMetaClientConfig{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
