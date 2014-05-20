var todo = angular.module('todo', [ 'ngRoute' ]);

todo.config(function($routeProvider, $httpProvider) {
  $routeProvider.when('/list', {
    templateUrl : 'todolist.html',
    controller : 'TodoCtrl'
  }).when('/edit/:id', {
    templateUrl : 'todoedit.html',
    controller : 'TodoEditCtrl',
    resolve : {
      todoHttp : function($route, $http) {
        return $http.get('data/todo/' + $route.current.params.id);
      }
    }
  }).otherwise({
    redirectTo : '/list'
  });
});

todo.controller('TodoCtrl', function($scope, $http, $location) {

  $http.get('data/todo/list').success(function(data) {
    $scope.todos = data;
  });

  $scope.addTodo = function() {
    $http.post('data/todo/new', {
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
    $http.post('/data/todo/' + $scope.todo.id, $scope.todo).success(function(data) {
      $scope.errors = data.errors;
      if (!data.errors) {
        $location.path('/list');
      }
    });
  };
});

todo.directive('todoDateInput', function() {
  function pad(n) {
    return n < 10 ? '0' + n : n;
  }
  return {
    require : '^ngModel',
    restrict : 'A',
    link : function(scope, elm, attrs, ngModel) {
      ngModel.$formatters.unshift(function(modelValue) {
        if (!modelValue) {
          return '';
        }
        return modelValue.getUTCFullYear() + '-' + pad(modelValue.getUTCMonth() + 1) + '-'
            + pad(modelValue.getUTCDate());
      });
      ngModel.$parsers.unshift(function(viewValue) {
        if (viewValue) {
          var parsedTimeMillis = Date.parse(viewValue);
          if (parsedTimeMillis) {
            ngModel.$setValidity('dateFormat', true);
            return new Date(parsedTimeMillis);
          } else {
            ngModel.$setValidity('dateFormat', false);
          }
        } else {
          ngModel.$setValidity('dateFormat', true);
        }
      });
    }
  };
});