package rtg.learning.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger=LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping(path="/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from,
			@PathVariable String to
			) {
//		CurrencyExchange currencyExchange=new CurrencyExchange(1000L,from, to, BigDecimal.valueOf(50));
		//INFO [currency-exchange-service,284fef1fa4fd94657bedf8b2ac7cbd57,c9325e9ec322df8c] 10564 --- [currency-exchange-service] [nio-8000-exec-1] [284fef1fa4fd94657bedf8b2ac7cbd57-c9325e9ec322df8c] r.l.m.c.CurrencyExchangeController       : retrieveExchangeValue called with USD to INR

		logger.info("retrieveExchangeValue called with {} to {}",from,to);
		
		CurrencyExchange currencyExchange=repository.findByFromAndTo(from, to);
		if(currencyExchange==null) {
			throw new RuntimeException("Unable to find data for "+from+" to "+to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}