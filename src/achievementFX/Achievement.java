package achievementFX;

import database.Connect;
import profile.Soilder;
import profile.boss;

import java.sql.SQLException;

public class Achievement {
    static Connect con = new Connect();

    public int beHero() throws SQLException {//done
        int bool = 0;
        String name = "命中注定";
        if (con.accomplishAch(name) == 1)
            bool = 1;
        else
            bool = 0;
        return bool;
    }

    public int moreSpeed(int round, boss mao) throws SQLException {//done
        int bool = 0;
        String name = "快還要再快";
        if (round <= 5 && mao.life == 0) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }

    public int hackHero(Soilder yusha, boss mao) throws SQLException {//done
        int bool = 0;
        String name = "真材實料";
        if (yusha.attack == 2500 && yusha.defend == 100 && yusha.highest_life == 5849 && mao.life == 0) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }

    public int runAway() throws SQLException {//done
        int bool;
        String name = "懦弱的選擇";
        if (con.accomplishAch(name) == 1)
            bool = 1;
        else
            bool = 0;
        return bool;
    }

    public int firstDie() throws SQLException {//done
        int bool = 0;
        String name = "接受命運";
        if (con.accomplishAch(name) == 1)
            bool = 1;
        else
            bool = 0;
        return bool;
    }

    public int takeAllWeapon(int weapon[]) throws SQLException {//done(有問題)
        int bool = 0;
        String name = "我全都要";
        if (weapon[0] == 1 && weapon[1] == 1 && weapon[2] == 1) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }

    public int otkMao(int round, boss mao) throws SQLException {//done
        int bool = 0;
        String name = "有人作弊R";
        if (round == 1 && mao.life == 0) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }

    public int modifyMaoHighestLife(boss mao) throws SQLException {//done
        int bool = 0;
        String name = "魔王的靈壓消失了";
        if (mao.highest_life == 0) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }

    public int eatParsley() throws SQLException {//done
        String name = "真正的勇者";
        int bool = 0;
        if (con.accomplishAch(name) == 1)
            bool = 1;
        else
            bool = 0;
        return bool;
    }

    public int noTakeWeapon() throws SQLException {//done
        int bool = 0;
        String name = "勇往直前";
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        return bool;
    }

    public int noTakeDamage(int damage) throws SQLException {
        int bool = 0;
        String name = "你什麼時候產生了有傷到我的錯覺";
        if (damage == 0) {
            if (con.accomplishAch(name) == 1)
                bool = 1;
            else
                bool = 0;
        }
        return bool;
    }
}