package de.gwdg.metadataqa.marc.definition.controlsubfields.tag007;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;

/**
 * Specific material designation
 * https://www.loc.gov/marc/bibliographic/bd007m.html
 */
public class Tag007motionPicture01 extends ControlSubfield {
	private static Tag007motionPicture01 uniqueInstance;

	private Tag007motionPicture01() {
		initialize();
		extractValidCodes();
	}

	public static Tag007motionPicture01 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag007motionPicture01();
		return uniqueInstance;
	}

	private void initialize() {
		label = "Specific material designation";
		id = "tag007motionPicture01";
		mqTag = "specificMaterialDesignation";
		positionStart = 1;
		positionEnd = 2;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd007m.html";
		codes = Utils.generateCodes(
			"c", "Film cartridge",
			"f", "Film cassette",
			"o", "Film roll",
			"r", "Film reel",
			"u", "Unspecified",
			"z", "Other",
			"|", "No attempt to code"
		);
	}
}