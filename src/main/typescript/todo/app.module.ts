namespace todo {
    "use strict";

    angular.module("todo", [
        "todo.tasks",
        "todo.editor",
        "todo.errors",
        "todo.logout",
        "todo.core",
        "ui.router"
    ]);
}