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
    .module('ContactDetail', dependencies)
    .config(state)
    .controller('ContactDetailController', controller);
