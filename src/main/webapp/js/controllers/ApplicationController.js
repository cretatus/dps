var applicationController = angular.module('cometa');

applicationController.controller('ApplicationController', function ($scope, $http, ngDialog, $q, $location, $window) {

	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.application={};
		$scope.applications=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.application={};
	}
	
	$scope.edit = function(application){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.application=application;
	}
	
	$scope.read = function(){
		$http.get('/read/applications_by_owner')
			.success(function(data, status, headers, config) {
	            $scope.applications = data;
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.save = function(){

		$http.post('/save/application', $scope.application)
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

	$scope.remove = function(application){
        $scope.confirmMessage = "Удалить приложение?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/application', application)
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
	
	$scope.enter = function(application){
		$http.post('/operation/select_application', application)
		.success(function (data, status, headers, config) {
			// $location.path('/app');
			$window.location = '/#/app';
			$window.location.reload();
		})
		.error(function (data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope });
		});
	}
	
	$scope.show();
});