angular.module("mainApp", ['ngSanitize'])
	.controller("mainCtrl", function($scope, $http) {



    $scope.fetchList = function() {
    	$http({
	      method: 'GET',
	      url: "/api/item/list"
	    }).then(function(response) {
	      if (response.status == 200) {
	        $scope.items = response.data;
	      } else {
	        console.log(response.data.data);
	      	$scope.errorMsg = response.data;
	      }
	    }, function(response) {
	    	console.log(response.data);
	      	$scope.errorMsg = response.data;
	    })
    }

    $scope.fetchList();

    $scope.voteFor = function(element) {
      console.log("clicked");
      var id = event.currentTarget.getAttribute("data-item-id");

      $http({
    	  method: 'GET',
    	  url: '/api/vote/?id=' + id
      }).then(function(response){
    	  var statusCode = response.status;

    	  if(statusCode == 200) {
    		  var status = response.data.status;
          var msg = response.data.msg;

          $scope.updateVoteMsg(status, msg);
    	  }
      }, function(response){})

      $scope.fetchList();
    }

    $scope.updateVoteMsg = function(type, msg) {

      //dump the whole content of the vote msg
      $scope.vote = {};
      $scope.vote[type] = msg;
    }

    $scope.propose = function() {
      var name = $scope.item.name;
      var desc = $scope.item.desc;
      $scope.submit = {}

      $http({
        method: 'GET',
        url: 'api/item/propose/?name=' + name + "&desc=" + desc
      }).then(function(response){
        var statusCode = response.status;
        var data = response.data;

        if(statusCode == 200) {

          console.log(data.status);
          if(data.status == "true") {
            $scope.submit.info = data.msg;
            $scope.submit.warning = null;
          } else {
            $scope.submit.warning = data.msg;
            $scope.submit.info = null;
          }


          $scope.fetchList();
        }
      }, function(response){
        console.log(response);
      })
    }
});
