package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.api.model.JsonPathCache;
import de.gwdg.metadataqa.api.util.FileUtils;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MarcFactoryTest {

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void mainTest() throws IOException, URISyntaxException {
		JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json"));

		MarcRecord record = MarcFactory.create(cache);
		assertNotNull(record);
		assertNotNull("Leader should not be null", record.getLeader());
		// System.err.println(record.format());
		// System.err.println(record.formatAsMarc());
		// System.err.println(record.formatForIndex());
		// System.err.println(record.getKeyValuePairs());
		Map<String, List<String>> pairs = record.getKeyValuePairs(SolrFieldType.HUMAN);
		assertEquals(119, pairs.size());
		Set<String> keys = pairs.keySet();
		keys.remove("GentLocallyDefinedField");
		keys.remove("BemerkungenZurTitelaufnahme");
		assertEquals(
			"type, Leader, Leader_recordLength, Leader_recordStatus, Leader_typeOfRecord, " +
			"Leader_bibliographicLevel, Leader_typeOfControl, Leader_characterCodingScheme, " +
			"Leader_indicatorCount, Leader_subfieldCodeCount, Leader_baseAddressOfData, " +
			"Leader_encodingLevel, Leader_descriptiveCatalogingForm, Leader_multipartResourceRecordLevel, " +
			"Leader_lengthOfTheLengthOfFieldPortion, Leader_lengthOfTheStartingCharacterPositionPortion, " +
			"Leader_lengthOfTheImplementationDefinedPortion, ControlNumber, ControlNumberIdentifier, " +
			"LatestTransactionTime, PhysicalDescription, PhysicalDescription_categoryOfMaterial, " +
			"PhysicalDescription_specificMaterialDesignation, GeneralInformation, " +
			"GeneralInformation_dateEnteredOnFile, GeneralInformation_typeOfDateOrPublicationStatus, " +
			"GeneralInformation_date1, GeneralInformation_date2, " +
			"GeneralInformation_placeOfPublicationProductionOrExecution, GeneralInformation_language, " +
			"GeneralInformation_modifiedRecord, GeneralInformation_catalogingSource, " +
			"GeneralInformation_frequency, GeneralInformation_regularity, " +
			"GeneralInformation_typeOfContinuingResource, GeneralInformation_formOfOriginalItem, " +
			"GeneralInformation_formOfItem, GeneralInformation_natureOfEntireWork, " +
			"GeneralInformation_natureOfContents, GeneralInformation_governmentPublication, " +
			"GeneralInformation_conferencePublication, " +
			"GeneralInformation_originalAlphabetOrScriptOfTitle, GeneralInformation_entryConvention, " +
			"IdIntifiedByLocal_source, IdIntifiedByLocal, IdIntifiedByLocal_agency, " +
			"Issn_levelOfInternationalInterest, Issn, SystemControlNumber_organizationCode, " +
			"SystemControlNumber, SystemControlNumber_recordNumber, SystemControlNumber_organization, " +
			"AdminMetadata_languageOfCataloging, AdminMetadata_descriptionConventions, " +
			"AdminMetadata_transcribingAgency, AdminMetadata_catalogingAgency, " +
			"Language_translationIndication, Language, Language_sourceOfCode, Place_country, " +
			"ClassificationDdc_editionType, ClassificationDdc_classificationSource, ClassificationDdc, " +
			"Classification_classificationPortion, Classification_source, Title_subtitle, " +
			"Title_responsibilityStatement, Title_mainTitle, Title_titleAddedEntry, " +
			"Title_nonfilingCharacters, Title_partName, ParallelTitle_mainTitle, ParallelTitle_type, " +
			"ParallelTitle_displayText, ParallelTitle_noteAndAddedEntry, " +
			"Publication_sequenceOfPublishingStatements, Publication_agent, Publication_place, " +
			"DatesOfPublication, DatesOfPublication_format, NumberingPeculiarities, " +
			"RSWKKette_nummerDesKettengliedes, RSWKKette_0, RSWKKette_a, " +
			"RSWKKette_nummerDerRSWKKette, RSWKKette_D, RSWKKette_5, AddedCorporateName, " +
			"AddedCorporateName_authorityRecordControlNumber, " +
			"AddedCorporateName_authorityRecordControlNumber_recordNumber, " +
			"AddedCorporateName_authorityRecordControlNumber_organization, " +
			"AddedCorporateName_authorityRecordControlNumber_organizationCode, " +
			"AddedCorporateName_entryType, AddedCorporateName_nameType, PartOf_relation, PartOf_displayConstant, " +
			"PartOf_recordControlNumber, PartOf_noteController, PartOf_recordControlNumber_recordNumber, " +
			"PartOf_recordControlNumber_organization, PartOf_recordControlNumber_organizationCode, PartOf, " +
			"PrecededBy, PrecededBy_relation, PrecededBy_recordControlNumber_organization, " +
			"PrecededBy_recordControlNumber_recordNumber, PrecededBy_typeOfRelationship, " +
			"PrecededBy_recordControlNumber, PrecededBy_recordControlNumber_organizationCode, " +
			"PrecededBy_noteController, Bestandsinformationen, Bestandsinformationen_9, Bestandsinformationen_d, " +
			"Bestandsinformationen_artDerRessource, Bestandsinformationen_b, Bestandsinformationen_c, " +
			"Bestandsinformationen_h, Bestandsinformationen_g",
			StringUtils.join(keys, ", "));

		assertEquals("Continuing Resources", pairs.get("type").get(0));
		assertEquals("02703cas a2200481   4500", pairs.get("Leader").get(0));
		assertEquals("02703", pairs.get("Leader_recordLength").get(0));
		assertEquals("Corrected or revised", pairs.get("Leader_recordStatus").get(0));
		assertEquals("Language material", pairs.get("Leader_typeOfRecord").get(0));
		assertEquals("Serial", pairs.get("Leader_bibliographicLevel").get(0));
		assertEquals("No specified type", pairs.get("Leader_typeOfControl").get(0));
		assertEquals("UCS/Unicode", pairs.get("Leader_characterCodingScheme").get(0));
		assertEquals("2", pairs.get("Leader_indicatorCount").get(0));
		assertEquals("2", pairs.get("Leader_subfieldCodeCount").get(0));
		assertEquals("00481", pairs.get("Leader_baseAddressOfData").get(0));
		assertEquals("Full level", pairs.get("Leader_encodingLevel").get(0));
		assertEquals("Non-ISBD", pairs.get("Leader_descriptiveCatalogingForm").get(0));
		assertEquals("Not specified or not applicable", pairs.get("Leader_multipartResourceRecordLevel").get(0));
		assertEquals("4", pairs.get("Leader_lengthOfTheLengthOfFieldPortion").get(0));
		assertEquals("5", pairs.get("Leader_lengthOfTheStartingCharacterPositionPortion").get(0));
		assertEquals("0", pairs.get("Leader_lengthOfTheImplementationDefinedPortion").get(0));
		assertEquals("000000027", pairs.get("ControlNumber").get(0));
		assertEquals("DE-576", pairs.get("ControlNumberIdentifier").get(0));
		assertEquals("20150107102000.0", pairs.get("LatestTransactionTime").get(0));
		assertEquals("tu", pairs.get("PhysicalDescription").get(0));
		assertEquals("Text", pairs.get("PhysicalDescription_categoryOfMaterial").get(0));
		assertEquals("Unspecified", pairs.get("PhysicalDescription_specificMaterialDesignation").get(0));
		assertEquals("850101d19912003xx    p   b   0    0ger c", pairs.get("GeneralInformation").get(0));
		assertEquals("850101", pairs.get("GeneralInformation_dateEnteredOnFile").get(0));
		assertEquals("Continuing resource ceased publication", pairs.get("GeneralInformation_typeOfDateOrPublicationStatus").get(0));
		assertEquals("1991", pairs.get("GeneralInformation_date1").get(0));
		assertEquals("2003", pairs.get("GeneralInformation_date2").get(0));
		assertEquals("xx ", pairs.get("GeneralInformation_placeOfPublicationProductionOrExecution").get(0));
		assertEquals("ger", pairs.get("GeneralInformation_language").get(0));
		assertEquals("Not modified", pairs.get("GeneralInformation_modifiedRecord").get(0));
		assertEquals("Cooperative cataloging program", pairs.get("GeneralInformation_catalogingSource").get(0));
		assertEquals("No determinable frequency", pairs.get("GeneralInformation_frequency").get(0));
		assertEquals(" ", pairs.get("GeneralInformation_regularity").get(0));
		assertEquals("Periodical", pairs.get("GeneralInformation_typeOfContinuingResource").get(0));
		assertEquals("None of the following", pairs.get("GeneralInformation_formOfOriginalItem").get(0));
		assertEquals("None of the following", pairs.get("GeneralInformation_formOfItem").get(0));
		assertEquals("Not specified", pairs.get("GeneralInformation_natureOfEntireWork").get(0));
		assertEquals("Bibliographies, Not specified", pairs.get("GeneralInformation_natureOfContents").get(0));
		assertEquals("Not a government publication", pairs.get("GeneralInformation_governmentPublication").get(0));
		assertEquals("Not a conference publication", pairs.get("GeneralInformation_conferencePublication").get(0));
		assertEquals("No alphabet or script given/No key title", pairs.get("GeneralInformation_originalAlphabetOrScriptOfTitle").get(0));
		assertEquals("Successive entry", pairs.get("GeneralInformation_entryConvention").get(0));
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("IdIntifiedByLocal_source").get(0));
		assertEquals("1056377-5", pairs.get("IdIntifiedByLocal").get(0));
		assertEquals("Source specified in subfield $2", pairs.get("IdIntifiedByLocal_agency").get(0));
		assertEquals("No level specified", pairs.get("Issn_levelOfInternationalInterest").get(0));
		assertEquals("0939-0480", pairs.get("Issn").get(0));
		assertEquals("DE-599", pairs.get("SystemControlNumber_organizationCode").get(0));
		assertEquals("(DE-599)ZDB1056377-5", pairs.get("SystemControlNumber").get(0));
		assertEquals("ZDB1056377-5", pairs.get("SystemControlNumber_recordNumber").get(0));
		assertEquals("Arbeitsgemeinschaft der Verbundsysteme", pairs.get("SystemControlNumber_organization").get(0));
		assertEquals("German", pairs.get("AdminMetadata_languageOfCataloging").get(0));
		assertEquals("Regeln für die alphabetische Katalogisierung an wissenschaftlichen Bibliotheken (Berlin: Deutsches Bibliotheksinstitut)", pairs.get("AdminMetadata_descriptionConventions").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AdminMetadata_transcribingAgency").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AdminMetadata_catalogingAgency").get(0));
		assertEquals("No information provided", pairs.get("Language_translationIndication").get(0));
		assertEquals("German", pairs.get("Language").get(0));
		assertEquals("MARC language code", pairs.get("Language_sourceOfCode").get(0));
		assertEquals("XA-DE", pairs.get("Place_country").get(0));
		assertEquals("Full edition", pairs.get("ClassificationDdc_editionType").get(0));
		assertEquals("No information provided", pairs.get("ClassificationDdc_classificationSource").get(0));
		assertEquals("010", pairs.get("ClassificationDdc").get(0));
		assertEquals("110", pairs.get("Classification_classificationPortion").get(0));
		assertEquals("ZDB-Systematik = ZDB-Classification", pairs.get("Classification_source").get(0));
		assertEquals("Amtsblatt /", pairs.get("Title_subtitle").get(0));
		assertEquals("Bearb. u. Hrsg.: Die Deutsche Bibliothek (Deutsche Bücherei Leipzig, Deutsche Bibliothek Frankfurt a.M., Deutsches Musikarchiv Berlin).", pairs.get("Title_responsibilityStatement").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen :", pairs.get("Title_mainTitle").get(0));
		assertEquals("Added entry", pairs.get("Title_titleAddedEntry").get(0));
		assertEquals("No nonfiling characters", pairs.get("Title_nonfilingCharacters").get(0));
		assertEquals("A B, Monographien und Periodika des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : wöchentliches Verzeichnis ; Register", pairs.get("Title_partName").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / A B", pairs.get("ParallelTitle_mainTitle").get(0));
		assertEquals("No type specified", pairs.get("ParallelTitle_type").get(0));
		assertEquals("Hauptsacht. anfangs: ", pairs.get("ParallelTitle_displayText").get(0));
		assertEquals("9", pairs.get("ParallelTitle_noteAndAddedEntry").get(0));
		assertEquals("Not applicable/No information provided/Earliest available publisher", pairs.get("Publication_sequenceOfPublishingStatements").get(0));
		assertEquals("Buchhändler-Vereinigung", pairs.get("Publication_agent").get(0));
		assertEquals("Frankfurt, M. :", pairs.get("Publication_place").get(0));
		assertEquals("1991 - 2003; damit Ersch. eingest.", pairs.get("DatesOfPublication").get(0));
		assertEquals("Unformatted note", pairs.get("DatesOfPublication_format").get(0));
		assertEquals("Ersch. wöchentlich, jedes 4.-5. Heft als Monatsregister", pairs.get("NumberingPeculiarities").get(0));
		// assertEquals("84!(09-02-04)", pairs.get("BemerkungenZurTitelaufnahme").get(0)); //
		assertEquals("Abschluss einer RSWK-Kettenfolge, Feld enthält dann zwei $5", pairs.get("RSWKKette_nummerDesKettengliedes").get(0));
		assertEquals("(DE-576)208865578", pairs.get("RSWKKette_0").get(0));
		assertEquals("Bibliografie", pairs.get("RSWKKette_a").get(0));
		assertEquals("Nummer der RSWK-Kette: 0", pairs.get("RSWKKette_nummerDerRSWKKette").get(0));
		assertEquals("s", pairs.get("RSWKKette_D").get(0));
		assertEquals("DE-101", pairs.get("RSWKKette_5").get(0));
		assertEquals("Deutsche Bibliothek", pairs.get("AddedCorporateName").get(0));
		assertEquals("(DE-576)190187867", pairs.get("AddedCorporateName_authorityRecordControlNumber").get(0));
		assertEquals("190187867", pairs.get("AddedCorporateName_authorityRecordControlNumber_recordNumber").get(0));
		assertEquals("Bibliotheksservice-Zentrum Baden-Württemberg (BSZ)", pairs.get("AddedCorporateName_authorityRecordControlNumber_organization").get(0));
		assertEquals("DE-576", pairs.get("AddedCorporateName_authorityRecordControlNumber_organizationCode").get(0));
		assertEquals("No information provided", pairs.get("AddedCorporateName_entryType").get(0));
		assertEquals("Name in direct order", pairs.get("AddedCorporateName_nameType").get(0));
		assertEquals("Index zu", pairs.get("PartOf_relation").get(0));
		assertEquals("No display constant generated", pairs.get("PartOf_displayConstant").get(0));
		assertEquals("(DE-600)1056366-0", pairs.get("PartOf_recordControlNumber").get(0));
		assertEquals("Display note", pairs.get("PartOf_noteController").get(0));
		assertEquals("1056366-0", pairs.get("PartOf_recordControlNumber_recordNumber").get(0));
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("PartOf_recordControlNumber_organization").get(0));
		assertEquals("DE-600", pairs.get("PartOf_recordControlNumber_organizationCode").get(0));
		assertEquals("Deutsche Nationalbibliografie und Bibliografie der im Ausland erschienenen deutschsprachigen Veröffentlichungen / B", pairs.get("PartOf").get(0));
		assertEquals("Deutsche Bibliographie. Wöchentliches Verzeichnis. Reihe A und Reihe B, Erscheinungen des Verlagsbuchhandels und außerhalb des Verlagsbuchhandels : Register", pairs.get("PrecededBy").get(0));
		assertEquals("Vorg.", pairs.get("PrecededBy_relation").get(0));
		assertEquals("Zeitschriftendatenbank (ZDB)", pairs.get("PrecededBy_recordControlNumber_organization").get(0));
		assertEquals("1429255", pairs.get("PrecededBy_recordControlNumber_recordNumber").get(0));
		assertEquals("Continues", pairs.get("PrecededBy_typeOfRelationship").get(0));
		assertEquals("(DE-600)1429255", pairs.get("PrecededBy_recordControlNumber").get(0));
		assertEquals("DE-600", pairs.get("PrecededBy_recordControlNumber_organizationCode").get(0));
		assertEquals("Display note", pairs.get("PrecededBy_noteController").get(0));
	}

	@Test
	public void marc2Test() throws IOException, URISyntaxException {
		JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/marc2.json"));

		MarcRecord record = MarcFactory.create(cache);
		assertNotNull(record);
		assertNotNull("Leader should not be null", record.getLeader());

		List<DataField> admins = record.getDatafield("040");
		assertEquals(1, admins.size());
		DataField adminMeta = admins.get(0);
		List<MarcSubfield> subfields = adminMeta.getSubfields();
		for (MarcSubfield subfield : subfields) {
			if (subfield.getCode().equals("b")) {
				assertEquals("LanguageCodes", subfield.getDefinition().getCodeList().getClass().getSimpleName());
				assertEquals("English", subfield.resolve());
			}
		}

		assertEquals(Arrays.asList("English"), record
			.getKeyValuePairs(SolrFieldType.HUMAN)
			.get("AdminMetadata_languageOfCataloging"));
	}

	@Test
	public void getKeyValuePairTest() throws IOException, URISyntaxException {
		JsonPathCache cache = new JsonPathCache(FileUtils.readFirstLine("general/verbund-tit.001.0000000.formatted.json"));

		MarcRecord record = MarcFactory.create(cache);
		Map<String, List<String>> pairs = record.getKeyValuePairs(SolrFieldType.MIXED);
		assertEquals(119, pairs.size());

		Set<String> keys = pairs.keySet();
		keys.remove("591a_GentLocallyDefinedField");
		keys.remove("591a_BemerkungenZurTitelaufnahme");

		assertEquals(
			"type, " +
				"Leader, " +
				"Leader_00-04_recordLength, " +
				"Leader_05_recordStatus, " +
				"Leader_06_typeOfRecord, " +
				"Leader_07_bibliographicLevel, " +
				"Leader_08_typeOfControl, " +
				"Leader_09_characterCodingScheme, " +
				"Leader_10_indicatorCount, " +
				"Leader_11_subfieldCodeCount, " +
				"Leader_12-16_baseAddressOfData, " +
				"Leader_17_encodingLevel, " +
				"Leader_18_descriptiveCatalogingForm, " +
				"Leader_19_multipartResourceRecordLevel, " +
				"Leader_20_lengthOfTheLengthOfFieldPortion, " +
				"Leader_21_lengthOfTheStartingCharacterPositionPortion, " +
				"Leader_22_lengthOfTheImplementationDefinedPortion, " +
				"001_ControlNumber, " +
				"003_ControlNumberIdentifier, " +
				"005_LatestTransactionTime, " +
				"007_PhysicalDescription, " +
				"007_00_PhysicalDescription_categoryOfMaterial, " +
				"007_01_PhysicalDescription_specificMaterialDesignation, " +
				"008_GeneralInformation, " +
				"008_00-05_GeneralInformation_dateEnteredOnFile, " +
				"008_06_GeneralInformation_typeOfDateOrPublicationStatus, " +
				"008_07-10_GeneralInformation_date1, " +
				"008_11-14_GeneralInformation_date2, " +
				"008_15-17_GeneralInformation_placeOfPublicationProductionOrExecution, " +
				"008_35-37_GeneralInformation_language, " +
				"008_38_GeneralInformation_modifiedRecord, " +
				"008_39_GeneralInformation_catalogingSource, " +
				"008_18_GeneralInformation_frequency, " +
				"008_19_GeneralInformation_regularity, " +
				"008_21_GeneralInformation_typeOfContinuingResource, " +
				"008_22_GeneralInformation_formOfOriginalItem, " +
				"008_23_GeneralInformation_formOfItem, " +
				"008_24_GeneralInformation_natureOfEntireWork, " +
				"008_25-27_GeneralInformation_natureOfContents, " +
				"008_28_GeneralInformation_governmentPublication, " +
				"008_29_GeneralInformation_conferencePublication, " +
				"008_33_GeneralInformation_originalAlphabetOrScriptOfTitle, " +
				"008_34_GeneralInformation_entryConvention, " +
				"0162_IdIntifiedByLocal_source, " +
				"016ind1_IdIntifiedByLocal_agency, " +
				"016a_IdIntifiedByLocal, " +
				"022ind1_Issn_levelOfInternationalInterest, " +
				"022a_Issn, " +
				"035a_SystemControlNumber_recordNumber, " +
				"035a_SystemControlNumber, " +
				"035a_SystemControlNumber_organizationCode, " +
				"035a_SystemControlNumber_organization, " +
				"040e_AdminMetadata_descriptionConventions, " +
				"040a_AdminMetadata_catalogingAgency, " +
				"040b_AdminMetadata_languageOfCataloging, " +
				"040c_AdminMetadata_transcribingAgency, " +
				"041a_Language, " +
				"041ind1_Language_translationIndication, " +
				"041ind2_Language_sourceOfCode, " +
				"044a_Place_country, " +
				"082ind2_ClassificationDdc_classificationSource, " +
				"082ind1_ClassificationDdc_editionType, " +
				"082a_ClassificationDdc, " +
				"0842_Classification_source, " +
				"084a_Classification_classificationPortion, " +
				"245a_Title_mainTitle, " +
				"245ind1_Title_titleAddedEntry, " +
				"245ind2_Title_nonfilingCharacters, " +
				"245p_Title_partName, " +
				"245b_Title_subtitle, " +
				"245c_Title_responsibilityStatement, " +
				"246ind1_ParallelTitle_noteAndAddedEntry, " +
				"246i_ParallelTitle_displayText, " +
				"246a_ParallelTitle_mainTitle, " +
				"246ind2_ParallelTitle_type, " +
				"260b_Publication_agent, " +
				"260a_Publication_place, " +
				"260ind1_Publication_sequenceOfPublishingStatements, " +
				"362ind1_DatesOfPublication_format, " +
				"362a_DatesOfPublication, " +
				"515a_NumberingPeculiarities, " +
				// "591a_GentLocallyDefinedField, " +
				"6890_RSWKKette_0, " +
				"689D_RSWKKette_D, " +
				"689ind2_RSWKKette_nummerDesKettengliedes, " +
				"689a_RSWKKette_a, " +
				"689ind1_RSWKKette_nummerDerRSWKKette, " +
				"6895_RSWKKette_5, " +
				"7100_AddedCorporateName_authorityRecordControlNumber, " +
				"7100_AddedCorporateName_authorityRecordControlNumber_recordNumber, " +
				"7100_AddedCorporateName_authorityRecordControlNumber_organization, " +
				"7100_AddedCorporateName_authorityRecordControlNumber_organizationCode, " +
				"710a_AddedCorporateName, " +
				"710ind1_AddedCorporateName_nameType, " +
				"710ind2_AddedCorporateName_entryType, " +
				"773w_PartOf_recordControlNumber, " +
				"773w_PartOf_recordControlNumber_organizationCode, " +
				"773ind1_PartOf_noteController, " +
				"773a_PartOf, " +
				"773i_PartOf_relation, " +
				"773ind2_PartOf_displayConstant, " +
				"773w_PartOf_recordControlNumber_organization, " +
				"773w_PartOf_recordControlNumber_recordNumber, " +
				"780ind1_PrecededBy_noteController, " +
				"780ind2_PrecededBy_typeOfRelationship, " +
				"780w_PrecededBy_recordControlNumber_organization, " +
				"780w_PrecededBy_recordControlNumber_organizationCode, " +
				"780i_PrecededBy_relation, " +
				"780w_PrecededBy_recordControlNumber_recordNumber, " +
				"780a_PrecededBy, " +
				"780w_PrecededBy_recordControlNumber, " +
				"924ind1_Bestandsinformationen_artDerRessource, " +
				"924a_Bestandsinformationen, " +
				"924d_Bestandsinformationen_d, " +
				"924b_Bestandsinformationen_b, " +
				"9249_Bestandsinformationen_9, " +
				"924c_Bestandsinformationen_c, " +
				"924h_Bestandsinformationen_h, " +
				"924g_Bestandsinformationen_g",
			StringUtils.join(pairs.keySet(), ", "));
	}

}
