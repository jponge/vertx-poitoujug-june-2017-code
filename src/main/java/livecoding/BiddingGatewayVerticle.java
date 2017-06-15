package livecoding;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.rxjava.ext.web.client.WebClient;
import io.vertx.rxjava.ext.web.codec.BodyCodec;
import rx.Observable;

public class BiddingGatewayVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    WebClient client = WebClient.create(vertx);
    vertx.createHttpServer()
      .requestHandler(request -> {

        Observable<HttpResponse<JsonObject>> bid1 = client.get(8080, "localhost", "/")
          .as(BodyCodec.jsonObject())
          .rxSend()
          .toObservable();

        Observable<HttpResponse<JsonObject>> bid2 = client.get(3000, "localhost", "/")
          .as(BodyCodec.jsonObject())
          .rxSend()
          .toObservable();

        Observable<HttpResponse<JsonObject>> bid3 = client.get(3001, "localhost", "/")
          .as(BodyCodec.jsonObject())
          .rxSend()
          .toObservable();

        Observable.merge(bid1, bid2, bid3)
          .reduce((acc, next) -> {
            if (acc.body().getInteger("bid") < (next.body().getInteger("bid"))) {
              return next;
            } else {
              return acc;
            }
          })
          .subscribe(bestResponse -> {
            request.response()
              .putHeader("Content-Type", "application/json")
              .end(bestResponse.body().encode());
          }, err -> {
            request.response().end("Error: " + err.getMessage());
          });

      })
      .rxListen(4000)
      .subscribe();
  }
}
