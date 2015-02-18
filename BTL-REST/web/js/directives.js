var sharedDirectives = angular.module("Directives", []);


sharedDirectives.directive('draggable', function () {
    return function (element) {
        // this gives us the native JS object
        var el = element[0];
        el.draggable = true;
        el.addEventListener(
                'dragstart',
                function (e) {
                    e.dataTransfer.effectAllowed = 'move';
                    e.dataTransfer.setData('Text', this.id);
                    this.classList.add('drag');
                    return false;
                },
                false
                );
        el.addEventListener(
                'dragend',
                function (e) {
                    this.classList.remove('drag');
                    return false;
                },
                false
                );
    }
});


sharedDirectives.directive('droppable', function () {
    return {
        scope: {
            drop: '&' // parent
        },
        link: function (scope, element) {
            // again we need the native object
            var el = element[0];
            el.addEventListener(
                    'dragover',
                    function (e) {
                        e.dataTransfer.dropEffect = 'move';
                        // allows us to drop
                        if (e.preventDefault)
                            e.preventDefault();
                        if (e.stopPropagation) {
                            e.stopPropagation();
                        }
                        this.classList.add('over');
                        return false;
                    },
                    false
                    );
            el.addEventListener(
                    'dragenter',
                    function (e) {
                        this.classList.add('over');
                        return false;
                    },
                    false
                    );
            el.addEventListener(
                    'dragleave',
                    function (e) {
                        this.classList.remove('over');
                        return false;
                    },
                    false
                    );
            el.addEventListener('drop', function (e) {
                // Stops some browsers from redirecting.
                if (e.stopPropagation)
                    e.stopPropagation();
                if (e.preventDefault)
                    e.preventDefault();
                this.classList.remove('over');
                var item = document.getElementById(e.dataTransfer.getData('Text'));
                this.appendChild(item);
                scope.$apply('drop()');
                return false;
            },
                    false
                    );
        }
    } //end return
});
