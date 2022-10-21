package com.penta.ps.license.license.service.log;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;


@Service
public class SchedulerService {

    private TaskScheduler scheduler;
    private ScheduledFuture<?> future;

    public SchedulerService(@Qualifier("schedulerExecutor") TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start(Runnable task, String cron) {
        this.stop();
        ScheduledFuture<?> future = this.scheduler.schedule(task, new CronTrigger(cron));
        this.future = future;
    }

    public void stop() {
        if (future != null) {
            future.cancel(true);
            this.future = null;
        }
    }
}
