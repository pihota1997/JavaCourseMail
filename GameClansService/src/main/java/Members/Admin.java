package Members;

import Common.Clan;
import io.vertx.core.AbstractVerticle;

public final class Admin extends AbstractVerticle {

    private final Clan clan;

    public Admin() {
        this.clan = new Clan("Common.Clan#" + (int) (Math.random() * 100),
                (int) (Math.random() * 100), (int) (Math.random() * 10));
    }

    public Clan getClan() {
        return clan;
    }

    @Override
    public void start() {

        addClanToAsyncMap();

        returnClanName();

        returnUsersLimit();

        controlLimit();

    }


    private void addClanToAsyncMap() {

        vertx.sharedData().getCounter("clan.number", counter ->
                counter.result().incrementAndGet(number ->
                    vertx.sharedData().getLocalMap("Clans").put(number.result(), getClan().getName())));

    }


    private void returnClanName() {
        vertx.eventBus().consumer("clan.name", event -> event.reply(getClan().getName()));
    }

    private void returnUsersLimit() {
        vertx.eventBus().consumer("users.limit", event -> event.reply(getClan().getUsersLimit()));
    }

    private void controlLimit() {
        vertx.setPeriodic(5000, timer -> {

            if(vertx.sharedData().getLocalMap("usersInClan" + getClan().getName()).size() > getClan().getUsersLimit() ||
            vertx.sharedData().getLocalMap("moderatorInClan" + getClan().getName()).size() > getClan().getModeratorsLimit())
                vertx.eventBus().publish("limit.exceeded", "Please log out and then apply to join the clan again");

        });
    }
}

