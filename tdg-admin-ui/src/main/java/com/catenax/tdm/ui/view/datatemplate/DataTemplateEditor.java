package com.catenax.tdm.ui.view.datatemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.model.DataTemplate;
import com.catenax.tdm.client.model.TestDataScenario;
import com.catenax.tdm.ui.dialog.YesNoDialog;
import com.catenax.tdm.ui.view.AbstractEditor;

import io.github.ciesielskis.AceMode;

public class DataTemplateEditor extends AbstractEditor<DataTemplate> {

	public DataTemplateEditor() {
		super(new DataTemplateCreateDialog());
		
		this.aceEditor.setMode(AceMode.text);// .json);
	}

	@Override
	protected void selectItem(DataTemplate scenario) {
		if(scenario != null) {
			this.selected = scenario;
			this.aceEditor.setValue(prettyPrintJs(_fixContent(scenario.getContent())));
			this.aceEditor.setMode(AceMode.json);
			this.setDetailTitle(this.selected.toString());
		}
	}

	@Override
	protected void loadData() {
		items = this.getClient().getDataTemplates();
		
		ArrayList<DataTemplate> list = new ArrayList<>();
		list.addAll(items);
		Collections.sort(list, new Comparator<DataTemplate>() {
			@Override
			public int compare(DataTemplate o1, DataTemplate o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		
		items = list;
		this.itemList.setItems(items);
	}

	@Override
	protected void createNew(DataTemplate result) {
		if(result.getName().strip().length() == 0 || result.getVersion().strip().length() == 0) {
			return;
		}

		try {
			if(result.getContent() == null || result.getContent().isBlank()) {
				result.setContent("{}");
			}
			this.getClient().getDataTemplateApi().createDataTemplateUsingPOST(result);
			loadData();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void deleteSelection() {
		if(this.selected != null) {
			YesNoDialog dia = new YesNoDialog(
					"This will delete '" + this.selected.toString() + "' permanently for all users and cannot be restored. Are you sure?",
					this::confirmDelete
			);
			dia.open();
		}
	}
	
	protected void confirmDelete() {
		if(this.selected != null) {
			try {
				this.getClient().getDataTemplateApi().deleteDataTemplateUsingDELETE(this.selected.getName(), this.selected.getVersion());	
				
				this.selected = null;

				this.itemList.clear();
				
				this.aceEditor.setValue("");
				
				loadData();
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void saveCurrent() {
		if(this.selected != null) {
			this.selected.setContent(prettyPrintJs(_fixContent(this.aceEditor.getValue())));
			
			try {
				this.getClient().getDataTemplateApi().updateDataTemplateContentUsingPUT(this.selected.getName(), this.selected.getVersion(), this.selected.getContent());
			} catch (ApiException e) {
				e.printStackTrace();
			}
		}
	}

}