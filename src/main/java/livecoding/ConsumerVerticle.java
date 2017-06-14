package livecoding;

import io.vertx.core.AbstractVerticle;

public class ConsumerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("tick", message -> {
      System.out.println(message.body());
    });
  }
}
