package com.example.kasperterp.pacmang;
public class GoldCoin {
    public boolean taken;
    public int posX;
    public int posY;
    public boolean init;

    public GoldCoin(int posX, int posY, boolean taken, boolean init) {
        this.posX = posX;
        this.posY = posY;
        this.taken = taken;
        this.init = init;

    }
}