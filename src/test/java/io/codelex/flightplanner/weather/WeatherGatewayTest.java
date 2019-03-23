package io.codelex.flightplanner.weather;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.codelex.flightplanner.api.Weather;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherGatewayTest {
    
    @Rule
    WireMockRule wireMock = new WireMockRule();
    
    WeatherGateway gateway;
    
    LocalDate date = LocalDate.of(2019, 3, 23);
    
    @BeforeEach
    void setUp() {
        wireMock.start();

        ApixuProperties props = new ApixuProperties();
        props.setApiUrl("http://localhost:" + wireMock.port());
        props.setApiKey("123");
        
        gateway = new WeatherGateway(props);
    }
    
    @Test
    void should_fetch_forecast() throws Exception {
        //given
        File file = ResourceUtils.getFile(this.getClass().getResource(
                "/stubs/successful-response.json"));
        assertTrue(file.exists());

        byte[] json = Files.readAllBytes(file.toPath());
        
        wireMock.stubFor(get(urlPathEqualTo("/v1/forecast.json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withStatus(200)
                        .withBody(json)));
        
        //when
        Weather weather = gateway.fetchForecast("Riga", date).get();
        
        //then
        assertEquals(
                "Moderate or heavy rain shower",
                weather.getCondition());
        assertEquals(
                1,
                weather.getPrecipitation());
        assertEquals(
                4.6,
                weather.getTemperature());
        assertEquals(
                23.8,
                weather.getWindSpeed());
    }

    @Test
    void should_handle_external_service_failure() {
        //given
        wireMock.stubFor(get(urlPathEqualTo("/v1/forecast.json"))
                .willReturn(aResponse()
                        .withStatus(500)));

        //when
        Optional<Weather> response = gateway.fetchForecast("Riga", date);
        
        //then
        assertFalse(response.isPresent());
    }
}