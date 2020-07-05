package models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return maximumSize == squad.maximumSize &&
                id == squad.id &&
                Objects.equals(name, squad.name) &&
                Objects.equals(causeToFight, squad.causeToFight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maximumSize, name, causeToFight, id);
    }
}
