package Members;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.impl.JavaVerticleFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public final class User extends AbstractVerticle {

    private final String name;

    public User(long number) {
        this.name = "user#" + number;
    }


    @Override
    public void start() {

        join();

    }

    public String getName() {
        return name;
    }

    public void join() {

        final String[] clanName = new String[1];

        vertx.setPeriodic(3000, timer -> {

            clanName[0] = selectClan();

            if (clanName[0] == null)
                return;


            vertx.eventBus().request(clanName[0], getName(), reply -> {

                if (reply.succeeded()) {
                    sendMessageToSomeUser(clanName[0]);
                    vertx.cancelTimer(timer);
                    System.out.println(getName() + " has joined the clan");
                }
            });
        });
    }

    public String selectClan() {

        String clanName;

        var clans = vertx.sharedData().getAsyncMap("Clans").result();

        ThreadLocalRandom random = ThreadLocalRandom.current();

        try {
            clanName = String.valueOf(clans.get(random.nextLong(
                    vertx.sharedData().getCounter("clan.number").result().get().result() + 1)).result());
        } catch (NullPointerException e) {
            return null;
        }

        return clanName;
    }

    public void sendMessageToSomeUser(String clanName) {
        vertx.setPeriodic(5000, timer -> {

            var users = vertx.sharedData().getAsyncMap("usersInClan" + clanName).result();

            ThreadLocalRandom random = ThreadLocalRandom.current();

            vertx.sharedData().getCounter("usersNumberInClan" + clanName, counter ->
                    vertx.eventBus().send(String.valueOf(users.get(random.nextLong(vertx.sharedData()
                            .getCounter("users.number").result().get().result() + 1))), "Hello"));
        });
    }

    public static final class UserFactory extends JavaVerticleFactory {
        private long number;

        @Override
        public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
            promise.complete(() -> new User(number++));
        }
    }
}
