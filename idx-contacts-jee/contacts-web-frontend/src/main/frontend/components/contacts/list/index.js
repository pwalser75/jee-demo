import angular from 'angular';
import angularUiRouter from 'angular-ui-router';

import common from '../../common/index';

import state from './state';
import controller from './controller';

const dependencies = [
   'ui.router',
   common.name
];

export default angular
    .module('ContactList', dependencies)
    .config(state)
    .controller('ContactListController', controller);
