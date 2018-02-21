package com.example.ryan.mobilegamesassignmentgalaga.Model;

/**
 * Created by Ryan on 21/02/2018.
 */

class Player {

    // Constructor

    public Player()
    {

    }

    // Data Members

    private int iPlayerLives = 3;

    // Member Functions

    // This will be used to check the current value of player lives.
    public int m_iGetPlayerLives()
    {
        return iPlayerLives;
    }

    // This will be used to update the value of player lives. || -1 to lower || +1 to increase ||
    public void m_SetPlayerLives(int iNewValue)
    {
        iPlayerLives += iNewValue;
    }

}
