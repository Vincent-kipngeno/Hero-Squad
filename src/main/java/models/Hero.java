package models;

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
}
