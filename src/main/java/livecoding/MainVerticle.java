package livecoding;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer()
      .requestHandler(request -> {
        request.response()
          .putHeader("Content-Type", "text/plain")
          .end("Hello le PoitouJUG");
      })
      .listen(8080);
  }
}
