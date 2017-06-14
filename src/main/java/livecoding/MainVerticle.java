package livecoding;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {

    vertx.deployVerticle("livecoding.ConsumerVerticle");

    vertx.setPeriodic(1000, id -> {
      vertx.eventBus().publish("tick", "!");
    });
  }
}
