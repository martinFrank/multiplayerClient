package com.github.martinfrank.multiplayerclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.martinfrank.multiplayerclient.model.AreaModel;
import com.github.martinfrank.multiplayerprotocol.area.Message;
import com.github.martinfrank.multiplayerprotocol.area.PlayerRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MultiPlayerAreaClient implements Runnable {


    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPlayerAreaClient.class);

    static ByteBuffer buffer = ByteBuffer.allocate(256);

    private final SocketChannel socketChannel;
    private final Selector selector;

    private final ObjectMapper mapper = new ObjectMapper();
    private final ClientMessageParser parser;

    public MultiPlayerAreaClient(String server, int port, AreaModel model) throws IOException {
        InetSocketAddress address = new InetSocketAddress(server, port);
        socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);

        parser = new ClientMessageParser(model);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel) key.channel();
        StringBuilder sb = new StringBuilder();

        buffer.clear();
        int read = 0;
        while ((read = ch.read(buffer)) > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            sb.append(new String(bytes));
            buffer.clear();
        }
        String msg;
        if (read < 0) {
            msg = key + " left the chat.\n";
            ch.close();
        } else {
            msg = sb.toString();
            handleIncomingServerMessage(msg);
        }


    }

    private void handleIncomingServerMessage(String messageRaw) {
        try {
            Message message = mapper.readValue(messageRaw, Message.class);
            parser.parse(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void handleWrite(SelectionKey key) throws IOException, InterruptedException {
        String msg = "fizzbuzz";
        ByteBuffer msgBuf = ByteBuffer.wrap(msg.getBytes());

        if (key.isValid() && key.channel() instanceof SocketChannel) {
            SocketChannel sch = (SocketChannel) key.channel();
            sch.write(msgBuf);
            msgBuf.rewind();
        }
        Thread.sleep(1000);
    }

    @Override
    public void run() {

        try {
            Iterator<SelectionKey> keyIterator;
            SelectionKey key;
            while (socketChannel.isOpen()) {
                selector.select();
                keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isReadable()) {
                        handleRead(key);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        ByteBuffer welcomeBuf = ByteBuffer.wrap(message.getBytes());
        try {
            socketChannel.write(welcomeBuf);
            welcomeBuf.rewind();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void register(PlayerRegistration playerRegistration) {
        try {
            //FIXME MessageFactory
            ObjectMapper mapper = new ObjectMapper();
            Message message = new Message();
            message.className = PlayerRegistration.class.getName();
            message.jsonContent = mapper.writeValueAsString(playerRegistration);
            String messageJson = mapper.writeValueAsString(message);
            write(messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
