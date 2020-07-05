package dao;

import models.Hero;
import models.Squad;

import java.util.List;

public interface SquadDao {
    //LIST
    List<Squad> getAll();

    //CREATE
    void add (Squad squad);

    //READ
    Squad findById(int id);
    List<Hero> getAllHeroesBySquad(int categoryId);

    //UPDATE
    void update(int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllSquads();
}
