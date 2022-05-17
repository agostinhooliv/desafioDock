package br.com.desafioDock.job;

import br.com.desafioDock.rabbit.Rabbitmq;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class ContaJob implements Job {

    private Rabbitmq rabbitmq = new Rabbitmq();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Lendo o conteúdo da fila!");
        try {
            rabbitmq.consumirFila();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniciaJob() throws SchedulerException {
        JobDetail job1 = JobBuilder.newJob(ContaJob.class).withIdentity("contaJob", "group1").build();

        Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "group1")
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever()).build();
        Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
        scheduler1.start();
        scheduler1.scheduleJob(job1, trigger1);

    }
}
