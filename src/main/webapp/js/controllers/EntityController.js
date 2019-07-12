var entityController = angular.module('cometa');

entityController.controller('EntityController', function ($scope, $http, ngDialog, $q) {
	$scope.regexSysname = '[a-zA-Z][\\w]*';
	$scope.show = function(){
		$scope.addView = false;
		$scope.editView = false;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = true;
		$scope.entity={};
		$scope.entities=[];
		$scope.read();
	}

	$scope.add = function(){
		$scope.addView = true;
		$scope.editView = false;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = false;
		$scope.entity={};
		$scope.attributes=[];
		$scope.keys=[];
		$scope.pk={};
		$scope.uq={};
		if($scope.search){
			$scope.entity.area=$scope.search.area;
			$scope.entity.stereotype=$scope.search.stereotype;
		}
	}
	
	$scope.addAttribute = function(){
		$scope.addAttributeView = true;
		$scope.editAttributeView = false;
		$scope.attribute={};
		$scope.attribute.element="";
		if(!$scope.keys){
			$scope.keys=[];
			$scope.pk={};
			$scope.uq={};
		}
	}
	
	$scope.edit = function(entity){
		$scope.addView = false;
		$scope.editView = true;
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
		$scope.tableView = false;
		$scope.entity=entity;
		$scope.entity.structureStereotype=$scope.entity.structure.stereotype;;
		$scope.readAttributes();
	}
	
	$scope.editAttribute = function(attribute){
		$scope.addAttributeView = false;
		$scope.editAttributeView = true;
		$scope.attribute=Object.assign({}, attribute);
		$scope.changingAttribute = attribute;
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
		if(!$scope.entity) return;
		if(!$scope.entity.structure) return;
		$http.post('/read/attributes', $scope.entity.structure)
		.success(function(data, status, headers, config) {
            $scope.attributes = data;
    		$http.post('/read/key_atributes', $scope.entity)
    		.success(function(data, status, headers, config) {
                $scope.keyAttributes = data;
                $http.post('/read/keys', $scope.entity)
        		.success(function(data, status, headers, config) {
                    $scope.keys = data;
                   	$scope.pk=$scope.findKey('pk') ? $scope.findOrCreateKey('pk') : {};
                   	$scope.uq=$scope.findKey('uq') ? $scope.findOrCreateKey('uq') : {};
                	$scope.refreshKeyCheckBoxes();
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

		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.readLookups = function(){
		if(!$scope.currentVersion) return;
		$http.get('/read/entities')
		.success(function(data, status, headers, config) {
            $scope.entities = data;
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
		$http.post('/read/stereotypes_lookup', 'entity')
		.success(function(data, status, headers, config) {
            $scope.stereotypes = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
		$http.post('/read/stereotypes_lookup', 'entity structure')
		.success(function(data, status, headers, config) {
            $scope.structureStereotypes = data;
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
		$http.get('/read/structures_lookup')
		.success(function(data, status, headers, config) {
            $scope.structures = data;
		})
		.error(function(data, status, headers, config) {
			$scope.popupMessage = data.message;
			ngDialog.open({template: 'popup', scope: $scope});
		});
	}

	$scope.save = function(){
		if(!$scope.entity.structure){
			$scope.entity.structure = {}
		}
		$scope.entity.structure.stereotype = $scope.entity.structureStereotype;
		$scope.entity.structure.attributes = $scope.attributes;
		$scope.entity.keys = $scope.keys;
		$http.post('/save/entity', $scope.entity)
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

	$scope.validateAttribute = function(attribute){
		$scope.popupMessage = null;
		for(var i=0; i<$scope.attributes.length; i++){
			if($scope.attributes[i].sysname == attribute.sysname){
				$scope.popupMessage = "The attribute with name [" + attribute.sysname + "] already exists";
				break;
			}
		}
		if(!attribute.stereotype){
			$scope.popupMessage = "The field stereotype cannot be empty";
		}
		if($scope.popupMessage){
			ngDialog.open({template: 'popup', scope: $scope});
			return false;
		}
		return true;
	}
	
	$scope.saveAttribute = function(){
		if(!$scope.validateAttribute($scope.attribute)){
			return false;
		}
		if($scope.addAttributeView){
			$scope.attributes.push($scope.attribute)
		}
		else{
			$scope.attributes[$scope.attributes.indexOf($scope.changingAttribute)] = $scope.attribute;
		}
		$scope.renumerateAttribute();
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
	}

	$scope.cancelAttribute = function(){
		$scope.addAttributeView = false;
		$scope.editAttributeView = false;
	}

	$scope.remove = function(entity){
        $scope.confirmMessage = "Remove record?";
        ngDialog.openConfirm({
            template: 'confirm_form.html',
            className: 'ngdialog-theme-default custom-width',
            scope: $scope
        }).then(
        	function () {
        		$http.post('/remove/entity', entity)
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
		$http.get('/read/element_types')
		.success(function(data, status, headers, config) {
			$scope.elementTypes = data;
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
		if(attribute.element){
			attribute.isReference = (attribute.element.type.name == 'reference');
			attribute.name = attribute.element.name;
			attribute.sysname = attribute.element.sysname;
			attribute.description = attribute.element.description;
			attribute.glossary = attribute.element.glossary;
		}
	}
	
	$scope.findKey = function(metatypeCode){
		for(var i=0; i<$scope.keys.length; i++){
			if($scope.keys[i].metatypeCode == metatypeCode){
				return $scope.keys[i];
			}
		}
		return null;
	}
	
	$scope.findKeyByStructure = function(structureId){
		for(var i=0; i<$scope.keys.length; i++){
			if($scope.keys[i].structure.id == structureId){
				return $scope.keys[i];
			}
		}
		return null;
	}
	
	$scope.findOrCreateKey = function(metatypeCode){
		var key = $scope.findKey(metatypeCode);
		if(!key){
			key = {};
			key.structure = {};
			key.structure.attributes = [];
			key.metatypeCode = metatypeCode;
			key.name = $scope.entity.name + " " + metatypeCode.toUpperCase();
			key.sysname = metatypeCode + "_" + $scope.entity.sysname + "_" + ($scope.keys.length + 1);
			$scope.keys.push(key);
		}
		return key;
	}
	
	$scope.attributChangeFlag = function(attribute, metatypeCode){
		if(!$scope[metatypeCode].structure){
			$scope[metatypeCode].structure = {};
		}
		$scope[metatypeCode].structure.attributes = [];
		for(var i=0; i<$scope.attributes.length; i++){
			if($scope.attributes[i][metatypeCode]){
				var detailAttribute = Object.assign({}, $scope.attributes[i]);
				detailAttribute.id = null;
				detailAttribute.parent = $scope.attributes[i];
				$scope[metatypeCode].structure.attributes.push(detailAttribute);
			}
		}
		if($scope[metatypeCode].structure.attributes.length > 0){
			var key = $scope.findOrCreateKey(metatypeCode);
			key.structure.attributes = key.structure.attributes ? key.structure.attributes : [];
			for(var i=key.structure.attributes.length-1; i>0; i--){
				if(!$scope[metatypeCode].structure.attributes.find(function(a){return a.sysname == key.structure.attributes[i].sysname;})){
					key.structure.attributes.splice(i, 1);
				}
			}
			for(var i=0; i<$scope[metatypeCode].structure.attributes.length; i++){
				if(!key.structure.attributes.find(function(a){return a.sysname == $scope[metatypeCode].structure.attributes[i].sysname;})){
					key.structure.attributes.push($scope[metatypeCode].structure.attributes[i]);
				}
			}
		}
		else {
			var key = $scope.findKey(metatypeCode);
			if(key){
				$scope.keys.splice($scope.keys.indexOf());
			}
		}
	}

	$scope.attributesLabel = function(key){
		var label = "";
		for(var i=0; i<key.structure.attributes.length; i++){
			label += key.structure.attributes[i].name;
			if(i < key.structure.attributes.length-1){
				label += ", ";
			}
		}
		return label;
	}

	$scope.findAttribute = function(id){
		for(var i=0; i<$scope.attributes.length; i++){
			if($scope.attributes[i].id == id){
				return $scope.attributes[i];
			}
		}
		return {};
	}
	
	$scope.findKeyBuStructure = function(id){
		for(var i=0; i<$scope.keys.length; i++){
			if($scope.keys[i].structure.id == id){
				return $scope.keys[i];
			}
		}
		return {};
	}
	
	$scope.refreshKeyCheckBoxes = function(){
		if(!$scope.keys) return;
		if(!$scope.keyAttributes) return;
		for(var i=0; i<$scope.keyAttributes.length; i++){
			var attribute = $scope.findAttribute($scope.keyAttributes[i].parent.id);
			var key = $scope.findKeyByStructure($scope.keyAttributes[i].structure.id);
			attribute[key.metatypeCode] = true;
		}
		for(var j=0; j<$scope.keys.length; j++){
			$scope.keys[j].structure.attributes = [];
			for(var i=0; i<$scope.keyAttributes.length; i++){
				if($scope.keyAttributes[i].structure.id == $scope.keys[j].structure.id){
					$scope.keys[j].structure.attributes.push($scope.keyAttributes[i]);
				}
			}
			
		}
	}

	$scope.boot();
});