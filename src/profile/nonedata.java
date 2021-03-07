package profile;
import achievementFX.Achievement;

import java.sql.SQLException;
import java.util.Random;
public class nonedata { //otk boss
	Random random = new Random();
	public String name = "";      //名稱
	public int life = 1;                  //當前生命值
	public int highest_life = 1;                  //最高生命值
	public int attack = 1;                //攻擊力
	public int defend = 1;    //防禦力
    public String property = "fire";
	//////////////////////////////////////////////////////////////////////
	public  void setName(String name){this.name = name;}

	public  String getProperty(){
		return property;
	}

	public  void  setProperty(String property){
		this.property = property;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getHighest_life() {
		return highest_life;
	}

	public void setHighest_life(int highest_life) {
		this.highest_life = highest_life;
	}

	public int getDefend() {
		return defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	public void skill() {
	} //選擇要使哪個技能
	public void fangyu(int attack,String property) {  //放入被攻擊者的攻擊力進行攻擊
		attack *= random.nextDouble()*0.2+1;
		int hurt = 0;              //受到的傷害
		int residue_life;          //剩餘生命值
		if (die()) {               //呼叫下面的陣亡處理函式
			if (attack > defend) {    //對手攻擊力大於自身防禦力則受到傷害，否則受傷值為0
				hurt = attack - defend;
			} else {
				hurt = 0;
			}
			switch(property){
				case "fire":
					break;
				case "water":
					break;
				case "wood":
					break;
				default:
			}
			residue_life = life - hurt;                               //每回合計算剩餘生命值
			System.out.println(this.name + "受到" + hurt + "點傷害！");//列印受到多少傷害
			if (residue_life < 0)
				residue_life = 0;
			System.out.println("還剩" + residue_life + "點生命值");          //列印剩餘生命值
			life = residue_life;                                      //當前生命值
		}
	}
	public int fangyu(int attack) throws SQLException {  //放入被攻擊者的攻擊力進行攻擊
		Achievement a = new Achievement();
		attack *= random.nextDouble()*0.5+1;
		int hurt = 0;              //受到的傷害
		int residue_life;          //剩餘生命值
		if (die()) {               //呼叫下面的陣亡處理函式
			if (attack > defend) {    //對手攻擊力大於自身防禦力則受到傷害，否則受傷值為0
				hurt = attack - defend;
				a.noTakeDamage(hurt);
			} else {
				hurt = 0;
			}
			residue_life = life - hurt;                               //每回合計算剩餘生命值
			System.out.println(this.name + "受到" + hurt + "點傷害！");//列印受到多少傷害
			if (residue_life < 0)
				residue_life = 0;
			System.out.println("還剩" + residue_life + "點生命值");          //列印剩餘生命值
			life = residue_life;                                      //當前生命值
		}
		return -hurt;
	}
	public void usefood(int heal) {  //放入要使用的食物的治癒輛
		int residue_life;          //剩餘生命值
		if (die()) {               //呼叫下面的陣亡處理函式
			residue_life = life + heal;                               //每回合計算剩餘生命值
			System.out.println(this.name + "補了" + heal + "點血量！");//列印補出的血
			if (residue_life > highest_life)
				residue_life = highest_life;
			System.out.println("還剩" + residue_life + "點生命值");          //列印剩餘生命值
			life = residue_life;                                      //當前生命值
		}
	}
	//陣亡時的處理函式，返回一個布林值，代表是否死亡
	public boolean die() {
		boolean flag = true;                     //初始化，代表活著
		if (life <= 0) {                       //生命值小於等於0則陣亡
			System.out.println(this.name + "陣亡,遊戲結束！");
			flag = false;
		}
		return flag;                           //返回生存狀態
	}
	public void showhp() {
		System.out.println("還剩" + life + "點生命值");
	}//顯示當前hp
}