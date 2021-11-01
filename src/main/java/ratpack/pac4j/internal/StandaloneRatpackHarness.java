package ratpack.pac4j.internal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import ratpack.exec.ExecController;
import ratpack.exec.Operation;
import ratpack.exec.Promise;
import ratpack.exec.Result;
import ratpack.exec.internal.DefaultExecController;
import ratpack.func.Block;
import ratpack.util.Exceptions;

public final class StandaloneRatpackHarness {

  private StandaloneRatpackHarness() {
  }

  public static <T> T get(Promise<T> promise) {
    return get(new DefaultExecController(), promise, false);
  }

  public static void execute(Operation op) {
    execute(op, false);
  }

  public static void execute(Operation op, boolean awaitBackgroundTermination) {
    execute(new DefaultExecController(), op, awaitBackgroundTermination);
  }

  public static void execute(ExecController controller, Operation op, boolean awaitBackgroundTermination) {
    get(controller, op.promise(), awaitBackgroundTermination);
  }

  public static <T> T get(ExecController controller, Promise<T> promise, boolean awaitBackgroundTermination) {
    AtomicReference<Result<T>> resultRef = new AtomicReference<>();
    try {
      CountDownLatch done = new CountDownLatch(1);
      controller.fork()
          .onComplete(e -> done.countDown())
          .start(e -> promise.result(resultRef::set));
      Exceptions.uncheck((Block) done::await);
    } finally {
      controller.close();
      if (awaitBackgroundTermination) {
        Exceptions.uncheck(() -> controller.getExecutor().awaitTermination(10, TimeUnit.MINUTES));
      }
    }

    Result<T> result = resultRef.get();
    if (result == null) {
      // something has done Promise.onError and we didn't get a result
      return null;
    } else if (result.isError()) {
      throw Exceptions.uncheck(result.getThrowable());
    } else {
      return result.getValue();
    }
  }

}