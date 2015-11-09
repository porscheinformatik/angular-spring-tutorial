/* global window */
(function(angular) {
  'use strict';

  var app = angular.module('todo', [ 'ngRoute', 'ui.bootstrap' ]);

  app.factory('connectionInterceptor', function($q, $rootScope) {
    return {
      'responseError' : function(response) {
        if(response.status === 400 && typeof response.data === 'object') {
          $rootScope.errors = response.data;
        } else {
          $rootScope.errors = [ 'Server error: ' + response.statusText + "(" + response.status + ")" ];
        }
        return $q.reject(response);
      },

      'response' : function(response) {
        $rootScope.errors = null;
        return response;
      }
    };
  });

  app.config(function($routeProvider, $httpProvider) {
    $httpProvider.interceptors.push('connectionInterceptor');

    $routeProvider.when('/list', {
      templateUrl : 'todolist',
      controller : 'TodoCtrl'
    }).when('/edit/:id', {
      templateUrl : 'todoedit',
      controller : 'TodoEditCtrl',
      resolve : {
        todoHttp : function($route, $http) {
          return $http.get('api/todos/' + $route.current.params.id);
        }
      }
    }).otherwise({
      redirectTo : '/list'
    });
  });

  app.controller('ErrorController', function($rootScope, $scope) {
    $scope.close = function(index) {
      $rootScope.errors.splice(index, 1);
    };
  });

  app.directive('logoutLink', function() {
    return {
      restrict : 'A',
      link : function link(scope, element) {
        element.bind('click', function(event) {
          event.preventDefault();
          document.getElementById('logout').submit();
        });
      }
    };
  });

  app.controller('TodoCtrl', function($scope, $http, $location) {

    $http.get('api/todos').success(function(data) {
      $scope.todos = data;
    });

    $scope.addTodo = function() {
      $http.post('api/todos', {
        title : $scope.newTodoTitle
      }).success(function(data) {
        $scope.todos.push(data);
        $scope.newTodoTitle = '';
      });
    };

    $scope.edit = function(id) {
      $location.path('/edit/' + id);
    };

  });

  app.controller('TodoEditCtrl', function($scope, $http, $location, todoHttp) {
    $scope.todo = todoHttp.data;
    if ($scope.todo.due) {
      $scope.todo.due = new Date(Date.parse($scope.todo.due));
    }
    $scope.save = function() {
      $http.post('/api/todos/' + $scope.todo.id, $scope.todo).success(function() {
        $location.path('/list');
      });
    };
  });

}(window.angular));