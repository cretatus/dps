var loginController = angular.module('cometa');

loginController.controller('LoginController', function ($rootScope, $scope, $http, $location, ngDialog, $window) {

    $scope.menuName='';
    var authenticate = function(callback) {
        $http.get('auth').success(function(data) {
            if (data.name) {
                $rootScope.authority = data.authorities["0"].authority;
                $rootScope.authenticated = true;
                $rootScope.userName = data.name;
            	$http.get('/read/current_application').success(function(data) {
            		$rootScope.currentApplication = data;
            	}).error(function() {
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
                	$location.path("/admin");
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
		$http.get('/operation/exit_application')
		.success(function () {
			$window.location = '/#/admin';
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
});