var structureController = angular.module('cometa');

structureController.controller('StructureController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = true;
		$scope.structure={};
		$scope.structures=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = false;
		$scope.structure={};
		$scope.attributes=[];
		if($scope.search){
			$scope.structure.area=$scope.search.area;
			$scope.structure.stereotype=$scope.search.stereotype;
		}
	}
	
	$scope.addAttribute = function(){
		$scope.addAttributeView = true;
		$scope.editAttributeView = false;
		$scope.attribute={};
	}
	
	$scope.edit = function(structure){
		$scope.addView = false;
		$scope.editView = true;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = false;
		$scope.structure=structure;
		$scope.readAttributes();
	}
	
	$scope.editAttribute = function(attribute){
		$scope.addAttributeView = false;
		$scope.editAttributeView = true;
		$scope.attribute=attribute;
	}
	
	$scope.read = function(){
		if(!$scope.currentVersion) return;
		$http.post('/save/current_version', $scope.currentVersion)
		.success(function(data, status, headers, config) {
			$scope.readLookups();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});

	}

	$scope.readAttributes = function(){
		if(!$scope.currentVersion) return;
		if(!$scope.structure) return;
		$http.post('/read/attributes', $scope.structure)
		.success(function(data, status, headers, config) {
            $scope.attributes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.readLookups = function(){
		if(!$scope.currentVersion) return;
		$http.get('/read/structures')
		.success(function(data, status, headers, config) {
            $scope.structures = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/areas_lookup')
		.success(function(data, status, headers, config) {
            $scope.areas = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.post('/read/stereotypes_lookup_by_metaobject', 'Structure')
		.success(function(data, status, headers, config) {
            $scope.stereotypes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.post('/read/stereotypes_lookup', 'attribute')
		.success(function(data, status, headers, config) {
            $scope.attributeStereotypes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/elements_lookup')
		.success(function(data, status, headers, config) {
            $scope.elements = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.save = function(){
		$scope.structure.attributes = $scope.attributes;
		$http.post('/save/structure', $scope.structure)
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

	$scope.saveAttribute = function(){
		if($scope.addAttributeView){
			$scope.attributes.push($scope.attribute)
		}
		$scope.renumerateAttribute();
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
	}

	$scope.remove = function(structure){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/structure', structure)
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
	
	$scope.removeAttribute = function(attribute){
		$scope.attribute = attribute;
        $scope.confirmMessage = "Remove attribute?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$scope.attributes.splice($scope.attributes.indexOf($scope.attribute), 1);
        		$scope.attribute = {};
        		$scope.renumerateAttribute();
        	}
        );
	}

	$scope.renumerateAttribute = function(){
		for(var i=0; i<$scope.attributes.length; i++){
			$scope.attributes[i].number = i;
		}
	}
	
	$scope.boot = function(){
		$http.get('/read/versions')
		.success(function(data, status, headers, config) {
			$scope.versions = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.get('/read/current_version')
		.success(function(data, status, headers, config) {
			$scope.currentVersion = data;
			$scope.show();
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}
	
	$scope.moveAttribute = function(index, shift){
		var a1 = $scope.attributes[index];
		var a2 = $scope.attributes[index-shift];
		a1.number -= shift;
		a2.number += shift;
		$scope.attributes.sort(function(a, b) {
			return a.number - b.number;
		});
	}
	
	$scope.attributeElementChange = function(attribute){
		attribute.isReference = (attribute.element.type.name == 'reference');
	}
	
	$scope.boot();
});