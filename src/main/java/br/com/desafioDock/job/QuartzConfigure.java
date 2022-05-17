package br.com.desafioDock.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

@org.springframework.stereotype.Service
public class QuartzConfigure {
    static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    public static void iniciarQuartz() throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
    }
}
