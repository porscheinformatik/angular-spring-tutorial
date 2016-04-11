namespace todo.editor {
    "use strict";

    export interface ITodoEditService {
        save: (todo: todo.Todo, text: string, completed: boolean, tododue: Date) => ng.IPromise<todo.Todo>;
    }

    export class TodoEditService implements ITodoEditService {
        static $inject = ["$http"];
        constructor(private $http: ng.IHttpService) {}

        save(todo: todo.Todo, text: string, completed: boolean, due: Date) {
          todo.title = text;
          todo.completed = completed;
          todo.due = due;
          return this.$http.post("/api/todos/" + todo.id, todo).then(this.success);
        }

        private success: (response: any) => {} = (response) => response.data;
    }

    angular.module("todo.editor")
        .service("todoEditService",  todo.editor.TodoEditService);
}

// return this.$stateService.go("list");
// private $stateService: ng.ui.IStateService
