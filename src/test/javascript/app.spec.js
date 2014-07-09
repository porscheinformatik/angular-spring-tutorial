'use strict';

describe('Application: todo', function() {

  beforeEach(module('todo'));

  describe('Controller: ErrorController', function() {

    it('removes errrs on close', function() {
      inject(function($rootScope, $controller) {
        var scope = $rootScope.$new;
        $rootScope.errors = [ 1, 2, 3 ];

        $controller('ErrorController', {
          $rootScope : $rootScope,
          $scope : scope
        });

        scope.close(1);

        expect($rootScope.errors).toEqual([ 1, 3 ]);
      });
    });
  });

});
