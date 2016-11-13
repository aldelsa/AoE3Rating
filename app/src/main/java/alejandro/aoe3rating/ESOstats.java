package alejandro.aoe3rating;

/**
 * Created by Alex on 13/11/16.
 */

public class ESOstats {
    private String name;
    private String presence;
    private String lastLogin;
    private int skillLeeve;

    public ESOstats (String name,String presence,String lastLogin,int skillLeeve) {
        this.name = name;
        this.presence = presence;
        this.lastLogin = lastLogin;
        this.skillLeeve = skillLeeve;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getSkillLeeve() {
        return skillLeeve;
    }

    public void setSkillLeeve(int skillLeeve) {
        this.skillLeeve = skillLeeve;
    }
}
