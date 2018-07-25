package controllers;

import java.io.File;
import java.util.List;

import data.Import;
import data.Mapper;
import models.Appelant;



public class ImportExportController {
	protected void importer(File file) {
		List<Appelant> appelants = Import.oldAppelants(file);
		Mapper.getInstance().importApplants(appelants);
	}
}
