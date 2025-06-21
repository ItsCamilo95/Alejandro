package stepdefinitions;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiSteps {

    String token;
    @Before
    public void capturarEscenario(Scenario scenario){
        this.scenario = scenario;
    }

    @Given("Generar Token de acceso con autenticacion basica")
    public void que_el_usuario_tiene_credenciales_validas() {
        response = given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
            .when()
                .get(BASE_URL);
                            JsonPath jsonPath = response.jsonPath();
        token = jsonPath.getString("Token");

        System.out.println("Respuesta completa: " + response.getBody().asString());
        System.out.println("Token extraído: " + token);
        assertEquals(200, response.getStatusCode());

    }


    @When("Emitir factura con el endpoint de emision y con variedad de datos {string}")
    public void emitir_factura_con_el_endpoint(String NumFactura) {

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("el token esta vacio o no se genero");
        }

        String requestBody = "{\r\n" + //
                        "  \"headerData\": {\r\n" + //
                        "    \"identification\": \"B56583263\",\r\n" + //
                        "    \"integrationName\": \"ERP System\",\r\n" + //
                        "    \"formatType\": \"Verifactu\",\r\n" + //
                        "    \"offline\":false\r\n" + //
                        "  },\r\n" + //
                        "  \"content\": {\r\n" + //
                        "    \"obligadoEmision\": {\r\n" + //
                        "      \"nif\": \"B56583263\",\r\n" + //
                        "      \"nombreRazon\": \"GURUSOFT SL\"\r\n" + //
                        "    },\r\n" + //
                        "    \"idFactura\": {\r\n" + //
                        "      \"idEmisorFactura\": \"B56583263\",\r\n" + //
                        "      \"numSerieFactura\": \"" + NumFactura + "/G33\",\r\n" +
                        "      \"fechaExpedicionFactura\": \"2025-06-20\"\r\n" + //
                        "    },\r\n" + //
                        "    \"tipoFactura\": \"F1\",\r\n" + //
                        "    \"fechaOperacion\": \"2025-06-20\",\r\n" + //
                        "    \"descripcionOperacion\": \"string\",\r\n" + //
                        "    \"facturaSimplificadaArt7273\": \"string\",\r\n" + //
                        "    \"facturaSinIdentifDestinatarioArt61d\": \"string\",\r\n" + //
                        "    \"macrodata\": \"string\",\r\n" + //
                        "    \"destinatarios\": [\r\n" + //
                        "      {\r\n" + //
                        "        \"nombreRazon\": \"string\",\r\n" + //
                        "        \"nif\": \"string\",\r\n" + //
                        "        \"idOtro\": {\r\n" + //
                        "          \"codigoPais\": \"string\",\r\n" + //
                        "          \"idType\": \"string\",\r\n" + //
                        "          \"id\": \"string\"\r\n" + //
                        "        }\r\n" + //
                        "      }\r\n" + //
                        "    ],\r\n" + //
                        "    \"cupon\": \"string\",\r\n" + //
                        "    \"desglose\": [\r\n" + //
                        "      {\r\n" + //
                        "        \"impuesto\": \"string\",\r\n" + //
                        "        \"claveRegimen\": \"string\",\r\n" + //
                        "        \"calificacionOperacion\": \"string\",\r\n" + //
                        "        \"operacionExenta\": \"string\",\r\n" + //
                        "        \"tipoImpositivo\": 0,\r\n" + //
                        "        \"baseImponibleOimporteNoSujeto\": 0,\r\n" + //
                        "        \"baseImponibleACoste\": 0,\r\n" + //
                        "        \"cuotaRepercutida\": 0,\r\n" + //
                        "        \"tipoRecargoEquivalencia\": 0,\r\n" + //
                        "        \"cuotaRecargoEquivalencia\": 0\r\n" + //
                        "      }\r\n" + //
                        "    ],\r\n" + //
                        "    \"cuotaTotal\": 0,\r\n" + //
                        "    \"importeTotal\": 12.56\r\n" + //
                        "  },\r\n" + //
                        "  \"additionalData\": {\r\n" + //
                        "    \"transactionCode\": \"C000000010\",\r\n" + //
                        "    \"transactionUser\": \"User ERP\",\r\n" + //
                        "    \"sequential\": \"User ERP\",\r\n" + //
                        "    \"receiverEmail\": [\r\n" + //
                        "      \"string\"\r\n" + //
                        "    ],\r\n" + //
                        "    \"responsibleEmail\": [\r\n" + //
                        "      \"string\"\r\n" + //
                        "    ],\r\n" + //
                        "    \"additionalData\": {\r\n" + //
                        "      \"additionalProp1\": \"string\",\r\n" + //
                        "      \"additionalProp2\": \"string\",\r\n" + //
                        "      \"additionalProp3\": \"string\"\r\n" + //
                        "    }\r\n" + //
                        "  }\r\n" + //
                        "}";

        response = given()
                .header("Authorization", "Bearer " + token)
                .header("TEST_ENCADENAMIENTO", 1)
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post(endpointEmision);

        System.out.println("Response********************************: " + response.asString());

        //Evidencia
        scenario.attach(requestBody.getBytes(), "application/json", "requestBody");
        scenario.attach(response.asPrettyString().getBytes(), "application/json", "RespuestaFactura");
        int  REspuesta_esperada = 200;
        assertEquals(response.getStatusCode(),REspuesta_esperada);

    }



}



