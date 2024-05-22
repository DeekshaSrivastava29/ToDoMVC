package pages

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver, WebElement}

import scala.collection.JavaConverters._

class ToDoMVCPage(driver: WebDriver) {
  private val todoInput: By = By.className("new-todo")
  private val todoList: By = By.className("todo-list")
  private val toggleAllCheckbox: By = By.className("toggle-all")
  private val toggleCheckbox: By = By.className("toggle")
  private val todoCount: By = By.className("todo-count")
  private val clearCompleted: By = By.className("clear-completed")
  private val filterAll: By = By.linkText("All")
  private val filterActive: By = By.linkText("Active")
  private val filterCompleted: By = By.linkText("Completed")


  def verifyPageHeading(expectedHeading: String): Boolean = {
    val headingElement = driver.findElement(By.tagName("h1"))
    val actualHeading = headingElement.getText.trim
    actualHeading == expectedHeading
  }

  def enterTodoItem(itemText: String): Unit = {
    val input: WebElement = driver.findElement(todoInput)
    input.sendKeys(itemText)
    input.sendKeys("\n")
  }

  def getTodoItems: List[String] = {
    driver.findElements(todoList)
      .asScala
      .flatMap(_.findElements(By.tagName("li")).asScala)
      .map(_.getText)
      .toList
  }

  def editTodoItem(oldText: String, newText: String): Unit = {
    val todoItem = driver.findElement(By.xpath(s"//label[text()='$oldText']"))

    // Execute JavaScript to modify the label text directly
    val jsExecutor = driver.asInstanceOf[JavascriptExecutor]
    jsExecutor.executeScript(s"arguments[0].innerText = '$newText';", todoItem)
  }

  def completeTodoItem(labelText: String): Unit = {
    val items = driver.findElements(todoList).asScala
    val itemToComplete = items.find { item =>
      item.findElement(By.cssSelector("label[data-testid='todo-item-label']")).getText == labelText
    }

    itemToComplete match {
      case Some(item) => item.findElement(toggleCheckbox).click()
      case None => throw new NoSuchElementException(s"Todo item with label $labelText not found")
    }
  }

  def toggleAllTodoItems(): Unit = {
    driver.findElement(toggleAllCheckbox).click()
  }

    def filterAllItems(): Unit = {
      driver.findElement(filterAll).click()
    }

    def filterActiveItems(): Unit = {
      driver.findElement(filterActive).click()
    }

    def filterCompletedItems(): Unit = {
      driver.findElement(filterCompleted).click()
    }


  def getCompletedItems: List[String] = {
    driver.findElements(By.cssSelector(".completed label[data-testid='todo-item-label']")).asScala.map(_.getText).toList
  }


  def getActiveItems: List[String] = {
    driver.findElements(By.cssSelector(".todo-list li:not(.completed) label[data-testid='todo-item-label']")).asScala.map(_.getText).toList
  }


  def getItemsLeftInfo: (Int, String) = {
      val countText = driver.findElement(todoCount).getText
      val count = countText.split(" ")(0).toInt
      (count, countText)
    }


    def clickClearCompleted(): Unit = {
      driver.findElement(clearCompleted).click()
    }


    def deleteTodoItem(index: Int): Unit = {
      val items = driver.findElements(todoList).asScala.flatMap(_.findElements(By.tagName("li")).asScala)
      if (index < items.size) {
        val item = items(index)
        val actions = new Actions(driver)
        actions.moveToElement(item).perform() // Hover over the item
        item.findElement(By.className("destroy")).click()
      } else {
        throw new IndexOutOfBoundsException(s"Todo item index $index is out of bounds")
      }
    }


  }

