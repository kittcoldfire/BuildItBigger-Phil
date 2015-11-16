package com.jokeprovider;

public class Jokes {

    public String getJoke(int jokeNum) {
        String joke = "";

        switch (jokeNum)
        {
            case 0:
                joke = "Q: Where would you learn how to make ice cream?\n" +
                        "A: At Sundae school.";
                break;
            case 1:
                joke = "Q: Why did the blonde keep taking off the soda's bottle cap and putting it back on?\n" +
                        "A: The bottle cap said, \"Sorry, try again.\"";
                break;
            case 2:
                joke = "Q: What has a mouth but no teeth?\n" +
                        "A: A river.";
                break;
            case 3:
                joke = "Q: What do you call a person who likes to hang around with musicians?\n" +
                        "A: A drummer.";
                break;
            case 4:
                joke = "Q: What did did the mother duck say to her duckling?\n" +
                        "A: \"If you don't behave, I'm gonna quack you one.\"";
                break;
            default:
                joke = "Jokes on you!";
                break;
        }


        return joke;
    }
}
