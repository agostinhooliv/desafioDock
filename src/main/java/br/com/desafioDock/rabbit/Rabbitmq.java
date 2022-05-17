package br.com.desafioDock.rabbit;

import br.com.desafioDock.business.ContaBusiness;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Rabbitmq {

    private final static String QUEUE_NAME = "transacoes";
    private ConnectionFactory factory;
    @Autowired
    private ContaBusiness contaBusiness;

    public Rabbitmq() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("myuser");
        factory.setPassword("mypassword");
    }

    public void pulicarFila(Long idConta, Double valorSaque) throws Exception {

        StringBuilder mensagem = new StringBuilder();

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            mensagem.append(idConta);
            mensagem.append("-");
            mensagem.append(valorSaque);

            channel.basicPublish("", QUEUE_NAME, null, mensagem.toString().getBytes("UTF-8"));
            System.out.println("Saque enviado para fila '" + mensagem.toString() + "'");

        }
    }

    public void consumirFila() throws IOException, TimeoutException {

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            Consumer consumer = new DefaultConsumer(channel) {
//                @Override
//                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                    String mensagem = new String(body, "UTF-8");
//                    System.out.println("Mensagem recebida: " + mensagem);
//                }
//            };
//            channel.basicConsume(QUEUE_NAME, true, consumer);

            System.out.println("Waiting for messages from the queue. To exit press CTRL+C");

            final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                final String message = new String(delivery.getBody(), "UTF-8");
                String[] dadosSaque = message.split("-");
                contaBusiness.saque(Long.valueOf(dadosSaque[0]), Double.valueOf(dadosSaque[1]));
                System.out.println("Received from message from the queue: " + message);
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });

        }
    }
}
