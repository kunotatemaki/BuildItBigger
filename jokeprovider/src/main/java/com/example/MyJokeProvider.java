package com.example;

public class MyJokeProvider {
    public String getJokeFromJavaLib(){
        //read the joke from somewhere. In this case, the var readOK will be allways true
        //but in a real case it could be false due to any error
        boolean readOk = true;
        String joke = "";
        if(readOk){
            joke = "This a really funny joke";
        }
        return joke;
    }
}
