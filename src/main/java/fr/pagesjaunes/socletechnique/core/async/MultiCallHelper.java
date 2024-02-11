package fr.pagesjaunes.socletechnique.core.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

@Component
@Slf4j
public class MultiCallHelper {

    @Autowired
    private Executor executor;

    public <T, R> List<R> execute(List<T> data, Function<T, R> action) {
        var futures = data.stream().map(d -> {
            FutureTask<R> f = new FutureTask<>(() -> action.apply(d));
            executor.execute(f);
            return f;
        }).toList();
        return futures.stream().map(rFuture -> {
            try {
                return rFuture.get();
            } catch (InterruptedException e) {
                LOGGER.error("Multi-call interrupted", e);
                Thread.currentThread().interrupt();
                throw new MultiCallHelperException("Multi-call interrupted", e);
            } catch (ExecutionException e) {
                LOGGER.error("Muti-call execution error", e);
                throw new MultiCallHelperException("Multi-call execution error", e);
            }
        }).toList();
    }

    private static class MultiCallHelperException extends RuntimeException {

        public MultiCallHelperException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
