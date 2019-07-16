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
		$http.get('admin/read_permissions')
			.success(function(data, status, headers, config) {
	            $scope.permissions = data;
	    		$http.get('admin/read_applications')
				.success(function(data, status, headers, config) {
		            $scope.applications = data;
				})
				.error(function(data, status, headers, config) {
					$scope.popupMessage = data.message;
					ngDialog.open({template: 'popup', scope: $scope});
				});
			})
			.error(function(data, status, headers, config) {
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.save = function(){

		$http.post('admin/save_application', $scope.application)
			.success(function(data, status, headers, config){
				$scope.popupMessage = 'Done';
				ngDialog.open({template: 'popup', scope: $scope});
				$scope.show();
			})
			.error(function(data, status, headers, config){
				$scope.popupMessage = data.message;
				ngDialog.open({template: 'popup', scope: $scope});
			});
	}

	$scope.remove = function(application){
        $scope.confirmMessage = "Remove application?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('admin/remove_application', application)
    			.success(function (data, status, headers, config) {
    				$scope.popupMessage = 'Done';
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
		$http.post('admin/select_application', application)
		.success(function (data, status, headers, config) {
			$window.location = '#/app';
			$window.location.reload();
		})
		.error(function (data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope });
		});
	}
	
	$scope.show();
});