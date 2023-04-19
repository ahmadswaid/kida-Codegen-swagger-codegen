package de.bund.bfr.kidacodegen;

import java.util.List;

import io.swagger.codegen.v3.CodegenModel;

public class KidaCodegenModel extends CodegenModel {
	public List<KidaPanelCodegen> panels;

	public List<KidaPanelCodegen> getPanels() {
		return panels;
	}

	public void setPanels(List<KidaPanelCodegen> panels) {
		this.panels = panels;
	}

	public String menus;

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public boolean isParentOf() {
		return (children != null && children.size() > 0);
	}

	public boolean isParentModelNotNUll() {
		return parentModel != null;
	}
}
