package livecoding;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.Random;
import java.util.UUID;

public class FakeBiddingVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    Random random = new Random();
    UUID myId = UUID.randomUUID();

    vertx.createHttpServer()
      .requestHandler(request -> {
        int myBid = random.nextInt(10) + 100;
        request.response()
          .putHeader("Content-Type", "application/json")
          .end(new JsonObject()
            .put("id", myId.toString())
            .put("bid", myBid)
            .encode());
      })
      .listen(config().getInteger("port", 8080));
  }
}
