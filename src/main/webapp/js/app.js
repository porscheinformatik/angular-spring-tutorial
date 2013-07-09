var todo = angular.module('todo', []);

todo.controller('TodoCtrl', function($scope, $http) {
  $http.get('data/todo/list').success(function(data) {
    $scope.todos = data;
  });
});