namespace todo.tasks {
    "use strict";

    export interface ITodoService {
        addTodo: (todo: todo.Todo) => ng.IPromise<todo.Todo>;
        getTodos: () => ng.IPromise<[todo.Todo]>;
    }

    export class TodoService implements ITodoService {
        static $inject = ["$http"];
        constructor(private $http: ng.IHttpService) {}

        addTodo(todo: todo.Todo) {
          return this.$http.post("api/todos", {title: todo}).then(this.success);
        }

        getTodos() {
          return this.$http.get("api/todos").then(this.success);
        }

        private success: (response: any) => {} = (response) => response.data;
    }

    angular.module("todo.tasks")
        .service("todoService",  todo.tasks.TodoService);
}

