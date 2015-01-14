Ext.namespace("com.zz91.zzwork.dept");

com.zz91.zzwork.dept.DeptSelector = function(dbclickfn){
	var deptTree = new com.zz91.zzwork.dept.Tree({
		region:"center",
		layout:"fit",
		rootVisible:false
	});
	
	deptTree.on("dblclick",dbclickfn);
	
	var win = new Ext.Window({
		id:"deptselector",
		title:"选择部门",
		width:400,
		height:450,
		layout:"border",
		modal:true,
		items:[deptTree]
	});
	
	win.show();
}

com.zz91.zzwork.dept.Tree = Ext.extend(Ext.tree.TreePanel,{
	nodeUrl:Context.ROOT+"/dept/deptChild.htm",
	constructor:function(config){
		config=config||{};
		Ext.apply(this,config);
		
		var root=new Ext.tree.AsyncTreeNode({
			text:"所有部门",
			draggable:false,
			id:"0",
			data:"",
			expanded:true
		});
		var _nodeUrl=this.nodeUrl;
		var dataLoader = new Ext.tree.TreeLoader({
			url: _nodeUrl,
			listeners:{
				"beforeload":function(treeLoader,node){
					this.baseParams["parentCode"]=node.attributes["data"];
				}
			}
		});
		
		var c={
			root:root,
			autoScroll:true,
			loader:dataLoader,
			tbar:[{
				text:"全部展开",
				scope:this,
				handler:function(btn){
					this.getRootNode().expand(true);
				}
			},{
				text:"全部折叠",
				scope:this,
				handler:function(btn){
					this.getRootNode().collapse(true);
				}
			}],
			listeners:{
				"contextmenu":function(node,event){
					if(config.contextmenu!="undefiend" || config.contextmenu!=null){
						event.preventDefault();
						config.contextmenu.showAt(event.getXY());
						node.select();
					}
				}
			}
		};
		
		com.zz91.zzwork.dept.Tree.superclass.constructor.call(this,c);
		
	}
})