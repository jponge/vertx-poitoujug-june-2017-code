package livecoding;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class MainVerticleTest {

  @Test
  public void smoke_test(TestContext testContext) {
    Vertx vertx = Vertx.vertx();
    Async completion = testContext.async();

    vertx.deployVerticle("livecoding.MainVerticle", testContext.asyncAssertSuccess(id -> {
      WebClient client = WebClient.create(vertx);
      client.get(8080, "localhost", "/yo")
        .as(BodyCodec.string())
        .send(testContext.asyncAssertSuccess(response -> {
          String body = response.body();
          testContext.assertEquals("Yo", body);
          completion.complete();
        }));
    }));

    completion.awaitSuccess(5000);
  }
}
