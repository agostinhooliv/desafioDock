package br.com.desafioDock;

import br.com.desafioDock.job.ContaJob;
import br.com.desafioDock.job.QuartzConfigure;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioDockApplication {

    @Autowired
    private static QuartzConfigure quartzConfigure;

    private static ContaJob contaJob = new ContaJob();

    public static void main(String[] args) {
        SpringApplication.run(DesafioDockApplication.class, args);
        try {
            quartzConfigure.iniciarQuartz();
            contaJob.iniciaJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
