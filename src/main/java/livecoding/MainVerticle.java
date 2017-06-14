package livecoding;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    Router router = Router.router(vertx);

    router.get("/yo").handler(context -> {
      context.response()
        .putHeader("Content-Type", "text/plain")
        .end("Yo");
    });

    router.get("/niort").handler(context -> {
      JsonObject payload = new JsonObject()
        .put("city", "niort")
        .put("rating", 5);
      context.response()
        .putHeader("Content-Type", "application/json")
        .end(payload.encode());
    });

    vertx.createHttpServer()
      .requestHandler(router::accept)
      .listen(8080, server -> {
        if (server.succeeded()) {
          System.out.println("Cool");
        } else {
          server.cause().printStackTrace();
        }
      });
  }
}
