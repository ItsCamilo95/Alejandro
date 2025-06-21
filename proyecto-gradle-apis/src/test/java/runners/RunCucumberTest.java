package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions"},
    plugin = { "pretty", "html:target/cucumber-reports.html"},
    monochrome = true,
    tags = "@EmitirFacturas" // Puedes cambiar o eliminar esta línea si no necesitas filtrar por etiquetas
)
public class RunCucumberTest {
}
