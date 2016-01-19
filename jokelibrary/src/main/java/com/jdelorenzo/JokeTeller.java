package com.jdelorenzo;

import java.util.Random;

public class JokeTeller {
    Random randomGenerator;
    String[] jokes = {
            "Why can't skeletons play church music? \r\nBecause they have no organs.",
            "A friend of mine tried to annoy me with bird puns, but I soon realized that toucan play at that game.," +
            "I wondered why the baseball was getting bigger. Then it hit me.",
            "Did you hear about the guy whose whole left side was cut off? He's all right now.",
            "I'd tell you a chemistry joke but I know I wouldn't get a reaction.",
    };

    public JokeTeller() {
        randomGenerator = new Random();
    }

    public String getJoke() {
        return jokes[randomGenerator.nextInt(jokes.length)];
    }
}
