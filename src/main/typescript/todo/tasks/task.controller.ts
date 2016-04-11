namespace todo.tasks {
    "use strict";

    class TodoController {
        static $inject = ["$q", "todoService"];
        constructor(private $q: ng.IQService, private todoService: todo.tasks.ITodoService) {
          let promises = [this.getTodos()];
          this.$q.all(promises);
        }

        todos: todo.Todo[];
        newTodoTitle: todo.Todo;

        addTodo() {
          this.todoService.addTodo(this.newTodoTitle).then((data) => {
              this.todos.push(data);
            });
        }

        getTodos() {
          return this.todoService.getTodos().then((data) => {
            this.todos = data;
            return this.todos;
          });
        }
    }

    angular
        .module("todo.tasks")
        .controller("TodoController", TodoController);
}