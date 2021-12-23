package Common;


public final class Clan{

    private String name;

    private int usersLimit;

    private int moderatorsLimit;

    public Clan(String name, int usersLimit, int moderatorsLimit) {
        this.name = name;
        this.usersLimit = usersLimit;
        this.moderatorsLimit = moderatorsLimit;
    }

    public String getName() {
        return name;
    }

    public int getUsersLimit() {
        return usersLimit;
    }

    public int getModeratorsLimit() {
        return moderatorsLimit;
    }
}
