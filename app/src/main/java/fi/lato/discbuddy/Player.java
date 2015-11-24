package fi.lato.discbuddy;

/**
 * Created by H4273 on 24.11.2015.
 */
public class Player {
    private String name;
    private int count;

    public Player(String name, int count) {
        this.name = name;
        this.count = count;
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

    public void addCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }

}
