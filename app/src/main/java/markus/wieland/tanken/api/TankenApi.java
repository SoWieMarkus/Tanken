package markus.wieland.tanken.api;

import android.app.Activity;

import java.util.List;

import markus.wieland.defaultappelements.api.API;
import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.api.GetRequest;
import markus.wieland.defaultappelements.api.RequestResultListener;
import markus.wieland.tanken.api.models.location.Location;
import markus.wieland.tanken.api.models.responses.ResponseQueryPrices;
import markus.wieland.tanken.api.models.responses.ResponseQueryStationDetail;
import markus.wieland.tanken.api.models.responses.ResponseQueryStations;

public class TankenApi extends API {

    private static final String BASE_URL = "http://sowiemarkus.com:8080/";
    private static final String DETAIL = "details";
    private static final String LIST = "stations";
    private static final String PRICES = "prices";
    private static final String LOCATIONS = "location";
    private static final String POSTCODE = "postcode";
    private static final String CITY = "city";


    public TankenApi(Activity context) {
        super(context);
    }

    public void queryStationDetail(APIResult<ResponseQueryStationDetail> apiResult, String stationId) {
        ApiURL url = new ApiURL(BASE_URL + DETAIL)
                .append("id", stationId);
        GetRequest<ResponseQueryStationDetail> routesGetRequest = new GetRequest<>(ResponseQueryStationDetail.class, url.toString(), new RequestResultListener<ResponseQueryStationDetail>() {
            @Override
            public void onLoad(ResponseQueryStationDetail response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void queryPrices(APIResult<ResponseQueryPrices> apiResult, List<String> stationIds) {
        ApiURL url = new ApiURL(BASE_URL + PRICES)
                .append("ids", String.join(",", stationIds));
        GetRequest<ResponseQueryPrices> routesGetRequest = new GetRequest<>(ResponseQueryPrices.class, url.toString(), new RequestResultListener<ResponseQueryPrices>() {
            @Override
            public void onLoad(ResponseQueryPrices response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void queryLocationByCity(APIResult<Location[]> apiResult, String city) {
        ApiURL url = new ApiURL(BASE_URL + LOCATIONS +"/" + CITY + "/" + city);
        GetRequest<Location[]> routesGetRequest = new GetRequest<>(Location[].class, url.toString(), new RequestResultListener<Location[]>() {
            @Override
            public void onLoad(Location[] response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();

    }

    public void queryLocationByPostCode(APIResult<Location[]> apiResult, String postCode) {
        ApiURL url = new ApiURL(BASE_URL + LOCATIONS +"/" + POSTCODE + "/" + postCode);
        GetRequest<Location[]> routesGetRequest = new GetRequest<>(Location[].class, url.toString(), new RequestResultListener<Location[]>() {
            @Override
            public void onLoad(Location[] response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();

    }

    public void queryStations(APIResult<ResponseQueryStations> apiResult, double lat, double lng, double rad) {
        queryStations(apiResult, String.valueOf(lat), String.valueOf(lng), String.valueOf(rad));
    }

    public void queryStations(APIResult<ResponseQueryStations> apiResult, String lat, String lng, String rad) {
        ApiURL url = new ApiURL(BASE_URL + LIST)
                .append("lat", lat)
                .append("lng", lng)
                .append("rad", rad)
                .append("sort", "dist")
                .append("type", "all");
        GetRequest<ResponseQueryStations> routesGetRequest = new GetRequest<>(ResponseQueryStations.class, url.toString(), new RequestResultListener<ResponseQueryStations>() {
            @Override
            public void onLoad(ResponseQueryStations response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }
}
