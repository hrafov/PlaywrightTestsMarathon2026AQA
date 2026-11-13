package login;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.Test;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.awt.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSuite {
    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int width = (int) screenSize.getWidth();
    private final int height = (int) screenSize.getHeight();

    //tests started from button "Log in"(on the right upper side) and then "Create an Account"
    @Test
    @Order(1)
    public void createNewAccountAndLoginWithValidCredentials() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String firstName = "Tester";
        String lastName = "TesterLastName";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mmssSSS");
        String timestamp = LocalDateTime.now().format(formatter);
        String email = "u" + timestamp + "@gmail.com";
        String password = "UnoUno1!";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an Account")).click();
        page.getByPlaceholder("Enter your First Name").fill(firstName);
        page.getByPlaceholder("Enter your Last Name").fill(lastName);
        page.getByPlaceholder("Enter your Email").fill(email);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).click();
        page.locator("app-password-with-rules").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("eye")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).fill(password);
        page.getByPlaceholder("Confirm your Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an Account")).click();
        assertThat(page.locator("form")).containsText("Sign In to Your Account");

        page.getByPlaceholder("e.g. username@domain.com").fill(email);
        page.getByLabel("PasswordForgot password?").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)).click();
        page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("user icon")).click();
        assertThat(page.getByRole(AriaRole.BANNER)).containsText(email);

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Order(2)
    public void tryToCreateNewAccountWithAlreadyExistentEmail() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String firstName = "Tester";
        String lastName = "TesterLastName";
        String email = "hrafov@gmail.com";
        String password = "UnoUno1!";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an Account")).click();
        page.getByPlaceholder("Enter your First Name").fill(firstName);
        page.getByPlaceholder("Enter your Last Name").fill(lastName);
        page.getByPlaceholder("Enter your Email").fill(email);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).click();
        page.locator("app-password-with-rules").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("eye")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).fill(password);
        page.getByPlaceholder("Confirm your Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an Account")).click();
        assertThat(page.locator("form")).containsText("User already exists");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    //tests started from button "Log in"(on the right upper side)
    @Test
    @Order(3)
    public void tryToLoginWithValidMailAndValidPassword() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String email = "hrafov@gmail.com";
        String password = "Green&Tasty1";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByPlaceholder("e.g. username@domain.com").fill(email);
        page.getByLabel("PasswordForgot password?").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)).click();
        page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("user icon")).click();
        assertThat(page.getByRole(AriaRole.BANNER)).containsText(email);

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Order(4)
    public void tryToLoginWithInvalidMailAndValidPassword() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String email = "hhhhhh@gmail.com";
        String password = "Green&Tasty1";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByPlaceholder("e.g. username@domain.com").fill(email);
        page.getByLabel("PasswordForgot password?").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)).click();

        assertThat(page.locator("form")).containsText("Incorrect email or password. Try again or create an account.");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Order(5)
    public void tryToLoginWithValidMailAndInvalidPassword() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String email = "hrafov@gmail.com";
        String password = "Green&Tasty2";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByPlaceholder("e.g. username@domain.com").fill(email);
        page.getByLabel("PasswordForgot password?").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)).click();

        assertThat(page.locator("form")).containsText("Incorrect email or password. Try again or create an account.");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Order(6)
    public void tryToLoginWithInvalidMailAndInvalidPassword() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String email = "hhhhhh@gmail.com";
        String password = "Green&Tasty2";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByPlaceholder("e.g. username@domain.com").fill(email);
        page.getByLabel("PasswordForgot password?").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In").setExact(true)).click();

        assertThat(page.locator("form")).containsText("Incorrect email or password. Try again or create an account.");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    // checkin one letter in first name and last name when creating a new account
    @Test
    @Order(7)
    public void tryToUseOneLetterInFirstNameWhenCreatingNewAccount() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String firstName = "T";
        String lastName = "TesterLastName";
        String email = "hrafov@gmail.com";
        String password = "UnoUno1!";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an Account")).click();
        page.getByPlaceholder("Enter your First Name").fill(firstName);
        page.getByPlaceholder("Enter your Last Name").fill(lastName);
        page.getByPlaceholder("Enter your Email").fill(email);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).click();
        page.locator("app-password-with-rules").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("eye")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).fill(password);
        page.getByPlaceholder("Confirm your Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an Account")).click();

        assertThat(page.locator("form")).containsText("First name must be between 2 and 50 characters long.");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    @Order(8)
    public void tryToUseOneLetterInLastNameWhenCreatingNewAccount() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(100));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate("https://d1lwtnsqdaha0i.cloudfront.net/main");

        String firstName = "Tester";
        String lastName = "T";
        String email = "hrafov@gmail.com";
        String password = "UnoUno1!";

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create an Account")).click();
        page.getByPlaceholder("Enter your First Name").fill(firstName);
        page.getByPlaceholder("Enter your Last Name").fill(lastName);
        page.getByPlaceholder("Enter your Email").fill(email);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).click();
        page.locator("app-password-with-rules").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("eye")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your Password")).fill(password);
        page.getByPlaceholder("Confirm your Password").fill(password);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create an Account")).click();

        assertThat(page.locator("form")).containsText("Last name must be between 2 and 50 characters long.");

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

}