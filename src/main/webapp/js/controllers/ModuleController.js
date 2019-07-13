var moduleController = angular.module('cometa');

moduleController.controller('ModuleController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.module={};
		$scope.modules=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.module={};
	}
	
	$scope.edit = function(module){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.module=module;
	}
	
	$scope.read = function(){
		$http.get('read/modules')
			.success(function(data, status, headers, config) {
	            $scope.modules = data;
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.save = function(){
		$http.post('save/module', $scope.module)
			.success(function(data, status, headers, config){
				$scope.popupMessage = 'Успешно сохранено';
				ngDialog.open({template: 'popup', scope: $scope});
				$scope.show();
			})
			.error(function(data, status, headers, config){
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.remove = function(module){
        $scope.confirmMessage = "Удалить приложение?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/module', module)
    			.success(function (data, status, headers, config) {
    				$scope.popupMessage = 'Успешно удалено';
    				ngDialog.open({template: 'popup', scope: $scope});
    				$scope.show();
    			})
    			.error(function (data, status, headers, config) {
    				$scope.popupMessage = data.message;
    				ngDialog.open({template: 'popup', scope: $scope });
    			});
        	}
        );

	}
	
	$scope.show();
});