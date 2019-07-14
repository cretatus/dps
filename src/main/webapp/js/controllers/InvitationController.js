var invitationController = angular.module('cometa');

invitationController.controller('InvitationController', function ($scope, $http, ngDialog, $q, $location, $window) {
	$scope.regexEmail = '^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.tableView = true;
		$scope.invite={};
		$scope.invites=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.tableView = false;
		$scope.invite={};
	}
	
	$scope.edit = function(invite){
		$scope.addView = false;
		$scope.editView = true;
		$scope.tableView = false;
		$scope.invite=invite;
	}
	
	$scope.read = function(){
		$http.get('read/all_invitatoins')
		.success(function(data, status, headers, config) {
            $scope.invites = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('read/applications_by_owner')
		.success(function(data, status, headers, config) {
            $scope.applications = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.save = function(invite){
		if(!invite){
			invite = $scope.invite;
		}
		$http.post('save/invitation', invite)
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

	$scope.remove = function(invite){
        $scope.confirmMessage = "Remove invitation?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('remove/invitation', invite)
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