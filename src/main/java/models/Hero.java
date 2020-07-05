package models;

import java.util.Objects;

public class Hero {
    private String name;
    private int age;
    private String specialPower;
    private String weakness;
    private int id;
    private int squadId;

    public Hero(String name, int age, String specialPower, String weakness, int squadId) {
        this.name = name;
        this.age = age;
        this.specialPower = specialPower;
        this.weakness = weakness;
        this.squadId = squadId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSpecialPower() {
        return specialPower;
    }

    public String getWeakness() {
        return weakness;
    }

    public int getId() {
        return id;
    }

    public int getSquadId() {
        return squadId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return age == hero.age &&
                id == hero.id &&
                squadId == hero.squadId &&
                Objects.equals(name, hero.name) &&
                Objects.equals(specialPower, hero.specialPower) &&
                Objects.equals(weakness, hero.weakness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, specialPower, weakness, id, squadId);
    }
}
