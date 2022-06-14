package br.com.itau.handlers.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import br.com.itau.handlers.domains.Joker;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;

public class SnsService {

	private final NotificationMessagingTemplate messagingTemplate;

	public SnsService(Regions region) {
		this.messagingTemplate = new NotificationMessagingTemplate(
				AmazonSNSClientBuilder.standard().withRegion(region).build());
	}

	public void sendMessage(Joker joker, String subject, LambdaLogger logger) {
		try {

			logger.log("Iniciando envio de mensagem para tópico SNS");

			messagingTemplate.sendNotification("topico-aws", joker, subject);

			logger.log("Mensagem enviada com sucesso para o tópico");

		} catch (Exception e) {
			logger.log("Mensagem enviada com falha: " + e.getMessage());
		}

	}

}
