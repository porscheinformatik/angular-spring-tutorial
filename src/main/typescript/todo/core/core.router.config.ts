namespace todo.core {
    "use strict";

    routerConfig.$inject = ["$locationProvider", "$stateProvider", "$urlRouterProvider"];
    function routerConfig($locationProvider: ng.ILocationProvider, $stateProvider: ng.ui.IStateProvider,
                          $urlRouterProvider: ng.ui.IUrlRouterProvider) {

      $locationProvider.html5Mode(true);
      $urlRouterProvider.otherwise("/list");
      $httpProvider.interceptors.push(todo.core.ConnectionInterceptor.Factory);

      $stateProvider.state("base", {
        abstract: true,
        template: "<ui-view/>"
      });

      $stateProvider.state("list", {
        url: "/list",
        parent: "base",
        templateUrl: "todolist.html",
        controller: "TodoController",
        controllerAs: "tc"
      });

      $stateProvider.state("edit", {
        url: "/edit/{id}",
        parent: "base",
        templateUrl: "todoedit.html",
        controller: "TodoEditController",
        controllerAs: "tec",
        resolve: {
          editTodo: ["$http", "$stateParams", function($http: ng.IHttpService, $stateParams: ng.ui.IStateParamsService) {
            return $http.get("api/todos/" + $stateParams["id"]);
          }]
        },
      });
    }

    angular
    .module("todo.core")
    .config(routerConfig);
}