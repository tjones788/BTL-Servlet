var controllers = angular.module("Controllers", []);
controllers.controller('GlobalController', function($scope, SimilarToolService) { //pass Tree factory
    $scope.selectedNode = {
        "id": "",
        "name": "",
        "childrenCt": "",
        "parent": "",
        "imageUrl": ""

    };
    $scope.restData = {
        "data": ""
    };
    $scope.toolInputs = {
        "format": []
    };
    $scope.toolOutputs = {
        "format": []
    };
    $scope.toolParameters = [];
    $scope.dataTreeSelectedNode = {
        "id": "",
        "dataSetId": "",
        "files": []
    };
    /*
     * 
     * GLOBAL DRAG AND DROP CONTROLLER 
     * 
     */
    $scope.draggableObjects1 = []; //global draggable ojects

    SimilarToolService.success(function(data) {
        $scope.draggableObjects1 = data; //set draggable objects to the similar tools list 
    });
    $scope.droppedObjects1 = [];
    $scope.onDropComplete1 = function(data, evt) {
        var index = $scope.droppedObjects1.indexOf(data);
        if (index === -1 && ($scope.droppedObjects1.length > 0))
            $scope.droppedObjects1.pop();
        $scope.droppedObjects1.push(data);
    };
    $scope.onDragSuccess1 = function(data, evt) {
        console.log("133", "$scope", "onDragSuccess1", "", evt);
        var index = $scope.droppedObjects1.indexOf(data);
        if (index > -1) {
            $scope.droppedObjects1.splice(index, 1);
        }
    };
});
controllers.controller('ToolTreeController', function($scope, TreeListService) { //pass Tree factory
    TreeListService.success(function(data) {
        $scope.treeList = data;
    });
    $scope.$watch('toolTree.currentNode', function() {
        var curNode;
        if ($scope.toolTree && angular.isObject($scope.toolTree.currentNode)) {
            $scope.droppedObjects1.length = 0;
            curNode = $scope.toolTree.currentNode; //set the selected tool tree node to a local variable
            $scope.selectedNode.id = curNode.id;
            $scope.selectedNode.name = curNode.roleName;
            if (curNode.children) { //if there are children, set them to the $scope.selectedNode object.  Tools do NOT have children.
                $scope.selectedNode.childrenCt = curNode.children.length;
            }
            //if currentNode is a tool
            if (curNode.isTool) {
                $scope.toolInputs.format.length = 0; //reset inputs array
                $scope.toolParameters.length = 0; //reset parameters array
                $scope.toolOutputs.length = 0;
                $scope.selectedNode.imageUrl = curNode.imageUrl;
                //add current tool to draggableObjects []
                $scope.draggableObjects1.push(curNode);
                var inputCt = curNode.inputs.length;
                if (inputCt === 0)
                    alert('TOOL HAS NO INPUTS');
                for (i = 0; i < inputCt; i++) {
                    $scope.toolInputs.format.push(curNode.inputs[i].format);
                    $scope.selectedNode.isTool = true;
                    $scope.selectedNode.childrenCt = 0;
                }
                //get outputs
                var outputCt = curNode.outputs.length;
                if (outputCt > 0) {
                    for (i = 0; i < outputCt; i++) {
                        $scope.toolOutputs.format.push(curNode.outputs[i].format);
                    }
                }
                //get parameters
                var paramCt = curNode.parameters.length;
                if (paramCt > 0) {
                    for (i = 0; i < paramCt; i++) {
                        $scope.toolParameters.push(curNode.parameters[i]);
                    }
                }
            } else {
                $scope.selectedNode.isTool = false;
            }
        }
    }); //End currentNode $watch
});
controllers.controller('ToolDataController', function($scope) {
    //https://github.com/fatlinesofcode/ngDraggable

});
controllers.controller('DataTreeController', function($scope, DataTreeService) {
    DataTreeService.success(function(data) {
        $scope.dataTree = data;
    });
    $scope.$watch('dataToolTree.currentNode', function() { //watch the $scope.currentNode on the datasetTree 
        if ($scope.dataToolTree && angular.isObject($scope.dataToolTree.currentNode)) {
            $scope.dataTreeSelectedNode.id = $scope.dataToolTree.currentNode.id;
            if ($scope.dataToolTree.currentNode.dataSetId) { //is a dataset, not a parent
                alert('Found Dataset');
                $scope.dataTreeSelectedNode.dataSetId = $scope.dataToolTree.currentNode.dataSetId;
                var files = [];
                files = $scope.dataToolTree.currentNode.files;
                files.forEach(function(file) {
                    $scope.dataTreeSelectedNode.files.push(file);
                }); //end forEach
            } //end if dataSet 
        }
        ;
        //http://seanhess.github.io/2013/10/14/angularjs-directive-design.html 
    }); //End currentNode $watch
});
controllers.controller('SimilarToolController', function($scope, SimilarToolService) { //data is injected from app.factory 'Data' service
    SimilarToolService.success(function(data) {
        $scope.similarToolList = data;
    });
    $scope.showTools = function(tool) {
        if ($scope.toolInputs.format.length) {
            var inputArray = [];
            inputArray = $scope.toolInputs.format;
            inputArray.forEach(function(input) {
                if (input === tool.input) { //if the tool input is equal to the input of the tool in similarToolList
                    console.log(input.toString());
                    return true;
                }
                ;
            });
        }
        return false;
    }; //end showTools()
});
controllers.controller('PrevNextToolController', function($scope, NewSimTools) { //data is injected from app.factory 'Data' service
    NewSimTools.success(function(data) {
        $scope.newSimTools = data;
    });
    $scope.$watch('toolOutputs.format', function() {
//        alert(toolOuptus.format.length);
//        alert('OUTPUT CHANGED');
    });
});
//controllers.controller('RESTController', function ($scope, RestServices) { //data is injected from app.factory 'Data' service
//    RestServices.success(function(resp){
//        console.log('Success', resp);
//        $scope.restData = resp.data;
//    });

controllers.controller('RESTController', ['$scope', 'RestServices', function($scope, RestServices) { //data is injected from app.factory 'Data' service
        $scope.data = {}; // Instantiate an object to store your scope data in (Best Practices) - See more at: http://coder1.com/articles/consuming-rest-services-angularjs#sthash.Lquxaf2L.dpuf
        RestServices.query(function(response) {
            // Assign the response INSIDE the callback
            $scope.rest = response;
            $scope.newPost = {
                title: 'Test Post'
            };
        });
    }]);



