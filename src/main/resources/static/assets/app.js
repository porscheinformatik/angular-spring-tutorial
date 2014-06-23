(function(angular) {

  var todo = angular.module('todo', [ 'ngRoute', 'ui.bootstrap' ]);

  todo.directive('logoutLink', function() {
    return {
      restrict : 'A',
      link : function link(scope, element, attrs) {
        element.bind('click', function() {
          document.getElementById('logout').submit();
        });
      }
    };
  });

  todo.config(function($routeProvider, $httpProvider) {
    $routeProvider.when('/list', {
      templateUrl : 'todolist',
      controller : 'TodoCtrl'
    }).when('/edit/:id', {
      templateUrl : 'todoedit',
      controller : 'TodoEditCtrl',
      resolve : {
        todoHttp : function($route, $http) {
          return $http.get('api/todo/' + $route.current.params.id);
        }
      }
    }).otherwise({
      redirectTo : '/list'
    });
  });

  todo.controller('TodoCtrl', function($scope, $http, $location) {

    $http.get('api/todo/list').success(function(data) {
      $scope.todos = data;
    });

    $scope.addTodo = function() {
      $http.post('api/todo/new', {
        title : $scope.newTodoTitle
      }).success(function(data) {
        $scope.errors = data.errors;
        if (!data.errors) {
          $scope.todos.push(data);
          $scope.newTodoTitle = '';
        }
      });
    };

    $scope.edit = function(id) {
      $location.path('/edit/' + id);
    };

  });

  todo.controller('TodoEditCtrl', function($scope, $http, $location, todoHttp) {
    $scope.todo = todoHttp.data;
    if ($scope.todo.due) {
      $scope.todo.due = new Date(Date.parse($scope.todo.due));
    }
    $scope.save = function() {
      $http.post('/api/todo/' + $scope.todo.id, $scope.todo).success(function(data) {
        $scope.errors = data.errors;
        if (!data.errors) {
          $location.path('/list');
        }
      });
    };
  });

}(angular));