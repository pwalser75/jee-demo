import angular from 'angular';
import angularLoadingBar from 'angular-loading-bar';
import angularUiRouter from 'angular-ui-router';
import state from './state';
import controller from './controller';
import loginComponent from '../login/index';
import contactComponent from '../contacts/index';

const dependencies = [
   'ui.router',
   'angular-loading-bar',
   contactComponent.name,
   loginComponent.name
];


export default angular
    .module('Application', dependencies)
    .config(state)
    .controller('ApplicationController', controller)
	.run(['$state', function ($state) {
		$state.transitionTo('index');
	}]);
