namespace todo.errors {
    "use strict";

    class TodoErrorController {
        constructor(private $rootScope: ng.IRootScopeService) {}

        close(index: number) {
          this.$rootScope["errors"].splice(index, 1);
        }
    }

    angular
        .module("todo.errors")
        .controller("TodoErrorController", TodoErrorController);
}