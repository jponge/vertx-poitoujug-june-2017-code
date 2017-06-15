package livecoding;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.deployVerticle("livecoding.FakeBiddingVerticle");

    vertx.deployVerticle("livecoding.FakeBiddingVerticle",
      new DeploymentOptions().setConfig(new JsonObject().put("port", 3000)));

    vertx.deployVerticle("livecoding.FakeBiddingVerticle",
      new DeploymentOptions().setConfig(new JsonObject().put("port", 3001)));

    vertx.deployVerticle("livecoding.BiddingGatewayVerticle");
  }
}
