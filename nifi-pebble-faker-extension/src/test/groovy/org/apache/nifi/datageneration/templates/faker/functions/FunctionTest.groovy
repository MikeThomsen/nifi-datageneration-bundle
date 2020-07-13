package org.apache.nifi.datageneration.templates.faker.functions

import com.mitchellbosecke.pebble.PebbleEngine
import com.mitchellbosecke.pebble.loader.ClasspathLoader
import org.apache.nifi.datageneration.templates.faker.FakerExtension
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test

class FunctionTest {
	def basicTemplateTest = { template ->
		def writer = new StringWriter()
		ENGINE.getTemplate(template).evaluate(writer)
		writer.close()

		def result = writer.toString()
		Assert.assertFalse(result.trim().isEmpty())

		result
	}

	def postExecute = { result, Closure c ->

	}

	static PebbleEngine ENGINE

	@BeforeClass
	static void beforeClass() {
		ENGINE = new PebbleEngine.Builder()
			.loader(new ClasspathLoader())
			.extension(new FakerExtension())
			.build()
	}

	@Test
	void testAddressBuildingNumberFunction() {
		def result = basicTemplateTest("templates/address_building_number_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCityFunction() {
		def result = basicTemplateTest("templates/address_city_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCityNameFunction() {
		def result = basicTemplateTest("templates/address_city_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCityPrefixFunction() {
		def result = basicTemplateTest("templates/address_city_prefix_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCitySuffixFunction() {
		def result = basicTemplateTest("templates/address_city_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCountryFunction() {
		def result = basicTemplateTest("templates/address_country_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressCountryCodeFunction() {
		def result = basicTemplateTest("templates/address_country_code_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressFirstNameFunction() {
		def result = basicTemplateTest("templates/address_first_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressFullAddressFunction() {
		def result = basicTemplateTest("templates/address_full_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressLastNameFunction() {
		def result = basicTemplateTest("templates/address_last_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressLatitudeFunction() {
		def result = basicTemplateTest("templates/address_latitude_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressLongitudeFunction() {
		def result = basicTemplateTest("templates/address_longitude_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressSecondaryAddressFunction() {
		def result = basicTemplateTest("templates/address_secondary_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStateFunction() {
		def result = basicTemplateTest("templates/address_state_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStateAbbrFunction() {
		def result = basicTemplateTest("templates/address_state_abbr_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStreetAddressFunction() {
		def result = basicTemplateTest("templates/address_street_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStreetAddressNumberFunction() {
		def result = basicTemplateTest("templates/address_street_address_number_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStreetNameFunction() {
		def result = basicTemplateTest("templates/address_street_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStreetPrefixFunction() {
		def result = basicTemplateTest("templates/address_street_prefix_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressStreetSuffixFunction() {
		def result = basicTemplateTest("templates/address_street_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressTimeZoneFunction() {
		def result = basicTemplateTest("templates/address_time_zone_test.template")
		postExecute(result) { }
	}

	@Test
	void testAddressZipCodeFunction() {
		def result = basicTemplateTest("templates/address_zip_code_test.template")
		postExecute(result) { }
	}

	@Ignore("Faker functionality appears to be broken...")
	@Test
	void testAddressZipCodeByStateFunction() {
		def result = basicTemplateTest("templates/address_zip_code_by_state_test.template")
		postExecute(result) { }
	}

	@Test
	void testAncientGodFunction() {
		def result = basicTemplateTest("templates/ancient_god_test.template")
		postExecute(result) { }
	}

	@Test
	void testAncientHeroFunction() {
		def result = basicTemplateTest("templates/ancient_hero_test.template")
		postExecute(result) { }
	}

	@Test
	void testAncientPrimordialFunction() {
		def result = basicTemplateTest("templates/ancient_primordial_test.template")
		postExecute(result) { }
	}

	@Test
	void testAncientTitanFunction() {
		def result = basicTemplateTest("templates/ancient_titan_test.template")
		postExecute(result) { }
	}

	@Test
	void testAppAuthorFunction() {
		def result = basicTemplateTest("templates/app_author_test.template")
		postExecute(result) { }
	}

	@Test
	void testAppNameFunction() {
		def result = basicTemplateTest("templates/app_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAppVersionFunction() {
		def result = basicTemplateTest("templates/app_version_test.template")
		postExecute(result) { }
	}

	@Test
	void testArtistNameFunction() {
		def result = basicTemplateTest("templates/artist_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testAvatarImageFunction() {
		def result = basicTemplateTest("templates/avatar_image_test.template")
		postExecute(result) { }
	}

	@Test
	void testBeerHopFunction() {
		def result = basicTemplateTest("templates/beer_hop_test.template")
		postExecute(result) { }
	}

	@Test
	void testBeerMaltFunction() {
		def result = basicTemplateTest("templates/beer_malt_test.template")
		postExecute(result) { }
	}

	@Test
	void testBeerNameFunction() {
		def result = basicTemplateTest("templates/beer_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testBeerStyleFunction() {
		def result = basicTemplateTest("templates/beer_style_test.template")
		postExecute(result) { }
	}

	@Test
	void testBeerYeastFunction() {
		def result = basicTemplateTest("templates/beer_yeast_test.template")
		postExecute(result) { }
	}

	@Test
	void testBookAuthorFunction() {
		def result = basicTemplateTest("templates/book_author_test.template")
		postExecute(result) { }
	}

	@Test
	void testBookGenreFunction() {
		def result = basicTemplateTest("templates/book_genre_test.template")
		postExecute(result) { }
	}

	@Test
	void testBookPublisherFunction() {
		def result = basicTemplateTest("templates/book_publisher_test.template")
		postExecute(result) { }
	}

	@Test
	void testBookTitleFunction() {
		def result = basicTemplateTest("templates/book_title_test.template")
		postExecute(result) { }
	}

	@Test
	void testBoolBoolFunction() {
		def result = basicTemplateTest("templates/bool_bool_test.template")
		postExecute(result) { }
	}

	@Test
	void testBusinessCreditCardExpiryFunction() {
		def result = basicTemplateTest("templates/business_credit_card_expiry_test.template")
		postExecute(result) { }
	}

	@Test
	void testBusinessCreditCardNumberFunction() {
		def result = basicTemplateTest("templates/business_credit_card_number_test.template")
		postExecute(result) { }
	}

	@Test
	void testBusinessCreditCardTypeFunction() {
		def result = basicTemplateTest("templates/business_credit_card_type_test.template")
		postExecute(result) { }
	}

	@Test
	void testCatBreedFunction() {
		def result = basicTemplateTest("templates/cat_breed_test.template")
		postExecute(result) { }
	}

	@Test
	void testCatNameFunction() {
		def result = basicTemplateTest("templates/cat_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testCatRegistryFunction() {
		def result = basicTemplateTest("templates/cat_registry_test.template")
		postExecute(result) { }
	}

	@Test
	void testChuckNorrisFactFunction() {
		def result = basicTemplateTest("templates/chucknorris_fact_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeAsinFunction() {
		def result = basicTemplateTest("templates/code_asin_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeEan13Function() {
		def result = basicTemplateTest("templates/code_ean13_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeEan8Function() {
		def result = basicTemplateTest("templates/code_ean8_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeGtin13Function() {
		def result = basicTemplateTest("templates/code_gtin13_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeGtin8Function() {
		def result = basicTemplateTest("templates/code_gtin8_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeImeiFunction() {
		def result = basicTemplateTest("templates/code_imei_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeIsbn10Function() {
		def result = basicTemplateTest("templates/code_isbn10_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeIsbn13Function() {
		def result = basicTemplateTest("templates/code_isbn13_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeIsbnGroupFunction() {
		def result = basicTemplateTest("templates/code_isbn_group_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeIsbnGs1Function() {
		def result = basicTemplateTest("templates/code_isbn_gs1_test.template")
		postExecute(result) { }
	}

	@Test
	void testCodeIsbnRegistrantFunction() {
		def result = basicTemplateTest("templates/code_isbn_registrant_test.template")
		postExecute(result) { }
	}

	@Test
	void testColorNameFunction() {
		def result = basicTemplateTest("templates/color_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommerceColorFunction() {
		def result = basicTemplateTest("templates/commerce_color_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommerceDepartmentFunction() {
		def result = basicTemplateTest("templates/commerce_department_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommerceMaterialFunction() {
		def result = basicTemplateTest("templates/commerce_material_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommercePriceFunction() {
		def result = basicTemplateTest("templates/commerce_price_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommerceProductNameFunction() {
		def result = basicTemplateTest("templates/commerce_product_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testCommercePromotionCodeFunction() {
		def result = basicTemplateTest("templates/commerce_promotion_code_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyBsFunction() {
		def result = basicTemplateTest("templates/company_bs_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyBuzzwordFunction() {
		def result = basicTemplateTest("templates/company_buzzword_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyCatchPhraseFunction() {
		def result = basicTemplateTest("templates/company_catch_phrase_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyIndustryFunction() {
		def result = basicTemplateTest("templates/company_industry_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyLogoFunction() {
		def result = basicTemplateTest("templates/company_logo_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyNameFunction() {
		def result = basicTemplateTest("templates/company_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyProfessionFunction() {
		def result = basicTemplateTest("templates/company_profession_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanySuffixFunction() {
		def result = basicTemplateTest("templates/company_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testCompanyUrlFunction() {
		def result = basicTemplateTest("templates/company_url_test.template")
		postExecute(result) { }
	}

	@Test
	void testCryptoMd5Function() {
		def result = basicTemplateTest("templates/crypto_md5_test.template")
		postExecute(result) { }
	}

	@Test
	void testCryptoSha1Function() {
		def result = basicTemplateTest("templates/crypto_sha1_test.template")
		postExecute(result) { }
	}

	@Test
	void testCryptoSha256Function() {
		def result = basicTemplateTest("templates/crypto_sha256_test.template")
		postExecute(result) { }
	}

	@Test
	void testCryptoSha512Function() {
		def result = basicTemplateTest("templates/crypto_sha512_test.template")
		postExecute(result) { }
	}

	@Test
	void testCurrencyCodeFunction() {
		def result = basicTemplateTest("templates/currency_code_test.template")
		postExecute(result) { }
	}

	@Test
	void testCurrencyNameFunction() {
		def result = basicTemplateTest("templates/currency_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testDateBetweenFunction() {
		def result = basicTemplateTest("templates/date_between_test.template")
		postExecute(result) { }
	}

	@Test
	void testDateBirthdayFunction() {
		def result = basicTemplateTest("templates/date_birthday_test.template")
		postExecute(result) { }
	}

	@Test
	void testDateFutureFunction() {
		def result = basicTemplateTest("templates/date_future_test.template")
		postExecute(result) { }
	}

	@Test
	void testDatePastFunction() {
		def result = basicTemplateTest("templates/date_past_test.template")
		postExecute(result) { }
	}

	@Test
	void testDemographicDemonymFunction() {
		def result = basicTemplateTest("templates/demographic_demonym_test.template")
		postExecute(result) { }
	}

	@Test
	void testDemographicEducationalAttainmentFunction() {
		def result = basicTemplateTest("templates/demographic_educational_attainment_test.template")
		postExecute(result) { }
	}

	@Test
	void testDemographicMaritalStatusFunction() {
		def result = basicTemplateTest("templates/demographic_marital_status_test.template")
		postExecute(result) { }
	}

	@Test
	void testDemographicRaceFunction() {
		def result = basicTemplateTest("templates/demographic_race_test.template")
		postExecute(result) { }
	}

	@Test
	void testDemographicSexFunction() {
		def result = basicTemplateTest("templates/demographic_sex_test.template")
		postExecute(result) { }
	}

	@Test
	void testEducatorCampusFunction() {
		def result = basicTemplateTest("templates/educator_campus_test.template")
		postExecute(result) { }
	}

	@Test
	void testEducatorCourseFunction() {
		def result = basicTemplateTest("templates/educator_course_test.template")
		postExecute(result) { }
	}

	@Test
	void testEducatorSecondarySchoolFunction() {
		def result = basicTemplateTest("templates/educator_secondary_school_test.template")
		postExecute(result) { }
	}

	@Test
	void testEducatorUniversityFunction() {
		def result = basicTemplateTest("templates/educator_university_test.template")
		postExecute(result) { }
	}

	@Test
	void testEsportsEventFunction() {
		def result = basicTemplateTest("templates/esports_event_test.template")
		postExecute(result) { }
	}

	@Test
	void testEsportsGameFunction() {
		def result = basicTemplateTest("templates/esports_game_test.template")
		postExecute(result) { }
	}

	@Test
	void testEsportsLeagueFunction() {
		def result = basicTemplateTest("templates/esports_league_test.template")
		postExecute(result) { }
	}

	@Test
	void testEsportsPlayerFunction() {
		def result = basicTemplateTest("templates/esports_player_test.template")
		postExecute(result) { }
	}

	@Test
	void testEsportsTeamFunction() {
		def result = basicTemplateTest("templates/esports_team_test.template")
		postExecute(result) { }
	}

	@Test
	void testFileExtensionFunction() {
		def result = basicTemplateTest("templates/file_extension_test.template")
		postExecute(result) { }
	}

	@Test
	void testFileFileNameFunction() {
		def result = basicTemplateTest("templates/file_file_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testFileMimeTypeFunction() {
		def result = basicTemplateTest("templates/file_mime_type_test.template")
		postExecute(result) { }
	}

	@Test
	void testFinanceBicFunction() {
		def result = basicTemplateTest("templates/finance_bic_test.template")
		postExecute(result) { }
	}

	@Test
	void testFinanceCreditCardFunction() {
		def result = basicTemplateTest("templates/finance_credit_card_test.template")
		postExecute(result) { }
	}

	@Test
	void testFinanceIbanFunction() {
		def result = basicTemplateTest("templates/finance_iban_test.template")
		postExecute(result) { }
	}

	@Test
	void testFoodIngredientFunction() {
		def result = basicTemplateTest("templates/food_ingredient_test.template")
		postExecute(result) { }
	}

	@Test
	void testFoodMeasurementFunction() {
		def result = basicTemplateTest("templates/food_measurement_test.template")
		postExecute(result) { }
	}

	@Test
	void testFoodSpiceFunction() {
		def result = basicTemplateTest("templates/food_spice_test.template")
		postExecute(result) { }
	}

	@Test
	void testFriendsCharacterFunction() {
		def result = basicTemplateTest("templates/friends_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testFriendsLocationFunction() {
		def result = basicTemplateTest("templates/friends_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testFriendsQuoteFunction() {
		def result = basicTemplateTest("templates/friends_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testGameOfThronesCharacterFunction() {
		def result = basicTemplateTest("templates/gameofthrones_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testGameOfThronesCityFunction() {
		def result = basicTemplateTest("templates/gameofthrones_city_test.template")
		postExecute(result) { }
	}

	@Test
	void testGameOfThronesDragonFunction() {
		def result = basicTemplateTest("templates/gameofthrones_dragon_test.template")
		postExecute(result) { }
	}

	@Test
	void testGameOfThronesHouseFunction() {
		def result = basicTemplateTest("templates/gameofthrones_house_test.template")
		postExecute(result) { }
	}

	@Test
	void testGameOfThronesQuoteFunction() {
		def result = basicTemplateTest("templates/gameofthrones_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testHackerAbbreviationFunction() {
		def result = basicTemplateTest("templates/hacker_abbreviation_test.template")
		postExecute(result) { }
	}

	@Test
	void testHackerAdjectiveFunction() {
		def result = basicTemplateTest("templates/hacker_adjective_test.template")
		postExecute(result) { }
	}

	@Test
	void testHackerIngverbFunction() {
		def result = basicTemplateTest("templates/hacker_ingverb_test.template")
		postExecute(result) { }
	}

	@Test
	void testHackerNounFunction() {
		def result = basicTemplateTest("templates/hacker_noun_test.template")
		postExecute(result) { }
	}

	@Test
	void testHackerVerbFunction() {
		def result = basicTemplateTest("templates/hacker_verb_test.template")
		postExecute(result) { }
	}

	@Test
	void testHarryPotterBookFunction() {
		def result = basicTemplateTest("templates/harrypotter_book_test.template")
		postExecute(result) { }
	}

	@Test
	void testHarryPotterCharacterFunction() {
		def result = basicTemplateTest("templates/harrypotter_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testHarryPotterLocationFunction() {
		def result = basicTemplateTest("templates/harrypotter_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testHarryPotterQuoteFunction() {
		def result = basicTemplateTest("templates/harrypotter_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testHipsterWordFunction() {
		def result = basicTemplateTest("templates/hipster_word_test.template")
		postExecute(result) { }
	}

	@Test
	void testIdNumberInvalidFunction() {
		def result = basicTemplateTest("templates/idnumber_invalid_test.template")
		postExecute(result) { }
	}

	@Test
	void testIdNumberInvalidSvSeSsnFunction() {
		def result = basicTemplateTest("templates/idnumber_invalid_sv_se_ssn_test.template")
		postExecute(result) { }
	}

	@Test
	void testIdNumberSsnValidFunction() {
		def result = basicTemplateTest("templates/idnumber_ssn_valid_test.template")
		postExecute(result) { }
	}

	@Test
	void testIdNumberValidFunction() {
		def result = basicTemplateTest("templates/idnumber_valid_test.template")
		postExecute(result) { }
	}

	@Test
	void testIdNumberValidSvSeSsnFunction() {
		def result = basicTemplateTest("templates/idnumber_valid_sv_se_ssn_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetAvatarFunction() {
		def result = basicTemplateTest("templates/internet_avatar_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetDomainNameFunction() {
		def result = basicTemplateTest("templates/internet_domain_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetDomainSuffixFunction() {
		def result = basicTemplateTest("templates/internet_domain_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetDomainWordFunction() {
		def result = basicTemplateTest("templates/internet_domain_word_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetEmailAddressFunction() {
		def result = basicTemplateTest("templates/internet_email_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetImageFunction() {
		def result = basicTemplateTest("templates/internet_image_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetIpV4AddressFunction() {
		def result = basicTemplateTest("templates/internet_ip_v4_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetIpV4CidrFunction() {
		def result = basicTemplateTest("templates/internet_ip_v4_cidr_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetIpV6AddressFunction() {
		def result = basicTemplateTest("templates/internet_ip_v6_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetIpV6CidrFunction() {
		def result = basicTemplateTest("templates/internet_ip_v6_cidr_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetMacAddressFunction() {
		def result = basicTemplateTest("templates/internet_mac_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetPasswordFunction() {
		def result = basicTemplateTest("templates/internet_password_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetPrivateIpV4AddressFunction() {
		def result = basicTemplateTest("templates/internet_private_ip_v4_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetPublicIpV4AddressFunction() {
		def result = basicTemplateTest("templates/internet_public_ip_v4_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetSafeEmailAddressFunction() {
		def result = basicTemplateTest("templates/internet_safe_email_address_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetSlugFunction() {
		def result = basicTemplateTest("templates/internet_slug_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetUrlFunction() {
		def result = basicTemplateTest("templates/internet_url_test.template")
		postExecute(result) { }
	}

	@Test
	void testInternetUuidFunction() {
		def result = basicTemplateTest("templates/internet_uuid_test.template")
		postExecute(result) { }
	}

	@Test
	void testJobFieldFunction() {
		def result = basicTemplateTest("templates/job_field_test.template")
		postExecute(result) { }
	}

	@Test
	void testJobKeySkillsFunction() {
		def result = basicTemplateTest("templates/job_key_skills_test.template")
		postExecute(result) { }
	}

	@Test
	void testJobPositionFunction() {
		def result = basicTemplateTest("templates/job_position_test.template")
		postExecute(result) { }
	}

	@Test
	void testJobSeniorityFunction() {
		def result = basicTemplateTest("templates/job_seniority_test.template")
		postExecute(result) { }
	}

	@Test
	void testJobTitleFunction() {
		def result = basicTemplateTest("templates/job_title_test.template")
		postExecute(result) { }
	}

	@Test
	void testLebowskiActorFunction() {
		def result = basicTemplateTest("templates/lebowski_actor_test.template")
		postExecute(result) { }
	}

	@Test
	void testLebowskiCharacterFunction() {
		def result = basicTemplateTest("templates/lebowski_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testLebowskiQuoteFunction() {
		def result = basicTemplateTest("templates/lebowski_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testLordOfTheRingsCharacterFunction() {
		def result = basicTemplateTest("templates/lordoftherings_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testLordOfTheRingsLocationFunction() {
		def result = basicTemplateTest("templates/lordoftherings_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremCharacterFunction() {
		def result = basicTemplateTest("templates/lorem_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremCharactersFunction() {
		def result = basicTemplateTest("templates/lorem_characters_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremFixedStringFunction() {
		def result = basicTemplateTest("templates/lorem_fixed_string_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremParagraphFunction() {
		def result = basicTemplateTest("templates/lorem_paragraph_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremParagraphsFunction() {
		def result = basicTemplateTest("templates/lorem_paragraphs_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremSentenceFunction() {
		def result = basicTemplateTest("templates/lorem_sentence_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremSentencesFunction() {
		def result = basicTemplateTest("templates/lorem_sentences_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremWordFunction() {
		def result = basicTemplateTest("templates/lorem_word_test.template")
		postExecute(result) { }
	}

	@Test
	void testLoremWordsFunction() {
		def result = basicTemplateTest("templates/lorem_words_test.template")
		postExecute(result) { }
	}

	@Test
	void testMatzQuoteFunction() {
		def result = basicTemplateTest("templates/matz_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testMusicChordFunction() {
		def result = basicTemplateTest("templates/music_chord_test.template")
		postExecute(result) { }
	}

	@Test
	void testMusicInstrumentFunction() {
		def result = basicTemplateTest("templates/music_instrument_test.template")
		postExecute(result) { }
	}

	@Test
	void testMusicKeyFunction() {
		def result = basicTemplateTest("templates/music_key_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameFirstNameFunction() {
		def result = basicTemplateTest("templates/name_first_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameFullNameFunction() {
		def result = basicTemplateTest("templates/name_full_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameLastNameFunction() {
		def result = basicTemplateTest("templates/name_last_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameNameFunction() {
		def result = basicTemplateTest("templates/name_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameNameWithMiddleFunction() {
		def result = basicTemplateTest("templates/name_name_with_middle_test.template")
		postExecute(result) { }
	}

	@Test
	void testNamePrefixFunction() {
		def result = basicTemplateTest("templates/name_prefix_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameSuffixFunction() {
		def result = basicTemplateTest("templates/name_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameTitleFunction() {
		def result = basicTemplateTest("templates/name_title_test.template")
		postExecute(result) { }
	}

	@Test
	void testNameUsernameFunction() {
		def result = basicTemplateTest("templates/name_username_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberDigitFunction() {
		def result = basicTemplateTest("templates/number_digit_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberDigitsFunction() {
		def result = basicTemplateTest("templates/number_digits_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberNumberBetweenFunction() {
		def result = basicTemplateTest("templates/number_number_between_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberRandomDigitFunction() {
		def result = basicTemplateTest("templates/number_random_digit_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberRandomDigitNotZeroFunction() {
		def result = basicTemplateTest("templates/number_random_digit_not_zero_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberRandomDoubleFunction() {
		def result = basicTemplateTest("templates/number_random_double_test.template")
		postExecute(result) { }
	}

	@Test
	void testNumberRandomNumberFunction() {
		def result = basicTemplateTest("templates/number_random_number_test.template")
		postExecute(result) { }
	}

	@Test
	void testPhoneNumberCellPhoneFunction() {
		def result = basicTemplateTest("templates/phonenumber_cell_phone_test.template")
		postExecute(result) { }
	}

	@Test
	void testPhoneNumberPhoneNumberFunction() {
		def result = basicTemplateTest("templates/phonenumber_phone_number_test.template")
		postExecute(result) { }
	}

	@Test
	void testPokemonLocationFunction() {
		def result = basicTemplateTest("templates/pokemon_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testPokemonNameFunction() {
		def result = basicTemplateTest("templates/pokemon_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testRandomNextBooleanFunction() {
		def result = basicTemplateTest("templates/random_next_boolean_test.template")
		postExecute(result) { }
	}

	@Test
	void testRandomNextDoubleFunction() {
		def result = basicTemplateTest("templates/random_next_double_test.template")
		postExecute(result) { }
	}

	@Test
	void testRandomNextIntFunction() {
		def result = basicTemplateTest("templates/random_next_int_test.template")
		postExecute(result) { }
	}

	@Test
	void testRandomNextLongFunction() {
		def result = basicTemplateTest("templates/random_next_long_test.template")
		postExecute(result) { }
	}

	@Test
	void testRickAndMortyCharacterFunction() {
		def result = basicTemplateTest("templates/rickandmorty_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testRickAndMortyLocationFunction() {
		def result = basicTemplateTest("templates/rickandmorty_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testRickAndMortyQuoteFunction() {
		def result = basicTemplateTest("templates/rickandmorty_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testRockBandNameFunction() {
		def result = basicTemplateTest("templates/rockband_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testShakespeareAsYouLikeItQuoteFunction() {
		def result = basicTemplateTest("templates/shakespeare_as_you_like_it_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testShakespeareHamletQuoteFunction() {
		def result = basicTemplateTest("templates/shakespeare_hamlet_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testShakespeareKingRichardIIIQuoteFunction() {
		def result = basicTemplateTest("templates/shakespeare_king_richard_i_i_i_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testShakespeareRomeoAndJulietQuoteFunction() {
		def result = basicTemplateTest("templates/shakespeare_romeo_and_juliet_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiActivityFunction() {
		def result = basicTemplateTest("templates/slackemoji_activity_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiCelebrationFunction() {
		def result = basicTemplateTest("templates/slackemoji_celebration_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiCustomFunction() {
		def result = basicTemplateTest("templates/slackemoji_custom_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiEmojiFunction() {
		def result = basicTemplateTest("templates/slackemoji_emoji_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiFoodAndDrinkFunction() {
		def result = basicTemplateTest("templates/slackemoji_food_and_drink_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiNatureFunction() {
		def result = basicTemplateTest("templates/slackemoji_nature_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiObjectsAndSymbolsFunction() {
		def result = basicTemplateTest("templates/slackemoji_objects_and_symbols_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiPeopleFunction() {
		def result = basicTemplateTest("templates/slackemoji_people_test.template")
		postExecute(result) { }
	}

	@Test
	void testSlackEmojiTravelAndPlacesFunction() {
		def result = basicTemplateTest("templates/slackemoji_travel_and_places_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceAgencyFunction() {
		def result = basicTemplateTest("templates/space_agency_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceAgencyAbbreviationFunction() {
		def result = basicTemplateTest("templates/space_agency_abbreviation_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceCompanyFunction() {
		def result = basicTemplateTest("templates/space_company_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceConstellationFunction() {
		def result = basicTemplateTest("templates/space_constellation_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceDistanceMeasurementFunction() {
		def result = basicTemplateTest("templates/space_distance_measurement_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceGalaxyFunction() {
		def result = basicTemplateTest("templates/space_galaxy_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceMeteoriteFunction() {
		def result = basicTemplateTest("templates/space_meteorite_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceMoonFunction() {
		def result = basicTemplateTest("templates/space_moon_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceNasaSpaceCraftFunction() {
		def result = basicTemplateTest("templates/space_nasa_space_craft_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceNebulaFunction() {
		def result = basicTemplateTest("templates/space_nebula_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpacePlanetFunction() {
		def result = basicTemplateTest("templates/space_planet_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceStarFunction() {
		def result = basicTemplateTest("templates/space_star_test.template")
		postExecute(result) { }
	}

	@Test
	void testSpaceStarClusterFunction() {
		def result = basicTemplateTest("templates/space_star_cluster_test.template")
		postExecute(result) { }
	}

	@Test
	void testStockNsdqSymbolFunction() {
		def result = basicTemplateTest("templates/stock_nsdq_symbol_test.template")
		postExecute(result) { }
	}

	@Test
	void testStockNyseSymbolFunction() {
		def result = basicTemplateTest("templates/stock_nyse_symbol_test.template")
		postExecute(result) { }
	}

	@Test
	void testSuperheroDescriptorFunction() {
		def result = basicTemplateTest("templates/superhero_descriptor_test.template")
		postExecute(result) { }
	}

	@Test
	void testSuperheroNameFunction() {
		def result = basicTemplateTest("templates/superhero_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testSuperheroPowerFunction() {
		def result = basicTemplateTest("templates/superhero_power_test.template")
		postExecute(result) { }
	}

	@Test
	void testSuperheroPrefixFunction() {
		def result = basicTemplateTest("templates/superhero_prefix_test.template")
		postExecute(result) { }
	}

	@Test
	void testSuperheroSuffixFunction() {
		def result = basicTemplateTest("templates/superhero_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testTeamCreatureFunction() {
		def result = basicTemplateTest("templates/team_creature_test.template")
		postExecute(result) { }
	}

	@Test
	void testTeamNameFunction() {
		def result = basicTemplateTest("templates/team_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testTeamSportFunction() {
		def result = basicTemplateTest("templates/team_sport_test.template")
		postExecute(result) { }
	}

	@Test
	void testTeamStateFunction() {
		def result = basicTemplateTest("templates/team_state_test.template")
		postExecute(result) { }
	}

	@Test
	void testTwinPeaksCharacterFunction() {
		def result = basicTemplateTest("templates/twinpeaks_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testTwinPeaksLocationFunction() {
		def result = basicTemplateTest("templates/twinpeaks_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testTwinPeaksQuoteFunction() {
		def result = basicTemplateTest("templates/twinpeaks_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testUniversityNameFunction() {
		def result = basicTemplateTest("templates/university_name_test.template")
		postExecute(result) { }
	}

	@Test
	void testUniversityPrefixFunction() {
		def result = basicTemplateTest("templates/university_prefix_test.template")
		postExecute(result) { }
	}

	@Test
	void testUniversitySuffixFunction() {
		def result = basicTemplateTest("templates/university_suffix_test.template")
		postExecute(result) { }
	}

	@Test
	void testUUIDFunction() {
		def result = basicTemplateTest("templates/uuid_test.template")
		postExecute(result) { }
	}

	@Test
	void testWeatherDescriptionFunction() {
		def result = basicTemplateTest("templates/weather_description_test.template")
		postExecute(result) { }
	}

	@Test
	void testWeatherTemperatureCelsiusFunction() {
		def result = basicTemplateTest("templates/weather_temperature_celsius_test.template")
		postExecute(result) { }
	}

	@Test
	void testWeatherTemperatureFahrenheitFunction() {
		def result = basicTemplateTest("templates/weather_temperature_fahrenheit_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherCharacterFunction() {
		def result = basicTemplateTest("templates/witcher_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherLocationFunction() {
		def result = basicTemplateTest("templates/witcher_location_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherMonsterFunction() {
		def result = basicTemplateTest("templates/witcher_monster_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherQuoteFunction() {
		def result = basicTemplateTest("templates/witcher_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherSchoolFunction() {
		def result = basicTemplateTest("templates/witcher_school_test.template")
		postExecute(result) { }
	}

	@Test
	void testWitcherWitcherFunction() {
		def result = basicTemplateTest("templates/witcher_witcher_test.template")
		postExecute(result) { }
	}

	@Test
	void testYodaQuoteFunction() {
		def result = basicTemplateTest("templates/yoda_quote_test.template")
		postExecute(result) { }
	}

	@Test
	void testZeldaCharacterFunction() {
		def result = basicTemplateTest("templates/zelda_character_test.template")
		postExecute(result) { }
	}

	@Test
	void testZeldaGameFunction() {
		def result = basicTemplateTest("templates/zelda_game_test.template")
		postExecute(result) { }
	}
}