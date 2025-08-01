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
    void testForAnIncompleteLastNameField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void theFirstAndLastNamesAreEnteredInLatin() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Novikova Oksana");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void hyphenatedLastName() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова-Анохина Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void enteringNumbersInTheFirstNameLastNameField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("1111111111111111111");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void enteringAnInvalidPhoneNumber() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79150000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void theBoundaryValuesAreLargerThanNecessary() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791500000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }
    @Test
    void theBoundaryValuesAreLessThanNecessary() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7915000000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void enteringASingleDigitInThePhoneField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void emptyPhoneInputField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void enteringANumberWithALetterInThePhoneField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7915000O000");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void theLettersAreEnteredInThePhoneField() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("аааааааааааааааа");
        from.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void theCheckboxIsNotChecked() {
        WebElement from = driver.findElement(By.cssSelector("form"));
        from.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Новикова Оксана");
        from.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79150000000");
        from.findElement(By.cssSelector("button")).click();
        WebElement result = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", result.getText().trim());
    }

}
