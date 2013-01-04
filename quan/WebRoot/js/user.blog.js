function ArticleItem(item) {
	this.itemPanel = $("<div class='index_item'></div>");
	this.index_itemname = $(
			"<div class='index_itemname'><a href='article?itemId=" + item.id
					+ "'>" + item.itemName + "</a></div>").appendTo(
			this.itemPanel);
	this.countSpan = $(
			"<div class='index_articlecount'>(" + item.articleCount + ")</div>")
			.appendTo(this.itemPanel);
	$("<div class='clear_float'></div>").appendTo(this.itemPanel);
}

ArticleItem.prototype = {
	asHtml : function() {
		return this.itemPanel;
	},
	getItemNamePanle : function() {
		return this.index_itemname;
	},
	setCount : function(count) {
		this.countSpan.html("(" + count + ")");
	}

}