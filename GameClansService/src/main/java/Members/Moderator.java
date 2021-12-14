package Members;

import io.vertx.core.AbstractVerticle;

public final class Moderator extends AbstractVerticle {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void start() {

        addModeratorInAsyncMap();

    }


    public void addUserToClan() {

        vertx.eventBus().<String>consumer(getName(), event ->
                vertx.eventBus().<Integer>request("users.limit", null, reply -> {

                    if (reply.result().body()
                            > vertx.sharedData().getAsyncMap("usersInClan" + getName()).result().size().result()) {

                        vertx.sharedData().getCounter("usersNumberInClan" + getName(), counter ->
                                counter.result().incrementAndGet(number ->
                                        vertx.sharedData().getAsyncMap("usersInClan" + getName()).result()
                                                .put(number.result(), event.body())));

                        event.reply(reply.succeeded());

                    } else
                        event.reply(reply.failed());

                }));
    }


    public void addModeratorInAsyncMap() {

        vertx.setPeriodic(1000, timer ->

                vertx.eventBus().<String>request("clan.name", null, reply -> {

                    if (reply.result().body() == null)
                        return;

                    vertx.cancelTimer(timer);
                    setName(reply.result().body());

                    vertx.sharedData().getCounter("moderatorsNumberInClan" + getName(), counter ->
                            counter.result().incrementAndGet(number ->
                                    vertx.sharedData().getAsyncMap("moderatorsInClan" + getName()).result()
                                            .put(number.result(), getName())));

                    vertx.eventBus().publish("moderator.ready", null);

                    addUserToClan();
                }));
    }

}
