/**
 * 
 */

$(function () {
	
	//Draggable refIds
	$(".refIdDraggable").on("dragstart", function (ev) {
		let uri = ev.target.dataset["refid"];
		ev.originalEvent.dataTransfer.setData("text/referencing-identifier-list", "[" + uri.length + "_" + uri + "]");
		ev.originalEvent.dataTransfer.setData("text/plain", uri);
		ev.originalEvent.dataTransfer.dropEffect = "copy";
	});
	
	$(function () {
		$('[data-toggle="popover"]').popover({
			container: 'body',
			render: 'window',
			trigger:"click | hover | focus | manual",
			delay: { "show": 500, "hide": 100 },
			html: true,
		});
	});
	
	$('.card-header[data-toggle="collapse"]').click(function (e) {
		if (e.target === this) {
			$(this).find('[data-toggle="collapse"]').first().click();			
		}
	});
	
	$("button.id-copy").click(function (e) {
		e.stopPropagation();
		e.preventDefault();
		
		let idElement = $(this).parent().find(".refIdField").first();
		idElement.removeAttr("readonly");
		idElement.get(0).select();
		idElement.get(0).setSelectionRange(0, 99999);
		
		document.execCommand("copy");

		if (window.getSelection) {
			window.getSelection().removeAllRanges();
		} else if (document.selection) {
			document.selection.empty();
		}
		
		idElement.attr("readonly",true);
	});
	
	$("nav-tabs").tab();
	$('.alert').alert();
});
