var todo = angular.module('todo', []);

todo.controller('TodoCtrl', function($scope, $http) {

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
});