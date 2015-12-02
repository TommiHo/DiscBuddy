package fi.lato.discbuddy;

import java.io.Serializable;

/**
 * Created by H4273 on 24.11.2015.
 */
public class Player implements Serializable{
    private String name;
    private int count;
    private int sum;

    public Player(String name, int count) {
        this.name = name;
        this.count = count;
        this.sum = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {return sum;}

    public void addCount() {
        this.count++;
        this.sum++;
    }

    public void decreaseCount() {
        this.count--;
        this.sum--;
    }

    public void resetCount() { this.count = 0; }

}
