namespace todo.core {
    "use strict";

    export class ConnectionInterceptor {

      public static Factory($q: ng.IQService, $rootScope: ng.IRootScopeService) {
        return new ConnectionInterceptor($q, $rootScope);
      }

      static $inject = ["$q", "$rootScope"];
      constructor(private $q: ng.IQService, private $rootScope: ng.IRootScopeService) {}

      public response = (response: any) => {
        this.$rootScope["errors"] = null;
        return response;
      };

      public responseError = (rejection: any) => {
        if (rejection.status === 400 && typeof rejection.data === "object") {
          this.$rootScope["errors"] = response.data;
        } else {
          this.$rootScope["errors"] = [ "Server error: " + rejection.statusText + "(" + rejection.status + ")" ];
        }
        return this.$q.reject(rejection);
      };
    }

    angular
    .module("todo.core")
    .factory("ConnectionInterceptor", ConnectionInterceptor);
}