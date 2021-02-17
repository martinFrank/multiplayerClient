package com.github.martinfrank.multiplayerclient;

import org.junit.Test;

public class HashTest {

    @Test
    public void doIt() {
        String test = "snakeitsasnake";
        int hashCode = test.hashCode();
        System.out.println("hashCode: " + hashCode);

        String salt = "seasalt";
        int saltHash = salt.hashCode();


    }
}
