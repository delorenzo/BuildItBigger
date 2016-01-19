package com.jdelorenzo;

import org.junit.Assert;
import org.junit.Test;
public class JokeTellerTests {
    @Test
    public void verifyJokeTellerReturnsJoke() {
        JokeTeller jokeTeller = new JokeTeller();
        String result = jokeTeller.getJoke();
        Assert.assertNotNull(result);
        Assert.assertNotEquals(result, "");
    }
}
