/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function(f) {
        f.module("angularTreeview", []).directive("treeModel", function($compile) {
            return{restrict: "A",
                link: function(b, h, c) {
                    var a = c.treeId;
                    var g = c.treeModel;
                    var e = c.nodeLabel || "label";
                    var d = c.nodeChildren || "children";
                    

                    e = '<ul><li data-ng-repeat="node in ' + g + '"><i class="collapsed" data-ng-show="node.' + d + '.length && node.collapsed" data-ng-click="' + a + '.selectNodeHead(node)">{{node.' + d +'.length}}</i><i class="expanded" data-ng-show="node.' + d + '.length && !node.collapsed" data-ng-click="' + a + '.selectNodeHead(node)">{{node.' + d +'.length}}</i><i class="normal" data-ng-hide="node.' +
                            d + '.length"></i> <span data-ng-class="node.selected" data-ng-click="' + a + '.selectNodeLabel(node)">{{node.' + e + '}}</span><div data-ng-hide="node.collapsed" data-tree-id="' + a + '" data-tree-model="node.' + d + '" data-node-id=' + (c.nodeId || "id") + " data-node-label=" + e + " data-node-children=" + d + "></div></li></ul>";
                    a && g && (c.angularTreeview && (b[a] = b[a] || {}, b[a].selectNodeHead = b[a].selectNodeHead || function(a) {
                        a.collapsed = !a.collapsed;
                    }, b[a].selectNodeLabel = b[a].selectNodeLabel || function(c) {
                        b[a].currentNode && b[a].currentNode.selected &&
                                (b[a].currentNode.selected = void 0);
                        c.selected = "selected";
                        b[a].currentNode = c;
                    }), h.html('').append($compile(e)(b)));
                }};
        });
    })(angular);

    (function(f) {
        f.module("dataTreeview", []).directive("datasetTreeModel", function($compile) {
            return{restrict: "A",
                link: function(b, h, c) {
                    var a = c.treeId;
                    var g = c.datasetTreeModel;
                    var e = c.nodeLabel || "label";
                    var d = c.nodeChildren || "children";

                    e = '<ul><li data-ng-repeat="node in ' + g + '"><i class="collapsed" data-ng-show="node.' + d + '.length && node.collapsed" data-ng-click="' + a + '.selectNodeHead(node)"></i><i class="expanded" data-ng-show="node.' + d + '.length && !node.collapsed" data-ng-click="' + a + '.selectNodeHead(node)"></i><i class="normal" data-ng-hide="node.' +
                            d + '.length"></i> <span data-ng-class="node.selected" data-ng-click="' + a + '.selectNodeLabel(node)">{{node.' + e + '}}</span><div data-ng-hide="node.collapsed" data-tree-id="' + a + '" data-tree-model="node.' + d + '" data-node-id=' + (c.nodeId || "id") + " data-node-label=" + e + " data-node-children=" + d + "></div></li></ul>";
                    a && g && (c.angularTreeview && (b[a] = b[a] || {}, b[a].selectNodeHead = b[a].selectNodeHead || function(a) {
                        a.collapsed = !a.collapsed;
                    }, b[a].selectNodeLabel = b[a].selectNodeLabel || function(c) {
                        b[a].currentNode && b[a].currentNode.selected &&
                                (b[a].currentNode.selected = void 0);
                        c.selected = "selected";
                        ;
                        b[a].currentNode = c;
                    }), h.html('').append($compile(e)(b)));
                }};
        });
    })(angular);