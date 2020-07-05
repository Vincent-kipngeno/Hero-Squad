package models;

public class Squad {
    private int maximumSize;
    private String name;
    private String causeToFight;
    private int id;

    public Squad(int maximumSize, String name, String causeToFight) {
        this.maximumSize = maximumSize;
        this.name = name;
        this.causeToFight = causeToFight;
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public String getName() {
        return name;
    }

    public String getCauseToFight() {
        return causeToFight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
