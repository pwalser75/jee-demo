import angular from "angular";
import state from "./state";
import controller from "./controller";
import loginComponent from "../login/index";
import contactComponent from "../contacts/index";

const dependencies = [
    'ui.router',
    //  'angular-loading-bar',
    contactComponent.name,
    loginComponent.name
];

export default angular
    .module('Application', dependencies)
    .factory('authHttpInterceptor', ['$q', '$window', '$location', function httpInterceptor($q, $window, $location) {

        var authInterceptor = {
            'response': function (response) {
                console.log("SUCCESS: " + response.status);
                return response;
            },
            responseError: (rejection) => {
                console.log("ERROR: " + response.status);
                if (response.status === 401) {
                    $location.url('/login');
                }

                return $q.reject(response);
            }
        };
        return authInterceptor;

    }])
    .config(['$httpProvider', function ($httpProvider) {
        // Push Unauthorized Interceptor
        $httpProvider.interceptors.push('authHttpInterceptor');
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }])
    .config(state)
    .controller('ApplicationController', controller)
    .run(['$state', function ($state) {
        $state.transitionTo('index');
    }]);