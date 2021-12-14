import Members.Admin;
import Members.Moderator;
import Members.User;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public final class Application {

    public static void main(String[] args) {

        final var vertx = Vertx.vertx();
        vertx.deployVerticle(new Admin());

        vertx.deployVerticle(new Moderator());
        vertx.deployVerticle(new Moderator());

        final User.UserFactory userFactory = new User.UserFactory();
        vertx.registerVerticleFactory(userFactory);
        final DeploymentOptions optionsUser = new DeploymentOptions().setInstances(5);
        vertx.deployVerticle(userFactory.prefix() + ":" + User.class.getName(), optionsUser);

    }
}
