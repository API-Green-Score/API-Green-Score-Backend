package fr.pagesjaunes.socletechnique.webmvc.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Slf4j
public class ContextAwarePoolExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(@NonNull Runnable task) {
        super.execute(new ContextAwareRunnable(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Override
    @NonNull
    public Future<?> submit(@NonNull Runnable task) {
        return super.submit(new ContextAwareRunnable(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Override
    @NonNull
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return super.submit(new ContextAwareCallable<T>(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Override
    @NonNull
    public ListenableFuture<?> submitListenable(@NonNull Runnable task) {
        return super.submitListenable(new ContextAwareRunnable(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Override
    @NonNull
    public <T> ListenableFuture<T> submitListenable(@NonNull Callable<T> task) {
        return super.submitListenable(
                new ContextAwareCallable<T>(task, RequestContextHolder.currentRequestAttributes()));
    }

    @Slf4j
    private static class ContextAwareCallable<T> implements Callable<T> {
        private final Callable<T> task;
        private final RequestAttributes context;

        public ContextAwareCallable(Callable<T> task, RequestAttributes context) {
            this.task = task;
            this.context = context;
        }

        @Override
        public T call() throws Exception {
            if (context != null) {
                RequestContextHolder.setRequestAttributes(context);
            }

            try {
                return task.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }

    public static class ContextAwareRunnable implements Runnable {

        private final Runnable task;
        private final RequestAttributes context;

        public ContextAwareRunnable(Runnable task, RequestAttributes context) {
            this.task = task;
            this.context = context;
        }

        @Override
        public void run() {
            if (context != null) {
                RequestContextHolder.setRequestAttributes(context);
            }
            try {
                task.run();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}
