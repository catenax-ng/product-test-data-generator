package com.catenax.tdm.ui.view.datatemplate;

import com.catenax.tdm.client.model.DataTemplate;
import com.catenax.tdm.ui.dialog.CreateDialog;

public class DataTemplateCreateDialog extends CreateDialog<DataTemplate> {

	public DataTemplateCreateDialog() {
		super();
	}

	@Override
	public DataTemplate getData() {
		DataTemplate result = new DataTemplate();
		
		result.setName(fName.getValue());
		result.setVersion(fVersion.getValue());
		result.setContent("");
		
		return result;
	}
}
