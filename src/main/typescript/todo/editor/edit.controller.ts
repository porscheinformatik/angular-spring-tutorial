namespace todo.editor {
    "use strict";

    class TodoEditController {
        static $inject = ["$q", "todoEditService", "editTodo"];
        constructor(private $q: ng.IQService, private todoEditService: todo.editor.ITodoEditService, private editTodo: any) {}

        todo = this.editTodo.data;
        todotitle: string;
        todocompleted: boolean;
        tododue: Date;

        save() {
          this.todoEditService.save(this.todo, this.todotitle, this.todocompleted, this.tododue).then((data) => {
              this.todo = data;
//              return this.todo;
            });
        }
    }

    angular
        .module("todo.editor")
        .controller("TodoEditController", TodoEditController);
}
