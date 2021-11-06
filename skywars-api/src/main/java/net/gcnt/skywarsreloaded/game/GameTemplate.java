package net.gcnt.skywarsreloaded.game;

import net.gcnt.skywarsreloaded.utils.Coord;

import java.util.List;

public interface GameTemplate {

    String getName();

    String getCreator();

    void setCreator(String creator);

    String getDisplayName();

    int getTeamSize();

    int getMinPlayers();

    void setMinPlayers(int amount);

    Coord getWaitingLobbySpawn();

    void setWaitingLobbySpawn(Coord loc);

    Coord getSpectateSpawn();

    void setSpectateSpawn(Coord loc);

    void disable();

    void enable();

    void loadData();

    void saveData();

    boolean addChest(Coord loc);

    boolean removeChest(Coord loc);

    List<Coord> getChests();

    void addSpawn(int team, Coord loc);

    void removeSpawn(Coord loc);

    List<List<Coord>> getTeamSpawnpoints();


}