package TestCases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.GivenWhenThen
import pages.BasePage.todoPage
import pages.Hooks

class PositiveSpec extends AnyFlatSpec  with Matchers with GivenWhenThen with Hooks {


  "The user" should "be able to navigate successfully through TodoMVC application" in {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user submits a new todo item")
    todoPage.enterTodoItem("TodoList1")
    todoPage.enterTodoItem("TodoList1$%^.!")
    val longTodoText = "a" * 250
    todoPage.enterTodoItem(longTodoText)

    Then("the new todo item should be added to the list")
    val todos = todoPage.getTodoItems
    todos should contain("TodoList1")
    todos should contain("TodoList1$%^.!")
    todos should contain(longTodoText)

    And("the '3 items left!' info text should be displayed")
    val (count, countText) = todoPage.getItemsLeftInfo
    count shouldBe 3
    countText shouldBe "3 items left!"

    When("the user marks all todos as complete")
    todoPage.toggleAllTodoItems()

    Then("the todo item should be marked as completed")
    val completedTodos = todoPage.getCompletedItems
    completedTodos should contain("TodoList1")
    completedTodos should contain("TodoList1$%^.!")
    completedTodos should contain(longTodoText)

    And ("the '0 items left!' info text should be displayed")
    val (newCount, newCountText) = todoPage.getItemsLeftInfo
    newCount shouldBe 0
    newCountText shouldBe "0 items left!"

    And("the user clicks on clear completed button")
    todoPage.clickClearCompleted()

    Then("there should be no todos")
    val todo = todoPage.getTodoItems
    todo shouldBe empty

  }

  "The user" should "be able to edit a todo item by double-clicking on it" in {
    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user adds a new todo item")
    todoPage.enterTodoItem("TodoList1")

    When("the user double-clicks on the todo item to edit it")
    todoPage.editTodoItem("TodoList1", "EditedTodo")

    Then("the todo item should be updated with the new text")
    val todosAfterEdit = todoPage.getTodoItems
    todosAfterEdit should contain("EditedTodo")
    todosAfterEdit should not contain "TodoList1"
  }

  "The user" should "be able to filter the todos" in  {

    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user adds two todos")
    todoPage.enterTodoItem("TodoList1")
    todoPage.enterTodoItem("TodoList2")

    And("the user marks one todo as complete")
    todoPage.completeTodoItem("TodoList1")

    And("the user filters active items")
    todoPage.filterActiveItems()

    Then("only the second todo should be visible")
    val filteredActiveTodos = todoPage.getActiveItems
    filteredActiveTodos should contain("TodoList2")

    When("the user filters completed items")
    todoPage.filterCompletedItems()

    Then("only the first todo should be visible")
    val filteredCompletedTodos = todoPage.getCompletedItems
    filteredCompletedTodos should contain ("TodoList1")
  }

  "The user" should "be able to delete the todo " in  {
    Given("the user is on the TodoMVC application page")
    todoPage.verifyPageHeading("todos")

    When("the user adds two todos")
    todoPage.enterTodoItem("TodoList1")
    todoPage.enterTodoItem("TodoList2")

    And("the user deletes the first todo")
    todoPage.deleteTodoItem(0)

    Then("first todo should not be in the list and only second todo should be visible")
    val todos = todoPage.getTodoItems
    todos should contain("TodoList2")

  }

}
