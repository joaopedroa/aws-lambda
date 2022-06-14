package br.com.itau.handlers;

import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.itau.handlers.domains.Joker;
import br.com.itau.handlers.services.JokerService;
import br.com.itau.handlers.services.SnsService;

public class JokerHandler implements RequestHandler<Map<String, Object>, Joker> {

	private JokerService jokerService = new JokerService();
	private SnsService snsService = new SnsService(Regions.US_EAST_1);

	public Joker handleRequest(Map<String, Object> input, Context context) {
		LambdaLogger logger = context.getLogger();

		try {

			logger.log("Iniciando lambda para publicação de piada");

			Joker joker = jokerService.generateJoker(logger);
			
			snsService.sendMessage(joker, input.get("filtro").toString(), logger);

			logger.log("Lambda finalizada com sucesso");
			
			return joker;

		} catch (Exception e) {
			logger.log("Erro inesperado: " + e.getMessage());
			return new Joker();
		}

	}

}
