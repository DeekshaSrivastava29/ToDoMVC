package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import com.typesafe.config.{Config, ConfigFactory}
import io.github.bonigarcia.wdm.WebDriverManager
import java.time.Duration

object BasePage {
  var driver: WebDriver = _
  val config: Config = ConfigFactory.load()
  var todoPage: ToDoMVCPage = _

  def initialize(): Unit = {
    val browserName = config.getString("browser")
    val url = config.getString("url")

    browserName.toLowerCase match {
      case "chrome" =>
        WebDriverManager.chromedriver().setup()
        driver = new ChromeDriver()
      case "firefox" =>
        WebDriverManager.firefoxdriver().setup()
        driver = new FirefoxDriver()
      case _ =>
        throw new IllegalArgumentException(s"Browser $browserName is not supported.")
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5))
    driver.manage().window().maximize()
    driver.get(url)

    // Initialize the ToDoMVCPage
    todoPage = new ToDoMVCPage(driver)
  }

  def quit(): Unit = {
    if (driver != null) {
      driver.quit()
    }
  }

  def getToDoPage(): ToDoMVCPage = {
    todoPage
  }

  def refreshPage(): Unit = {
    driver.navigate().refresh()
  }
}
