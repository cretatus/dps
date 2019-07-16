var inboxController = angular.module('cometa');

inboxController.controller('InboxController', function ($scope, $http, ngDialog, $q, $location, $window) {
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.invite={};
		$scope.invites=[];
		$scope.read();
	}

	$scope.read = function(){
		$http.get('admin/read_my_invitatoins')
		.success(function(data, status, headers, config) {
            $scope.invites = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.cancel = function(invite){
        $scope.confirmMessage = "Cancel invitation?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('admin/cancel_invitation', invite)
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
		
	$scope.accept = function(invite){
        $scope.confirmMessage = "Accept invitation?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('admin/accept_invitation', invite)
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
		
	$scope.show();
});