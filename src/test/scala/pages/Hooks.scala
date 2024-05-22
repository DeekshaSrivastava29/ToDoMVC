package pages

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, Suite}
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import java.nio.file.{Files, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Try}

trait Hooks extends BeforeAndAfterEach with BeforeAndAfterAll { this: Suite =>

  val screenshotsFolderPath = "screenshots"

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    clearScreenshotsFolder(screenshotsFolderPath)
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    Try(BasePage.initialize()) match {
      case Failure(exception) =>
        fail(s"Failed to initialize WebDriver: ${exception.getMessage}")
      case _ =>
    }
  }

  override protected def afterEach(): Unit = {
    captureFullScreenScreenshot(BasePage.driver, generateScreenshotName(currentTestName.getOrElse("unknown")))
    BasePage.quit()
    super.afterEach()
  }

  private def clearScreenshotsFolder(folderPath: String): Unit = {
    val screenshotsFolder = new File(folderPath)
    if (screenshotsFolder.exists() && screenshotsFolder.isDirectory) {
      val files = screenshotsFolder.listFiles()
      if (files != null) {
        files.foreach(_.delete())
      }
    }
  }

  private def captureFullScreenScreenshot(driver: WebDriver, screenshotName: String): Unit = {
    if (driver != null) {
      val screenshot = driver.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.FILE)
      val destinationPath = Paths.get(screenshotName)
      Files.createDirectories(destinationPath.getParent)
      Files.copy(screenshot.toPath, destinationPath)
      println(s"Screenshot saved: $screenshotName")
    }
  }

  private def generateScreenshotName(testName: String): String = {
    val sanitizedTestName = testName.replaceAll("[^a-zA-Z0-9_-]", "_").substring(0, Math.min(testName.length, 50))
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
    s"$screenshotsFolderPath/$sanitizedTestName-$timestamp.png"
  }

  private var currentTestName: Option[String] = None

  abstract override def runTest(testName: String, args: org.scalatest.Args): org.scalatest.Status = {
    currentTestName = Some(testName)
    super.runTest(testName, args)
  }
}
