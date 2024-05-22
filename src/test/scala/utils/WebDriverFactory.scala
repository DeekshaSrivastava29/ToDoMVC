package utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxOptions}
import org.openqa.selenium.safari.SafariDriver

object WebDriverFactory {

  def getDriver(browser: String = "chrome"): WebDriver = {
    browser.toLowerCase match {
      case "chrome" =>
        WebDriverManager.chromedriver().setup()
        val options = new ChromeOptions()
        options.addArguments("--start-maximized")
        new ChromeDriver(options)

      case "firefox" =>
        WebDriverManager.firefoxdriver().setup()
        val options = new FirefoxOptions()
        options.addArguments("--start-maximized")
        new FirefoxDriver(options)

      case "safari" =>
        new SafariDriver()

      case _ =>
        throw new IllegalArgumentException(s"Browser $browser is not supported.")
    }
  }
}
