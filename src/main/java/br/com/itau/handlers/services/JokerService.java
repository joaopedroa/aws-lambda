package br.com.itau.handlers.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.itau.handlers.domains.Joker;

public class JokerService {

	private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	public Joker generateJoker(LambdaLogger logger) throws Exception {
		try {

			logger.log("Iniciando chamada para api chucknorris");
			HttpRequest request = HttpRequest.newBuilder().GET()
					.uri(URI.create("https://api.chucknorris.io/jokes/random")).build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			Joker joker = new ObjectMapper().readValue(response.body(), Joker.class);

			logger.log("Finalizando chamada para api chucknorris com sucesso");
			logger.log("Piada: " + joker.getValue());

			return joker;
		} catch (Exception e) {
			logger.log("Erro ao gerar piada: " + e.getMessage());
			throw e;
		}
	}

}
