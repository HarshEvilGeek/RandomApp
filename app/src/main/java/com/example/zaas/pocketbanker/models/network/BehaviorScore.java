package com.example.zaas.pocketbanker.models.network;

/**
 * Created by zaraahmed on 3/19/16.
 */
public class BehaviorScore
{

    private int score;

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "BehaviorScore{" + "score=" + score + '}';
    }
}
