package markus.wieland.tanken.api.models.responses;

import java.util.HashMap;
import java.util.Map;

import markus.wieland.tanken.api.models.prices.Price;

public class ResponseQueryPrices extends Response {

    private HashMap<String, Price> prices;

    public Map<String, Price> getPrices() {
        return prices;
    }
}
