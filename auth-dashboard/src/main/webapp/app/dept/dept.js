Ext.namespace("com.zz91.zzwork.dept");

var DEPT=new function(){
	this.EDITWIN="righteditwin";
	this.TREE="depttree";
}

com.zz91.zzwork.dept.UpdateDeptRight=function(deptId, rightId, checked){
	Ext.Ajax.request({
        url:Context.ROOT+"/dept/updateDeptRight.htm",
        params:{
            "deptId":deptId,
            "rightId":rightId,
            "checked":checked
        },
        success:function(response,opt){
            var obj = Ext.decode(response.responseText);
            if(obj.success){
                com.zz91.zzwork.utils.Msg(MESSAGE.picTitleInfo,MESSAGE.saveSuccess);
            }else{
                Ext.MessageBox.show({
                    title:MESSAGE.title,
                    msg : MESSAGE.saveFailure,
                    buttons:Ext.MessageBox.OK,
                    icon:Ext.MessageBox.ERROR
                });
            }
        },
        failure:function(response,opt){
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.submitFailure,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
        }
    });
}

com.zz91.zzwork.dept.AddFormWin=function(){
	var form = new com.zz91.zzwork.dept.FormPanel();
    form.saveUrl = Context.ROOT+"/dept/createDept.htm";
    form.initParentForAdd();
    
    var win = new Ext.Window({
        id:DEPT.EDITWIN,
        title:"添加部门信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

com.zz91.zzwork.dept.EditFormWin=function(id){
	var tree = Ext.getCmp(DEPT.TREE);
    var node = tree.getSelectionModel().getSelectedNode();
    if(tree.getRootNode() == node){
            return false;
    }
    var form = new com.zz91.zzwork.dept.FormPanel();
    form.saveUrl = Context.ROOT+"/dept/updateDept.htm";
    form.initParentForUpdate();
    form.loadDept();
    var win = new Ext.Window({
        id:DEPT.EDITWIN,
        title:"修改部门信息",
        width:380,
        autoHeight:true,
        modal:true,
        items:[form]
    });
    win.show();
}

com.zz91.zzwork.dept.FormPanel=Ext.extend(Ext.form.FormPanel,{
    constructor:function(config){
	    config = config || {};
	    Ext.apply(this,config);
	    
	    var c = {
            labelAlign : "right",
            labelWidth : 80,
            region:"center",
            layout:"form",
            bodyStyle:'padding:5px 0 0',
            frame:true,
            defaults:{
                anchor:"95%",
                xtype:"textfield",
                labelSeparator:""
            },
            items:[{
                xtype:"hidden",
                id:"id",
                name:"id"
            },{
                xtype:"hidden",
                id:"parentCode",
                name:"parentCode"
            },{
                id:"parent",
                readOnly:true,
                fieldLabel:"上级部门"
            },{
                id:"code",
                name:"code",
                readOnly:true,
                fieldLabel:"部门code"
            },{
                id:"name",
                name:"name",
                fieldLabel:"部门名称",
                itemCls:"required",
                allowBlank:false
            },{
                xtype:"textarea",
                id:"note",
                name:"note",
                fieldLabel:"备注"
            }
            ],
            buttons:[{
                text:"保存",
                handler:this.saveForm,
                scope:this
            },{
                text:"关闭",
                handler:function(){
                    Ext.getCmp(DEPT.EDITWIN).close();
                }
            }]
	    };
	    com.zz91.zzwork.dept.FormPanel.superclass.constructor.call(this,c);
	},
	initParentForAdd:function(){
	    var tree = Ext.getCmp(DEPT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parentCode").setValue(node.attributes.data);
	    this.findById("parent").setValue(node.text);
	},
	initParentForUpdate:function(){
	    var tree = Ext.getCmp(DEPT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    this.findById("parent").setValue(node.parentNode.text);
	},
	saveUrl:Context.ROOT+"/dept/createDept.htm",
	saveForm:function(btn){
	    if(this.getForm().isValid()){
            this.getForm().submit({
                url:this.saveUrl,
                method:"post",
                type:"json",
                success:this.onSaveSuccess,
                failure:this.onSaveFailure,
                scope:this
            });
	    }else{
            Ext.MessageBox.show({
                title:MESSAGE.title,
                msg : MESSAGE.unValidate,
                buttons:Ext.MessageBox.OK,
                icon:Ext.MessageBox.ERROR
            });
	    }
	},
	onSaveSuccess:function(_form,_action){
	    var tree = Ext.getCmp(DEPT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    if(this.findById("id").getValue() > 0){
            node.setText(this.findById("name").getValue()); 
	    }else{
            node.leaf= false;
            tree.getLoader().load(node,function(){
                node.expand();
            });
	    }
	    Ext.getCmp(DEPT.EDITWIN).close();
	},
	onSaveFailure:function(_form,_action){
	    Ext.MessageBox.show({
            title:MESSAGE.title,
            msg : MESSAGE.saveFailure,
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.ERROR
	    });
	},
	loadDept:function(){
	    var tree = Ext.getCmp(DEPT.TREE);
	    var node = tree.getSelectionModel().getSelectedNode();
	    
	    var _fields = ["id","name","code","note"];
	    var form = this;
	    var _store = new Ext.data.JsonStore({
            root : "records",
            fields : _fields,
            url : Context.ROOT + "/dept/queryOneDept.htm?code="+node.attributes.data,
            autoLoad : true,
            listeners : {
                "datachanged" : function() {
                    var record = _store.getAt(0);
                    if (record == null) {
                        Ext.MessageBox.alert(MESSAGE.title,MESSAGE.loadError);
                        Ext.getCmp(DEPT.EDITWIN).close();
                    } else {
                        form.getForm().loadRecord(record);
                    }
                }
            }
	    });
	    
	}

}); 