import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void fillingOutTheForm() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void f1() {
        WebElement from = driver.findElement(By.cssSelector("form"));
   //     from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys();
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());

    }


}
