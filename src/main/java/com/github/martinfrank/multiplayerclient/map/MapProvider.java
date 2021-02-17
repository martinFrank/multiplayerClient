package com.github.martinfrank.multiplayerclient.map;

import com.github.martinfrank.multiplayerclient.MultiPlayerMetaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tiledreader.FileSystemTiledReader;
import org.tiledreader.TiledMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MapProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapProvider.class);

    private static final String MAP = "map.tmx";

    private final TiledMap tiledMap;

    public MapProvider(MultiPlayerMetaClient metaClient) throws Exception {
        downloadMap(metaClient);
        String jarDirectory = FileUtils.getJarDirectory();
        String areaDownloadDir = "maps";
        String areaMapId = "templeTest";
        String mapName = jarDirectory + "/"
                + areaDownloadDir + "/"
                + areaMapId + "/"
                + MAP;
        LOGGER.debug("loading map: {}", mapName);
        FileSystemTiledReader fileSystemTiledReader = new FileSystemTiledReader();
        tiledMap = fileSystemTiledReader.getMap(mapName);
    }

    private void downloadMap(MultiPlayerMetaClient metaClient) {
        String areaDownloadDir = "maps";
        String dlFilename = "download.zip";
        String areaMapId = "templeTest";
        try {
            String jarDirectory = FileUtils.getJarDirectory();
            String mapDir = FileUtils.createDirectory(areaDownloadDir);
            String downloadFilename = FileUtils.reCreateFile(mapDir, dlFilename);
            LOGGER.debug("downloadFilename {}", downloadFilename);
            File zipFile = metaClient.downloadMap(downloadFilename, areaMapId);
            LOGGER.debug("ZipFile: {}", zipFile);
            UnzipUtility.unzip(zipFile, jarDirectory + "/maps/" + areaMapId);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TiledMap getMap() {
        return tiledMap;
    }
}
