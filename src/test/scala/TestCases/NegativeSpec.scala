package TestCases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen
import pages.BasePage.todoPage
import pages.Hooks

class NegativeSpec extends AnyFlatSpec with Matchers with GivenWhenThen with Hooks{

  "The user" should "not be able to add any empty todos" in {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user submits a blank todo item")
    todoPage.enterTodoItem("")

    Then("the new todo item should not be added to the list")
    val todos = todoPage.getTodoItems
    todos should not contain ""

    When("the user tries to submit a space as a todo item")
    todoPage.enterTodoItem(" ")

    Then("the new todo item should not be added to the list")
    val todo = todoPage.getTodoItems
    todo should not contain " "
  }

  "The user" should "not be able to clear any todos which is not completed" in {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user submits a todo item")
    todoPage.enterTodoItem("Task A")

    Then("the new todo item should be added to the list")
    val todos = todoPage.getTodoItems
    todos should contain("Task A")

    And("the user clicks on clear completed button")
    todoPage.clickClearCompleted()

    Then("the todo still remains in active status ")
    val filteredActiveTodos = todoPage.getActiveItems
    filteredActiveTodos should contain("Task A")

  }

  "The user" should "be able to edit any completed todos" in {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user submits a todo item")
    todoPage.enterTodoItem("Task A")

    Then("the new todo item should be added to the list")
    val todos = todoPage.getTodoItems
    todos should contain("Task A")

    And("the user marks todo as complete")
    todoPage.completeTodoItem("Task A")

    Then("the todo item should be marked as completed")
    val completedTodos = todoPage.getCompletedItems
    completedTodos should contain("Task A")

    And("the user tries to edit the todo item")
    todoPage.editTodoItem("Task A", "Task B")

    Then("the todo item should be updated with the new text")
    val todosAfterEdit = todoPage.getTodoItems
    todosAfterEdit should contain("Task B")

  }

  "The user" should "be able to mark any completed todos as Active using toggle" in {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user submits a todo item")
    todoPage.enterTodoItem("Task A")

    And("the user marks todo as complete")
    todoPage.completeTodoItem("Task A")

    Then("the todo item should be marked as completed")
    val completedTodos = todoPage.getCompletedItems
    completedTodos should contain("Task A")

    And ("the user clicks on the toggle all button")
    todoPage.toggleAllTodoItems()

    Then("the todo should become active again")
    todoPage.filterActiveItems()
    val filteredActiveTodos = todoPage.getActiveItems
    filteredActiveTodos should contain("Task A")

  }
}
