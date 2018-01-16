'use strict';
var ALERT = ALERT || {
	info: (options) => {
		var title = options.title || 'default title';
		var text = options.text || 'default text';
		swal({
		    title: title,
		    text: text,
			imageUrl: "image/info_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		})
	},
	
	warn: (options) => {
		var title = options.title || 'default title';
		var text = options.text || 'default text';
		swal({
		    title: title,
		    text: text,
			imageUrl: "image/warning_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		})
		
	},

	error: (options) => {
		var title = options.title || 'default title';
		var text = options.text || 'default text';
		swal({
		    title: title,
		    text: text,
			imageUrl: "image/error_information.png",
		    showCancelButton: false,
		    confirmButtonColor: "#3b6dde",
		    confirmButtonText: "确认",
		    closeOnConfirm: false,
		})
		
	},
	
	confirm: (options) => {
		var title = options.title || 'default title';
		var text = options.text || 'default text';
		swal({
	    title: title,
	    text: text,
		imageUrl: "image/warning_information.png",
	    showCancelButton: true,
	    confirmButtonColor: "#3b6dde",
 	    cancelButtonText: "否",
	    confirmButtonText: "是",
	    closeOnConfirm: true,
		},
		function(){
			options.callback();
		    // swal("Deleted!", "已删除", "success");
		});
		
	}
};

