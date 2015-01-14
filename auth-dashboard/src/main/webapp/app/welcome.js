Ext.namespace("com.zz91.zzwork");

com.zz91.zzwork.mybsView = Ext.extend(Ext.Panel,{
	bsTypes:0,
	constructor:function(config){
		config = config||{};
		Ext.apply(this,config);
		var bstypes=this.bsTypes;
		var _myStore = this.bsStore(Context.ROOT+'/mybs.htm?types='+bstypes);
		var _xtpl = this.xtpl;
		var c={
			bodyCssClass:"images-view",
			autoScroll:true,
			items:new Ext.DataView({
				store:_myStore,
				tpl:_xtpl,
				multiSelect:true,
				overClass:'x-view-over',
				itemSelector:'div.thumb-wrap',
				emptyText:MESSAGE.noRecord
			})
		};
		
		com.zz91.zzwork.mybsView.superclass.constructor.call(this,c);

		this.get(0).on("dblclick",function(view,index,node,e){
//			var store = view.getSelectedRecords();
//			var arr= view.getSelectedRecords()[0];
//			alert(view.getSelectedRecords()[0].data.url);
			window.open(view.getSelectedRecords()[0].data.url)
		});
	},
	porductId:0,
	//xtemplate,图片展现模板
	xtpl:new Ext.XTemplate('<tpl for=".">',
        '<div class="thumb-wrap" id="img-{id}">',
        '<div class="thumb"><img src="'+Context.ROOT+'{avatar}" title="{name}"></div>',
        '<span>{name}</span></div>',
	    '</tpl>',
	    '<div class="x-clear"></div>'
	),
	bsStore:function(_url){
		return new Ext.data.JsonStore({
		    url: _url,
		    root: 'records',
		    autoLoad:true,
		    fields: ["id","name","avatar","url"]
		})
	},
	imageRecord:Ext.data.Record.create(["id","name","avatar","url"])
})