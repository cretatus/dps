var loginController = angular.module('cometa');

loginController.controller('LoginController', function ($rootScope, $scope, $http, $location, ngDialog, $window) {

	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.regexEmail = '^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$';
    $scope.menuName='';
    var authenticate = function(callback) {
        $http.get('auth').success(function(data) {
            if (data.name) {
                $rootScope.authority = data.authorities["0"].authority;
                $rootScope.authenticated = true;
                $rootScope.userName = data.name;
            	$http.get('admin/current_application')
				.success(function(data) {
            		$rootScope.currentApplication = data;
            	})
				.error(function(data) {
					$scope.popupMessage = data.message;
					ngDialog.open({template: 'popup', scope: $scope });
	                $rootScope.authenticated = false;
	                callback && callback();
		        });
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).error(function() {
            $rootScope.authenticated = false;
            callback && callback();
        });
    }

    authenticate();

    $scope.credentials = {};

    $scope.login = function() {
        $http.post('login', $.param($scope.credentials), {
            headers : {
                "content-type" : "application/x-www-form-urlencoded"
            }
        }).success(function(data) {
            authenticate(function() {
                if ($rootScope.authenticated) {
                    $scope.error = false;
                	$location.path("admin");
                } else {
                    $rootScope.badTip = 'Bad credentials';
                    $scope.error = true;
                    $location.path("/");
                }
            });
        }).error(function(data) {
            $location.path("/");
            $scope.error = true;
            $rootScope.authenticated = false;
        })
    }

    $scope.logout = function() {
        $http.post('logout', {}).success(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        }).error(function(data) {
            $rootScope.authenticated = false;
        });
    };

    $scope.credentials = {};

	$scope.exit = function(){
		$http.get('operation/exit_application')
		.success(function () {
			$window.location = '#/admin';
			$window.location.reload();
		})
		.error(function (data) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope });
		});
	}

    $scope.selectTab = function(name){
        $scope.menuName = name;
    }

    $scope.register = function(){
    	$scope.registration = {};
        $scope.registerView = true;
    }

    $scope.registerSubmit = function(){
    	$scope.popupMessage = "";
    	if($scope.registration.password != $scope.registration.confPassword){
    		$scope.popupMessage = "The confirmation of password is not correct";
    	}
    	if($scope.popupMessage != ""){
    		ngDialog.open({template: 'popup', scope: $scope });
    		return false;
    	}
		$http.post('register', $scope.registration)
		.success(function(data, status, headers, config) {
			if(data.id){
				$scope.popupMessage = "The registration finished successfully. Try to login";
				ngDialog.open({template: 'popup', scope: $scope});
		        $scope.registerView = false;
		        $scope.error = false;
			}
			else{
				$scope.popupMessage = "Something is wrong! God, save our souls!";
				ngDialog.open({template: 'popup', scope: $scope});
				return false;
			}
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
			return false;
		});
    }

    $scope.registerBack = function(){
        $scope.registerView = false;
    }

});