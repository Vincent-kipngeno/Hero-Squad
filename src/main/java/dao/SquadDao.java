package dao;

import models.Hero;
import models.Squad;

import java.util.List;

public interface SquadDao {
    //LIST
    List<Squad> getAll();
    List<Squad> getAllSquadsWithSpace(List<Squad> squads);//space to accommodate heroes

    //CREATE
    void add (Squad squad);

    //READ
    Squad findById(int id);
    List<Hero> getAllHeroesBySquad(int squadId);

    //UPDATE
    void update(int id, int maximumSize, String name, String causeToFight);

    //DELETE
    void deleteById(int id);
    void clearAllSquads();
}
