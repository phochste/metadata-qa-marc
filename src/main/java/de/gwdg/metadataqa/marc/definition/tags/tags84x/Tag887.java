package de.gwdg.metadataqa.marc.definition.tags.tags84x;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.FormatSourceCodes;

/**
 * Non-MARC Information Field
 * http://www.loc.gov/marc/bibliographic/bd887.html
 */
public class Tag887 extends DataFieldDefinition {

	private static Tag887 uniqueInstance;

	private Tag887() {
		initialize();
		postCreation();
	}

	public static Tag887 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag887();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "887";
		label = "Non-MARC Information Field";
		mqTag = "NonMARCField";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd887.html";

		ind1 = new Indicator();
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Content of non-MARC field", "NR",
			"2", "Source of data", "NR"
		);

		getSubfield("2").setCodeList(FormatSourceCodes.getInstance());

		getSubfield("a").setMqTag("content");
		getSubfield("2").setMqTag("source");
	}
}
