package com.mygdx.game.desktop;
import java.util.ArrayList;

public class Weapon {
    private ArrayList<Projectile> projectileList;

    //add weapon characteristics

    public Weapon()
    {
        this.projectileList = new ArrayList<>();
    }

    public void fireWeapon(int angle, float x, float y){
        Projectile p = new Projectile(1000, angle, x, y);
        projectileList.add(p);
    }
}
