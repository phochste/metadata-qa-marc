package de.gwdg.metadataqa.marc.definition.tags.tags6xx;

import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.Indicator;
import de.gwdg.metadataqa.marc.definition.general.codelist.RelatorCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.SubjectHeadingAndTermSourceCodes;

/**
 * Subject Added Entry - Uniform Title
 * http://www.loc.gov/marc/bibliographic/bd630.html
 */
public class Tag630 extends DataFieldDefinition {

	private static Tag630 uniqueInstance;

	private Tag630() {
		initialize();
		postCreation();
	}

	public static Tag630 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag630();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "630";
		label = "Subject Added Entry - Uniform Title";
		mqTag = "SubjectAddedUniformTitle";
		cardinality = Cardinality.Repeatable;
		descriptionUrl = "https://www.loc.gov/marc/bibliographic/bd630.html";

		ind1 = new Indicator("Nonfiling characters")
			.setCodes(
				"0-9", "Number of nonfiling characters"
			)
			.setMqTag("nonfilingCharacters");
		ind1.getCode("0-9").setRange(true);
		ind2 = new Indicator("Thesaurus")
			.setCodes(
				"0", "Library of Congress Subject Headings",
				"1", "LC subject headings for children's literature",
				"2", "Medical Subject Headings",
				"3", "National Agricultural Library subject authority file",
				"4", "Source not specified",
				"5", "Canadian Subject Headings",
				"6", "Répertoire de vedettes-matière",
				"7", "Source specified in subfield $2"
			)
			.setMqTag("thesaurus");

		setSubfieldsWithCardinality(
			"a", "Uniform title", "NR",
			"d", "Date of treaty signing", "R",
			"e", "Relator term", "R",
			"f", "Date of a work", "NR",
			"g", "Miscellaneous information", "R",
			"h", "Medium", "NR",
			"k", "Form subheading", "R",
			"l", "Language of a work", "NR",
			"m", "Medium of performance for music", "R",
			"n", "Number of part/section of a work", "R",
			"o", "Arranged statement for music", "NR",
			"p", "Name of part/section of a work", "R",
			"r", "Key for music", "NR",
			"s", "Version", "NR",
			"t", "Title of a work", "NR",
			"v", "Form subdivision", "R",
			"x", "General subdivision", "R",
			"y", "Chronological subdivision", "R",
			"z", "Geographic subdivision", "R",
			"0", "Authority record control number or standard number", "R",
			"2", "Source of heading or term", "NR",
			"3", "Materials specified", "NR",
			"4", "Relationship", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);
		getSubfield("2").setCodeList(SubjectHeadingAndTermSourceCodes.getInstance());
		getSubfield("4").setCodeList(RelatorCodes.getInstance());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("d").setMqTag("dateOfTreaty");
		getSubfield("e").setMqTag("relatorTerm");
		getSubfield("f").setMqTag("dateOfAWork");
		getSubfield("g").setMqTag("miscellaneous");
		getSubfield("h").setMqTag("medium");
		getSubfield("k").setMqTag("formSubheading");
		getSubfield("l").setMqTag("languageOfAWork");
		getSubfield("m").setMqTag("mediumOfPerformance");
		getSubfield("n").setMqTag("numberOfPart");
		getSubfield("o").setMqTag("arrangedStatement");
		getSubfield("p").setMqTag("nameOfPart");
		getSubfield("r").setMqTag("keyForMusic");
		getSubfield("s").setMqTag("version");
		getSubfield("t").setMqTag("titleOfAWork");
		getSubfield("v").setBibframeTag("formGenre").setMqTag("formSubdivision");
		getSubfield("x").setBibframeTag("topic").setMqTag("generalSubdivision");
		getSubfield("y").setBibframeTag("temporal").setMqTag("chronologicalSubdivision");
		getSubfield("z").setBibframeTag("geographic").setMqTag("geographicSubdivision");
		getSubfield("0").setMqTag("authorityRecordControlNumber");
		getSubfield("2").setMqTag("source");
		getSubfield("3").setMqTag("materialsSpecified");
		getSubfield("4").setMqTag("relationship");
		getSubfield("6").setMqTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}
}
