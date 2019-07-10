
var resultCalculationParameters= angular.module('eco');
resultCalculationParameters.service("ResultCalculationParameters", function() {

	var selectedLocation = null;
	var selectedEmissionSource = null;
	var selectedReleaseSource = null;
	var selectedPeriod = null;
	
	return {
		getSelectedLocation: function () {
			return selectedLocation;
		},
		getSelectedEmissionSource: function () {
			return selectedEmissionSource;
		},
		getSelectedReleaseSource: function () {
			return selectedReleaseSource;
		},
		getSelectedPeriod: function () {
			return selectedPeriod;
		},
		
		setSelectedLocation: function (value) {
			selectedLocation = value;
		},
		setSelectedEmissionSource: function (value) {
			selectedEmissionSource = value;
		},
		setSelectedReleaseSource: function (value) {
			selectedReleaseSource = value;
		},
		setSelectedPeriod: function (value) {
			selectedPeriod = value;
		}
	};

});