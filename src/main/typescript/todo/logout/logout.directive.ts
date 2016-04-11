namespace todo.logout {
    "use strict";

    class LogoutLink implements ng.IDirective {
        constructor() {}

        static instance(): ng.IDirective {
          return new LogoutLink();
        }

        restrict: string = "A";
        link: (scope: ng.IScope, element: ng.IAugmentedJQuery, attrs: ng.IAttributes) => void = this.linkFn;

        private linkFn(scope: ng.IScope, element: ng.IAugmentedJQuery) {
            element.bind("click", function(event: any) {
              event.preventDefault();
              var logoutForm = <HTMLFormElement>document.getElementById("logout");
              logoutForm.submit();
            });
          }
    }

    angular
      .module("todo.logout")
      .directive("logoutLink", LogoutLink.instance);
}
