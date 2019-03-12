package com.cl.erp.entity;

import java.util.ArrayList;
import java.util.List;
/**树形菜单结构体
 * 
 * @author CL
 *
 */
public class Tree {

	private String text;//菜单名称
	private String id;//菜单id
	private boolean checked;//是否选中 
	private List<Tree> children; //子菜单
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Tree> getChildren() {
		if(null==children){
			//确保调用添加子菜单的时候不出现null
			children=new ArrayList<Tree>();
		}
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
}
